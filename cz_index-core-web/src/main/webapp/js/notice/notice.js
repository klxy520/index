var rolePath = contextPath + "/role";
var noticePath = contextPath + "/notice";
var menuPath = contextPath + "/menu";
var orgTree;
var areaTree;
var noticeListTable;

$(document).ready(function() {
	init();
});

function init() {
	// 初始化dataTable
	noticeListTable = $("#NoticeList");
	if (noticeListTable && noticeListTable.length > 0) {
		noticeListDataTable = noticeListTable
				.dataTable({
					"oLanguage" : { // 语言国际化
						"sUrl" : contextPath + "/js/de_DE.txt"
					},
					"bPaginate" : true,
					"bFilter" : false,
					"bLengthChange" : true,
					"iDisplayLength" : 15,
					"bSort" : true,
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0, 2, 5,  6 ]
					} ],
					"aLengthMenu" : [ [ 15, 30, 50, 100 ], [ 15, 30, 50, 100 ] ],// 定义每页显示数据数量
					"bInfo" : true,
					"bWidth" : true,
					"bScrollCollapse" : true,
					"sPaginationType" : "full_numbers",
					"bProcessing" : true,
					"bServerSide" : true,
					"aaSorting" : [ [ 1, "desc" ] ],
					"bDestroy" : true,
					"bSortCellsTop" : true,
					"bStateSave" : true,// 状态保持 刷新页面保持页码等数据
					"asStripeClasses" : [ 'text-c odd', 'text-c even' ],
					"aoSearchCols" : [ null, {
						"sSearch" : "name"
					} ],
					"sAjaxSource" : noticePath + '/noticeList',
					"aoColumns" : [
							{
								"mData" : "id",
								'sClass' : 'center',
								'sName' : "id",
								'mRender' : function(data, type, full) {
									return '<input type="checkbox" id="checkbox_id_'
											+ data
											+ '" class="checkbox_id" value="'
											+ data + '">'
								}
							},
							{
								"mData" : "id",
								'sClass' : 'center',
								'sName' : "id",
								'mRender' : function(data, type, full) {
									var title = full.title
									if (full.title.length > 20)
										title = title.substring(0, 15) + "..."
									return '<a  class="particulars" id="title_id_'
											+ full.id
											+ '"  onclick="getNoticeDetailById('
											+ full.id + ')">' + title + '</a>'
								}

							},

							{
								"mData" : "content",
								'sClass' : 'center',
								'sName' : "content",
								'mRender' : function(data, type, full) {
									var content = full.content;
									if (content.length > 20) {
										return content.substring(0, 15)
												+ '.....'
												+ content.substring(content.length - 5);
									}
									return full.content;
								}
							},
							{
								"mData" : "startTime",
								'sClass' : 'center',
								'sName' : "startTime",
								'mRender' : function(data, type, full) {
									return timetostr(full.startTime) + "至" + timetostr(full.endTime)
								}
							},
							{
								"mData" : "createDate",
								'sClass' : 'center',
								'sName' : "createDate"
							},
							{
								"mData" : "creator",
								'sClass' : 'center',
								'sName' : "creator",
								'mRender' : function(data, type, full) {
									if (data != null && data > 0) {
										return full.creatorUser.name;
									} else {
										return "";
									}
								}
							},
							 {
								"mData" : "status",
								'sClass' : 'center',
								'sName' : "status",
								'mRender' : function(data, type, full) {
									if (data == '1') {
										return '启用';
									} else {
										return '禁用';
									}
								}
							} ],
					"fnDrawCallback" : function(settings) {
						checkedOfClickTr(".checkbox_id");
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						// 条件查询
						aoData.push({
							"name" : "filter_title_LIKE",
							"value" : $('#title').val()
						},// 名称
						{
							"name" : "filter_creatorName_LIKE",
							"value" : $('#creatorName').val()
						},// 标识
						{
							"name" : "filter_status_EQ",
							"value" : $('#status').val()
						}, {
							"name" : "filter_updatorName_LIKE",
							"value" : $('#updatorName').val()
						},// 名称
						{
							"name" : "filter_createDate1_GEQ",
							"value" : $('#createDate1').val()
						}, {
							"name" : "filter_createDate2_LEQ",
							"value" : $('#createDate2').val()
						}, {
							"name" : "filter_useTime1_GEQ",
							"value" : $('#useTime1').val()
						},// 标识
						{
							"name" : "filter_useTime2_LEQ",
							"value" : $('#useTime2').val()
						}, {
							"name" : "filter_id_EQ",
							"value" : $('.formControls #id').val()
						}

						),// 状态
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

	// 初始化搜索按钮
	$("#search_btn").click(function() {
		noticeListTable.fnFilter();
	});
}

/**
 * 添加公告
 */
function addNotice() {
	// 清空添加表单中输入框内容
	$("#addNoticeForm :input").val("");
	popupForHtml('添加公告', $('#addNotice'), '70%', '60%');
	var addNoticeForm = $("#addNoticeForm");
	if (addNoticeForm && addNoticeForm.length > 0) {
		addNoticeForm.Validform({
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
				var status = data.responseData.status;
				if (status == 0) {
					layer.alert('添加公告成功', {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						// 刷新页面		
						layer.closeAll();
						noticeListTable.fnDraw(true);
						$("#addNoticeForm :input").val("");
					});
				} else {
					layer.alert(data.message);
				}
			}
		});
	}
}

/**
 * 查看公告详情
 */
function getNoticeDetailById(id) {
	if (null != id)
		popup('公告详情', noticePath + '/queryNoticeDetails?id=' + id,750,450);
}

/**
 * 删除公告
 */
function deleteNotice() {
	var ids = "";
	var titles = "";
	var checkedNotices = getAllCheckedObject(noticeListTable);
	if (!checkedNotices || checkedNotices.length < 1) {
		layer.alert("请选择一条公告！")
		return;
	}

	for (var i = 0; i < checkedNotices.length; i++) {
		var notice = checkedNotices[i];

		ids += notice.id;
		titles += notice.title;
		if (i != checkedNotices.length - 1) {
			ids += ",";
			titles += ",";
		}
	}

	layer.confirm("公告删除不可恢复，是否删除？", function() {
		$.ajax({
			url : noticePath + '/deleteNotice',
			data : {
				"ids" : ids,
				"titles" : titles
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				layer.alert('公告删除成功', {
					skin : 'layui-layer-lan',
					closeBtn : 0,
					shift : 4
				}, function(index) {
					layer.closeAll();
					noticeListTable.fnDraw(true); // 刷新当前页面
				});
			},
			error : function() {
				layer.alert("提交失败，请稍候重试！");
			}
		});
	});
}
/**
 * 修改公告
 */
function updateNotice() {
	var checkedNotices = getAllCheckedObject(noticeListTable);
	if (!checkedNotices || checkedNotices.length < 1) {
		layer.alert("请选择一条公告！")
		return;
	}
	// 获取公告
	var notice = getNotice(checkedNotices[0].id);
	setNoticeForUpdate(notice);
	popupForHtml('编辑公告', $('#updateNotice'), '70%', '70%');
	var updateNoticeForm = $("#updateNoticeForm");
	if (updateNoticeForm && updateNoticeForm.length > 0) {
		updateNoticeForm.Validform({
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
					layer.alert('修改公告成功', {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						// 刷新页面
						noticeListTable.fnDraw(true);
						layer.closeAll();
					});
				} else {
					layer.alert(data.message);
				}
			}
		});
	}
}
/**
 * 通过id获取公告
 * @param id
 * @returns
 */
function getNotice(id) {
	var notice = null;
	$.ajax({
		"type" : 'post',
		"url" : noticePath + "/getMyNoticeById",
		"dataType" : "json",
		"async" : false,
		"data" : {
			'id' : id
		},
		"success" : function(data) {
			if (data.status == 0) {
				notice = data.data;
			} else {
				layer.alert(data.message);
			}
		}
	});
	return notice;
}
/**
 * 修改公告时填充
 */
function setNoticeForUpdate(notice) {
	$("#updateNotice :input").val("");
	$("#updateNotice").find("#id").val(notice.id);
	$("#updateNotice").find("#title").val(notice.title);
	$("#updateNotice").find("#content").val(notice.content);
	$("#updateNotice").find("#status").val(notice.status);
	$("#updateNotice").find("#startTime").val(timetostr(notice.startTime));
	$("#updateNotice").find("#endTime").val(timetostr(notice.endTime));
	textarealength($("#updateNotice").find("#content"), 200);
}
/**
 * 设置权限弹出框,初始化数据
 */
function setUserAuthLayer() {
	var checkedNotices = getAllCheckedObject(noticeListTable);
	if (!checkedNotices || checkedNotices.length < 1) {
		layer.alert("请选择一条公告！")
		return;
	}
	popupForHtml('公告授权', $('#setUserAuth'), '55%', '70%');
	var indexOrg = layer.load(0, {
		shade : [ 0.2, '#666' ],
		zIndex :10000
		
	// 0.1透明度的白色背景
	});
	$('#setUserAuth').find('#noticeTitle').val(checkedNotices[0].title)
	$('#setUserAuth').find('#noticeId').val(checkedNotices[0].id)
	initOrg()
	initNoticeUser(checkedNotices[0].id)
	layer.close(indexOrg);
	
}

/**
 * 初始化菜单树
 * 
 * @param zNodes
 * 
 * 
 */

var setting = {
	check : {
		enable : true, // 是否显示 checkbox / radio
	},
	view : {
		dblClickExpand : false,
		showLine : true,
		selectedMulti : false
	},
	data : {
		key : {
			checked : "checked",
			children : "childNode",
			name : "name",
			url : "url"
		}
	},
	callback : {
		beforeClick : function(treeId, treeNode) {
		}
	}
};

function initTree(areaNode, orgNode) {
	var areaUser = $("#areaUser");
	var orgUser = $("#orgUser");
	orgTree  = $.fn.zTree.init(orgUser, setting,orgNode  );
	areaTree = $.fn.zTree.init(areaUser, setting,areaNode  );
	//orgTree.selectNode(orgTree.getNodes()[0]);
	var img=contextPath +"/images/orgPng/level0.png"
	$("#areaUser_1_ico").css('background','url('+img+')center center/16px 160x no-repeat')
	var areaNodes= areaTree.getNodesByParam("isNode", 1, null);
	for (var i=0;i<areaNodes.length;i++){
	
		
		
		if(areaNodes[i].childId==-1){
			areaNodes[i].icon=contextPath +"/images/orgPng/user.png"
		}else if(areaNodes[i].level==0){

			areaNodes[i].icon=contextPath +"/images/orgPng/level0.png"

			
		}else if(areaNodes[i].level==1){
			areaNodes[i].icon=contextPath +"/images/orgPng/level1.png"
			
		}else if(areaNodes[i].level==2){
			areaNodes[i].icon=contextPath +"/images/orgPng/level2.png"
			
		}else if(areaNodes[i].level==3){
			areaNodes[i].icon=contextPath +"/images/orgPng/level3.png"
			
		}else{
			areaNodes[i].icon=contextPath +"/images/orgPng/default.png"
		}
		
	}
	orgTree.expandAll(false);
}

function initOrg() {
	$.ajax({
		"type" : "POST",
		"url" : noticePath + "/getAllUserForTree",
		"dataType" : "json",
		"async" : false,
		"data" : null,
		"success" : function(data) {
			if (data.status == 0) {
				var areaNodes = data.data.areaNodes
				var orgNode = data.data.orgNodes
				initTree(areaNodes, orgNode);
			} else {

				layer.alert(data.message);
			}
		}
	});
}
/**
 *  将该公告所有有权限查看的用户进行勾选
 * @param id 公告id
 */
function initNoticeUser(id) {
	$.ajax({
				"type" : "POST",
				"url" : noticePath + "/getNoticeUserById",
				"dataType" : "json",
				"async" : false,
				"data" : {
					"id" : id
				},
				"success" : function(data) {
					if (data.status == 0) {
						var noticeUsers = data.data;

						for (var i = 0; i < noticeUsers.length; i++) {
							// 选中已赋权限菜单
							var orgNode = orgTree.getNodesByParam("id",
									noticeUsers[i], null);
							var areaNode = areaTree.getNodesByParam("id",
									noticeUsers[i], null);

							if (orgNode && orgNode[0]) {
								for (var j = 0; j < orgNode.length; j++) {
									if (orgNode[j].childId == -1)
										for (var j = 0; j < areaNode.length; j++) {
											orgTree.checkNode(orgNode[j], true,
													false);
											continue;
										}
								}
								if (areaNode && areaNode[0]) {
									for (var j = 0; j < areaNode.length; j++) {
										if (areaNode[j].childId == -1)
											areaTree.checkNode(areaNode[j],
													true, false);
										continue;
									}
								}
							}
						}
					} else {
						layer.alert(data.message);
					}
				}
			});
}

/************************************************************************** */

function setNoticeUser() {
	var noticeId = $("#setUserAuth").find("#noticeId").val();
	var uids = "";
	var orgNodes = orgTree.getCheckedNodes(true);//获取所有选择的节点
	var areaNodes = areaTree.getCheckedNodes(true);//获取所有选择的节点
	for (var i = 0; i < orgNodes.length; i++) {
		if (orgNodes[i].childId == "-1") {
			uids += orgNodes[i].id + ",";
		}
	}
	for (var i = 0; i < areaNodes.length; i++) {
		if (areaNodes[i].childId == "-1") {
			uids += areaNodes[i].id + ",";
		}
	}
	$.ajax({
		"type" : 'post',
		"url" : noticePath + "/setNoticeUser",
		"dataType" : "json",
		"async" : false,
		"data" : {
			"noticeId" : noticeId,
			"uids" : uids
		},
		"success" : function(data) {
			if (data.status == 0) {
				layer.alert('设置成功', {
					skin : 'layui-layer-lan',
					closeBtn : 0,
					shift : 4
				}, function(index) {
					// 刷新页面
					layer.closeAll();
				});
			} else {
				layer.alert(data.message);
			}
		}
	});
}
function timetostr(date){  
    var str="";  
    var time=new Date(date.replace(/-/g,"/"));
    str=time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();  
    return str;  
}