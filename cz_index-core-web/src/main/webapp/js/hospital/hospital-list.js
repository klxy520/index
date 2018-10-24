var hospitalListPath = contextPath + "/hospital";
var hospitalTable;
/**
 * 初始化医院机构管理列表
 */
$(document).ready(function(){
	iniList();
});
function iniList(){
	var hospitalList=$("#hospitalList");
	if(hospitalList && hospitalList.length>0){
		hospitalTable = hospitalList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aaSorting" : [[8,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,1,2,3,4,5,6]}],
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
		    "sAjaxSource": hospitalListPath+'/list',
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
			    	return '<a class="particulars" onclick="queryHospitalDetails('+full.id+')" href="javascript:;">'+data+'</a>';
			    } 
			   },
			   {
				   "mData": 'level',
				   'sClass': 'center',
				   'sName':"level"
			   },
			   {
				   "mData": "typeName",
				   'sClass': 'center',
				   'sName':"typeName"
			   },
			    {
			    	"mData": "hKey",
			    	'sClass': 'center',
			    	'sName':"hKey"
			    },
			     {
				     "mData": 'pycode',
				     'sClass': 'center',
				     'sName':"pycode"
			     },
			     {
			    	 "mData": 'status',
			    	 'sClass': 'center',
			    	 'sName':"status",
			    	 'mRender':function(data){
					    	if(data == "0"){
					    		return "启用";
					    	}else if(data == "1"){
					    		return "禁用";
					  }
			   }
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
	                { "name": "filter_name_EQ", "value": $('#indexName').val() },
	               { "name": "filter_level_EQ", "value": $('#indexlevel').val() },
	                { "name": "filter_typeName_EQ", "value": $('#indextypeName').val() },
	                { "name": "filter_hKey_EQ", "value": $('#indexhKey').val() },
	                { "name": "filter_status_EQ", "value": $('#indexstatus').val() },
	               { "name": "filter_maxUpateDate_LEQ", "value": $('#maxUpateDate').val()+' 23:59:59'} ,
		            { "name": "filter_minUpateDate_GEQ", "value": $("#minUpateDate").val()+' 00:00:00'}
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
 * 医院机构管理信息列表条件查询
 */
function search(){
	hospitalTable.fnFilter();
}
/**
 * 查询医院详细信息
 */
function queryHospitalDetails(id){
	popup('医院信息详情',hospitalListPath+'/queryHospitalDetails?id='+id,'800','410');
}
/***
 *获取单个复选框的医院信息
 */
function getHospital(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条医院信息");
		return false;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一条医院信息");
		return false;
	}
	return getCheckedObject(hospitalTable);
}
/**
 * 根据ID删除医院信息
 */
function deleteHospitalById(){
	var hospital=getHospital()
	if(hospital!=false){
		layer.confirm("确认删除该医院信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : hospitalListPath + "/deleteHospitalById",
				"dataType" : "json",
				"data":{"id":hospital.id,"name":hospital.name},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							hospitalTable.fnDraw(true); //刷新当前页面
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
 * 根据ID禁用医院信息
 */
function disableHospitalById(){
	var hospital=getHospital()
	if(hospital!=false){
		var status=hospital.status
		if(status!="1"){
		layer.confirm("确认禁用该医院信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : hospitalListPath + "/disableHospitalById",
				"dataType" : "json",
				"data":{"id":hospital.id,"name":hospital.name},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							hospitalTable.fnDraw(true); //刷新当前页面
							layer.closeAll();
						});
					}else{
						layer.alert(data.message)
					}
				}
			});
		});
		}else{
			layer.alert("该医院信息已为禁用状态")
		}
	}
}
/**
 * 根据ID启用医院信息
 */
function EnableHospitalById(){
	var hospital=getHospital()
	if(hospital!=false){
		var status=hospital.status
		if(status!="0"){
		layer.confirm("确认启用该医院信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : hospitalListPath + "/EnableHospitalById",
				"dataType" : "json",
				"data":{"id":hospital.id,"name":hospital.name},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							hospitalTable.fnDraw(true); //刷新当前页面
							layer.closeAll();
						});
					}else{
						layer.alert(data.message)
					}
				}
			});
		});
		}else{
			layer.alert("该医院信息已为启用状态")
		}
	}
}
/**
 * 添加地址信息
 */
var temp=null
function addHospital(){	
	temp=null;
	$("#addHospital form input").each(function() {
		$(this).val('');
	});  
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	$("#addHospital").find("#status").val("启用");
	popupForHtml('添加医院信息',$('#addHospital'),'70%','70%');
	var addAddressForm = $("#addHospitalForm");
	if(addAddressForm && addAddressForm.length>0){
		addAddressForm.Validform({
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
						hospitalTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    $("#addAddressForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/***
 * 修改地址信息
 */

var hospital
function updateHospitalPage(){
	var hospital=getHospital()
	if(hospital!=false){
		setupdateHospitalFormValue(hospital)//为编辑表单设置值
		popupForHtml('编辑医院信息',$('#updateHospital'),'70%','67%');
	}
	updateHospital();
}
/**
 * 编辑医院信息为表单设置值
 */
function setupdateHospitalFormValue(hospital){
	temp=hospital;
	$("#updateHospital").find("#id").val(hospital.id);
	$("#updateHospital").find("#name").val(hospital.name);
	$("#updateHospital").find("#level").val(hospital.level);
	$("#updateHospital").find("#type").val(hospital.type);
	$("#updateHospital").find("#typeName").val(hospital.typeName);
	$("#updateHospital").find("#hKey").val(hospital.hKey);
	$("#updateHospital").find("#phone").val(hospital.phone);
	$("#updateHospital").find("#address").val(hospital.address);
	$("#updateHospital").find("#pycode").val(hospital.pycode);
	$("#updateHospital").find("#status").val(hospital.status);
}
/**
 * 修改医院信息(提交数据库)
 */
function updateHospital(){
	var updateHospitalForm = $("#updateHospitalForm");
	if(updateHospitalForm && updateHospitalForm.length>0){
		updateHospitalForm.Validform({
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
						hospitalTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    $("#updateAddressForm :input").val("");
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
		"lm":/^[a-zA-Z]{0,20}$/, // 20位字母
		"n0":/^[0-9a-zA-Z]*$/, // 字母或数字
		"key":/^[\d+]{1,20}$/,//0,20位数字
		"tel":/^1[3|5|8|7][0-9]\d{4,8}$/,
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
		"exist_hospitalname_datatype":function(gets,obj,curform,regxp){
		 return queryResidentsInfoExistence(gets,obj,curform,regxp);
		}, // 推广
		"exist_key_datatype":function(gets,obj,curform,regxp){
			return queryHospitalByKeyExistence(gets,obj,curform,regxp);
		} // 推广
};
/***
 * 根据身份证号查询居民信息是是否存在
 */
function queryResidentsInfoExistence(gets,obj,curform,regxp){
	var check=true;
	if(temp==null){
		check=queryResidentsInfoExistenceAXAJ(gets);
	}else{
		var name=temp.name
		if(name!=gets){
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
			"url" : hospitalListPath + "/queryHospitalByNameExistence",
			"dataType" : "json",
			"data":{"name":gets},
			"async" : false,
			"success" : function(data) {
				if (data.status==1) {
					check="该医院信息已存在";
				}
			}
		});
	return check;
}
/***
 * 根据key查询居民信息是是否存在
 */
function queryHospitalByKeyExistence(gets,obj,curform,regxp){
	var check=true;
	if(temp==null){
		check=queryHospitalByKeyExistenceAXAJ(gets);
	}else{
		var name=temp.hkey
		if(name!=gets){
			check=queryHospitalByKeyExistenceAXAJ(gets);	
		}
	}
	return check;
}
/***
 * 根据key查询居民信息是是否存在
 */
function queryHospitalByKeyExistenceAXAJ(gets){
	var check=true;
	$.ajax({
		"type" : 'get',
		"url" : hospitalListPath + "/queryHospitalByKeyExistence",
		"dataType" : "json",
		"data":{"key":gets},
		"async" : false,
		"success" : function(data) {
			if (data.status==1) {
				check="该key已存在";
			}
		}
	});
	return check;
}