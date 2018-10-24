<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
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
	<input id="parentId" name="parentid" type="hidden" value="1">
	<ul id="topDictionary" class="ztree">
	</ul>
</div>
<div style="margin-left:200px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 字典管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-20">
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
			<span class="l">
				<jc:operationButton classname="btn btn-primary radius" onclick="addDictionary();" btnSn="0101010701" btnName="添加" icon="&#xe600;"></jc:operationButton>
				<jc:operationButton classname="btn btn-secondary radius" onclick="updateDictionary();" btnSn="0101010702" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
				<jc:operationButton classname="btn btn-success radius" onclick="enableDictionary();" btnSn="0101010703" btnName="启用" icon="&#xe6dc;"></jc:operationButton>
				<jc:operationButton classname="btn btn-default radius" onclick="disableDictionary();" btnSn="0101010704" btnName="禁用" icon="&#xe6de;"></jc:operationButton>
			</span> 
		</div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="dictionaryList">
				<thead>
					<tr class="text-c">
						<th width="3%"></th>
						<th width="4%">字典名称</th>
						<th width="4%">字典(key)</th>
						<th width="4%">字典值</th>
						<th width="4%">描述</th>
						<th width="2%">状态</th>
						<th width="2%">是否默认</th>
						<th width="2%">显示顺序</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<!-- 字典添加 -->
<div id="addDictionary" class="pd-20" style="display:none;">
	<form action="${contextPath}/dictionary/addDictionary" method="post" class="form form-horizontal" id="addDictionaryForm">
		<input id="parentId" name="parentid" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>字典名称:</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名称" datatype="zhy2-50" errormsg="请输2-50位名称">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>字典(前缀):</label>
			<div class="formControls col-2">
				<input id="dictkey" name="dictkey" type="text" class="input-text" placeholder="请输入字典钥匙" datatype="s2-20" errormsg="请输2-20位的字典钥匙">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>字典值:</label>
			<div class="formControls col-2">
				<input id="value" name="value" type="text" class="input-text" placeholder="请输入字典值" datatype="s2-40" errormsg="请输2-40位字典值">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>描述:</label>
			<div class="formControls col-2">
				<input id="description" name="description" type="text" class="input-text" placeholder="请描述字典" datatype="*" errormsg="请描述字典">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>状态:</label>
			<div class="formControls col-2">
				<select id="status" name="status" class="select input-text">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>是否默认:</label>
			<div class="formControls col-2">
				<select id="isdefault" name="isdefault" class="select input-text">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</div>
			<label class="form-label col-2">显示顺序:</label>
			<div class="formControls col-2">
				<input id="showindex" name="showindex" type="text" class="input-text" placeholder="请输入显示顺序" datatype="n1-3" errormsg="请输入数字">
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
<!--  字典编辑 -->
<div id="updateDictionary" class="pd-20" style="display:none;">
		<form action="${contextPath}/dictionary/update" method="post" class="form form-horizontal" id="updateDictionaryForm">
		<input id="id" name="id" type="hidden">
		<input id="parentId" name="parentid" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>字典名称:</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名称" datatype="zhy2-50" errormsg="请输2-50位名称">
			</div>
			<label class="form-label col-2">字典(前缀):</label>
			<div class="formControls col-2">
				<input id="dictkey" name="dictkey" type="text" class="input-text" readonly="readonly">
			</div>
			<label class="form-label col-2">字典值:</label>
			<div class="formControls col-2">
				<input id="value" name="value" type="text" class="input-text" placeholder="请输入字典值" datatype="s2-40" errormsg="请输2-40位字典值" ignore="ignore">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>描述:</label>
			<div class="formControls col-2">
				<input id="description" name="description" type="text" class="input-text" placeholder="请描述字典" datatype="*" errormsg="请描述字典">
			</div>
			<label class="form-label col-2"><span class="c-red">*</span>状态:</label>
			<div class="formControls col-2">
				<select id="status" name="status" class="select input-text">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">是否默认:</label>
			<div class="formControls col-2">
				<select id="isdefault" name="isdefault" class="select input-text">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</div>
			<label class="form-label col-2">显示顺序:</label>
			<div class="formControls col-2">
				<input id="showindex" name="showindex" type="text" class="input-text" placeholder="请输入显示顺序" datatype="n1-3" errormsg="请输入数字">
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
<script type="text/javascript" src="${contextPath}/js/dictionary/dictionaryList.js?v=${appVerDate}"></script>
</body>
</html>