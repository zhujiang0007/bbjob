(function() {
	'use strict';
	angular.module('custom')
			.service('sysroleService', sysroleService);
	sysroleService.$inject = [ '$rootScope', '$resource'];
	function sysroleService($rootScope, $resource) {
		var url = $rootScope.server.basePath+'sys/role/:type/:id.json';
		var userrole = $resource(url,{userId:'@userId',type:'@type'},{
			 put:{method:'PUT'},
			 update:{method:'PATCH'}
		});
		this.getRoles=function(userId){
			return userrole.query({type:'list',userId:userId}).$promise;
		}
		this.getRoleList=function(params,selffilters){
			var filter = params.filter();
			var search = {};
			angular.copy(filter, search);
			angular.copy(selffilters, search);
			search.page = params.page();
			search.size = params.count();
			search.type='page';
			return userrole.get(search).$promise;
		}
		this.getRole=function(roleId){
			return userrole.get({roleId:roleId}).$promise;
		}
		this.editRole=function(role){
			return userrole.update(role).$promise;
		}
		this.addRole=function(role){
			return userrole.put(role).$promise;
		}
		this.delRole=function(roleid){
			return userrole.remove({roleId:roleid}).$promise;
		}
	}
})();
