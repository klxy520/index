<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<title>信息日志详情</title>

</head>
<body>
<div class="pd-20">
	<%-- <p style="font-weight:blod;font-size: 15px;">日志详情:</p> --%>
	<div class="form form-horizontal" style="border:1px solid #BEBEBE">
		<div class="row cl">
		
			<label class="form-label col-2">日志编号：</label>
			<div class="formControls col-2">
				<font>${Log.id }</font> 
			</div>
			<label class="form-label col-2">操作人：</label>
			<div class="formControls col-2">
				<font>${Log.creatorName }</font> 
			</div>
			<label class="form-label col-2">操作类型：</label>
			<div class="formControls col-2">
				<c:if test="${Log.type==1}">
					<font>添加</font>
				</c:if>
				<c:if test="${Log.type==-1}">
					<font>删除</font>
				</c:if>
				<c:if test="${Log.type==0}">
					<font>修改</font>
				</c:if>
			</div>
		</div>
		<div class="row cl">
			
			<label class="form-label col-2">表单名称：</label>
			<div class="formControls col-2">
				<font>${Log.formName }</font>
			</div>
			<label class="form-label col-2">记录ID：</label>
			<div class="formControls col-2">
				<font>${Log.recordId }</font>
			</div>
			
		</div>
		<div class="row cl">
			
			<label class="form-label col-2">操作时间：</label>
			<div class="formControls">
			    <%-- <font> --%>
				<fmt:formatDate value="${Log.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				<%-- </font> --%>
			</div>
		</div>
		<div class="row cl banner_addDescribe" style="margin-top:10px;">
			<label class="form-label col-2">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
			<div class="formControls col-10">
				<font>${operationLog.remark }</font>
			</div>
		</div>
		<div class="row cl banner_addDescribe mt_10" style="word-break:break-all">
			<label class="form-label col-2">详细操作：</label>
			<div class="formControls col-10">
				<font>${Log.details }</font>
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