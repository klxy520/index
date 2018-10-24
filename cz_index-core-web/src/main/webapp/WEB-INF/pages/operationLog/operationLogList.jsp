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
<title>日志查询列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 日志管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-2">操作表单：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" id="formName" name="formName">
			</div>
			<label class="form-label col-2">操作类型：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" id="type" name="type">
			</div>
			<label class="form-label col-2">操作人员：</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" id="userName" name="userName">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">开始时间：</label>
			<div class="formControls col-2">
				<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'datemin2\')}'})" id="datemin" class="input-text Wdate" name="operationDate">
		    </div>
		    <label class="form-label col-2">结束时间：</label>
			<div class="formControls col-2">
				<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})" id="datemin2" class="input-text Wdate" name="operationDate2">
			</div>
		</div>
		<button class="btn btn-success" onclick="fn_search();" type="submit" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull" style="text-align: center;cursor: pointer;"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="operationLogList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>
					<th width="5%">详情</th>
					<th width="10%">操作人</th>
					<th width="10%">操作表单</th>
					<th width="10%">操作类型</th>
					<th width="20%">操作详细信息</th>
					<th width="12%">操作时间</th>
				</tr>
			</thead>
			<tbody id="operationLogdata">
			</tbody>
		</table>
	</div>
</div>

<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/operationLog/operationLog.js?v=${appVerDate}"></script>
</body>
</html>