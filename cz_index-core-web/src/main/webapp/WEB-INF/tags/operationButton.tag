<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/el_ex_tag" prefix="ex" %>
<%@ attribute name="btnSn" required="true"%>
<%@ attribute name="onclick" required="true"%>
<%@ attribute name="btnName" required="true"%>
<%@ attribute name="id"%>
<%@ attribute name="classname" required="true"%>
<%@ attribute name="href"%>
<%@ attribute name="icon"%>


<c:set var="btnSn" value="${btnSn}"/>
<c:set var="onclick" value="${onclick}"/>
<c:set var="btnName" value="${btnName}"/>
<c:set var="id" value="${id}"/>
<c:set var="classname" value="${classname}"/>
<c:set var="href" value="${href}"/>
<c:set var="icon" value="${icon}"/>


<c:if test="${not empty ex:checkMenuBySn(btnSn) }">
	<a class="${classname}" onclick="${onclick}" href="javascript:;"><i class="Hui-iconfont">${icon}</i> ${btnName}</a>
</c:if>

