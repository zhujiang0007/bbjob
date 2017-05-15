(function() {
	'use strict';

	angular
			.module('app.lazyload')
			.constant(
					'APP_REQUIRES',
					{
						// jQuery based and standalone scripts
						scripts : {
							
						},
						// Angular based script (use the right module name)
						modules : [

								

								//用户
								{
									name: 'sysuser.controller',
									files: [
									    	'js/modules/controllers/menu1/service/sysuser.service.js',
											'js/modules/controllers/menu1/user/sysuser.controller.js'
									       ]
								},
								{
									name: 'userDetail.controller',
									files: ['css/style.userDetail.css',
											'js/modules/controllers/menu1/service/sysuserfunction.service.js',
											'js/modules/controllers/menu1/service/sysfunction.service.js',
											'js/modules/controllers/menu1/service/sysrole.service.js',
											'js/modules/controllers/menu1/service/sysuserrole.service.js',
											'js/modules/controllers/menu1/service/sysuser.service.js',
											'js/modules/controllers/menu1/user/userfunctionpanel.controller.js',
									        'js/modules/controllers/menu1/user/userDetail.controller.js'
									        
										   ]
								},
								//角色
								{
									name: 'sysrole.controller',
									files: [
										'js/modules/controllers/menu1/service/sysrole.service.js',
										'js/modules/controllers/menu1/role/sysrole.controller.js'		
									]
								},
								{
									name: 'sysroleDetail.controller',
									files: [
										'js/modules/controllers/menu1/service/sysrole.service.js',
										'js/modules/controllers/menu1/service/sysfunction.service.js',
										'js/modules/controllers/menu1/service/sysrolefunction.service.js',
										'js/modules/controllers/menu1/role/rolefunctionpanel.controller.js',
										'js/modules/controllers/menu1/role/sysroleDetail.controller.js'
										]
								},
								//功能管理
								{
									name: 'syspurview.controller',
									files: [
										'js/modules/controllers/menu1/service/sysfunction.service.js',
										'js/modules/controllers/menu1/purview/functionpanel.controller.js',
										'js/modules/controllers/menu1/purview/syspurview.controller.js'
									]
								},
								{
									name: 'syspurviewDetail.controller',
									files: ['js/modules/controllers/menu1/purview/syspurviewDetail.controller.js']
								}
						]
					});

})();
