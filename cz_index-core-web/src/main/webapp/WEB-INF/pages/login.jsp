<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>健康崇州-卡管平台</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/common.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/login.css">
<!--[if lt IE 9]>
   <style type="text/css">
   button {
       background:#000;
       filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#7fffffff,endColorstr=#7fffffff);
       zoom: 1;
    }
    </style>
<![endif]-->
</head>
<body>
<img src='${contextPath}/images/bg.jpg' class="login_bg">
<div class="login_box">
<p class="login_tit">健康崇州-卡管平台</p>
<div id="loginform">
	<div id="mainlogin">
		<p style="font-size:18px;text-align:center;margin-bottom:10px;color:#fff;"><b>卡管平台登录</b></p>
		<p id="errorMsg" style="display:none;">${errorFive}</p>
		<form action="${contextPath}/j_spring_security_check" method="post" id="loginForm">
			<input id="j_username" name="j_username" type="text" placeholder="请输入用户名" value="" class="user">
			<input id="j_password" name="j_password" type="password" placeholder="请输入密码" value="" class="pass">
			<p class='error nullError'></p>
			<div class="login_btn"><button type="submit">登&nbsp;&nbsp;录</button></div>
		</form>
	</div>
</div>
</div>
<%@ include file="commons/comm_const_js.jsp"%> 
<script type="text/javascript">
// session过期 登录页面嵌套问题处理
if (window != top){
    top.location.href = location.href;
}
$(function(){
	var errorMsg = $("#errorMsg").text();
	if(errorMsg && errorMsg.length>0){
		$(".nullError").text(errorMsg).show();
	}else{
		$(".nullError").hide();
	}
	$(".login_btn").click(function(){
		$(".error").hide();
		if($(".user").val() == "" || $(".pass").val() == ""){
			$(".nullError").text("用户名或密码不能为空！").show();
			return false;
		}
	})
})
</script>
</body>
</html>
