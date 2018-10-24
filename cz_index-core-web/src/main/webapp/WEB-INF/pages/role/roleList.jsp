<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
<style type="text/css">
.col-2{
	width:25%;
}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 角色管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-2" style="width:16.66667%;">角色名称：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input id="name" type="text" class="input-text" placeholder="请输入用户名">
			</div>
			<label class="form-label col-2" style="width:16.66667%;">标识：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input id="mark" type="text" class="input-text" placeholder="请输入标识">
			</div>
			<label class="form-label col-2" style="width:16.66667%;">状态：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<select id="status" name="status" class="select input-text">
					<option value="">全部</option>
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
			</div>
		</div>
		<button id="search_btn" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton classname="btn btn-primary radius" onclick="addRole();" btnSn="0101010201" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateRole();" btnSn="0101010202" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton classname="btn btn-success radius" onclick="setRoleMenuLayer();" btnSn="0101010203" btnName="角色授权" icon="&#xe6dc;"></jc:operationButton>
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="roleList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>
					<th width="7%">角色名称</th>
					<th width="10%">权限标识</th>
					<th width="50%">描述</th>
					<th width="5%">状态</th>
					<th width="5%">显示顺序</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<%--角色添加 --%>
<div id="addRole" class="pd-20" style="display:none;">
	<form action="${contextPath}/role/add" method="post" class="form form-horizontal" id="addRoleForm">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名称" datatype="zh4-20" errormsg="请输入4-20位汉字">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>权限标识：</label>
			<div class="formControls col-2">
				<input id="mark" name="mark" type="text" class="input-text" placeholder="请输入标识" datatype="s4-20" errormsg="请输入正确的标识">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>显示顺序：</label>
			<div class="formControls col-2">
				<input id="showIndex" name="showIndex" type="text" class="input-text" placeholder="请输入顺序" datatype="n" errormsg="请输入正确的顺序">
			</div>
			<label class="form-label col-2">状态：</label>
			<div class="formControls col-2">
				<select id="status" name="status" class="select input-text" datatype="*">
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">描述：</label>
			<div class="formControls col-2" style="width:75%;">
				<textarea id="description" name="description" class="textarea" dragonfly="true" onKeyUp="textarealength(this,200)" datatype="*1-200" ignore="ignore"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
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
<%--角色编辑 --%>
<div id="updateRole" class="pd-20" style="display:none;">
	<form action="${contextPath}/role/update" method="post" class="form form-horizontal" id="updateRoleForm">
		<input id="id" name="id" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名称" datatype="zh4-20" errormsg="请输入4-20位汉字">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>权限标识：</label>
			<div class="formControls col-2">
				<input id="mark" name="mark" type="text" class="input-text" placeholder="请输入标识" datatype="s4-20" errormsg="请输入正确的标识">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>显示顺序：</label>
			<div class="formControls col-2">
				<input id="showIndex" name="showIndex" type="text" class="input-text" placeholder="请输入顺序" datatype="n" errormsg="请输入正确的顺序">
			</div>
			<label class="form-label col-2">状态：</label>
			<div class="formControls col-2">
				<select id="status" name="status" class="select input-text" datatype="*">
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">描述：</label>
			<div class="formControls col-10" style="width:75%;">
				<textarea id="description" name="description" class="textarea" dragonfly="true" onKeyUp="textarealength(this,200)" datatype="*1-200" ignore="ignore"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
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
<%--角色授权 --%>
<div id="setRoleMenu" class="pd-20" style="display:none;">
	<form action="" method="post" class="form form-horizontal" id="form-article-add">
		<input id="roleId" name="roleId" type="hidden"> 
		<div class="row cl">
			<label class="form-label col-2" style="width:16%;">角色名称：</label>
			<div class="formControls col-2">
				<input id="roleName" name="roleName" type="text" class="input-text" readonly="readonly">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2" style="width:16%;">功能权限：</label>
			<div class="formControls col-10" style="border:1px solid #C7C7E2;overflow:hidden;overflow-y:scroll">
				<ul id="topMenu" class="ztree"></ul>
			</div>
		</div>
		<div class="row cl">
			<div class="col-10 col-offset-2">
				<button onClick="setRoleMenu();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close()" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>

<%--查看信息授权 --%>
<div id="setRoleField" class="pd-20" style="display:none;">
	<form action="" method="post" class="form form-horizontal" id="form-article-add">
		<input id="roleId" name="roleId" type="hidden"> 
		<div class="row cl">
			<label class="form-label col-2" style="width:16%;">角色名称：</label>
			<div class="formControls col-2">
				<input id="roleName" name="roleName" type="text" class="input-text" readonly="readonly">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2" style="width:16%;">查看权限：</label>
			<div class="formControls col-10" style="border:1px solid #C7C7E2;overflow:hidden;overflow-y:scroll">
				<ul id="topField" class="">
				
				</ul>
			</div>
		</div>
		<div class="row cl">
			<div class="col-10 col-offset-2">
				<button onClick="setRoleField();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close()" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/role/role.js?v=${appVerDate}"></script>
</body>
</html>