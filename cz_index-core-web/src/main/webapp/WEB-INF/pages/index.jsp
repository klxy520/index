<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="webkit" name="renderer" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/lib/html5.js"></script>
<title>健康崇州-卡管平台</title>
<style type="text/css">
#taskItems li{cursor:pointer;color :red;}
.index_affiche{
	border-top:1px solid #19a97b;
    height: 39px;
    line-height: 39px;
}
#readNotice{
	position: relative;
    height: 100%;
    width: 92%;
    margin: auto;
}
.col-lg-offset-7{
	float:none;
}
.index_establish{
	width:50%;
	float:left;
}
.index_affiche_con{
	min-height:215px;
	text-indent: 2em;
}
</style>
</head>
<body>
<header class="Hui-header cl"> <a class="Hui-logo l" title="健康崇州-卡管平台" href="${contextPath}">健康崇州-卡管平台</a> <span class="Hui-subtitle l"></span>
	<input id="userId" name="userId" value="${session_user.id}" type="hidden">
	
	<ul class="Hui-userbar">
			<li>
				<!-- 公告 -->
				<div class="noticeInfo" id="noticeInfo" >
					<span class="noticeNum" id="taskCount"></span><i class="Hui-iconfont notice">&#xe62f;</i>
					<div class="list_lh">
						<ul id="taskItems" >
							<li>
								
							</li>
						</ul>
					</div>
				</div>
			</li>
		<c:choose>
			<c:when test="${not empty session_user_org }"> 
				<li>所属行政机构：</li>
				 <li class="dropDown dropDown_hover">${session_user_org }</li>
			</c:when>
			<c:when test="${not empty session_user_area }"> 
				<li>所属区域机构：</li>
				 <li class="dropDown dropDown_hover">${session_user_area }</li>
			</c:when>
			<c:otherwise>
				<li>所属区域/行政机构：</li>
				<li>无</li>
			</c:otherwise>
		</c:choose>
		<li>&nbsp;|&nbsp;登录用户：</li>
		<li class="dropDown dropDown_hover index_userName">
			<a href="#" class="dropDown_A">
				${session_user.name }
				<i class="Hui-iconfont">&#xe6d5;</i>
			</a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a onclick="updateUser(${session_user.id });" href="javascript:;">个人信息管理</a></li>
				<li><a href="${contextPath}/loginPage">切换账户</a></li>
				<li><a href="${contextPath}/j_security_logout">退出</a></li>
			</ul>
		</li>
	</ul>
	<a aria-hidden="false" class="Hui-nav-toggle" href="#"></a> </header>	
	<aside class="Hui-aside">
		<input runat="server" id="divScrollValue" type="hidden" value="" />
		<div class="menu_dropdown bk_2">
			<c:if test="${not empty menuList }"> 
			<c:forEach var="menu" items="${menuList }">
				<dl id="menu-article">
					<dt class="${menu.defaultOpen==1?'selected':''} "><i class="Hui-iconfont">${menu.iconUrl }</i>&nbsp;${menu.name }<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd style="${menu.defaultOpen==1?'display: block;':''}">
						<c:if test="${not empty menu.subMenuList }">
							<ul>
								<c:forEach var="subMenu" items="${menu.subMenuList }">
									<li><a id="${subMenu.sn}" _href="${contextPath}${subMenu.targetUrl}" href="javascript:void(0)"  title="${subMenu.description}">${subMenu.name }</a></li>
								</c:forEach>
							</ul>
						</c:if>
					</dd>
				</dl>
			</c:forEach>
		</c:if>
			；
			
		</div>
	</aside>
	<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
	<section class="Hui-article-box">
		<div id="Hui-tabNav" class="Hui-tabNav">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active"><span title="我的桌面" data-href="${contextPath}/welcome.html">我的桌面</span><em></em></li>
				</ul>
			</div>
			<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
		</div>
		 <div id="iframe_box" class="Hui-article">
			<div class="show_iframe">
				<div style="display:none" class="loading"></div>
				<iframe scrolling="yes" frameborder="0" src="${contextPath}/welcome.html"></iframe>
			</div>
		</div> 
	</section>
	
	
	<%--用户编辑 --%>
<div id="updateUser" class="pd-20" style="display:none;">
	<form action="${contextPath}/user/updateUserForLogin" method="post" class="form form-horizontal" id="updateUserForm">
		<input id="id" name="id" type="hidden">
		<input id="systemUserAuthId" name="systemUserAuth.id" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>真实名字：</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名字" datatype="zh2-20" errormsg="请输入2-20位汉字">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>联系电话：</label>
			<div class="formControls col-2">
				<input id="phone" name="phone" type="text" class="input-text" placeholder="请输入电话" datatype="m" errormsg="请输入正确的手机号码">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>email：</label>
			<div class="formControls col-2">
				<input id="email" name="email" type="text" class="input-text" placeholder="请输入email" datatype="e" errormsg="请输入正确的邮箱">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>登录名：</label>
			<div class="formControls col-2">
				<input id="loginName" name="systemUserAuth.loginName" type="text" class="input-text" placeholder="请输入登录名" readonly="readonly">
			</div>
			<label class="form-label col-2">密码：</label>
			<div class="formControls col-2">
				<input id="password" name="systemUserAuth.password" type="password" class="input-text" placeholder="不修改不输入" datatype="empty|*6-10" errormsg="密码长度6-10位">
			</div>
		</div>
		<div class="row cl">
			<div class="col-offset-9">
				<button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>

<%--重置密码 --%>
<div id="passwordChange" class="pd-20" style="display:none;">
	<form action="${contextPath}/user/passwordChange" method="post" class="form form-horizontal" id="passwordChangeForm">
		<input id="systemUserAuthId" name="systemUserAuthId" type="hidden"> 
		<label class="form-label col-9" style="color:red;font-size:17px;" id="passwordChangeName"></label>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>登录名：</label>
			<div class="formControls col-7">
				<label class="form-label col-3.5" id="loginName"></label>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>旧密码：</label>
			<div class="formControls col-7">
				<input id="oldPassword" name="oldPassword" type="password" class="input-text" placeholder="请输入旧密码" datatype="*6-10" errormsg="密码长度6-10位" required>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>新密码：</label>
			<div class="formControls col-7">
				<input id="newPassword" name="newPassword" type="password" class="input-text" placeholder="请输入新密码" datatype="*6-10" errormsg="密码长度6-10位" required>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>确认密码：</label>
			<div class="formControls col-7">
				<input id="confirmPassword" name="confirmPassword" type="password" class="input-text" placeholder="请输入新密码" datatype="*6-10" errormsg="密码长度6-10位" required>
			</div>
		</div>
		<div class="row cl">
			<div class="col-offset-5">
				<button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
			</div>
		</div>
	</form>
</div>
<%--查看公告 --%>
<div id="readNotice" style="display:none;">
	<div class="row">	
			<h3 id="title"  class="form-label text-c">标题ti</h3>
	</div>
	<div class="row index_affiche_con">	
			<label id="content"  class="form-label"></label>
	</div>
	
	<div class="index_affiche">
		<div class="row index_establish">
			<div  class="" >
				<label >创建人：</label>
				<label id="creatorName" ></label>
			</div>
		</div>
		<div  class="row index_establish">
			<div class="">
				<label >创建时间：</label>
				<label id="createDate" ></label>
			</div>
		</div>
	</div>
	
</div>


<%@ include file="commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/index/index.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/scroll.js?v=${appVerDate}"></script>
<script type="text/javascript">
	var nowIsIndex = "index";
	fn_GetDictionary();
</script>
</body>
</html>