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
<title>所有数据信息导入</title>
</head>
<body>
<nav class="breadcrumb" style="width: 2000px"><i class="Hui-iconfont"></i></nav>
<div style="padding:0 20px 20px 20px;">
<input type="hidden" id="excelUrl" value="${excelUrl}"/> 
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
		<label class="form-label col-4"><font color="red">* 表格有红色内容处格式有错误，请重新确认</font></label>
			<div class="col-offset-9">
				<button onclick="batchAddResidentData();" id="sureImport" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 确定导入</button>
				<button onClick="cancelImport();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
			</div>
		</div>
	<div class="mt-10" style="width: 2000px" >
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="importBaseResidentTable">
			<thead>
				<tr class="text-c">
					<th width="4%">姓名</th>
					<th width="5%">居民健康卡号</th>
					<th width="4%">社保卡号</th>
					<th width="5%">身份证号</th>
					<th width="5%">证件有效期</th>
					<th width="2%">性别</th>
					<th width="2%">年龄</th>
					<th width="3%">民族</th>
					<th width="8%">户籍地址</th>
					<th width="8%">现住地址</th>
					<th width="5%">邮编</th>
					<th width="5%">联系电话</th>
					<th width="5%">工作单位</th>
					<th width="3%">学历</th>
					<th width="3.5%">医保类型</th>
					<th width="3.5%">残疾类型</th>
					<th width="3.5%">工会特征</th>
					<th width="3.5%">离休干部</th>
					<th width="4%">扶贫户</th>
					<th width="5%">低保类型</th>
					<th width="5%">病种类型</th> 
				</tr>
			</thead>
		</table>
	</div>
</div>
<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/resident/allresident_import.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/resident/resident.js?v=${appVerDate}"></script>
</body>
</html>