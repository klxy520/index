<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/el_ex_tag" prefix="ex" %>

<%@ attribute name="key" required="true" %>
<%@ attribute name="id"%>
<%@ attribute name="name"%>
<%@ attribute name="classname"%>
<%@ attribute name="datatype"%>
<%@ attribute name="nullmsg"%>
<%@ attribute name="style"%>
<%@ attribute name="defaultValue"  %>

<c:set var="id" value="${id}"/>
<c:set var="name" value="${name}"/>
<c:set var="key" value="${key}"/>
<c:set var="classname" value="${classname}"/>
<c:set var="nullmsg" value="${nullmsg}"/>
<c:set var="datatype" value="${datatype}"/>
<c:set var="style" value="${style}"/>
<c:set var="defaultValue" value="${defaultValue}"/>


<select  id="${id}" name="${name}" class="${classname}"
	<c:if test="${not empty style}">style="${style}"</c:if> 
	<c:if test="${not empty nullmsg}">nullmsg="${nullmsg}"</c:if> 
	<c:if test="${not empty datatype}">datatype="${datatype}"</c:if> >
	
	<c:if test="${not empty defaultValue}"><option value="${defaultValue}">--请选择--</option></c:if>
	<c:if test="${empty defaultValue}"><option value=" ">--请选择--</option></c:if>
	
	<c:forEach items="${ex:dictList(key)}" var="item">
		<c:choose>
			<c:when test="${item.isdefault==1}">
				<option value="${item.value}" selected="selected">${item.value}</option>
			</c:when>
			<c:otherwise>
				<option value="${item.value}">${item.value}</option>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</select>