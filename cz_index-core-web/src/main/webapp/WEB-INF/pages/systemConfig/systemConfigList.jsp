
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统参数管理</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.col-2{
	width:25%;
}
.sysconfig_value{
	word-break: break-all; 
	overflow:hidden;
}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 系统参数管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-2" style="width:16.66667%;">系统参数key：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input id="systemKey" type="text" class="input-text" placeholder="请输入key">
			</div>
			<label class="form-label col-2" style="width:16.66667%;">参数值：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input id="systemValue" type="text" class="input-text" placeholder="请输入参数值">
			</div>
		</div>
		<button id="search_btn" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton classname="btn btn-primary radius" onclick="addSystemConfig();" btnSn="0101010401" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateSystemConfig();" btnSn="0101010402" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="systemConfigList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>
					<th width="7%">参数key</th>
					<th width="60%">参数值</th>
					<th width="30%">描述</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<%--系统参数添加 --%>
<div id="addSystemConfig" class="pd-20" style="display:none;">
	<form action="${contextPath}/systemConfig/add" method="post" class="form form-horizontal" id="addSystemConfigForm">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>参数key：</label>
			<div class="formControls col-2">
				<input id="systemKey" name="systemKey" type="text" class="input-text" placeholder="请输入key" datatype="s4-50" errormsg="请输入4-50个字符">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>参数值：</label>
			<div class="formControls col-2">
				<input id="systemValue" name="systemValue" type="text" class="input-text" placeholder="请输入参数值" datatype="*1-200" errormsg="请输入正确的参数值">
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
<%--系统参数编辑 --%>
<div id="updateSystemConfig" class="pd-20" style="display:none;">
	<form action="${contextPath}/systemConfig/update" method="post" class="form form-horizontal" id="updateSystemConfigForm">
		<input id="id" name="id" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>参数key：</label>
			<div class="formControls col-2">
				<input id="systemKey" name="systemKey" type="text" class="input-text" placeholder="请输入key" datatype="s4-50" errormsg="请输入4-50个字符">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>参数值：</label>
			<div class="formControls col-2">
				<input id="systemValue" name="systemValue" type="text" class="input-text" placeholder="请输入参数值" datatype="*1-200" errormsg="请输入正确的参数值">
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


<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/systemConfig/systemConfig.js?v=${appVerDate}"></script>
</body>
</html>