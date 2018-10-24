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
<title>居民信息采集详情</title>
</head>
<body>
	<div class="pd-20">
		<div class="form form-horizontal" style="border: 1px solid #BEBEBE">
			 <div class="row cl">
                <label class="form-label col-2">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                <div class="formControls col-4">
                   <font>${residentAcquisition.name }</font>
                </div>
                <label class="form-label col-2">身份证号:</label>
                <div class="formControls col-4">
                    <font>${residentAcquisition.identityNumber }</font>
                </div>
            </div>
            
            <div class="row cl">
                 <label class="form-label col-2">申办单位:</label>
                <div class="formControls col-4">
                   <font>${residentAcquisition.bidUtil }</font>
                </div>
                <label class="form-label col-2">发证机关:</label>
                <div class="formControls col-4">
                     <font>${residentAcquisition.issuersCertificateOrgan }</font>
                </div>
               
            </div>
            <div class="row cl">
                <label class="form-label col-2">新农合号:</label>
                <div class="formControls col-4">
                    <font>${residentAcquisition.newRuralNumber}</font>
                </div>
                <label class="form-label col-2">社&nbsp;保&nbsp;号:</label>
                <div class="formControls col-4">
                    <font>${residentAcquisition.socialSecurityNum}</font> 
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">文化程度:</label>
                <div class="formControls col-4">
                     <font>${residentAcquisition.educationLevel }</font> 
                </div>
                <label class="form-label col-2">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
                <div class="formControls col-4">
                     <font>${residentAcquisition.national }</font> 
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">联系电话:</label>
                <div class="formControls col-4">
                     <font>${residentAcquisition.contactPhone}</font>   
                </div>
                 <label class="form-label col-2">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label>
                     <font>${residentAcquisition.postCode}</font>  
                <div class="formControls col-4">    
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">证件有效截止日期:</label>
                <div class="formControls col-4">
                    <font>${residentAcquisition.certificateValidityPeriod}</font>
                </div>
                <label class="form-label col-2">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label>
                    <font>${residentAcquisition.industry }</font>
                <div class="formControls col-4">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">健康卡发放银行:</label>
                <div class="formControls col-4">
                    <font>${residentAcquisition.healthCardBank }</font>
                </div>
                <label class="form-label col-2">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label>
                <div class="formControls col-4">    
                     <font>${residentAcquisition.professional }</font> 
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2">工资卡发放银行:</label>
                <div class="formControls col-4">
                   <font>${residentAcquisition.salaryCardBank }</font>
                </div>
                <label class="form-label col-2">创&nbsp;建&nbsp;人:</label>
                <div class="formControls col-4">
                   <font>${residentAcquisition.creatorUser.name }</font>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">创建时间:</label>
                <div class="formControls col-4">
                  <font><fmt:formatDate value="${residentAcquisition.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
                </div>
                <label class="form-label col-2">修&nbsp;改&nbsp;人:</label>
                <div class="formControls col-4">
                   <font>${residentAcquisition.updatorUser.name }</font>
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2">户籍地址:</label>
            <div class="formControls col-4">
                <font>${residentAcquisition.houseAddress }</font>
            </div>
            <label class="form-label col-2">修改时间:</label>
                <div class="formControls col-4">
                  <font><fmt:formatDate value="${residentAcquisition.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
                </div>        
        </div>
        <div class="row cl">
            <label class="form-label col-2">现住地址:</label>
            <div class="formControls col-10">
                <font>${residentAcquisition.nowAddress }</font>
            </div>          
        </div>

			<div class="form form-horizontal">
				<div class="row cl">
					<div class="col-offset-9">
						<button onClick="layer_close();" class="btn btn-default radius"
							type="button">&nbsp;&nbsp;关闭&nbsp;&nbsp;</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../commons/comm_const_js.jsp"%>
</body>
</html>