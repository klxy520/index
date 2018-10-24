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
<style type="text/css">
.form-horizontal .form-label {
    margin-top: 0px;
    cursor: text;
    text-align: right;
    padding-right: 10px;
}
</style>

<title>日志管理详情</title>
</head>
<body>
<div class="pd-20">
	<div class="form form-horizontal" style="border:1px solid #BEBEBE">
		<div class="row cl">
			<label class="form-label col-2">日志编号：</label>
			<div class="formControls col-2">
				<font>${operationLog.id }</font> 
			</div>
			<label class="form-label col-2">用户编号：</label>
			<div class="formControls col-2">
				<font>${operationLog.userId }</font> 
			</div>
			<label class="form-label col-2">用户类型：</label>
			<div class="formControls col-2">
				<c:if test="${operationLog.userType==1}">
					<font>平台管理员</font>
				</c:if>
				<c:if test="${operationLog.userType==2}">
					<font>商家管理员</font>
				</c:if>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">用户名称：</label>
			<div class="formControls col-2">
				<font>${operationLog.userName }</font>
			</div>
			<label class="form-label col-2">表单名称：</label>
			<div class="formControls col-2">
				<font>${operationLog.formName }</font>
			</div>
			<label class="form-label col-2">记录ID：</label>
			<div class="formControls col-2">
				<font>${operationLog.recordId }</font>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">操作类型：</label>
			<div class="formControls col-2">
				<font>${operationLog.type }</font> 
			</div>
			<label class="form-label col-2">操作时间：</label>
			<div class="formControls">
			    <%-- <font> --%>
				<fmt:formatDate value="${operationLog.operationDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				<%-- </font> --%>
			</div>
		</div>
		<div class="row cl banner_addDescribe" style="margin-top:10px;">
			<label class="form-label col-2">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
			<div class="formControls col-10">
				<font>${operationLog.remark }</font>
			</div>
		</div>
		<div class="row cl banner_addDescribe" style="margin-top:10px;">
			<label class="form-label col-2">详细操作：</label>
			<div class="formControls col-10">
				<font style="word-break: break-all;">${operationLog.detail }</font>
			</div>
		</div>
	</div>
	<div class="row cl ">
		<div class="col-offset-11">
			<button onClick="layer_close();" class="btn btn-default radius mt-20" type="button">关闭</button>
		</div>
	</div>
</div>
<%@ include file="../commons/comm_const_js.jsp"%>
</body>
</html>