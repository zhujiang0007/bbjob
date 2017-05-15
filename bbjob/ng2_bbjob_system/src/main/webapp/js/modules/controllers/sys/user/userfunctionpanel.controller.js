

(function() {
	'use strict';

	angular.module('custom')
	.controller('userfuncpanelController',userfuncpanelController);
	
	userfuncpanelController.$inject = ['$rootScope', '$scope', '$timeout',
							'toaster', 'sysfunctionService','sysUserFunctionService'
							];

	function userfuncpanelController($rootScope, $scope,$timeout,toaster,sysfunctionService,sysUserFunctionService) {


		var self = this;// 把controller 定义为变量self
		$scope.footList=[$scope.func];
		var curPage=$scope.func;
		var userId=$scope.user.userId;
		sysfunctionService.getFunctions($scope.func.id).then(getFuncSuccess)
		function getFuncSuccess(data){
			$scope.funcList=data;
			angular.forEach($scope.funcList,function(data){
				data.checked=funccheck(data.id);
			})
			
		}
		function funccheck(functionId){
			return $scope.user.userFunc.indexOf(functionId)!=-1;
		}
		$scope.funChange=function(fun){
			if($scope.user.userFunc.indexOf(fun.id)==-1){
				sysUserFunctionService.addUserFUnctions(userId,[fun.id]).then(function(data){
					$scope.user.userFunc.push(fun.id);
					fun.check=true;
				},function(data){fun.checked=false;});			
			}else{
				sysUserFunctionService.delUserFUnctions(userId,[fun.id]).then(function(data){
					$scope.user.userFunc.splice($scope.user.userFunc.indexOf(fun.id), 1);
					fun.check=false;
				},function(data){fun.checked=true;});
				
			}
			
		}
		$scope.goFunctionPage=function(foot){
			if($scope.isCurPage(foot))
				return;
			if(foot.type==4)
				return;
			sysfunctionService.getFunctions(foot.id).then(function(data){
				getFuncSuccess(data);
				curPage=foot;
				var isNew=true;
				angular.forEach($scope.footList,function(data,index,array){
					if(data.id==foot.id){
						array=array.splice(index+1,array.length-index-1);
						isNew=false;
					}
				});
				if(isNew){
					$scope.footList.push(foot);
				}
			});
		}
		$scope.isCurPage=function(fun){
			return curPage.id==fun.id;
		}

	}

})();
