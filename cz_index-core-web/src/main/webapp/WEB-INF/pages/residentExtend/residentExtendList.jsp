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
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/select2.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" type="text/css">
<link href="${contextPath}/css/loading.css" rel="stylesheet"
	type="text/css" />
</head>
<style type="text/css">
.form-horizontal .form-label {
    margin-top: 0px;
    cursor: text;
    text-align: right;
    padding-right: 10px;
}
</style>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span>居民信息管理 <span class="c-gray en">&gt;</span>
	扩展信息管理 <a class="btn btn-success radius r mr-20"
		style="line-height: 1.6em; margin-top: 3px"
		href="javascript:location.replace(location.href);" title="刷新"><i
		class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div style="padding: 0 20px 20px 20px;">
		<!-- 隐藏的搜索模块 -->
		<div class="text-c form form-horizontal banner_xiala" id="searchs">
			<div class="row cl">
				<label class="form-label col-1">居民姓名：</label>
				<div class="formControls col-2">
					<input id="realName" type="text" class="input-text"
						placeholder="请输入居民姓名">
				</div>
				<label class="form-label col-1">身份证号：</label>
				<div class="formControls col-2">
					<input id="idNumber" type="text" class="input-text"
						placeholder="请输入身份证号">
				</div>
			<%-- 	<label class="form-label col-1">健康卡号：</label>
				<div class="formControls col-2">
					<input id=healthNumber type="text" class="input-text"
						placeholder="请输入社保卡号">
				</div>
				<label class="form-label col-1">低保类型：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey_Code key="low_type" classname="input-text"
						id="lowType" name="lowType" />
				</div> --%>
				
				
			<label class="form-label col-1">民政性质：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey key="isCivilAffairs" classname="input-text"
						id="isCivilAffairsSearch" name="isCivilAffairs" />
				</div>
				<label class="form-label col-1">残联性质：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey key="isDisableFederation" classname="input-text"
						id="isDisableFederationSeaech" name="isDisableFederation" />
				</div>

			</div>
			<%--<div class="row cl">
				<label class="form-label col-1">医保类型：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey_Code key="insurance_type"
						classname="input-text" id="insuranceType" name="insuranceType" />
				</div>
				<label class="form-label col-1">病种类型：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey_Code key="illness_type" classname="input-text"
						id="illnessType" name="illnessType" />
				</div>
				<label class="form-label col-1">残疾类型：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey_Code key="disability_type"
						classname="input-text" id="disabilityType" name="disabilityType" />
				</div>
				 <label class="form-label col-1">工会特征：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey_Code key="union_feature"
						classname="input-text" id="unionFeature" name="unionFeature" />
				</div> 
			</div>--%>

			<div class="row cl">
				<%-- <label class="form-label col-1">离休干部：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey_Code key="retired_cadres"
						classname="input-text" id="retiredCadres" name="retiredCadres" />
				</div>
				<label class="form-label col-1">扶贫户：</label>
				<div class="formControls col-2">
					<jc:selectDictionaryByKey_Code key="help_house" classname="input-text"
						id="helpHouse" name="helpHouse" />
				</div> --%>
				<label class="form-label col-1">更新时间：</label>
				<div class="formControls col-2" >
					<input type="text"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' ,maxDate:'#F{$dp.$D(\'date2\')||\'%y-%M-{%d} %H:%m:%s\'}'})" 
						id="date1" class="input-text Wdate" />
				</div>
				<span style="float:left;margin: 0 8px;">-</span>
				<div class="formControls col-2" style="margin-left:10px;">
					<input type="text"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'date1\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})"
						id="date2" class="input-text Wdate" />
				</div>
			</div>
			<button id="search_btn" onclick="search();" class="btn btn-success"
				type="button" style="margin-top: 10px;">
				<i class="Hui-iconfont">&#xe665;</i> 搜索
			</button>
		</div>
		<div class="pull">
			<img src="${contextPath}/images/pull_3.gif"><span>搜索</span>
		</div>
		<!-- 删除  添加  编辑 -->
		<div class="cl pd-5 bg-1 bk-gray mt-10">
			<span class="l"> <jc:operationButton
					classname="btn btn-secondary radius"
					onclick="updateResidentExtend();" btnSn="0101020202" btnName="编辑"
					icon="&#xe6df;"></jc:operationButton> <jc:operationButton
					classname="btn btn-primary radius" onclick="extendImport();"
					btnSn="0101020201" btnName="扩展信息导入" icon="&#xe645;"></jc:operationButton>
			</span>
		</div>
		<!-- list -->
		<div class="mt-10">
			<table
				class="table table-border table-bordered table-bg table-hover table-sort"
				id="extendTable">
				<thead>
					<tr class="text-c">
						<th width="3%"></th>
						<th width="7%">居民姓名</th>
						<th width="10%">身份证号</th>
						<!--<th width="7%">居民银行卡号</th>
						 <th width="8%">医保类型</th>
						<th width="6%">病种类型</th>
						<th width="6%">残疾类型</th>
						<th width="5%">工会特征</th>
						<th width="5%">离休干部</th>
						<th width="5%">扶贫户</th>											
						<th width="7%">低保类型</th> -->
						<th width="5%">民政性质</th>
						<th width="5%">残联性质</th>
						<th width="7%">创建时间</th>
						<th width="7%">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div id="readResidentExtend" class="pd-20" style="display: none;">
		<div class="form form-horizontal" id="extendInfo"
			style="border: 1px solid #BEBEBE"></div>
		<div class="form form-horizontal">
			<div class="row cl">
				<div class="col-offset-9">
					<button onClick="layer_close();" class="btn btn-default radius"
						type="button">&nbsp;&nbsp;关闭&nbsp;&nbsp;</button>
				</div>
			</div>
		</div>
	</div>


	<!--操作扩展信息的表单-->
	<div id="addExtend" class="pd-20" style="display: none;">
		<form action="${contextPath}/residentExtendinfo/addExtendInfo"
			method="post" class="form form-horizontal" id="addExtendInfo">
			<input id="id" name="id" type="hidden"> 
			<input id="baseId" name="baseId" type="hidden">

			<div class="row cl">
				<label class="form-label col-2">居民姓名：</label>
				<div class="formControls col-4">
					<input id="realName" type="text" name="realName" readonly="readonly" class="input-text">
				</div>
				<label class="form-label col-2">身份证号：</label>
				<div class="formControls col-4">
					<input id="idNumber" type="text"  name="idNumber" readonly="readonly" class="input-text">
				</div>
			</div>

		 	<%-- <div class="row cl">
				<label class="form-label col-2">居民健康卡号：</label>
				<div class="formControls col-4">
					<input id="healthNumber" type="text" class="input-text"
						readonly="readonly" name="healthNumber">
				</div>
				<label class="form-label col-2">低保类型：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="low_type" classname="input-text"
						id="lowType" name="lowType" />
					<input id="lowType2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">
				</div>
			</div>

			<div class="row cl">
				<label class="form-label col-2">医保类型：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="insurance_type"
						classname="input-text" id="insuranceType" name="insuranceType" />
					<input id="insuranceType2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">

				</div>
				<label class="form-label col-2">病种类型：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="illness_type" classname="input-text"
						id="illnessType" name="illnessType" />
					<input id="illnessType2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">

				</div>
			</div> 

			<div class="row cl">
				<label class="form-label col-2">残疾类型：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="disability_type"
						classname="input-text" id="disabilityType" name="disabilityType" />
					<input id="disabilityType2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">
				</div>
				<label class="form-label col-2">工会特征：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="union_feature"
						classname="input-text" id="unionFeature" name="unionFeature" />
					<input id="unionFeature2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">
				</div>
			</div>

			<div class="row cl">
				<label class="form-label col-2">离休干部：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="retired_cadres"
						classname="input-text" id="retiredCadres" name="retiredCadres" />
					<input id="retiredCadres2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">
				</div>
				<label class="form-label col-2">扶贫户：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey_Code key="help_house" classname="input-text"
						id="helpHouse" name="helpHouse" />
					<input id="helpHouse2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">
				</div>
			</div>
			--%>
			<div class="row cl">
				<label class="form-label col-2">民政性质：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey key="isCivilAffairs" defaultValue="无"
						classname="input-text" id="isCivilAffairs" name="isCivilAffairs" />
					<input id="isCivilAffairs2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">
				</div>
				<label class="form-label col-2">残联性质：</label>
				<div class="formControls col-4">
					<jc:selectDictionaryByKey key="isDisableFederation" classname="input-text" defaultValue="无"
						id="isDisableFederation" name="isDisableFederation" />
					<input id="isDisableFederation2" type="text" readonly="readonly"
						style="display: none;" value="无权查看" class="input-text">
				</div>
			</div>

			<div class="row cl">
				<div class="col-offset-5">
					<button id="form_submit" class="btn btn-primary radius" type="button">
						<i class="Hui-iconfont">&#xe632;</i> 提交
					</button>
					<button onClick="layer_close();" class="btn btn-default radius"
						type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
	</div>


	<div id="importResident" class="pd-20" style="display: none;">
		<jc:uploadexcel id="upFile" />
		<div class="row cl" style="padding-top: 10px;">
			<button id="batchImport_submit" class="btn btn-primary radius"
				type="button">
				<i class="Hui-iconfont">&#xe632;</i> 开始解析
			</button>
			<button onClick="layer_close();" class="btn btn-default radius"
				type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			<B><label>模板下载：</label></B> <a classname="btn btn-primary radius"
				href="" id="importResident_a"><i class="Hui-iconfont">${icon}</i></a>
		</div>
	</div>

	<%@ include file="../commons/comm_const_js.jsp"%>



	<script type="text/javascript" src="${contextPath}/js/resident/extendResident_import.js?v=${appVerDate}"></script>
	<script type="text/javascript" src="${contextPath}/js/select2.full.js?v=${appVerDate}"></script>
	<script type="text/javascript" src="${contextPath}/js/residentExtend/residentExtend.js?v=${appVerDate}"></script>
	<script type="text/javascript" src="${contextPath}/js/commons/loading.js?v=${appVerDate}"></script>
	<script type="text/javascript" src="${contextPath}/js/commons/sonic.js?v=${appVerDate}"></script>
</body>
</html>