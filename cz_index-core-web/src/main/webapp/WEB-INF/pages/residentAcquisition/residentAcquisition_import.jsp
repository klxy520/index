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
<title>居民采集信息导入</title>
</head>
<body>
<nav class="breadcrumb" style="width: 1300px"><i class="Hui-iconfont"></i></nav>
<div style="padding:0 20px 20px 20px;">
	<input type="hidden" id="excelUrl" value="${excelUrl}"> 
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
		<label class="form-label col-4"><font color="red">* 表格有红色内容处格式有错误，请重新确认</font></label>
			<div class="col-offset-9">
				<button onclick="bathAddResidentAcquisitions();" id="sureImport" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 确定导入</button>
				<button onClick="cancelImport();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</div>
	<div class="mt-10" style="width: 2500px">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="importresidentacquisitionTable">
			<thead>
				<tr class="text-c">
					<th width="4%">申办单位</th>
					<th width="4%">姓名</th>
					<th width="5%">身份证号</th>
					<th width="5%">发卡机关</th>
					<th width="5%">证件有效截止日期</th>
					<th width="4%">民族</th>
					<th width="6%">文化程度</th>
					<th width="7%">户籍地址</th>
					<th width="7%">现住址</th>
					<th width="3%">邮编</th>
					<th width="5%">联系电话</th>
					<th width="5%">新农合号</th>
					<th width="5%">社保号</th>
					<th width="5%">工资卡发放银行</th>
					<th width="5%">健康卡办理银行</th>
					<th width="7%">职业</th>
					<th width="7%">行业</th>
				</tr>
			</thead>
		</table>
		</div>
</div>
<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/residentAcquisition/residentAcquisition_import.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/residentAcquisition/residentAcquisition_list.js?v=${appVerDate}"></script>
</body>
</html>