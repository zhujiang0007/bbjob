

(function() {
	'use strict';

	angular.module('custom')
	.controller('userfuncpanelController',userfuncpanelController);
	
	userfuncpanelController.$inject = ['$rootScope', '$scope', '$timeout',
							'toaster', 'sysfunctionService','sysroleFunctionService'
							];

	function userfuncpanelController($rootScope, $scope,$timeout,toaster,sysfunctionService,sysroleFunctionService) {


		var self = this;// 把controller 定义为变量self
		$scope.footList=[$scope.func];
		var curPage=$scope.func;
		var roleId=$scope.role.id;
		sysfunctionService.getFunctions($scope.func.id).then(getFuncSuccess)
		function getFuncSuccess(data){
			$scope.funcList=data;
			angular.forEach($scope.funcList,function(data){
				data.checked=funccheck(data.id);
			})
			
		}
		function funccheck(functionId){
			return $scope.roleFunctions.indexOf(functionId)!=-1;
		}
		$scope.funChange=function(fun){
			if($scope.roleFunctions.indexOf(fun.id)==-1){
				sysroleFunctionService.addRoleFUnctions(roleId,[fun.id]).then(function(data){
					$scope.roleFunctions.push(fun.id);
					fun.check=true;
				},function(data){fun.checked=false;});			
			}else{
				sysroleFunctionService.delRoleFUnctions(roleId,[fun.id]).then(function(data){
					$scope.roleFunctions.splice($scope.roleFunctions.indexOf(fun.id), 1);
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
