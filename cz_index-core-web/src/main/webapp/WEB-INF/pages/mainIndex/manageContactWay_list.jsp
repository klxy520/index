<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联系方式信息</title>
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
<nav class="breadcrumb">姓名:【${person.personName }】主索引ID:【${person.mpiId}】的联系方式管理
<input id="allPersonId" type="hidden" value="${person.id }">
<input id="allMpiId" type="hidden" value="${person.mpiId }">

</nav>
<div style="padding:0 20px 20px 20px;">

<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-1 "  >联系方式:</label>
			<div class="formControls col-2" >
				<jc:selectDictionaryByKey_Code classname="input-text" key="contactWayTypeCode" id="s_contactWayTypeCode" ></jc:selectDictionaryByKey_Code>   
			</div>
			<label class="form-label col-1 " >联系号码:</label>
			<div class="formControls col-2" >
				<input type="text" id="s_contactNo" class="input-text" placeholder="请输入联系号码">
			</div>
			 <label class="form-label col-1">修改时间:</label>
            <div class="formControls col-2">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'maxUpateDate\')||\'%y-%M-{%d} %H:%m:%s\'}'})" id="minUpateDate" class="input-text Wdate">
            </div>
            <span style="float:left;margin: 0 8px;">-</span>
            <div class="formControls col-2" style="margin-left:10px;">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'minUpateDate\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})" id=maxUpateDate class="input-text Wdate">
        	</div>
		</div>
		<button id="search_btn" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
 
    <!-- 删除  添加  编辑 -->
    <div class="cl pd-5 bg-1 bk-gray mt-10"> 
        <span class="l">
            <jc:operationButton classname="btn btn-primary radius" onclick="addContactWay();" btnSn="010105010901" btnName="添加" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-secondary radius" onclick="updateContactWay();" btnSn="010105010902" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
            <jc:operationButton classname="btn btn-danger radius" onclick="deleteContactWay();" btnSn="010105010903" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
        </span> 
    </div>
    <!-- list -->
    <div class="mt-10">
        <table class="table table-border table-bordered table-bg table-hover table-sort" id="personContactWayList">
            <thead>
                <tr class="text-c">
                    <th width="2%"></th>
                    <th width="24%">联系号码</th>
                   <th width="24%">联系方式</th>
                    <th width="24%">创建时间</th>
                    <th width="24%">修改时间</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
<!--添加表单-->
<div id="addContactWay" class="pd-20" style="display:none;">
    <form action="${contextPath}/contactWay/addContactWay" method="post" class="form form-horizontal" id="addContactWayFrom">
        <input id="personId"  name="personId" type="hidden">
        <div class="row cl">
        
        	 <label class="form-label col-2">主索引ID:</label>
              <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId"  name="mpiId" readonly="readonly" />
               </div>
              <label class="form-label col-2"><span class="c-red">*</span>联系方式:</label>
              <div class="formControls col-4">
                    <jc:selectDictionaryByKey_Code classname="input-text" isdefault="isdefault" datatype="*" key="contactWayTypeCode" id="contactTypeCode" name="contactTypeCode"></jc:selectDictionaryByKey_Code>         
               </div>
             
          </div>

          <div class="row cl">
          	<label class="form-label col-2"><span class="c-red">*</span>联系号码:</label>
              <div class="formControls col-4">
                    <input type="text" class="input-text" id="contactNo" name="contactNo" datatype="contactNo" placeholder="请输入联系号码"/>
              </div>
             
             <label class="form-label col-2">创建机构:</label>
              <div class="formControls col-4">
              	   <jc:selectHospital key="createUnit" classname="input-text" id="add_createUnit" name="createUnit"></jc:selectHospital>
              </div>

          </div>
          <div class="row cl">
             <label class="form-label col-2">修改机构:</label>
              <div class="formControls col-4">
              	   <jc:selectHospital key="createUnit" classname="input-text" id="add_LastModifyUnit" name="lastModifyUnit"></jc:selectHospital>
              </div>
          </div>
          
          
      	  <div class="row cl">
	          <div class="col-offset-5">
	                <button id="add_form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
	                <button onClick="layer.closeAll();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
	           </div>
        </div>
        </form>
       
    </div>
    
    
    <div id="updateContactWay" class="pd-20" style="display:none;">
    <form action="${contextPath}/contactWay/updateContactWay" method="post" class="form form-horizontal" id="updateContactWayFrom">
   		 <input id="id"  name="id" type="hidden">
   		 <div class="row cl">
   		 	 <label class="form-label col-2">主索引ID:</label>
              <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId"  name="mpiId" readonly="readonly" />
               </div>
            <label class="form-label col-2"><span class="c-red">*</span>联系方式:</label>
              <div class="formControls col-4">
                    <jc:selectDictionaryByKey_Code classname="input-text" datatype="*" isdefault="isdefault" key="contactWayTypeCode" id="u_contactTypeCode" name="contactTypeCode"></jc:selectDictionaryByKey_Code>         
               </div>
           </div>
            <div class="row cl">
             <label class="form-label col-2"><span class="c-red">*</span>联系号码:</label>
              <div class="formControls col-4">
                    <input type="text" class="input-text" id="u_contactNo" name="contactNo" datatype="contactNo" placeholder="请输入联系号码"/>
              </div>
             <label class="form-label col-2">创建机构:</label>
              <div class="formControls col-4">
              	   <jc:selectHospital key="createUnit" classname="input-text" id="u_createUnit" name="createUnit"></jc:selectHospital>
              </div>
          </div>
          <div class="row cl">
           <label class="form-label col-2">修改机构:</label>
              <div class="formControls col-4">
              	   <jc:selectHospital key="createUnit" classname="input-text" id="u_LastModifyUnit" name="lastModifyUnit"></jc:selectHospital>
              </div>
          </div>
      	  <div class="row cl">
            <div class="col-offset-5">
                <button id="update_form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
                <button onClick="layer.closeAll();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
       </form>
    </div>
   <%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/mainIndex/contactWayManage.js?v=${appVerDate}"></script>
</body> 
</html>
