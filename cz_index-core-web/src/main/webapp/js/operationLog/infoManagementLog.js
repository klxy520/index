var operationLogPath = contextPath + "/infoManagementLog";
var operationLogTable;
$(document).ready(function(){
	init();  //初始化日志列表
});

/**
 * 初始化日志列表
 */
function init(){
	var operationLogList=$("#LogTableList");
	if(operationLogList && operationLogList.length>0){
		
		operationLogTable = operationLogList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":10,
		    "bSort": true,
		    "aaSorting" : [ [ 6, "desc" ] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0, 1, 5]}],
		    "aLengthMenu": [[10,30,50,100], [10,30,50,100]],// 定义每页显示数据数量
		    "bInfo": true,
		    "bWidth": true,
		    "bScrollCollapse": true,
		    "sPaginationType": "full_numbers",
		    "bProcessing": true,
		    "bServerSide": true,
		    "bDestroy": true,
		    "bSortCellsTop": true,
		    "bStateSave":true,// 状态保持 刷新页面保持页码等数据
		    "asStripeClasses":['text-c odd','text-c even'],
		    "sAjaxSource": operationLogPath+'/QueryLogByParams',
		    "aoColumns": [
		     {
		        "mData": 'id',
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){ 
		        	return '<input  type="checkbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'
		        }
		     },
		     {
				 "render": function (filed, type, full, meta) {
				        return '<a class="particulars" onclick="fn_showLogDetail('+full.id+')" href="javascript:;">详情</a>';
				    } 
			 },
		     {
			    "mData": "creatorName",
			    'sClass': 'center',
			    'sName':"creatorName",
			 },
			 {
			    "mData": "formName",
			    'sClass': 'center',
			    'sName':"formName",
			 },
			 {
				"render" : function(filed, type, full, meta) {
								var n = full.type;
								switch (n) {
									case 1:
										return "增加"
									case 0:
										return "修改"
									default:
										return "删除"
								}
							}
			},
			 {
				 "render": function (filed, type, full, meta) {
					 var detail=full.details;
					 if(detail.length>26){
						 return  detail.substring(0,20)+'.....'+detail.substring(detail.length-5);					 
					 }
				        return full.details;
				    }
		     },
			 {
			    "mData": "createDate",
			    'sClass': 'center',
			    'sName':"createDate",
			 }],
			"fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
			 },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	aoData.push(
		    			{ "name": "filter_formName_LIKE", "value": $('#formName').val() },//表单名
	                    { "name": "filter_type_EQ", "value": $('#type').val() },    //类型 
	                    { "name": "filter_creatorName_LIKE", "value": $("#userName").val() },
	                    { "name": "filter_createDate_GEQ", "value": $("#datemin").val() },
	                    { "name": "filter_createDate_LEQ", "value": $("#datemin2").val() },
	                    { "name": "filter_details_LIKE", "value": $("#details").val() }
	            )	
		        $.ajax({
		            "type": 'post',
		            "url": sSource,
		            "dataType": "json",
		            "data": {
		                aoData: JSON.stringify(aoData)
		            },
		            "success": function(resp) {
		                fnCallback(resp);
		                $(":input[type='search']").attr("placeholder","请输入");
		            }
		        });
		    }
		});
	}
}

/**
 * 搜索
 */
function fn_search(){
	
	operationLogTable.fnDraw();
}

function fn_showLogDetail(str){
	//获取被选中的记录的 id
	var id = ""; 
	id = str;
	//进入编辑页面
	popup('详情',appPath+'/infoManagementLog/QueryLog?id='+id)
}



