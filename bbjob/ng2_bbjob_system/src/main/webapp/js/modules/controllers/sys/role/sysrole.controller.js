(function() {
	'use strict';

	angular.module('app.sysrole', []);
	
})();


(function() {
	'use strict';

	angular.module('app.sysrole')
	.controller('sysroleCtrl',sysroleCtrl);
	
	sysroleCtrl.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$timeout',
							'toaster', 'SweetAlert', 'ngDialog', 'sysroleService', 'NgTableParams'];

	function sysroleCtrl($rootScope, $scope, $state, $stateParams, $timeout,
						toaster, SweetAlert, ngDialog, sysroleService, NgTableParams) {


		var self = this;// 把controller 定义为变量self
		$scope.ids = [];//行存储
		$scope.newTpls = null;//弹窗模板
		$scope.changeIf = null;//edit or add  true:edit, false:add
		$scope.selffilters = {};
		$scope.role = {};
		// 查询表单model
				$scope.search = function() {
					self.tableParams.reload();
				};
		$scope.resetSearchBar = function() {
					$scope.selffilters = {
						roleName:null
					};
		};
		/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 角色  Start ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
		getRole();
		function getRole(){
			self.tableParams = new NgTableParams({
				page : 1, // show first page
				count : 10, // count per page
				filter : {}
			}, {
				total : 0, // length of data
				counts : [ 10, 20, 50, 100 ], // hide page counts control
				getData : function(params) {
					$scope.ids = [];
					var result = sysroleService.getRoleList(params,$scope.selffilters);
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
					roleName: null,
					remark: null
			};//弹窗模板
			ngDialog
					.open({
						width : '40%',
						className : 'ngdialog-theme-default',
						template : '<div ng-include="\'jsp/views/operative/menu1/role/sysrole_add.html\'"> </div>',
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
			sysroleService.getRole($scope.ids[0]).then(getById)
			function getById(data){
					$scope.newTpls = data;
					ngDialog
							.open({
								width : '40%',
								className : 'ngdialog-theme-default',
								template : '<div ng-include="\'jsp/views/operative/menu1/role/sysrole_add.html\'"> </div>',
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
	              text: '是否删除'+$scope.ids.length+'条数据？',   
	              type: 'warning',   
	              showCancelButton: true,   
	              confirmButtonColor: '#ee6666',   
	              confirmButtonText: '是的',   
	              cancelButtonText: '取消',   
	              closeOnConfirm: true,   
	              closeOnCancel: true 
	            }, function(isConfirm){
	              if (isConfirm) {
		      			sysroleService.delRole($scope.ids[0]).then(delSuccess,denError);
						function delSuccess(data){
		      				
		      					$scope.ids = [];
		      					self.tableParams.reload(); 
		      					toaster.pop('success', "提示", data.message);
		      				
						}
						function denError(data){
	      					toaster.pop('error', "提示", data);
						}
						
	              } else {     
	               // SweetAlert.swal('取消操作', '你取消了操作', 'error');
	              } 
           })
			
		}
			
		//save
		$scope.save = function(type){
			var data= $scope.newTpls;
			if(type){
				//change
				sysroleService.editRole(data).then(onSuccess,onError)
			}else{
				//add
				sysroleService.addRole(data).then(onSuccess,onError)
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
			$state.go('app.sysroleDetail', {roleId:item.id});
		}
		/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 角色  End ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
		

		
		
		
		
		
		
		//success and error *** ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		function onSuccess(data){
				toaster.pop('success', "操作成功!");
				ngDialog.close();
				self.tableParams.reload(); 
				$scope.ids = [];
		}
		function onError(data){
			toaster.pop('error', "操作失败!", data.message);
			ngDialog.close();
		}
		//success and error *** ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
		
		
	}

	
})();
