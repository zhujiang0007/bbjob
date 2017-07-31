(function() {
	'use strict';

	angular.module('app.syspurview', []);
	
})();

(function() {
	'use strict';

	angular.module('app.syspurview')
	.controller('syspurviewCtrl',syspurviewCtrl);
	
	syspurviewCtrl.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$timeout',
							'toaster', 'SweetAlert', 'ngDialog','sysfunctionService' ];

	function syspurviewCtrl($rootScope, $scope, $state, $stateParams, $timeout,
						toaster, SweetAlert, ngDialog,sysfunctionService) {


		var self = this;// 把controller 定义为变量self
		$scope.changeIf = null;//edit or add  true:edit, false:add
		
		$scope.purview = {};
		$scope.functions = [];
		init();
		function init(){
			sysfunctionService.getFunctions(0).then(function(data){
				$scope.functions=data;
				$scope.functions.push({id:null,name:"登录权限"});
			})
		}
		
		
		$scope.sortableOptions = {
	              placeholder: '<div class="col-lg-4" style="height:400px;"><div></div></div>',
	              forcePlaceholderSize: true
	          };
        $scope.sortableCallback = function (sourceModel, destModel, start, end) {
            console.log(start + ' -> ' + end);
          };
            
		
		$scope.doesNotExist=function(value,id){
			return sysfunctionService.checkPerCodeExists(value,id);
		}
		
		
		//add
		$scope.add = function(pId){
			$scope.changeIf = false;
			$scope.dialogTitle = '新增';
			$scope.newTpls = {
					isMenu:1,
					type:2,
					openType:1,
					pId:pId,
					isVisible:1,
					remark: null
			};//弹窗模板
			ngDialog
					.open({
						width : '40%',
						className : 'ngdialog-theme-default',
						template : '<div ng-include="\'views/operative/sys/purview/purview_add.html\'"> </div>',
						plain : true,
						scope : $scope
					});
		}
		//edit
		$scope.edit = function(id){
			$scope.changeIf = true;
			$scope.dialogTitle = '编辑';
			
			sysfunctionService.get(id).then(getById,function(data){
      		  toaster.pop('error', "提示", data.message);
      	  });
			function getById(data){
					$scope.newTpls = data;
					ngDialog
							.open({
								width : '40%',
								className : 'ngdialog-theme-default',
								template : '<div ng-include="\'views/operative/sys/purview/purview_add.html\'"> </div>',
								plain : true,
								scope : $scope
							});
				
			}
		}
		
		//del
		$scope.del = function(func){
			

			SweetAlert.swal({   
	              title: '警告',   
	              text: '是否删除  '+func.name+'?',   
	              type: 'warning',   
	              showCancelButton: true,   
	              confirmButtonColor: '#ee6666',   
	              confirmButtonText: '是的',   
	              cancelButtonText: '取消',   
	              closeOnConfirm: true,   
	              closeOnCancel: true 
	            }, function(isConfirm){
	              if (isConfirm) {
	            	  sysfunctionService.remove(func.id).then(delSuccess,function(data){
	            		  toaster.pop('error', "提示", data.message);
	            	  });
						function delSuccess(data){			
		      					toaster.pop('success', "提示", "删除成功！");
		      					$scope.$broadcast("delSuccess",func);
		      					if(func.pId==0){
		      						init();
		      					}
						}
						
						
	              } else {     
	               // SweetAlert.swal('取消操作', '你取消了操作', 'error');
	              } 
           })
			
		}
			
		//save
		$scope.save = function(type){
			var func= $scope.newTpls;
			function saveSuccess(data){
				toaster.pop('success', "提示", "保存成功！");
				ngDialog.close();
				$scope.$broadcast("saveSuccess",func);
				if(func.pId==0){
					init();
				}
			}

			function saveFailure(data){
				toaster.pop('error', "提示",data);
			}
			if(type){
				//change
				sysfunctionService.update(func).then(saveSuccess,saveFailure);
			}else{
				//add
				sysfunctionService.add(func).then(saveSuccess,saveFailure);
			}
		}
		//关闭dialog
		$scope.close = function(){
			ngDialog.close();
		}
	
		function saveSuccess(data){
			toaster.pop('success', "提示", "保存成功！");
			ngDialog.close();
			$scope.$broadcast("saveSuccess",data);
			if(data.pId==0){
				$scope.functions.push(data);
			}
		}

		function saveFailure(data){
			toaster.pop('error', "提示", data.message);
		}
		
	
		
	}

})();
