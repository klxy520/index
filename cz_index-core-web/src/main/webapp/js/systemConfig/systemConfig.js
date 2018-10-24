
var systemConfigPath = contextPath + "/systemConfig";
var systemConfigListDataTable;

$(document).ready(function(){
	// 初始化
	init();
});

function init(){
	// 初始化dataTable
	var systemConfigListTable=$("#systemConfigList");
	if(systemConfigListTable && systemConfigListTable.length>0){
		systemConfigListDataTable =systemConfigListTable.dataTable({
		    "oLanguage": { //语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,3] }],
		    "aLengthMenu": [[15,30,50,100], [15,30,50,100]],//定义每页显示数据数量
		    "bInfo": true,
		    "bWidth": true,
		    "bScrollCollapse": true,
		    "sPaginationType": "full_numbers",
		    "bProcessing": true,
		    "bServerSide": true,
		    "aaSorting" : [ [ 1, "desc" ] ],
		    "bDestroy": true,
		    "bSortCellsTop": true,
		    "bStateSave":true,// 状态保持 刷新页面保持页码等数据
		    "asStripeClasses":['text-c odd','text-c even'],
		    "aoSearchCols":[null, {"sSearch": "name"}],
		    "sAjaxSource": systemConfigPath+'/list',
		    "aoColumns": [{
		        "mData": "id",
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){return '<input type="checkbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'}
		    },
		    {
		        "mData": "systemKey",
		        'sClass': 'center',
		        'sName':"systemKey"
		    },
		    {
		        "mData": "systemValue",
		        'sClass': 'center sysconfig_value',
		        'sName':"systemValue"
		    },
		    {
		        "mData": "description",
		        'sClass': 'center',
		        'sName':"description"
		    }
		    ],
		    "fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	// 条件查询
		    	aoData.push(
	                    { "name": "filter_systemKey_LIKE", "value": $('#systemKey').val() },//key
	                    { "name": "filter_systemValue_LIKE", "value": $('#systemValue').val() })//value
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
	
	// 初始化搜索按钮
	$("#search_btn").click(function(){
		systemConfigListTable.fnFilter();
	});
}

/**
 * 添加系统参数
 */
function addSystemConfig(){
	//清空添加表单中输入框内容
	$("#addSystemConfigForm :input").val("");
	popupForHtml('添加系统参数',$('#addSystemConfig'),'60%','45%');
	var addSystemConfigForm = $("#addSystemConfigForm");
	if(addSystemConfigForm && addSystemConfigForm.length>0){
		addSystemConfigForm.Validform({
			btnSubmit:"#form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:false,
			dragonfly:false,
			tipSweep:false,
			label:".label",
			showAllError:false,
			postonce:true,
			ajaxPost:true,
			datatype:customDataType,
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
				if(data.status==0){
					layer.alert('添加系统参数成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						systemConfigListDataTable.fnFilter();
					    layer.closeAll();
					   //$("#addSystemConfigForm :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");//核心
					    $("#addSystemConfigForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}

/**
 * 编辑系统参数
 */
function updateSystemConfig(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择系统参数");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个系统参数");
		return;
	}
	
	//获取系统参数
	var systemConfig =getCheckedObject(systemConfigListDataTable);
	//设置值
	setValueForUpdate(systemConfig);
	
	popupForHtml('编辑系统参数',$('#updateSystemConfig'),'60%','45%');
	var updateSystemConfigForm = $("#updateSystemConfigForm");
	if(updateSystemConfigForm && updateSystemConfigForm.length>0){
		updateSystemConfigForm.Validform({
			btnSubmit:"#form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:false,
			dragonfly:false,
			tipSweep:false,
			label:".label",
			showAllError:false,
			postonce:true,
			ajaxPost:true,
			datatype:customDataType,
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
				if(data.status==0){
					layer.alert('修改系统参数成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						systemConfigListDataTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/**
 * 获取系统参数
 * @param id
 * @returns
 */
function getSystemConfig(id){
	var systemConfig = null;
	$.ajax({
        "type": 'post',
        "url": systemConfigPath+"/get",
        "dataType": "json",
        "async" : false,
        "data": {
            'id': id
        },
        "success": function(data) {
        	if(data.status==0){
        		systemConfig = data.data;
			}else{
				layer.alert(data.message);
			}   
        }
    });
	return systemConfig;
}
/**
 *编辑页面设置值
 * @param systemConfig
 */
function setValueForUpdate(systemConfig){
	$("#updateSystemConfig").find("#id").val(systemConfig.id);
	$("#updateSystemConfig").find("#systemKey").val(systemConfig.systemKey);
	$("#updateSystemConfig").find("#systemValue").val(systemConfig.systemValue);
	$("#updateSystemConfig").find("#description").val(systemConfig.description);
	// bug:积分商城-173
	textarealength($("#updateSystemConfig").find("#description"),200);
}