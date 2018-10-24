<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/select2.css" rel="stylesheet" type="text/css" />
</head>
<style>
	.formControls .form-label{text-align:left;}
</style>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-2">用户名：</label>
			<div class="formControls col-2">
				<input id="name" type="text" class="input-text" placeholder="请输入用户名">
			</div>
			<label class="form-label col-2">员工工号：</label>
			<div class="formControls col-2">
				<input id="sn" type="text" class="input-text" placeholder="请输入工号">
			</div>
			<label class="form-label col-2">电话：</label>
			<div class="formControls col-2">
				<input id="phone" type="text" class="input-text" placeholder="请输入电话">
			</div>
		</div>
		<button id="search_btn" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton classname="btn btn-primary radius" onclick="addUser();" btnSn="0101010301" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateUser();" btnSn="0101010302" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton classname="btn btn-success radius" onclick="setUserRoleLayer();" btnSn="0101010306" btnName="用户授权" icon="&#xe6dc;"></jc:operationButton>
			<jc:operationButton classname="btn btn-success radius" onclick="setUserEnable();" btnSn="0101010303" btnName="启用" icon="&#xe6dc;"></jc:operationButton>
			<jc:operationButton classname="btn btn-default radius" onclick="setUserDisable();" btnSn="0101010304" btnName="禁用" icon="&#xe6de;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="resetPassword();" btnSn="0101010305" btnName="重置密码" icon="&#xe6df;"></jc:operationButton>
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="userList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>
					<th width="7%">用户名</th>
					<th width="7%">员工工号</th>
					<th width="7%">所属区域机构</th>
					<th width="7%">所属行政机构</th>
					<th width="10%">电话</th>
					<th width="6%">邮箱</th>
					<th width="5%">类型</th>
					<th width="7%">登录名</th>
					<th width="7%">最后一次登录时间</th>
					<th width="5%">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<%--用户添加 --%>
<div id="addUser" class="pd-20" style="display:none;">
	<form action="${contextPath}/user/add" method="post" class="form form-horizontal" id="addUserForm">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>用户名：</label>
			<div class="formControls col-3">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名字" datatype="zh2-20" errormsg="请输入2-20位汉字">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>员工工号：</label>
			<div class="formControls col-3">
				<input id="sn" name="sn" type="text" class="input-text" placeholder="请输入工号" datatype="n3-10,checkSn" errormsg="请输入3-10位数字">
			</div>
			
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>电话：</label>
			<div class="formControls col-3">
				<input id="phone" name="phone" type="text" class="input-text" placeholder="请输入电话" datatype="m" errormsg="请输入正确的手机号码">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>登录名：</label>
			<div class="formControls col-3">
				<input id="loginName" name="systemUserAuth.loginName" type="text" class="input-text" placeholder="请输入登录名" datatype="lm2-4,checkLoginName" errormsg="请输入4-12数字或英文">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">密码(默认111111)：</label>
			<div class="formControls col-3">
				<input id="password" name="systemUserAuth.password" type="password" class="input-text" placeholder="111111" value="111111" readonly="readonly">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>邮箱：</label>
			<div class="formControls col-3">
				<input id="email" name="email" type="text" class="input-text" placeholder="请输入email" datatype="e" errormsg="请输入正确的邮箱">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">类型：</label>
			<div class="formControls col-3">
				<select id="userType" name="userType" style="width: 100%;" class="input-text" onchange="checkType(this.value)">
					<option value="1">系统管理员</option>
					<option value="2">机构操作员</option>
				</select>
			</div>
			<label class="form-label col-3 adminType">行政or区域机构：</label>
			<div class="formControls col-3 adminType">
				<select id="adminType" style="width: 100%;" class="input-text" onchange="organizationType(this.value)">
					<option value="1">区域机构</option>
					<option value="2">行政机构</option>
				</select>
			</div>
		</div>
		<div class="row cl" id="div_organizationId" style="display: none;">
			<label class="form-label col-3">所属区域机构：</label>
			<div class="formControls col-3">
				<input id="areaId" name="areaId" type="hidden"  value="1">
				<select id="areaId_1" name="areaId_1" class="input-text" >
					<option value="1">崇州市</option>
				</select> 
			</div>
			<div class="formControls col-3">
				<select id="areaId_2" name="areaId_2" class="input-text" datatype="*" onchange="changeOrg('addUser',2,this.value)">
				</select>
			</div>
			<div class="formControls col-3">
				<select id="areaId_3" name="areaId_3" class="input-text" onchange="changeOrg('addUser',3,this.value)">
				</select>
			</div>
		</div>
		<div class="row cl" id="div_organizationType" style="display: none;">
			<label class="form-label col-3">所属行政机构：</label>
			<div class="formControls col-3">
				<select id="organizationId" name="organizationId" datatype="*" class="input-text">
				</select>
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
<%--用户编辑 --%>
<div id="updateUser" class="pd-20" style="display:none;">
	<form action="${contextPath}/user/update" method="post" class="form form-horizontal" id="updateUserForm">
		<input id="id" name="id" type="hidden">
		<input id="systemUserAuthId" name="systemUserAuth.id" type="hidden">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>用户名：</label>
			<div class="formControls col-3">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名字" datatype="zh2-20" errormsg="请输入2-20位汉字">
			</div>
			<label class="form-label col-3">员工工号：</label>
			<div class="formControls col-3">
				<input id="sn"  type="text" class="input-text"  readonly>
			</div>
			
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>电话：</label>
			<div class="formControls col-3">
				<input id="phone" name="phone" type="text" class="input-text" placeholder="请输入电话" datatype="m" errormsg="请输入正确的手机号码">
			</div>
			<label class="form-label col-3">登录名：</label>
			<div class="formControls col-3">
				<input id="loginName" name="systemUserAuth.loginName" type="text" class="input-text"  readonly>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">密码(默认111111)：</label>
			<div class="formControls col-3">
				<input id="password" name="systemUserAuth.password" type="password" class="input-text" placeholder="不修改不输入" datatype="*6-10" errormsg="请输入正确密码" ignore="ignore">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>邮箱：</label>
			<div class="formControls col-3">
				<input id="email" name="email" type="text" class="input-text" placeholder="请输入email" datatype="e" errormsg="请输入正确的邮箱">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3">类型：</label>
			<div class="formControls col-3">
				<select id="userType" name="userType" style="width: 100%;color:#bebebe" class="input-text" datatype="*" onchange="checkType(this.value)" disabled="disabled">
					<option value="1">系统管理员</option>
					<option value="2">业务操作员</option>
				</select>
			</div>
		</div>
		<div class="row cl" id="div_organizationId" style="display: none">
			<label class="form-label col-3">所属区域机构：</label>
			<div class="formControls col-3">
				<input id="areaId" name="areaId" type="hidden"  value="1">
				<select id="areaId_1" name="areaId_1" class="input-text" disabled="disabled" style="color:#bebebe">
					<option value="1">崇州市</option>
				</select> 
			</div>
			<div class="formControls col-3">
				<select id="areaId_2" name="areaId_2" class="input-text" datatype="*" onchange="changeOrg('addUser',2,this.value)" disabled="disabled" style="color:#bebebe">
				</select>
			</div>
			<div class="formControls col-3">
				<select id="areaId_3" name="areaId_3" class="input-text" onchange="changeOrg('addUser',3,this.value)" disabled="disabled" style="color:#bebebe">
				</select>
			</div>
		</div>
		<div class="row cl" id="div_organizationType" style="display: none;">
			<label class="form-label col-3">所属行政机构：</label>
			<div class="formControls col-3">
				<select id="organizationId" name="organizationId" class="input-text" disabled="disabled" style="color:#bebebe">
				</select>
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
<%--用户详情 --%>
<div id="userInfo" class="pd-20" style="display:none;">
	<div class="form form-horizontal" style="border:1px solid #BEBEBE;">
	<div class="row cl">
		<label class="form-label col-3">用户名：</label>
		<div class="formControls col-3">
			<label id="name" class="form-label"></label>
		</div>
		<label class="form-label col-3">员工工号：</label>
		<div class="formControls col-3">
			<label id="sn" class="form-label"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-3">电话：</label>
		<div class="formControls col-3">
			<label id="phone" class="form-label"></label>
		</div>
		<label class="form-label col-3">登录名：</label>
		<div class="formControls col-3">
			<label id="loginName" class="form-label"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-3">邮箱：</label>
		<div class="formControls col-3">
			<label id="email" class="form-label"></label>
		</div>
		<label class="form-label col-3">类型：</label>
		<div class="formControls col-3">
			<label id="userType" class="form-label"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-3">创建时间：</label>
			<div class="formControls col-3">
				<label id="systemUserAuthCreateDate" class="form-label"></label>
			</div>
		<label class="form-label col-3">最后修改密码时间：</label>
		<div class="formControls col-3">
			<label id="systemUserAuthUpdatePassDate" class="form-label"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-3">状态：</label>
		<div class="formControls col-3">
			<label id="systemUserAuthStatus" class="form-label"></label>
		</div>
		<label class="form-label col-3">最后登录时间：</label>
		<div class="formControls col-3">
			<label id="systemUserAuthLoginDate" class="form-label"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-3">所属行政机构：</label>
		<div class="formControls col-3">
			<label id="organizationName" class="form-label"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-3">所属区域机构：</label>
		<div class="formControls col-3">
			<label id="administrativeManagement" class="form-label"></label>
		</div>
	</div>
	</div>
	<div class="row cl">
		<div class="col-offset-11">
			<button onClick="layer_close();" class="btn btn-default radius mt-20" type="button">关闭</button>
		</div>
	</div>
</div>
<%--用户授权 --%>
<div id="userRole" class="pd-20" style="display:none;">
	<input id="authId" type="hidden" value="">
	<table class="table table-border table-bordered table-hover table-bg" style="margin-top:20px;" >
		<thead>
			<tr>
				<th scope="col" colspan="6">角色列表</th>
			</tr>
			<tr class="text-c">
				<th width="5%"></th>
				<th width="15%">角色名</th>
				<th width="15%">标识</th>
				<th width="65%">描述</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<br>
	<br>
	<div class="row cl">
		<div class="col-offset-9">
			<button id="form_submit" onClick="setUserRole();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
			<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
		</div>
	</div>
</div>

<%--重置密码 --%>
<div id="resetPassword" class="pd-20" style="display:none;">
	<form action="${contextPath}/user/resetPassword" method="post" class="form form-horizontal" id="resetPasswordForm">
		<input id="id" name="id" type="hidden">
		<input id="systemUserAuthId" name="systemUserAuth.id" type="hidden">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>用户名：</label>
			<div class="formControls col-7">
				<label class="form-label col-4.5" id="name"></label>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>登录名：</label>
			<div class="formControls col-7">
				<label class="form-label col-3.5" id="loginName"></label>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>密码：</label>
			<div class="formControls col-7">
				<input id="password" name="systemUserAuth.password" type="password" class="input-text" placeholder="必填" datatype="*6-10" errormsg="密码长度6-10位" required>
			</div>
		</div>
		<div class="row cl">
			<div class="col-offset-4">
				<button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/select2.full.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/user/user.js?v=${appVerDate}"></script>
</body>
</html>