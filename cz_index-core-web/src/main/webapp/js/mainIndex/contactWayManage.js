var contactWayPath = contextPath + "/contactWay";
var contactWayTable;
var mainIndex=$('#allMpiId').val()
var pid=$('#allPersonId').val()
$(document).ready(function() {
	init(); // 初始化日志列表
	$("#contactTypeCode").change(function(){
		var no=$("#contactNo").val();
		$("#contactNo").val('').blur().val(no).blur();
	});
	$("#u_contactTypeCode").change(function() {
		var no=$("#u_contactNo").val();
		$("#u_contactNo").val('').blur().val(no).blur();
	});
});

/**
 * 初始化日志列表
 */
function init() {
	var contactWayList = $("#personContactWayList");
	if (contactWayList && contactWayList.length > 0) {
		contactWayTable = contactWayList
				.dataTable({
					"oLanguage" : { // 语言国际化
						"sUrl" : contextPath + "/js/de_DE.txt"
					},
					"bPaginate" : true,
					"bFilter" : false,
					"bLengthChange" : true,
					"iDisplayLength" : 5,
					"bSort" : true,
					"aaSorting" : [ [ 4, "desc" ] ],
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0, 1, 2 ]
					} ],
					"aLengthMenu" : [ [ 5, 10, 15  ], [ 5, 10, 15 ] ],// 定义每页显示数据数量
					"bInfo" : true,
					"bWidth" : true,
					"bScrollCollapse" : true,
					"sPaginationType" : "full_numbers",
					"bProcessing" : true,
					"bServerSide" : true,
					"bDestroy" : true,
					"bSortCellsTop" : true,
					"bStateSave" : true,// 状态保持 刷新页面保持页码等数据
					"asStripeClasses" : [ 'text-c odd', 'text-c even' ],
					"sAjaxSource" : contactWayPath + '/QueryPersonContactWay',
					"aoColumns" : [
							{
								"mData" : 'id',
								'sClass' : 'center',
								'sName' : "id",
								'mRender' : function(data, type, full) {
									return '<input  type="checkbox" id="checkbox_id_'
											+ data
											+ '" class="checkbox_id" value="'
											+ data + '">'
								}
							},

							{
								"mData" : 'contactNo',
								'sClass' : 'center',
								'sName' : "contactNo",
								"render" : function(data, type, full) {
									return '<a class="particulars" onclick="queryContactWayById('
											+ full.id
											+ ')" href="javascript:;">'
											+ data
											+ '</a>';
								}
							},

							{
								"mData" : 'contactTypeCode',
								'sClass' : 'center',
								'sName' : "contactTypeCode",
								"render" : function(data, type, full) {
									return fn_getDictByKeyCode(
											"contactWayTypeCode", data);
								}
							},

							{
								"mData" : 'createDate',
								'sClass' : 'center',
								'sName' : "createDate"
							}, {
								"mData" : 'updateDate',
								'sClass' : 'center',
								'sName' : "updateDate"
							}

					],
					"fnDrawCallback" : function(settings) {
						checkedOfClickTr(".checkbox_id");
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						aoData.push({
							"name" : "filter_personId_EQ",
							"value" : $('#allPersonId').val()
						}, {
							"name" : "filter_contactNo_EQ",
							"value" : $('#s_contactNo').val()
						}, {
							"name" : "filter_contactTypeCode_EQ",
							"value" : $('#s_contactWayTypeCode').val()
						}, {
							"name" : "filter_updateDate_LEQ",
							"value" : $('#maxUpateDate').val()
						}, {
							"name" : "filter_updateDate_GEQ",
							"value" : $("#minUpateDate").val()
						})
						$.ajax({
							"type" : 'post',
							"url" : sSource,
							"dataType" : "json",
							"data" : {
								aoData : JSON.stringify(aoData)
							},
							"success" : function(resp) {
								fnCallback(resp);
								$(":input[type='search']").attr("placeholder",
										"请输入");
							}
						});
					}
				});
	}
}

function deleteContactWay(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条联系方式信息");
		return false;
	}
	layer.confirm("确认删除该联系方式?",function(){
		$.ajax({
	        "type": 'post',
	        "url" : contactWayPath + "/deleteContactWayById?id="+checkedIds,
	        "dataType": "json",
	        "async" : false,
	        "success": function(data) {
	        	console.log(data)
	        	if(data.status==0){
					layer.alert('删除成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						contactWayTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	});
}

$("#search_btn").click(function() {
	contactWayTable.fnDraw();
});

/**
 * 添加联系方式信息
 */
function addContactWay(){	
	$("#addContactWay form  input").each(function() {
		$(this).val('');
	});  
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	
	$("#addContactWay").find("#personId").val(pid);
	$("#addContactWay").find("#mpiId").val(mainIndex);
	popupForHtml('添加联系方式',$('#addContactWay'),'70%','71%');
	console.log(1)
	var addContactWayForm = $("#addContactWayFrom");
	if(addContactWayForm && addContactWayForm.length>0){
		addContactWayForm.Validform({
			btnSubmit:"#add_form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:false,
			dragonfly:false,
			tipSweep:false,
			label:".label",
			showAllError:false,
			postonce:true,
			ajaxPost:true,
			datatype:addContactWayType,
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
				console.log(data)
				if(data.status==0){
					console.log(data)
					layer.alert("添加成功", {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						contactWayTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addContactWayForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/***
 * 编辑联系方式信息
 */
function updateContactWay(){	
	$("#updateContactWay form  input").each(function() {
		$(this).val('');
	});  
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条联系方式信息");
		return false;
	}
	setupdateContactWayForm();
	popupForHtml('编辑联系方式',$('#updateContactWay'),'70%','71%');
	var updateContactWayForm = $("#updateContactWayFrom");
	
	if(updateContactWayForm && updateContactWayForm.length>0){
		updateContactWayForm.Validform({
			btnSubmit:"#update_form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:false,
			dragonfly:false,
			tipSweep:false,
			label:".label",
			showAllError:false,
			postonce:true,
			ajaxPost:true,
			datatype:updateContactWayType,
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
				console.log(data)
				if(data.status==0){
					console.log(data)
					layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						contactWayTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#updateContactWayForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}


/**
 * 编辑联系方式信息为表单设置值
 */
function setupdateContactWayForm(){
	var updateContactWay =getCheckedObject(contactWayTable);
	$("#updateContactWay").find("#u_contactNo").val(updateContactWay.contactNo);
	$("#updateContactWay").find("#id").val(updateContactWay.id);
	$("#updateContactWay").find("#u_contactTypeCode").val(updateContactWay.contactTypeCode);
	$("#updateContactWay").find("#u_LastModifyUnit").val(updateContactWay.lastModifyUnit);
	$("#updateContactWay").find("#u_createUnit").val(updateContactWay.createUnit);	
	$("#updateContactWay").find("#mpiId").val(mainIndex);
	
}
var updateContactWayType = {
		"contactNo" : function(gets, obj, curform, regxp) {
			var type = $("#u_contactTypeCode").val();
			if (type == null || type.length <= 0) {
				obj.attr("nullmsg", "请选择联系方式！")
				return "请选择联系方式！"
			}
			if (gets == null || gets.length <= 0) {
				obj.removeAttr("nullmsg");
				obj.attr("nullmsg", "请填写信息！")
				return "请填写信息！"
			}
			switch (type) {
			case "07"://邮件
				var reg =/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/; 
				if (!reg.test(gets)) {
					return "邮件格式错误";
				}
				break;
			case "08"://msn
				var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/; 
				if (!reg.test(gets)) {
					return "msn格式错误";
				}
				break;
			case "09"://qq
				var reg = /^[0-9]\d{4,16}$/; 
				if (!reg.test(gets)) {
					return "QQ号格式错误";
				}
				break;
			case "99"://其他
				var reg = /^\S{4,20}$/; 
				if (!reg.test(gets)) {
					return "联系号码错误";
				}
				break;
			default:
				var reg = /^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$|^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/; 
				if (!reg.test(gets)) {
					return "电话号码格式错误";
				}
			}
			var no=	$('#u_contactNo').val()
			var type=$('#u_contactTypeCode').val()
			var id = $("#updateContactWay").find("#id").val();
			if (isExistNo(no,type) != -1 && id != isExistNo(no,type)) {
				return "已存在相同类型联系号码";
			}
			return true// 改为重复验证
		}
	}

var addContactWayType = {
		"contactNo" : function(gets, obj, curform, regxp) {
			var type = $("#contactTypeCode").val();
			if (type == null || type.length <= 0) {
				obj.attr("nullmsg", "请选择联系方式！")
				return "请选择联系方式！"
			}
			if (gets == null || gets.length <= 0) {
				obj.removeAttr("nullmsg");
				obj.attr("nullmsg", "请填写信息！")
				return "请填写信息！"
			}
			switch (type) {
			case "07"://邮件
				var reg =/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/; 
				if (!reg.test(gets)) {
					return "邮件格式错误";
				}
				break;
			case "08"://msn
				var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/; 
				if (!reg.test(gets)) {
					return "msn格式错误";
				}
				break;
			case "09"://qq
				var reg = /^[0-9]\d{4,16}$/; 
				if (!reg.test(gets)) {
					return "QQ号格式错误";
				}
				break;
			case "99"://其他
				var reg =/^\S{4,20}$/;; 
				if (!reg.test(gets)) {
					return "联系号码错误";
				}
				break;
			default:
				var reg = /^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$|^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/; 
				if (!reg.test(gets)) {
					return "电话号码格式错误";
				}
			}
			var no=	$('#contactNo').val()
			var type=$('#contactTypeCode').val()
			if (isExistNo(no,type) >0) {
				return "已存在相同类型联系号码";
			}
			return true// 改为重复验证
	}

}

	function isExistNo(no,type) {
		var id;
		$.ajax({
			"type" : 'post',
			"url" : contactWayPath + "/isExistNo",
			"data" : {
				"no" : no,
				"type" : type,
				"personId":	$('#allPersonId').val()	
			},
			"dataType" : "json",
			"async" : false,
			"success" : function(data) {
				id = data.id;
			}
		});
		return id
	}



function queryContactWayById(id){
	popup('联系方式详情',contactWayPath+'/queryContactWayById?id='+id,'650','400');
}