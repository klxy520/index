<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本 信息采集</title>
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>机构管理<span class="c-gray en">&gt;</span>医院管理<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div style="padding:0 20px 20px 20px;">
    <!-- 隐藏的搜索模块 -->
    <div class="text-c form form-horizontal banner_xiala"> 
        <div class="row cl">
            <label class="form-label col-1">医院名称:</label>
            <div class="formControls col-2">
                <input id="indexName" type="text" class="input-text" placeholder="请输入医院名称">
            </div>
            <label class="form-label col-1">医院等级:</label>
            <div class="formControls col-2">
                <jc:selectDictionaryByKey key="hospitalLevel" id="indexlevel" classname="input-text"></jc:selectDictionaryByKey>
            </div>
            <label class="form-label col-1" style="width: 9%">医院类型名称:</label>
            <div class="formControls col-2">
                 <jc:selectDictionaryByKey key="hospitalName" id="indextypeName"  classname="input-text"></jc:selectDictionaryByKey>
            </div>
       </div>
        <div class="row cl">
            <label class="form-label col-1">状态:</label>
            <div class="formControls col-2">
                <select id="indexstatus" name="status" class="select input-text">
                    <option value="">全部</option>
                    <option value="0">启用</option>
                    <option value="1">禁用</option>
                </select>
            </div>
            <label class="form-label col-1">key:</label>
            <div class="formControls col-2">
                <input id="indexhKey" type="text" class="input-text" placeholder="请输入医院的key">
            </div>
            <label class="form-label col-1" style="width: 7%">修改时间:</label>
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
            <jc:operationButton classname="btn btn-primary radius" onclick="addHospital();" btnSn="0101060101 " btnName="添加" icon="&#xe600;"></jc:operationButton>
            <jc:operationButton classname="btn btn-secondary radius" onclick="updateHospitalPage();" btnSn="0101060102" btnName="编辑" icon="&#xe6df;"></jc:operationButton>
            <jc:operationButton classname="btn btn-danger radius" onclick="deleteHospitalById();" btnSn="0101060103" btnName="删除" icon="&#xe6e2;"></jc:operationButton>
            <jc:operationButton classname="btn btn-success radius" onclick="EnableHospitalById();" btnSn="0101060104" btnName="启用" icon="&#xe6dc;"></jc:operationButton> 
              <jc:operationButton classname="btn btn-default radius" onclick="disableHospitalById();" btnSn="0101060105" btnName="禁用" icon="&#xe6de;"></jc:operationButton> 
        </span> 
    </div>
    <!-- list -->
    <div class="mt-10">
        <table class="table table-border table-bordered table-bg table-hover table-sort" id="hospitalList">
            <thead>
                <tr class="text-c">
                    <th width="2%"></th>
                    <th width="12%">医院名称</th>
                    <th width="7%">医院等级</th>
                    <th width="7%">医院类型名称</th>
                    <th width="8%">key</th>
                    <th width="7%">pycode</th>
                    <th width="6%">状态</th>
                    <th width="12%">创建时间</th>
                    <th width="12%">修改时间</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
<!--添加表单-->
<div id="addHospital" class="pd-20" style="display:none;">
    <form action="${contextPath}/hospital/addHospital" method="post" class="form form-horizontal" id="addHospitalForm">
        <input id="id" name="id" type="hidden">
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>医院名称:</label>
                <div class="formControls col-4">
                        <input type="text" class="input-text" id="name" name="name" placeholder="请输入医院名称" datatype="name,exist_hospitalname_datatype" errormsg="只能2-20个中文字符">
                </div>
                <label class="form-label col-2">医院等级:</label>
                <div class="formControls col-4">
                        <jc:selectDictionaryByKey key="hospitalLevel" id="level" name="level" classname="input-text"></jc:selectDictionaryByKey>
                </div>
            </div>
           <div class="row cl">
                <label class="form-label col-2">医院类型:</label>
                <div class="formControls col-4">
                        <jc:selectDictionaryByKey key="hospitalType" id="type" name="type" classname="input-text"></jc:selectDictionaryByKey>
                </div>
                <label class="form-label col-2">医院类型名称:</label>
                <div class="formControls col-4">
                        <jc:selectDictionaryByKey key="hospitalName" id="typeName" name="typeName" classname="input-text"></jc:selectDictionaryByKey>
                </div>
            </div>
            <div class="row cl">
                 <label class="form-label col-2"><span class="c-red">*</span>key:</label>
                <div class="formControls col-4">
                        <input type="text" class="input-text" id="hKey" name="hKey" placeholder="请输入key" datatype="key,exist_key_datatype" errormsg="key只能输入0-20位数字" >
                </div>
                <label class="form-label col-2">pyCode:</label>
                <div class="formControls col-4">
                        <input type="text" class="input-text" id="pycode" name="pycode" datatype="empty|lm" errormsg="只能输入0-20位英文字母" placeholder="请输入pyCode"  >
                </div>
               
            </div>
            <div class="row cl">
                 <label class="form-label col-2">医院电话:</label>
                <div class="formControls col-4">
                     <input type="text" class="input-text" id="phone" name="phone" placeholder="请输入医院电话"  datatype="empty|tel" errormsg="医院电话格式不对">
                </div>
                <label class="form-label col-2">状态:</label>
                <div class="formControls col-4">
                    <select id="status" name="status" class="input-text"  disabled="disabled">
                    <option value="0" selected="selected">启用</option>
                    <option value="1">禁用</option>
                </select>
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2">医院地址:</label>
            <div class="formControls col-10">
                <input id="address" name="address" datatype="empty|address" errormsg="医院地址只能输入0-50位字符" type="text" placeholder="请输入医院地址"   class="input-text"  >
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
<!--添加表单-->
<div id="updateHospital" class="pd-20" style="display:none;">
    <form action="${contextPath}/hospital/updateHospital" method="post" class="form form-horizontal" id="updateHospitalForm">
        <input id="id" name="id" type="hidden">
        <input id="statushidden" name="status" type="hidden">
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>医院名称:</label>
                <div class="formControls col-4">
                        <input type="text" class="input-text" id="name" name="name" placeholder="请输入医院名称" datatype="name,exist_hospitalname_datatype" errormsg="只能2-20个中文字符" >
                </div>
                <label class="form-label col-2">医院等级:</label>
                <div class="formControls col-4">
                        <jc:selectDictionaryByKey key="hospitalLevel" id="level" name="level" classname="input-text"></jc:selectDictionaryByKey>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">医院类型:</label>
                <div class="formControls col-4">
                        <jc:selectDictionaryByKey key="hospitalType" id="type" name="type" classname="input-text"></jc:selectDictionaryByKey>
                </div>
                <label class="form-label col-2">医院类型名称:</label>
                <div class="formControls col-4">
                        <jc:selectDictionaryByKey key="hospitalName" id="typeName" name="typeName" classname="input-text"></jc:selectDictionaryByKey>
                </div>
            </div>
            <div class="row cl">
                 <label class="form-label col-2"><span class="c-red">*</span>key:</label>
                <div class="formControls col-4">
                        <input type="text" class="input-text" id="hKey" name="hKey" placeholder="请输入key"datatype="key,exist_key_datatype" errormsg="key只能输入0-20位数字"  >
                </div>
                <label class="form-label col-2">pyCode:</label>
                <div class="formControls col-4">
                        <input type="text" class="input-text" id="pycode" name="pycode" placeholder="请输入pyCode" datatype="empty|lm" errormsg="只能输入0-20位英文字母" >
                </div>
               
            </div>
            <div class="row cl">
                 <label class="form-label col-2">医院电话:</label>
                <div class="formControls col-4">
                     <input type="text" class="input-text" id="phone" name="phone" placeholder="请输入医院电话" datatype="empty|tel" errormsg="医院电话格式不对"  >
                </div>
                <label class="form-label col-2">状态:</label>
                <div class="formControls col-4">
                <select id="status" name="status" class="input-text"  disabled="disabled">
                    <option value="0" >启用</option>
                    <option value="1">禁用</option>
                </select>
                </div>
            </div>
        <div class="row cl">
            <label class="form-label col-2">医院地址:</label>
            <div class="formControls col-10">
                <input id="address" name="address" type="text" placeholder="请输入医院地址" datatype="empty|address" errormsg="医院地址只能输入0-50位字符"  class="input-text"  >
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
<script type="text/javascript" src="${contextPath}/js/hospital/hospital-list.js?v=${appVerDate}"></script>
</body> 
</html>
