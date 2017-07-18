(function() {
	'use strict';
	angular.module('custom')
			.service('sysfunctionService', sysfunctionService);
	sysfunctionService.$inject = [ '$rootScope', '$resource'];
	function sysfunctionService($rootScope, $resource) {
		var url = $rootScope.server.basePath+'sys/function/:qtype/:pid.json';
		var functions = $resource(url,{userId:'@userId',qtype:'@qtype',pid:'@pid'},{
			 put:{method:'PUT'},
			 update:{method:'PATCH'}
		});
		this.getFunctions=function(pid){
			return functions.query({qtype:'list',pid:pid}).$promise;
		}
		this.checkPerCodeExists=function(permissionCode,id){
			return functions.query({qtype:'check',permissionCode:permissionCode,id:id}).$promise;
		}
		this.add=function(data){
			return functions.put(data).$promise;
		}
		this.update=function(data){
			return functions.update(data).$promise;
		}
		this.remove=function(id){
			return functions.remove({id:id}).$promise;
		}
		this.get=function(id){
			return functions.get({id:id}).$promise;
		}
	}
})();
