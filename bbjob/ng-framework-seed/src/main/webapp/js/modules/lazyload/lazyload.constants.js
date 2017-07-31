(function() {
	'use strict';

	angular
			.module('app.lazyload')
			.constant(
					'APP_REQUIRES',
					{
						// jQuery based and standalone scripts
						scripts : {
							'app.css' : [ 'vendor/app/css/app.css' ],
							'whirl' : [ 'vendor/whirl/dist/whirl.css' ],
							'classyloader' : [ 'vendor/jquery-classyloader/js/jquery.classyloader.min.js' ],
							'animo' : [ 'vendor/animo.js/animo.js' ],
							'sparklines' : [ 'vendor/sparkline/index.js' ],
							'modernizr' : [ 'vendor/modernizr/modernizr.custom.js' ],
							'icons' : [
									'vendor/fontawesome/css/font-awesome.min.css',
									'vendor/simple-line-icons/css/simple-line-icons.css' ],
							'toaster' : [
									'vendor/angularjs-toaster/toaster.css',// 提示
									'vendor/angularjs-toaster/toaster.js' ],
							'loaders.css' : [ 'vendor/loaders.css/loaders.css' ], // 过度动画相关
							'spinkit' : [ 'vendor/spinkit/css/spinkit.css' ],
							'flot-chart' : [ 'vendor/Flot/jquery.flot.js',
									         'vendor/Flot/angular-flot.js' ],
							'flot-chart-plugins' : [
									'vendor/flot.tooltip/js/jquery.flot.tooltip.min.js',
									'vendor/Flot/jquery.flot.resize.js',
									'vendor/Flot/jquery.flot.pie.js',
									'vendor/Flot/jquery.flot.time.js',
									'vendor/Flot/jquery.flot.categories.js',
									'vendor/flot-spline/js/jquery.flot.spline.min.js' ],
							'filestyle' : [ 'vendor/bootstrap-filestyle/src/bootstrap-filestyle.js' ],
							'html.sortable' : [ 'vendor/html.sortable/js/html.sortable.js',
								'vendor/html.sortable/js/html.sortable.angular.js'
								],
								'ngDraggble' : [ 'vendor/ng-Draggable/js/ngDraggable.js'
									]
						// 过度动画相关
						},
						// Angular based script (use the right module name)
						modules : [

								// 上传
								{
									name : 'angularFileUpload',
									files : [ 'vendor/angular-file-upload/dist/angular-file-upload.js' ]
								},
								// 上传
								{
									name : 'ngFileUpload',
									files : [ 
											'vendor/ng-file-upload/ng-file-upload-shim.js',
											'vendor/ng-file-upload/ng-file-upload.js',
											'vendor/ng-file-upload/upload.js'
											]
								},
								//
								{
									name : 'ngImgCrop',
									files : [
											'vendor/ng-img-crop/compile/unminified/ng-img-crop.js',
											'vendor/ng-img-crop/compile/unminified/ng-img-crop.css' ]
								},

								// 弹出层
								{
									name : 'ngDialog',
									files : [
											'vendor/ngDialog/js/ngDialog.min.js',
											'vendor/ngDialog/css/ngDialog.min.css',
											'vendor/ngDialog/css/ngDialog-theme-default.min.css',
											'vendor/ngDialog/css/ngDialog-theme-50.min.css' ]
								},
								// 提示框 官方文档
								{
									name : 'oitozero.ngSweetAlert',
									files : [
											'vendor/sweetalert/dist/sweetalert.css',
											'vendor/sweetalert/dist/sweetalert.min.js',
											'vendor/angular-sweetalert/SweetAlert.js' ]
								},

								// 表格文档
								{
									name : 'datatables',
									files : [
											'vendor/datatables/media/css/jquery.dataTables.css',
											'vendor/datatables/media/js/jquery.dataTables.js',
											'vendor/angular-datatables/dist/angular-datatables.js' ],
									serie : true
								},
								// table
								{
									name : 'ngTable',
									files : [
											'vendor/ng-table/dist/ng-table.js',
											'vendor/ng-table/dist/ng-table.css' ]
								},
								// uiTree
								{
									name : 'uiTree',
									files : [
											'vendor/angular-ui-tree/angular-ui-tree.min.js',
											'vendor/angular-ui-tree/angular-ui-tree.min.css' ]
								},
								{
									name : 'ngTableExport',
									files : [ 'vendor/ng-table-export/ng-table-export.js' ]
								},
								// 时间控件flatpickr(主页index.jsp还有个flatpickr.js)
								{
									name : 'flatpickr',
									files : [
											'vendor/flatpickr/css/flatpickr.material_blue.min.css',
											'vendor/flatpickr/js/ng-flatpickr.js' ]
								},
								{
									name : 'paneltool',
									files : [ 'js/modules/paneltool/paneltool.js' ]
								},
								{
									name : 'echarts',
									files : [
											'vendor/echarts-master/dist/echarts.min.js',
											'vendor/echarts-master/eCharts.directive.js' ]
								},
								{
									name : 'mao.select',
									files : [
											'vendor/angular-select2-Mao2/css/select2.css',
											'vendor/angular-select2-Mao2/js/select2.js',
											'vendor/angular-select2-Mao2/select-directive/select2.directive.js' ]
								},
								{
									name : 'match.number',
									files : [ 'vendor/match-number/match-number.css' ]
								},
								// 以上内容为固定功能模块↑↑↑↑↑↑↑↑↑↑

								/** ***************************************以下具体业务模块******************************************************* */

								//echarts.controller
								{
									name: 'echarts.controller',
									files: ['js/modules/controllers/menu1/echarts/echarts.controller.js']
								},
								//ngTable.controller
								{
									name: 'ngTable.controller',
									files: ['js/modules/controllers/menu1/ngTable/ngTable.controller.js']
								}
						]
					});

})();
