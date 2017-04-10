(function() {
	'use strict';

	angular.module('custom', [])
	.controller('ngTableCtrl',ngTableCtrl);
	
	ngTableCtrl.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$timeout',
							'toaster', 'SweetAlert', 'ngDialog', 'serverHttpMethod', 'houseService', '$http', 'NgTableParams'];

	function ngTableCtrl($rootScope, $scope, $state, $stateParams, $timeout,
						toaster, SweetAlert, ngDialog, httpMethod, houseService, $http, NgTableParams) {
		
		//房屋设置 DEMO
		
		var self = this;// 把controller 定义为变量self
		$scope.ids = [];//行存储
		$scope.newTpls = null;//模板
		$scope.changeIf = null;//edit or add  true:edit, false:add
		
		// 查询表单model
		$scope.searchfilters = {
				commId:null ,
				buildingId:null,
				unitNo:null,
				houseNo:null
		};
		//查询表单数据
		$scope.setData = {
			comm: null,
			building: null,
			unit: null,
			house: null
		}
		//addTpls data
		$scope.add = {
			comm: null,
			building: null,
			unit: null,
			propertysel: null,
			statussel: null,
			structuresel: null
		}
		
		//****************************search 区域****************************
		getComm();
		//get小区List
		function getComm(){
			var url = "http://192.168.0.123:8080/wy/service/house/getCommData.json";
			httpMethod.getHttp(url, null, getCommSuccess);
			function getCommSuccess(data, status, headers,config){
				if(data.statusCode == '200'){
					$scope.setData.comm = data.data;
				}
			}
		}
		
		//get楼宇List
		$scope.changecomm = function(commId) {
			//clear
			$scope.searchfilters.buildingId = null;
			$scope.searchfilters.unitNo = null;
			$scope.searchfilters.houseNo = null;
			
			$scope.setData.building = null;
			$scope.setData.unit = null;
			$scope.setData.house = null;
			
			var url = "http://192.168.0.123:8080/wy/service/house/getBuildDataByCommId.json?id=" + ( commId || "" );
			httpMethod.getHttp(url, null, getBuindingSuccess, getBuindingError);
			
			function getBuindingSuccess(data, status, headers, config){
				if(data.statusCode == '200'){
					$scope.setData.building = data.data;
				}else{
					toaster.pop('error', "提示", obj2.message);
				}
			}
			function getBuindingError(data, status, headers, config){
					toaster.pop('error', "提示", "服务器繁忙！");
			}
		};
		
		//get单元List
		$scope.changebuild = function(commId, buildingId){
			//clear
			$scope.searchfilters.unitNo = null;
			$scope.searchfilters.houseNo = null;
			
			$scope.setData.unit = null;
			$scope.setData.house = null;
			
			var url = "http://192.168.0.123:8080/wy/service/house/getUnitByBuildId.json?id=" + ( buildingId || "" );
			httpMethod.getHttp(url, null, getUnitSuccess, getUnitError);
			
			function getUnitSuccess(data, status, headers, config){
				if(data.statusCode == '200'){
					$scope.setData.unit = data.data;
				}else{
					toaster.pop('error', "提示", data.message);
				}
			}
			function getUnitError(data, status, headers, config){
					toaster.pop('error', "提示", "服务器繁忙！");
			}
		}
		
		//get门牌号list
		$scope.changeunit = function(commId, buildingId, unitId){
			//clear
			$scope.searchfilters.houseNo = null;
			
			$scope.setData.house = null;
			
			var url = "http://192.168.0.123:8080/wy/service/house/getRoomByBuildAndUnitId.json?buildingid=" + (buildingId || "") + "&unitno=" + (unitId || "");
			httpMethod.getHttp(url, null, getHouseSuccess, getHouseError);
			
			function getHouseSuccess(data, status, headers, config){
				if(data.statusCode == '200'){
					$scope.setData.house = data.data;
				}else{
					toaster.pop('error', "提示", obj2.message);
				}
			}
			function getHouseError(data, status, headers, config){
					toaster.pop('error', "提示", "服务器繁忙！");
			}
		}
		
		//RESET重置
		$scope.resetSearchBar = function(){
			//clear
			$scope.searchfilters.buildingId = null;
			$scope.searchfilters.unitNo = null;
			$scope.searchfilters.houseNo = null;
			
			$scope.setData.building = null;
			$scope.setData.unit = null;
			$scope.setData.house = null;
		}
		
		//Search查询
		$scope.search = function(){
			self.tableParams.reload();//刷新ngtable列表
		}
		
		//****************************search 区域 END****************************
		
		
		//****************************ngTbale 区域****************************
		getTable();
		//ngtable
		function getTable(){
			self.tableParams = new NgTableParams({
				page : 1, // show first page
				count : 10, // count per page
				filter : {}
			}, {
				total : 0, // length of data
				counts : [ 10, 20, 50, 100 ], // hide page counts control
				getData : function(params) {
					$scope.ids = [];
					var result = houseService.getLists(params, $scope.searchfilters);
					return result;
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
		
		// 新增
		$scope.add = function() {
			$scope.dialogTitle = "新增";
			$scope.changeIf = false;
			$scope.newTpls = null;
			$scope.add.comm = $scope.setData.comm;
			$scope.add.statussel = {"自用":1,"租贸":2};
			getPropertysel();getStructuresel();
			ngDialog
					.open({
						width : '60%',
						className : 'ngdialog-theme-default',
						template : '<div ng-include="\'jsp/views/operative/menu1/ngTable/ngTable_add.html\'"> </div>',
						plain : true,
						scope : $scope,
						preCloseCallback : function() {
							$scope.newTpls = {};
						}
					});
		}
		// 编辑
		$scope.edit = function() {
			$scope.dialogTitle = "编辑";
			$scope.changeIf = true;
			getPropertysel();getStructuresel();
			$scope.add.statussel = {"自用":1,"租贸":2};
			
			if ($scope.ids.length == 1) {
				var url = "http://192.168.0.123:8080/wy/service/house/getById.json?id=" + $scope.ids;
				httpMethod.getHttp(url, null, getByIdSuccess, getByIdError);
				function getByIdSuccess(data, status, headers, config) {
					if(data.statusCode == '200'){
						$scope.newTpls = data.data;
						
						ngDialog
								.open({
									width : '60%',
									className : 'ngdialog-theme-default',
									template : '<div ng-include="\'jsp/views/operative/menu1/ngTable/ngTable_add.html\'"> </div>',
									plain : true,
									scope : $scope,
									preCloseCallback : function() {
										$scope.newTpls = {};
									}
								});
								
					}else{
						toaster.pop('error', "提示", obj2.message);
					}
				}
				function getByIdError(data, status, headers, config) {
					toaster.pop('error', "提示", "服务器繁忙！");
				}
			}else if($scope.ids.length > 1){
				toaster.pop('error', "提示", "请选择一条数据");
			} else {
				toaster.pop('error', "提示", "请至少选择一条数据");
			}
		}
		
		//删除
		$scope.delete = function(){
			if ($scope.ids.length == 1) {
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
							var url = "http://192.168.0.123:8080/wy/service/house/delete.json?id=" + $scope.ids;
							httpMethod.getHttp(url, null, delSuccess, denError);
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
			}else {
				toaster.pop('error', "提示", "请至少选择一条数据");
			}
		}
		// save
		$scope.save = function(type){
			if(!type){
				var url = "http://192.168.0.123:8080/wy/service/house/save.json";
				var data = $scope.newTpls;
				httpMethod.postHttp(url, data, saveSuccess, saveError);
			} else { 
				var url = "http://192.168.0.123:8080/wy/service/house/save.json";
				var data = $scope.newTpls;
				httpMethod.postHttp(url, data, saveSuccess, saveError);
			}
			
			function saveSuccess (data){
				if(data.statusCode == '200'){
					toaster.pop('success', "提示", data.message);
					$scope.close();
					$scope.ids = "";
					self.tableParams.reload(); 
				}else{
					toaster.pop('error', "提示", data.message);
				}
			}
			function saveError (data){
				toaster.pop('error', "提示", "服务器繁忙！");
			}
		}
		//关闭
		$scope.close = function(){
			$scope.NewTpls = {};
			ngDialog.close();
			self.tableParams.reload();
		}
		
		function getPropertysel(){
			var url = "http://192.168.0.123:8080/wy/service/house/getHouseProperty.json";
			httpMethod.getHttp(url, null, getPropertyselFn);
			function getPropertyselFn(data){
				if (data.statusCode == 200) {
					$scope.add.propertysel = data.data;
				}
			}
		}
		function getStructuresel(){
			var url = "http://192.168.0.123:8080/wy/service/house/getStructure.json";
			httpMethod.getHttp(url, null, getStructureselFn);
			function getStructureselFn(data){
				if (data.statusCode == 200) {
					$scope.add.structuresel = data.data;
				}
			}
		}
		
	//****************************ngTbale 区域 END****************************
		
	//success and error *** success and error *** success and error *** success and error
	function onSuccess(data, status, headers, config){
    	if (data.statusCode == '200') {
			toaster.pop('success', "操作成功");
			ngDialog.close();
		} else {
			toaster.pop('error', "操作失败", data.message);
    		idsReset();
		}
	}
	function onError(data, status, headers, config){
		toaster.pop('error', "提示", "服务器繁忙！");
		ngDialog.close();
	}
	//success and error *** success and error *** success and error *** success and error
	}

	angular.module('custom')
			.service('houseService', houseService);
	houseService.$inject = [ '$rootScope', '$resource'];
	function houseService($rootScope, $resource) {
		var lists = $resource("http://192.168.0.123:8080/wy/service/house/page.json");
		this.getLists = function(params, selffilters) {
			if (lists) {
				var filter = params.filter();
				var search = {};
				angular.copy(filter, search);
				angular.copy(selffilters, search);
				search.page = params.page();
				search.size = params.count();
				return lists.get(search).$promise.then(function(pageData) {
					params.total(pageData.data.total);
					return pageData.data.list;
				});
			}
		}
	}
})();
