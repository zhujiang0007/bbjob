/**=========================================================
 * Module: utils.js
 * Utility library to use across the theme
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .service('HttpServiceUtil', HttpServiceUtil);

    HttpServiceUtil.$inject = ['$rootScope'];
    function HttpServiceUtil($rootScope) {
		function getServPathfunction(){
//			return $rootScope.server.basePath;
		}
		function getSysPath(){
//			return $rootScope.server.basePath+$rootScope.server.sysPath+"/";
		}
		function getBookPath(){
//			return $rootScope.server.basePath+$rootScope.server.bookPath+"/";
		}
     	this.getSysAction=function(action){
//   		return getSysPath()+action+"."+$rootScope.server.mediaType;
     	}
     	this.getBookAction=function(action){
//   		return getBookPath()+action+"."+$rootScope.server.mediaType;
     	}
     	this.getPostAction=function(action){
//   		return $rootScope.server.basePath+"posts/"+action+"."+$rootScope.server.mediaType;
     	}
     	this.getReplayAction=function(action){
//   		return $rootScope.server.basePath+"replay/"+action+"."+$rootScope.server.mediaType;
     	}
     	
    
    }
})();
