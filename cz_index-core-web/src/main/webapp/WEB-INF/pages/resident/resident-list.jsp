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
			<label class="form-label col-1">居民姓名：</label>
			<div class="formControls col-2">
				<input id="realName" type="text" class="input-text" placeholder="请输入居民姓名">
			</div>
			<label class="form-label col-1">性别：</label>
			<div class="formControls col-2">
				<select class="select input-text" id="sex">
					<option value="">--请选择--</option>
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
			</div>
			<label class="form-label col-1">学历：</label>
			<div class="formControls col-2">
				<jc:selectDictionaryByKey key="education" classname="input-text" id="education"  name="education"  />
			</div>
			<label class="form-label col-1">民族：</label>
			<div class="formControls col-2">
				<jc:selectDictionaryByKey key="nation" classname="input-text" id="nation"  name="nation"  />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-1">行政机构：</label>
			<div class="formControls col-2">
				<jc:selectAdministrativeDivision key="officeId"  classname="input-text" id="officeId"  name="officeId"  />
			</div>
			<label class="form-label col-1">健康卡号：</label>
			<div class="formControls col-2">
				<input id="healthNumber" type="text" class="input-text" placeholder="请输入居民健康卡号">
			</div>
			<label class="form-label col-1">社保卡号：</label>
			<div class="formControls col-2">
				<input id="socialNumber" type="text" class="input-text" placeholder="请输入社保卡号">
			</div>
			<label class="form-label col-1">身份证号：</label>
			<div class="formControls col-2">
				<input id="idNumber" type="text" class="input-text" placeholder="请输入身份证号">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-1">区域机构：</label>
			<div class="formControls col-2">
				<jc:selectAdministrativeDivisionByParntId key="areaId"  classname="input-text" id="areaId"  name="areaId"  />
			</div>
		<label class="form-label col-1">二级机构：</label>
			<div class="formControls col-2">
				<select class="select input-text" id=areaId2>
					<option value="">--请选择--</option>
				</select>
			</div>
			<input type="hidden" id="regional">
		</div>		
		<button id="search_btn" onclick="search();" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton classname="btn btn-primary radius" onclick="addresident();" btnSn="0101020101" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateResident();" btnSn="0101020102" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton classname="btn btn-danger radius" onclick="deleteResidentBaseinfo();" btnSn="0101020103" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
			<jc:operationButton classname="btn btn-primary radius" onclick="residentImport();" btnSn="0101020105" btnName="基本信息导入" icon="&#xe645;"></jc:operationButton>
			<jc:operationButton classname="btn btn-primary radius" onclick="allresidentImport();" btnSn="0101020106" btnName="全部信息导入" icon="&#xe645;"></jc:operationButton>
			<jc:operationButton onclick="otherResidentQuery();" classname="btn btn-primary radius" btnSn="0101020104" btnName="其他基本信息查询" icon="&#xe665;"></jc:operationButton>
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="residentList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>
					<th width="7%">居民姓名</th>
					<th width="7%">居民健康卡号</th>
					<th width="5%">社保卡号</th>
					<th width="10%">身份证号</th>
					<th width="8%">行政机构</th>
					<th width="12%">区域机构</th>
					<th width="4%">性别</th>
					<th width="5%">民族</th>
					<th width="9%">学历</th>
					<th width="15%">创建时间</th>
					<th width="15%">更新时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<!--操作健康卡居民基本信息的表单-->
<div id="addresident" class="pd-20" style="display:none;">
	<form action="${contextPath}/residentBase/addResidentBaseinfo" method="post" class="form form-horizontal" id="addresidentForm">
		<input id="id" name="id" type="hidden">
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>居&nbsp;&nbsp;民&nbsp;&nbsp;&nbsp;姓&nbsp;&nbsp;名：</label>
				<div class="formControls col-4">
					<input type="text" class="input-text" id="realName" name="realName" placeholder="请输入居民姓名" datatype="zhy2-20" errormsg="只能2-20个字符">
				</div>
				<label class="form-label col-2"> <span class="c-red">*</span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
				<div class="formControls col-4">
				<select class="select input-text" id="sex" name="sex" datatype="*">
					<option value="">--请选择--</option>
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text"   id="age" name="age"placeholder="请输入年龄"  datatype="n1-3" errormsg="只能1-3位的数字">
				</div>
				<label class="form-label col-2"><span class="c-red">*</span> 民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey key="nation" classname="select input-text category select2 " style="width:100%;" id="nation"  name="nation"    datatype="*" />
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>身&nbsp;&nbsp;份&nbsp;&nbsp;&nbsp;证&nbsp;&nbsp;号：</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text" datatype="sfzh" errormsg="身份证号格式不正确"   id="idNumber" name="idNumber">
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>证&nbsp;件&nbsp;有&nbsp;效期:</label>
				<div class="formControls col-4">
					<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}'})" datatype="*" name="periodValidityDate" id="periodValidityDate" class="input-text Wdate" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>居民健康卡号：</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text" datatype="n15-30" errormsg="居民健康卡号是15-30位数字"   id="healthNumber" name="healthNumber">
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>社&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;卡&nbsp;&nbsp;号:</label>
				<div class="formControls col-4">
					<input  type="text" class="input-text" datatype="n8-15" errormsg="居民健康卡号是8-15位数字" id="socialNumber" name="socialNumber" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>所属行政机构：</label>
				<div class="formControls col-4">
				<jc:selectAdministrativeDivision key="officeId"  datatype="*" classname="input-text" id="officeId"  name="officeId"  />
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>所属区域机构:</label>
				<div class="formControls col-4">
					<input  type="text" class="input-text" id="areaname" name="areaname"  datatype="*" onclick="setAreaTree();">
					<input type="hidden"id="areaId" name="areaId" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey key="education" classname="input-text" id="education"  name="education" datatype="*" />
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>工&nbsp;&nbsp;作&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;位:</label>
				<div class="formControls col-4">
					<input  type="text" class="input-text"  id="wrokUnit"name="wrokUnit" datatype="*" >
				</div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-2"><span class="c-red">*</span>联&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;电&nbsp;&nbsp;话：</label>
				<div class="formControls col-4">
					<input   type="text" class="input-text"  datatype="lxdh" errormsg="联系电话格式不正确" id="phone" name="phone">
					
				</div>
				<label class="form-label col-2"><span class="c-red">*</span>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label>
				<div class="formControls col-4">
					<input  type="text" class="input-text" datatype="n6-6" errormsg="邮编只能是6位数字"id="postCode" name="postCode">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-2">户&nbsp;&nbsp;&nbsp;籍&nbsp;&nbsp;&nbsp;&nbsp;地&nbsp;&nbsp;&nbsp;址:</label>
				<div class="formControls col-10">
					<input id="houseAddress" name="houseAddress" type="text" class="input-text"  >
				</div>			
		</div>
		<div class="row cl">
			<label class="form-label col-2">现&nbsp;&nbsp;&nbsp;在&nbsp;&nbsp;&nbsp;&nbsp;地&nbsp;&nbsp;&nbsp;址:</label>
			<div class="formControls col-10">
				<input id="nowAddress" name="nowAddress" type="text" class="input-text"  >
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
<script type="text/javascript" src="${contextPath}/js/resident/resident.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/resident/allresident_import.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/commons/loading.js?v=${appVerDate}"></script>
<script type="text/javascript" src="${contextPath}/js/commons/sonic.js?v=${appVerDate}"></script>
</body> 
