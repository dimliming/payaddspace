'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;        
      }
    ]
  )
  .config(
    [          '$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 
      function ($stateProvider,   $urlRouterProvider, JQ_CONFIG) {
          
          $urlRouterProvider.otherwise('/login');
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: 'tpl/app.html'
              })
              .state('app.dashboard-v1', {
                  url: '/dashboard-v1',
                  templateUrl: 'tpl/app_dashboard_v1.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
              .state('app.dashboard-v2', {
                  url: '/dashboard-v2',
                  templateUrl: 'tpl/app_dashboard_v2.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
                        return $ocLazyLoad.load(['js/controllers/chart.js']);
                    }]
                  }
              })
              .state('app.ui', {
                  url: '/ui',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.ui.buttons', {
                  url: '/buttons',
                  templateUrl: 'tpl/ui_buttons.html'
              })
              .state('app.ui.icons', {
                  url: '/icons',
                  templateUrl: 'tpl/ui_icons.html'
              })
              .state('app.ui.grid', {
                  url: '/grid',
                  templateUrl: 'tpl/ui_grid.html'
              })
              .state('app.ui.widgets', {
                  url: '/widgets',
                  templateUrl: 'tpl/ui_widgets.html'
              })          
              .state('app.ui.bootstrap', {
                  url: '/bootstrap',
                  templateUrl: 'tpl/ui_bootstrap.html'
              })
              .state('app.ui.sortable', {
                  url: '/sortable',
                  templateUrl: 'tpl/ui_sortable.html'
              })
              .state('app.ui.scroll', {
                  url: '/scroll',
                  templateUrl: 'tpl/ui_scroll.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/scroll.js');
                      }]
                  }
              })
              .state('app.ui.portlet', {
                  url: '/portlet',
                  templateUrl: 'tpl/ui_portlet.html'
              })
              .state('app.ui.timeline', {
                  url: '/timeline',
                  templateUrl: 'tpl/ui_timeline.html'
              })
              .state('app.ui.tree', {
                  url: '/tree',
                  templateUrl: 'tpl/ui_tree.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('angularBootstrapNavTree').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/tree.js');
                              }
                          );
                        }
                      ]
                  }
              })
              .state('app.ui.toaster', {
                  url: '/toaster',
                  templateUrl: 'tpl/ui_toaster.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('toaster').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/toaster.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.ui.jvectormap', {
                  url: '/jvectormap',
                  templateUrl: 'tpl/ui_jvectormap.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('js/controllers/vectormap.js');
                      }]
                  }
              })
              .state('app.ui.googlemap', {
                  url: '/googlemap',
                  templateUrl: 'tpl/ui_googlemap.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( [
                            'js/app/map/load-google-maps.js',
                            'js/app/map/ui-map.js',
                            'js/app/map/map.js'] ).then(
                              function(){
                                return loadGoogleMaps(); 
                              }
                            );
                      }]
                  }
              })
              .state('app.chart', {
                  url: '/chart',
                  templateUrl: 'tpl/ui_chart.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/chart.js');
                      }]
                  }
              })
              // table
              .state('app.table', {
                  url: '/table',
                  template: '<div ui-view></div>'
              })
              .state('app.table.static', {
                  url: '/static',
                  templateUrl: 'tpl/table_static.html'
              })
              .state('app.table.datatable', {
                  url: '/datatable',
                  templateUrl: 'tpl/table_datatable.html'
              })
              .state('app.table.footable', {
                  url: '/footable',
                  templateUrl: 'tpl/table_footable.html'
              })
              .state('app.table.grid', {
                  url: '/grid',
                  templateUrl: 'tpl/table_grid.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('ngGrid').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/grid.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.table.uigrid', {
                  url: '/uigrid',
                  templateUrl: 'tpl/table_uigrid.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('ui.grid').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/uigrid.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.table.editable', {
                  url: '/editable',
                  templateUrl: 'tpl/table_editable.html',
                  controller: 'XeditableCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('xeditable').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/xeditable.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.table.smart', {
                  url: '/smart',
                  templateUrl: 'tpl/table_smart.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('smart-table').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/table.js');
                              }
                          );
                      }]
                  }
              })
              // form
              .state('app.form', {
                  url: '/form',
                  template: '<div ui-view class="fade-in"></div>',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load('js/controllers/form.js');
                      }]
                  }
              })
              .state('app.form.components', {
                  url: '/components',
                  templateUrl: 'tpl/form_components.html',
                  resolve: {
                      deps: ['uiLoad', '$ocLazyLoad',
                        function( uiLoad, $ocLazyLoad ){
                          return uiLoad.load( JQ_CONFIG.daterangepicker )
                          .then(
                              function(){
                                return uiLoad.load('js/controllers/form.components.js');
                              }
                          ).then(
                              function(){
                                return $ocLazyLoad.load('ngBootstrap');
                              }
                          );
                        }
                      ]
                  }
              })
              .state('app.form.elements', {
                  url: '/elements',
                  templateUrl: 'tpl/form_elements.html'
              })
              .state('app.form.validation', {
                  url: '/validation',
                  templateUrl: 'tpl/form_validation.html'
              })
              .state('app.form.wizard', {
                  url: '/wizard',
                  templateUrl: 'tpl/form_wizard.html'
              })
              .state('app.form.fileupload', {
                  url: '/fileupload',
                  templateUrl: 'tpl/form_fileupload.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('angularFileUpload').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/file-upload.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.imagecrop', {
                  url: '/imagecrop',
                  templateUrl: 'tpl/form_imagecrop.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('ngImgCrop').then(
                              function(){
                                 return $ocLazyLoad.load('js/controllers/imgcrop.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.select', {
                  url: '/select',
                  templateUrl: 'tpl/form_select.html',
                  controller: 'SelectCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('ui.select').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/select.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.slider', {
                  url: '/slider',
                  templateUrl: 'tpl/form_slider.html',
                  controller: 'SliderCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('vr.directives.slider').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/slider.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.editor', {
                  url: '/editor',
                  templateUrl: 'tpl/form_editor.html',
                  controller: 'EditorCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('textAngular').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/editor.js');
                              }
                          );
                      }]
                  }
              })
              .state('app.form.xeditable', {
                  url: '/xeditable',
                  templateUrl: 'tpl/form_xeditable.html',
                  controller: 'XeditableCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('xeditable').then(
                              function(){
                                  return $ocLazyLoad.load('js/controllers/xeditable.js');
                              }
                          );
                      }]
                  }
              })
              // pages
              .state('app.page', {
                  url: '/page',
                  template: '<div ui-view class="fade-in-down"></div>'
              })
              .state('app.page.profile', {
                  url: '/profile',
                  templateUrl: 'tpl/page_profile.html'
              })
              .state('app.page.post', {
                  url: '/post',
                  templateUrl: 'tpl/page_post.html'
              })
              .state('app.page.search', {
                  url: '/search',
                  templateUrl: 'tpl/page_search.html'
              })
              .state('app.page.invoice', {
                  url: '/invoice',
                  templateUrl: 'tpl/page_invoice.html'
              })
              .state('app.page.price', {
                  url: '/price',
                  templateUrl: 'tpl/page_price.html'
              })
              .state('app.docs', {
                  url: '/docs',
                  templateUrl: 'tpl/docs.html'
              })
              // others
              .state('lockme', {
                  url: '/lockme',
                  templateUrl: 'tpl/page_lockme.html'
              })
              .state('access', {
                  url: '/access',
                  template: '<div ui-view class="fade-in-right-big smooth"></div>'
              })
              .state('access.signin', {
                  url: '/signin',
                  templateUrl: 'tpl/page_signin.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signin.js'] );
                      }]
                  }
              })
              .state('access.signup', {
                  url: '/signup',
                  templateUrl: 'tpl/page_signup.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signup.js'] );
                      }]
                  }
              })
              .state('access.forgotpwd', {
                  url: '/forgotpwd',
                  templateUrl: 'tpl/page_forgotpwd.html'
              })
              .state('access.404', {
                  url: '/404',
                  templateUrl: 'tpl/page_404.html'
              })

              // fullCalendar
              .state('app.calendar', {
                  url: '/calendar',
                  templateUrl: 'tpl/app_calendar.html',
                  // use resolve to load other dependences
                  resolve: {
                      deps: ['$ocLazyLoad', 'uiLoad',
                        function( $ocLazyLoad, uiLoad ){
                          return uiLoad.load(
                            JQ_CONFIG.fullcalendar.concat('js/app/calendar/calendar.js')
                          ).then(
                            function(){
                              return $ocLazyLoad.load('ui.calendar');
                            }
                          )
                      }]
                  }
              })

              // mail
              .state('app.mail', {
                  abstract: true,
                  url: '/mail',
                  templateUrl: 'tpl/mail.html',
                  // use resolve to load other dependences
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/mail/mail.js',
                                               'js/app/mail/mail-service.js',
                                               JQ_CONFIG.moment] );
                      }]
                  }
              })
              .state('app.mail.list', {
                  url: '/inbox/{fold}',
                  templateUrl: 'tpl/mail.list.html'
              })
              .state('app.mail.detail', {
                  url: '/{mailId:[0-9]{1,4}}',
                  templateUrl: 'tpl/mail.detail.html'
              })
              .state('app.mail.compose', {
                  url: '/compose',
                  templateUrl: 'tpl/mail.new.html'
              })

              .state('layout', {
                  abstract: true,
                  url: '/layout',
                  templateUrl: 'tpl/layout.html'
              })
              .state('layout.fullwidth', {
                  url: '/fullwidth',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_fullwidth.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_fullwidth.html'
                      }
                  },
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/vectormap.js'] );
                      }]
                  }
              })
              .state('layout.mobile', {
                  url: '/mobile',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_mobile.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_mobile.html'
                      }
                  }
              })
              .state('layout.app', {
                  url: '/app',
                  views: {
                      '': {
                          templateUrl: 'tpl/layout_app.html'
                      },
                      'footer': {
                          templateUrl: 'tpl/layout_footer_fullwidth.html'
                      }
                  },
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/tab.js'] );
                      }]
                  }
              })
              .state('apps', {
                  abstract: true,
                  url: '/apps',
                  templateUrl: 'tpl/layout.html'
              })
              .state('apps.note', {
                  url: '/note',
                  templateUrl: 'tpl/apps_note.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/note/note.js',
                                               JQ_CONFIG.moment] );
                      }]
                  }
              })
              .state('apps.contact', {
                  url: '/contact',
                  templateUrl: 'tpl/apps_contact.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/contact/contact.js'] );
                      }]
                  }
              })
              .state('app.weather', {
                  url: '/weather',
                  templateUrl: 'tpl/apps_weather.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load(
                              {
                                  name: 'angular-skycons',
                                  files: ['js/app/weather/skycons.js',
                                          'js/app/weather/angular-skycons.js',
                                          'js/app/weather/ctrl.js',
                                          JQ_CONFIG.moment ] 
                              }
                          );
                      }]
                  }
              })
              .state('app.todo', {
                  url: '/todo',
                  templateUrl: 'tpl/apps_todo.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/app/todo/todo.js',
                                               JQ_CONFIG.moment] );
                      }]
                  }
              })
              .state('app.todo.list', {
                  url: '/{fold}'
              })
              .state('music', {
                  url: '/music',
                  templateUrl: 'tpl/music.html',
                  controller: 'MusicCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load([
                            'com.2fdevs.videogular', 
                            'com.2fdevs.videogular.plugins.controls', 
                            'com.2fdevs.videogular.plugins.overlayplay',
                            'com.2fdevs.videogular.plugins.poster',
                            'com.2fdevs.videogular.plugins.buffering',
                            'js/app/music/ctrl.js', 
                            'js/app/music/theme.css'
                          ]);
                      }]
                  }
              })
                .state('music.home', {
                    url: '/home',
                    templateUrl: 'tpl/music.home.html'
                })
                .state('music.genres', {
                    url: '/genres',
                    templateUrl: 'tpl/music.genres.html'
                })
                .state('music.detail', {
                    url: '/detail',
                    templateUrl: 'tpl/music.detail.html'
                })
                .state('music.mtv', {
                    url: '/mtv',
                    templateUrl: 'tpl/music.mtv.html'
                })
                .state('music.mtvdetail', {
                    url: '/mtvdetail',
                    templateUrl: 'tpl/music.mtv.detail.html'
                })
                .state('music.playlist', {
                    url: '/playlist/{fold}',
                    templateUrl: 'tpl/music.playlist.html'
                })
                
                //polymer app define
                .state('login', {
                  url: '/login',
                  templateUrl: 'tpl/login.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/mainframe/login.js'] );
                      }]
                  }
              })
              .state('papp', {
                  abstract: true,
                  url: '/papp',
                  templateUrl: 'tpl/mainframe/papp.html'
              })
              .state('papp.index', {
                  url: '/index',
                  templateUrl: 'tpl/mainframe/index.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/mainframe/index.js');
                      }]
                  }
              })
              //基础数据管理
              .state('papp.bdm', {
                  url: '/bdm',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('papp.bdm.merchant', {
                  url: '/merchant',
                  template: '<div ui-view class="fade-in-up" ng-controller="MerchantCtrl"></div>'
              })
              .state('papp.bdm.merchant.list', {
                  url: '/list',
                  templateUrl: 'tpl/bdm/merchant-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                    	  
                          return uiLoad.load('js/controllers/bdm/merchant.js');
                      }]
                  }
              })
              .state('papp.bdm.merchant.edit', {
                  url: '/edit',
                  templateUrl: 'tpl/bdm/merchant-edit.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                    	 
                          return uiLoad.load('js/controllers/bdm/merchant.js');
                      }]
                  }
              })
              .state('papp.bdm.securitycfg', {
                  url: '/securitycfg',
                  template: "<div ui-view class='fade-in-up' ng-controller='MerchantSecCtrl'></div>",  
              })
             .state('papp.bdm.securitycfg.configlist', {
                  url: '/configlist',
                  templateUrl: 'tpl/bdm/merchant-security-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/merchant-security.js');
                      }]
                  }
              })
               .state('papp.bdm.securitycfg.securityedit', {
                  url: '/configlist',
                  templateUrl: 'tpl/bdm/merchant-security-edit.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/merchant-security.js');
                      }]
                  }
              })
              //账户管理
              .state('papp.account', {
                  url: '/account',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('papp.account.recharge', {
                  url: '/recharge',
                  template: "<div ui-view class='fade-in-up' ng-controller='RechargeCtrl'></div>",            
              })
               .state('papp.account.recharge.edit', {
                  url: '/edit',
                  templateUrl: 'tpl/bdm/recharge-edit.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){ 
                          return uiLoad.load('js/controllers/bdm/recharge.js');
                      }]
                  }
              })
               .state('papp.account.commonfee', {
                  url: '/recharge',
                  template: "<div ui-view class='fade-in-up' ng-controller='CommonFeeCtrl'></div>",            
              })
                 .state('papp.account.commonfee.edit', {
                  url: '/edit',
                  templateUrl: 'tpl/bdm/common-fee-edit.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){ 
                          return uiLoad.load('js/controllers/bdm/common-fee.js');
                      }]
                  }
              })
                .state('papp.account.commonfee.enquiry', {
                  url: '/enquiry',
                  templateUrl: 'tpl/bdm/common-fee-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/common-fee.js');
                      }]
                  }
              })
              .state('papp.account.recharge.enquiry', {
                  url: '/enquiry',
                  templateUrl: 'tpl/bdm/recharge-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/recharge.js');
                      }]
                  }
              })
               .state('papp.account.detail', {
                  url: '/recharge',
                  template: "<div ui-view class='fade-in-up' ng-controller='AccountDetailCtrl'></div>",            
              })
              .state('papp.account.detail.list', {
                  url: '/accdetail',
                  templateUrl: 'tpl/bdm/accountDetail-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/accountDetail.js');
                      }]
                  }
              })
              .state('papp.account.balance', {
                  url: '/balance',
                  template: "<div ui-view class='fade-in-up' ng-controller='BalanceCtrl'></div>", 
              })
               .state('papp.account.balance.list', {
                  url: '/accdetail',
                  templateUrl: 'tpl/bdm/balance-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/balance.js');
                      }]
                  }
              })
              //交易管理
              .state('papp.trade', {
                  url: '/trade',
                  template: '<div ui-view class="fade-in-up" ng-controller="TradeCtrl"></div>'
              })
               .state('papp.trade.edit', {
                  url: '/edit',
                  templateUrl: 'tpl/bdm/trade-detail.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                    	 
                          return uiLoad.load('js/controllers/bdm/trade.js');
                      }]
                  }
              })
              .state('papp.trade.enquiry', {
                  url: '/enquiry',
                  templateUrl: 'tpl/bdm/trade-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/trade.js');
                      }]
                  }
              })
              .state('papp.merchantmsg', {
                  url: '/merchantmsg',
                  template: '<div ui-view class="fade-in-up" ng-controller="MerchantMsgCtrl"></div>'
              })
               .state('papp.merchantmsg.enquiry', {
                  url: '/enquiry',
                  templateUrl: 'tpl/bdm/merchantmsg-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/merchantMsg.js');
                      }]
                  }
              })
               .state('papp.merchantmsg.edit', {
                  url: '/edit',
                  templateUrl: 'tpl/bdm/merchantmsg-edit.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){	 
                          return uiLoad.load('js/controllers/bdm/merchantMsg.js');
                      }]
                  }
              })
              .state('papp.channelmsg', {
                  url: '/channelmsg',
                  template: '<div ui-view class="fade-in-up" ng-controller="ChannelMsgCtrl"></div>'
              })
               .state('papp.channelmsg.enquiry', {
                  url: '/enquiry',
                  templateUrl: 'tpl/bdm/channelmsg-list.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){
                          return uiLoad.load('js/controllers/bdm/channelMsg.js');
                      }]
                  }
              })
               .state('papp.channelmsg.edit', {
                  url: '/edit',
                  templateUrl: 'tpl/bdm/channelmsg-edit.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad){	 
                          return uiLoad.load('js/controllers/bdm/channelMsg.js');
                      }]
                  }
              })
              .state('papp.product',{
            	  url:'/product',
            	  template:'<div ui-view class="fade-in-up" ng-controller="ProductCtrl"></div>',
              })
              .state('papp.product.enquiry',{
            	  url:'/enquiry',
            	  templateUrl:'tpl/com/product-list.html',
            	  resolve:{
            		  deps: ['uiLoad',
                             function( uiLoad){
                               return uiLoad.load('js/controllers/com/product.js');
                           }]
            	  }
              })
              .state('papp.product.edit',{
            	  url:'/edit',
            	  templateUrl:'tpl/com/product-edit.html',
            	  resolve:{
            		  deps: ['uiLoad',
                             function( uiLoad){
                               return uiLoad.load('js/controllers/com/product.js');
                           }]
            	  }
              })
               .state('papp.productRelation',{
            	  url:'/product',
            	  template:'<div ui-view class="fade-in-up" ng-controller="ProductRelationCtrl"></div>',
              })
              .state('papp.productRelation.enquiry',{
            	  url:'/enquiry',
            	  templateUrl:'tpl/bdm/product-relation-list.html',
            	  resolve:{
            		  deps: ['uiLoad',
                             function( uiLoad){
                               return uiLoad.load('js/controllers/bdm/productRelation.js');
                           }]
            	  }
              })
              .state('papp.productRelation.edit',{
            	  url:'/edit',
            	  templateUrl:'tpl/bdm/product-relation-edit.html',
            	  resolve:{
            		  deps: ['uiLoad',
                             function( uiLoad){
                               return uiLoad.load('js/controllers/bdm/productRelation.js');
                           }]
            	  }
              })
              .state('papp.docs', {
                  url: '/docs',
                  templateUrl: 'tpl/docs.html'
              })
              
      }
    ]
  );
