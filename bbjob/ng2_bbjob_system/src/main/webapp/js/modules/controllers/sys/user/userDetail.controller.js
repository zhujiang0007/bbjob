(function() {
	'use strict';

	angular.module('app.user', []);
	
})();


(function() {
	'use strict';

	angular.module('app.user')
	.controller('userDetailCtrl',userDetailCtrl);
	
	userDetailCtrl.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$timeout',
							'toaster', 'SweetAlert', 'ngDialog','sysuserService','sysuserroleService',
							'sysroleService','sysfunctionService','sysUserFunctionService'
							];

	function userDetailCtrl($rootScope, $scope, $state, $stateParams, $timeout,
						toaster, SweetAlert, ngDialog,sysuserService,
						sysuserroleService,sysroleService,sysfunctionService,sysUserFunctionService) {


		var self = this;// 把controller 定义为变量self
		$scope.ids = [];//行存储
		$scope.newTpls = null;//弹窗模板
		$scope.changeIf = null;//edit or add  true:edit, false:add
		
		var userId = $stateParams.userId;//接收routes参数
		
		$scope.user = {
				userMsg: null,				//用户信息
				userRole: null,				//用户角色
				userRoleMsg: null,			//all用户角色
				userPurview: [],			//用户权限
				userFunc:[],
				userId:$stateParams.userId
		}
		

		/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 用户信息  Start ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
		getUserMsg(userId);
		
		function getUserMsg(id){

			sysuserService.get(id).then(getUserMsgFn,function(resp){
				toaster.pop('error', "操作失败", data.message);
			});
			
			function getUserMsgFn(data){
					$scope.user.userMsg = data;	//用户信息赋值
			}
			
		}
		/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 用户信息  End ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
		

		
		/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 用户角色  Start ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
		
		//userRole
		getUserRole(userId);
		function getUserRole(id){
			sysuserroleService.getUserRoles(userId).then(getUserRoleFn,function(resp){
				toaster.pop('error', "操作失败", data.message);
			});
			function getUserRoleFn(data){		
					$scope.user.userRole = data;	//用户角色赋值	
			}
		}

		var roleIds = [];
		$scope.roleClick = function(role){
			var itin = roleIds.indexOf(role.id);
			if(itin!=-1){
				roleIds.splice(itin,1);
			}else{
				roleIds.push(role.id);
			}
			role.selected = !role.selected;
			if(roleIds.length == $scope.user.userRole.length){
				$scope.isRole = true;
			}
		}

		//allRole
		getSysRoles(userId);
		function getSysRoles(userId){
			sysroleService.getRoles(userId).then(getUserRoleFn);
			function getUserRoleFn(data){			
					$scope.user.userRoleMsg = data;	//所有角色	
			}
		}
		//选择事件
		var roleAllIds = [];
		$scope.roleAllClick = function(role){
			var itin = roleAllIds.indexOf(role.id);
			if(itin!=-1){
				roleAllIds.splice(itin,1);
			}else{
				roleAllIds.push(role.id);
			}
			role.selected = !role.selected;
			if(roleIds.length == $scope.user.userRoleMsg.length){
				$scope.isRoleAll = true;
			}
		}
		
		
		//全选
		$scope.isRoleAll = false;
		$scope.isRole = false;
		$scope.roleAll = function(type){
			if(type=='all'){
				roleAllIds = [];
				for(var i = 0; i < $scope.user.userRoleMsg.length; i ++){
					if($scope.isRoleAll){
						roleAllIds.push($scope.user.userRoleMsg[i].id);
						$scope.user.userRoleMsg[i].selected = true;
					}else{
						$scope.user.userRoleMsg[i].selected = false;
					}
				}
			}else{
				roleIds = [];
				for(var i = 0; i < $scope.user.userRole.length; i ++){
					if($scope.isRole){
						roleIds.push($scope.user.userRole[i].id);
						$scope.user.userRole[i].selected = true;
					}else{
						$scope.user.userRole[i].selected = false;
					}
				}
			}
		}
		//添加角色
		$scope.roleAdd = function(){
			if(roleAllIds.length==0){
				toaster.pop('error', "提示", "请至少选择一个角色！");
				return;
			}
			sysuserroleService.addUserRole(userId,roleAllIds).then(function(){
				getUserRole(userId);
				getSysRoles(userId);
				roleAllIds = [];
				roleIds = [];
			},function(resp){
				toaster.pop('error', "提示", resp);
			})
			
			
			
		}
		//移除角色
		$scope.roleRemove = function(){
			if(roleIds.length==0){
				toaster.pop('error', "提示", "请至少选择一个角色！");
				return;
			}
			sysuserroleService.delUserRole(userId,roleIds).then(function(){
				getUserRole(userId);
				getSysRoles(userId);
				roleAllIds = [];
				roleIds = [];
			},function(resp){
				toaster.pop('error', "提示", resp);
			});
			
			
		}
		/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 用户角色  End ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
		
		
		
		/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 用户权限  Start ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
		//get用户权限
		sysUserFunctionService.getUserFunctions(userId).then(function(data){
			$scope.user.userFunc=data;
			return sysfunctionService.getFunctions(0);
		}).then(initFunctionsPanels);
		function initFunctionsPanels(data){
			$scope.userPurview=data;
			$scope.userPurview.push({id:null,name:"登录权限"});
		}
		
		

	}

})();
