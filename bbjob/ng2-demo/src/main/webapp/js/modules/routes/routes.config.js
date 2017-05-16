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
        $urlRouterProvider.otherwise('/app/sysuser');

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
         
          //用户
          .state('app.sysuser', {
              url: '/sysuser',
              title: '用户管理',
              controller: 'sysuserCtrl',
              controllerAs: 'sysuser',
              templateUrl: helper.basepath('sys/user/sysuser.html'),
              resolve: helper.resolveFor('sysuser.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'mao.select', 
            		  					 'table.nothing')
          })
          .state('app.userDetail', {
              url: '/userDetail/:userId',
              title: '用户详情',
              controller: 'userDetailCtrl',
              controllerAs: 'userDetail',
              templateUrl: helper.basepath('sys/user/user_detail.html'),
              resolve: helper.resolveFor('userDetail.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 
            		  					 'paneltool', 'treeControl', 'text.over')
          })
          //角色
          .state('app.sysrole', {
              url: '/sysrole',
              title: '角色管理',
              controller: 'sysroleCtrl',
              controllerAs: 'sysrole',
              templateUrl: helper.basepath('sys/role/sysrole.html'),
              resolve: helper.resolveFor('sysrole.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing')
          })
          .state('app.sysroleDetail', {
              url: '/sysroleDetail/:roleId',
              title: '角色详情',
              controller: 'sysroleDetailCtrl',
              controllerAs: 'sysroleDetail',
              templateUrl: helper.basepath('sys/role/sysrole_detail.html'),
              resolve: helper.resolveFor('sysroleDetail.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing', 'paneltool')
          })
          //权限
          .state('app.syspurview', {
              url: '/syspurview',
              title: '权限管理 ',
              controller: 'syspurviewCtrl',
              controllerAs: 'syspurview',
              templateUrl: helper.basepath('sys/purview/purview.html'),
              resolve: helper.resolveFor('syspurview.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing')
          })
          .state('app.syspurviewDetail', {
              url: '/syspurviewDetail/:pruviewId',
              title: '权限详情',
              controller: 'syspurviewDetailCtrl',
              controllerAs: 'syspurviewDetail',
              templateUrl: helper.basepath('sys/role/syspurview_detail.html'),
              resolve: helper.resolveFor('syspurviewDetail.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing')
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

