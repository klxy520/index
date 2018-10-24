<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/el_ex_tag" prefix="ex" %>

<%@ attribute name="key" required="true" %>
<%@ attribute name="code"%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="name"%>
<%@ attribute name="classname"%>
<%@ attribute name="datatype"%>
<%@ attribute name="nullmsg"%>
<%@ attribute name="style"%>
<%@ attribute name="disabled"%>
<%@ attribute name="isdefault"%>

<c:set var="id" value="${id}"/>
<c:set var="name" value="${name}"/>
<c:set var="key" value="${key}"/>
<c:set var="code" value="${code }"/>
<c:set var="classname" value="${classname}"/>
<c:set var="nullmsg" value="${nullmsg}"/>
<c:set var="datatype" value="${datatype}"/>
<c:set var="style" value="${style}"/>
<c:set var="disabled" value="${disabled}"/>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>
<c:set var="isdefault" value="${isdefault}"/>

<!-- 
	注意id全局唯一
 -->

<select id="${id}" name="${name}" class="${classname}"
	<c:if test="${not empty style}">style="${style}"</c:if> 
	<c:if test="${not empty disabled}">disabled="${disabled}"</c:if> 
	<c:if test="${not empty nullmsg}">nullmsg="${nullmsg}"</c:if> 
	<c:if test="${not empty datatype}">datatype="${datatype}"</c:if> >
	<c:if test="${empty isdefault}">
		<option value="">--请选择--</option>
	</c:if>
</select>
<script type="text/javascript" src="${contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/commons/commons.js"></script>
<script type="text/javascript">
	var select_id = '${id}';
	var dictKey = '${key}';
	var dictCode = '${code}';
	var dictionaryList = window.top['_dictionaryList'];
	if(dictionaryList==null){
		fn_GetDictionary();
		dictionaryList = window.top['_dictionaryList'];
	}
	for(var i in dictionaryList){ 
		if(dictKey===dictionaryList[i].dictkey){
			// 获取二级字典
			var subDicitonaryList = dictionaryList[i].subDicitonaryList;
			for(var j in subDicitonaryList){
				var subOption = '<option value="'+subDicitonaryList[j].description+'">'+subDicitonaryList[j].value+'</option>'
				$("#"+select_id).append($(subOption));
			}
			break;
		}
	}
	if(typeof(dictCode) !== "undefined"){
		//id唯一
		$("#"+select_id).find("option[value='"+dictCode+"']").attr("selected",true);
	//	$("#"+select_id).val(dictCode);
	}
</script>