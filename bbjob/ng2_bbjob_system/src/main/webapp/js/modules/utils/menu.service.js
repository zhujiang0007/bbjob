(function(){
	"use strict";
	angular.module("app.topnav")
	.service("MenuLoader",MenuLoader);
	MenuLoader.$inject = ['$rootScope', '$resource'];
	
	function MenuLoader($rootScope, $resource) {
		var menu= $resource($rootScope.server.basePath + 'sys/menu.json');
    	this.getMenus=function(pid){
    		return menu.query({pid:pid}).$promise;
    	}
	}
})();