(function() {
    'use strict';

    angular
        .module('app.topnav')
        .controller('LoginLogController', LoginLogController);

    LoginLogController.$inject = ['$rootScope', '$scope','LoginLogService','$filter'];
    function LoginLogController($rootScope, $scope,LoginLogService,$filter) {
    	$scope.message="&nbsp;";
    	$rootScope.$watch('user', function(oldVal, newVal) {
			if(oldVal){
				LoginLogService.getLastLoginLog(onReady);
			}
		});
    	function onReady(data, status, headers, config){
    		if (data.data){
    			$scope.message = "您上次登录时间:"+$filter('date')(data.data.createDate,'yyyy-MM-dd hh:mm:ss');
			}else{
				$scope.message=data.message;
			}
    	}
    	
    }
})();
