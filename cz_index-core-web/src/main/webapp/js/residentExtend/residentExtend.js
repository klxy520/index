var residentPath = contextPath + "/residentBase";
var residentExtendPath = contextPath + "/residentExtendinfo";
var fieldPath = contextPath + "/roleField";
var importResidentExtendinfoPath = contextPath + "/residentExtendinfoImport";
var Col;
var extendTable;
var LegalFalg = false;

/**
 * 区域机构树
 */
$(document).ready(function() {
	iniList(); // 初始化居民扩展信息列表
});

/**
 * 获取用户查看权限
 */
function getCol() {
	var hiddenIndex = [];
	var showIndex = [ 0, 1, 2 ];
	var fieldIndex = [ [], [ 0, 1, 2, 11, 12 ] ];
	$.ajax({
		"type" : "POST",
		"url" : fieldPath + '/getloginUserField',
		"dataType" : "json",
		"async" : false,
		"data" : null,
		"success" : function(data) {
			if (data.status == 0) {
				var isOrg = data.data.isOrg;
				var field = data.data.roleField;
				if (isOrg == false) {
					return fieldIndex
				} else
					for (var i = 0; i < field.length; i++) {
						if (field[i].id == null) {
							hiddenIndex.push(3 + i)
						} else {
							showIndex.push(11 + i)
						}
					}
				fieldIndex[0] = hiddenIndex;
				fieldIndex[1] = showIndex;
			} else {
				layer.alert(data.message);
			}
		}
	});
	var a = 1;
	return fieldIndex

}

function iniList() {
	Col = getCol();
	var hiddenCol = Col[0];
	var residentExtendList = $("#extendTable");
	if (residentExtendList && residentExtendList.length > 0) {
		extendTable = residentExtendList
				.dataTable({
					"oLanguage" : { // 语言国际化
						"sUrl" : contextPath + "/js/de_DE.txt"
					},
					"bPaginate" : true,
					"bFilter" : false,
					"bLengthChange" : true,
					"iDisplayLength" : 15,
					"bSort" : true,
					"aaSorting" : [ [ 5, "desc" ] ],
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [0,1,2,3,4]
					}, {
						"bVisible" : false,
						"aTargets" : hiddenCol
					} ],
					"aLengthMenu" : [ [ 15, 30, 50, 100 ], [ 15, 30, 50, 100 ] ],// 定义每页显示数据数量
					"bInfo" : true,
					"bWidth" : true,
					"bScrollCollapse" : true,
					"sPaginationType" : "full_numbers",
					"bProcessing" : true,
					"bServerSide" : true,
					"bDestroy" : true,
					"bSortCellsTop" : true,
					"bStateSave" : false,// true状态保持 刷新页面保持页码等数据,false:反之
					"asStripeClasses" : [ 'text-c odd', 'text-c even' ],
					"sAjaxSource" : residentExtendPath + '/list',
					"fnDrawCallback" : function(settings) {
						checkedOfClickTr(".checkbox_id");
					},
					"aoColumns" : [
							{
								"mData" : 'id',
								'sClass' : 'center',
								'sName' : "id",
								'mRender' : function(data, type, full) {
									return '<input type="checkbox" name="goodCheckbox" id="checkbox_id_'
											+ data
											+ '" class="checkbox_id" value="'
											+ data + '">'
								}
							},
							{
								"mData" : "realName",
								'sClass' : 'center',
								'sName' : "realName",
								'mRender' : function(data, type, full) {
									return '<a class="particulars"  onclick="getExtendDetail('
											+ full.id + ')">' + data + '</a>'

								}
							}, {
								"mData" : "idNumber",
								'sClass' : 'center',
								'sName' : "idNumber"
							},
							// {
							// "mData" : "healthNumber",
							// 'sClass' : 'center',
							// 'sName' : "healthNumber"
							// },
							// {
							// "mData" : "insuranceType",
							// 'sClass' : 'center',
							// 'sName' : "insuranceType"
							// },
							// {
							// "mData" : 'illnessType',
							// 'sClass' : 'center',
							// 'sName' : "illnessType"
							// },
							// {
							// "mData" : 'disabilityType',
							// 'sClass' : 'center',
							// 'sName' : "disabilityType"
							// },
							// {
							// "mData" : 'unionFeature',
							// 'sClass' : 'center',
							// 'sName' : "unionFeature"
							// },
							// {
							// "mData" : 'retiredCadres',
							// 'sClass' : 'center',
							// 'sName' : "retiredCadres"
							// },
							// {
							// "mData" : 'helpHouse',
							// 'sClass' : 'center',
							// 'sName' : "helpHouse"
							// },
							// {
							// "mData" : 'lowType',
							// 'sClass' : 'center',
							// 'sName' : "lowType"
							// },

							{
								"mData" : 'isCivilAffairs',
								'sClass' : 'center',
								'sName' : "isCivilAffairs"
							// ,"render": function (data,type,full) {return
							// fn_getDictByKeyCode("isCivilAffairs",data+'') }

							},

							{
								"mData" : 'isDisableFederation',
								'sClass' : 'center',
								'sName' : "isDisableFederation"
							// ,"render": function (data,type,full) {return
							// fn_getDictByKeyCode("isDisableFederation",data+'')
							// }
							}, {
								"mData" : 'createDate',
								'sClass' : 'center',
								'sName' : "createDate"
							}, {
								"mData" : 'updateDate',
								'sClass' : 'center',
								'sName' : "updateDate"
							} ],
					"fnServerData" : function(sSource, aoData, fnCallback) {
						aoData.push({
							"name" : "filter_realName_EQ",
							"value" : $('#searchs').find('#realName').val()
						}, {
							"name" : "filter_idNumber_EQ",
							"value" : $('#searchs').find('#idNumber').val().trim()
						},

						{
							"name" : "filter_isCivilAffairs_EQ",
							"value" : $('#searchs').find(
									'#isCivilAffairsSearch').val()
						}, {
							"name" : "filter_isDisableFederation_EQ",
							"value" : $('#searchs').find(
									'#isDisableFederationSeaech').val()
						},

						// { "name": "filter_healthNumber_EQ", "value":
						// $('#searchs').find('#healthNumber').val() },
						//						 
						// { "name": "filter_lowType_EQ", "value":
						// $('#searchs').find('#lowType').val()
						// },
						// { "name": "filter_insuranceType_EQ", "value":
						// $('#searchs').find('#insuranceType').val() },
						// { "name": "filter_illnessType_EQ", "value":
						// $('#searchs').find('#illnessType').val() },
						// { "name": "filter_disabilityType_EQ", "value":
						// $('#searchs').find('#disabilityType').val() },
						// { "name": "filter_unionFeature_EQ", "value":
						// $('#searchs').find('#unionFeature').val() },
						//						 
						// { "name": "filter_retiredCadres_EQ", "value":
						// $('#searchs').find('#retiredCadres').val() },
						// { "name": "filter_helpHouse_EQ", "value":
						// $('#searchs').find('#helpHouse').val() },
						{
							"name" : "filter_maxDate_EQ",
							"value" : $('#searchs').find('#date2').val()
						}, {
							"name" : "filter_minDate_EQ",
							"value" : $('#searchs').find('#date1').val()
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
							}
						});
					}
				});
	}
}

function getExtendDetailByid(id) {

	var residentextInfo;
	$.ajax({
		"type" : "post",
		"url" : residentExtendPath + '/getExtendDetailById',
		"dataType" : "json",
		"async" : false,
		"data" : {
			"id" : id
		},
		"success" : function(data) {
			if (data.status == 0) {
				residentextInfo = data.data;
			} else {
				layer.alert(data.message);
				return null;
			}

		}
	});
	return residentextInfo
}

function getExtendDetail(id) {
	Col = getCol();
	var showCol = Col[1];
	var residentextInfo = getExtendDetailByid(id);
	createDomForRead(residentextInfo, showCol)
	popupForHtml('居民健康卡扩展信息详情', $('#readResidentExtend'), '60%', '50%');
}
function replaceNull(str) {
	if (null == str) {
		return '';
	}
	return str
}

function replaceCode(key, code) {
	if (null == key || code == null) {
		return '';
	}
	return fn_getDictByKeyCode(key + '', code + '')
}

function createDomForRead(Info, showCol) {
	$('#extendInfo').empty();
	var html = '';
	var dom = '';
	var allField = [];

	allField[0] = "";// 居民id

	dom = '';// 清空
	dom = '<label class="form-label col-2">居民姓名：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.realName) + '</font>'
	dom += '</div>'
	allField[1] = dom;// 身份证号码

	dom = '';// 清空
	dom = '<label class="form-label col-2">身份证号：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>'+replaceNull(Info.idNumber)+'</font>'
	dom += '</div>'
	allField[2] = dom;// 身份证号码

	dom = '';// 清空
	dom = '<label class="form-label col-2">健康卡号：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.healthNumber) + '</font>'
	dom += '</div>'
	allField[3] = dom;// 居民健康卡号

	dom = '';// 清空
	dom = '<label class="form-label col-2">医保类型：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.insuranceType) + '</font>'
	dom += '</div>'
	allField[4] = dom;// 医保类型

	dom = '';// 清空
	dom = '<label class="form-label col-2">病种类型：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.illnessType) + '</font>'
	dom += '</div>'
	allField[5] = dom;// 医保病种类型

	dom = '';// 清空
	dom = '<label class="form-label col-2">残疾类型：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.disabilityType) + '</font>'
	dom += '</div>'
	allField[6] = dom;// 残疾类型

	dom = '';// 清空
	dom = '<label class="form-label col-2">工会特征：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.unionFeature) + '</font>'
	dom += '</div>'
	allField[7] = dom;// 工会特征

	dom = '';// 清空
	dom = '<label class="form-label col-2">离休干部：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.retiredCadres) + '</font>'
	dom += '</div>'
	allField[8] = dom;// 离休干部

	dom = '';// 清空
	dom = '<label class="form-label col-2">扶&nbsp;贫&nbsp;户	：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.helpHouse) + '</font>'
	dom += '</div>'
	allField[9] = dom;// 扶贫户

	dom = '';// 清空
	dom = '<label class="form-label col-2">低保类型	：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.lowType) + '</font>'
	dom += '</div>'
	allField[10] = dom;// 低保类型

	dom = '';// 清空
	dom = '<label class="form-label col-2">民政性质：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.isCivilAffairs) + '</font>'
	dom += '</div>'
	allField[11] = dom;// 民政性质

	dom = '';// 清空
	dom = '<label class="form-label col-2">残联性质：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + replaceNull(Info.isDisableFederation) + '</font>'
	dom += '</div>'
	allField[12] = dom;// 残联性质

	dom = '';// 清空
	dom = '<label class="form-label col-2">更新时间：</label>'
	dom += '<div class="formControls col-3">'
	dom += '<font>' + (Info.updateDate == null?"":Info.updateDate)
			+ '</font>'
	dom += '</div>'
	allField[13] = dom;// 更新时间

	for (var i = 0; i < showCol.length; i++) {
		if (i % 2 == 1) {
			html += '<div class="row cl">'
			html += allField[showCol[i]]
			if (i == showCol.length - 1) {
				html += '</div >'
			}
		} else {
			html += allField[showCol[i]]
			html += '</div >'
		}
	}
	html += '<div class="row cl">' + allField[13] + '</div >'
	$('#extendInfo').append(html)
}

function updateResidentExtend() {
	var extend = getResidentExtend()

	if (extend != false) {
		var extendBefore = getExtendDetailByid(extend.id)
		setExtendFormValue(extendBefore)// 为编辑表单设置值
		popupForHtml('编辑居民健康卡扩展信息', $('#addExtend'), '70%', '60%');
	}
	updateExtendInfo();
}

function getResidentExtend() {
	var checkedIds = getCheckedIds();
	if (!checkedIds || checkedIds.length == 0) {
		layer.alert("请选择一条居民信息");
		return false;
	} else if (checkedIds.length > 1) {
		layer.alert("只能选择一条居民信息");
		return false;
	}
	return getCheckedObject(extendTable);
}

/**
 * 居民扩展信息列表条件查询
 */
function search() {
	extendTable.fnFilter();
}

function setExtendFormValue(extendBefore) {
	$("#addExtendInfo .input-text").val("");
	var hiddenId = [];
	var isOrg = false;
	$.ajax({
		"type" : "POST",
		"url" : fieldPath + '/getloginUserField',
		"dataType" : "json",
		"async" : false,
		"data" : null,
		"success" : function(data) {
			if (data.status == 0) {
				var field = data.data.roleField;
				isOrg = data.data.isOrg;
				for (var i = 0; i < field.length; i++) {
					if (field[i].id == null) {
						hiddenId.push(field[i].fieldName)
					}
				}
			} else {
				layer.alert(data.message);
			}
		}
	});
	$("#addExtendInfo").find("#id").val(extendBefore.id);
	$("#addExtendInfo").find("#baseId").val(extendBefore.baseId);
	$("#addExtendInfo").find("#realName").val(extendBefore.realName);
	$("#addExtendInfo").find("#idNumber").val(extendBefore.idNumber);
	$("#addExtendInfo").find("#healthNumber").val(extendBefore.healthNumber);
	$("#addExtendInfo").find("#insuranceType").val(extendBefore.insuranceType);
	$("#addExtendInfo").find("#illnessType").val(extendBefore.illnessType);
	$("#addExtendInfo").find("#disabilityType")
			.val(extendBefore.disabilityType);
	$("#addExtendInfo").find("#unionFeature").val(extendBefore.unionFeature);
	$("#addExtendInfo").find("#retiredCadres").val(extendBefore.retiredCadres);
	$("#addExtendInfo").find("#helpHouse").val(extendBefore.helpHouse);
	$("#addExtendInfo").find("#lowType").val(extendBefore.lowType);

	$("#addExtendInfo").find("#isCivilAffairs")
			.val(extendBefore.isCivilAffairs);
	$("#addExtendInfo").find("#isDisableFederation").val(
			extendBefore.isDisableFederation);
	$("#addExtendInfo").find("#updateDate").val(extendBefore.updateDate);
	if (isOrg == true) {// 当行政机构，设值编辑权限
		for (var i = 0; i < hiddenId.length; i++) {
			var id = "#" + hiddenId[i]
			var id2 = "#" + hiddenId[i] + "2"
			$("#addExtendInfo").find(id).hide();
			$("#addExtendInfo").find(id2).show()
			$("#addExtendInfo").find(id2).val("无权编辑");
		}
	}

}

function updateExtendInfo() {
	var extendForm = $("#addExtendInfo");
	if (extendForm && extendForm.length > 0) {
		extendForm.Validform({
			btnSubmit : "#form_submit",
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
			datatype : customDataType,
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
				if (data.status == 0) {
					layer.alert(data.message, {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						extendTable.fnDraw(true); // 刷新当前页面
						layer.closeAll();
						$("#addExtendInfo :input").val("");
					});
				} else {
					layer.alert(data.message);
				}
			}
		});
	}
}
/**
 * [导入扩展信息]解析扩展信息
 */
function extendImport() {
	popupForHtml('居民扩展信息导入', $('#importResident'), '40%', '50%');
	$('#importResident_a').attr('href',
			contextPath + '/template/扩展信息模板.xlsx');
	$('#importResident_a').text('扩展信息模板.xlsx');
	loadWebUploaderExcel();
	$('#batchImport_submit').unbind("click");
	$("#batchImport_submit").click(
			function() {
				var ie8 = isIe8();
				if (!ie8) {
					loadAlert("importResident", "正在解析文件");
				}
				setTimeout(function() {
					var upFileExcelPath = $("#upFileExcelPath").val();
					if (upFileExcelPath == null || upFileExcelPath == "") {
						layer.alert("请上传文件");
						if (!ie8) {
							load.loadClose();
						}
						return;
					}
					LegalFalg = judgmentResidentBaseinfoLegal(ie8,
							upFileExcelPath);
					if (LegalFalg) {
						if (!ie8) {
							load.loadClose();
						}
						popup('扩展信息导入', importResidentExtendinfoPath
								+ "/listPage?excelUrl=" + upFileExcelPath,
								'1000', '550');
					}
				}, 300);
			});
}
/**
 * [导入扩展信息]将扩展信息批量插入数据库
 */
function batchAddResidentBaseinfo() {
	if (falg) {
		$.ajax({
			"type" : 'post',
			"url" : importResidentExtendinfoPath
					+ "/batchAddResidentExtendinfo",
			"dataType" : "json",
			"async" : false,
			"success" : function(data) {
				if (data.status == 0) {
					layer.alert(data.message, {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						parent.extendTable.fnFilter();
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
/*******************************************************************************
 * 判断Excel文件数据是否合法
 * 
 * @param excelUrl
 */
function judgmentResidentBaseinfoLegal(ie8, excelUrl) {
	var LegalFalg = true;
	$
			.ajax({
				"type" : 'post',
				"url" : importResidentExtendinfoPath
						+ "/judgmentResidentBaseinfoLegal",
				"dataType" : "json",
				"data" : {
					"excelUrl" : excelUrl
				},
				"async" : false,
				"success" : function(data) {
					if (data.status == 1) {
						LegalFalg = false;
						layer.alert(data.message)
						if (!ie8) {
							load.loadClose();
						}
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
