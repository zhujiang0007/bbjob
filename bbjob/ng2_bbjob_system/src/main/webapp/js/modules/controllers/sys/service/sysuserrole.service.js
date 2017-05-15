(function() {
	'use strict';
	angular.module('custom')
			.service('sysuserroleService', sysuserroleService);
	sysuserroleService.$inject = [ '$rootScope', '$resource'];
	function sysuserroleService($rootScope, $resource) {
		var url = $rootScope.server.basePath+'sys/userrole.json';
		var userrole = $resource(url,{},{
			 put:{method:'PUT'}
		});
		this.getUserRoles=function(userId){
			return userrole.query({userId:userId}).$promise;
		}
		this.addUserRole=function(userId,roleId){
			return userrole.put({userId:userId,roleIds:roleId}).$promise;
		}
		this.delUserRole=function(userId,roleId){
			return userrole.remove({userId:userId,roleIds:roleId}).$promise;
		}
	}
})();
