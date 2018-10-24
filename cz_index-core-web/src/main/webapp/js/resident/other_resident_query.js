var residentPath = contextPath + "/residentBase";
var otherResidentQueryTable;
$(document).ready(function(){
	iniList();  //初始化居民基本信息列表
	
});

/**
 * 初始化居民基本信息列表
 */
function iniList(){
	var otherResidentQueryList=$("#otherResidentQuery");
	if(otherResidentQueryList && otherResidentQueryList.length>0){
		otherResidentQueryTable = otherResidentQueryList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": false,
		    "aaSorting" : [ [ 1, "desc" ] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,3,4,5,6,7]}],
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
		    "sAjaxSource": residentPath+'/queryOtherResidentList',
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
			     "mData": 'realName',
			     'sClass': 'center',
			     'sName':"realName",
			   
			   },
			     {
				     "mData": 'healthNumber',
				     'sClass': 'center',
				     'sName':"healthNumber"
				     },
		     {
		        "mData": "socialNumber",
		        'sClass': 'center',
		        'sName':"socialNumber"
		    },
		    {
		        "mData": "idNumber",
		        'sClass': 'center',
		        'sName':"idNumber"
		    },
		    {
		        "mData": "periodValidityDate",
		        'sClass': 'center',
		        'sName':"periodValidityDate",
		        'mRender':function(data,type,full){ 
		        	return data.substring(0,10)
		        }
		    },
		    {
		        "mData": "office",
		        'sClass': 'center',
		        'sName':"office"
		    },
		    {
		    	"mData": "area",
		    	'sClass': 'center',
		    	'sName':"area"
		    },
		    {
		    	"mData": "sex",
		    	'sClass': 'center',
		    	'sName':"sex"
		    }
		    ],
		    "fnDrawCallback": function( settings ) {
		        //绑定 tr  事件 点击checkbox更容易
		    	checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	aoData.push(
	                   { "name": "number", "value": $('#number').val() }
	             
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
		            }
		        });
		    }
		});
	}
}
/**
 * 居民基本信息列表条件查询
 */
function search(){
	otherResidentQueryTable.fnFilter();
}
/***
 *获取单个复选框的居民信息
 */
function getResident(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条居民信息");
		return false;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一条居民信息");
		return false;
	}
	return getCheckedObject(otherResidentQueryTable);
}
/**
 * 把其他居民健康卡信息添加到我的管理权限范围之内
 */
function addManagementScope(){
	var resident=getResident()
	if(resident!=false){
		layer.confirm("确认将此居民信息添加到我的管理权限范围",function(){
			$.ajax({
				"type" : 'post',
				"url" : residentPath + "/addManagementScope",
				"dataType" : "json",
				"data":{"residentId":resident.id},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							parent.residentListTable.fnDraw(true); //刷新当前页面
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