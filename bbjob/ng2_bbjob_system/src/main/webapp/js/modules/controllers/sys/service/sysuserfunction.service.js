(function() {
	'use strict';
	angular.module('custom')
			.service('sysUserFunctionService', sysUserFunctionService);
	sysUserFunctionService.$inject = [ '$rootScope', '$resource'];
	function sysUserFunctionService($rootScope, $resource) {
		var url = $rootScope.server.basePath+'sys/userfunction/:quserId/:type/:pid.json';
		var userfunction = $resource(url,{quserId:'@quserId',type:'@type',pid:'@pid'},{
			 put:{method:'PUT'},
			 update:{method:'PATCH'}
		});
		this.getUserFunctions=function(userId){
			return userfunction.query({type:'list',quserId:userId}).$promise;
		}
		this.addUserFUnctions=function(userId,functiondIds){
			return userfunction.put({userId:userId,functionIds:functiondIds}).$promise;
		}
		this.delUserFUnctions=function(userId,functiondIds){
			return userfunction.remove({userId:userId,functionIds:functiondIds}).$promise;
		}
	}
})();
