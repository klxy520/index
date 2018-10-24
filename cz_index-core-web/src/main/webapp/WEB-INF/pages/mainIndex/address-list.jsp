<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>地址信息</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/select2.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav class="breadcrumb"><input id="personId" type="hidden" value="${person.id }">姓名:【${person.personName }】
    主索引ID:【${person.mpiId}】的地址管理</nav>
<div style="padding:0 20px 20px 20px;">
    <!-- 隐藏的搜索模块 -->
    <div class="text-c form form-horizontal banner_xiala" style="width:98%" > 
        <div class="row cl">
            <label class="form-label col-1">地址类别:</label>
            <div class="formControls col-2">
          <jc:selectDictionaryByKey_Code classname="input-text"  key="indexAddressTypeCode" id="indexaddressTypeCode" name="indexaddressTypeCode"></jc:selectDictionaryByKey_Code>
            </div>
            <label class="form-label col-1">邮编:</label>
            <div class="formControls col-2">
                <input id="indexpostalCode" type="text" class="input-text" placeholder="请输入邮编">
            </div>
                       <label class="form-label col-1">修改时间:</label>
            <div class="formControls col-2">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'maxUpateDate\')||\'%y-%M-{%d} %H:%m:%s\'}'})" id="minUpateDate" class="input-text Wdate">
            </div>
            <span style="float:left;margin: 0 8px;">-</span>
            <div class="formControls col-2" style="margin-left:10px;">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'minUpateDate\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})" id=maxUpateDate class="input-text Wdate">
        </div>
       </div>

        <button id="search_btn" onclick="search();" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
    </div>
    <div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
    <!-- 删除  添加  编辑 -->
    <div class="cl pd-5 bg-1 bk-gray mt-10"> 
        <span class="l">
            <jc:operationButton classname="btn btn-primary radius" onclick="addAddress();" btnSn="010105010601" btnName="添加" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-secondary radius" onclick="updateAddressPage();" btnSn="010105010602" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
            <jc:operationButton classname="btn btn-danger radius" onclick="deleteAddressById();" btnSn="010105010603" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
        </span> 
    </div>
    <!-- list -->
    <div class="mt-10">
        <input type="hidden" id="",value="">
        <table class="table table-border table-bordered table-bg table-hover table-sort" id="addressList">
            <thead>
                <tr class="text-c">
                    <th width="2%"></th>
                    <th width="9%">地址类别</th>
                    <th width="30%">详细地址</th>
                    <th width="7%">邮编</th>
                    <th width="12%">创建时间</th>
                    <th width="12%">修改时间</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
<!--添加表单-->
<div id="addAddress" class="pd-20" style="display:none;">
    <form action="${contextPath}/address/addAddress" method="post" class="form form-horizontal" id="addAddressForm">
            <div class="row cl">
                <label class="form-label col-2">主索引ID:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId" value="${person.mpiId}" readonly="readonly"   name="mpiId" placeholder="请输入主索引id" >
                </div>
                <label class="form-label col-2"> <span class="c-red">*</span>地址类别:</label>
                <div class="formControls col-4">
          <jc:selectDictionaryByKey_Code classname="input-text" datatype="*" key="indexAddressTypeCode" id="addressTypeCode" name="addressTypeCode"></jc:selectDictionaryByKey_Code>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">邮编:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="postalCode" datatype="postCode6|empty" name="postalCode" placeholder="请输入邮编" errormsg="邮编必须是6位数字 "/>
                </div>
                 <label class="form-label col-2">创建机构:</label>
                <div class="formControls col-4">
                    <jc:selectHospital key="createUnit" classname="input-text" id="createUnit" name="createUnit"></jc:selectHospital>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">修改机构:</label>
                <div class="formControls col-4">
                    <jc:selectHospital key="lastModifyUnit" classname="input-text" id="lastModifyUnit" name="lastModifyUnit"></jc:selectHospital>
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>详细地址:</label>
            <div class="formControls col-10">
                <input id="address" name="address" datatype="address" type="text" placeholder="请输入详细地址"   class="input-text" errormsg="只能输入1-50个字符" >
            </div>          
        </div>
        <div class="row cl">
            <div class="col-offset-5">
                <button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
                <button onClick="layer.closeAll();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
        </form>
    </div>
<!--编辑表单-->
<div id="updateAddress" class="pd-20" style="display:none;">
    <form action="${contextPath}/address/updateAddress" method="post" class="form form-horizontal" id="updateAddressForm">
        <input id="updateid" name="id" type="hidden">
        <input id="updatempiId" name="mpiId" type="hidden">
            <div class="row cl">
                <label class="form-label col-2">主索引ID:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId" value="${person.mpiId}" readonly="readonly"  placeholder="请输入主索引id" >
                </div>
                <label class="form-label col-2"><span class="c-red">*</span>地址类别:</label>
                <div class="formControls col-4">
                 <jc:selectDictionaryByKey_Code classname="input-text"  key="indexAddressTypeCode" datatype="*" id="updateaddressTypeCode" name="addressTypeCode"></jc:selectDictionaryByKey_Code>
                </div>
            </div>
            <div class="row cl">
                 <label class="form-label col-2">邮编:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="updatepostalCode" name="postalCode" datatype="postCode6|empty" placeholder="请输入邮编" errormsg="邮编必须是6位数字 "/>
                </div>
                 <label class="form-label col-2">创建机构:</label>
                <div class="formControls col-4">
                   <jc:selectHospital key="updatecreateUnit" classname="input-text" id="updatecreateUnit" name="createUnit"></jc:selectHospital>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">修改机构:</label>
                <div class="formControls col-4">
                     <jc:selectHospital key="updatelastModifyUnit" classname="input-text" id="updatelastModifyUnit" name="lastModifyUnit"></jc:selectHospital>
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>详细地址:</label>
            <div class="formControls col-10">
                <input id="updateaddress" name="address" type="text" placeholder="请输入详细地址"  datatype="address" class="input-text"  errormsg="只能输入1-50个字符" >
            </div>          
        </div>
        <div class="row cl">
            <div class="col-offset-5">
                <button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
                <button onClick="layer.closeAll();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
        </form>
    </div>

   <%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/mainIndex/address-list.js?v=${appVerDate}"></script>
</body> 
</html>
