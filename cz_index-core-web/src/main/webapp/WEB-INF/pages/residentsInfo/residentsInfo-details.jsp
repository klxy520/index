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
<title>居民健康卡详情</title>
</head>
<body>
<div class="pd-20">
<!-- 	<p style="font-weight:blod;font-size: 15px;">居民健康卡基本信息:</p> -->
	<div class="form form-horizontal" style="border:1px solid #BEBEBE">
		<div class="row cl">
			<label class="form-label col-2">居&nbsp;&nbsp;民&nbsp;&nbsp;姓&nbsp;&nbsp;名：</label>
			<div class="formControls col-3">
				<font>${residentInfo.name }</font> 
			</div>
		
		<label class="form-label col-2">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
			<div class="formControls col-3">
				<font><jc:labelDictionaryByKey_Code id="sex" key="sex" code="${residentInfo.sex}"></jc:labelDictionaryByKey_Code></font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</label>
			<div class="formControls col-3 ">
				<font><jc:labelDictionaryByKey_Code id="nation" key="nation" code="${residentInfo.nationalCode }"></jc:labelDictionaryByKey_Code></font> 
			</div>
			<label class="form-label col-2">出&nbsp;&nbsp;生&nbsp;&nbsp;日&nbsp;&nbsp;期：</label>
			<div class="formControls col-3  ">
				<font>${residentInfo.birthday }</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">身&nbsp;&nbsp;份&nbsp;&nbsp;证&nbsp;&nbsp;号：</label>
			<div class="formControls col-3">
				<font>${residentInfo.identityNumber }</font>
			</div>
		
		<label class="form-label col-2">银&nbsp;&nbsp;行&nbsp;&nbsp;卡&nbsp;&nbsp;号：</label>
			<div class="formControls col-4">
				<font>${residentInfo.bankCardNumber }</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">卡&nbsp;&nbsp;的&nbsp;&nbsp;类&nbsp;&nbsp;别：</label>
			<div class="formControls col-3 ">
				<font><jc:labelDictionaryByKey_Code id="card_type" key="card_type" code="${residentInfo.cardType}"></jc:labelDictionaryByKey_Code></font> 
			</div>
			<label class="form-label col-2">社&nbsp;&nbsp;保&nbsp;&nbsp;卡&nbsp;&nbsp;号：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.socialSecurityNum}</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">申&nbsp;&nbsp;办&nbsp;&nbsp;单&nbsp;&nbsp;位：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.bidUtil }</font> 
       
			</div>
			<label class="form-label col-2 ">发&nbsp;&nbsp;卡&nbsp;&nbsp;银&nbsp;&nbsp;行：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.issuingBank }</font> 
			</div>
		</div>
		<div class="row cl">
		<label class="form-label col-2 "> 发卡机构证书：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.issuersCardCertificate }</font>          
			</div>	
			<label class="form-label col-2">发卡&nbsp;序&nbsp;列&nbsp;号：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.issuingSerialNumber }</font>      
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2 ">发卡机构名称：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.issuersCardName }</font> 
			</div>
			<label class="form-label col-2 "> 发卡机构代码：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.issuersCardCode }</font> 
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-2">成行同步状态：</label>
			<div class="formControls col-3 ">
       <font><jc:labelDictionaryByKey_Code id="card_sync_status1" key="card_sync_status" code="${residentInfo.cardSyncStatus1}"></jc:labelDictionaryByKey_Code></font> 
			</div>
			<label class="form-label col-2 "> 工行同步状态：</label>
			<div class="formControls col-3 ">
         <font><jc:labelDictionaryByKey_Code id="card_sync_status2" key="card_sync_status" code="${residentInfo.cardSyncStatus2}"></jc:labelDictionaryByKey_Code></font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">卡管同步状态：</label>
			<div class="formControls col-3 ">
       <font><jc:labelDictionaryByKey_Code id="card_sync_status3" key="card_sync_status" code="${residentInfo.cardSyncStatus3}"></jc:labelDictionaryByKey_Code></font> 
			</div>
			<label class="form-label col-2 "> 卡&nbsp;&nbsp;&nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.cardStatus }</font> 
         
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">发&nbsp;&nbsp;卡&nbsp;&nbsp;时&nbsp;&nbsp;间：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.issuingTime }</font> 
       
			</div>
			<label class="form-label col-2 ">卡&nbsp;&nbsp;有&nbsp;&nbsp;效&nbsp;&nbsp;期：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.cardValidityPeriod }</font> 
         
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">芯&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.chipNum }</font> 
			</div>
			<label class="form-label col-2 ">医疗支付方式：</label>
			<div class="formControls col-3 ">
				<font><jc:labelDictionaryByKey_Code id="medical_payment" key="medical_payment" code="${residentInfo.medicalPayment}"></jc:labelDictionaryByKey_Code></font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"> 行&nbsp;&nbsp;政&nbsp;&nbsp;机&nbsp;&nbsp;构：</label>
			<div class="formControls col-3">
				<font>${residentInfo.officeName }</font>
			</div>
		<label class="form-label col-2">区&nbsp;&nbsp;域&nbsp;&nbsp;机&nbsp;&nbsp;构：</label>
			<div class="formControls col-3">
				<font>${residentInfo.areaName}</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">文&nbsp;&nbsp;化&nbsp;&nbsp;程&nbsp;&nbsp;度：</label>
			<div class="formControls col-3 ">
				<font><jc:labelDictionaryByKey_Code id="education" key="education" code="${residentInfo.educationLevelCode}"></jc:labelDictionaryByKey_Code></font> 
			</div>
		<label class="form-label col-2 "> 婚&nbsp;&nbsp;姻&nbsp;&nbsp;状&nbsp;&nbsp;况：</label>
			<div class="formControls col-3 ">
				<font><jc:labelDictionaryByKey_Code id="marital_status" key="marital_status" code="${residentInfo.maritalStatusCode}"></jc:labelDictionaryByKey_Code></font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2 ">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</label>
			<div class="formControls col-3 ">
			<font><jc:labelDictionaryByKey_Code id="professional" key="professional" code="${residentInfo.professionalCode}"></jc:labelDictionaryByKey_Code></font> 
			</div>
		<label class="form-label col-2 ">本&nbsp;&nbsp;人&nbsp;&nbsp;电&nbsp;&nbsp;话：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.selfPhone }</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">联系&nbsp;人&nbsp;姓&nbsp;名：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.contactName }</font> 
			</div>
			<label class="form-label col-2 ">联系人&nbsp;&nbsp;关&nbsp;系：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.contactRelation }</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">联系&nbsp;人&nbsp;电&nbsp;话：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.contactPhone }</font> 
			</div>
			<label class="form-label col-2">创&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;建&nbsp;&nbsp;&nbsp;&nbsp;人：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.creatorName }</font> 
			</div>
		</div>
		<div class="row cl">
		<label class="form-label col-2 ">创&nbsp;&nbsp;建&nbsp;&nbsp;时&nbsp;&nbsp;间：</label>
			<div class="formControls col-3 ">
				<font><fmt:formatDate value="${residentInfo.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
			</div>
			<label class="form-label col-2 ">修&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;改&nbsp;&nbsp;&nbsp;&nbsp;人：</label>
			<div class="formControls col-3 ">
				<font>${residentInfo.updatorName }</font> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2 ">修&nbsp;&nbsp;改&nbsp;&nbsp;时&nbsp;&nbsp;间：</label>
			<div class="formControls col-3 ">
				<font><fmt:formatDate value="${residentInfo.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
			</div>
				<label class="form-label col-2">户&nbsp;&nbsp;籍&nbsp;&nbsp;地&nbsp;&nbsp;址：</label>
				<div class="formControls col-3">
					<font>${residentInfo.houseAddress}</font>
				</div>					
		</div>
		<div class="row cl">	
			<label class="form-label col-2">现&nbsp;&nbsp;住&nbsp;&nbsp;地&nbsp;&nbsp;址：</label>
			<div class="formControls col-3">
					<font>${residentInfo.nowAddress}</font>
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