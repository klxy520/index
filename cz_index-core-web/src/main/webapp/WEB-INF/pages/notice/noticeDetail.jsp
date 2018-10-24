<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.form-horizontal .form-label {
    margin-top: 0px;
    cursor: text;
    text-align: right;
    padding-right: 10px;
}
.zm_break{
		word-break: break-all;
	}
</style>

<title>公告详情</title>

</head>
<body>
	<div class="pd-20">
	<%-- <p style="font-weight:blod;font-size: 15px;">公告详情:</p>--%>
		<div class="form form-horizontal" style="border:1px solid #BEBEBE">
			<div class="row cl">

				<label class="form-label col-2">公&nbsp;告&nbsp;编&nbsp;号：</label>
				<div class="formControls col-2">
					<font>${notice.id }</font>
				</div>
				<label class="form-label col-2">公告创建人：</label>
				<div class="formControls col-2">
					<font>${notice.creatorUser.name }</font>
				</div>
				<label class="form-label col-2">创&nbsp;建&nbsp;&nbsp;时间：</label>
				<div class="formControls col-2">
					<font><fmt:formatDate value="${notice.createDate }" pattern="yyyy-MM-dd " /></font>
				</div>
			</div>
			<div class="row cl">
			
				<label class="form-label col-2">最近修改人：</label>
				<div class="formControls col-2">
					<font>${notice.updatorUser.name }</font>
				</div>
				
				<label class="form-label col-2">修&nbsp;改&nbsp;时&nbsp;间：</label>
				<div class="formControls col-2">
					<font><fmt:formatDate value="${notice.updateDate }" pattern="yyyy-MM-dd " /></font>
				</div>

				<label class="form-label col-2">公&nbsp;告&nbsp;状&nbsp;态：</label>
				<div class="formControls col-2">
					<c:if test="${notice.status==1}">
						<font>启用</font>
					</c:if>
					<c:if test="${notice.status==0}">
						<font>禁用</font>
					</c:if>
				</div>

				

			</div>
			<div class="row cl">

				<label class="form-label col-2">启&nbsp;用&nbsp;时&nbsp;间：</label>
				<div class="formControls">
					<%-- <font> --%>
					<fmt:formatDate value="${notice.startTime}" pattern="yyyy-MM-dd " />
					&nbsp&nbsp&nbsp至&nbsp&nbsp&nbsp<fmt:formatDate value="${notice.endTime}" pattern="yyyy-MM-dd " />
					<%-- </font> --%>
				</div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-2">公&nbsp;告&nbsp;标&nbsp;题：</label>
				<div class="formControls">
					<font>${notice.title }</font>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">公&nbsp;告&nbsp;内&nbsp;容：</label>
				<div class="formControls zm_break col-9">
					<font>${notice.content }</font>
				</div>
			</div>
		</div>
		<div class="row cl button">
			<div class="col-offset-10 mt-10">
				<button onClick="layer_close();" class="btn btn-default radius mt-10" style="float:left"  type="button">关闭</button>
			</div>
		</div>		
	</div>
	<%@ include file="../commons/comm_const_js.jsp"%>
</body>
</html>