var platformPath = contextPath + "/platform";

var platformListDataTable;
$(document).ready(function(){
	// 初始化
	init();
});

function init(){
	// 初始化dataTable
	var platformListTable=$("#platformList");
	if(platformListTable && platformListTable.length>0){
		platformListDataTable = platformListTable.dataTable({
		    "oLanguage": { //语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,3,4,6] }],
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
		    "bStateSave":false,// true状态保持 刷新页面保持页码等数据,false:反之
		    "asStripeClasses":['text-c odd','text-c even'],
		    "aoSearchCols":[null, {"sSearch": "name"}],
		    "sAjaxSource": platformPath + '/list',
		    "aoColumns": [{
		        "mData": "id",
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){return '<input type="checkbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'}
		    },
		    {
		        "mData": "appid",
		        'sClass': 'center',
		        'sName':"appid",
		    },
		    {
		        "mData": "appName",
		        'sClass': 'center',
		        'sName':"appName",
		    },
		    {
		        "mData": "platName",
		        'sClass': 'center',
		        'sName':"platName",
		        'mRender':function(data,type,full){return '<a class="particulars" onClick="platformInfo('+full.id+')">'+data+'</a>'} 
		    },
		    {
		        "mData": "publicKey",
		        'sClass': 'center publicKey',
		        'sName':"publicKey",
		    },
		    {
		    	"mData": "privateKey",
		    	'sClass': 'center privateKey',
		    	'sName':"privateKey",
		    },
		    {
		    	"mData": "status",
		    	'sClass': 'center',
		    	'sName':"status",
		    	'mRender':function(data,type,full){ 
		    		if(data=='0'){
		        		return '<font color="blue">启用</font>';
		        	}else if(data=='1'){
		        		return '<font color="red">禁用</font>';
		        	}
	        	}
		    }
		    ],
		    "fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	// 条件查询
		    	aoData.push(
		    			{ "name": "filter_appid_EQ", "value": $('#appid').val() },  //appid
	                    { "name": "filter_platName_LIKE", "value": $('#platName').val() },   //平台名称
	                    { "name": "filter_appName_LIKE", "value": $('#appName').val() })   //应用名称（缩写）
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
	
	// 初始化搜索按钮
	$("#search_btn").click(function(){
		platformListTable.fnFilter();
	});
}


/**
 * 添加平台
 */
function addPlatform(){
	 $("#addPlatformForm :input").val("");//核心
	popupForHtml('添加平台',$('#addPlatform'),'70%','50%');
	var addPlatformForm = $("#addPlatformForm");
	if(addPlatformForm && addPlatformForm.length>0){
		addPlatformForm.Validform({
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
					layer.alert('添加平台成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
					//	platformListDataTable.fnFilter();
						platformListDataTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    $("#addPlatformForm :input").val("");//核心
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}


/**
 * 编辑平台
 */
function updatePlatform(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择平台");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个平台");
		return;
	}
	
	//获取平台
	var platform = getPlatform(checkedIds[0]);
	//设置值
	setValueForUpdate(platform);
	
	popupForHtml('编辑平台',$('#updatePlatform'),'70%','50%');
	var updateUserForm = $("#updatePlatformForm");
	if(updateUserForm && updateUserForm.length>0){
		updateUserForm.Validform({
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
					layer.alert('修改平台成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						platformListDataTable.fnFilter();
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
 * 展示平台详情
 * @param id
 */
function platformInfo(id){
	//获取用户
	var platform = getPlatform(id);
	//设置值
	setValueForInfo(platform);
	
	popupForHtml('平台详情',$('#platformInfo'),'80%','80%');
}

/**
 * 获取平台
 * @param id
 * @returns
 */
function getPlatform(id){
	var platform = null;
	$.ajax({
        type: 'post',
        url: platformPath + "/get",
        dataType: "json",
        async : false,
        data: {
            'id': id
        },
        success: function(data) {
        	if(data.status==0){
        		platform = data.data;
			}else{
				layer.alert(data.message);
			}   
        }
    });
	return platform;
}

/**
 * 编辑页面设置值
 * @param platform
 */
function setValueForUpdate(platform){
	$("#updatePlatform").find("#id").val(platform.id);
	$("#updatePlatform").find("#appid").val(platform.appid);
	$("#updatePlatform").find("#platName").val(platform.platName);
	$("#updatePlatform").find("#appName").val(platform.appName);
	$("#updatePlatform").find("#appSecret").val(platform.appSecret);
	$("#updatePlatform").find("#appPrivateKey").val(platform.appPrivateKey);
	$("#updatePlatform").find("#exchangeRule").val(platform.exchangeRule);
	$("#updatePlatform").find("#publicKey").val(platform.publicKey);
	$("#updatePlatform").find("#privateKey").val(platform.privateKey);
	$("#updatePlatform").find("#status").val(platform.status);
}

/**
 * 详情页面设置值
 * @param platform
 */
function setValueForInfo(platform){
	$("#platformInfo").find("#appid").text(platform.appid);
	$("#platformInfo").find("#platName").text(platform.platName);
	$("#platformInfo").find("#appName").text(platform.appName);
	$("#platformInfo").find("#appSecret").text(platform.appSecret);
	$("#platformInfo").find("#appPrivateKey").text(platform.appPrivateKey);
	$("#platformInfo").find("#exchangeRule").text(platform.exchangeRule);
	$("#platformInfo").find("#publicKey").text(platform.publicKey);
	$("#platformInfo").find("#privateKey").text(platform.privateKey);
}

/**
 * 启用平台
 */
function setPlatformEnable(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择平台");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个平台");
		return;
	}
	var data = getCheckedObject(platformListDataTable);
	if(data && data.status=='1'){   //状态:0启用,1禁用
		$.ajax({
	        "type": 'post',
	        "url": platformPath + "/setPlatformEnable",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'platformId': data.id
	        },
	        "success": function(data) {
	        	if(data.status==0){
					layer.alert('启用平台成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						platformListDataTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	}else{
		layer.alert("平台已启用");
		return;
	}
}


/**
 * 禁用平台
 */
function setPlatformDisable(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择平台");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个平台");
		return;
	}
	var data = getCheckedObject(platformListDataTable);
	if(data && data.status=='0'){   //状态:0启用,1禁用
		$.ajax({
			"type": 'post',
	        "url": platformPath + "/setPlatformDisable",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'platformId': data.id
	        },
	        "success": function(data) {
	        	if(data.status==0){
	        		layer.alert('禁用平台成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						platformListDataTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	}else{
		layer.alert("平台已禁用");
		return;
	}
}