<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib uri="http://bbjob.zj.com/jsp/prop" prefix="p"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" data-ng-app="custom">

<head>
   <meta charset="utf-8">
   <meta name="renderer" content="webkit">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <title data-ng-bind="::pageTitle()">后台管理 - 登录</title>
   <!-- Bootstrap styles-->
   <link rel="stylesheet" href="${p:get('system.res')}app/css/bootstrap.css">
   <!-- Themes-->
   <link rel="stylesheet" type="text/css" href="${p:get('system.res')}app/css/theme-e.css"/>
   <link rel="stylesheet" ng-href="{{app.layout.theme}}" data-ng-if="app.layout.theme">
   <link rel="stylesheet" type="text/css" href="${p:get('system.res')}app/css/login.css"/>
	<script src="${p:get('system.res')}vendor/jquery/dist/jquery.js"></script>   
	<script src="${p:get('system.res')}vendor/jquery.toaster/jquery.toaster.js"></script>   
	<script src="${p:get('system.res')}vendor/bootstrap/dist/js/bootstrap.js"></script>   
	
</head>

<body>
	<div class="login_bg" style="margin:0">
	</div>
		<div class="login_container">
			<div class="login_content">
				<div class="login_img">	</div>
				<div class="login_heading">
					用户登录
				</div>
				<form action="sys/login" method="post">
					<div class="login_body" id="login_body">
						<input type="text" id="userName" name="username" placeholder="请输入用户名">
						<input type="password" id="password" name="password" placeholder="请输入密码">
						<input type="text" id="verification" name="verifyCode" placeholder="请输入验证码">
						<span id="verificationImg">
							<img alt="" src="${p:get('system.ctx')}captcha" onclick="javascript:this.src = '${p:get('system.ctx')}captcha?time=' + new Date().getTime();">
						</span>
						<div class="clearfix"></div>
						<div id="hintHolder"></div>
						<button id="login" type="submit">登录</button>
					</div>
				</form>
			</div>
			<canvas id="loginShadow" width="550px" height="530px"></canvas>
		</div>
</body>
<script>
 </script>
<script src="${p:get('system.res')}js/modules/login/login.js">
</script>
<c:if test="${errInfo!=null}">
<script type="text/javascript">
	hintMsg('show', '${errInfo}')
</script>
</c:if>
</html>