var contactListPath = contextPath + "/contact";
var contactTable;
var tag="#certificateTypeCode";
/**
 * 初始化联系人列表
 */
$(document).ready(function(){
	iniList();
	$("#certificateTypeCode").change(function(){
		var certificateNo=$("#certificateNo").val()
		$("#certificateNo").val('').blur().val(certificateNo).blur();
	});
	$("#updatecertificateTypeCode").change(function(){
		var no=$("#updatecertificateNo").val()
		$("#updatecertificateNo").val('').blur().val(no).blur();
	});
});
function iniList(){
	var contactList=$("#contactList");
	if(contactList && contactList.length>0){
		contactTable = contactList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":5,
		    "bSort": true,
		    "aaSorting" : [[6,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,1,2,3,4]}],
		    "aLengthMenu": [[5,10,15], [5,10,15]],// 定义每页显示数据数量
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
		    "sAjaxSource": contactListPath+'/list',
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
			     "mData": 'certificateTypeCode',
			     'sClass': 'center',
			     'sName':"certificateTypeCode",
			    "render": function (data,type,full) {
			    	return '<a class="particulars" onclick="queryContactDetails('+full.id+')" href="javascript:;">'+fn_getDictByKeyCode("indexCertificateTypeCode",data+'')+'</a>';
			    } 
			   },
			   {
				   "mData": 'certificateNo',
				   'sClass': 'center',
				   'sName':"certificateNo"
			   },
			   {
				   "mData": "contactName",
				   'sClass': 'center',
				   'sName':"contactName"
			   },
			    {
			    	"mData": "contactNo",
			    	'sClass': 'center',
			    	'sName':"contactNo"
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
	                { "name": "filter_personId_EQ", "value": $('#personId').val() },
	                { "name": "filter_certificateTypeCode_EQ", "value": $('#indexCertificateTypeCode').val() },
	                { "name": "filter_contactName_EQ", "value": $('#indexcontactName').val() },
	                { "name": "filter_certificateNo_EQ", "value": $('#indexcertificateNo').val() },
	                { "name": "filter_contactNo_EQ", "value": $('#indexcontactNo').val() }
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
 * 联系人列表条件查询
 */
function search(){
	contactTable.fnFilter();
}
/**
 * 联系人信息详情
 */
function queryContactDetails(id){
	popup('联系人信息详情',contactListPath+'/queryContactDetails?id='+id,'650','370');
}
/***
 *获取单个复选框的联系人
 */
function getcontact(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个联系人");
		return false;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个联系人");
		return false;
	}
	return getCheckedObject(contactTable);
}
/**
 * 根据ID删除联系人
 */
function deleteContactById(){
	var contact=getcontact()
	if(contact!=false){
		layer.confirm("确认删除该联系人吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : contactListPath + "/deleteContactById",
				"dataType" : "json",
				"data":{"id":contact.id},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							contactTable.fnDraw(true); //刷新当前页面
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
 * 添加联系人
 */
function addContact(){
	 tag="#certificateTypeCode";
	var mpiId=$("#mpiId").val();
    $("#addContact form input").each(function() {
		$(this).val('');
    });
    mpiId=$("#mpiId").val(mpiId);
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	popupForHtml('添加联系人',$('#addContact'),'70%','69%');
	var addContactForm = $("#addContactForm");
	if(addContactForm && addContactForm.length>0){
		addContactForm.Validform({
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
						contactTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					   // $("#addContactForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/***
 * 修改联系人
 */

var contact
function updateContactPage(){
	 tag="#updatecertificateTypeCode";
	contact=getcontact()
	if(contact!=false){
		setupdateContactFormValue(contact)//为编辑表单设置值
		popupForHtml('编辑联系人',$('#updateContact'),'70%','69%');
	}
	updateContact();
}
/**
 * 编辑联系人为表单设置值
 */
function setupdateContactFormValue(contact){
	$("#updateContact").find("#updateid").val(contact.id);
	$("#updateContact").find("#updatempiId").val(contact.mpiId);
	$("#updateContact").find("#mpiId").val(contact.mpiId);
	$("#updateContact").find("#updatecertificateTypeCode").val(contact.certificateTypeCode);
	$("#updateContact").find("#updatecontactName").val(contact.contactName);
	$("#updateContact").find("#updatecertificateNo").val(contact.certificateNo);
	$("#updateContact").find("#updatecontactNo").val(contact.contactNo);
	$("#updateContact").find("#updatecreateUnit").val(contact.createUnit);
	$("#updateContact").find("#updatelastModifyUnit").val(contact.lastModifyUnit);
}
/**
 * 修改联系人信息(提交数据库)
 */
function updateContact(){
	var updateContactForm = $("#updateContactForm");
	if(updateContactForm && updateContactForm.length>0){
		updateContactForm.Validform({
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
						contactTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    $("#updateContactForm :input").val("");
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
		"n0":/^[0-9a-zA-Z]{1,50}$/, // 字母或数字
		"postCode6":/^[\d+]{6}$/,//6位数字
		"tel":/^1[3|5|8|7|9][0-9]\d{8}$/,
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
		},
		"certificateNoVailType":function(gets,obj,curform,regxp){
		return certificateNoVailType(gets,obj,curform,regxp);
} // 推广
		
};

function certificateNoVailType(gets, obj, curform, regxp) {
	var type = $(tag).val();
	if (type == null || type.length <= 0) {
		obj.attr("nullmsg","请选择证件类型！")
		return "请选择证件类型！"
	}
	if (gets == null || gets.length <= 0) {
		obj.removeAttr("nullmsg");
		obj.attr("nullmsg","请填写信息！")
		return "请填写信息！"
	}
	switch (type) {
	case "01":
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  // 验证身份证
		if (!reg.test(gets)) {
			return "身份证号码格式错误";
		}
		break;
	case "03":
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  // 户口簿证件号
		if (!reg.test(gets)) {
			return "居民户口簿证件号码格式错误";
		}
		break;
	case "04":
		var reg =  /^[a-zA-Z0-9]{3,21}$/ ;  // 护照
		if (!reg.test(gets)) {
			return "护照号码格式错误";
		}
		break;
	case "05":
		var reg = /^[0-9]{8,18}$/;  // 军官证
		if (!reg.test(gets)) {
			return "军官证号码格式错误";
		}
		break;
	case "08":
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  //驾照身份证
		if (!reg.test(gets)) {
			return "驾驶执照号码格式错误";
		}
		break;
	default:
		var reg = /^[0-9a-zA-Z_]{8,20}$/; // 验证规则
		if (!reg.test(gets)) {
			return "证件号码格式错误";
		}
	}
	return  true//改为重复验证		
} 
