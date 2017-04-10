(function(){
	"use strict";
	angular.module("app.topnav")
	.service("LoginLogService",LoginLogService);
	LoginLogService.$inject = ['$rootScope', '$http', 'HttpServiceUtil'];
	
	function LoginLogService($rootScope, $http, HttpServiceUtil) {
		
		this.getLastLoginLog=function(onReady,onError){
			var action="getlastLog";
			var url=HttpServiceUtil.getSysAction(action);
			onError = onError || function() { alert('Failure loading menu'); };
			 $http
            .get(url)
            .success(onReady)
            .error(onError);
		}
	}
})();