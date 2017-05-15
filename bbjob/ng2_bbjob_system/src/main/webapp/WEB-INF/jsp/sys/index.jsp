<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib uri="http://bbjob.zj.com/jsp/prop" prefix="p"  %>
<!DOCTYPE html>
<html lang="en" data-ng-app="custom">

<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="{{app.description}}">
   <meta name="keywords" content="app, responsive, angular, bootstrap, dashboard, admin">
   <title data-ng-bind="::pageTitle()"></title>
   <!-- Bootstrap styles-->
   <link rel="stylesheet" href="app/css/bootstrap.css" data-ng-if="!app.layout.isRTL">
   <link rel="stylesheet" href="app/css/bootstrap-rtl.css" data-ng-if="app.layout.isRTL">
   <!-- Application styles-->
   <link rel="stylesheet" href="app/css/app.css" data-ng-if="!app.layout.isRTL">
   <link rel="stylesheet" href="app/css/app-rtl.css" data-ng-if="app.layout.isRTL">
   <!-- Themes-->
   <link rel="stylesheet" type="text/css" href="app/css/theme-e.css"/>
   <link rel="stylesheet" ng-href="{{app.layout.theme}}" data-ng-if="app.layout.theme">
   <link rel="stylesheet" type="text/css" href="app/css/style.css"/>
   <link rel="stylesheet" type="text/css" href="app/css/style.adv.css"/>
</head>

<body data-ng-class="{ 'layout-fixed' : app.layout.isFixed, 'aside-collapsed' : app.layout.isCollapsed, 'layout-boxed' : app.layout.isBoxed, 'layout-fs': app.useFullLayout, 'hidden-footer': app.hiddenFooter, 'layout-h': app.layout.horizontal, 'aside-float': app.layout.isFloat, 'offsidebar-open': app.offsidebarOpen, 'aside-toggled': app.asideToggled}">
   <div data-preloader></div>
   <div data-ui-view="" data-autoscroll="false" class="wrapper"></div>
   <script src="js/base.js"></script>
   <!--<script src="app/js/app.js"></script>-->
   <!--app.module-->
   <script src="js/modules/app.module.js" type="text/javascript" charset="utf-8"></script>
   <!--app.core-->
   <script src="js/modules/core/core.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/core/core.config.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/core/core.constants.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/core/core.run.js" type="text/javascript" charset="utf-8"></script>
   <!--app.router-->
   <script src="js/modules/routes/routes.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/routes/route-helpers.provider.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/routes/routes.config.js" type="text/javascript" charset="utf-8"></script>
   <!--app.navsearch-->
   <script src="js/modules/navsearch/navsearch.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/navsearch/navsearch.directive.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/navsearch/navsearch.service.js" type="text/javascript" charset="utf-8"></script>
   <!--app.topnav-->
   <script src="js/modules/topnav/topnav.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/topnav/topNav.directive.js" type="text/javascript" charset="utf-8"></script>
   
   <script src="js/modules/topnav/login.log.service.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/topnav/topnav.loginlog.controller.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/topnav/topnav.userblock.controller.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/topnav/topnav.controller.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/topnav/user.service.js" type="text/javascript" charset="utf-8"></script>
   <!--app.sidebar-->
   <script src="js/modules/sidebar/sidebar.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/sidebar/sidebar.controller.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/sidebar/sidebar.directive.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/sidebar/sidebar.userblock.controller.js" type="text/javascript" charset="utf-8"></script>
   <!--app.preloader-->
   <script src="js/modules/preloader/preloader.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/preloader/preloader.directive.js" type="text/javascript" charset="utf-8"></script>
   <!--app.loadingbar-->
   <script src="js/modules/loadingbar/loadingbar.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/loadingbar/loadingbar.config.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/loadingbar/loadingbar.run.js" type="text/javascript" charset="utf-8"></script>
   <!--app.translate-->
   <script src="js/modules/translate/translate.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/translate/translate.config.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/translate/translate.run.js" type="text/javascript" charset="utf-8"></script>
   <!--app.settings-->
   <script src="js/modules/settings/settings.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/settings/settings.run.js" type="text/javascript" charset="utf-8"></script>
   <!--app.utils-->
   <script src="js/modules/utils/utils.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/utils/utils.service.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/utils/menu.service.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/utils/util.http.service.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/utils/http-auth-interceptor.js" type="text/javascript" charset="utf-8"></script>
   <!--lazyload-->
   <script src="js/modules/lazyload/lazyload.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/lazyload/lazyload.config.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/lazyload/lazyload.constants.js" type="text/javascript" charset="utf-8"></script>
   <!--colors-->
   <script src="js/modules/colors/colors.module.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/colors/colors.service.js" type="text/javascript" charset="utf-8"></script>
   <script src="js/modules/colors/colors.contant.js" type="text/javascript" charset="utf-8"></script>
   
   <!--httpMethod-->
   <script src="js/modules/server/server.httpMethod.js" type="text/javascript" charset="utf-8"></script>
   
   <script src="vendor/flatpickr/js/flatpickr.js"></script> 
   
   <!-- mock -->
   <script src="mockjs/mock.js"></script>
   <script src="mockjs/mockTpls.js"></script>
   
   
</body>

</html>