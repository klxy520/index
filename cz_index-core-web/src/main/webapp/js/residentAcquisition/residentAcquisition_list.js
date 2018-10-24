var residentAcquisitionListPath = contextPath + "/residentacquisition";
var residentacquisitionImportPath = contextPath + "/residentacquisitionImport";
var residentAcquisitionTable;
/**
 * 初始化居民采集信息列表
 */
$(document).ready(function(){
	iniList();
});
function iniList(){
	var residentAcquisitionList=$("#residentAcquisitionList");
	if(residentAcquisitionList && residentAcquisitionList.length>0){
		residentAcquisitionTable = residentAcquisitionList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aaSorting" : [[8,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,2,4,5,6,7]}],
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
		    "sAjaxSource": residentAcquisitionListPath+'/list',
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
					       return '<a class="particulars" onclick="queryresidentAcquisitionDetails('+full.id+')" href="javascript:;">'+data+'</a>';
			    } 
			   },
			   {
			    	"mData": "identityNumber",
			    	'sClass': 'center',
			    	'sName':"identityNumber"
			    },
			    {
			    	"mData": "certificateValidityPeriod",
			    	'sClass': 'center',
			    	'sName':"certificateValidityPeriod"
			    },
			     {
				     "mData": 'bidUtil',
				     'sClass': 'center',
				     'sName':"bidUtil"
				     },
		     {
		        "mData": "newRuralNumber",
		        'sClass': 'center',
		        'sName':"newRuralNumber"
		    },
		    {
		    	"mData": "socialSecurityNum",
		    	'sClass': 'center',
		    	'sName':"socialSecurityNum"
		    },
		    {
		    	"mData": "healthCardBank",
		    	'sClass': 'center',
		    	'sName':"healthCardBank"
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
	                 { "name": "filter_name_LIKE", "value": $('#name').val() },
	                  { "name": "filter_identityNumber_LIKE", "value": $('#identityNumber').val() },
	                    {"name": "filter_socialSecurityNum_LIKE", "value": $('#socialSecurityNum').val() }, 
	                    { "name": "filter_newRuralNumber_LIKE", "value": $("#newRuralNumber").val() } ,
	                    { "name": "filter_maxUpateDate_LEQ", "value": $('#maxUpateDate').val()+' 23:59:59'},   
	                    { "name": "filter_minUpateDate_GEQ", "value": $("#minUpateDate").val()+' 00:00:00'},
	                    { "name": "filter_bidUtil_LIKE", "value": $("#bidUtil").val() } ,
	                    { "name": "filter_healthCardBank_EQ", "value": $("#healthCardBank").val() }/*,
	                    { "name": "filter_issuersCardName_EQ", "value": $("#issuersCardName").val() },
	                    { "name": "filter_cardStatus_EQ", "value": $("#cardStatus1").val() },
	                     	{ "name": "filter_maxDate_LEQ", "value": $('#maxDate').val()},   
		                    { "name": "filter_minDate_GEQ", "value": $("#minDate").val() },
		                    { "name": "filter_maxUpateDate_LEQ", "value": $('#maxUpateDate').val()+' 23:59:59'},   
		                    { "name": "filter_minUpateDate_GEQ", "value": $("#minUpateDate").val()+' 00:00:00'}*/
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
/**
 * 居民采集信息列表条件查询
 */
function search(){
	residentAcquisitionTable.fnFilter();
}
/**
 * 查询单条详细信息
 */
function queryresidentAcquisitionDetails(id){
	popup('居民采集信息详情',residentAcquisitionListPath+'/queryresidentAcquisitionDetails?id='+id,'880','550');
}
/***
 *获取单个复选框的居民采集信息
 */
function getresidentAcquisition(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条居民采集信息");
		return false;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一条居民采集信息");
		return false;
	}
	return getCheckedObject(residentAcquisitionTable);
}
/**
 * 根据ID删除居民采集信息
 */
function deleteResidentAcquisition(){
	var residentAcquisition=getresidentAcquisition()
	if(residentAcquisition!=false){
		layer.confirm("确认删除居民采集信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : residentAcquisitionListPath + "/deleteResidentAcquisitionById",
				"dataType" : "json",
				"data":{"id":residentAcquisition.id,"name":residentAcquisition.name,"identityNumber":residentAcquisition.identityNumber},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							residentAcquisitionTable.fnDraw(true); //刷新当前页面
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
 * [导入居民采集信息](解析居民采集信息)
 */
function residentacquisitionImport() {
	popupForHtml('居民采集信息导入', $('#importResidentAcquisition'), '40%', '50%');
	$('#residentAcquisition_a').attr('href',contextPath+'/template/居民采集信息模板.xls');
	$('#residentAcquisition_a').text('居民采集信息模板.xls');
	loadWebUploaderExcel();
	$('#batchImport_submit').unbind("click");
	$("#batchImport_submit").click(function() {
		var ie8 = isIe8();
		if (!ie8) {
			loadAlert("importResidentAcquisition", "正在解析文件");
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
			LegalFalg=judgmentResidentAcquisitionLegal(ie8,upFileExcelPath);
			if(LegalFalg){
				if (!ie8) {
					load.loadClose();
				}
				popup('居民采集信息导入', residentacquisitionImportPath + "/listPage?excelUrl="+upFileExcelPath,  '1000', '550');
			}
		},300);
	});
}

/***
 * 判断Excel文件数据是否合法
 * @param excelUrl
 */
function judgmentResidentAcquisitionLegal(ie8,excelUrl){
	var LegalFalg=true;
	$.ajax({
		"type" : 'get',
		"url" : residentacquisitionImportPath + "/judgmentResidentAcquisitionLegal",
		"dataType" : "json",
		"data":{"excelUrl":excelUrl},
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
 * 添加居民采集信息
 */
function addResidentAcquisition(){	
	$("#addResidentAcquisition form input").each(function() {
		$(this).val('');
	});  
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	popupForHtml('添加居民采集信息',$('#addResidentAcquisition'),'72%','98%');
	var addResidentAcquisitionForm = $("#addResidentAcquisitionForm");
	if(addResidentAcquisitionForm && addResidentAcquisitionForm.length>0){
		addResidentAcquisitionForm.Validform({
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
						residentAcquisitionTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addResidentAcquisitionForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
var ResidentVailType={
		"s2-10":/^[\S+]{1,20}$/,//2-10个字符
		"zh2-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/,
		"name":/^[\u4e00-\u9fa5\\]{2,20}$/,//输入2-20个中文字符允许小数点
		"zh4-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{4,20}$/,
		"zh2-50":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,50}$/,
		"zhy2-50":/^[\u4e00-\u9fa5a-zA-Z]{2,50}$/,
		"lm8":/^[a-zA-Z0-9_]{8}$/, // 8位字母+数字
		"n0":/^[0-9a-zA-Z]*$/, // 字母或数字
		"postCode6":/^[\d+]{6}$/,//6位数字
		"tel":/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,
		"sfzh": /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,//身份证号(15位,18位都可以)
		"lxdh":/^[\d+]{8}$/,
		"bank":/^[\d+]{8,20}$/ ,//银行卡号  修复bug595:银行卡字段输入9位数字,无法验证通过
		"socialNum":/^[\d+]{9}$/, //社保卡号
		"newRuralNum":/^[\d+]{8,20}$/, //新农合号
		"bidUtil":/^[\S+]{1,20}$/ ,//申办单位
		"issuingBank": /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/ , //发卡银行
		"issuingSerialNumber":/^[\d+]{10}$/ , //发卡序列号
		"issuersCardCertificate":/^[\S+]{0,20}$/ ,//发卡机构证书
		"chipNum":/^[A-Za-z0-9]{15,20}$/, //芯片号
		"address":/^.{1,50}$/,
		"empty": /^\s*$/,
		"exist_ID_datatype":function(gets,obj,curform,regxp){
		 return queryResidentsInfoExistence(gets,obj,curform,regxp);
		} // 推广
};
/***
 * 修改居民采集信息()
 */

var residentAcquisition
function updateResidentAcquisitionPage(){
	residentAcquisition=getresidentAcquisition()
	if(residentAcquisition!=false){
		setaddresidentAcquisitionFormValue(residentAcquisition)//为编辑表单设置值
		popupForHtml('编辑居民采集信息',$('#updateResidentAcquisition'),'72%','98%');
	}
	updateResidentAcquisition();
}
/**
 * 编辑居民采集信息为表单设置值
 */
function setaddresidentAcquisitionFormValue(residentAcquisition){
	$("#updateResidentAcquisition").find("#id").val(residentAcquisition.id);
	$("#updateResidentAcquisition").find("#name").val(residentAcquisition.name);
	$("#updateResidentAcquisition").find("#identityNumber").val(residentAcquisition.identityNumber);
	$("#updateResidentAcquisition").find("#bidUtil").val(residentAcquisition.bidUtil);
	$("#updateResidentAcquisition").find("#issuersCertificateOrgan").val(residentAcquisition.issuersCertificateOrgan);
	$("#updateResidentAcquisition").find("#newRuralNumber").val(residentAcquisition.newRuralNumber);
	var certificateValidityPeriod=residentAcquisition.certificateValidityPeriod;
	if(certificateValidityPeriod!=""&&certificateValidityPeriod!=null){
		$("#updateResidentAcquisition").find("#certificateValidityPeriod").val(certificateValidityPeriod.substring(0,10));
	}
	$("#updateResidentAcquisition").find("#socialSecurityNum").val(residentAcquisition.socialSecurityNum);
	$("#updateResidentAcquisition").find("#educationLevel").val(residentAcquisition.educationLevel);
	$("#updateResidentAcquisition").find("#national").val(residentAcquisition.national)
	$("#updateResidentAcquisition").find("#contactPhone").val(residentAcquisition.contactPhone);
	$("#updateResidentAcquisition").find("#postCode").val(residentAcquisition.postCode);
	$("#updateResidentAcquisition").find("#industry").val(residentAcquisition.industry);
	$("#updateResidentAcquisition").find("#healthCardBank").val(residentAcquisition.healthCardBank);
	$("#updateResidentAcquisition").find("#professional").val(residentAcquisition.professional);
	$("#updateResidentAcquisition").find("#salaryCardBank").val(residentAcquisition.salaryCardBank);
	$("#updateResidentAcquisition").find("#houseAddress").val(residentAcquisition.houseAddress);
	$("#updateResidentAcquisition").find("#nowAddress").val(residentAcquisition.nowAddress);
}
/**
 * 修改居民采集信息(提交数据库)
 */
function updateResidentAcquisition(){
	var updateResidentAcquisitionForm = $("#updateResidentAcquisitionForm");
	if(updateResidentAcquisitionForm && updateResidentAcquisitionForm.length>0){
		updateResidentAcquisitionForm.Validform({
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
					//	signListTable.fnFilter();
						residentAcquisitionTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    $("#updateResidentAcquisitionForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/***
 * 根据身份证号查询居民信息是是否存在
 */
function queryResidentsInfoExistence(gets,obj,curform,regxp){
	var check=true;
	if(residentAcquisition==null){
		check=queryResidentsInfoExistenceAXAJ(gets);
	}else{
		identityNumber=residentAcquisition.identityNumber
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
			"url" : residentAcquisitionListPath+"/queryResidentsInfoExistence",
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