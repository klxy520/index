var residentPath = contextPath + "/residentBase";
var importResidentPath = contextPath + "/importResident";
var residentListTable;
var residentTree;
var LegalFalg=false;
var select2Object;
/**
 * 区域机构树
 */
var setting = {
		check: {enable: true,chkStyle: "radio",radioType: "all",chkboxType:  { "Y": "", "N": "" }},
		data: {
			/*key : {
				checked : "checked",
				children : "subAdministrativeDivisionList",
				name : "name",
				title : "description",
				url : "url"
			},*/
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentid",
				rootPId : null
			}
		},
		callback: {onCheck: zTreeOnCheck}
};
//选择
function zTreeOnCheck(event, treeId, treeNode) {
	var node = treeNode.getParentNode();
	$("#addresidentForm").find("#areaId").val(treeNode.id);
	$("#addresidentForm").find("#areaname").val(node.name+"-"+treeNode.name);
};

$(document).ready(function(){
	iniList();
	select2Object=$("#addresident").find("#nation").select2();// 初始化select2插件
		$.ajax({
			"type":"POST",
			"url":contextPath + "/administrativeDivision/topAdministrativeDivision",
			"dataType":"json",
			"async":true,
			"data":null,
			"success":function(data){
				if(data.status==0){
					residentTree=$.fn.zTree.init($("#areaTree"), setting, data.data);
					residentTree.selectNode(residentTree.getNodes()[0]);
				}else{
					layer.alert(data.message);
				}   
			}
		});
	
		$("#areaId").change(function(){
			selectMechanis();
		})
		$("#areaId2").change(function(){
			areaId=$(this).val();
			if(areaId!=null&&areaId!=""){
				$("#regional").val(areaId);
			}
			
		})
		$("#addresidentForm").find("#nation").change(function(){
			$("#addresidentForm").find("#nation").blur();
		})
});
/**
 * 初始化居民基本信息列表
 */
function iniList(){
	var residentList=$("#residentList");
	if(residentList && residentList.length>0){
		residentListTable = residentList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aaSorting" : [ [ 11 , "desc" ],[10,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,3,6,7]}],
		    "aLengthMenu": [[15,30,50,100], [15,30,50,100]],// 定义每页显示数据数量
		    "bInfo": true,
		    "bWidth": true,
		    "bScrollCollapse": true,
		    "sPaginationType": "full_numbers",
		    "bProcessing": true,
		    "bServerSide": true,
		    "bDestroy": true,
		    "bSortCellsTop": true,
		    "bStateSave":false,// true状态保持 刷新页面保持页码等数据,false:反之
		    "asStripeClasses":['text-c odd','text-c even'],
		    "sAjaxSource": residentPath+'/list',
		    "aoColumns": [
		     {
		        "mData": 'id',
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){ 
		        	return '<input type="checkbox" name="goodCheckbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'
		        }
		     },
		     {
			     "mData": 'realName',
			     'sClass': 'center',
			     'sName':"realName",
			    "render": function (data,type,full) {
					       return '<u style="cursor:pointer;color:blue" onclick="queryResidentBaseinfoDetails('+full.id+')" href="javascript:;">'+data+'</u>';
			    } 
			   },
			     {
				     "mData": 'healthNumber',
				     'sClass': 'center',
				     'sName':"healthNumber"
				     },
		     {
		        "mData": "socialNumber",
		        'sClass': 'center',
		        'sName':"socialNumber"
		    },
		    {
		        "mData": "idNumber",
		        'sClass': 'center',
		        'sName':"idNumber"
		    },
		    {
		        "mData": "office",
		        'sClass': 'center',
		        'sName':"office"
		    },
		    {
		    	"mData": "area",
		    	'sClass': 'center',
		    	'sName':"area"
		    },
		    {
		    	"mData": "sex",
		    	'sClass': 'center',
		    	'sName':"sex"
		    },
		    {
		        "mData": "nation",
		        'sClass': 'center',
		        'sName':"nation"
		        
		    },
		    {
		    	"mData": "education",
		    	'sClass': 'center',
		    	'sName':"education"
		    },
		    {
		    	"mData": "createDate",
		    	'sClass': 'center',
		    	'sName':"createDate"
		    },
		    {
		    	"mData": "updateDate",
		    	'sClass': 'center',
		    	'sName':"updateDate"
		    }
		    ],
		    "fnDrawCallback": function( settings ) {
		        //绑定 tr  事件 点击checkbox更容易
		    	checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	aoData.push(
	                   { "name": "filter_realName_LIKE", "value": $('#realName').val() },
	                     { "name": "filter_sex_EQ", "value": $('#sex').val() },
	                     { "name": "filter_areaId_EQ", "value": $('#regional').val() },
	                    {"name": "filter_education_EQ", "value": $('#education').val() },
	                    {"name": "filter_nation_LIKE", "value": $('#nation').val() },
	                    { "name": "filter_officeId_EQ", "value": $('#officeId').val() },
	                    { "name": "filter_idNumber_LIKE", "value": $("#idNumber").val() },
	                   { "name": "filter_socialNumber_LIKE", "value": $("#socialNumber").val() },
	                   { "name": "filter_healthNumber_LIKE", "value": $("#healthNumber").val() }
            )	
		        $.ajax({
		            "type": 'post',
		            "url": sSource,
		            "dataType": "json",
		            "data": {
		                aoData: JSON.stringify(aoData)
		            },
		            "success": function(resp) {
		                fnCallback(resp);
		            }
		        });
		    }
		});
	}
}
/**
 * 居民基本信息列表条件查询
 */
function search(){
	residentListTable.fnFilter();
}
/**
 * 添加居民健康卡基本信息
 */
function addresident(){
	setaddresidentFormValue(null)
	popupForHtml('添加居民健康卡基本信息',$('#addresident'),'70%','96%');
	//clearFromText();
	var addresidentForm = $("#addresidentForm");
	if(addresidentForm && addresidentForm.length>0){
		addresidentForm.Validform({
			btnSubmit:"#form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:false,
			dragonfly:false,
			tipSweep:false,
			label:".label",
			showAllError:false,
			postonce:true,
			ajaxPost:true,
			datatype:customDataType,
			usePlugin:{
				swfupload:{},
				datepicker:{},
				passwordstrength:{},
				jqtransform:{
					selector:"select,input"
				}
			},
			beforeCheck:function(curform){
			},
			beforeSubmit:function(curform){
			},
			callback:function(data){
				if(data.status==0){
					layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
					//	residentTable.fnFilter();
						residentListTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addresidentForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}

}
/***
 * 修改居民健康卡基本信息
 */
function updateResident(){
	var resident=getResident()
	if(resident!=false){
		var residentDB=getResidentById(resident.id)
		setaddresidentFormValue(residentDB)//为编辑表单设置值
		popupForHtml('编辑居民健康卡基本信息',$('#addresident'),'68%','100%');
	}
	updateresident();
}
/**
 * 根据ID删除居民健康卡基本信息
 */
function deleteResidentBaseinfo(){
	var resident=getResident()
	if(resident!=false){
		layer.confirm("确认删除基本信息和扩展信息?",function(){
			$.ajax({
				"type" : 'post',
				"url" : residentPath + "/deleteResidentBaseinfo",
				"dataType" : "json",
				"data":{"id":resident.id,"realName":resident.realName,"idNumber":resident.idNumber},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							residentListTable.fnDraw(true); //刷新当前页面
						    layer.closeAll();
						});
					}else{
						layer.alert(data.message)
					}
				}
			});
		});
	}
}
/**
 * 根据id查询居民健康卡基本信息详情
 */
function queryResidentBaseinfoDetails(id){
	popup('居民健康卡基本信息详情',residentPath+'/queryResidentBaseinfoDetails?id='+id,'950','500');
}
/***
 *获取单个复选框的居民信息
 */
function getResident(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条居民信息");
		return false;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一条居民信息");
		return false;
	}
	return getCheckedObject(residentListTable);
}
/***
 * 根据id从后台获取居民健康卡基本信息
 */
function getResidentById(residentId){
	var resident=null;
	$.ajax({
		"type" : 'get',
		"url" : residentPath + "/queryResidentBaseinfoById",
		"dataType" : "json",
		"data":{"id":residentId},
		"async" : false,
		"success" : function(data) {
			if (data.status== 0) {
				resident=data.data
			}else{
				layer.alert("获取居民健康卡基本信息失败")
			}
		}
	});
	return resident;
}
/**
 * 编辑居民健康卡基本为表单设置值
 */
function setaddresidentFormValue(resident){
	if(resident!=null){
		$("#addresident").find("#id").val(resident.id);
		$("#addresident").find("#realName").val(resident.realName);
		$("#addresident").find("#sex").val(resident.sex);
		$("#addresident").find("#age").val(resident.age);
		$("#addresident").find("#nation").val(resident.nation);
		select2Object.val(resident.nation).trigger("change");
		$("#addresident").find("#idNumber").val(resident.idNumber);
		 var periodValidityDate=resident.periodValidityDate;
		 if(periodValidityDate!=""&&periodValidityDate!=null){
			 $("#addresident").find("#periodValidityDate").val(periodValidityDate.substring(0,10));
		 }
		$("#addresident").find("#healthNumber").val(resident.healthNumber);
		$("#addresident").find("#socialNumber").val(resident.socialNumber);
		$("#addresident").find("#officeId").val(resident.officeId);
		residentTree.expandAll(false);
		residentTree.refresh();
		var treeNode=residentTree.getNodeByParam("id",resident.areaId,null);
		if(treeNode!=null){
			var node = treeNode.getParentNode();
			$("#addresident").find("#areaname").val(node.name+"-"+treeNode.name);
		residentTree.selectNode(treeNode,true);
		residentTree.checkNode(treeNode, !treeNode.checked, true);  
		}else{
			$("#addresident").find("#areaname").val('');
		}
		$("#addresident").find("#areaId").val(resident.areaId);
		$("#addresident").find("#education").val(resident.education);
		$("#addresident").find("#wrokUnit").val(resident.wrokUnit);
		$("#addresident").find("#phone").val(resident.phone);
		$("#addresident").find("#postCode").val(resident.postCode);
		$("#addresident").find("#wrokUnit").val(resident.wrokUnit);
		$("#addresident").find("#houseAddress").val(resident.houseAddress);
		$("#addresident").find("#nowAddress").val(resident.nowAddress);
	}else{
		residentTree.expandAll(false);
		residentTree.refresh();
		$("#addresident").find("#id").val('');
		$("#addresident").find("#realName").val('');
		$("#addresident").find("#sex").val('');
		$("#addresident").find("#age").val('');
		$("#addresident").find("#nation").val('');
		$("#addresident").find("#idNumber").val('');
		$("#addresident").find("#periodValidityDate").val('');
		$("#addresident").find("#healthNumber").val('');
		$("#addresident").find("#socialNumber").val('');
		$("#addresident").find("#officeId").val('');
		$("#addresident").find("#areaname").val('');
		$("#addresident").find("#education").val('');
		$("#addresident").find("#wrokUnit").val('')
		$("#addresident").find("#phone").val('')
		$("#addresident").find("#postCode").val('')
		$("#addresident").find("#wrokUnit").val('')
		$("#addresident").find("#houseAddress").val('')
		$("#addresident").find("#nowAddress").val('')
	}
}
/***
 * 其他居民健康卡信息查询
 */
function otherResidentQuery(){
	 popup('其他居民健康卡信息查询',residentPath + '/otherResidentQuery','1000','450');
}
/**
 * 修改居民健康卡基本信息
 */
function updateresident(){
	var addresidentForm = $("#addresidentForm");
	if(addresidentForm && addresidentForm.length>0){
		addresidentForm.Validform({
			btnSubmit:"#form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:false,
			dragonfly:false,
			tipSweep:false,
			label:".label",
			showAllError:false,
			postonce:true,
			ajaxPost:true,
			datatype:customDataType,
			usePlugin:{
				swfupload:{},
				datepicker:{},
				passwordstrength:{},
				jqtransform:{
					selector:"select,input"
				}
			},
			beforeCheck:function(curform){
			},
			beforeSubmit:function(curform){
			},
			callback:function(data){
				if(data.status==0){
					layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
					//	signListTable.fnFilter();
						residentListTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    $("#addresidentForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}

}/**
 * 设置区域机构树
 */
var indexTree;
function setAreaTree(){
    //popupForHtml('设置区域机构树',$('#areaTreediv'),'30%','40%');
	// 删除validform 验证错误信息
	removeValidErrorMsg($('#areaTreediv'));
	indexTree = layer.open({
		type: 1,
		title: "设置区域机构树",
		area: ['30%', '45%'], //定义层的宽高
		maxmin:true, 
		zIndex:1050,
		content:$('#areaTreediv')
	});
}
function closeTree(){
	$("#addresidentForm").find("#areaname").blur();
	layer.close(indexTree);
	
}

function onchange(){
	$("#addresidentForm").find("#areaname").blur();
	layer.close(indexTree);
	
}
/**
 * [导入基本信息](解析居民基本信息)
 */
function residentImport() {
	popupForHtml('居民基本信息导入', $('#importResident'), '40%', '50%');
	$('#importResident_a').attr('href',contextPath+'/template/baseResident.xlsx');
	$('#importResident_a').text('基本信息模板.xlsx');
	loadWebUploaderExcel();
	$('#batchImport_submit').unbind("click");
	$("#batchImport_submit").click(function() {
		var ie8 = isIe8();
		if (!ie8) {
			loadAlert("importResident", "正在解析文件");
		}
		setTimeout(function(){ 
			var upFileExcelPath = $("#upFileExcelPath").val();
			if (upFileExcelPath == null || upFileExcelPath == "") {
				layer.alert("请上传文件");
				if (!ie8) {
					load.loadClose();
				}
				return;
			}
			LegalFalg=judgmentResidentBaseinfoLegal(ie8,upFileExcelPath,15);
			if(LegalFalg){
				if (!ie8) {
					load.loadClose();
				}
				popup('居民基本信息导入', importResidentPath + "/baseListPage?excelUrl="+upFileExcelPath,  '1000', '550');
			}
		},300);
	});

}
/**
 * [导入基本信息]将基本信息批量插入数据库
 */
function batchAddResidentBaseinfo() {
	if (falg) {
		$.ajax({
			"type" : 'post',
			"url" : importResidentPath + "/batchAddResidentBaseinfo",
			"dataType" : "json",
			"async" : false,
			"success" : function(data) {
				if (data.status == 0) {
					layer.alert(data.message, {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						parent.residentListTable.fnFilter();
						parent.layer_close();
					});
				} else {
					layer.alert(data.message)
				}
			}
		});
	} else {
		layer.alert("数据存在错误不能进行导入,请修改数据");
	}
}
/**
 *[导入基本信息和扩展信息](居民全部信息,解析基本信息和扩展信息)
 */
function allresidentImport() {
	popupForHtml('居民全部信息导入', $('#importResident'),  '40%', '50%');
	$('#importResident_a').attr('href',contextPath+'/template/allResident.xlsx');
	$('#importResident_a').text('基本和扩展模板.xlsx');
	loadWebUploaderExcel();
	$('#batchImport_submit').unbind("click");
	$("#batchImport_submit").click(function() {
		var ie8 = isIe8();
		if (!ie8) {
			loadAlert("importResident", "正在解析文件");
		}
		setTimeout(function(){
			var upFileExcelPath = $("#upFileExcelPath").val();
			if (upFileExcelPath == null || upFileExcelPath == "") {
				layer.alert("请上传文件");
				if (!ie8) {
					load.loadClose();
				}
				return;
			}
			LegalFalg=judgmentResidentBaseinfoLegal(ie8,upFileExcelPath,22);
			if(LegalFalg){
				if (!ie8) {
					load.loadClose();
				}
				popup('居民基本和扩展信息导入', importResidentPath + "/listPage?excelUrl="+upFileExcelPath, '1000', '550');
			}
		},300);
	});
}
/**
 * [导入基本信息和扩展信息]将基本信息和扩展信息批量插入数据库
 */
function batchAddResidentData() {
	if (falg) {
		$.ajax({
			"type" : 'post',
			"url" : importResidentPath + "/batchAddResidentData",
			"dataType" : "json",
			"async" : false,
			"success" : function(data) {
				if (data.status == 0) {
					layer.alert(data.message, {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						parent.residentListTable.fnFilter();
						parent.layer_close();
					});
				} else {
					layer.alert(data.message)
				}
			}
		});
	} else {
		layer.alert("数据存在错误不能进行导入,请修改数据");
	}

}
/***
 * 判断Excel文件数据是否合法
 * @param excelUrl
 */
function judgmentResidentBaseinfoLegal(ie8,excelUrl,number){
	var LegalFalg=true;
	$.ajax({
		"type" : 'post',
		"url" : importResidentPath + "/judgmentResidentBaseinfoLegal",
		"dataType" : "json",
		"data":{"excelUrl":excelUrl,"number":number},
		"async" : false,
		"success" : function(data) {
			if (data.status== 1) {
				LegalFalg=false;
				if (!ie8) {
					load.loadClose();
				}
				layer.alert(data.message)
			}
		}
	});
	return LegalFalg;
}
/**
 * 判断浏览器是否是ie8及其以下版本
 */
function isIe8() {
	var DEFAULT_VERSION = "8.0";
	var ua = navigator.userAgent.toLowerCase();
	var isIE = ua.indexOf("msie") > -1;
	var safariVersion;
	if (isIE) {
		safariVersion = ua.match(/msie ([\d.]+)/)[1];
	}
	if (safariVersion <= DEFAULT_VERSION) {
		return true;
	} else {
		return false;
	}
}
/**
 * 选择二级机构
 */
function selectMechanis(){
	parantId=$("#areaId").val();
	if(parantId!=null&&parantId!=""){
		$("#regional").val(parantId);
	}
	$.ajax({
		"type" : 'get',
		"url" : residentPath + "/administrativeDivisionListByParentId",
		"dataType" : "json",
		"data":{"parantId":parantId},
		"async" : false,
		"success" : function(data) {
		 setLogisticsCompany(data,$("#areaId2"));
		}
	});
}
/**
 * 设置二级机构
 * 
 * @param tag
 * @param companyCode
 */
function setLogisticsCompany(list,tag) {
	tag.empty();// 清空就选项
	tag.append("<option value=''> --请选择-- </option>");
	for (var i = 0; i < list.length; i++) {
		$option = $('<option value="' + list[i].id + '">' + list[i].name + '</option>');
		tag.append($option);
	}
}