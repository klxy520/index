var residentPath = contextPath + "/residentsInfo";
var importResidentPath = contextPath + "/residentsInfoImport";
var residentListTable;
var areaTree;
var areaNode;
var residentInfo=null;
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
		    "aaSorting" : [[11,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,2,3,4,,5,6,7,8,10]}],
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
			     "mData": 'name',
			     'sClass': 'center',
			     'sName':"name",
			    "render": function (data,type,full) {
					       return '<a class="particulars" onclick="queryResidentBaseinfoDetails('+full.id+')" href="javascript:;">'+data+'</a>';
			    } 
			   },
			   {
			    	"mData": "identityNumber",
			    	'sClass': 'center',
			    	'sName':"identityNumber"
			    },
			     {
				     "mData": 'bidUtil',
				     'sClass': 'center',
				     'sName':"bidUtil"
				     },
		     {
		        "mData": "bankCardNumber",
		        'sClass': 'center',
		        'sName':"bankCardNumber"
		    },
		    {
		    	"mData": "cardType",
		    	'sClass': 'center',
		    	'sName':"cardType",
		    	 "render": function (data,type,full) {
				       return fn_getDictByKeyCode("card_type",data+'') ;
		    } 
		    },
		    {
		        "mData": "socialSecurityNum",
		        'sClass': 'center',
		        'sName':"socialSecurityNum"
		    },
		    {
		    	"mData": "isFloating",//renyuanxingz
		    	'sClass': 'center',
		    	'sName':"isFloating",
		    	 "render": function (data,type,full) {
				     return   data==0?"常住人口":"流动人口"
		    	 } 
		    },    
		    {
		    	"mData": "issuersCardName",
		    	'sClass': 'center',
		    	'sName':"issuersCardName"
		    },
		    {
		    	"mData": "issuingTime",
		    	'sClass': 'center',
		    	'sName':"issuingTime"
		    },
		    {
		    	"mData": "cardStatus",
		    	'sClass': 'center',
		    	'sName':"cardStatus"
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
	                   { "name": "filter_name_EQ", "value": $('#name').val() },
	                   { "name": "filter_identityNumber_EQ", "value": $('#identityNumber').val() },
	                   { "name": "filter_bidUtil_EQ", "value": $('#bidUtil').val() },
	                   {"name": "filter_bankCardNumber_EQ", "value": $('#bankCardNumber').val() },
	                   {"name": "filter_socialSecurityNum_EQ", "value": $('#socialSecurityNum').val() }, 
	                   { "name": "filter_issuersCardName_EQ", "value": $("#issuersCardName").val() },
	                   { "name": "filter_cardType_EQ", "value": $("#cardType1").val() },
	                   { "name": "filter_issuersCardName_EQ", "value": $("#issuersCardName").val() },
	                   { "name": "filter_cardStatus_EQ", "value": $("#cardStatus1").val() },
	                   { "name": "filter_maxDate_LEQ", "value": $('#maxDate').val()},   
		               { "name": "filter_minDate_GEQ", "value": $("#minDate").val() },
		               { "name": "filter_maxUpateDate_LEQ", "value": $('#maxUpateDate').val()+' 23:59:59'},   
		               { "name": "filter_minUpateDate_GEQ", "value": $("#minUpateDate").val()+' 00:00:00'},
	                   { "name": "filter_isFloating_EQ", "value": $("#isFloating").val() }

            ),	
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
$(document).ready(function(){
	iniList()
	$.ajax({
		"type":"POST",
		"url":contextPath + "/administrativeDivision/topAdministrativeDivision",
		"dataType":"json",
		"async":true,
		"data":null,
		"success":function(data){
			if(data.status==0){
				areaNode=data.data;
				areaTree=$.fn.zTree.init($("#areaTree"), setting, areaNode);
				areaTree.selectNode(areaTree.getNodes()[0]);
			}else{
				layer.alert(data.message);
			}   
		}
	});
});
/**
 * 居民基本信息列表条件查询
 */
function search(){
	residentListTable.fnFilter();
}
/**
 * 根据ID删除居民健康卡基本信息
 */
function deleteResidentsInfo(){
	var residentsInfo=getResident()
	if(residentsInfo!=false){
		layer.confirm("确认删除基本信息和扩展信息?",function(){
			$.ajax({
				"type" : 'post',
				"url" : residentPath + "/deleteResidentsinfoById",
				"dataType" : "json",
				"data":{"id":residentsInfo.id,"name":residentsInfo.name,"identityNumber":residentsInfo.identityNumber},
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
 * 其他居民健康卡信息查询
 */
function otherResidentsInfoQuery(){
	 popup('其他居民健康卡信息查询',residentPath + '/otherResidentsInfoQuery','1000','480');
}
/**
 * [导入基本信息](解析居民基本信息)
 */
function residentImport() {
	popupForHtml('居民基本信息导入', $('#importResident'), '40%', '50%');
	$('#importResident_a').attr('href',contextPath+'/template/居民信息模板.xlsx');
	$('#importResident_a').text('居民信息模板.xlsx');
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
			LegalFalg=judgmentResidentBaseinfoLegal(ie8,upFileExcelPath,28);
			if(LegalFalg){
				if (!ie8) {
					load.loadClose();
				}
				popup('居民基本信息导入', importResidentPath + "/listPage?excelUrl="+upFileExcelPath,  '1000', '550');
			}
		},300);
	});
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
 * 查询单条详细信息
 */
function queryResidentBaseinfoDetails(id){
	popup('居民健康卡基本信息详情',residentPath+'/queryResidentsInfoDetails?id='+id,'800','500');
}

/**
 * 
 */

/**
 * 添加居民健康卡基本信息
 */
function addResidentsInfo(){	
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	$("#add form input").each(function() {
		$(this).val('');
	});  
	areaTree=$.fn.zTree.init($("#areaTree"), setting, areaNode);
	popupForHtml('添加居民健康卡基本信息',$('#add'),'70%','96%');
	var addresidentForm = $("#addResidentsInfoForm");
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
			datatype:ResidentVailType,
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
					    var SelectArr = $("select")
						for (var i = 0; i < SelectArr.length; i++) {
							SelectArr[i].options[0].selected = true;
						}
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}

//自定义验证类型
var ResidentVailType={
		"s2-10":/^[\S+]{1,20}$/,//2-10个字符
		"zh2-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/,
		"name":/^[\u4e00-\u9fa5\\]{2,20}$/,//输入2-20个中文字符允许小数点
		"zh4-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{4,20}$/,
		"zh2-50":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,50}$/,
		"zhy2-50":/^[\u4e00-\u9fa5a-zA-Z]{2,50}$/,
		"lm8":/^[a-zA-Z0-9_]{8}$/, // 8位字母+数字
		"n0":/^[0-9a-zA-Z]*$/, // 字母或数字
		"tel":/^((\d{3,4}\-)|)\d{7,8}$/, // 固定电话：区号+号码
		"sfzh": /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,//身份证号(15位,18位都可以)
		"lxdh":/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,//固定电话和手机号码都可以
		"bank":/^[\d+]{8,20}$/ ,//银行卡号  修复bug595:银行卡字段输入9位数字,无法验证通过
		"socialNum":/^[\d+]{8,20}$/, //社保卡号
		"bidUtil":/^[\S+]{2,20}$/ ,//申办单位
		"issuingBank": /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/ , //发卡银行
		"issuingSerialNumber":/^[\d+]{10}$/ , //发卡序列号
		"issuersCardCertificate":/^[\S+]{0,20}$/ ,//发卡机构证书
		"chipNum":/^[A-Za-z0-9]{15,20}$/, //芯片号
		"address":/^.{1,50}$/,
		"exist_ID_datatype":function(gets,obj,curform,regxp){
			return queryResidentsInfoExistence(gets,obj,curform,regxp);
		} // 推广
};

var setting = {
		check: {enable: true,chkStyle: "radio",radioType: "all",chkboxType:  { "Y": "", "N": "" }},
		data: {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentid",
				rootPId : null
			}
		},
		callback: {onCheck: zTreeOnCheck}
};
function zTreeOnCheck(event, treeId, treeNode) {
//	var node = treeNode.getParentNode();
	var areaName="";
	var node=treeNode
	$("#addResidentsInfoForm").find("#areaId").val(treeNode.id);
	while (node) {
		areaName=node.name+"-"+areaName
		node = node.getParentNode();
	  }
	areaName=areaName.substring(0,areaName.length-1)
	$("#addResidentsInfoForm").find("#areaName").val(areaName);
};


//发卡机构名称下拉框修改为代码输入框以及名称赋值
$("#getIssuersCardCode").change(function(){
	$("#addResidentsInfoForm").find("#issuersCardName").val($("#getIssuersCardCode").find('option:selected').text());
	$("#addResidentsInfoForm").find("#issuersCardCode").val(this.value)
});
var indexTree;
$("#areaName").click(function(){
	removeValidErrorMsg($('#addresidentForm'));
	indexTree = layer.open({
		type: 1,
		title: "选择区域机构",
		area: ['30%', '45%'], //定义层的宽高
		maxmin:true, 
		zIndex:1050,
		content:$('#areaTreediv')
	});
});
function closeTree(){
	$("#addResidentsInfoForm").find("#areaName").blur();
	layer.close(indexTree);
};



function  updateResidentsInfo(){
	 $("#addresidentForm :input").val("");
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}//清空下拉框
	areaTree=$.fn.zTree.init($("#areaTree"), setting, areaNode);
	var resident=getResidentsInfo()
	if(resident!=null){
		var residentDB=getResidentById(resident.id)
		setAddresidentFormValue(residentDB)//为编辑表单设置值
	}
	updateResidentsInfos();
}

function getResidentsInfo() {
	var checkedIds = getCheckedIds();
	if (!checkedIds || checkedIds.length == 0) {
		layer.alert("请选择一条居民信息");
		return false;
	} else if (checkedIds.length > 1) {
		layer.alert("只能选择一条居民信息");
		return false;
	}
	return getCheckedObject(residentListTable);
}
function getCheckedIds(){
	var checkedIds = null;
	var checkedDoms = $(".checkbox_id:checked");
	if(checkedDoms && checkedDoms.length>0){
		checkedIds = new Array();
		for(var i=0;i<checkedDoms.length;i++){
			checkedIds.push($(checkedDoms[i]).val());
		}
	}
	return checkedIds;
}
function updateResidentsInfos(){	 
	popupForHtml('编辑居民健康卡基本信息',$('#add'),'70%','96%');
	var addresidentForm = $("#addResidentsInfoForm");
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
			datatype:ResidentVailType,
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
						residentListTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addresidentForm :input").val("");
					    var SelectArr = $("select")
						for (var i = 0; i < SelectArr.length; i++) {
							SelectArr[i].options[0].selected = true;
						}
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
function getResidentById(id){
	var resident=null;
	$.ajax({
		"type" : 'get',
		"url" : residentPath + "/queryResidentinfoForUpdate",
		"dataType" : "json",
		"data":{"id":id},
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


/*updateResidentsInfo*/
function setAddresidentFormValue(resident){
	residentInfo=resident;
		$("#addResidentsInfoForm").find("#id").val(resident.id);
		$("#addResidentsInfoForm").find("#bidUtil").val(resident.bidUtil);
		$("#addResidentsInfoForm").find("#cardType").val(resident.cardType);
		$("#addResidentsInfoForm").find("#issuersCardName").val(resident.issuersCardName);
		$("#addResidentsInfoForm").find("#issuersCardCode").val(resident.issuersCardCode);
		$("#addResidentsInfoForm").find("#getIssuersCardCode").val(resident.issuersCardCode);
		
		$("#addResidentsInfoForm").find("#issuingSerialNumber").val(resident.issuingSerialNumber);
		$("#addResidentsInfoForm").find("#issuersCardCertificate").val(resident.issuersCardCertificate);
		$("#addResidentsInfoForm").find("#issuingTime").val(resident.issuingTime);
		$("#addResidentsInfoForm").find("#name").val(resident.name);
		$("#addResidentsInfoForm").find("#issuersCardCode").val(resident.issuersCardCode);
		$("#addResidentsInfoForm").find("#sex").val(resident.sex);
		
		$("#addResidentsInfoForm").find("#nationalCode").val(resident.nationalCode);
		$("#addResidentsInfoForm").find("#birthday").val(resident.birthday);
		$("#addResidentsInfoForm").find("#identityNumber").val(resident.identityNumber);
		$("#addResidentsInfoForm").find("#cardValidityPeriod").val(resident.cardValidityPeriod);
		$("#addResidentsInfoForm").find("#selfPhone").val(resident.selfPhone);
		$("#addResidentsInfoForm").find("#medicalPayment").val(resident.medicalPayment);
		
		$("#addResidentsInfoForm").find("#nationalCode").val(resident.nationalCode);
		$("#addResidentsInfoForm").find("#birthday").val(resident.birthday);
		$("#addResidentsInfoForm").find("#identityNumber").val(resident.identityNumber);
		$("#addResidentsInfoForm").find("#cardValidityPeriod").val(resident.cardValidityPeriod);
		$("#addResidentsInfoForm").find("#selfPhone").val(resident.selfPhone);
		$("#addResidentsInfoForm").find("#medicalPayment").val(resident.medicalPayment);
		
		$("#addResidentsInfoForm").find("#nationalCode").val(resident.nationalCode);
		$("#addResidentsInfoForm").find("#birthday").val(resident.birthday);
		$("#addResidentsInfoForm").find("#identityNumber").val(resident.identityNumber);
		$("#addResidentsInfoForm").find("#cardValidityPeriod").val(resident.cardValidityPeriod);
		$("#addResidentsInfoForm").find("#selfPhone").val(resident.selfPhone);
		$("#addResidentsInfoForm").find("#medicalPayment").val(resident.medicalPayment);
		
		$("#addResidentsInfoForm").find("#nationalCode").val(resident.nationalCode);
		$("#addResidentsInfoForm").find("#birthday").val(resident.birthday);
		$("#addResidentsInfoForm").find("#identityNumber").val(resident.identityNumber);
		$("#addResidentsInfoForm").find("#cardValidityPeriod").val(resident.cardValidityPeriod);
		$("#addResidentsInfoForm").find("#selfPhone").val(resident.selfPhone);
		$("#addResidentsInfoForm").find("#medicalPayment").val(resident.medicalPayment);
		
		$("#addResidentsInfoForm").find("#houseAddress").val(resident.houseAddress);
		$("#addResidentsInfoForm").find("#nowAddress").val(resident.nowAddress);
		$("#addResidentsInfoForm").find("#contactName").val(resident.contactName);
		$("#addResidentsInfoForm").find("#contactRelation").val(resident.contactRelation);
		$("#addResidentsInfoForm").find("#contactPhone").val(resident.contactPhone);
		$("#addResidentsInfoForm").find("#educationLevelCode").val(resident.educationLevelCode);
		
		$("#addResidentsInfoForm").find("#professionalCode").val(resident.professionalCode);
		$("#addResidentsInfoForm").find("#socialSecurityNum").val(resident.socialSecurityNum);
		$("#addResidentsInfoForm").find("#issuingBank").val(resident.issuingBank);
		$("#addResidentsInfoForm").find("#cardStatus").val(resident.cardStatus);
		$("#addResidentsInfoForm").find("#officeId").val(resident.officeId);
		$("#addResidentsInfoForm").find("#chipNum").val(resident.chipNum);

		$("#addResidentsInfoForm").find("#areaId").val(resident.areaId);
		$("#addResidentsInfoForm").find("#cardSyncStatus1").val(resident.cardSyncStatus1);
		$("#addResidentsInfoForm").find("#cardSyncStatus2").val(resident.cardSyncStatus2);
		$("#addResidentsInfoForm").find("#maritalStatusCode").val(resident.maritalStatusCode);
		$("#addResidentsInfoForm").find("#bankCardNumber").val(resident.bankCardNumber);
		var areaName = '';
		if (resident.areaId) {
			var node = areaTree.getNodeByParam("id", resident.areaId, null);
			areaTree.selectNode(node, true);
			areaTree.checkNode(node, !node.checked, true);
			areaTree.expandNode(node, true, false, true,false);
			while (node) { 
				areaName = node.name + "-" + areaName
				node = node.getParentNode();
			}
		}
		areaName=areaName.substring(0,areaName.length-1)
		$("#addResidentsInfoForm").find("#areaName").val(areaName);
}
/***
 * 根据身份证号查询居民信息是是否存在
 */
function queryResidentsInfoExistence(gets,obj,curform,regxp){
	var check=true;
	if(residentInfo==null){
		check=queryResidentsInfoExistenceAXAJ(gets);
	}else{
		identityNumber=residentInfo.identityNumber
		if(identityNumber!=gets){
			check=queryResidentsInfoExistenceAXAJ(gets);	
		}
	}
	return check;
}
/***
 * 根据身份证号查询居民信息是是否存在
 */
function queryResidentsInfoExistenceAXAJ(gets){
	var check=true;
		$.ajax({
			"type" : 'get',
			"url" : residentPath + "/queryResidentsInfoExistence",
			"dataType" : "json",
			"data":{"identityNumber":gets},
			"async" : false,
			"success" : function(data) {
				if (data.status==1) {
					check="该身份证号已存在";
				}
			}
		});
	return check;
}