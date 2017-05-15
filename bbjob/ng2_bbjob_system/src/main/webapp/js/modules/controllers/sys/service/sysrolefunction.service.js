(function() {
	'use strict';
	angular.module('custom')
			.service('sysroleFunctionService', sysroleFunctionService);
	sysroleFunctionService.$inject = [ '$rootScope', '$resource'];
	function sysroleFunctionService($rootScope, $resource) {
		var url = $rootScope.server.basePath+'sys/rolefunction/:type/:qroleId.json';
		var rolefunction = $resource(url,{type:'@type',roleId:'@qroleId'},{
			 put:{method:'PUT'},
			 update:{method:'PATCH'}
		});
		this.getRoleFunctions=function(roleId){
			return rolefunction.query({type:'list',qroleId:roleId}).$promise;
		}
		this.addRoleFUnctions=function(roleId,functionIds){
			return rolefunction.put({roleId:roleId,functionIds:functionIds}).$promise;
		}
		this.delRoleFUnctions=function(roleId,functionIds){
			return rolefunction.remove({roleId:roleId,functionIds:functionIds}).$promise;
		}
	}
})();
