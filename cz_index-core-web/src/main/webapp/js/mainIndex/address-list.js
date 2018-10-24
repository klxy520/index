var addressListPath = contextPath + "/address";
var addressTable;
/**
 * 初始化主索引地址列表
 */
$(document).ready(function(){
	iniList();
});
function iniList(){
	var addressList=$("#addressList");
	if(addressList && addressList.length>0){
		addressTable = addressList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":5,
		    "bSort": true,
		    "aaSorting" : [[5,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,1,2,3]}],
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
		    "sAjaxSource": addressListPath+'/list',
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
			     "mData": 'addressTypeCode',
			     'sClass': 'center',
			     'sName':"addressTypeCode",
			    "render": function (data,type,full) {
			    	return '<a class="particulars" onclick="queryAddressDetails('+full.id+')" href="javascript:;">'+fn_getDictByKeyCode("indexAddressTypeCode",data+'')+'</a>';
			    } 
			   },
			   {
				   "mData": 'address',
				   'sClass': 'center',
				   'sName':"address"
			   },
			   {
				   "mData": "postalCode",
				   'sClass': 'center',
				   'sName':"postalCode"
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
	                { "name": "filter_addressTypeCode_EQ", "value": $('#indexaddressTypeCode').val() },
	                { "name": "filter_postalCode_EQ", "value": $('#indexpostalCode').val() },
	                { "name": "filter_maxUpateDate_LEQ", "value": $('#maxUpateDate').val()+' 23:59:59'},   
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
 * 主索引地址信息列表条件查询
 */
function search(){
	addressTable.fnFilter();
}
/**
 * 查询地址详细信息
 */
function queryAddressDetails(id){
	popup('地址信息详情',addressListPath+'/queryAddressDetails?id='+id,'650','370');
}
/***
 *获取单个复选框的主索引基本身份信息
 */
function getAddress(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条地址信息");
		return false;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一条地址信息");
		return false;
	}
	return getCheckedObject(addressTable);
}
/**
 * 根据ID删除地址信息
 */
function deleteAddressById(){
	var address=getAddress()
	if(address!=false){
		layer.confirm("确认删除该地址信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : addressListPath + "/deleteAddressById",
				"dataType" : "json",
				"data":{"id":address.id},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							addressTable.fnDraw(true); //刷新当前页面
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
 * 添加地址信息
 */
function addAddress(){
	 var mpiId=$("#mpiId").val();
	    $("#addAddress form input").each(function() {
			$(this).val('');
	    });
	    mpiId=$("#mpiId").val(mpiId);
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	popupForHtml('添加地址信息',$('#addAddress'),'70%','76%');
	var addAddressForm = $("#addAddressForm");
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
						addressTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					   // $("#addAddressForm :input").val("");
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

var raddress
function updateAddressPage(){
	address=getAddress()
	if(address!=false){
		setupdateAddressFormValue(address)//为编辑表单设置值
		popupForHtml('编辑地址信息',$('#updateAddress'),'70%','70%');
	}
	updateAddress();
}
/**
 * 编辑地址信息为表单设置值
 */
function setupdateAddressFormValue(address){
	$("#updateAddress").find("#updateid").val(address.id);
	$("#updateAddress").find("#updatempiId").val(address.mpiId);
	$("#updateAddress").find("#mpiId").val(address.mpiId);
	$("#updateAddress").find("#updateaddressTypeCode").val(address.addressTypeCode);
	$("#updateAddress").find("#updatepostalCode").val(address.postalCode);
	$("#updateAddress").find("#updatecreateUnit").val(address.createUnit);
	$("#updateAddress").find("#updatelastModifyUnit").val(address.lastModifyUnit);
	$("#updateAddress").find("#updateaddress").val(address.address);
}
/**
 * 修改地址信息(提交数据库)
 */
function updateAddress(){
	var updateAddressForm = $("#updateAddressForm");
	if(updateAddressForm && updateAddressForm.length>0){
		updateAddressForm.Validform({
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
						addressTable.fnDraw(true); //刷新当前页面
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