<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台管理</title>
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
.publicKey,.privateKey{
	word-break: break-all; 
	overflow:hidden;
}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 平台管理 <span class="c-gray en">&gt;</span> 平台管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-1">APPID:</label>
			<div class="formControls col-2">
				<input id="appid" type="text" class="input-text" placeholder="请输入平台ID">
			</div>
			<label class="form-label col-1">平台名称:</label>
			<div class="formControls col-2">
				<input id="platName" type="text" class="input-text" placeholder="请输入平台名称">
			</div>
			<label class="form-label col-1">应用名称:</label>
			<div class="formControls col-2">
				<input id="appName" type="text" class="input-text" placeholder="请输入应用名称（缩写）">
			</div>
		</div>
		<button id="search_btn" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull" style="text-align: center;cursor: pointer;"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton onclick="addPlatform();" classname="btn btn-primary radius" btnSn="0101030101" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton onclick="updatePlatform();" classname="btn btn-secondary radius" btnSn="0101030102" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton onclick="setPlatformEnable();" classname="btn btn-success radius" btnSn="0101030103" btnName="启用" icon="&#xe6dc;"></jc:operationButton>
			<jc:operationButton onclick="setPlatformDisable();" classname="btn btn-default radius" btnSn="0101030104" btnName="禁用" icon="&#xe6de;"></jc:operationButton>
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="platformList">
			<thead>
				<tr class="text-c">
					<th width="1%"></th>
					<th width="20%">APPID</th>
					<th width="15%">应用名称缩写</th>
					<th width="8%">平台名称</th>
					<th width="15%">交付公钥</th>
					<th width="35%">交付私钥</th>
					<th width="5%">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<%--平台添加 --%>
<div id="addPlatform" class="pd-20" style="display:none;">
	<form action="${contextPath}/platform/add" method="post" class="form form-horizontal" id="addPlatformForm">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>APPID:</label>
			<div class="formControls col-2">
				<input id="appid" name="appid" type="text" class="input-text" placeholder="请输入平台ID" datatype="*" errormsg="请输入平台ID">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>平台名称:</label>
			<div class="formControls col-2">
				<input id="platName" name="platName" type="text" class="input-text" placeholder="请输入平台名称" datatype="*" errormsg="请输入平台名称">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>应用名称(缩写):</label>
			<div class="formControls col-2">
				<input id="appName" name="appName" type="text" class="input-text" placeholder="请输入应用名称(缩写)" datatype="*" errormsg="请输入应用名称">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>交付公钥:</label>
			<div class="formControls col-2">
				<input id="publicKey" name="publicKey" type="text" class="input-text" placeholder="请输入交付公钥" datatype="*" errormsg="请输入交付公钥">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>交付私钥:</label>
			<div class="formControls col-2">
				<input id="privateKey" name="privateKey" type="text" class="input-text" placeholder="请输入交付私钥" datatype="*" errormsg="请输入交付私钥">
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
<%--平台编辑 --%>
<div id="updatePlatform" class="pd-20" style="display:none;">
	<form action="${contextPath}/platform/update" method="post" class="form form-horizontal" id="updatePlatformForm">
		<input id="id" name="id" type="hidden">
		<input id="status" name="status" type="hidden">
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>APPID:</label>
			<div class="formControls col-2">
				<input id="appid" name="appid" type="text" class="input-text" placeholder="请输入平台ID" datatype="*" errormsg="请输入平台ID">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>平台名称:</label>
			<div class="formControls col-2">
				<input id="platName" name="platName" type="text" class="input-text" placeholder="请输入平台名称" datatype="*" errormsg="请输入平台名称">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>应用名称(缩写):</label>
			<div class="formControls col-2">
				<input id="appName" name="appName" type="text" class="input-text" placeholder="请输入应用名称(缩写)" datatype="*" errormsg="请输入应用名称">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>交付公钥:</label>
			<div class="formControls col-2">
				<input id="publicKey" name="publicKey" type="text" class="input-text" placeholder="请输入交付公钥" datatype="*" errormsg="请输入交付公钥">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3"><span class="c-red">*</span>交付私钥:</label>
			<div class="formControls col-2">
				<input id="privateKey" name="privateKey" type="text" class="input-text" placeholder="请输入交付私钥" datatype="*" errormsg="请输入交付私钥">
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
<%--平台详情 --%>
<div id="platformInfo" class="pd-20 form form-horizontal" style="display:none;">
	<div class="row cl">
		<label class="form-label col-2">APPIDD:</label>
		<div class="formControls col-8">
			<label id="appid"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-2">平台名称:</label>
		<div class="formControls col-8">
			<label id="platName"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-2">应用名称(缩写):</label>
		<div class="formControls col-8">
			<label id="appName"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-2">交付公钥:</label>
		<div class="formControls col-8">
			<label id="publicKey" style="word-wrap:break-word;word-break:break-all;"></label>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-2">交付私钥:</label>
		<div class="formControls col-8">
			<label id="privateKey" style="word-wrap:break-word;word-break:break-all;"></label>
		</div>
	</div>
</div>


<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/platform/platform.js?v=${appVerDate}"></script>
</body>
</html>