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
<title>居民健康卡详情</title>
</head>
<body>
<div class="pd-20">
	<p style="font-weight:blod;font-size: 15px;">居民健康卡基本信息:</p>
	<div class="form form-horizontal" style="border:1px solid #BEBEBE">
		<div class="row cl">
			<label class="form-label col-2">居&nbsp;&nbsp;民&nbsp;&nbsp;姓&nbsp;&nbsp;名：</label>
			<div class="formControls col-3">
				<font>${residentBase.realName }</font> 
			</div>
		
		<label class="form-label col-2">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
			<div class="formControls col-3">
				<font>${residentBase.sex }</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;齡：</label>
			<div class="formControls col-3">
				<font>${residentBase.age }</font> 
			</div>
			<label class="form-label col-2">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</label>
			<div class="formControls col-3">
				<font>${residentBase.nation }</font>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">身&nbsp;&nbsp;份&nbsp;&nbsp;证&nbsp;&nbsp;号：</label>
			<div class="formControls col-3">
				<font>${residentBase.idNumber }</font>
			</div>
		
		<label class="form-label col-2">证件有效期：</label>
			<div class="formControls col-3">
				<font><fmt:formatDate value="${residentBase.periodValidityDate }" pattern="yyyy-MM-dd"/></font>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"> 居民健康卡号：</label>
			<div class="formControls col-3">
				<font>${residentBase.healthNumber }</font>
			</div>
		<label class="form-label col-2">社&nbsp;保&nbsp;卡&nbsp;号：</label>
			<div class="formControls col-3">
				<font>${residentBase.socialNumber}</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"> 行&nbsp;&nbsp;政&nbsp;&nbsp;机&nbsp;&nbsp;构：</label>
			<div class="formControls col-3">
				<font>${residentBase.office }</font>
			</div>
		<label class="form-label col-2">区&nbsp;域&nbsp;机&nbsp;构：</label>
			<div class="formControls col-3">
				<font>${residentBase.area}</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"> 联&nbsp;&nbsp;系&nbsp;&nbsp;电&nbsp;&nbsp;话：</label>
			<div class="formControls col-3">
				<font>${residentBase.phone }</font>
			</div>
		<label class="form-label col-2">工&nbsp;作&nbsp;单&nbsp;位：</label>
			<div class="formControls col-3">
				<font>${residentBase.wrokUnit}</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"> 邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</label>
			<div class="formControls col-3">
				<font>${residentBase.postCode }</font>
			</div>
		<label class="form-label col-2">创&nbsp;&nbsp;&nbsp;建&nbsp;&nbsp;&nbsp;人：</label>
			<div class="formControls col-3">
				<font>${residentBase.creator}</font> 
			</div>
		</div>
		<div class="row cl">
				<label class="form-label col-2">户&nbsp;&nbsp;&nbsp;籍&nbsp;&nbsp;&nbsp;地&nbsp;&nbsp;址:</label>
				<div class="formControls col-3">
					<font>${residentBase.houseAddress}</font>
				</div>			
			<label class="form-label col-2">现&nbsp;&nbsp;住&nbsp;&nbsp;地&nbsp;址:</label>
			<div class="formControls col-3">
					<font>${residentBase.nowAddress}</font>
			</div>			
		</div>
	</div>
	<div class="form form-horizontal">
				<div class="row cl">
			<div class="col-offset-9">
				<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;关闭&nbsp;&nbsp;</button>
			</div>
		</div>
	</div>
</div>
<%@ include file="../commons/comm_const_js.jsp"%>
</body>
</html>