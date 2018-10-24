<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<style>
	.zm_break{
		word-break: break-all;
	}
</style>
<title>行政机构管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 行政机构管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
				<label class="form-label col-2">行政机构名称：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text" id="administrativeName" name="administrativeName">
				</div>
				<label class="form-label col-2">地址：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text" id="address" name="address">
				</div>
				<label class="form-label col-2">电话：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text" id="phone" name="phone">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">负责人：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text" id="personCharge" name="personCharge">
				</div>
				<label class="form-label col-2">邮编：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text" id="zipCode" name="zipCode">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">创建开始时间：</label>
				<div class="formControls col-2" style="width:17%">
					<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' ,maxDate:'#F{$dp.$D(\'datemin2\')||\'%y-%M-{%d} %H:%m:%s\'}'})" id="datemin" class="input-text Wdate" name="createDate">
				</div>
				<label class="form-label col-2" style="width:16.3%">创建结束时间：</label>
				<div class="formControls col-2" style="width:17%">
					<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' ,minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})" id="datemin2" class="input-text Wdate" name="createDate2">
				</div>
			</div>
		<button class="btn btn-success" id="search_btn" type="submit" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull" style="text-align: center;cursor: pointer;"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton classname="btn btn-primary radius" onclick="addAdministrativeManagement();" btnSn="0101010801" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateAdministrativeManagement();" btnSn="0101010802" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton classname="btn btn-danger radius" onclick="del();" btnSn="0101010803" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
			<jc:operationButton classname="btn btn-success radius" onclick="setRoleFieldLayer();" btnSn="0101010804" btnName="信息授权" icon="&#xe6dc;"></jc:operationButton>
		</span> 
	</div>
	<!-- list -->
		<div class="mt-10">
			<table
				class="table table-border table-bordered table-bg table-hover table-sort" id="administrativeManagementList">
				<thead>
					<tr class="text-c">
						<th width="3%"></th>
						<th width="10%">行政机构名称</th>
						<th width="10%">地址</th>
						<th width="10%">电话</th>
						<th width="5%">邮编</th>
						<th width="5%">负责人</th>
						<th width="10%">创建时间</th>
						<th width="10%">修改时间</th>
					</tr>
				</thead>
			</table>
		</div>
</div>

<!-- 机构添加 -->
<div id="addAdministrativeManagement" class="pd-20" style="display:none;">
	<form action="${contextPath}/administrativeManagement/add" method="post" class="form form-horizontal" id="addAdministrativeManagementForm">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>行政机构名称:</label>
			<div class="formControls col-2">
				<input id="administrativeName" name="administrativeName" type="text" class="input-text" placeholder="请输入行政机构名称" datatype="zhy2-50" errormsg="请输2-50位中文">
			</div>
			<label class="form-label col-2">地址:</label>
			<div class="formControls col-2">
				<input id="address" name="address" type="text" class="input-text" placeholder="请输入地址" datatype="empty|*2-50" errormsg="请输2-50位地址">
			</div>
			<label class="form-label col-2">电话:</label>
			<div class="formControls col-2">
				<input id="phone" name="phone" type="text" class="input-text" placeholder="请输入电话号码" datatype="empty|lxdh" errormsg="请输入正确号码">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">邮编:</label>
			<div class="formControls col-2">
				<input id="zipCode" name="zipCode" type="text" class="input-text" placeholder="请输入邮编" datatype="empty|/^[0-9]{6}$/" errormsg="请输入正确邮编">
			</div>
			<label class="form-label col-2">负责人:</label>
			<div class="formControls col-2">
				<input id="personCharge" name="personCharge" type="text" class="input-text" placeholder="请输入负责人">
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
<!--  机构编辑 -->
<div id="updateAdministrativeManagement" class="pd-20" style="display:none;">
		<form action="${contextPath}/administrativeManagement/update" method="post" class="form form-horizontal" id="updateAdministrativeManagementForm">
		<input id="id" name="id" type="hidden">
		<input id="isDelete" name="isDelete" type="hidden" value="0">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>行政机构名称:</label>
			<div class="formControls col-2">
				<input id="administrativeName" name="administrativeName" type="text" class="input-text" placeholder="请输入行政机构名称" datatype="zhy2-50" errormsg="请输2-50中文">
			</div>
			<label class="form-label col-2">地址:</label>
			<div class="formControls col-2">
				<input id="address" name="address" type="text" class="input-text" placeholder="请输入地址" datatype="empty|*2-50" errormsg="请输2-50位地址">
			</div>
			<label class="form-label col-2">电话:</label>
			<div class="formControls col-2">
				<input id="phone" name="phone" type="text" class="input-text" placeholder="请输入电话号码" datatype="empty|lxdh" errormsg="请输入正确号码">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">邮编:</label>
			<div class="formControls col-2">
				<input id="zipCode" name="zipCode" type="text" class="input-text" placeholder="请输入邮编" datatype="empty|/^[0-9]{6}$/" errormsg="请输入正确邮编">
			</div>
			<label class="form-label col-2">负责人:</label>
			<div class="formControls col-2">
				<input id="personCharge" name="personCharge" type="text" class="input-text" placeholder="请输入负责人">
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

<%--查看信息授权 --%>
<div id="setRoleField" class="pd-20" style="display:none;">
	<form action="" method="post" class="form form-horizontal" id="form-article-add">
		<input id="roleId" name="roleId" type="hidden"> 
		<div class="row cl" >
			<label class="form-label col-3" >机构名称：</label>
			<div class="formControls col-9" >
				<input id="roleName" name="roleName" type="text" class="input-text col-12" readonly="readonly">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-3" >查看权限：</label>
			<div class="formControls col-9" style="border:1px solid #C7C7E2">
				<ul id="topField" class="ml-10">
				
				</ul>
			</div>
		</div>
		<div class="row cl">
			<div class="col-5 col-offset-7">
				<button onClick="setRoleField();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close()" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/administrativeManagement/administrativeManagement.js?v=${appVerDate}"></script>
</body>
</html>