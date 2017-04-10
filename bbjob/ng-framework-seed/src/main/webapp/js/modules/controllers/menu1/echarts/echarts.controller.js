(function() {
	'use strict';

	angular.module('custom', [])
	.controller('echartsCtrl',echartsCtrl);
	
	echartsCtrl.$inject = ['$rootScope', '$scope', '$state', '$stateParams', '$timeout',
							'toaster', 'SweetAlert', 'ngDialog', 'serverHttpMethod'];

	function echartsCtrl($rootScope, $scope, $state, $stateParams, $timeout,
						toaster, SweetAlert, ngDialog, httpMethod) {
		
		var self = this;
		
		getCommList();
		
		//get comm list 
		function getCommList() {
			
			var url = 'server/community.json';
			
			httpMethod.getHttp(url, null, getCommListOnSuccess, getCommListOnError)

			function getCommListOnSuccess(data, status, headers, config) {
				if(data.statusCode == '200') {
					$scope.commList = data.data;
					giveMsg($scope.commList);
				}else{
				toaster.pop('error', "操作失败", data.message);
				}
			};

			function getCommListOnError(data, status, headers, config) {
				toaster.pop('error', "操作失败", '请稍后再试');
			};
		}
		function giveMsg(data){
		//条形图
		self.ebar = {
			title: {
				text: "小区信息统计图"
			},
			color: ['#8bb8f1','#43d967', '#51c6ea', '#ffab5e', '#f47f7f','#58ceb1',  '#f763eb', '#9289ca','#fbe164'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    legend: {
		        data:['入住数', "剩余房间",'车位数', '车辆数']
		    },
			grid: {
			    left: '2%',
			    right: '2%',
			    bottom: '2%',
			    containLabel: true
			}, 
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : function(){
							var its = [];
							for(var i = 0; i < data.length; i ++){
								its.push(data[i].commName);
							}
							return its;
						}()
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : function(){
						var it = {},
							its = [];
							//入住数(不包含租户)输出
							it = {name: "入住数", 
								  type:'bar', 
								  stack: '房间', 
          			  		      barWidth : 25,
          			  		      itemStyle: {
          			  		      	normal: {
          			  		      		barBorderRadius: 4
          			  		      	}
          			  		      },
          			  		      label : {
          			  		      	normal: {
	          			  		      	show: true, 
	          			  		      	position: 'insideTop'},
          			  		      	},
								  data: function(){
								var c = [];
								for(var i = 0; i < data.length; i ++ ) {
									c.push((data[i].houseOwenrCount));
								}
								return c;
							}()};
							its.push(it);
							//剩余房间输出
							it = {name: "剩余房间", 
								  type:'bar', 
								  stack: '房间', 
          			  		      barWidth : 25,
          			  		      itemStyle: {
          			  		      	normal: {
          			  		      		barBorderRadius: 4
          			  		      	}
          			  		      },
          			  		      label : {
          			  		      	normal: {
	          			  		      	show: true, 
	          			  		      	position: 'insideTop'
	          			  		      	},
          			  		      	},
								  data: function(){
								var c = [];
								for(var i = 0; i < data.length; i ++ ) {
									c.push((data[i].nullHouseCount));
								}
								return c;
							}()};
							its.push(it);
							//车位数输出
							it = {name: "车位数", 
								  type:'bar', 
								  stack: '车位', 
          			  		      barWidth : 18,
          			  		      itemStyle: {
          			  		      	normal: {
          			  		      		barBorderRadius: 2
          			  		      	}
          			  		      },
          			  		      label : {
          			  		      	normal: {
	          			  		      	show: true, 
	          			  		      	position: 'insideTop',
          			  		      		textStyle: {
          			  		      			fontSize: 10
          			  		      		}
          			  		      		},
          			  		      	},
								  data: function(){
								var c = [];
								for(var i = 0; i < data.length; i ++ ) {
									c.push(data[i].parkingCount);
								}
								return c;
							}()};
							its.push(it);
							//车辆数输出
							it = {name: "车辆数", 
								  type:'bar', 
								  stack: '车辆', 
          			  		      barWidth : 18,
          			  		      itemStyle: {
          			  		      	normal: {
          			  		      		barBorderRadius: 2
          			  		      	}
          			  		      },
          			  		      label : {
          			  		      	normal: {
	          			  		      	show: true, 
	          			  		      	position: 'insideTop',
          			  		      		textStyle: {
          			  		      			fontSize: 10
          			  		      		}
          			  		      	},
          			  		      	},
								  data: function(){
								var c = [];
								for(var i = 0; i < data.length; i ++ ) {
									c.push(data[i].carCount);
								}
								return c;
							}()};
							its.push(it);
							return its;
		    }()
		};
		//圆饼图
		self.epie = {
				title: {
					text: '小区住房占比',
					x: 'center'
				},
				color: ['#8bb8f1','#43d967', '#51c6ea', '#ffab5e', '#f47f7f','#58ceb1',  '#f763eb', '#9289ca','#fbe164'],
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					orient: 'vertical',
					x: 'left',
					data: function(){
							var its = [];
							for(var i = 0; i < data.length; i ++){
								its.push(data[i].commName);
							}
							return its;
						}()
				},
				toolbox: {
					show: true,
					feature: {
						mark: { show: true },
						dataView: { show: true, readOnly: false },
						magicType: {
							show: true,
							type: ['pie', 'funnel'],
							option: {
								funnel: {
									x: '25%',
									width: '50%',
									funnelAlign: 'left',
									max: 1548
								}
							}
						},
						restore: { show: true },
						saveAsImage: { show: true }
					}
				},
				calculable: true,
				series: [{
					name: '住户数量',
					type: 'pie',
					radius: '55%',
					center: ['50%', '60%'],
					data: function(){
						var it = {},
							its = [];
						for(var i = 0; i < data.length; i ++ ) {
							it = { name: data[i].commName, value: data[i].houseOwenrCount };
							its.push(it);
						};
							return its;
					}()
				}]
			};
		self.epie2 = {
				title: {
					text: '小区车辆占比',
					x: 'center'
				},
				color: ['#8bb8f1','#43d967', '#51c6ea', '#ffab5e', '#f47f7f','#58ceb1',  '#f763eb', '#9289ca','#fbe164'],
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					orient: 'vertical',
					x: 'left',
					data: function(){
							var its = [];
							for(var i = 0; i < data.length; i ++){
								its.push(data[i].commName);
							}
							return its;
						}()
				},
				toolbox: {
					show: true,
					feature: {
						mark: { show: true },
						dataView: { show: true, readOnly: false },
						magicType: {
							show: true,
							type: ['pie', 'funnel'],
							option: {
								funnel: {
									x: '25%',
									width: '50%',
									funnelAlign: 'left',
									max: 1548
								}
							}
						},
						restore: { show: true },
						saveAsImage: { show: true }
					}
				},
				calculable: true,
				series: [{
					name: '住房数量',
					type: 'pie',
					radius: '55%',
					center: ['50%', '60%'],
					data: function(){
						var it = {},
							its = [];
						for(var i = 0; i < data.length; i ++ ) {
							it = { name: data[i].commName, value: data[i].carCount };
							its.push(it);
						};
							return its;
					}()
				}]
			};
		}
	}

})();
