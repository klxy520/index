<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告管理</title>
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 公告管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
	<!-- 隐藏的搜索模块 -->
	<div class="text-c form form-horizontal banner_xiala"> 
		<div class="row cl">
			<label class="form-label col-2" style="width:16.66667%;">公告标题：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input id="title" type="text" class="input-text" placeholder="公告标题">
			</div>
			<label class="form-label col-2" style="width:16.66667%;">公告创建人：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input id="creatorName" type="text" class="input-text" placeholder="创建人">
			</div>
			<label class="form-label col-2" style="width:16.66667%;">公告编号：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input type="text" id="id" class="input-text" placeholder="公告编号">
			</div>
			
		</div>
		
		<div class="row cl">
			<label class="form-label col-2" style="width:16.66667%;">公告修改人：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input id="updatorName" type="text" class="input-text" placeholder="公告修改人">
			</div>
			<label class="form-label col-2" style="width:16.66667%;">启用状态：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<select id="status" name="status" class="select input-text">
					<option value="">全部</option>
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2" style="width:16.66667%;">创建时间：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input type="text" 
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' ,maxDate:'#F{$dp.$D(\'createDate2\')||\'%y-%M-{%d} %H:%m:%s\'}'})" 
				id="createDate1" class="input-text Wdate">
			</div>
			<%-- <label class="form-label col-1" style="width:2%;">-</label> --%>
			<span style="float:left;margin: 0 8px;">-</span>
			<div class="formControls col-2" style="width:16.66667%;margin-left:10px;">
				<input type="text" 
					 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' ,minDate:'#F{$dp.$D(\'createDate1\')}',maxDate:'%y-%M-{%d} %H:%m:%s'})" 
				id="createDate2" class="input-text Wdate">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2" style="width:16.66667%;">启用时间：</label>
			<div class="formControls col-2" style="width:16.66667%;">
				<input type="text" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ' ,maxDate:'#F{$dp.$D(\'useTime2\')}'})" 
				id="useTime1" class="input-text Wdate">
			</div>
			<%-- <label class="form-label col-1" style="width:2%;">-</label> --%>
			<span style="float:left;margin: 0 8px;">-</span>
			<div class="formControls col-2" style="width:16.66667%;margin-left:10px;">
				<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',minDate:'#F{$dp.$D(\'useTime1\')}' })" id="useTime2" class="input-text Wdate">
			</div>
		</div>
		
		<button id="search_btn" class="btn btn-success" type="button" style="margin-top:10px;"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="pull"><img src="${contextPath}/images/pull_3.gif"><span>搜索</span></div>
	<!-- 删除  添加  编辑 -->
	<div class="cl pd-5 bg-1 bk-gray mt-10"> 
		<span class="l">
			<jc:operationButton classname="btn btn-primary radius" onclick="addNotice();" btnSn="0101011101" btnName="添加" icon="&#xe600;"></jc:operationButton>
			<jc:operationButton classname="btn btn-secondary radius" onclick="updateNotice();" btnSn="0101011102" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
			<jc:operationButton classname="btn btn-danger radius" onclick="deleteNotice();" btnSn="0101011103" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
			<jc:operationButton classname="btn btn-success radius" onclick="setUserAuthLayer();" btnSn="0101011104" btnName="公告授权" icon="&#xe6dc;"></jc:operationButton>
		
		</span> 
	</div>
	<!-- list -->
	<div class="mt-10">
		<table class="table table-border table-bordered table-bg table-hover table-sort" id="NoticeList">
			<thead>
				<tr class="text-c">
					<th width="3%"></th>	
					<th width="10%">公告标题</th>
					<th width="20%">公告内容</th>
					<th width="20%">公告启用时间</th>
					<th width="10%">创建时间</th>
					<th width="6%">创建人</th>
					<th width="5%">使用状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<%--公告添加 --%>
<div id="addNotice" class="pd-20" style="display:none;">
	<form action="${contextPath}/notice/addNotice" method="post" class="form form-horizontal" id="addNoticeForm">
		<input id="id" name="id" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>公告主题：</label>
			<div class="formControls col-3">
				<input id="title" name="title" type="text" class="input-text" placeholder="请输入公告主题" datatype="s4-20" errormsg="请输入4-20位汉字">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>启用状态：</label>
			<div class="formControls col-3">
				<select id="status" name="status" class="select input-text" datatype="*">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
		<label class="form-label col-2" ;"><span class="c-red">*</span>开始时间：</label>
			<div class="formControls col-3" ">
				<input type="text"
				 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}',maxDate:'#F{$dp.$D(\'endTime1\')}'})" 
				id="startTime1"  name="startTime"  datatype="*" class="input-text Wdate">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>结束时间：</label>
			<div class="formControls col-3" >
				<input type="text"
				 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'startTime1\')}'})" 
 				id="endTime1" name="endTime" datatype="*"  class="input-text Wdate">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>公告内容：</label>
			<div class="formControls col-10" style="width:75%;">
				<textarea id="content" name="content" class="textarea" dragonfly="true" onKeyUp="textarealength(this,200)" datatype="s500" errormsg="请输入公告内容"></textarea>
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


<%--公告修改 --%>
<div id="updateNotice" class="pd-20" style="display:none;">
	<form action="${contextPath}/notice/updateNoticeById" method="post" class="form form-horizontal" id="updateNoticeForm">
		<input id="id" name="id" type="hidden">
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>公告主题：</label>
			<div class="formControls col-3">
				<input id="title" name="title" type="text" class="input-text" placeholder="请输入主题" datatype="s4-20" errormsg="请输入4-20位汉字">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>启用状态：</label>
			<div class="formControls col-3">
				<select id="status" name="status" class="select input-text" datatype="*">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</div>
		</div>
		<div class="row cl">
		<label class="form-label col-2" ><span class="c-red">*</span>开始时间：</label>
			<div class="formControls col-3" >
				<input type="text" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" 
				id="startTime"  name="startTime"  datatype="*" class="input-text Wdate">
			</div>
			<label class="form-label col-3"><span class="c-red">*</span>结束时间：</label>
			<div class="formControls col-3" >
				<input type="text" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ',minDate:'#F{$dp.$D(\'startTime\')}'})"
				id="endTime" name="endTime" datatype="*"  class="input-text Wdate">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-2"><span class="c-red">*</span>公告内容：</label>
			<div class="formControls col-10" style="width:75%">
				<textarea id="content" name="content" class="textarea" dragonfly="trues" onKeyUp="textarealength(this,200)" datatype="s500" errormsg="请输入公告内容"></textarea>
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
<%--公告授权树 --%> 
<div id="setUserAuth" class="pd-20" style="display:none;">
		<form action="${contextPath}/residentsInfo/addResidentsInfo" method="post" class="form form-horizontal" id="addResidentsInfoForm">
		<input id="noticeId" name="noticeId" type="hidden"> 
			<div class="row cl">
				<label class="form-label col-2">公告名称</label>
				<div class="formControls col-6">
					<input id="noticeTitle"  type="text" class="input-text" readonly="readonly">
				</div>
			</div>
			
			<div class="row cl">
				<div>
				<label class="form-label col-2">公告查看权限</label>
				<div class="formControls col-5" >
					<div style="border: 1px solid #C7C7E2;  overflow-y: scroll">
					<ul id="areaUser"  class="ztree"></ul>
					</div>
				</div>
				<div class="formControls col-5" >
					<div style="border: 1px solid #C7C7E2;  overflow-y: scroll">
					<ul id="orgUser" class="ztree"></ul>
				</div>
				</div>
				</div>
			</div>
			<div class="row cl" >
			<div class="col-offset-5">
				<button onClick="setNoticeUser();" class="btn btn-primary radius " type="button"><i class="Hui-iconfont">&#xe632;</i> 提交</button>
				<button onClick="layer_close()" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
			</div>
</form>
</div>

<%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/notice/notice.js?v=${appVerDate}"></script>
</body>
</html>