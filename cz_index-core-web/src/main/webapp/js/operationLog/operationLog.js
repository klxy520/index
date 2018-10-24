var operationLogPath = contextPath + "/operationLog";
var operationLogTable;
$(document).ready(function(){
	init();  //初始化日志列表
});

/**
 * 初始化日志列表
 */
function init(){
	var operationLogList=$("#operationLogList");
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
		    "sAjaxSource": operationLogPath+'/operationLogListAllData',
		    "aoColumns": [
		     {
		        "mData": 'id',
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){ 
		        	return '<input type="checkbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'
		        }
		     },
		     {
				 "render": function (filed, type, full, meta) {
				        return '<a class="particulars" onclick="fn_showOperationLogViewPage('+full.id+')" href="javascript:;">详情</a>';
				    } 
			 },
		     {
			    "mData": "userName",
			    'sClass': 'center',
			    'sName':"userName",
			 },
			 {
			    "mData": "formName",
			    'sClass': 'center',
			    'sName':"formName",
			 },
			 {
			    "mData": "type",
			    'sClass': 'center',
			    'sName':"type",
			 },
			 {
				    "mData": "detail",
				    'sClass': 'center zm_break',
				    'sName':"detail",
				   'mRender':function(data,type,full){ 
			        	return data.substring(0,25)+"...";
			        }
		     },
			 {
			    "mData": "operationDate",
			    'sClass': 'center',
			    'sName':"operationDate",
			 }],
			"fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
			 },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	aoData.push(
		    			{ "name": "filter_formName_LIKE", "value": $('#formName').val() },//表单名
	                    { "name": "filter_type_LIKE", "value": $('#type').val() },    //类型 
	                    { "name": "filter_userName_LIKE", "value": $("#userName").val() },
	                    { "name": "filter_operationDate_GEQ", "value": $("#datemin").val() },
	                    { "name": "filter_operationDate_LEQ", "value": $("#datemin2").val() }
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

function fn_showOperationLogViewPage(str){
	//获取被选中的记录的 id
	var id = ""; 
	id = str;
	//进入编辑页面
	popup('详情',appPath+'/operationLog/operationLogView?id='+id)
}



