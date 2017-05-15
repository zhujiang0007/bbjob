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
		sysfunctionService.getFunctions(0).then(function(data){
			$scope.functions=data;
			$scope.functions.push({id:null,name:"登录权限"});
		})
		
		

		
		
		//add
		$scope.add = function(){
			$scope.changeIf = false;
			$scope.dialogTitle = '新增';
			$scope.newTpls = {
					rolename: null,
					remark: null
			};//弹窗模板
			ngDialog
					.open({
						width : '40%',
						className : 'ngdialog-theme-default',
						template : '<div ng-include="\'jsp/views/operative/menu1/role/purview_add.html\'"> </div>',
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
			var url = HttpServiceUtil.sysrole + '?roleId=' + $scope.ids[0];
			httpMethod.getHttp( url, null, getById, onError);
			function getById(data, status, headers, config){
				if(data.statusCode == '200'){
					$scope.newTpls = data.data;
					ngDialog
							.open({
								width : '40%',
								className : 'ngdialog-theme-default',
								template : '<div ng-include="\'jsp/views/operative/menu1/role/sysrole_add.html\'"> </div>',
								plain : true,
								scope : $scope
							});
				}else{
					toaster.pop('error', "操作失败", data.message);
				}
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
		      			var url = HttpServiceUtil.sysrole + '?roleId=' + $scope.ids;
						httpMethod.deleteHttp(url, null, delSuccess, denError);
						function delSuccess(data, status, headers, config){
		      				if (data.statusCode == 200) {
		      					$scope.ids = [];
		      					self.tableParams.reload(); 
		      					toaster.pop('success', "提示", data.message);
		      				} else {
		      					toaster.pop('error', "提示", data.message);
		      				}
						}
						function denError(data, status, headers, config){
	      					toaster.pop('error', "提示", "服务器繁忙！");
						}
						
	              } else {     
	               // SweetAlert.swal('取消操作', '你取消了操作', 'error');
	              } 
           })
			
		}
			
		//save
		$scope.save = function(type){
			var url = HttpServiceUtil.sysrole,
			data= $scope.newTpls;
			if(type){
				//change
				httpMethod.postHttp( url, data, onSuccess, onError);
			}else{
				//add
				httpMethod.putHttp( url, data, onSuccess, onError);
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
			$state.go('app.syspurviewDetail', {pruviewId:item.id});
		}
		/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 角色  End ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
		

		
		
		
		
		
		
		//success and error *** ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		function onSuccess(data, status, headers, config){
	    	if (data.statusCode == '200') {
				toaster.pop('success', "操作成功");
				ngDialog.close();
				self.tableParams.reload(); 
				$scope.ids = [];
			} else {
				toaster.pop('error', "操作失败", data.message);
				ngDialog.close();
			}
		}
		function onError(data, status, headers, config){
			toaster.pop('error', "提示", "服务器繁忙！");
			ngDialog.close();
		}
		//success and error *** ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
		
		
	}

	/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ ngTable Service  Start ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
	angular.module('custom')
			.service('syspurviewService', syspurviewService);
	syspurviewService.$inject = [ '$rootScope', '$resource', 'HttpServiceUtil'];
	function syspurviewService($rootScope, $resource, HttpServiceUtil) {
		var lists = $resource(HttpServiceUtil.getSyspurview);
		this.getLists = function(params) {
			if (lists) {
				var filter = params.filter();
				var search = {};
				angular.copy(filter, search);
//				angular.copy(selffilters, search);
				search.page = params.page();
				search.size = params.count();
				return lists.save(search).$promise.then(function(pageData) {
					params.total(pageData.total);
					return pageData.list;
				});
			}
		}
	}
	/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ ngTable Service  End ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
})();
