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
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<style>
	.zm_break{
		word-break: break-all;
	}
</style>
<title>医院前置机管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 医院前置机管理 <span class="c-gray en">&gt;</span> 医院前置机管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-2">前置机编码:</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" id="frontEndMachinecode" name="frontEndMachinecode">
			</div>
			<label class="form-label col-2">前置机地址:</label>
			<div class="formControls col-2">
				<input type="text" class="input-text" id="frontEndMachineaddress" name="frontEndMachineaddress">
			</div>
			<label class="form-label col-2">状态:</label>
			<div class="formControls col-2">
				<select id="state" name="state" class="select input-text">
					<option value="">全部</option>
					<option value="0">启用</option>
					<option value="1">禁用</option>
					<option value="3">异常</option>
				</select>
			</div>
		</div>
		<button class="btn btn-success" id="search_btn" type="submit" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull" style="text-align: center;cursor: pointer;"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton classname="btn btn-primary radius" onclick="addFrontEndMachine();" btnSn="0101040101" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateFrontEndMachine();" btnSn="0101040102" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton classname="btn btn-danger radius" onclick="deleteFrontEndMachine();" btnSn="0101040103" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
<%-- 			<jc:operationButton classname="btn btn-success radius" onclick="enableFrontEndMachine();" btnSn="0101040104" btnName="启用" icon="&#xe6dc;"></jc:operationButton> --%>
<%-- 			<jc:operationButton classname="btn btn-default radius" onclick="disableFrontEndMachine();" btnSn="0101040105" btnName="禁用" icon="&#xe6de;"></jc:operationButton> --%>
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="frontEndMachineList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>
					<th width="15%">前置机编码</th>
					<th width="15%">前置机地址</th>
					<th width="5%">状态</th>
					<th width="20%">备注</th>
					<th width="10%">创建者</th>
					<th width="15%">创建时间</th>
					<th width="15%">修改时间</th>
				</tr>
			</thead>
			<tbody id="frontEndMachineData">
			</tbody>
		</table>
	</div>
</div>
<!-- 前置机添加 -->
<div id="addFrontEndMachine" class="pd-20" style="display:none;">
	<form action="${contextPath}/frontEndMachine/add" method="post" class="form form-horizontal" id="addFrontEndMachineForm">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>前置机编码:</label>
			<div class="formControls col-5">
				<input id="frontEndMachinecode" name="frontEndMachinecode" type="text" class="input-text" placeholder="请输入前置机编码" datatype="lm8-50" errormsg="请输8-50位字母和数字">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>前置机地址:</label>
			<div class="formControls col-9">
				<input id="frontEndMachineaddress" name="frontEndMachineaddress" type="text" class="input-text" placeholder="请输入地址" datatype="wsdlurl" errormsg="请输入正确的前置机地址">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">备注:</label>
			<div class="formControls col-9">
				<textarea id="remarks" name="remarks" class="textarea" dragonfly="trues" onKeyUp="textarealength(this,200)" errormsg="请输入详情"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-offset-8">
				<button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>
<!-- 编辑前置机 -->
<div id="updateFrontEndMachine" class="pd-20" style="display:none;">
		<form action="${contextPath}/frontEndMachine/update" method="post" class="form form-horizontal" id="updateFrontEndMachineForm">
		<input id="id" name="id" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>前置机编码:</label>
			<div class="formControls col-5">
				<input id="frontEndMachinecode" name="frontEndMachinecode" type="text" class="input-text" placeholder="请输入前置机编码" datatype="lm8-50" errormsg="请输8-50位字母和数字">
			</div>
			<label class="form-label col-2">状态:</label>
			<div class="formControls col-2">
				<select id="state" name="state" class="select input-text">
					<option value="0">启用</option>
					<option value="1">禁用</option>
					<option value="3">异常</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>前置机地址:</label>
			<div class="formControls col-9">
				<input id="frontEndMachineaddress" name="frontEndMachineaddress" type="text" class="input-text" placeholder="请输入地址" datatype="wsdlurl" errormsg="请输入正确的前置机地址">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2">备注:</label>
			<div class="formControls col-9">
				<textarea id="remarks" name="remarks" class="textarea" dragonfly="trues" onKeyUp="textarealength(this,200)" errormsg="请输入详情"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-offset-8">
				<button id="form_submit" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>
<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/frontEndMachine/frontEndMachine.js?v=${appVerDate}"></script>
</body>
</html>