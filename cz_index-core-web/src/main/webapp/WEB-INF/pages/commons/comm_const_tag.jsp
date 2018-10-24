<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>  
<%@ taglib uri="/el_ex_tag" prefix="ex" %>
<%@ taglib prefix="jc" tagdir="/WEB-INF/tags" %>
<%--下面是常量定义 --%>
<c:set var="appName" value="${pageContext.servletContext.servletContextName}"/><%-- 应用上下文，web.xml中display-name --%>
<c:set var="appVer" value="${initParam.appVersion}"/><%-- 应用版本，web.xml中appVersion --%>
<c:set var="appVerDate" value="${initParam.appVersionDate}"/><%-- 日期版本，web.xml中appVersionDate --%>
<c:set var="appPath" value="${pageContext.servletContext.contextPath}${initParam.prePath}"/><%-- 写页面绝对路径时使用，如果需要在模块和应用上下文增加一级路径时候，这个定义的价值就体现出来了 --%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/><%-- 写静态资源时使用 --%>
<c:set var="imagePath" value=""/><%-- 开发用,指向静态资源地址 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"><%-- js常量 --%>
	var appPath = '${appPath}';
	var contextPath = '${contextPath}';
	var appVerDate = '${appVerDate}';
	var imagePath='${imagePath}';
</script>
</head>
<body>
</body>
</html>
