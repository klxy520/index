<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/el_ex_tag" prefix="ex" %>

<%@ attribute name="key" required="true"%>
<%@ attribute name="id" required="true"%>
<%@ attribute name="classname"%>
<%@ attribute name="nullmsg"%>
<%@ attribute name="style"%>

<c:set var="id" value="${id}"/>
<c:set var="key" value="${key}"/>
<c:set var="classname" value="${classname}"/>
<c:set var="nullmsg" value="${nullmsg}"/>
<c:set var="style" value="${style}"/>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>

<font id="${id}" class="${classname}"
	<c:if test="${not empty style}">style="${style}"</c:if> 
	<c:if test="${not empty nullmsg}">nullmsg="${nullmsg}"</c:if> >
</font>

<script type="text/javascript" src="${contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/commons/commons.js"></script>
<script type="text/javascript">
	var span_id = '${id}';
	var dictKey = '${key}';
	var strText = fn_getHospitalByKey(dictKey);
	$("#"+span_id).text(strText === "暂无数据" ? "" : strText);
</script>