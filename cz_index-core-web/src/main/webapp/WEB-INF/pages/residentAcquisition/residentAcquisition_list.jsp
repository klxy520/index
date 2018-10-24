<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>居民信息采集</title>
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
<link href="${contextPath}/css/loading.css" rel="stylesheet" type="text/css" />
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>居民信息管理 <span class="c-gray en">&gt;</span> 居民信息采集 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
    <!-- 隐藏的搜索模块 -->
    <div class="text-c form form-horizontal banner_xiala"> 
        <div class="row cl">
            <label class="form-label col-1">姓名：</label>
            <div class="formControls col-2">
                <input id="name" type="text" class="input-text" placeholder="请输入姓名">
            </div>
            <label class="form-label col-1">身份证号：</label>
            <div class="formControls col-2">
                <input id="identityNumber" type="text" class="input-text" placeholder="请输入身份证号">
            </div>
         <label class="form-label col-1">新农合号：</label>
            <div class="formControls col-2">
                <input id="newRuralNumber" type="text" class="input-text" placeholder="请输入银新农合号">
            </div>
            <label class="form-label col-1">社保卡号：</label>
            <div class="formControls col-2">
                <input id="socialSecurityNum" type="text" class="input-text" placeholder="请输入社保卡号">
            </div>
       </div>
        <div class="row cl">
            <label class="form-label col-1">发放银行：</label>
            <div class="formControls col-2">
               <jc:selectDictionaryCodeValueByKey key="bank_health_card" classname="input-text" id="healthCardBank"  name="bank_health_card"  />
            </div>
            <label class="form-label col-1">申办单位：</label>
            <div class="formControls col-2">
                <input id="bidUtil" type="text" class="input-text" placeholder="请输入申办单位">
            </div>
            <label class="form-label col-1">更新时间：</label>
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

            <jc:operationButton classname="btn btn-primary radius" onclick="addResidentAcquisition();" btnSn="0101020401" btnName="添加" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-secondary radius" onclick="updateResidentAcquisitionPage();" btnSn="0101020402" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
            <jc:operationButton classname="btn btn-danger radius" onclick="deleteResidentAcquisition();" btnSn="0101020403" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
            <jc:operationButton classname="btn btn-primary radius" onclick="residentacquisitionImport();" btnSn="0101020404" btnName="采集信息导入" icon="&#xe645;"></jc:operationButton>
        </span> 
    </div>
    <!-- list -->
    <div class="mt-10">
        <table class="table table-border table-bordered table-bg table-hover table-sort" id="residentAcquisitionList">
            <thead>
                <tr class="text-c">
                    <th width="3%"></th>
                    <th width="8%">姓名</th>
                    <th width="7%">身份证号码</th>
                    <th width="10%">证件有效截止日期</th>
                    <th width="12%">申办单位</th>
                    <th width="8%">新农合号</th>
                    <th width="7%">社保卡号</th>
                    <th width="12%">健康卡发放银行</th>
                    <th width="9%">创建时间</th>
                    <th width="9%">更新时间</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
<div id="addResidentAcquisition" class="pd-20" style="display:none;">
    <form action="${contextPath}/residentacquisition/addResidentAcquisition" method="post" class="form form-horizontal" id="addResidentAcquisitionForm">
        <input id="id" name="id" type="hidden">
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="name" name="name" placeholder="请输入居民姓名" datatype="name" errormsg="只能2-20个中文字符">
                </div>
                <label class="form-label col-2"> <span class="c-red">*</span>身份证号:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="identityNumber" name="identityNumber" placeholder="请输入身份证号" datatype="sfzh,exist_ID_datatype" errormsg="身份证号格式错误">
                </div>
            </div>
            
            <div class="row cl">
                 <label class="form-label col-2"><span class="c-red">*</span>申办单位:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="bidUtil" name="bidUtil" placeholder="请输入申办单位" datatype="address" errormsg="只允许2-20个中文字符">
                </div>
                <label class="form-label col-2">发证机关:</label>
                <div class="formControls col-4">
                     <input type="text" class="input-text" id="issuersCertificateOrgan" datatype="empty|name" name="issuersCertificateOrgan" placeholder="请输入发卡机关" errormsg="只允许2-20个中文字符">
                </div>
               
            </div>
            <div class="row cl">
                <label class="form-label col-2">新农合号:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text"   id="newRuralNumber" datatype="empty|newRuralNum" name="newRuralNumber" placeholder="请输入新农合号"  errormsg="只允许8-20位数字">
                </div>
                <label class="form-label col-2">社&nbsp;保&nbsp;号:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text" placeholder="请输入社保号"  datatype="empty|socialNum" errormsg="只允许9位数字" id="socialSecurityNum" name="socialSecurityNum">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">文化程度:</label>
                <div class="formControls col-4">
                      <jc:selectDictionaryCodeValueByKey key="education" classname="input-text" id="educationLevel"  name="educationLevel"  />
                </div>
                <label class="form-label col-2"><span class="c-red">*</span>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryCodeValueByKey key="nation" classname="input-text" id="national"  name="national" datatype="*" />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>联系电话:</label>
                <div class="formControls col-4">
                      <input type="text" class="input-text" id="contactPhone" name="contactPhone" placeholder="请输入联系电话" datatype="lxdh|tel" errormsg="联系电话格式错误">
                </div>
                 <label class="form-label col-2">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label>
                <div class="formControls col-4">    
                    <input type="text" class="input-text" id="postCode" name="postCode" placeholder="请输入邮编" datatype="empty|postCode6" errormsg="只允许6位数字">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">证件有效截止日期:</label>
                <div class="formControls col-4">
                    <input type="text" placeholder="请选择证件有效截止日期" class="input-text Wdate" id="certificateValidityPeriod" name="certificateValidityPeriod" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'9999-12-30'})"  />
                </div>
                <label class="form-label col-2">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label>
                <div class="formControls col-4">
                    <jc:selectDictionaryCodeValueByKey key="industry" id="industry"  name="industry" classname="input-text "></jc:selectDictionaryCodeValueByKey>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>健康卡发放银行:</label>
                <div class="formControls col-4">
                    <jc:selectDictionaryCodeValueByKey key="bank_health_card" classname="input-text" datatype="*" id="healthCardBank"  name="healthCardBank"  />
                </div>
                <label class="form-label col-2">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label>
                <div class="formControls col-4">    
                    <jc:selectDictionaryCodeValueByKey key="professional" id="professional"  name="professional" classname="input-text "></jc:selectDictionaryCodeValueByKey>
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>工资卡发放银行:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryCodeValueByKey key="bank_payroll_card" datatype="*" classname="input-text" id="salaryCardBank"  name="salaryCardBank"  />
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>户籍地址:</label>
            <div class="formControls col-10">
                <input id="houseAddress" name="houseAddress" type="text" placeholder="请输入户籍地址" errormsg="地址不得超过50个字符"   datatype="address" class="input-text"  >
            </div>          
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>现住地址:</label>
            <div class="formControls col-10">
                <input id="nowAddress" name="nowAddress" type="text" datatype="address" errormsg="地址不得超过50个字符" placeholder="请输入现住地址" class="input-text"  >
            </div>          
        </div>

        <div class="row cl">
            <div class="col-offset-5">
                <button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
                <button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
        </form>
    </div>
<div id="updateResidentAcquisition" class="pd-20" style="display:none;">
    <form action="${contextPath}/residentacquisition/updateResidentAcquisition" method="post" class="form form-horizontal" id="updateResidentAcquisitionForm">
        <input id="id" name="id" type="hidden">
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="name" name="name" placeholder="请输入居民姓名" datatype="name" errormsg="只能2-20个中文字符">
                </div>
                <label class="form-label col-2"> <span class="c-red">*</span>身份证号:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="identityNumber" name="identityNumber" placeholder="请输入身份证号" datatype="sfzh,exist_ID_datatype" errormsg="身份证号格式错误">
                </div>
            </div>
            
            <div class="row cl">
                 <label class="form-label col-2"><span class="c-red">*</span>申办单位:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="bidUtil" name="bidUtil" placeholder="请输入申办单位" datatype="address" errormsg="只允许2-20个中文字符">
                </div>
                <label class="form-label col-2">发证机关:</label>
                <div class="formControls col-4">
                     <input type="text" class="input-text" id="issuersCertificateOrgan" datatype="empty|name" name="issuersCertificateOrgan" placeholder="请输入发卡机关" errormsg="只允许2-20个中文字符">
                </div>
               
            </div>
            <div class="row cl">
                <label class="form-label col-2">新农合号:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text"   id="newRuralNumber" name="newRuralNumber" placeholder="请输入新农合号"  datatype="empty|newRuralNum"errormsg="只允许8-20位数字">
                </div>
                <label class="form-label col-2">社&nbsp;保&nbsp;号:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text" placeholder="请输入社保号"  datatype="empty|socialNum" errormsg="只允许9位数字" id="socialSecurityNum" name="socialSecurityNum">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">文化程度:</label>
                <div class="formControls col-4">
                      <jc:selectDictionaryCodeValueByKey key="education" classname="input-text" id="educationLevel"  name="educationLevel"  />
                </div>
                <label class="form-label col-2"><span class="c-red">*</span>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryCodeValueByKey key="nation" classname="input-text" id="national"  name="national" datatype="*" />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>联系电话:</label>
                <div class="formControls col-4">
                      <input type="text" class="input-text" id="contactPhone" name="contactPhone" placeholder="请输入联系电话" datatype="lxdh|tel" errormsg="联系电话格式错误">
                </div>
                 <label class="form-label col-2">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label>
                <div class="formControls col-4">    
                    <input type="text" class="input-text" id="postCode" name="postCode" placeholder="请输入邮编" datatype="empty|postCode6" errormsg="只允许6位数字">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">证件有效截止日期:</label>
                <div class="formControls col-4">
                    <input type="text" placeholder="请选择证件有效截止日期" class="input-text Wdate" id="certificateValidityPeriod" name="certificateValidityPeriod" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'9999-12-30'})"  />
                </div>
                <label class="form-label col-2">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label>
                <div class="formControls col-4">
                    <jc:selectDictionaryCodeValueByKey key="industry" id="industry"  name="industry" classname="input-text "></jc:selectDictionaryCodeValueByKey>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>健康卡发放银行:</label>
                <div class="formControls col-4">
                    <jc:selectDictionaryCodeValueByKey key="bank_health_card" classname="input-text" id="healthCardBank" datatype="*" name="healthCardBank"  />
                </div>
                <label class="form-label col-2">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label>
                <div class="formControls col-4">    
                    <jc:selectDictionaryCodeValueByKey key="professional" id="professional"  name="professional" classname="input-text "></jc:selectDictionaryCodeValueByKey>
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>工资卡发放银行:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryCodeValueByKey key="bank_payroll_card" datatype="*" classname="input-text" id="salaryCardBank"  name="salaryCardBank"  />
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>户籍地址:</label>
            <div class="formControls col-10">
                <input id="houseAddress" name="houseAddress" type="text" placeholder="请输入户籍地址" errormsg="地址不得超过50个字符"   datatype="address" class="input-text"  >
            </div>          
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>现住地址:</label>
            <div class="formControls col-10">
                <input id="nowAddress" name="nowAddress" type="text" datatype="address" errormsg="地址不得超过50个字符" placeholder="请输入现住地址" class="input-text"  >
            </div>          
        </div>

        <div class="row cl">
            <div class="col-offset-5">
                <button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
                <button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
        </form>
    </div>
<%--批量导入 --%>
 <div id="importResidentAcquisition" class="pd-20" style="display:none;">
    <jc:uploadexcel id="upFile" />
    <div class="row cl" style="padding-top: 10px;">
        <button id="batchImport_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 开始解析</button>
        <button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
        <B><label>模板下载：</label></B>
        <a classname="btn btn-primary radius" href="" id="residentAcquisition_a"><i class="Hui-iconfont">${icon}</i></a>
    </div>
</div>  
   <%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/residentAcquisition/residentAcquisition_list.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/commons/loading.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/commons/sonic.js?v=${appVerDate}"></script>
</body> 
</html>
