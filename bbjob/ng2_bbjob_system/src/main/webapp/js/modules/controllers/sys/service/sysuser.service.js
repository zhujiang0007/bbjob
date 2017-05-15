(function() {
	'use strict';
	angular.module('custom')
			.service('sysuserService', sysuserService);
	sysuserService.$inject = [ '$rootScope', '$resource'];
	function sysuserService($rootScope, $resource) {
		var url = $rootScope.server.basePath+'sys/users.json';
		var url2 = $rootScope.server.basePath+'sys/user.json';	
		var lists = $resource(url);
		var users = $resource(url2,{},{
			 put:{method:'PUT'},
			 update:{method:'PATCH'}
		});
		this.getLists = function(params,selffilters) {
			if (lists) {
				var filter = params.filter();
				var search = {};
				angular.copy(filter, search);
				angular.copy(selffilters, search);
				search.page = params.page();
				search.size = params.count();
				return lists.get(search).$promise;
			}
		}
		this.save=function(user){
			return users.put(user).$promise;
		}
		this.update=function(user){
			return users.update(user).$promise;
		}
		this.get=function(id){
			return users.get({userId:id}).$promise;
		}
		this.remove=function(id){
			return users.remove({userId:id}).$promise;
		}
	}
	/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ ngTable Service  End ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
})();
