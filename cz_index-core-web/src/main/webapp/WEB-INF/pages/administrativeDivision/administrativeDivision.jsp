<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域机构管理</title>
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
	<ul id="topAdministrativeDivision" class="ztree">
	</ul>
</div>
<div style="margin-left:200px;">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 区域机构管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="pd-20">
		<div class="cl pd-5 bg-1 bk-gray mt-20"> 
			<span class="l">
				<jc:operationButton classname="btn btn-primary radius" onclick="addAdministrativeDivision();" btnSn="0101010901" btnName="添加" icon="&#xe600;"></jc:operationButton>
				<jc:operationButton classname="btn btn-secondary radius" onclick="updateAdministrativeDivision();" btnSn="0101010902" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
				<jc:operationButton classname="btn btn-danger radius" onclick="del();" btnSn="0101010903" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
			</span> 
		</div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort" id="administrativeDivisionList">
				<thead>
					<tr class="text-c">
						<th width="3%"></th>
						<th width="4%">区域机构名称</th>
						<th width="4%">地址</th>
						<th width="4%">电话</th>
						<th width="4%">邮编</th>
						<th width="2%">负责人</th>
						<th width="2%">创建时间</th>
						<th width="2%">修改时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<!-- 行政区域添加 -->
<div id="addAdministrativeDivision" class="pd-20" style="display:none;">
	<form action="${contextPath}/administrativeDivision/add" method="post" class="form form-horizontal" id="addAdministrativeDivisionForm">
		<input id="parentId" name="parentid" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>区域机构名称:</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名称" datatype="zhy2-50" errormsg="请输2-50位中文">
			</div>
			<label class="form-label col-2">地址:</label>
			<div class="formControls col-2">
				<input id="address" name="address" type="text" class="input-text" placeholder="请输入地址" datatype="empty|*2-50" errormsg="请输2-50位地址">
			</div>
			<label class="form-label col-2">电话:</label>
			<div class="formControls col-2">
				<input id="phone" name="phone" type="text" class="input-text" placeholder="请输入电话" datatype="empty|lxdh" errormsg="请输入正确号码">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">邮箱:</label>
			<div class="formControls col-2">
				<input id="zipCode" name="zipCode" type="text" class="input-text" placeholder="请输入邮箱" datatype="empty|/^[0-9]{6}$/" errormsg="请输入正确邮编">
			</div>
			<label class="form-label col-2">负责人:</label>
			<div class="formControls col-2">
				<input id="personCharge" name="personCharge" type="text" class="input-text" placeholder="请输入负责人"																								>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">备注:</label>
			<div class="formControls col-9">
				<input id="remark" name="remark" type="text" class="input-text" placeholder="请填写备注">
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
<!--  区域编辑 -->
<div id="updateAdministrativeDivision" class="pd-20" style="display:none;">
		<form action="${contextPath}/administrativeDivision/update" method="post" class="form form-horizontal" id="updateAdministrativeDivisionForm">
		<input id="id" name="id" type="hidden">
		<input id="parentId" name="parentid" type="hidden">
		<input id="isDelete" name="isDelete" type="hidden" value="0">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>区域机构名称:</label>
			<div class="formControls col-2">
				<input id="name" name="name" type="text" class="input-text" placeholder="请输入名称" datatype="zhy2-50" errormsg="请输2-50位中文">
			</div>
			<label class="form-label col-2">地址:</label>
			<div class="formControls col-2">
				<input id="address" name="address" type="text" class="input-text" placeholder="请输入地址" datatype="empty|*2-50" errormsg="请输2-50位地址">
			</div>
			<label class="form-label col-2">电话:</label>
			<div class="formControls col-2">
				<input id="phone" name="phone" type="text" class="input-text" placeholder="请输入电话" datatype="empty|lxdh" errormsg="请输入正确号码">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">邮箱:</label>
			<div class="formControls col-2">
				<input id="zipCode" name="zipCode" type="text" class="input-text" placeholder="请输入邮箱" datatype="empty|/^[0-9]{6}$/" errormsg="请输入正确邮编">
			</div>
			<label class="form-label col-2">负责人:</label>
			<div class="formControls col-2">
				<input id="personCharge" name="personCharge" type="text" class="input-text" placeholder="请输入负责人"																								>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">备注:</label>
			<div class="formControls col-9">
				<input id="remark" name="remark" type="text" class="input-text" placeholder="请填写备注">
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
<script type="text/javascript" src="${contextPath}/js/administrativeDivision/administrativeDivision.js?v=${appVerDate}"></script>
</body>
</html>