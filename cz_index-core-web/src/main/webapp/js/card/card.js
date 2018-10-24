var cardPath = contextPath + "/card";
var cardTable;
$(document).ready(function() {
	init(); // 初始化日志列表
	$("#addcardTypeCode").change(function(){
		var no=$("#addCardNo").val();
		$("#addCardNo").val('').blur().val(no).blur();
	});
	$("#cardTypeCode").change(function() {
		var no=$("#cardNo").val();
		$("#cardNo").val('').blur().val(no).blur();
	});
});

/**
 * 初始化日志列表
 */
function init() {
	var cardList = $("#cardList");
	if (cardList && cardList.length > 0) {
		cardTable = cardList
		.dataTable({
			"oLanguage" : { // 语言国际化
				"sUrl" : contextPath + "/js/de_DE.txt"
			},
			"bPaginate" : true,
			"bFilter" : false,
			"bLengthChange" : true,
			"iDisplayLength" : 15,
			"bSort" : true,
			"aaSorting" : [ [ 8, "desc" ] ],
			"aoColumnDefs" : [ {
				"bSortable" : false,
				"aTargets" : [0,1,2,3,4,5,6,7]
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
					"sAjaxSource" : cardPath + '/QueryCardByParams',
					"aoColumns" : [
							{
								"mData" : 'personId',
								'sClass' : 'center',
								'sName' : "personId",
								'mRender' : function(data, type, full) {
									return '<input  type="checkbox" id="checkbox_id_'
											+ data
											+ '" class="checkbox_id" value="'
											+ data + '">'
								}
							},
							{
								"mData" : 'personId',
								'sClass' : 'center',
								'sName' : "personId",
								"render" : function(data, type, full) {
									return '<a class="particulars" onclick="queryPersonDetails('
											+ data
											+ ')" href="javascript:;">'
											+ full.personName + '</a>';
								}
							},
							{
								"mData" : "idCard",
								'sClass' : 'center',
								'sName' : "idCard",
							}, 
							{
								"mData" : "cardNo",
								'sClass' : 'center',
								'sName' : "cardNo",
								"render" : function(data, type, full) {
									 if(data==null||data.length<=0){
										 return "-"
									 }
									return '<a class="particulars" onclick="queryCardById('
											+ full.cardId
											+ ')" href="javascript:;">'
											+data+ '</a>';
								}
							},
							
							{
								"mData" : "cardCode",
								'sClass' : 'center',
								'sName' : "cardCode",
								 "render": function (data,type,full) {
									 if(data==null){
										 return "-"
									 }
									 return data
								  } 
							}, 
							{
								"mData" : "cardTypeCode",
								'sClass' : 'center',
								'sName' : "cardTypeCode",
								 "render": function (data,type,full) {
									 if(data==null){
										 return "-"
									 }
									   return fn_getDictByKeyCode("cardTypeCode",data+'') ;
								  } 
							}, 
							{
								"mData" : "status",
								'sClass' : 'center',
								'sName' : "status",
								 "render": function (data,type,full) {
									 if(data==null){
										 return "-"
									 }
									   return fn_getDictByKeyCode("cardStatus",data+'') ;
								  } 
							}, 			
						      {
									"mData" : 'validTime',
									'sClass' : 'center',
									'sName' : "validTime",
									 "render": function (data,type,full) {
										 if(data==null){
											 return "-"
										 }
										 return data.substring(0,10)
									  } 
									
								},		
								{
									"mData" : 'createDate',
									'sClass' : 'center',
									'sName' : "createDate",
									 "render": function (data,type,full) {
										 if(data==null){
											 return "-"
										 }
										 return data
									  } 
									
								},
								{
									"mData" : 'updateDate',
									'sClass' : 'center',
									'sName' : "updateDate",
									 "render": function (data,type,full) {
										 if(data==null){
											 return "-"
										 }
										 return data
									  } 
									
								}
					],
					"fnDrawCallback" : function(settings) {
						checkedOfClickTr(".checkbox_id");
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						aoData.push(
							{
								"name" : "filter_personName_EQ",
								"value" : $('#s_personName').val().trim()
							},
							{
								"name" : "filter_idCard_EQ",
								"value" : $('#s_id_card').val().trim()
							},
							{
								"name" : "filter_cardTypeCode_EQ",
								"value" : $('#s_card_type_code').val().trim()
							},
							{
								"name" : "filter_status_EQ",
								"value" : $('#s_status').val().trim()
							},
							{
								"name" : "filter_cardNo_EQ",
								"value" : $('#s_card_no').val().trim()
							},
							{
								"name" : "filter_cardCode_EQ",
								"value" : $('#s_card_code').val().trim()
							},						
							
							{
								"name" : "filter_validTime1_EQ",
								"value" : $('#s_valid_time1').val().trim()
							},
							{
								"name" : "filter_validTime2_EQ",
								"value" : $('#s_valid_time2').val().trim()
							},							
							{
								"name" : "filter_updateDate1_EQ",
								"value" : $('#s_update_date1').val().trim()
							},
							{
								"name" : "filter_updateDate2_EQ",
								"value" : $('#s_update_date2').val().trim()
							}
						)
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


/**
 * 搜索
 */
$("#search_btn").click(function() {
	cardTable.fnFilter();
});
function replaceNull(str){
	if (str==null){
		return "-"
	}else{
		return str
	}
}

function queryCardById(id){
	popup('卡务详情',cardPath+'/queryCardById?id='+id,'650','500');
}


/**
 * 添加卡务信息
 */
function addCard(){	
	$("#addCard form  input").each(function() {
		$(this).val('');
	});  
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	var data=getCheckedObject(cardTable)
	if(data==null){
		layer.alert("请选择一位居民");
		return false;
	}
	$("#addCard").find("#personId").val(data.personId);
	$("#addCard").find("#mpiId").val(data.mpiId);
	popupForHtml('添加卡务信息',$('#addCard'),'62%','55%');
	var addCardForm = $("#addCardFrom");
	if(addCardForm && addCardForm.length>0){
		addCardForm.Validform({
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
			datatype:addCardType,
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
						cardTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addCardForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/***
 * 编辑卡务信息
 */
function updateCard(){	
	$("#updateCard form  input").each(function() {
		$(this).val('');
	});  
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	var data=getCheckedObject(cardTable)
	if(data==null){
		layer.alert("请选择一条卡信息");
		return false;
	}
	setupdateCardForm();
	popupForHtml('编辑卡务信息',$('#updateCard'),'62%','55%');
	var updateCardForm = $("#updateCardFrom");
	if(updateCardForm && updateCardForm.length>0){
		updateCardForm.Validform({
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
			datatype:updateCardType,
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
						cardTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#updateCardForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}


/**
 * 编辑卡信息初始化数据
 */
function setupdateCardForm(){
	var updateCard =getCheckedObject(cardTable);
	$("#updateCard").find("#mpiId").val(updateCard.mpiId);
	$("#updateCard").find("#id").val(updateCard.cardId);
	$("#updateCard").find("#cardNo").val(updateCard.cardNo);
	$("#updateCard").find("#cardCode").val(updateCard.cardCode);
	$("#updateCard").find("#createUnit").val(updateCard.createUnit);
	if(updateCard.validTime){
		$("#updateCard").find("#validTime").val(updateCard.validTime.substring(0,10));
	}
	$("#updateCard").find("#status").val(updateCard.status);
	$("#updateCard").find("#cardTypeCode").val(updateCard.cardTypeCode);
	$("#updateCard").find("#u_LastModifyUnit").val(updateCard.lastModifyUnit);
}

var addCardType = {
	"d5_15" : /^[a-zA-Z0-9]{5,32}$/,// 2-10个字符
	"cardNo" : function(gets, obj, curform, regxp) {
		var type = $("#addcardTypeCode").val();
		var reg = /^[a-zA-Z0-9]{5,32}$/; // 验证身份证
		if (!reg.test(gets)) {
			return "卡号格式错误";
		}
		if (isExistNo(gets, type) > 0) {
			return "已存在相同卡"
		}

		return true// 改为重复验证
	}

};
var updateCardType={
		"d5_15" : /^[a-zA-Z0-9]{5,32}$/,// 2-10个字符
		"cardNo" : function(gets, obj, curform, regxp) {
			var type = $("#cardTypeCode").val();
			var id = $("#id").val();
			var reg = /^[a-zA-Z0-9]{5,32}$/; // 验证身份证
			if (!reg.test(gets)) {
				return "卡号格式错误";
			}
			if (isExistNo(gets, type)> 0&& id!=isExistNo(gets, type)) {
				return "已存在相同卡"
			}
			return true// 改为重复验证
		}

};

function deleteCard(){
	var updateCard =getCheckedObject(cardTable);
	if(!updateCard ){
		layer.alert("请选择一条卡信息");
		return false;
	}
	if(!updateCard.cardId ){
		layer.alert("该居民无卡可删");
		return false;
	}
	var checkedIds=updateCard.cardId;
	layer.confirm("确认删除该卡?",function(){
		$.ajax({
	        "type": 'post',
	        "url" : cardPath + "/deleteCardById?id="+checkedIds,
	        "dataType": "json",
	        "async" : false,
	        "success": function(data) {
	        	console.log(data)
	        	if(data.status==0){
					layer.alert('删除成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						cardTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	});
}

/**
 * 验证重复
 * @param no
 * @param type
 * @returns
 */
function isExistNo(no,type) {
	var id;
	$.ajax({
		"type" : 'post',
		"url" : cardPath + "/isExistNo",
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


