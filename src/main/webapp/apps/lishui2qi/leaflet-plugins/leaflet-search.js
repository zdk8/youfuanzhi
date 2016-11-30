﻿global.selectMarker=[];
global.circles;
global.flag;
var proxy="auth/proxy?url=";
(function() {

	L.Control.Search = L.Control.extend({
		includes: L.Mixin.Events,
	
	options: {
		wrapper: '',				//container id to insert Search Control
		url: '',					//url for search by ajax request, ex: "search.php?q={s}"
		jsonpParam: null,			//jsonp param name for search by jsonp service, ex: "callback"
		layer: null,				//layer where search markers(is a L.LayerGroup)
		wfslayer:false,
		callData: null,				//function that fill _recordsCache, passed searching text by first param and callback in second
		//TODO important! implements uniq option 'sourceData' that recognizes source type: url,array,callback or layer		
		//TODO implement can do research on multiple sources
		propertyName: 'title',		//property in marker.options(or feature.properties for vector layer) trough filter elements in layer,
		propertyLoc: 'loc',			//field for remapping location, using array: ['latname','lonname'] for select double fields(ex. ['lat','lon'] )
									// support dotted format: 'prop.subprop.title'
		callTip: null,				//function that return row tip html node(or html string), receive text tooltip in first param
		filterJSON: null,			//callback for filtering data to _recordsCache
		minLength: 1,				//minimal text length for autocomplete
		initial: true,				//search elements only by initial text
		autoType: true,				//complete input with first suggested result and select this filled-in text.
		delayType: 400,				//delay while typing for show tooltip
		tooltipLimit: -1,			//limit max results to show in tooltip. -1 for no limit.
		tipAutoSubmit: true,  		//auto map panTo when click on tooltip
		autoResize: true,			//autoresize on input change
		collapsed: true,			//collapse search control at startup
		autoCollapse: false,		//collapse search control after submit(on button or on tips if enabled tipAutoSubmit)
		//TODO add option for persist markerLoc after collapse!
		autoCollapseTime: 1200,		//delay for autoclosing alert and collapse after blur
		zoom: null,					//zoom after pan to location found, default: map.getZoom()
		text: '输入查找...........',			//placeholder value
		textCancel: '取消',		//title in cancel button
		textErr: '抱歉，无相关信息！',//'Location not found',	//error message
		position: 'topleft',
		maxFeatures:50,
		searchLayers:[],
		editLayers:[],
		searchField:'',
		animateLocation: true,		//animate a circle over location found
		circleLocation: true,		//draw a circle in location found
		markerLocation: false,		//draw a marker in location found
		markerIcon: new L.Icon.Default()//custom icon for maker location
	},


initialize: function(options) {
	L.Util.setOptions(this, options || {});
	this._inputMinSize = this.options.text ? this.options.text.length : 10;
	this._layer = this.options.layer || new L.LayerGroup();
	this._filterJSON = this.options.filterJSON || this._defaultFilterJSON;
		this._autoTypeTmp = this.options.autoType;	//useful for disable autoType temporarily in delete/backspace keydown
		this._countertips = 0;		//number of tips items
		this._recordsCache = {};	//key,value table! that store locations! format: key,latlng
		this._searchLayers=this.options.searchLayers;
		this._selectSearchLayer=null;
		this._histroyMarkers=[];
		this._searchField=this.options.searchField;
		this._editIndex=undefined;
		this._maxFeatures=this.options.maxFeatures;
	},

	onAdd: function (map) {
		this._map = map;
		this._container = L.DomUtil.create('div', 'leaflet-control-search');
		this._searchDiv=this._createSearchDiv(this.options.text,'leaflet-search-div');
		this._input = this._createInput(this.options.text, 'search-input');

		this._select = this._createLayersSelect(this.options.text, 'easyui-combobox');
		this._isedit =this._createIsChecked('easyui-linkbutton');
		this._tooltip = this._createTooltip('search-tooltip');
		this._cancel = this._createCancel(this.options.textCancel, 'search-cancel');
		this._button = this._createButton(this.options.text, 'search-button');
		this._alert = this._createAlert('search-alert');


		if(this.options.collapsed===false)
			this.expand();

		if(this.options.circleLocation || this.options.markerLocation || this.options.markerIcon)
			this._markerLoc = new SearchMarker([0,0], {
				showMarker: this.options.markerLocation,
				icon: this.options.markerIcon
				});//see below
		
		this.setLayer( this._layer );
		map.on({
		// 		'layeradd': this._onLayerAddRemove,
		// 		'layerremove': this._onLayerAddRemove
		'resize': this._handleAutoresize
	}, this);
		return this._container;
	},
	addTo: function (map) {

		if(this.options.wrapper) {
			this._container = this.onAdd(map);
			this._wrapper = L.DomUtil.get(this.options.wrapper);
			this._wrapper.style.position = 'relative';
			this._wrapper.appendChild(this._container);
		}
		else
			L.Control.prototype.addTo.call(this, map);

		var me=this;



		return this;
	},

//单击属性
propertyclick:function(index,table){
		var editIndex=this._editIndex;
		if (editIndex != index){
			if (this.endEditing(table)){
            	table.datagrid('selectRow', index)
            	.datagrid('beginEdit', index);
            	this._editIndex = index;
            } else {
            	table.datagrid('selectRow', editIndex);
            }
        }else{
        	table.datagrid('beginEdit', index);
        }
    },
 // 更新要素属性   
 updateFeatureProperty:function(feature,table,layer){
    	var changedata=table.datagrid('getChanges');
    	table.datagrid('acceptChanges');	
    	if(changedata.length>0){
    		layer.edited=true;
    		if(!feature.properties)feature.properties={};
    		for(var i=0;i<changedata.length;i++){
    			feature.properties[changedata[i].field]=changedata[i].value;
    		}
    	}
    },
//生成要素popup
 makesearchPopup:function(feature,marker,type,isediting){
    	var that=this;
    	this._map.closePopup();
    	var id=feature.id.replace(".","_")+type;
    	var tablestr='<table id="grid'+id+'" style="width:300px" ></table>';
    	marker.bindPopup(tablestr);

},
//属性表结束编辑
endEditing:function(table){
	var editIndex=this._editIndex;
	if (editIndex == undefined){return true}
		if (table.datagrid('validateRow', editIndex)){
            table.datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
        	return false;
        }
    },
 // 生成编辑控制模块
makeDrawEdit:function(){
    	var wfs_url=this._selectSearchLayer.url;
    	var layers=this._selectSearchLayer.layers.split(":");
    	var featurens=layers[0];
    	var featuretype=layers[layers.length-1];
    	var me=this;
    	var drawnItems = L.wfst(null,{
            // Required
          // url : proxy+wfs_url+"?service=wfs", //'http://192.168.2.142:8080/geoserver/zsmz/wfs'
           url : proxy="auth/proxy?url="+wfs_url+"?service=wfs",
            featureNS : featurens,//xsdata  zs_csmz
            version:'1.1.0',
            searchLayer:me,
            onEachFeature: function (feature,marker){return me.makesearchPopup(feature,marker,'edit');},
            featureType : featuretype//*,STP_DW STL_ALL_ROAD  STR_XianJ
        }).addTo(me._map);

	//将编辑后的数据添加到mylayercollection中，为清理功能使用；

	mylayercollection.push(drawnItems);

    // Initialize the draw control and pass it the FeatureGroup of editable layers,marker means [point]
    var drawControl = new L.Control.Draw({
    	draw: {
    		polygon: me._selectSearchLayer.shape==='polygon',
    		marker: me._selectSearchLayer.shape==='marker',
    		rectangle:me._selectSearchLayer.shape==='rectangle',
    		circle:me._selectSearchLayer.shape==='circle',
    		polyline:me._selectSearchLayer.shape==='polyline'
    	},
    	edit: {
    		featureGroup: drawnItems,
    		searchlayer:me
    	}
    });
    me._drawControl=drawControl;
    me._drawnItems=drawnItems;
    me._map.addControl(drawControl);

    me._map.on('draw:created', me.drawcreated,me);
    me._map.on('draw:editstart', function (e) {
        //layers.drawnItems.addLayer(e.layer);
    });
    me._map.on('draw:edited',me.drawedited,me);
    me._map.on('draw:deleted',me.drawdeleted,me);

},
//删除完成后触发事件
drawdeleted:function(e){
    this._drawnItems.removeLayerFromWFS(e.layers.getLayers());
},
//编辑完成后触发事件
drawedited: function (e) {
    	this._drawnItems.wfstSave(e.layers);
 }
,
//新增完成后触发事件
drawcreated:function (e) {
    	var layer=e.layer;

     var me=this;
    	this._drawnItems.addLayer(layer,{"success":function(){
    		var feature=layer.feature;
    		me.makesearchPopup(feature,layer,'edited',true);
			//为了删除所添加的；
			global.selectMarker.push(feature);

    	}});
    }
,
//删除缓存清空
onRemove: function(map) {
	this._recordsCache = {};
		// map.off({
		// 		'layeradd': this._onLayerAddRemove,
		// 		'layerremove': this._onLayerAddRemove
		// 	}, this);
},

	/* _onLayerAddRemove: function(e) {
	console.info('_onLayerAddRemove');
		//without this, run setLayer also for each Markers!! to optimize!
		if(e.layer instanceof L.LayerGroup)
		if( L.stamp(e.layer) != L.stamp(this._layer) )
				this.setLayer(e.layer);
	},*/
//获取路径
_getPath: function(obj, prop) {
	var parts = prop.split('.'),
	last = parts.pop(),
	len = parts.length,
	cur = parts[0],
	i = 1;

	if(len > 0)
		while((obj = obj[cur]) && i < len)
			cur = parts[i++];

		if(obj)
			return obj[last];
	},
//设置指定图层
setLayer: function(layer) {	//set search layer at runtime
	//this.options.layer = layer; //setting this, run only this._recordsFromLayer()
	this._layer = layer;
	this._layer.addTo(this._map);
	if(this._markerLoc)
		this._layer.addLayer(this._markerLoc);
	return this;
},
//警告
showAlert: function(text) {
	text = text || this.options.textErr;
	this._alert.style.display = 'block';
	this._alert.innerHTML = text;
	clearTimeout(this.timerAlert);
	var that = this;		
	this.timerAlert = setTimeout(function() {
		that.hideAlert();
	},this.options.autoCollapseTime);
	return this;
},
//hide 警告
hideAlert: function() {
	this._alert.style.display = 'none';
	return this;
},
//取消（暂时放弃）
cancel: function() {
	this._input.value = '';
	this._handleKeypress({keyCode:8});//simulate backspace keypress
	this._input.size = this._inputMinSize;
	this._input.focus();
	this._cancel.style.display = 'none';
	return this;
},
//搜索展开
expand: function() {
	$(this._searchDiv).show();
	L.DomUtil.addClass(this._container, 'search-exp');
	this._input.focus();
	this._map.on('dragstart click', this.collapse, this); //拖动地图隐藏搜索栏
	return this;	
},
//搜索收缩
collapse: function() {
	this._hideTooltip();
	this.cancel();
	this._alert.style.display = 'none';
	this._input.blur();
	if(this.options.collapsed)
	{
		$(this._searchDiv).hide();
		this._cancel.style.display = 'none';
		L.DomUtil.removeClass(this._container, 'search-exp');		
		//this._markerLoc.hide();//maybe unuseful
		this._map.off('dragstart click', this.collapse, this);
	}
	this.fire('search_collapsed');
	return this;
},

collapseDelayed: function() {	//collapse after delay, used on_input blur
	if (!this.options.autoCollapse) return this;
	var that = this;
	clearTimeout(this.timerCollapse);
	this.timerCollapse = setTimeout(function() {
		that.collapse();
	}, this.options.autoCollapseTime);
	return this;		
},

collapseDelayedStop: function() {
	clearTimeout(this.timerCollapse);
	return this;		
},

////start DOM creations 以下几个create 是search的控件生成，不一一重复
_createAlert: function(className) {
	var alert = L.DomUtil.create('div', className, this._container);
	alert.style.display = 'none';

	L.DomEvent
	.on(alert, 'click', L.DomEvent.stop, this)
	.on(alert, 'click', this.hideAlert, this);

	return alert;
},

_createInput: function (text, className) {
	var input = L.DomUtil.create('input', className, this._searchDiv);
	input.type = 'text';
	input.size = this._inputMinSize;
	input.value = '';
	input.autocomplete = 'off';
	input.placeholder = text;
		//input.style.display = 'none';
		
		L.DomEvent
		.disableClickPropagation(input)
		.on(input, 'keyup', this._handleKeypress, this)
		.on(input, 'keydown', this._handleAutoresize, this)
		.on(input, 'blur', this.collapseDelayed, this)
		.on(input, 'focus', this.collapseDelayedStop, this);
		
		return input;
	},
	_createSearchDiv:function(text,className){
		var div=L.DomUtil.create('div', className,this._container);
		div.style.display = 'none';
		L.DomEvent
		.disableClickPropagation(div)
            //.on(input, 'keyup', this._handleKeypress, this)
            //.on(input, 'keydown', this._handleAutoresize, this)
            .on(div, 'blur', this.collapseDelayed, this)
            .on(div, 'focus', this.collapseDelayedStop, this);
            return div;
        },
        _createIsChecked:function(className){
        	var input = L.DomUtil.create('a', className, this._searchDiv);
        	$(input).attr("id","map_edit_btn");
        	return input;

        },
        _createLayersSelect: function (text, className) {
		var input = L.DomUtil.create('input', className, this._searchDiv); //this._container

		$(input).attr("id","map_search_layers");
		$(input).attr("data-options",
			"data:"+ $.toJSON(this._searchLayers)
			+""
			);

		L.DomEvent
		.disableClickPropagation(input)
			//.on(input, 'keyup', this._handleKeypress, this)
			//.on(input, 'keydown', this._handleAutoresize, this)
			.on(input, 'blur', this.collapseDelayed, this)
			.on(input, 'focus', this.collapseDelayedStop, this);
			return input;
		},

		_createCancel: function (title, className) {
			var cancel = L.DomUtil.create('a', className, this._container);
			cancel.href = '#';
			cancel.title = title;
			cancel.style.display = 'none';
		cancel.innerHTML = "<span>&otimes;</span>";//imageless(see css)

		L.DomEvent
		.on(cancel, 'click', L.DomEvent.stop, this)
		.on(cancel, 'click', this.cancel, this);

		return cancel;
	},
	
	_createButton: function (title, className) {
		var button = L.DomUtil.create('a', className, this._container);
		button.href = '#';
		button.title = title;

		L.DomEvent
		.on(button, 'click', L.DomEvent.stop, this)
		.on(button, 'click', this._handleSubmit, this)			
		.on(button, 'focus', this.collapseDelayedStop, this)
		.on(button, 'blur', this.collapseDelayed, this);

		return button;
	},

	_createTooltip: function(className) {
		var tool = L.DomUtil.create('div', className, this._container);
		tool.style.display = 'none';

		var that = this;
		L.DomEvent
		.disableClickPropagation(tool)
		.on(tool, 'blur', this.collapseDelayed, this)
		.on(tool, 'mousewheel', function(e) {
			that.collapseDelayedStop();
				L.DomEvent.stopPropagation(e);//disable zoom map
			}, this)
		.on(tool, 'mouseover', function(e) {
			that.collapseDelayedStop();
		}, this);
		return tool;
	},

	_createTip: function(text, val) {//val is object in recordCache, usually is Latlng
		var tip;
		//console.log(val);
		text=val.layer.feature.properties[this._searchField];
		
		if(this.options.callTip)
		{
			tip = this.options.callTip(text,val); //custom tip node or html string
			if(typeof tip === 'string')
			{
				var tmpNode = L.DomUtil.create('div');
				tmpNode.innerHTML = tip;
				tip = tmpNode.firstChild;
			}
		}
		else
		{
			tip = L.DomUtil.create('a', '');
			tip.href = '#';
			tip.innerHTML = text;
		}
		
		L.DomUtil.addClass(tip, 'search-tip');
		tip._text = text; //value replaced in this._input and used by _autoType

		L.DomEvent
		.disableClickPropagation(tip)		
		.on(tip, 'click', L.DomEvent.stop, this)
		.on(tip, 'click', function(e) {
			this._input.value = text;
			this._input.layerdata=val;
			this._handleAutoresize();
			this._input.focus();
			this._hideTooltip();	
				if(this.options.tipAutoSubmit)//go to location at once
					this._handleSubmit();
			}, this);

		return tip;
	},

//////end DOM creations

// 过滤搜索结果
_filterRecords: function(text) {	//Filter this._recordsCache case insensitive and much more..

	var regFilter = new RegExp("^[.]$|[\[\]|()*]",'g'),	//remove . * | ( ) ] [
	I, regSearch,
	frecords = {};

	text = text.replace(regFilter,'');	  //sanitize text
	I = this.options.initial ? '^' : '';  //search only initial text
	//TODO add option for case sesitive search, also showLocation
	regSearch = new RegExp(I + text,'i');


	//TODO use .filter or .map
	for(var key in this._recordsCache)
		if( regSearch.test(key) )
			frecords[key]= this._recordsCache[key];
		return frecords;
	},

	showTooltip: function() {
		var filteredRecords, newTip;

		this._countertips = 0;

//FIXME problem with jsonp/ajax when remote filter has different behavior of this._filterRecords
if(this.options.layer)
	filteredRecords = this._filterRecords( this._input.value );
else
	filteredRecords = this._recordsCache;
this._tooltip.innerHTML = '';
	this._tooltip.currentSelection = -1;  //inizialized for _handleArrowSelect()

	for(var key in filteredRecords)//fill tooltip
	{
		if(++this._countertips == this.options.tooltipLimit) break;

		newTip = this._createTip(key, filteredRecords[key] );

		this._tooltip.appendChild(newTip);
	}
	
	if(this._countertips > 0)
	{
		this._tooltip.style.display = 'block';
		if(this._autoTypeTmp)
			this._autoType();
		this._autoTypeTmp = this.options.autoType;//reset default value
	}
	else
		this._hideTooltip();

	this._tooltip.scrollTop = 0;
	return this._countertips;
},
//隐藏toolTip
_hideTooltip: function() {
	this._tooltip.style.display = 'none';
	this._tooltip.innerHTML = '';
	return 0;
},
//默认过滤效果

_defaultFilterJSON: function(json) {	//default callback for filter data
	var jsonret = {}, i,
		//propName = this.options.propertyName,
		propName = this._searchField,
		propLoc = this.options.propertyLoc;
		if( L.Util.isArray(propLoc) )

			for(i in json)

				jsonret[ this._getPath(json[i],propName) ]= L.latLng( json[i][ propLoc[0] ], json[i][ propLoc[1] ] );

				for(i in json)
					jsonret[ this._getPath(json[i],propName) ]= L.latLng( this._getPath(json[i],propLoc) );
	//TODO throw new Error("propertyName '"+propName+"' not found in JSON data");
	return jsonret;
},
//读取数据 json
_recordsFromJsonp: function(text, callAfter) {  //extract searched records from remote jsonp service
	//TODO remove script node after call run
	var that = this;

	L.Control.Search.callJsonp = function(data) {	//jsonp callback
		var fdata = that._filterJSON(data);//_filterJSON defined in inizialize...

		callAfter(fdata);
	}
	var script = L.DomUtil.create('script','search-jsonp', document.getElementsByTagName('body')[0] ),			
		url = L.Util.template(this.options.url+'&'+this.options.jsonpParam+'=L.Control.Search.callJsonp', {s: text}); //parsing url
		//rnd = '&_='+Math.floor(Math.random()*10000);
		//TODO add rnd param or randomize callback name! in recordsFromJsonp
		script.type = 'text/javascript';
		script.src = url;
		return this;
	//may be return {abort: function() { script.parentNode.removeChild(script); } };
},

//读取数据 wfs server
_recordsFromWfs:function(text,callAfter){
	this._selectSearchLayer;

	if(this._selectSearchLayer==null){
		alert('请选择要查询的图层!');
	}

	var xml_str='<wfs:GetFeature';
	xml_str += ' service="WFS" version="1.1.0" '
	+' outputFormat="JSON"'
	+' maxFeatures="'+this._maxFeatures+'"'
	+' xmlns:wfs="http://www.opengis.net/wfs"'
	+' xmlns:ogc="http://www.opengis.net/ogc">'
	+' <wfs:Query typeName="'+this._selectSearchLayer.layers+'">'
	+ this._makeFilters({field:this._searchField,value:text})
	+'</wfs:Query>'
	+'</wfs:GetFeature>';

   // console.log( proxy+this._selectSearchLayer.url)
	$.ajax({
		type: 'POST',
		contentType: "text/hda; charset=utf-8",
		//url: proxy+this._selectSearchLayer.url,
		url: "auth/proxy?url="+this._selectSearchLayer.url,
		processData: false,
		data: xml_str,
		success: callAfter,
		dataType: "json"
	});


},
// ogc 标准过滤条件 
_makeFilters:function(fieldValue){
	var str="<ogc:Filter>"
	+ "<ogc:PropertyIsLike wildCard='*' singleChar='.' escape='!'>"
	+ "<ogc:PropertyName>"+fieldValue.field+"</ogc:PropertyName>"
	+ "<ogc:Literal>*"+fieldValue.value+"*</ogc:Literal>"
	+ "</ogc:PropertyIsLike>"
	+ "</ogc:Filter>";
	return str;

},
//  数据从ajax
_recordsFromAjax: function(text, callAfter) {	//Ajax request
	if (window.XMLHttpRequest === undefined) {
		window.XMLHttpRequest = function() {
			try { return new ActiveXObject("Microsoft.XMLHTTP.6.0"); }
			catch  (e1) {
				try { return new ActiveXObject("Microsoft.XMLHTTP.3.0"); }
				catch (e2) { throw new Error("XMLHttpRequest is not supported"); }
			}
		};
	}
	var request = new XMLHttpRequest(),
		url = L.Util.template(this.options.url, {s: text}), //parsing url
		//rnd = '&_='+Math.floor(Math.random()*10000);
		//TODO add rnd param or randomize callback name! in recordsFromAjax			
		response = {};

		request.open("GET", url);
		var that = this;
		request.onreadystatechange = function() {
			if(request.readyState === 4 && request.status === 200) {
				response = JSON.parse(request.responseText);
	    	var fdata = that._filterJSON(response);//_filterJSON defined in inizialize...
	    	callAfter(fdata);
	    }else{
	    	L.DomUtil.removeClass(that._container, 'search-load');
	    }
	};
	request.send();
	return this;   
},	
// 数据从layer
_recordsFromLayer: function() {	//return table: key,value from layer
	var that = this,
	retRecords = {},
		//propName = this.options.propertyName,
		propName = this._searchField,
		loc;

		this._layer.eachLayer(function(layer) {

			if(layer instanceof SearchMarker) return;

			if(layer instanceof L.Marker)
			{
				
				loc = layer.getLatLng();
				loc.layer = layer;
				retRecords[ layer.feature.id] = loc;

			}
		else if(layer.hasOwnProperty('feature'))//GeoJSON layer
		{
			
			loc = layer.getBounds().getCenter();
			loc.layer = layer;
			retRecords[ layer.feature.id] = loc;

		}
		
	},this);
		return retRecords;

	},

	_autoType: function() {

	//TODO implements autype without selection(useful for mobile device)
	
	var start = this._input.value.length,
	firstRecord = this._tooltip.firstChild._text,
	end = firstRecord.length;

	if (firstRecord.indexOf(this._input.value) === 0) { // If prefix match
		this._input.value = firstRecord;
		this._handleAutoresize();

		if (this._input.createTextRange) {
			var selRange = this._input.createTextRange();
			selRange.collapse(true);
			selRange.moveStart('character', start);
			selRange.moveEnd('character', end);
			selRange.select();
		}
		else if(this._input.setSelectionRange) {
			this._input.setSelectionRange(start, end);
		}
		else if(this._input.selectionStart) {
			this._input.selectionStart = start;
			this._input.selectionEnd = end;
		}
	}
},

_hideAutoType: function() {	// deselect text:

	var sel;
	if ((sel = this._input.selection) && sel.empty) {
		sel.empty();
	}
	else if (this._input.createTextRange) {
		sel = this._input.createTextRange();
		sel.collapse(true);
		var end = this._input.value.length;
		sel.moveStart('character', end);
		sel.moveEnd('character', end);
		sel.select();
	}
	else {
		if (this._input.getSelection) {
			this._input.getSelection().removeAllRanges();
		}
		this._input.selectionStart = this._input.selectionEnd;
	}
},

//搜索键盘事件

_handleKeypress: function (e) {	//run _input keyup event
	switch(e.keyCode)
	{
		case 27: //Esc
		this.collapse();
		break;
		case 13: //Enter
		if(this._countertips == 1)
			this._handleArrowSelect(1);
			this._handleSubmit();	//do search
			break;
		case 38://Up
		this._handleArrowSelect(-1);
		break;
		case 40://Down
		this._handleArrowSelect(1);
		break;
		case 37://Left
		case 39://Right
		case 16://Shift
		case 17://Ctrl
		//case 32://Space
		break;
		//case 8://backspace
		case 46://delete
			this._autoTypeTmp = false;//disable temporarily autoType
			break;
		default://All keys

		if(this._input.value.length)
			this._cancel.style.display ='none' ;//'inline-block';
		else
			this._cancel.style.display = 'none';

		if(this._input.value.length >= this.options.minLength)
		{

			var that = this;
				clearTimeout(this.timerKeypress);	//cancel last search request while type in				
				this.timerKeypress = setTimeout(function() {	//delay before request, for limit jsonp/ajax request

					that._fillRecordsCache();

				}, this.options.delayType);
			}
			else
				this._hideTooltip();
		}
	},
	//删除历史要素
	_clearHistoryMarkers:function(){
		for(var i=0;i<this._histroyMarkers.length;i++){
			this._map.removeLayer(this._histroyMarkers[i]);
		}
		this._histroyMarkers=[];
	},

        //_recordsCache这时候村数据；
	//缓存数据
	_fillRecordsCache: function() {
//TODO important optimization!!! always append data in this._recordsCache
//  now _recordsCache content is emptied and replaced with new data founded
//  always appending data on _recordsCache give the possibility of caching ajax, jsonp and layersearch!
//
//TODO here insert function that search inputText FIRST in _recordsCache keys and if not find results.. 
//  run one of callbacks search(callData,jsonpUrl or options.layer) and run this.showTooltip
//
//TODO change structure of _recordsCache
//	like this: _recordsCache = {"text-key1": {loc:[lat,lng], ..other attributes.. }, {"text-key2": {loc:[lat,lng]}...}, ...}
//	in this mode every record can have a free structure of attributes, only 'loc' is required

var inputText = this._input.value,
that;

L.DomUtil.addClass(this._container, 'search-load');
		if(this.options.callData)	//CUSTOM SEARCH CALLBACK

		{
			that = this;
			this.options.callData(inputText, function(jsonraw) {

				that._recordsCache = that._filterJSON(jsonraw);

				that.showTooltip();

				L.DomUtil.removeClass(that._container, 'search-load');
			});
		}else if(this._searchLayers){
			that=this;
			this._clearHistoryMarkers();
			this._searchInputText=inputText;
            	this._recordsFromWfs(inputText,function(data) {// is async request then it need callback
            	var featuresLayer = new L.GeoJSON(data, {
            		style: function(feature) {
            			return {color: "pink" };
            		},
            		coordsToLatLng:function(a){
            			return a;
            		},
            		onEachFeature: function(feature, marker) {
            			that._histroyMarkers.push(marker);
            			that.makesearchPopup(feature,marker,'search');

            		}
            	});
            	//that._map.removeLayer(that._layer);
            	that._map.addLayer(featuresLayer);
                    global.selectMarker.push(featuresLayer)
            	that._map.off('draw:created', that.drawcreated,that);
		     that._map.off('draw:edited',that.drawedited,that);
    			that._map.off('draw:deleted',that.drawdeleted,that);
		     
		     if(that._drawControl){

		     	   that._map.removeControl(that._drawControl);
		        
    			   that._map.removeLayer(that._drawnItems);
		     	   that.makeDrawEdit() ;
		     }

            	that._layer=featuresLayer;
            	that._geojson=data;
                that._recordsCache = that._recordsFromLayer();	//fill table key,value from markers into layer
                that.showTooltip();
                L.DomUtil.removeClass(that._container, 'search-load');

            });
        }
		else if(this.options.url)	//JSONP/AJAX REQUEST
		{
			if(this.options.jsonpParam)
			{
				that = this;
				this._recordsFromJsonp(inputText, function(data) {// is async request then it need callback
					that._recordsCache = data;
					that.showTooltip();
					L.DomUtil.removeClass(that._container, 'search-load');
				});
			}
			else
			{
				that = this;
				this._recordsFromAjax(inputText, function(data) {// is async request then it need callback
					that._recordsCache = data;
					that.showTooltip();
					L.DomUtil.removeClass(that._container, 'search-load');
				});
			}
		}
		else if(this.wfslayer){

		}
		else if(this.options.layer)	//SEARCH ELEMENTS IN PRELOADED LAYER
		{
			this._recordsCache = this._recordsFromLayer();	//fill table key,value from markers into layer				
			this.showTooltip();
			L.DomUtil.removeClass(this._container, 'search-load');
		}
	},
	
	_handleAutoresize: function() {	//autoresize this._input
	    //TODO refact _handleAutoresize now is not accurate
	    if (this._input.style.maxWidth != this._map._container.offsetWidth) //If maxWidth isn't the same as when first set, reset to current Map width
	    	this._input.style.maxWidth = L.DomUtil.getStyle(this._map._container, 'width');

	    if(this.options.autoResize && (this._container.offsetWidth + 45 < this._map._container.offsetWidth))
	    	this._input.size = this._input.value.length<this._inputMinSize ? this._inputMinSize : this._input.value.length;
	},

	_handleArrowSelect: function(velocity) {

		var searchTips = this._tooltip.hasChildNodes() ? this._tooltip.childNodes : [];

		for (i=0; i<searchTips.length; i++)
			L.DomUtil.removeClass(searchTips[i], 'search-tip-select');
		
		if ((velocity == 1 ) && (this._tooltip.currentSelection >= (searchTips.length - 1))) {// If at end of list.
			L.DomUtil.addClass(searchTips[this._tooltip.currentSelection], 'search-tip-select');
		}
		else if ((velocity == -1 ) && (this._tooltip.currentSelection <= 0)) { // Going back up to the search box.
			this._tooltip.currentSelection = -1;
		}
		else if (this._tooltip.style.display != 'none') { // regular up/down
			this._tooltip.currentSelection += velocity;
			
			L.DomUtil.addClass(searchTips[this._tooltip.currentSelection], 'search-tip-select');
			
			this._input.value = searchTips[this._tooltip.currentSelection]._text;

			// scroll:
			var tipOffsetTop = searchTips[this._tooltip.currentSelection].offsetTop;
			
			if (tipOffsetTop + searchTips[this._tooltip.currentSelection].clientHeight >= this._tooltip.scrollTop + this._tooltip.clientHeight) {
				this._tooltip.scrollTop = tipOffsetTop - this._tooltip.clientHeight + searchTips[this._tooltip.currentSelection].clientHeight;
			}
			else if (tipOffsetTop <= this._tooltip.scrollTop) {
				this._tooltip.scrollTop = tipOffsetTop;
			}
		}
	},
     
     //搜索提交
	_handleSubmit: function() {	//button and tooltip click and enter submit
		this._hideAutoType();
		
		this.hideAlert();
		this._hideTooltip();


		if(this._searchDiv.style.display == 'none')	//on first click show _input only
			this.expand();
		else
		{
			if(this._input.value === '')	//hide _input only
				this.collapse();
			else
			{
                pp = this._input;
             var loc = this._getLocation(this._input.layerdata.layer.feature.id);
                //var loc = this._getLocation(this._input.value);
                if(loc===false)
                    this.showAlert();
                else
                {
                    this.showLocation(loc, this._input.value);
                    this.fire('search_locationfound', {
                        latlng: loc,
                        text: this._input.value,
                        layer: loc.layer ? loc.layer : null
                    });
                }
				//this.collapse();
				//FIXME if collapse in _handleSubmit hide _markerLoc!
			}
		}
	},
      //获取位置
	_getLocation: function(key) {	//extract latlng from _recordsCache

        //console.log(this._recordsCache.hasOwnProperty("舟山市定海区岑港镇社会治安综合治理协会"))
		if( this._recordsCache.hasOwnProperty(key) ){
            return this._recordsCache[key];//then after use .loc attribute
        }

		else{
            return false;
        }
	},
      // 显示位置
	showLocation: function(latlng, title) {	//set location on map from _recordsCache

		if(this.options.zoom)
        {this._map.setView(latlng, this.options.zoom);}
		else
        {this._map.panTo(latlng);}
		if(this._markerLoc)
		{
		 this._markerLoc.setLatLng(latlng);  //show circle/marker in location found
			this._markerLoc.setTitle(title);
			this._markerLoc.show();
           // global.flag=true;
			if(this.options.animateLocation)
				this._markerLoc.animate();
           // circle.hide();

			//TODO showLocation: start animation after setView or panTo, maybe with map.on('moveend')...	
		}
		
		//FIXME autoCollapse option hide this._markerLoc before that visualized!!
		if(this.options.autoCollapse)
			this.collapse();
		return this;
	}
});
// SearchMarker extend from L.Marker
var SearchMarker = L.Marker.extend({

	includes: L.Mixin.Events,
	
	options: {
		radius: 10,
		weight: 3,
		color: '#e03',
		stroke: true,
		fill: false,
		title: '',
		icon: new L.Icon.Default(),
		showMarker: false	//show icon optional, show only circleLoc
	},
	
	initialize: function (latlng, options) {
		L.setOptions(this, options);
		L.Marker.prototype.initialize.call(this, latlng, options);
		this._circleLoc = new L.CircleMarker(latlng, this.options);

	},

	onAdd: function (map) {
		L.Marker.prototype.onAdd.call(this, map);
		map.addLayer(this._circleLoc);
		this.hide();
	},

	onRemove: function (map) {
		L.Marker.prototype.onRemove.call(this, map);
		map.removeLayer(this._circleLoc);
	},	
	
	setLatLng: function (latlng) {
		L.Marker.prototype.setLatLng.call(this, latlng);
		this._circleLoc.setLatLng(latlng);
		return this;
	},
	
	setTitle: function(title) {
		title = title || '';
		this.options.title = title;
		if(this._icon)
			this._icon.title = title;
		return this;
	},

	show: function() {
		if(this.options.showMarker)
		{
			if(this._icon)
				this._icon.style.display = 'block';
			if(this._shadow)
				this._shadow.style.display = 'block';
			//this._bringToFront();
		}
		if(this._circleLoc)
		{
			this._circleLoc.setStyle({fill: this.options.fill, stroke: this.options.stroke});
			//this._circleLoc.bringToFront();
		}
		return this;
	},

	hide: function() {
		if(this._icon)
			this._icon.style.display = 'none';
		if(this._shadow)
			this._shadow.style.display = 'none';
		if(this._circleLoc)			
			this._circleLoc.setStyle({fill: false, stroke: false});
		return this;
	},

	animate: function() {

	//TODO refact animate() more smooth! like this: http://goo.gl/DDlRs
      // this._radius.setRadius(10);
	var circle = this._circleLoc,
			tInt = 200,	//time interval
			ss = 10,	//frames
			mr = parseInt(circle._radius/ss),
			oldrad = this.options.radius,
			//newrad = circle._radius * 2.5,
             newrad=10*2.5;
			acc = 0;
			circle._timerAnimLoc = setInterval(function() {

				acc += 0.5;
			mr += acc;	//adding acceleration
			newrad -= mr;
			circle.setRadius(newrad);
                global.circles=circle;
              // global.selectMarker.push(circle);
                //circle= new L.CircleMarker(latlng, this.options);
			if(newrad<oldrad)
			{
				clearInterval(circle._timerAnimLoc);
				circle.setRadius(oldrad);//reset radius
				//if(typeof afterAnimCall == 'function')
					//afterAnimCall();
					//TODO use create event 'animateEnd' in SearchMarker 
				}
			}, tInt);
			return this;
		}
	});

L.Map.addInitHook(function () {
	if (this.options.searchControl) {
		this.searchControl = L.control.search(this.options.searchControl);
		this.addControl(this.searchControl);
	}
});
L.control.search = function (options) {
	return new L.Control.Search(options);
};

}).call(this);

