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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>同步日志管理<span class="c-gray en">&gt;</span>基本身份信息日志<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
    <!-- 隐藏的搜索模块 -->
    <div class="text-c form form-horizontal banner_xiala"> 
        <div class="row cl">
            <label class="form-label col-1">姓名:</label>
            <div class="formControls col-2">
               <input id="personName" type="text" class="input-text" placeholder="请输入姓名">
            </div>
            <label class="form-label col-1">身份证号:</label>
            <div class="formControls col-2">
               <input id="idCard" type="text" class="input-text" placeholder="请输入身份证号">
            </div>
            <label class="form-label col-1">同步状态:</label>
            <div class="formControls col-2">
                <select id="syncStatus" name="syncStatus" class="select input-text">
                    <option value="">--请选择--</option>
                    <option value="0" selected="selected">未同步</option>
                    <option value="1">已同步</option>
                </select>
            </div>
            <label class="form-label col-1">前置机:</label>
            <div class="formControls col-2">
                <jc:selectFrontEndMachine key="frontId"  classname="input-text" id="frontId"  name="frontId"></jc:selectFrontEndMachine>
            </div>
        </div>
        <div class="row cl">
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
          <jc:operationButton classname="btn btn-primary radius" onclick="syncLogPersonById();" btnSn="0101070101 " btnName="同步" icon="&#xe6e1;"></jc:operationButton>
        </span> 
    </div>
    <!-- list -->
    <div class="mt-10">
        <table class="table table-border table-bordered table-bg table-hover table-sort" id="personSyncLogList">
            <thead>
                <tr class="text-c">
                    <th width="2%"><input type="checkbox" name="goodCheckbox" id="checkbox_id_all" class="checkbox_id" ></th>
                    <th width="7%">姓名</th>
                    <th width="7%">身份证号</th>
                    <th width="8%">前置机</th>
                    <th width="22%">前置机地址</th>
                    <th width="7%">前置机状态</th>
                    <th width="6%">同步状态</th>
                    <th width="12%">创建时间</th>
                    <th width="12%">修改时间</th>
                    <th width="5%">备注</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
   <%@ include file="../commons/comm_const_js.jsp"%>
<script type="text/javascript" src="${contextPath}/js/personSyncLog/personSyncLog-list.js?v=${appVerDate}"></script>
</body> 
</html>
