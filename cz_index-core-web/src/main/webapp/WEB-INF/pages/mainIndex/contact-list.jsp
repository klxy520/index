<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联系人信息</title>
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
    主索引ID:【${person.mpiId}】的联系人管理</nav>
<div style="padding:0 20px 20px 20px;">
    <!-- 隐藏的搜索模块 -->
    <div class="text-c form form-horizontal banner_xiala"style="width:98%"> 
        <div class="row cl">
            <label class="form-label col-1">证件类别:</label>
            <div class="formControls col-2">
          <jc:selectDictionaryByKey_Code classname="input-text"  key="indexCertificateTypeCode" id="indexCertificateTypeCode" name="indexCertificateTypeCode"></jc:selectDictionaryByKey_Code>
            </div>
            <label class="form-label col-1">证件号码:</label>
            <div class="formControls col-2">
                <input id="indexcertificateNo" type="text" class="input-text" placeholder="请输入证件号码">
            </div>
            <label class="form-label col-1">姓名:</label>
            <div class="formControls col-2">
          <input id="indexcontactName" type="text" class="input-text" placeholder="请输入姓名">
            </div>
            <label class="form-label col-1">电话号码:</label>
            <div class="formControls col-2">
                <input id="indexcontactNo" type="text" class="input-text" placeholder="请输入电话号码">
            </div>
             <!--  <label class="form-label col-1">更新时间:</label>
            <div class="formControls col-2">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'maxUpateDate\')||\'%y-%M-{%d} %H:%m:%s\'}'})" id="minUpateDate" class="input-text Wdate">
            </div>
            <span style="float:left;margin: 0 8px;">-</span>
            <div class="formControls col-2" style="margin-left:10px;">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'minUpateDate\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})" id=maxUpateDate class="input-text Wdate">
        </div> -->
       </div>

        <button id="search_btn" onclick="search();" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
    </div>
    <div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
    <!-- 删除  添加  编辑 -->
    <div class="cl pd-5 bg-1 bk-gray mt-10"> 
        <span class="l">
            <jc:operationButton classname="btn btn-primary radius" onclick="addContact();" btnSn="010105010701" btnName="添加" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-secondary radius" onclick="updateContactPage();" btnSn="010105010702" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
            <jc:operationButton classname="btn btn-danger radius" onclick="deleteContactById();" btnSn="010105010703    " btnName="删除" icon="&#xe6e2;"></jc:operationButton>
        </span> 
    </div>
    <!-- list -->
    <div class="mt-10">
        <input type="hidden" id="",value="">
        <table class="table table-border table-bordered table-bg table-hover table-sort" id="contactList">
            <thead>
                <tr class="text-c">
                    <th width="2%"></th>
                    <th width="10%">证件类别</th>
                    <th width="16%">证件号码</th>
                    <th width="10%">姓名</th>
                    <th width="10%">电话号码</th>
                    <th width="12%">创建时间</th>
                    <th width="12%">修改时间</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
<!--添加表单-->
<div id="addContact" class="pd-20" style="display:none;">
    <form action="${contextPath}/contact/addContact" method="post" class="form form-horizontal" id="addContactForm">
            <div class="row cl">
                <label class="form-label col-2">主索引ID:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId" value="${person.mpiId}" name="mpiId" readonly="readonly" placeholder="请输入主索引id" errormsg="只能2-20个中文字符">
                </div>
                 <label class="form-label col-2"><span class="c-red">*</span>姓名:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="contactName" name="contactName" placeholder="请输入姓名"  datatype="name" errormsg="只允许2-20个中文字符">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"> <span class="c-red">*</span>证件类别:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code classname="input-text" isdefault="isdefault" datatype="*" key="indexCertificateTypeCode" id="certificateTypeCode" name="certificateTypeCode"></jc:selectDictionaryByKey_Code>
                </div>
                <label class="form-label col-2"><span class="c-red">*</span> 证件号码:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="certificateNo" name="certificateNo" datatype="certificateNoVailType" errormsg="证件号码只能输入字母和数字" placeholder="请输入证件号码"/>
                </div>
            </div>
            <div class="row cl">
                 <label class="form-label col-2">创建机构:</label>
                <div class="formControls col-4">
                    <jc:selectHospital key="createUnit" classname="input-text" id="createUnit" name="createUnit"></jc:selectHospital>
                </div>
                 <label class="form-label col-2">修改机构:</label>
                <div class="formControls col-4">
                    <jc:selectHospital key="lastModifyUnit" classname="input-text" id="lastModifyUnit" name="lastModifyUnit"></jc:selectHospital>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>电话号码:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text"  errormsg="电话号码格式不正确" datatype="tel" id="contactNo" name="contactNo" placeholder="请输入电话号码" >
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
<div id="updateContact" class="pd-20" style="display:none;">
    <form action="${contextPath}/contact/updateContact" method="post" class="form form-horizontal" id="updateContactForm">
        <input id="updateid" name="id" type="hidden">
        <input id="updatempiId" name="mpiId" type="hidden">
            <div class="row cl">
                 <label class="form-label col-2">主索引ID:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId" value="${person.mpiId}"  readonly="readonly" placeholder="请输入主索引id" errormsg="只能2-20个中文字符">
                </div>
                <label class="form-label col-2"><span class="c-red">*</span>姓名:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="updatecontactName" name="contactName" placeholder="请输入姓名" datatype="name"  errormsg="只允许2-20个中文字符">
                </div>
                
            </div>
            <div class="row cl">
                <label class="form-label col-2"> <span class="c-red">*</span>证件类别:</label>
                <div class="formControls col-4">
              <jc:selectDictionaryByKey_Code classname="input-text"  key="indexCertificateTypeCode" datatype="*" id="updatecertificateTypeCode" name="certificateTypeCode"></jc:selectDictionaryByKey_Code>
                </div>
                <label class="form-label col-2"><span class="c-red">*</span>证件号码:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="updatecertificateNo"  name="certificateNo" datatype="certificateNoVailType" placeholder="请输入证件号码"datatype="n0" errormsg="证件号码只能输入字母和数字"/>
                </div>
            </div>
            <div class="row cl">
                 <label class="form-label col-2">创建机构:</label>
                <div class="formControls col-4">
                    <jc:selectHospital key="createUnit" classname="input-text" id="updatecreateUnit" name="createUnit"></jc:selectHospital>
                </div>
                 <label class="form-label col-2">修改机构:</label>
                <div class="formControls col-4">
                    <jc:selectHospital key="lastModifyUnit" classname="input-text" id="updatelastModifyUnit" name="lastModifyUnit"></jc:selectHospital>
                </div>
            </div>
            <div class="row cl">
                 <label class="form-label col-2"><span class="c-red">*</span>电话号码:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text" errormsg="电话号码格式不正确" datatype="tel" id="updatecontactNo" name="contactNo" placeholder="请输入电话号码" >
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
<script type="text/javascript" src="${contextPath}/js/mainIndex/contact-list.js?v=${appVerDate}"></script>
</body> 
</html>
