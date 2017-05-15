(function() {
	'use strict';

	angular.module('custom')
	.controller('sysroleDetailCtrl',sysroleDetailCtrl);
	
	sysroleDetailCtrl.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$timeout',
							'toaster', 'SweetAlert', 'ngDialog',
							'sysroleService','sysfunctionService','sysroleFunctionService'
							];

	function sysroleDetailCtrl($rootScope, $scope, $state, $stateParams, $timeout,
						toaster, SweetAlert, ngDialog
						,sysroleService,sysfunctionService,sysroleFunctionService) {
		var roleId = $stateParams.roleId;
		$scope.roleFunctions=[];
		$scope.functions=[];
		$scope.role=null;
		sysroleService.getRole(roleId).then(function(data){
			$scope.role=data;
		});
		sysroleFunctionService.getRoleFunctions(roleId).then(function(data){
			$scope.roleFunctions=data;
			return sysfunctionService.getFunctions(0);
		}).then(function(data){
			$scope.functions=data;
			$scope.functions.push({id:null,name:"登录权限"});
		});
	}
})();