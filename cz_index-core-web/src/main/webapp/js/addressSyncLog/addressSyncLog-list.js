var personListPath = contextPath + "/addressSyncLog";
var personTable;
/**
 * 初始化主索引基本身份信息列表
 */
$(document).ready(function(){
	iniList();
});
function iniList(){
	var personList=$("#contactSyncLogList");
	if(personList && personList.length>0){
		personTable = personList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aaSorting" : [[8,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,1,2,3,4,5,6,9]}],
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
		    "sAjaxSource": personListPath+'/list',
		    "aoColumns": [
		     {
		        "mData": 'addressSyncLogId',
		        'sClass': 'center',
		        'sName':"addressSyncLogId",
		        'mRender':function(data,type,full){ 
		        	return judgmentIsSyncLog(data,full.syncStatus,full.frontEndMachinestate);
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
				   "mData": 'addressTypeCode',
				   'sClass': 'center',
				   'sName':"addressTypeCode",
				   "render": function (data,type,full) {
					   return '<a class="particulars" onclick="queryAddressDetails('+full.addressId+')" href="javascript:;">'+fn_getDictByKeyCode("indexAddressTypeCode",data+'')+'</a>';
				   } 
			   },
			   {
				   "mData": 'frontEndMachinecode',
				   'sClass': 'center',
				   'sName':"frontEndMachinecode"
			   },
			   {
				   "mData": "frontEndMachineaddress",
				   'sClass': 'center',
				   'sName':"frontEndMachineaddress"
				   
			   },
			    {
			    	"mData": "frontEndMachinestate",
			    	'sClass': 'center',
			    	'sName':"frontEndMachinestate",
			    	'mRender':function(data){
				    	if(data == "0"){
				    		return "启用";
				    	}else if(data == "1"){
				    		return "禁用";
				    	}else if(data == "3"){
				    		return "异常";
				    	}
			        	return "";
			        }
			    },
			     {
				     "mData": 'syncStatus',
				     'sClass': 'center',
				     'sName':"syncStatus",
				     'mRender':function(data){
					    	if(data == "0"){
					    		return "未同步";
					    	}else if(data == "1"){
					    		return "已同步";
					    	}
				        	return "";
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
		    },
		    {
		    	"mData": "remark",
		    	'sClass': 'center',
		    	'sName':"remark"
		    }
		    ],
		    "fnDrawCallback": function( settings ) {
		    	checkedOfManyClickTr(".checkbox_id","checkbox_id_all");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	aoData.push(
	                /*{ "name": "filter_personName_EQ", "value": $('#indexName').val() },
	                { "name": "filter_idCard_EQ", "value": $('#indexIdCard').val() },
	                {"name": "filter_cardNo_EQ", "value": $('#indexCardNo').val() },
	                {"name": "filter_sexCode_EQ", "value": $('#indexSex').val() },*/
	               { "name": "filter_frontId_EQ", "value": $("#frontId").val() },
	                { "name": "filter_syncStatus_EQ", "value": $("#syncStatus").val() },
	                { "name": "filter_personName_EQ", "value": $("#personName").val() },
	                { "name": "filter_addressTypeCode_EQ", "value": $("#addressTypeCode").val() },
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
 * 主索引基本身份信息列表条件查询
 */
function search(){
	personTable.fnFilter();
}
/**
 * 查询地址详细信息
 */
function queryAddressDetails(id){
	popup('地址信息详情',contextPath+'/address/queryAddressDetails?id='+id,'650','330');
}
/**
 * 查询单条详细信息
 */
function queryPersonDetails(id){
	popup('基本身份信息详情',contextPath+'/person/queryPersonDetails?id='+id,'880','550');
}
/***
 *获取多个复选框的地址信息日志的id
 */
function getAddressSyncLogListId(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0||checkedIds=="on"){
		layer.alert("至少选择一条地址信息日志");
		return false;
	}
	return checkedIds;
}
/**
 * 同步地址信息日志
 */
function syncLogAddress(){ 
	var ids=getAddressSyncLogListId();
	if(ids!=false&&ids!="on"){
	layer.confirm("确认同步地址信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : contextPath + "/addressSyncLog/syncLogAddress",
				"dataType" : "json",
				"data":{"addressSyncLogId":JSON.stringify(ids)},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							personTable.fnDraw(true); //刷新当前页面
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
 * 判断日志是否能够同步
 */
function judgmentIsSyncLog(data,syncStatus,frontEndMachinestate){
	var content=""
	if(syncStatus=="0"&&frontEndMachinestate=="0"){
		content='<input type="checkbox" name="goodCheckbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'
	}
	return content;
}