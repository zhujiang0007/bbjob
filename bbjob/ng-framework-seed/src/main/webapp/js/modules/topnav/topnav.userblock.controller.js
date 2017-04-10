(function() {
    'use strict';

    angular
        .module('app.topnav')
        .controller('UserBlockController', UserBlockController);

    UserBlockController.$inject = ['$rootScope', '$scope','UserService'];
    function UserBlockController($rootScope, $scope,UserService) {
//    	UserService.getCurUser(onReady);
    	 $scope.getUserImg=function(){
       	  if($rootScope.user&&$rootScope.user.imgUrl)
       		  return res+$rootScope.user.imgUrl;
       	  else
       		  return $rootScope.app.defaultUserImg;
         }
    	function onReady(data, status, headers, config){
    		if (data.statusCode = "200"){
    			$rootScope.user = data.data;
			}
    	}
    	
		 $scope.$on('event:auth-loginConfirmed', function(event, data){
//			 UserService.getCurUser(onReady);
			  });

    }
})();
