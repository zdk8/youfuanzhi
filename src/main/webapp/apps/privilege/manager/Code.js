define(['text!manager/CodeTpl.htm','BzModel','underscore'],
function(htm,BzModel,_){

  return {render:function(local,cb){

    var aaa100=cb.params.record.aaa100;
    var Combodtinfo = BzModel.extend({
    urlRoot : 'eers/xt_combodt',
     idAttribute: "aaz093",
    defaults: function() {
      return {
        aaz093:null,
        aaa100:null,
        aaa102:null,
        aaa103:null,
        aaa105:null,
        aae100:null,
        aaa104:null,
        oldid:null,
        title: "emptytodo...",
        order: Combodtinfos.nextOrder(),
        done: false
      };
    },
    validate: function(attrs, options) {

  },
    toggle: function() {
      this.save({done: !this.get("done")});
    }
  });

  var CombodtinfoList = Backbone.Collection.extend({
    url:'eers/xt_combodt',
    model: Combodtinfo,
    //localStorage: new Backbone.LocalStorage("combodtinfo-backbone"),
    done: function() {
      return this.where({done: true});
    },
    remaining: function() {
      return this.where({done: false});
    },
    nextOrder: function() {
      if (!this.length) return 1;
      return this.last().get('order') + 1;
    },
    comparator: 'order'

  });
    var Combodtinfos = new CombodtinfoList;

  var CombodtinfoView = Backbone.View.extend({
    template: _.template(htm),
    events: {
      "click a[action=destroy]" : "destroy",
      "click a[action=save]"    : "save"
    },
   initialize: function() {
      this.listenTo(this.model, 'change', this.render);
      this.listenTo(this.model, 'destroy', this.remove);
    },
    // Re-render the name and tel of the combodtinfo item.
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));
      this.$el.toggleClass('done', this.model.get('done'));
      return this;
    },
    destroy: function() {
      this.model.destroy();
    },
    save: function() {
       var o={};
       var os=this.$('form').serializeArray();
       var me=this;
       _.each(os,function(item){
          if(item.value)
           o[item.name]=item.value;
       })
      $.extend(o,{aaa100:aaa100});
       this.model.set(o);
       this.model.save();
    }

  });

  var AppView = Backbone.View.extend({
    el: local.find("div[opt=main]"),
    events: {
      "click div[opt=add]":  "addOneView"
    },
    initialize: function() {
      this.listenTo(Combodtinfos, 'add', this.addOne);
      this.listenTo(Combodtinfos, 'reset', this.addAll);
      this.listenTo(Combodtinfos, 'all', this.render);
      if(aaa100){
        Combodtinfos.fetch({ data: $.param({ page: 1,customtag:'nopagination',
                      intelligentsearch:[{name:'aaa100',operate:'=',value:aaa100}]}) });
      }

    },

    // Re-rendering the App just means refreshing the statistics -- the rest
    // of the app doesn't change.
    render: function() {

    },
    addOne: function(todo) {
      var view = new CombodtinfoView({model: todo});
      var vel=view.render().el;
      this.$("div[opt=list]").append(vel);
      $.parser.parse(vel);
    },
    addOneView: function(e) {
      var m=new Combodtinfo({});
          m.on("invalid", function(model, error) {
      alert(" " + error);
    });

      var view = new CombodtinfoView({model:m});
      var vel=view.render().el;
      this.$("div[opt=list]").append(vel);
      $.parser.parse(vel);
    },
    addAll: function() {
      Combodtinfos.each(this.addOne, this);
    }

  });

  // Finally, we kick things off by creating the **App**.
  var App = new AppView;
  }}
})
