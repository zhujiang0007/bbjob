/**=========================================================
 * Module: config.js
 * App routes and resources configuration
 =========================================================*/


(function() {
    'use strict';

    angular
        .module('app.routes')
        .config(routesConfig);

    routesConfig.$inject = ['$stateProvider', '$locationProvider', '$urlRouterProvider', 'RouteHelpersProvider'];
    function routesConfig($stateProvider, $locationProvider, $urlRouterProvider, helper){
        
        // Set the following to true to enable the HTML5 Mode
        // You may have to set <base> tag in index and a routing configuration in your server
        $locationProvider.html5Mode(false);

        // defaults to dashboard
        $urlRouterProvider.otherwise('/app/echarts');

        // 
        // Application Routes
        // -----------------------------------   
        $stateProvider
          .state('app', {
              url: '/app',
              abstract: true,
              templateUrl: helper.basicpath('app.html'),
              resolve: helper.resolveFor('modernizr', 'icons',
									'toaster', 'loaders.css', 'app.css',
									'spinkit', 'animo', 'sparklines',
									'classyloader', 'whirl', 'ngDialog')
          })
          .state('app.echarts', {
              url: '/echarts',
              title: 'echarts 展示',
              controller: 'echartsCtrl',
              controllerAs: 'echarts',
              templateUrl: helper.basepath('menu1/echarts/echarts.html'),
              resolve: helper.resolveFor('echarts.controller', 'echarts', 'oitozero.ngSweetAlert', 'toaster')
          })
          .state('app.ngTable', {
              url: '/ngTable',
              title: 'ngTable 展示',
              controller: 'ngTableCtrl',
              controllerAs: 'ngTable',
              templateUrl: helper.basepath('menu1/ngTable/ngTable.html'),
              resolve: helper.resolveFor('ngTable.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'mao.select', 'paneltool')
          })
          // 
          // CUSTOM RESOLVES
          //   Add your own resolves properties
          //   following this object extend
          //   method
          // ----------------------------------- 
          // .state('app.someroute', {
          //   url: '/some_url',
          //   templateUrl: 'path_to_template.html',
          //   controller: 'someController',
          //   resolve: angular.extend(
          //     helper.resolveFor(), {
          //     // YOUR RESOLVES GO HERE
          //     }
          //   )
          // })
          ;

    } // routesConfig

})();

