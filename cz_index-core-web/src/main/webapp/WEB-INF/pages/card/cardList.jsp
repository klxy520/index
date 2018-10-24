<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>卡务信息</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
<style type="text/css">
.form-horizontal .form-label {
    margin-top: 0px;
    cursor: text;
    text-align: right;
    padding-right: 10px;
}

</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 主索引服务 <span class="c-gray en">&gt;</span> 卡务信息 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-1 "  >姓名:</label>
			<div class="formControls col-2" >
				<input id="s_personName" type="text" class="input-text" placeholder="请输入姓名">
			</div>
			<label class="form-label col-1 " >身份证号:</label>
			<div class="formControls col-2" >
				<input id="s_id_card" type="text" class="input-text" placeholder="请输入身份证号">
			</div>
			<label class="form-label col-1 " >卡类别:</label>
			<div class="formControls col-2" >
			    <jc:selectDictionaryByKey_Code classname="input-text"   key="cardTypeCode" id="s_card_type_code" name="cardTypeCode"></jc:selectDictionaryByKey_Code>  
			</div>
			<label class="form-label col-1 " >卡状态:</label>
			<div class="formControls col-2" >
					<jc:selectDictionaryByKey_Code classname="input-text"  key="cardStatus"  id="s_status" name="status"></jc:selectDictionaryByKey_Code>     
			</div>	
		</div>
		<div class="row cl">
			<label class="form-label col-1 " >卡号:</label>
			<div class="formControls col-2" >
				<input id="s_card_no" type="text" class="input-text" placeholder="请输入卡号">
			</div>
			<label class="form-label col-1 " >卡内号:</label>
			<div class="formControls col-2" >
				<input type="text" id="s_card_code" class="input-text" placeholder="请输入卡内号">
			</div>	
			<label class="form-label col-1 "  >卡有效期:</label>
			<div class="formControls col-2" >
				<input type="text" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd '})" 
				id="s_valid_time1" class="input-text Wdate">
			</div>
			<span style="float:left;margin: 0 8px;">-</span>
			<div class="formControls col-2" >
				<input type="text" 
					 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ' ,minDate:'#F{$dp.$D(\'s_valid_time1\')}'})" 
				id="s_valid_time2" class="input-text Wdate">
			</div>		

		</div>

		<div class="row cl" >		
			<label class="form-label col-1 "  >修改时间:</label>
			<div class="formControls col-2" >
				<input type="text" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' ,maxDate:'#F{$dp.$D(\'s_update_date2\')}'})" 
				id="s_update_date1" class="input-text Wdate">
			</div>
			<span style="float:left;margin: 0 8px;">-</span>
			<div class="formControls col-2" >
				<input type="text" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'s_update_date1\')}' })"
				id="s_update_date2" class="input-text Wdate">
			</div>
		</div>
		<button id="search_btn" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
 <div class="cl pd-5 bg-1 bk-gray mt-10"> 
        <span class="l">
        	<jc:operationButton classname="btn btn-primary radius" onclick="addCard();" btnSn="0101050202" btnName="添加" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-secondary radius" onclick="updateCard();" btnSn="0101050203" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
            <jc:operationButton classname="btn btn-danger radius" onclick="deleteCard();" btnSn="0101050204" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
        </span> 
    </div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="cardList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>	
					<th width="10%">姓名</th>
					<th width="10%">身份证号</th>
					<th width="10%">卡号</th>
					<th width="10%">卡内号</th>
					<th width="10%">卡类别</th>
					<th width="10%">卡状态</th>
					<th width="10%">卡有效期</th>
					<th width="10%">创建时间</th>
					<th width="10%">修改时间</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<!--添加表单-->
<div id="addCard" class="pd-20" style="display:none;">
    <form action="${contextPath}/card/addCard" method="post" class="form form-horizontal" id="addCardFrom">
        <input id="personId"  name="personId" type="hidden" >
        <div class="row cl">
        	<label class="form-label col-2"><span class="c-red">*</span>主索引ID:</label>
              <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId"  name="mpiId" readonly="readonly"/>
               </div>
                <label class="form-label col-2"><span class="c-red">*</span>卡类别:</label>
	            <div class="formControls col-4">
             		<jc:selectDictionaryByKey_Code classname="input-text"  datatype="*" key="cardTypeCode" id="addcardTypeCode" name="cardTypeCode"></jc:selectDictionaryByKey_Code>  
	            </div>  
          </div>
            <div class="row cl">
             <label class="form-label col-2"><span class="c-red">*</span>卡号:</label>
              <div class="formControls col-4">
                    <input type="text" class="input-text" id="addCardNo" name="cardNo" datatype="cardNo" errormsg="卡号格式错误" placeholder="请输入卡号"/>
              </div>
               <label class="form-label col-2">卡内号:</label>
              <div class="formControls col-4">
                    <input type="text" class="input-text" id="cardCode" name="cardCode" datatype="d5_15" ignore="ignore" errormsg="卡内号格式错误" placeholder="请输入卡内号"/>
               </div>
            </div>
             <div class="row cl">
             <label class="form-label col-2"><span class="c-red">*</span>卡状态:</label>
	            <div class="formControls col-4">
	          		 <jc:selectDictionaryByKey_Code classname="input-text" datatype="*" key="cardStatus" id="addCardStatus" name="status"></jc:selectDictionaryByKey_Code>    	            
	            </div>   
                <label class="form-label col-2"><span class="c-red">*</span>卡有效期:</label>
                <div class="formControls col-4">
                    <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}'})" datatype="*" name ="validTime" id="validTime" class="input-text Wdate">                
              </div>
          </div>
       		<div class="row cl">
       		 <label class="form-label col-2">发卡机构:</label>
                <div class="formControls col-4">
                     <jc:selectHospital key="createUnit" classname="input-text" id="addCreateUnit" name="createUnit"></jc:selectHospital>
                </div>
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
	
	<div id="updateCard" class="pd-20" style="display:none;">
	<form action="${contextPath}/card/updateCard" method="post" class="form form-horizontal" id="updateCardFrom">
   		 <input id="id" name="id" type="hidden">
			<div class="row cl">
			   <label class="form-label col-2"><span class="c-red">*</span>主索引ID:</label>
               <div class="formControls col-4">
                    <input type="text" class="input-text" id="mpiId"  name="mpiId" readonly="readonly"/>
               </div>
                 <label class="form-label col-2"><span class="c-red">*</span>卡类别:</label>
	            <div class="formControls col-4">
           		  <jc:selectDictionaryByKey_Code classname="input-text"  key="cardTypeCode" datatype="*" id="cardTypeCode" name="cardTypeCode"></jc:selectDictionaryByKey_Code>   
	            </div> 
			</div>
			  <div class="row cl">
      	  <label class="form-label col-2"><span class="c-red">*</span>卡号:</label>
			   <div class="formControls col-4">
					<input type="text" class="input-text" id="cardNo" datatype="cardNo" name="cardNo" errormsg="卡号格式错误" placeholder="请输入卡号" />
				</div>
      	  		<label class="form-label col-2">卡内号:</label>
				<div class="formControls col-4">
					<input type="text" class="input-text" id="cardCode" datatype="d5_15" ignore="ignore" name="cardCode" errormsg="卡内号格式错误" placeholder="请输入卡内号" />
				</div>
               
          </div>
			<div class="row cl">
				 <label class="form-label col-2"><span class="c-red">*</span>卡状态:</label>
	            <div class="formControls col-4">
            		 <jc:selectDictionaryByKey_Code classname="input-text"  key="cardStatus" datatype="*" id="status" name="status"></jc:selectDictionaryByKey_Code>     
	            </div> 
               
                <label class="form-label col-2"><span class="c-red">*</span>卡有效期:</label>
                <div class="formControls col-4">
                    <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}'})"  name ="validTime" datatype="*" id="validTime" class="input-text Wdate">                
              </div>
            </div>
       		 <div class="row cl">
       		   <label class="form-label col-2">发卡机构:</label>
                <div class="formControls col-4">
                      <jc:selectHospital key="createUnit" classname="input-text" id="createUnit" name="createUnit"></jc:selectHospital>
                </div>
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
</div>

<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/card/card.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/mainIndex/person-list.js?v=${appVerDate}"></script>

</body>
</html>