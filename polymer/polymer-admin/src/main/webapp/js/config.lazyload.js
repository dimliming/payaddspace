// lazyload config

angular.module('app')
    /**
   * jQuery plugin config use ui-jq directive , config the js and css files that required
   * key: function name of the jQuery plugin
   * value: array of the css js file located
   */
  .constant('JQ_CONFIG', {
      easyPieChart:   [   'js/lib/jquery.easy-pie-chart/dist/jquery.easypiechart.fill.js'],
      sparkline:      [   'js/lib/jquery.sparkline/dist/jquery.sparkline.retina.js'],
      plot:           [   'js/lib/flot/jquery.flot.js',
                          'js/lib/flot/jquery.flot.pie.js', 
                          'js/lib/flot/jquery.flot.resize.js',
                          'js/lib/flot.tooltip/js/jquery.flot.tooltip.js',
                          'js/lib/flot.orderbars/js/jquery.flot.orderBars.js',
                          'js/lib/flot-spline/js/jquery.flot.spline.js'],
      moment:         [   'js/lib/moment/moment.js'],
      screenfull:     [   'js/lib/screenfull/dist/screenfull.min.js'],
      slimScroll:     [   'js/lib/slimscroll/jquery.slimscroll.min.js'],
      sortable:       [   'js/lib/html5sortable/jquery.sortable.js'],
      nestable:       [   'js/lib/nestable/jquery.nestable.js',
                          'js/lib/nestable/jquery.nestable.css'],
      filestyle:      [   'js/lib/bootstrap-filestyle/src/bootstrap-filestyle.js'],
      slider:         [   'js/lib/bootstrap-slider/bootstrap-slider.js',
                          'js/lib/bootstrap-slider/bootstrap-slider.css'],
      chosen:         [   'js/lib/chosen/chosen.jquery.min.js',
                          'js/lib/bootstrap-chosen/bootstrap-chosen.css'],
      TouchSpin:      [   'js/lib/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.js',
                          'js/lib/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.css'],
      wysiwyg:        [   'js/lib/bootstrap-wysiwyg/bootstrap-wysiwyg.js',
                          'js/lib/bootstrap-wysiwyg/external/jquery.hotkeys.js'],
      dataTable:      [   'js/lib/datatables/media/js/jquery.dataTables.min.js',
                          'js/lib/plugins/integration/bootstrap/3/dataTables.bootstrap.js',
                          'js/lib/plugins/integration/bootstrap/3/dataTables.bootstrap.css'],
      vectorMap:      [   'js/lib/bower-jvectormap/jquery-jvectormap-1.2.2.min.js', 
                          'js/lib/bower-jvectormap/jquery-jvectormap-world-mill-en.js',
                          'js/lib/bower-jvectormap/jquery-jvectormap-us-aea-en.js',
                          'js/lib/bower-jvectormap/jquery-jvectormap-1.2.2.css'],
      footable:       [   'js/lib/footable/dist/footable.all.min.js',
                          'js/lib/footable/css/footable.core.css'],
      fullcalendar:   [   'js/lib/moment/moment.js',
                          'js/lib/fullcalendar/dist/fullcalendar.min.js',
                          'js/lib/fullcalendar/dist/fullcalendar.css',
                          'js/lib/fullcalendar/dist/fullcalendar.theme.css'],
      daterangepicker:[   'js/lib/moment/moment.js',
                          'js/lib/bootstrap-daterangepicker/daterangepicker.js',
                          'js/lib/bootstrap-daterangepicker/daterangepicker-bs3.css'],
      tagsinput:      [   'js/lib/bootstrap-tagsinput/dist/bootstrap-tagsinput.js',
                          'js/lib/bootstrap-tagsinput/dist/bootstrap-tagsinput.css']
                      
    }
  )
  // oclazyload config
  .config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
      // We configure ocLazyLoad to use the lib script.js as the async loader
      $ocLazyLoadProvider.config({
          debug:  true,
          events: true,
          modules: [
              {
                  name: 'ngGrid',
                  files: [
                      'js/lib/ng-grid/build/ng-grid.min.js',
                      'js/lib/ng-grid/ng-grid.min.css',
                      'js/lib/ng-grid/ng-grid.bootstrap.css'
                  ]
              },
              {
                  name: 'ui.grid',
                  files: [
                      'js/lib/angular-ui-grid/ui-grid.min.js',
                      'js/lib/angular-ui-grid/ui-grid.min.css',
                      'js/lib/angular-ui-grid/ui-grid.bootstrap.css'
                  ]
              },
              {
                  name: 'ui.select',
                  files: [
                      'js/lib/angular-ui-select/dist/select.min.js',
                      'js/lib/angular-ui-select/dist/select.min.css'
                  ]
              },
              {
                  name:'angularFileUpload',
                  files: [
                    'js/lib/angular-file-upload/angular-file-upload.min.js'
                  ]
              },
              {
                  name:'ui.calendar',
                  files: ['js/lib/angular-ui-calendar/src/calendar.js']
              },
              {
                  name: 'ngImgCrop',
                  files: [
                      'js/lib/ngImgCrop/compile/minified/ng-img-crop.js',
                      'js/lib/ngImgCrop/compile/minified/ng-img-crop.css'
                  ]
              },
              {
                  name: 'angularBootstrapNavTree',
                  files: [
                      'js/lib/angular-bootstrap-nav-tree/dist/abn_tree_directive.js',
                      'js/lib/angular-bootstrap-nav-tree/dist/abn_tree.css'
                  ]
              },
              {
                  name: 'toaster',
                  files: [
                      'js/lib/angularjs-toaster/toaster.js',
                      'js/lib/angularjs-toaster/toaster.css'
                  ]
              },
              {
                  name: 'textAngular',
                  files: [
                      'js/lib/textAngular/dist/textAngular-sanitize.min.js',
                      'js/lib/textAngular/dist/textAngular.min.js'
                  ]
              },
              {
                  name: 'vr.directives.slider',
                  files: [
                      'js/lib/venturocket-angular-slider/build/angular-slider.min.js',
                      'js/lib/venturocket-angular-slider/build/angular-slider.css'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular',
                  files: [
                      'js/lib/videogular/videogular.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.controls',
                  files: [
                      'js/lib/videogular-controls/controls.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.buffering',
                  files: [
                      'js/lib/videogular-buffering/buffering.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.overlayplay',
                  files: [
                      'js/lib/videogular-overlay-play/overlay-play.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.poster',
                  files: [
                      'js/lib/videogular-poster/poster.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.imaads',
                  files: [
                      'js/lib/videogular-ima-ads/ima-ads.min.js'
                  ]
              },
              {
                  name: 'xeditable',
                  files: [
                      'js/lib/angular-xeditable/dist/js/xeditable.min.js',
                      'js/lib/angular-xeditable/dist/css/xeditable.css'
                  ]
              },
              {
                  name: 'smart-table',
                  files: [
                      'js/lib/angular-smart-table/dist/smart-table.min.js'
                  ]
              }
          ]
      });
  }])
;
