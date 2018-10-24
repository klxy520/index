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
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<title>其他居民健康卡查询</title>

</head>
<body>
<nav class="breadcrumb">其他居民健康卡基本信息查询<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-5">身份证号或居民健康卡卡号：</label>
			<div class="formControls col-4">
				<input id="number" type="text" class="input-text" placeholder="请输入身份证号或居民健康卡卡号">
			</div>
		</div>
		<button id="search_btn" onclick="search();" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton onclick="addManagementScope();" classname="btn btn-primary radius" btnSn="0101010301" btnName="添加到我的管理范围" icon="&#xe600;"></jc:operationButton>
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="otherResidentQuery">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>
					<th width="7%">居民姓名</th>
					<th width="7%">居民健康卡号</th>
					<th width="7%">社保卡号</th>
					<th width="10%">身份证号</th>
					<th width="8%">证件有效期</th>
					<th width="6%">行政机构</th>
					<th width="6%">区域机构</th>
					<th width="5%">性别</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/resident/other_resident_query.js?v=${appVerDate}"></script>
</body>
</html>