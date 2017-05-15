(function() {
	'use strict';
	angular.module('custom')
			.service('sysfunctionService', sysfunctionService);
	sysfunctionService.$inject = [ '$rootScope', '$resource'];
	function sysfunctionService($rootScope, $resource) {
		var url = $rootScope.server.basePath+'sys/function/:type/:pid.json';
		var userrole = $resource(url,{userId:'@userId',type:'@type',pid:'@pid'},{
			 put:{method:'PUT'},
			 update:{method:'PATCH'}
		});
		this.getFunctions=function(pid){
			return userrole.query({type:'list',pid:pid}).$promise;
		}
	}
})();
