define(['underscore'], function (_) {

    var tpl = _.template('<a class="tip-index" title="<%=cuurtime.pattern(\'HH:mm:ss\')%>"><%=index%></a><a class="tip-title"><%=title%></a>:<a class="tip-text"><%=text%></a><a class="tip-close">x</a>');
    var TipIndex = 1;
    //模型
    var Tip = Backbone.Model.extend({
        defaults: {
            title: null,
            text: null,
            index: 0,
            cuurtime:new Date()
        },

        promptColor: function () {
            var cssColor = prompt("Please enter a CSS color:");
            this.set({color: cssColor});
        },
        initialize: function () {

        }
    });

    //集合
    var TipList = Backbone.Collection.extend({
        model: Tip
    });
    var tipList = new TipList();

    //Tip视图
    var TipView = Backbone.View.extend({

        tagName: "li",

        className: "mytip-row",

        events: {
            "click .icon": "open",
            "click a.tip-close": "closeMe",
            "click .button.edit": "openEditDialog",
            "click .button.delete": "destroy"
        },
        initialize: function () {
            this.listenTo(this.model, "change", this.render);
            this.listenTo(this.model, "remove", this.remove);
        },
        closeMe: function () {
            tipList.remove(this.model);
        },
        render: function () {
            this.$el.append(tpl(this.model.toJSON()));
        }

    });


    //TipManagerView
    TipManagerView = Backbone.View.extend({
        tagName: "ul",
        className: "document-row",

        events: {
            "click .icon": "open",
            "click .button.edit": "openEditDialog",
            "click .button.delete": "destroy"
        },

        initialize: function () {
            this.listenTo(this.model, "change", this.render);
            this.listenTo(tipList, 'remove', function (model, collection, options) {
                console.log('remove');
                console.log(model.toJSON());
            });
            this.listenTo(tipList, 'add', this.onAddTip);

        },
        renderItem: function (tip) {
            var tipView = new TipView({model: tip});
            tipView.render();
            this.$el.prepend(tipView.$el);
        },
        onAddTip: function (model, collection, options) {
            $('#tip-manager').show();
            var tip = model;
            tip.set({index: TipIndex++});
            this.renderItem(tip);
        },
        addTip: function (tipJson) {
            tipList.add(new Tip(tipJson));
        },
        render: function () {
            //初始化时添加5个
            for (var i = 0; i < 5; i++) {
                tipList.add(new Tip({title: 'Title' + i, text: 'text' + i * 5}));
            }
        }
    });

    tipManagerView = new TipManagerView();
    tipManagerView.render();
    $('#tip-manager').append(tipManagerView.$el);
    $('#tip-manager>div>a.close').bind('click',function () {
        $('#tip-manager').hide();
    });
    return {
        render: function () {

        },
        /*title,text,callback()*/
        add: function (tip) {
            //tipManagerView.add(tip);
        },
        addTip: function (title, text) {
            tipManagerView.addTip({title: title, text: text});
        }
    }
});