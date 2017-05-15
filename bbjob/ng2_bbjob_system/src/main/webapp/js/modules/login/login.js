//自适应验证宽度 验证图固定100
getverificationWidth();
window.onresize = function(){
	
	getverificationWidth();
}
function getverificationWidth(){

	 if ( (navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0) ){
			var its = ['userName', 'password', 'verification'];
			for(var i = 0; i < its.length-1; i ++){
			  var is = document.getElementById(its[i]);
			  is.style.width = 300 + 'px';
			}
			
		  var is3 = document.getElementById(its[2]);
		  is3.style.width = 200 + 'px';
		  
		  var cont = document.getElementsByClassName('login_container');
		  cont[0].style.paddingTop = '100px';
		  
	 }else{
	var verification = document.getElementById('verification');
	var verificationImg = document.getElementById('verificationImg');
	var userName = document.getElementById('userName');
	var width = userName.offsetWidth - verificationImg.offsetWidth;
	verification.style.width = width.toString() + 'px';
	 }
}
//canvas阴影
var canvas = document.getElementById('loginShadow');
var ctx = canvas.getContext('2d');
ctx.fillStyle='rgba(0, 0, 0, 0.05)';
ctx.moveTo(0, 0);
ctx.lineTo(370, 0);
ctx.lineTo(550, 180);
ctx.lineTo(550, 530);
ctx.lineTo(180, 530);
ctx.lineTo(0, 380);
ctx.closePath();
ctx.fill();

//更新验证码
getverificationImg();
function getverificationImg(){
	
}
$('#verificationImg').click(function(){
	getverificationImg();
})

//submit
//$('#login').click(loginSubmit());

function loginSubmit(){
	var userName = $('#userName').val(),
	password = $('#password').val(),
	verification = $('#verification').val();
if(!userName.length){
	$.toaster({ title : '错误', priority : 'danger', message : '请输入用户名！' });
	return false; 
}
if(!password.length){
	$.toaster({ title : '错误', priority : 'danger', message : '请输入密码！' });
	return false; 
}
if(!verification.length){
	$.toaster({ title : '错误', priority : 'danger', message : '请输入验证码！' });
	return false;; 
}
//提交
return true;
}
//hintMsg('show', '登录失败')
//hintMsg('hide')
function hintMsg(type, msg){
	if(type=='show'){
		if($("#hintCont").length){ $("#hintCont").remove();}
		var temp = '<span class="text-danger" id="hintCont">' + msg + '</span>';
		$("#hintHolder").append(temp);
		return;
	}
	if(type=='hide'){
		$("#hintCont").remove();
		return;
	}
}