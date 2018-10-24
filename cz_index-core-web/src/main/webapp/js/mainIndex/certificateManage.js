var certificatePath = contextPath + "/certificate";
var certificateTable;
$(document).ready(function() {
	init(); // 初始化日志列表
	$("#certificateTypeCode").change(function() {
		var no=$("#certificateNo").val();
		$("#certificateNo").val('').blur().val(no).blur();
	});
	$("#u_certificateTypeCode").change(function() {
		var no=$("#u_certificateNo").val();
		$("#u_certificateNo").val('').blur().val(no).blur();
	});
});

/**
 * 初始化日志列表
 */
function init() {
	var certificateList = $("#personCertificateList");
	if (certificateList && certificateList.length > 0) {
		certificateTable = certificateList
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
			"aLengthMenu" : [ [ 5, 10, 15 ], [ 5, 10, 15] ],// 定义每页显示数据数量
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
					"sAjaxSource" : certificatePath + '/QueryPersonCertificate',
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
								"mData" : 'certificateNo',
								'sClass' : 'center',
								'sName' : "certificateNo",
								"render" : function(data, type, full) {
									return '<a class="particulars" onclick="queryCertificateById('
											+ full.id
											+ ')" href="javascript:;">'
											+ data
											+ '</a>';
								}
							},
							{
								"mData" : 'certificateTypeCode',
								'sClass' : 'center',
								'sName' : "certificateTypeCode",
								"render" : function(data, type, full) {
									return fn_getDictByKeyCode(
											"indexCertificateTypeCode", data);
								}
							}, {
								"mData" : 'createDate',
								'sClass' : 'center',
								'sName' : "createDate"
							}, {
								"mData" : 'updateDate',
								'sClass' : 'center',
								'sName' : "updateDate"
							} ],
					"fnDrawCallback" : function(settings) {
						checkedOfClickTr(".checkbox_id");
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						aoData.push({
							"name" : "filter_personId_EQ",
							"value" : $('#allPersonId').val().trim()
						}, {
							"name" : "filter_certificateNo_EQ",
							"value" : $('#s_certificateNo').val().trim()
						}, {
							"name" : "filter_certificateTypeCode_EQ",
							"value" : $('#s_certificateTypeCode').val().trim()
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

$("#search_btn").click(function() {
	certificateTable.fnDraw();
});
function deleteCertificate() {
	var checkedIds = getCheckedIds();
	if (!checkedIds || checkedIds.length == 0) {
		layer.alert("请选择一条证件信息");
		return false;
	}
	layer.confirm("确认删除该证件?", function() {
		$.ajax({
				"type" : 'post',
				"url" : certificatePath + "/deleteCertificateById?id="+ checkedIds,
				"dataType" : "json",
				"async" : false,
				"success" : function(data) {
					console.log(data)
					if (data.status == 0) {
						layer.alert('删除成功', {
							skin : 'layui-layer-lan',
							closeBtn : 0,
							shift : 4
						}, function(index) {
								// 刷新页面
							certificateTable.fnFilter();
							layer.closeAll();
						});
					} else {
						layer.alert(data.message);
					}
				}
			});
	});
}

/**
 * 添加证件信息
 */
function addCertificate() {
	$("#addCertificate form  input").each(function() {
		$(this).val('');
	});
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	var pid = $('#allPersonId').val()
	var mainIndex = $('#allMpiId').val()
	$("#addCertificate").find("#personId").val(pid);
	$("#addCertificate").find("#mpiId").val(mainIndex);
	popupForHtml('添加证件信息', $('#addCertificate'), '70%', '71%');
	var addCertificateForm = $("#addCertificateFrom");
	if (addCertificateForm && addCertificateForm.length > 0) {
		addCertificateForm.Validform({
			btnSubmit : "#add_form_submit",
			btnReset : "",
			tiptype : function(msg, o, cssctl) {
				validformTiptype(msg, o, cssctl)
			},
			ignoreHidden : false,
			dragonfly : false,
			tipSweep : false,
			label : ".label",
			showAllError : false,
			postonce : true,
			ajaxPost : true,
			datatype : addCertificateType,
			usePlugin : {
				swfupload : {},
				datepicker : {},
				passwordstrength : {},
				jqtransform : {
					selector : "select,input"
				}
			},
			beforeCheck : function(curform) {
			},
			beforeSubmit : function(curform) {
			},
			callback : function(data) {
				console.log(data)
				if (data.status == 0) {
					console.log(data)
					layer.alert("添加成功", {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						// 刷新页面
						certificateTable.fnDraw(true); // 刷新当前页面
						layer.closeAll();
						// 清空添加表单中输入框内容
						$("#addCertificateForm :input").val("");
					});
				} else {
					layer.alert(data.message);
				}
			}
		});
	}
}
/*******************************************************************************
 * 编辑证件信息
 */
function updateCertificate() {
	$("#updateCertificate form  input").each(function() {
		$(this).val('');
	});
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	var checkedIds = getCheckedIds();
	if (!checkedIds || checkedIds.length == 0) {
		layer.alert("请选择一条证件信息");
		return false;
	}
	setupdateCertificateForm();
	popupForHtml('编辑证件信息', $('#updateCertificate'), '70%', '71%');
	var updateCertificateForm = $("#updateCertificateFrom");
	if (updateCertificateForm && updateCertificateForm.length > 0) {
		updateCertificateForm.Validform({
			btnSubmit : "#update_form_submit",
			btnReset : "",
			tiptype : function(msg, o, cssctl) {
				validformTiptype(msg, o, cssctl)
			},
			ignoreHidden : false,
			dragonfly : false,
			tipSweep : false,
			label : ".label",
			showAllError : false,
			postonce : true,
			ajaxPost : true,
			datatype : updateCertificateType,
			usePlugin : {
				swfupload : {},
				datepicker : {},
				passwordstrength : {},
				jqtransform : {
					selector : "select,input"
				}
			},
			beforeCheck : function(curform) {
			},
			beforeSubmit : function(curform) {
			},
			callback : function(data) {
				console.log(data)
				if (data.status == 0) {
					console.log(data)
					layer.alert(data.message, {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						// 刷新页面
						certificateTable.fnDraw(true); // 刷新当前页面
						layer.closeAll();
						// 清空添加表单中输入框内容
						$("#updateCertificateForm :input").val("");
					});
				} else {
					layer.alert(data.message);
				}
			}
		});
	}
}

/**
 * 编辑证书信息为表单设置值
 */
function setupdateCertificateForm() {

	var updateCertificate = getCheckedObject(certificateTable);
	$("#updateCertificate").find("#u_certificateNo").val(
			updateCertificate.certificateNo);
	var mainIndex = $('#allMpiId').val()
	$("#updateCertificate").find("#mpiId").val(mainIndex);
	$("#updateCertificate").find("#u_certificateNo").val(
			updateCertificate.certificateNo);
	$("#updateCertificate").find("#id").val(updateCertificate.id);
	$("#updateCertificate").find("#u_certificateTypeCode").val(
			updateCertificate.certificateTypeCode);
	$("#updateCertificate").find("#u_LastModifyUnit").val(
			updateCertificate.lastModifyUnit);
	$("#updateCertificate").find("#u_createUnit").val(
			updateCertificate.createUnit);

}

var addCertificateType = {
	"certificateNo" : function(gets, obj, curform, regxp) {
		if (gets == null || gets.length <= 0) {
			return "请填写信息！"
		}
		var type = $("#certificateTypeCode").val();
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
		if (isExistNo(gets, type, "add") > 0) {
			return "该用户已存在相同类型证件号";
		}
		return true// 改为重复验证
	}
}

var updateCertificateType = {
	"certificateNo" : function(gets, obj, curform, regxp) {
		var type = $("#u_certificateTypeCode").val();
		if (type == null || type.length <= 0) {
			obj.attr("nullmsg", "请选择证件类型！")
			return "请选择证件类型！"
		}
		if (gets == null || gets.length <= 0) {
			obj.removeAttr("nullmsg");
			obj.attr("nullmsg", "请填写信息！")
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
		var id = $("#updateCertificate").find("#id").val();
		if (isExistNo(gets, type) != -1 && id != isExistNo(gets, type)) {
			return "已存在相同类型的相同证件号";
		}
		return true// 改为重复验证
	}
}

function isExistNo(no, type) {
	var id;
	$.ajax({
		"type" : 'post',
		"url" : certificatePath + "/isExistNo",
		"data" : {
			"no" : no,
			"type" : type
		},
		"dataType" : "json",
		"async" : false,
		"success" : function(data) {
			id = data.id;
		}
	});
	return id
}

var CertificateType = {
	"s2-10" : /^[\S+]{1,20}$/,// 2-10个字符
	"zh2-20" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/,
	"name" : /^[\u4e00-\u9fa5\\]{2,20}$/,// 输入2-20个中文字符允许小数点
	"exist_ID_datatype" : function(gets, obj, curform, regxp) {
		return queryResidentsInfoExistence(gets, obj, curform, regxp);
	} // 推广
};
function queryCertificateById(id) {
	popup('证件详情', certificatePath + '/queryCertificateById?id=' + id, '650',
			'400');
}