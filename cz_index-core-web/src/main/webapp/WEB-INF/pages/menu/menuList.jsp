<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
</head>
<body class="pos-r">
<!-- 树状图 -->
<div class="pos-a" style="width:200px;left:0;top:0; bottom:0; height:100%; border-right:1px solid #e5e5e5; background-color:#f5f5f5">
	<input id="parentId" name="parentId" type="hidden" value="1">
	<ul id="topMenu" class="ztree">
	</ul>
</div>
<div style="margin-left:200px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 菜单管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-20">
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
			<span class="l">
				<jc:operationButton classname="btn btn-primary radius" onclick="addMenu();" btnSn="0101010101" btnName="添加" icon="&#xe600;"></jc:operationButton>
				<jc:operationButton classname="btn btn-secondary radius" onclick="updateMenu();" btnSn="0101010102" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			</span> 
		</div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="menuList">
				<thead>
					<tr class="text-c">
						<th width="3%"></th>
						<th width="5%">菜单名称</th>
						<th width="2%">编号</th>
						<th width="2%">URL</th>
						<th width="10%">描述</th>
						<th width="1%">ICON_URL</th>
						<th width="1%">显示顺序</th>
						<th width="3%">类型</th>
						<th width="3%">HTTP方法</th>
						<th width="3%">状态</th>
						<th width="3%">默认展开</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<%--菜单添加 --%>
<div id="addMenu" class="pd-20" style="display:none;">
	<form action="${contextPath}/menu/add" method="post" class="form form-horizontal" id="addMenuForm">
		<input id="parentId" name="parentId" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>名称：</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名称" datatype="s2-20" errormsg="请输入2-20位名称">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>类型：</label>
			<div class="formControls col-2">
				<select id="type" name="type" class="select input-text" datatype="*">
					<option value="0">菜单</option>
					<option value="1">按钮</option>
				</select>
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>状态：</label>
			<div class="formControls col-2">
				<select id="status" name="status" class="select input-text" datatype="*">
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">菜单图标：</label>
			<div class="formControls col-2">
				<input id="iconUrl" name="iconUrl" type="text" class="input-text"   datatype="*1-20" ignore="ignore" errormsg="请输入正确的图标代码">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>显示顺序：</label>
			<div class="formControls col-2">
				<input id="showIndex" name="showIndex" type="text" class="input-text" datatype="n" errormsg="请输入正确的数字">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>HTTP方法：</label>
			<div class="formControls col-2">
				<select id="method" name="method" class="select input-text" datatype="*">
					<option value="0">GET</option>
					<option value="1">POST</option>
					<option value="2">PUT</option>
					<option value="3">DELETE</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>URL：</label>
			<div class="formControls col-6" style="width:50%;">
				<input id="targetUrl" name="targetUrl" type="text" class="input-text" datatype="*1-200" errormsg="请输入正确的URL">
			</div>
			<label class="form-label col-2">默认展开：</label>
			<div class="formControls col-2">
				<select id="defaultOpen" name="defaultOpen" class="select input-text" datatype="*">
					<option value="0">否</option>
					<option value="1">是</option>
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
<%--菜单编辑 --%>
<div id="updateMenu" class="pd-20" style="display:none;">
	<form action="${contextPath}/menu/update" method="post" class="form form-horizontal" id="updateMenuForm">
		<input id="id" name="id" type="hidden">
		<input id="parentId" name="parentId" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>名称：</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名字" datatype="s2-20" errormsg="请输入2-20位名称">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>类型：</label>
			<div class="formControls col-2">
				<select id="type" name="type" class="select input-text" datatype="*">
					<option value="0">菜单</option>
					<option value="1">按钮</option>
				</select>
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>状态：</label>
			<div class="formControls col-2">
				<select id="status" name="status" class="select input-text" datatype="*">
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">菜单图标：</label>
			<div class="formControls col-2">
				<input id="iconUrl" name="iconUrl" type="text" class="input-text"   datatype="*1-20" ignore="ignore" errormsg="请输入正确的图标代码">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>显示顺序：</label>
			<div class="formControls col-2">
				<input id="showIndex" name="showIndex" type="text" class="input-text" datatype="n" errormsg="请输入正确的数字">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>HTTP方法：</label>
			<div class="formControls col-2">
				<select id="method" name="method" class="select input-text" datatype="*">
					<option value="0">GET</option>
					<option value="1">POST</option>
					<option value="2">PUT</option>
					<option value="3">DELETE</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>URL：</label>
			<div class="formControls col-6" style="width:50%;">
				<input id="targetUrl" name="targetUrl" type="text" class="input-text" datatype="*1-200" errormsg="请输入正确的URL">
			</div>
			<label class="form-label col-2">默认展开：</label>
			<div class="formControls col-2">
				<select id="defaultOpen" name="defaultOpen" class="select input-text" datatype="*">
					<option value="0">否</option>
					<option value="1">是</option>
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


<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/menu/menu.js?v=${appVerDate}"></script>

</body>
</html>