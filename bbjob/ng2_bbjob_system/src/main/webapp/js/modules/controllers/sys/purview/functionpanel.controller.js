

(function() {
	'use strict';

	angular.module('custom')
	.controller('functionpanelController',functionpanelController);
	
	functionpanelController.$inject = ['$rootScope', '$scope', '$timeout',
							'toaster', 'sysfunctionService'
							];

	function functionpanelController($rootScope, $scope,$timeout,toaster,sysfunctionService) {


		var self = this;// 把controller 定义为变量self
		$scope.footList=[$scope.func];
		var curPage=$scope.func;
		init();
		function init(){
			sysfunctionService.getFunctions($scope.func.id).then(getFuncSuccess);
		}
		
		function getFuncSuccess(data){
			$scope.funcList=data;
		}
		
		$scope.$on("saveSuccess",function(event,data){
			if(data.pId==$scope.func.id)
			init();
		});
		$scope.$on("delSuccess",function(event,data){
			if(data.pId==$scope.func.id)
			init();
		});
		
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
