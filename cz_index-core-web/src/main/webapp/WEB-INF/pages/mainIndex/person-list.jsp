<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本身份信息</title>
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>主索引服务<span class="c-gray en">&gt;</span>基本身份信息<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
    <!-- 隐藏的搜索模块 -->
    <div class="text-c form form-horizontal banner_xiala"> 
        <div class="row cl">
            <label class="form-label col-1">姓名:</label>
            <div class="formControls col-2">
                <input id="indexName" type="text" class="input-text" placeholder="请输入姓名">
            </div>
            <label class="form-label col-1">身份证号:</label>
            <div class="formControls col-2">
                <input id="indexIdCard" type="text" class="input-text" placeholder="请输入身份证号">
            </div>
            <label class="form-label col-1">一卡通号:</label>
            <div class="formControls col-2">
                <input id="indexCardNo" type="text" class="input-text" placeholder="请输入一卡通号">
            </div>
         <label class="form-label col-1">性别:</label>
            <div class="formControls col-2">
              <jc:selectDictionaryByKey_Code classname="input-text"  key="indexSex" id="indexSex"></jc:selectDictionaryByKey_Code>
            </div>
       </div>
        <div class="row cl">
            <label class="form-label col-1">状态:</label>
            <div class="formControls col-2">
              <jc:selectDictionaryByKey_Code classname="input-text"  key="indexIdentityStatus" id="indexIdentityStatus"></jc:selectDictionaryByKey_Code>
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
            <jc:operationButton classname="btn btn-primary radius" onclick="addPerson();" btnSn="0101050101 " btnName="添加" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-secondary radius" onclick="updateupdatePersonPage();" btnSn="0101050102" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
            <jc:operationButton classname="btn btn-danger radius" onclick="deletePerson();" btnSn="0101050103" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
            <jc:operationButton classname="btn btn-success radius" onclick="recoveryPersonById();" btnSn="0101050104" btnName="恢复" icon="&#xe6dc;"></jc:operationButton>
            <jc:operationButton classname="btn btn-default radius" onclick="cancelPersonById();" btnSn="0101050105" btnName="注销" icon="&#xe6de;"></jc:operationButton>
            <jc:operationButton classname="btn btn-warning radius" onclick="addAddress();" btnSn="0101050106" btnName="地址管理" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-warning radius" onclick="addcontact();" btnSn="0101050107" btnName="联系人管理" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-warning radius" onclick="manageCertificate();" btnSn="0101050108" btnName="证件管理" icon="&#xe600;"></jc:operationButton>       
            <jc:operationButton classname="btn btn-warning radius" onclick="manageContactWay();" btnSn="0101050109" btnName="联系方式管理" icon="&#xe600;"></jc:operationButton>
      
        </span> 
    </div>
    <!-- list -->
    <div class="mt-10">
        <table class="table table-border table-bordered table-bg table-hover table-sort" id="personList">
            <thead>
                <tr class="text-c">
                    <th width="2%"></th>
                    <th width="7%">主索引ID</th>
                    <th width="7%">姓名</th>
                    <th width="8%">性别</th>
                    <th width="7%">身份证号</th>
                    <th width="9%">一卡通号</th>
                    <th width="9%">出生日期</th>
                    <th width="6%">状态</th>
                    <th width="12%">创建时间</th>
                    <th width="12%">修改时间</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
<!--添加表单-->
<div id="addPerson" class="pd-20" style="display:none;">
    <form action="${contextPath}/person/addPerson" method="post" class="form form-horizontal" id="addPersonForm">
        <input id="id" name="id" type="hidden">
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="updatepersonName" name="personName" placeholder="请输入姓名" datatype="name" errormsg="只能2-20个中文字符">
                </div>
                <label class="form-label col-2"> <span class="c-red">*</span>性别:</label>
                <div class="formControls col-4">
                   <jc:selectDictionaryByKey_Code classname="input-text"  key="indexSex" id="sexCode" datatype="*" name="sexCode"></jc:selectDictionaryByKey_Code>
                </div>
            </div>
            
            <div class="row cl">
                 <label class="form-label col-2"><span class="c-red">*</span>身份证号:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="idCard" name="idCard" placeholder="请输入身份证号" datatype="sfzh,exist_ID_datatype" errormsg="身份证号格式错误">
                </div>
                <label class="form-label col-2">状态:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code classname="input-text"  key="indexIdentityStatus" id="status" name="status" disabled="disabled"></jc:selectDictionaryByKey_Code>
                </div>
               
            </div>
            <div class="row cl">
                <label class="form-label col-2">一卡通号:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text"   id="cardNo" name="cardNo" placeholder="请输入一卡通号"  datatype="empty|n0" errormsg="只能输入8-20位字母或数字">
                </div>
                <label class="form-label col-2">民族:</label>
                <div class="formControls col-4">
                      <jc:selectDictionaryByKey_Code key="nation" classname="input-text" id="nationCode"  name="nationCode"  />
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>出生日期:</label>
                <div class="formControls col-4">
                     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" id="birthday" placeholder="请选择出生日期"name="birthday" datatype="*" class="input-text Wdate">
                </div>
                <label class="form-label col-2">血型:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexBloodTypeCode" classname="input-text" id="bloodTypeCode"  name="bloodTypeCode" />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">文化程度:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexEducationCode" classname="input-text" id="educationCode"  name="educationCode" />
                </div>
                 <label class="form-label col-2">RH血型:</label>
                <div class="formControls col-4">    
                     <jc:selectDictionaryByKey_Code key="indexRhBloodCode" classname="input-text" id="rhBloodCode"  name="rhBloodCode"  />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">婚姻状况:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexMaritalStatus" classname="input-text" id="maritalStatusCode"  name="maritalStatusCode" />
                </div>
                <label class="form-label col-2">国籍:</label>
                <div class="formControls col-4">
                    <jc:selectDictionaryByKey_Code key="indexNationalityCode" id="nationalityCode"  name="nationalityCode" classname="input-text "></jc:selectDictionaryByKey_Code>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">本人电话:</label>
                <div class="formControls col-4">
                      <input type="text" class="input-text" id="contactNo"  name="contactNo" placeholder="请输入本人电话" datatype="empty|tel" errormsg="本人电话格式不对">
                </div>
                <label class="form-label col-2">户籍标志:</label>
                <div class="formControls col-4">    
                    <jc:selectDictionaryByKey_Code key="indexRegisteredPermanent" id="registeredPermanent"  name="registeredPermanent" classname="input-text "></jc:selectDictionaryByKey_Code>
                </div>
             </div>
            <div class="row cl">
            
                <label class="form-label col-2">保险类别:</label>
                
                <div class="formControls col-4">    
                    <jc:selectDictionaryByKey_Code key="indexInsuranceType" classname="input-text" id="insuranceType"  name="insuranceType"  />
                </div>
                <label class="form-label col-2">医保类别:</label>
                <div class="formControls col-4">    
                    <jc:selectDictionaryByKey_Code key="indexInsuranceCode" id="insuranceCode"  name="insuranceCode" classname="input-text "></jc:selectDictionaryByKey_Code>
                </div>
                
             </div>
            <div class="row cl">
	            <label class="form-label col-2">工作类别:</label>
	            <div class="formControls col-4">
	                     <jc:selectDictionaryByKey_Code key="workCode"  classname="input-text" id="workCode"  name="workCode"  />
	                </div>
	            <label class="form-label col-2">工作单位:</label>
	            <div class="formControls col-4">
	                <input id="workPlace" name="workPlace" type="text" placeholder="请输入工作单位"  datatype="empty|bidUtil" errormsg="工作单位只能输入0-20位字符" class="input-text"  >
	            </div>          
        </div>
            <div class="row cl">
                <label class="form-label col-2">开始工作日期:</label>
                <div class="formControls col-4">
                     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" id="startWorkDate" placeholder="请选择开始工作日期"name="startWorkDate" class="input-text Wdate">
                </div>
                <label class="form-label col-2"><span class="c-red">*</span>死亡标记:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexInDeceasedInd"  datatype="*" classname="input-text" id="deceasedInd" name="deceasedInd"  />
                </div>
            </div>
            <div class="row cl"  style="display:none" id="deceasedTimediv">
                <label class="form-label col-2"><span class="c-red">*</span> 死亡时间:</label>
                <div class="formControls col-4">
                     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" datatype="*" id="deceasedTime" placeholder="请选择死亡时间"name="deceasedTime" class="input-text Wdate">
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2">户籍地址:</label>
            <div class="formControls col-10">
                <input id="address" name="address"  datatype="empty|address" errormsg="户籍地址只能输入0-50位字符" type="text" placeholder="请输入户籍地址"   class="input-text"  >
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
<!--编辑表单-->
<div id="updatePerson" class="pd-20" style="display:none;">
    <form action="${contextPath}/person/updatePerson" method="post" class="form form-horizontal" id="updatePersonForm">
        <input id="updateid" name="id" type="hidden">
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="updatepersonName" name="personName" placeholder="请输入姓名" datatype="name" errormsg="只能2-20个中文字符">
                </div>
                <label class="form-label col-2"> <span class="c-red">*</span>性别:</label>
                <div class="formControls col-4">
                   <jc:selectDictionaryByKey_Code classname="input-text"  key="indexSex" id="updatesexCode" name="sexCode"></jc:selectDictionaryByKey_Code>
                </div>
            </div>
            
            <div class="row cl">
                 <label class="form-label col-2"><span class="c-red">*</span>身份证号:</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" id="updateidCard" name="idCard" placeholder="请输入身份证号" datatype="sfzh,exist_ID_datatype" errormsg="身份证号格式错误">
                </div>
                <label class="form-label col-2">状态:</label>
                <div class="formControls col-4">
                       <jc:selectDictionaryByKey_Code classname="input-text" disabled="disabled"  key="indexIdentityStatus" id="updatestatus" name="status"></jc:selectDictionaryByKey_Code> 
                </div>
               
            </div>
            <div class="row cl">
                <label class="form-label col-2">一卡通号:</label>
                <div class="formControls col-4">
                    <input   type="text" class="input-text"   id="updatecardNo" name="cardNo" placeholder="请输入一卡通号" datatype="empty|n0" errormsg="只能输入8-20位字母或数字" >
                </div>
                <label class="form-label col-2">民族:</label>
                <div class="formControls col-4">
                      <jc:selectDictionaryByKey_Code key="nation" classname="input-text" id="updatenationCode"  name="nationCode"  />
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>出生日期:</label>
                <div class="formControls col-4">
                     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" id="updatebirthday" placeholder="请选择出生日期"name="birthday" class="input-text Wdate">
                </div>
                 <label class="form-label col-2">国籍:</label>
                <div class="formControls col-4">
                    <jc:selectDictionaryByKey_Code key="indexNationalityCode" id="updatenationalityCode"  name="nationalityCode" classname="input-text "></jc:selectDictionaryByKey_Code>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">文化程度:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexEducationCode" classname="input-text" id="updateeducationCode"  name="educationCode" />
                </div>
                 <label class="form-label col-2">血型:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexBloodTypeCode" classname="input-text" id="updatebloodTypeCode"  name="bloodTypeCode" />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">婚姻状况:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexMaritalStatus" classname="input-text" id="updatemaritalStatusCode"  name="maritalStatusCode" />
                </div>
                <label class="form-label col-2">RH血型:</label>
                <div class="formControls col-4">    
                     <jc:selectDictionaryByKey_Code key="indexRhBloodCode" classname="input-text" id="updaterhBloodCode"  name="rhBloodCode"  />
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">本人电话:</label>
                <div class="formControls col-4">
                      <input type="text" class="input-text" id="updatecontactNo"  name="contactNo" placeholder="请输入本人电话" datatype="empty|tel" errormsg="本人电话格式不对">
                </div>
                <label class="form-label col-2">户籍标志:</label>
                <div class="formControls col-4">    
                    <jc:selectDictionaryByKey_Code key="indexRegisteredPermanent" id="updateregisteredPermanent"  name="registeredPermanent" classname="input-text "></jc:selectDictionaryByKey_Code>
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2">保险类别:</label>
                <div class="formControls col-4">
                    <jc:selectDictionaryByKey_Code key="indexInsuranceType" classname="input-text" id="updateinsuranceType"  name="insuranceType"  />
                </div>
                <label class="form-label col-2">医保类别:</label>
                <div class="formControls col-4">    
                    <jc:selectDictionaryByKey_Code key="indexInsuranceCode" id="updateinsuranceCode"  name="insuranceCode" classname="input-text "></jc:selectDictionaryByKey_Code>
                </div>
             </div>
            <div class="row cl">
            
	            <label class="form-label col-2">工作类别:</label>
	            <div class="formControls col-4">
	                     <jc:selectDictionaryByKey_Code key="workCode"  classname="input-text" id="updateworkCode"  name="workCode"  />
	                </div>
	            <label class="form-label col-2">工作单位:</label>
	            <div class="formControls col-4">
	                <input id="updateworkPlace" name="workPlace" type="text" placeholder="请输入工作单位"   datatype="empty|bidUtil" errormsg="工作单位只能输入0-20位字符" class="input-text"  >
	            </div>          
        </div>
            <div class="row cl">
                <label class="form-label col-2">开始工作日期:</label>
                <div class="formControls col-4">
                     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" id="updatestartWorkDate" placeholder="请选择开始工作日期"name="startWorkDate" class="input-text Wdate">
                </div>
                <label class="form-label col-2"><span class="c-red">*</span>死亡标记:</label>
                <div class="formControls col-4">
                     <jc:selectDictionaryByKey_Code key="indexInDeceasedInd"  classname="input-text" id="updatedeceasedInd" datatype="*" name="deceasedInd"  />
                </div>
            </div>
            <div class="row cl" style="display:none" id="deceasedTimediv1">
                <label class="form-label col-2"><span class="c-red">*</span>死亡时间:</label>
                <div class="formControls col-4">
                     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"  datatype="*" id="updatedeceasedTime" placeholder="请选择死亡时间"name="deceasedTime" class="input-text Wdate">
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2">户籍地址:</label>
            <div class="formControls col-10">
                <input id="updateaddress" name="address" datatype="empty|address" errormsg="户籍地址只能输入0-50位字符" type="text" placeholder="请输入户籍地址"    class="input-text"  >
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
   <%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/mainIndex/person-list.js?v=${appVerDate}"></script>
</body> 
</html>
