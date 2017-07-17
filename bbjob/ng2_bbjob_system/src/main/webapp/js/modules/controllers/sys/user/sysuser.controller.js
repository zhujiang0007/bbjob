(function() {
	'use strict';

	angular.module('app.user', []);
	
})();


(function() {
	'use strict';

	angular.module('app.user')
	.controller('sysuserCtrl',sysuserCtrl);
	
	sysuserCtrl.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$timeout',
							'toaster', 'SweetAlert', 'ngDialog', 'sysuserService', 'NgTableParams'];

	function sysuserCtrl($rootScope, $scope, $state, $stateParams, $timeout,
						toaster, SweetAlert, ngDialog, sysuserService, NgTableParams) {


		var self = this;// 把controller 定义为变量self
		$scope.ids = [];//行存储
		$scope.newTpls = null;//弹窗模板
		$scope.changeIf = null;//edit or add  true:edit, false:add
		// 查询表单数据
		$scope.selffilters = {};
		$scope.statussel = {
				"启用" : 1,
				"禁用" : 0
			};// 性别
		// 查询表单model
		$scope.search = function() {
			self.tableParams.reload();
		};

		$scope.resetSearchBar = function() {
			$scope.selffilters = {
				username : null,
				email : null,
				enable : null,
				phone : null
			};
		};

		/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 用户  Start ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
		getUser();
		function getUser(){
			self.tableParams = new NgTableParams({
				page : 1, // show first page
				count : 10, // count per page
				filter : {}
			}, {
				total : 0, // length of data
				counts : [ 10, 20, 50, 100 ], // hide page counts control
				getData : function(params) {
					$scope.ids = [];
					var result = sysuserService.getLists(params,$scope.selffilters);
					return result.then(function(data){
						params.total(data.total);
						return data.list;
					});
				}
			});
		}

		//table 单击行
		$scope.clickRow = function(row) {
			if (row.$selected == true) {
				$scope.ids.push(row.id);
			} else {
				for ( var index in $scope.ids) {
					if ($scope.ids[index] === row.id) {
						// 删除
						$scope.ids.splice(index, 1);
						return;
					}
				}
			}
		}
		
		//add
		$scope.add = function(){
			$scope.changeIf = false;
			$scope.dialogTitle = '新增';
			$scope.newTpls = {
					username: null,
					email: null,
					phone: null,
					password: '123456',
					type: 1,
					enable: 1
			};//弹窗模板
			ngDialog
					.open({
						width : '60%',
						className : 'ngdialog-theme-default',
						template : '<div ng-include="\'views/operative/sys/user/sysuser_add.html\'"> </div>',
						plain : true,
						scope : $scope
					});
		}
		//edit
		$scope.edit = function(){
			$scope.changeIf = true;
			$scope.dialogTitle = '编辑';
			if($scope.ids.length!=1){
				toaster.pop('error', "提示", "请选择一条数据！");
				return;
			}
  			sysuserService.get($scope.ids[0]).then(getById,onError)
			function getById(data){
					$scope.newTpls = data;
					ngDialog
							.open({
								width : '60%',
								className : 'ngdialog-theme-default',
								template : '<div ng-include="\'views/operative/sys/user/sysuser_add.html\'"> </div>',
								plain : true,
								scope : $scope
							});
			}
		}
		
		//del
		$scope.del = function(){
			if($scope.ids.length!=1){
				toaster.pop('error', "提示", "请选择一条数据！");
				return;
			}

			SweetAlert.swal({   
	              title: '警告',   
	              text: '是否删除用户？删除后将无法恢复。',   
	              type: 'warning',   
	              showCancelButton: true,   
	              confirmButtonColor: '#ee6666',   
	              confirmButtonText: '是的',   
	              cancelButtonText: '取消',   
	              closeOnConfirm: true,   
	              closeOnCancel: true 
	            }, function(isConfirm){
	              if (isConfirm) {
	            	   sysuserService.remove($scope.ids[0]).then(delSuccess,denError);
						function delSuccess(data){
		      					$scope.ids = [];
		      					self.tableParams.reload(); 
		      					toaster.pop('success', "提示", "删除成功!");
						}
						function denError(data){
	      					toaster.pop('error', "提示", data);
						}
						
	              }
           })
			
		}
			
		//save
		$scope.save = function(type){
			var data = $scope.newTpls;
			if(type){
				//change
				sysuserService.update(data).then(onSuccess,onError);
			}else{
				//add
				sysuserService.save(data).then(onSuccess,onError);
			}
		}
		//关闭dialog
		$scope.close = function(){
			ngDialog.close();
		}
		
		//详情跳转
		$scope.rowdetail = function(item){
			if(item.id==null){
				toaster.pop('error', "提示", "操作失败！");
				return;
			}
			$state.go('app.userDetail', {userId:item.id});
		}
		
		
		$scope.changeEnable=function(id,enable){
			sysuserService.update({id:id,enable:enable}).then(function(resp){
				toaster.pop('success', "提示", "操作成功!");
				self.tableParams.reload(); 
			},
			function(resp){
				toaster.pop('error', "提示", resp);
			});
		}
		$scope.reset=function(id){
			sysuserService.update({id:id,password:123456}).then(function(resp){
				toaster.pop('success', "提示", "操作成功,已重置为123456");
			},
			function(resp){
				toaster.pop('error', "提示", resp);
			});
		}
		/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 用户  End ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
		

		
		//success and error *** ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		function onSuccess(data){
				toaster.pop('success', data);
				ngDialog.close();
				self.tableParams.reload(); 
				$scope.ids = [];
		}
		function onError(data){
			    toaster.pop('error', "提示", data);
			    ngDialog.close();
		}
		//success and error *** ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
		
		
	}
})();
