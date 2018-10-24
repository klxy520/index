var path = contextPath + "/cardSyncLog";
/**
 * 初始化联系人列表
 */
var tableList;
$(document).ready(function() {

	iniList();
});
function iniList() {
	tableList = $("#tableList");
	if (tableList && tableList.length > 0) {
		tableList = tableList
				.dataTable({
					"oLanguage" : { // 语言国际化
						"sUrl" : contextPath + "/js/de_DE.txt"
					},
					"bPaginate" : true,
					"bFilter" : false,
					"bLengthChange" : true,
					"iDisplayLength" : 15,
					"bSort" : true,
					"aaSorting" : [ [ 9, "desc" ] ],
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [0,1,2,3,4,5,6,7,10]
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
					"sAjaxSource" : path + '/list',
					"aoColumns" : [
							{
								"mData" : 'id',
								'sClass' : 'center',
								'sName' : "id",
								'mRender' : function(data, type, full) {
									var content=""
										if(full.femState=="0"&&full.syncStatus=="0"){
											content='<input type="checkbox"  id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'
										}
										return content;
								}
							},
							{
							     "mData": 'personName',
							     'sClass': 'center',
							     'sName':"personName",
							    "render": function (data,type,full) {
									       return '<a class="particulars" onclick="queryPersonDetails('+full.personId+')" href="javascript:;">'+data+'</a>';
							    } 
							 },
							{
								"mData" : 'cardNo',
								'sClass' : 'center',
								'sName' : "cardNo",
								"render" : function(data, type, full) {
									return '<a class="particulars" style="word-break: break-all;" onclick="queryCardById('
											+ full.cardId
											+ ')" href="javascript:;">'
											+ data
											+ '</a>';
								}
							}, 
							{
								"mData" : "type",
								'sClass' : 'center',
								'sName' : "type",
								 "render": function (data,type,full) {
									   return fn_getDictByKeyCode("cardTypeCode",data) ;
								  } 
							}, 
							{
								"mData" : 'femCode',
								'sClass' : 'center',
								'sName' : "femCode"
							},
							{
								"mData" : 'femAddress',
								'sClass' : 'center',
								'sName' : "femAddress"

							},
							{
								"mData" : 'femState',
								'sClass' : 'center',
								'sName' : "femState",
								'mRender':function(data, type, full){
									
									switch (data) {
									case 0:
										return "启用";
									case 1:
										return "禁用";
									case 3:
										return "异常";
									default:
										return ""
									} 							    
						        }
							}, 
							{
								"mData" : "syncStatus",
								'sClass' : 'center',
								'sName' : "syncStatus",
								"render" : function(data, type, full) {
									switch (data) {
									case "0":
										return "未同步"
									case "1":
										return "已同步"
									default:
										return ""
									}
								}
							},
						

							{
								"mData" : "createDate",
								'sClass' : 'center',
								'sName' : "createDate"
							},
							{
								"mData" : "updateDate",
								'sClass' : 'center',
								'sName' : "updateDate"
							} ,
							{
								"mData" : "remark",
								'sClass' : 'center',
								'sName' : "remark"
							}],
					"fnDrawCallback" : function(settings) {
						$(".checkbox_id").click(function(){
							if($(this).is(':checked')){
								$(this).prop('checked',false);
							}else{
								$(this).prop('checked',true);
							};
						})
						$(".checkbox_id").closest('tr').click(function(){
							//$(".checkbox_id").prop('checked',false);
							if($(this).find(".checkbox_id").is(':checked')){
								$(this).find(".checkbox_id").prop('checked',false);
							}else{
								$(this).find(".checkbox_id").prop('checked',true);
							};
						})
						
					},
					"fnServerData" : function(sSource, aoData, fnCallback) {
						aoData.push(
								{
									"name" : "filter_type_EQ",
									"value" : $('#s_TypeCode').val()
								},{
									"name" : "filter_personName_EQ",
									"value" : $('#s_name').val()
								}, 
								{
									"name" : "filter_cardNo_EQ",
									"value" : $('#s_no').val()
								}, {
									"name" : "filter_frontId_EQ",
									"value" : $('#s_femId').val()
								}, {
									"name" : "filter_syncStatus_EQ",
									"value" : $('#s_status').val()
								},{ 
									"name": "filter_maxUpateDate_EQ",
									"value": $('#maxUpateDate').val()
								},{
									"name": "filter_minUpateDate_EQ",
									"value": $("#minUpateDate").val()
								}
						), $.ajax({
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
/**
 * 联系人列表条件查询
 */

$("#search_btns").click(function() {
	tableList.fnFilter();
});

/*******************************************************************************
 * 获取单个复选框的联系人
 */

function changeSyncStatus(){ 
	var ids=getCheckedIds();
	if(!ids||ids.length<1){
		layer.alert("至少选择一条卡务信息日志");
		return false;
	}
	layer.confirm("确认同步卡务信息?",function(){
			$.ajax({
				"type" : 'post',
				"url" : path + "/updateSyncStatus",
				"dataType" : "json",
				"data":{"ids":JSON.stringify(ids)},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							tableList.fnFilter();
							layer.closeAll();
						});
					}else{
						layer.alert(data.message)
					}
				}
			});
		});
}
function queryPersonDetails(id){
	var personListPath = contextPath + "/person";
	popup('基本身份信息详情',personListPath+'/queryPersonDetails?id='+id,'880','550');
}
function queryCardById(id){
	var cardPath = contextPath + "/card";
	popup('卡务详情',cardPath+'/queryCardById?id='+id,'650','400');
}