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
<title>居民信息导入</title>
</head>
<body>
<nav class="breadcrumb" style="width: 1500px"><i class="Hui-iconfont"></i></nav>
<div style="padding:0 20px 20px 20px;">
	<input type="hidden" id="excelUrl" value="${excelUrl}"> 
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
		<label class="form-label col-4"><font color="red">* 表格有红色内容处格式有错误，请重新确认</font></label>
			<div class="col-offset-9">
				<button onclick="batchAddResidentsinfo();" id="sureImport" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 确定导入</button>
				<button onClick="cancelImport();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</div>
	<div class="mt-10" style="width: 2500px">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="residentsInfoImportTable">
			<thead>
				<tr class="text-c">
					<th width="3%">申办单位</th>
					<th width="3%">银行卡号</th>
					<th width="2%">卡的类别</th>
					<th width="3%">发卡机构名称</th>
					<th width="3%">发卡机构代码</th>
					<th width="3%">发卡序列号</th>
					<th width="3%">发卡机构证书</th>
					<th width="3%">发卡时间</th>
					<th width="3%">姓名</th>
					<th width="2%">性别</th>
					<th width="3%">民族代码</th>
					<th width="3%">出生日期</th>
					<th width="3%">居民身份证号码</th>
					<th width="3%">卡有效期</th>
					<th width="3%">本人电话</th>
					<th width="3%">医疗支付方式</th>
					<th width="4%">户籍地址</th>
					<th width="4%">现住址</th>
					<th width="3%">联系人姓名</th>
					<th width="3%">联系人关系</th>
					<th width="3%">联系人电话</th>
					<th width="3%">文化程度代码</th>
					<th width="3%">婚姻状况代码</th>
					<th width="3%">职业代码</th>
					<th width="3%">社保卡号</th>
					<th width="3%">发卡银行</th>
					<th width="3%">卡状态</th>
					<th width="3%">芯片号</th>
				</tr>
			</thead>
		</table>
		</div>
</div>
<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/residentsInfo/residentsInfo_import.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/residentsInfo/residentsInfo.js?v=${appVerDate}"></script>
</body>
</html>