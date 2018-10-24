<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
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

<style type="text/css">
.select2-container--default .select2-selection--single {
    border-radius: 0px; 
    border: solid 1px #ddd;
} 
</style>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>居民信息管理 <span class="c-gray en">&gt;</span> 基本信息管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
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
			<label class="form-label col-1">申办单位：</label>
			<div class="formControls col-2">
				<input id="bidUtil"  type="text" class="input-text" placeholder="请输入申办单位">
			</div>
			<label class="form-label col-1">卡的类别：</label>
			<div class="formControls col-2">
			<jc:selectDictionaryByKey_Code classname="input-text"  key="card_type" id="cardType1"></jc:selectDictionaryByKey_Code>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-1">银行卡号：</label>
			<div class="formControls col-2">
				<input id="bankCardNumber" type="text" class="input-text" placeholder="请输入银行卡号">
			</div>
			<label class="form-label col-1">社保卡号：</label>
			<div class="formControls col-2">
				<input id="socialSecurityNum" type="text" class="input-text" placeholder="请输入社保卡号">
			</div>
			<label class="form-label col-1"> 发卡时间：</label>
			<div class="formControls col-2">
				<input type="text" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'maxDate\')||\'%y-%M-{%d} %H:%m:%s\'}'})" id="minDate" class="input-text Wdate">
		    </div>
		    <span style="float:left;margin: 0 8px;">-</span>
			<div class="formControls col-2" style="margin-left:10px;">
		    	<input type="text" onfocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'minDate\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})" id="maxDate" class="input-text Wdate">
		    </div>
		</div>		
		<div class="row cl">
			<label class="form-label col-1">发卡机构：</label>
			<div class="formControls col-2">
				<jc:selectDictionaryByKey key="issuers_card_name" id="issuersCardName"  classname="input-text"></jc:selectDictionaryByKey>
			</div>
			<label class="form-label col-1">卡状态：</label>
			<div class="formControls col-2">
				<jc:selectDictionaryByKey key="card_status" id="cardStatus1"  classname="input-text"></jc:selectDictionaryByKey>
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
		<div class="row cl">
			<label class="form-label col-1">人员性质：</label>
			<div class="formControls col-2">
				<select id="isFloating"  class="input-text">
					<option value="">--请选择--</option>
					<option value="0">常住人口</option>
					<option  value="1">流动人口</option>
				</select>
			</div>
		</div>	
			
		<button id="search_btn" onclick="search();" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">

			<jc:operationButton classname="btn btn-primary radius" onclick="addResidentsInfo();" btnSn="0101020301" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateResidentsInfo();" btnSn="0101020302" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton classname="btn btn-danger radius" onclick="deleteResidentsInfo();" btnSn="0101020303" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
			<jc:operationButton classname="btn btn-primary radius" onclick="residentImport();" btnSn="0101020304" btnName="居民信息导入" icon="&#xe645;"></jc:operationButton>
			<jc:operationButton onclick="otherResidentsInfoQuery();" classname="btn btn-primary radius" btnSn="0101020305" btnName="其他基本信息查询" icon="&#xe665;"></jc:operationButton>

		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="residentList">
			<thead>
				<tr class="text-c">
					<th width="2%"></th>
					<th width="7%">姓名</th>
					<th width="7%">身份证号码</th>
					<th width="11%">申办单位</th>
					<th width="8%">银行卡号</th>
					<th width="8%">卡的类别</th>
					<th width="7%">社保卡号</th>
					<th width="8%">人员性质</th>
					<th width="11%">发卡机构</th>
					<th width="7%">发卡时间</th>
					<th width="6%">卡状态</th>
					<th width="9%">创建时间</th>
					<th width="9%">更新时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="add" class="pd-20" style="display:none;">
	<form action="${contextPath}/residentsInfo/addResidentsInfo" method="post" class="form form-horizontal" id="addResidentsInfoForm">
		<input id="id" name="id" type="hidden">
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>居&nbsp;&nbsp;&nbsp;民&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;名:</label>
				<div class="formControls col-4">
					<input type="text" class="input-text" id="name" name="name" placeholder="请输入居民姓名" datatype="name" errormsg="只能2-20个中文字符">
				</div>
				<label class="form-label col-2"> <span class="c-red">*</span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
				<div class="formControls col-4">
				<jc:selectDictionaryByKey_Code key="sex"  datatype="*" classname="input-text" id="sex"  name="sex"  />
				</div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
				<div class="formControls col-4">
						<jc:selectDictionaryByKey_Code key="nation"  datatype="*" classname="input-text" id="nationalCode"  name="nationalCode"  />
				</div>
				<label class="form-label col-2"><span class="c-red">*</span> 出&nbsp;&nbsp;生&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;期:</label>
				<div class="formControls col-4">
					<input type="text" placeholder="请选择出生日期" class="input-text Wdate" id="birthday" name="birthday" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" datatype="*" errormsg="出生日期未填写" />
				</div>
			</div>
			<!-- 2文化程度代码,educationLevelCode;职业代码,professionalCode; -->
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>身&nbsp;份&nbsp;&nbsp;证&nbsp;号&nbsp;码:</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text"   id="identityNumber" name="identityNumber" placeholder="请输入身份证号"  datatype="sfzh,exist_ID_datatype" errormsg="身份证号格式错误">
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>银&nbsp;&nbsp;行&nbsp;&nbsp;&nbsp;卡&nbsp;&nbsp;号:</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text" placeholder="请输入银行卡号"  datatype="bank" errormsg="银行卡号正确格式8-20位数字" id="bankCardNumber" name="bankCardNumber">
				</div>
			</div>
			<!-- 民族代码,nationalCode;婚姻状况代码,maritalStatusCode; -->
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>卡&nbsp;&nbsp;&nbsp;的&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;别:</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="card_type"  datatype="*" classname="input-text" id="cardType"  name="cardType"  />
				</div>
				<label class="form-label col-2">社&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;卡&nbsp;&nbsp;号:</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text"   id="socialSecurityNum" name="socialSecurityNum" placeholder="请输入社保卡号" ignore="ignore" datatype="n8-20" errormsg="社保卡号为8-20 个数字">
				</div>
			</div>
				<!-- 社保卡号,socialSecurityNum;芯片号,chipNum; -->
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>申&nbsp;&nbsp;&nbsp;&nbsp;办&nbsp;&nbsp;单&nbsp;&nbsp;位:</label>
				<div class="formControls col-4">
					<input type="text" class="input-text" id="bidUtil" name="bidUtil" placeholder="请输入申办单位" datatype="bidUtil" errormsg="申办单位只允许2-20个字符">
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>发&nbsp;&nbsp;卡&nbsp;&nbsp;&nbsp;银&nbsp;&nbsp;行:</label>
				<div class="formControls col-4">
					<input  type="text" placeholder="请输入发卡银行" class="input-text" datatype="issuingBank"  errormsg="发卡银行格式为2-20个汉字" id="issuingBank" name="issuingBank">
				</div>
			</div>
			<!-- 申办单位,bidUtil;卡的类别,cardType; -->
				<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>发&nbsp;卡&nbsp;序&nbsp;列&nbsp;号:</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text"   id="issuingSerialNumber" name="issuingSerialNumber" placeholder="请输入发卡序列号"  datatype="issuingSerialNumber" errormsg="发卡序列号格式为10位数字">
				</div>
				<label class="form-label col-2">发卡机构证书:</label>
				<div class="formControls col-4">
					<input type="text"  class="input-text "placeholder="请输入发卡机构证书" id="issuersCardCertificate"  ignore="ignore"  name="issuersCardCertificate"  datatype="issuersCardCertificate" errormsg="发卡机构证书不得超过20个字符" />
				</div>
			</div>
		<!-- 	发卡机构名称,issuersCardName;发卡机构代码,issuersCardCode; -->
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>发卡机构名称:</label>
				<div class="formControls col-4">	
				<jc:selectDictionaryByKey_Code key="issuers_card_name" id="getIssuersCardCode" datatype="*"  classname="input-text "></jc:selectDictionaryByKey_Code>
					<input type="hidden"  id="issuersCardName"  name="issuersCardName"  />
				</div>
				<label class="form-label col-2">发卡机构代码:</label>
				<div class="formControls col-4">
						<input type="text" class="input-text " id="issuersCardCode" name="issuersCardCode"  readonly="readonly" placeholder="通过发卡机构名称获得"/>				
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="card_status"  datatype="*" classname="input-text" id="cardStatus"  name="cardStatus"  />	
				</div>		
				<label class="form-label col-2"><span class="c-red">*</span>芯&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
				<div class="formControls col-4">
					<input type="text"  class="input-text " id="chipNum" name="chipNum"  placeholder="请输入芯片号" datatype="chipNum" errormsg="芯片号格式15-20位字母/数字" />
				</div>
			</div>
	<!-- 	发卡时间,issuingTime;卡有效期,cardValidityPeriod; -->
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>发&nbsp;&nbsp;&nbsp;卡&nbsp;&nbsp;时&nbsp;&nbsp;间:</label>
				<div class="formControls col-4">			
					<input type="text"  class="input-text  Wdate" id="issuingTime" placeholder="请选择发卡时间" name="issuingTime" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'cardValidityPeriod\',{d:-1});}'})" 	 datatype="*" errormsg="发卡时间未填写" />
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>卡&nbsp;&nbsp;有&nbsp;&nbsp;&nbsp;效&nbsp;&nbsp;期:</label>
				<div class="formControls col-4">
					<input type="text"  class="input-text  Wdate" id="cardValidityPeriod" placeholder="请输入卡有效期" name="cardValidityPeriod" onfocus="WdatePicker({dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'issuingTime\',{d:1});}'})"	 datatype="*" errormsg="卡有效期未填写" />			
				</div>
			</div>
		<!-- 行政机构,officeId;区域机构,areaId; -->
		<div class="row cl">
			<label class="form-label col-2">行&nbsp;&nbsp;&nbsp;政&nbsp;&nbsp;机&nbsp;&nbsp;构:</label>
			<div class="formControls col-4">
				<jc:selectAdministrativeDivision key="officeId"  classname="input-text" id="officeId"  name="officeId"  />
			</div>	
			<label class="form-label col-2"><span class="c-red">*</span>区&nbsp;&nbsp;域&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;构:</label>
			<div class="formControls col-4">
				<input id="areaName"  type="text" class="input-text" datatype="*" readonly="readonly" placeholder="请输入区域机构" >
				<input id="areaId" name="areaId" type="hidden" class="input-text"  >
			</div>			
		</div>
		<div class="row cl">
				<label class="form-label col-2">文&nbsp;&nbsp;&nbsp;化&nbsp;&nbsp;程&nbsp;&nbsp;度:</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="education"   classname="input-text" id="educationLevelCode"  name="educationLevelCode"  />
				</div>
				<label class="form-label col-2">婚&nbsp;&nbsp;姻&nbsp;&nbsp;&nbsp;状&nbsp;&nbsp;况:</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="marital_status"   classname="input-text" id="maritalStatusCode"  name="maritalStatusCode"  />
				</div>
			</div>
		<div class="row cl">
			<label class="form-label col-2">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label>
				<div class="formControls col-4">
						<jc:selectDictionaryByKey_Code key="professional"   classname="input-text" id="professionalCode"  name="professionalCode"  />
				</div>
			<label class="form-label col-2">医疗支付方式:</label>
			<div class="formControls col-4">
				<jc:selectDictionaryByKey_Code key="medical_payment"   classname="input-text" id="medicalPayment"  name="medicalPayment"  />	
			</div>			
		</div>
		<div class="row cl">
			<label class="form-label col-2">联&nbsp;系&nbsp;人&nbsp;姓&nbsp;名:</label>
			<div class="formControls col-4">
				<input id="contactName" name="contactName" type="text" class="input-text" ignore="ignore" datatype="name" placeholder="请输入联系人姓名" errormsg="只能2-20个中文字符">
			</div>	
			<label class="form-label col-2">联系&nbsp;人&nbsp;关&nbsp;系:</label>
			<div class="formControls col-4">
				<input id="contactRelation" name="contactRelation" type="text" class="input-text" ignore="ignore" datatype="zh2-20" placeholder="请输入联系人关系" errormsg="只能2-20个中文字符">
			</div>			
		</div>
		<!-- 	本人电话,selfPhone;医疗支付方式,medicalPayment; -->
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>本&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;电&nbsp;&nbsp;话:</label>
			<div class="formControls col-4">
				<input id="selfPhone" name="selfPhone" datatype="lxdh" placeholder="请输入本人电话" errormsg="电话格式错误" type="text" class="input-text"  >
			</div>
			<label class="form-label col-2">联系&nbsp;人&nbsp;电&nbsp;话:</label>
			<div class="formControls col-4">
				<input id="contactPhone" name="contactPhone" placeholder="请输入联系人电话" datatype="lxdh" ignore="ignore"  errormsg="电话格式错误" type="text" class="input-text"  >
			</div>			
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>户&nbsp;&nbsp;&nbsp;籍&nbsp;&nbsp;地&nbsp;&nbsp;址:</label>
			<div class="formControls col-10">
				<input id="houseAddress" name="houseAddress" type="text" placeholder="请输入户籍地址" errormsg="地址不得超过50个字符"	datatype="address" class="input-text"  >
			</div>			
		</div>
	<!-- 	现住地址,nowAddress; -->
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>现&nbsp;&nbsp;&nbsp;住&nbsp;&nbsp;地&nbsp;&nbsp;址:</label>
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
<div id="areaTreediv" class="pd-20" style="display: none;">
		<div class="row cl">
			<div class="formControls col-4">
				<ul id="areaTree" class="ztree"></ul>
			</div>
		</div>
		<div class="row cl">
			<div class="col-offset-5">
				<button onClick="closeTree();" class="btn btn-default radius"
					type="button">&nbsp;&nbsp;确定&nbsp;&nbsp;</button>
			</div>
		</div>
</div>
<%--批量导入 --%>
 <div id="importResident" class="pd-20" style="display:none;">
	<jc:uploadexcel id="upFile" />
	<div class="row cl" style="padding-top: 10px;">
		<button id="batchImport_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 开始解析</button>
		<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
		<B><label>模板下载：</label></B>
		<a classname="btn btn-primary radius" href="" id="importResident_a"><i class="Hui-iconfont">${icon}</i></a>
	</div>
</div> 
	<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/select2.full.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/residentsInfo/residentsInfo.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/commons/loading.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/commons/sonic.js?v=${appVerDate}"></script>
</body> 
