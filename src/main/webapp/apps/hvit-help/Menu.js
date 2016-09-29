define([cj.getModuleJs('model/BzModel'),'underscore'],
       function(BzModel,_){

         return {render:function(local,clickFn){

           var Menu = BzModel.extend({
             defaults: function() {
               return {
                 leaf: false
               };
             },
             toggle: function() {
               this.save({done: !this.get("done")});
             }
           });

           var MenuList = Backbone.Collection.extend({
             //url:'menutree',
             url:'data-json/menutree.json',
             model: Menu
           });
           var Menus = new MenuList;
           var template_htm='<a class="dir leaf"><%=title%></a>';
           var MenuView = Backbone.View.extend({
             tagName: "li",
             className:'inactives',
             template: _.template(template_htm),
             events: {
               "click .dir"    : "clickDir",
               "click .leaf"    : "clickLeaf"
             },
             initialize: function() {
               this.listenTo(this.model, 'change', this.render);
               this.listenTo(this.model, 'destroy', this.remove);
             },
             render: function() {
               this.$el.html(this.template(this.model.toJSON()));
               this.$('.leaf').toggleClass('leaf', this.model.get('leaf'));
               this.$('.dir').toggleClass('dir', !this.model.get('leaf'));
               this.$el.toggleClass('leaf', this.model.get('leaf'));
               this.$el.toggleClass('dir', !this.model.get('leaf'));
               return this;
             },
             clickLeaf:function(event){
               event.stopPropagation();
               clickFn(this.model.toJSON());
             },
             clickDir:function(){
               //
               if(this.model.get('loadlist')){
                 if(this.$('ul').is(':visible')){
                   this.$('ul').hide();
                 }else{
                   this.$('ul').show();
                 }
                 console.log(9999)
                 return;
               }
               var Menus2 = new MenuList;
               this.listenTo(Menus2, 'add',function(todo){
                 var view = new MenuView({model: todo});
                 var vel=view.render().el;
                 if(!this.$('ul').length){
                   this.$el.append('<ul></ul>');
                 }
                 this.$('ul').append(vel);
               });
               Menus2.fetch({ data: $.param({id:this.model.get('id') }) });
               this.model.set('loadlist',true);
             }
           });


           var AppView = Backbone.View.extend({
             el: local,
             events: {
               "click div[opt=add]":  "addOneView"
             },
             initialize: function() {
               this.listenTo(Menus, 'add', this.addOne);
               this.listenTo(Menus, 'reset', this.addAll);
               this.listenTo(Menus, 'all', this.render);
               //Menus.fetch({ data: $.param({ page: 1,customtag:'nopagination' }) });
               Menus.fetch({success:function(){
                  //to-be-remove
                  //console.log('加载完成');
                  $('#to-be-remove').remove();
               }});

             },
             render: function() {

             },
             addOne: function(todo) {
               var view = new MenuView({model: todo});
               var vel=view.render().el;
               llm=this;
               this.$el.append(vel);
             },
             addOneView: function(e) {
               var m=new Menu({});
               var view = new MenuView({model:m});
               var vel=view.render().el;
               this.$("div[opt=list]").append(vel);
               $.parser.parse(vel);
             },
             addAll: function() {
               Menus.each(this.addOne, this);
             }

           });

           // Finally, we kick things off by creating the **App**.
           var App = new AppView;
         }}
       })
