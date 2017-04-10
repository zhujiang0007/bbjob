
(function(){
	"use strict";
	angular.module("app.httpMethod", [])
})();
(function(){
	"use strict";
	angular.module("app.httpMethod")
	.service("serverHttpMethod",serverHttpMethod);
	serverHttpMethod.$inject = ['$rootScope', '$http'];
	function serverHttpMethod($rootScope, $http){
		
		
		this.getHttp = SERVER_getHttp;//get
		
		this.postHttp = SERVER_postHttp;//post
		
		this.putHttp = SERVER_putHttp;//put
		
		this.deleteHttp = SERVER_deleteHttp;//delete
		
		this.patchHttp = SERVER_patchHttp;//patch
		
		this.methodHttp = SERVER_methodHttp;//method
		
		
		function SERVER_getHttp( url, data, onSuccess, onError ){
			var isOnError = onError || onErrorMsg;
			var isOnSuccess = onSuccess || onSuccessMsg;
			
			$http
			.get(url)
			.success(onSuccess)
			.error(isOnSuccess);
		}
		
		function SERVER_postHttp( url, data, onSuccess, onError ){
			var isOnError = onError || onErrorMsg;
			var isOnSuccess = onSuccess || onSuccessMsg;
			
			$http
			.post(url, data)
			.success(onSuccess)
			.error(isOnSuccess);
		}

		function SERVER_putHttp( url, data, onSuccess, onError){
			var isOnError = onError || onErrorMsg;
			var isOnSuccess = onSuccess || onSuccessMsg;
			
			$http
			.put(url, data)
			.success(onSuccess)
			.error(isOnSuccess);
		}
		function SERVER_patchHttp( url, data, onSuccess, onError){
			var isOnError = onError || onErrorMsg;
			var isOnSuccess = onSuccess || onSuccessMsg;
			
			$http
			.patch(url, data)
			.success(onSuccess)
			.error(isOnSuccess);
		}
		
		function SERVER_deleteHttp( url, data, onSuccess, onError ){
			var isOnError = onError || onErrorMsg;
			var isOnSuccess = onSuccess || onSuccessMsg;
			
			$http
			.delete(url)
			.success(onSuccess)
			.error(isOnSuccess);
		}
		
		function SERVER_methodHttp( method, url, data, onSuccess, onError ){
			var isOnError = onError || onErrorMsg;
			var isOnSuccess = onSuccess || onSuccessMsg;
			
			$http({
				method: method,
				url: url,
				data: data//,
  		       // headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
			})
			.success(onSuccess)
			.error(isOnSuccess);
		}
		
		function onSuccessMsg( data, status, headers, config ){
		}
		
		function onErrorMsg( data, status, headers, config ){
		}
		
	}
})();