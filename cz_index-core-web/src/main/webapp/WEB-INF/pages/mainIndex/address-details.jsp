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
<title>地址信息详情</title>
</head>
<body>
    <div class="pd-20">
        <div class="form form-horizontal" style="border: 1px solid #BEBEBE">
             <div class="row cl">
                <label class="form-label col-2">主索引ID:</label>
                <div class="formControls col-9">
                     <font>${address.mpiId}</font>
                </div>
            </div>
             <div class="row cl">
                <label class="form-label col-2">地址类别:</label>
                <div class="formControls col-4">
                 <jc:labelDictionaryByKey_Code key="indexAddressTypeCode" code="${address.addressTypeCode}" id="indexAddressTypeCode"></jc:labelDictionaryByKey_Code>
                </div>
                <label class="form-label col-2">邮编:</label>
                <div class="formControls col-4">
                   <font>${address.postalCode}</font>
                </div>
            </div>
            
            <div class="row cl">
                 <label class="form-label col-2">创建机构:</label>
                <div class="formControls col-4">
                   <jc:labelHospitalByKey key="${address.createUnit }"  id="createUnit"></jc:labelHospitalByKey>
                </div>
                <label class="form-label col-2">修改机构:</label>
                <div class="formControls col-4">
                    <jc:labelHospitalByKey key="${address.lastModifyUnit }"  id="lastModifyUnit"></jc:labelHospitalByKey>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">创建人:</label>
                <div class="formControls col-4">
                   <font>${address.creatorUser.name }</font>
                </div>
                <label class="form-label col-2">创建时间:</label>
                <div class="formControls col-4">
                  <font><fmt:formatDate value="${address.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">修改人:</label>
                <div class="formControls col-4">
                   <font>${address.updatorUser.name }</font>
                </div>
                <label class="form-label col-2">修改时间:</label>
                <div class="formControls col-4">
                  <font><fmt:formatDate value="${address.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
                </div>     
            </div>
        <div class="row cl">
            <label class="form-label col-2">详细地址:</label>
            <div class="formControls col-10">
                <font>${address.address}</font>
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