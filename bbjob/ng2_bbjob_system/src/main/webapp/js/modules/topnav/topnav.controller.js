(function() {
	"use strict";
	angular.module("app.topnav").controller("TopNavController",
			TopNavController);
	TopNavController.$inject = [ '$state', '$rootScope', '$scope',
			'MenuLoader', '$http','toaster', 'ngDialog', '$location' ];

	function TopNavController($state, $rootScope, $scope, MenuService, $http,toaster,
			ngDialog,$location) {
		$scope.menuItems = [];
		$scope.noticeItems = [];
		$scope.noticeCount = 0;
		$scope.temp = {};
		//获取顶层导航
		MenuService.getMenus().then(onItemsReady,onItemsError);
		
		//
		var absurl = $location.absUrl();  
		var appUrl = absurl.substr(absurl.indexOf('#')+2);
		var APP = appUrl.substring(0,appUrl.indexOf('/')),
			URL = appUrl.substr(appUrl.indexOf('/')+1);
		if(URL.indexOf('/')!=-1){
			URL = URL.substring(0, URL.indexOf('/'));
		}
				
		var menuUrl =APP + '.' + URL;
		
		// MenuService.getNotice(getNoticesOnSuccess)
		// MenuService.getNoticeCount(getNoticesCountOnSuccess)
		$scope.isActive = isActive;
		$scope.menuClick = menuClick;
		function onItemsReady(data) {
				$scope.menuItems = data;
				init();
		}
		function onItemsError(data) {
			
		}
		function isActive(index) {
			return $rootScope.server.curRootMenu === $scope.menuItems[index];
		}
		function menuClick(index) {
			$rootScope.server.curRootMenu = $scope.menuItems[index];
			$rootScope.app.layout.isCollapsed = false;
			// if ($scope.menuItems[index].url != null &&
			// $scope.menuItems[index].url != '')
			// {
			// $state.go($scope.menuItems[index].url);
			// }
		}

		$scope.changepassword = function() {
			ngDialog
					.open({
						width : '40%',
						className : 'ngdialog-theme-default',
						template : '<div ng-include="\'jsp/views/partials/change_pass.html\'"> </div>',
						plain : true,
						scope : $scope,
						preCloseCallback : function() {
							$scope.temp = {};
						}
					});
		}

		$scope.addSave = function() {
			$http({
				method : 'POST',
				url : 'manage/user/changepassword.json',
				data : $.param({
					oldpass : $scope.temp.oldpass,
					newpass : $scope.temp.newpass
				}),
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				}

			}).success(function(data, status, headers, config) {
				var obj2 = eval(data);
				if (obj2.statusCode == 200) {
					toaster.pop('success', "提示", obj2.message);
					$scope.temp = {};
					$scope.close();
				} else {
					toaster.pop('error', "提示", obj2.message);
				}
			}).error(function(data, status, headers, config) {
				toaster.pop('error', "提示", "服务器繁忙！");
			});
		}

		// 提交
		$scope.close = function() {
			$scope.temp = {};
			ngDialog.close();
		};

		// 通知 byBHF 点击弹窗显示消息内容，并计入已读
		$scope.noticeDetail = function(index) {
			$scope.info = $scope.noticeItems[index];
			ngDialog
					.open({
						className : 'ngdialog-theme-default',
						showClose : true,
						template : '<div ng-include="\'jsp/views/userinfo/mynotice_detail.html\'"></div>',
						plain : true,
						width : '40%',
						scope : $scope
					});
			var data = {
				id : $scope.noticeItems[index].id
			}
			MenuService.noticeRead(data, noticeReadOnSuccess)
		}

		$scope.goNoticeList = function(index) {
			$state.go('app.mynotice');
		}

		function noticeReadOnSuccess(data, status, headers, config) {
			if (data.statusCode = "200") {
				MenuService.getNotice(getNoticesOnSuccess)
				MenuService.getNoticeCount(getNoticesCountOnSuccess)
			}
		}

		function getNoticesOnSuccess(data, status, headers, config) {
			if (data.statusCode = "200") {
				$scope.noticeItems = data.data;
				// $scope.noticeItems = $scope.noticeItems.slice(0,3);
			}
		}

		function getNoticesCountOnSuccess(data, status, headers, config) {
			if (data.statusCode = "200") {
				$scope.noticeCount = data.data;
			}
		}

		
		/* 跳转菜单 */
		function init() {
			var url = 'sys/getCurrentMenu.json?menuName=' + menuUrl;
			$http.get(url).success(function(data,status,headers,config){
				if(data.statusCode=='200'){
					if(data.data.parentSortNo){
						$rootScope.menuIndex = data.data;
						menuClick($rootScope.menuIndex.parentSortNo);
					}else{
						menuClick(0);
					}
				}else{
					menuClick(0);
				}
			}).error(function(data,status,headers,config){
				menuClick(0);
			})
		}

		$scope.$on("noticeReadToChild", function(event, msg) {
			// MenuService.getNotice(getNoticesOnSuccess)
			// MenuService.getNoticeCount(getNoticesCountOnSuccess)
		});
	}
})();