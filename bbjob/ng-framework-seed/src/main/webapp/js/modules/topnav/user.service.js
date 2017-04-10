(function(){
	"use strict";
	angular.module("app.topnav")
	.service("UserService",UserService);
	UserService.$inject = ['$rootScope', '$http', 'HttpServiceUtil'];
	
	function UserService($rootScope, $http, HttpServiceUtil) {
		this.getCurUser=function(onReady,onError){
			var action="getCurUser";
			var url=HttpServiceUtil.getSysAction(action);
			onError = onError || function() { alert('Failure loading menu'); };
			 $http
            .get(url)
            .success(onReady)
            .error(onError);
		}
		this.getFriends=function(userId,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			 $http
	        .get(HttpServiceUtil.friendsUrl+"?userId="+userId)
	        .success(onReady)
	        .error(onError);
		}
		this.getCircle=function(userId,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			 $http
	        .get(HttpServiceUtil.circleUrl+"?userId="+userId)
	        .success(onReady)
	        .error(onError);
		}
		this.getCircleUser=function(circleId,userId,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			 $http
	        .get(HttpServiceUtil.circleUserUrl+"?userId="+userId+"&circleId="+circleId)
	        .success(onReady)
	        .error(onError);
		}
		this.getUserInfo=function(userId,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			$http
	        .get(HttpServiceUtil.userInfoUrl+"?userId="+userId)
	        .success(onReady)
	        .error(onError);
		}
		this.saveUserInfo=function(data,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			$http({method : 'POST', url : HttpServiceUtil.userInfoReplaceUrl, data : data })
	        .success(onReady)
	        .error(onError);
		}
		this.saveAccountInfo=function(data,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			$http({method : 'POST', url : HttpServiceUtil.accountEditUrl, data : data })
	        .success(onReady)
	        .error(onError);
		}
		this.modifyPassword=function(data,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			$http({method : 'POST', url : HttpServiceUtil.modifyPasswordUrl, data : data })
	        .success(onReady)
	        .error(onError);
		}
		this.getAccountInfo=function(userId,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			$http
	        .get(HttpServiceUtil.accountGetUrl+"?userId="+userId)
	        .success(onReady)
	        .error(onError);
		}
		this.updateImage=function(url,userId,onReady,onError){
			onError = onError || function() { alert('Failure loading menu'); };
			$http
			.get(HttpServiceUtil.userheadImgUpdateUrl+"?userId="+userId+"&url="+url)
	        .success(onReady)
	        .error(onError);
		}
	}
})();