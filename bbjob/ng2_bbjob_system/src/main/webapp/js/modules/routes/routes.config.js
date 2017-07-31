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
        $urlRouterProvider.otherwise('/app/usercheck');

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
          //用户
          .state('app.sysuser', {
              url: '/sysuser',
              title: '用户管理',
              controller: 'sysuserCtrl',
              controllerAs: 'sysuser',
              templateUrl: helper.basepath('menu1/user/sysuser.html'),
              resolve: helper.resolveFor('sysuser.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'mao.select', 
            		  					 'table.nothing')
          })
          .state('app.userDetail', {
              url: '/userDetail/:userId',
              title: '用户详情',
              controller: 'userDetailCtrl',
              controllerAs: 'userDetail',
              templateUrl: helper.basepath('menu1/user/user_detail.html'),
              resolve: helper.resolveFor('userDetail.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 
            		  					 'paneltool', 'treeControl', 'text.over')
          })
          //角色
          .state('app.sysrole', {
              url: '/sysrole',
              title: '角色管理',
              controller: 'sysroleCtrl',
              controllerAs: 'sysrole',
              templateUrl: helper.basepath('menu1/role/sysrole.html'),
              resolve: helper.resolveFor('sysrole.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing')
          })
          .state('app.sysroleDetail', {
              url: '/sysroleDetail/:roleId',
              title: '角色详情',
              controller: 'sysroleDetailCtrl',
              controllerAs: 'sysroleDetail',
              templateUrl: helper.basepath('menu1/role/sysrole_detail.html'),
              resolve: helper.resolveFor('sysroleDetail.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing', 'paneltool')
          })
          //权限
          .state('app.syspurview', {
              url: '/syspurview',
              title: '权限管理 ',
              controller: 'syspurviewCtrl',
              controllerAs: 'syspurview',
              templateUrl: helper.basepath('menu1/purview/purview.html'),
              resolve: helper.resolveFor('syspurview.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing','html.sortable')
          })
          .state('app.syspurviewDetail', {
              url: '/syspurviewDetail/:pruviewId',
              title: '权限详情',
              controller: 'syspurviewDetailCtrl',
              controllerAs: 'syspurviewDetail',
              templateUrl: helper.basepath('menu1/role/syspurview_detail.html'),
              resolve: helper.resolveFor('syspurviewDetail.controller', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 'table.nothing')
          })
          .state('app.check', {
              url: '/usercheck',
              title: '参赛人员审核',
              controller: 'userCheckCtrl',
              controllerAs: 'uCtrl',
              templateUrl: helper.basepath('business/usercheck/userCheckManagement.html'),
              resolve: helper.resolveFor('userCheckManagement', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 
            		  					 'mao.select', 'paneltool', 'uiTree')
          })
           .state('app.announcement', {
              url: '/announcement',
              title: '通知公告',
              controller: 'announcementCtrl',
              controllerAs: 'aCtrl',
              templateUrl: helper.basepath('business/announcement/announcementManagement.html'),
              resolve: helper.resolveFor('announcementManagement', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 
            		  					 'mao.select', 'paneltool', 'uiTree')
           })
            .state('app.addannouncement', {
              url: '/addannouncement/:id',
              title: '新增公告',
              controller: 'addannouncementCtrl',
              controllerAs: 'adCtrl',
              templateUrl: helper.basepath('business/announcement/ann_add.html'),
              resolve: helper.resolveFor('addannouncement', 'ngTable', 'oitozero.ngSweetAlert', 'toaster', 
            		  					 'mao.select', 'paneltool', 'uiTree')
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

