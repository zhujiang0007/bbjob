(function(){
	"use strict";
	angular.module("app.topnav")
	.service("MenuLoader",MenuLoader);
	MenuLoader.$inject = ['$rootScope', '$http', 'HttpServiceUtil'];
	
	function MenuLoader($rootScope, $http, HttpServiceUtil) {
		this.getRootMenu = getRootMenu;
		this.getMenu=getMenu;
		this.getNotice=getNotice;
		this.getNoticeCount=getNoticeCount;
		this.noticeRead=noticeRead;
		function getRootMenu(onReady,onError){
			var action="getRootMenu";
			var url=HttpServiceUtil.getSysAction(action);
			onError = onError || function() { alert('Failure loading menu'); };
			 $http
            .get(url)
            .success(onReady)
            .error(onError);
		}
		function getMenu(pid,onReady,onError){
			var action="getMenu";
			var url=HttpServiceUtil.getSysAction(action)+"?pid="+pid;
			onError = onError || function() { alert('Failure loading menu'); };
			 $http
            .get(url)
            .success(onReady)
            .error(onError);
		}
		function getNotice(onReady,onError){
//			var action="getRootMenu";
			var url= 'manage/memberInfo/userNotice.json';
			onError = onError || function() { };
			 $http
            .get(url)
            .success(onReady)
            .error(onError);
		}
		function getNoticeCount(onReady,onError){
			var url= 'manage/memberInfo/userNoticeCount.json';
			onError = onError || function() {};
			 $http
            .get(url)
            .success(onReady)
            .error(onError);
			 
		}
		function noticeRead(data,onReady,onError){
			var url= 'manage/memberInfo/readNotice.json?';
			onError = onError || function() {};
			 $http({
					method : "POST",
					url : url,
					data : data
				}).success(onReady).error(onError);
		}
	}
})();