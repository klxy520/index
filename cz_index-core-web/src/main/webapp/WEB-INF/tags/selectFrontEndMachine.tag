<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/el_ex_tag" prefix="ex"%>

<%@ attribute name="key" required="true"%>
<%@ attribute name="id"%>
<%@ attribute name="name"%>
<%@ attribute name="classname"%>
<%@ attribute name="datatype"%>
<%@ attribute name="nullmsg"%>

<c:set var="id" value="${id}" />
<c:set var="name" value="${name}" />
<c:set var="key" value="${key}" />
<c:set var="classname" value="${classname}" />
<c:set var="nullmsg" value="${nullmsg}" />
<c:set var="datatype" value="${datatype}" />

<select id="${id}" name="${name}" class="${classname}"
	<c:if test="${not empty nullmsg}">nullmsg="${nullmsg}"</c:if>
	<c:if test="${not empty datatype}">datatype="${datatype}"</c:if>>
	<option value="">--请选择--</option>
	<c:forEach items="${ex:getFrontEndMachine()}" var="item">
		<option value="${item.id}">${item.frontEndMachinecode}</option>
	</c:forEach>
</select>
