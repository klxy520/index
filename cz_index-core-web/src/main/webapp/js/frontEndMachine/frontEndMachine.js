var frontEndMachinePath = contextPath + "/frontEndMachine";
var frontEndMachineTable;
$(document).ready(function(){
	init();  //初始化前置机列表
});

/**
 * 初始化前置机列表
 */
function init(){
	var frontEndMachineList=$("#frontEndMachineList");
	if(frontEndMachineList && frontEndMachineList.length>0){
		frontEndMachineTable = frontEndMachineList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":10,
		    "bSort": true,
		    "aaSorting" : [ [ 6, "desc" ] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,1,2,3,4,5]}],
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
		    "sAjaxSource": frontEndMachinePath+'/frontEndMachineData',
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
			    "mData": "frontEndMachinecode",
			    'sClass': 'center',
			    'sName':"frontEndMachinecode",
			 },
			 {
			    "mData": "frontEndMachineaddress",
			    'sClass': 'center',
			    'sName':"frontEndMachineaddress",
			 },
			 {
			    "mData": "state",
			    'sClass': 'center',
			    'sName':"state",
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
				"mData": "remarks",
				'sClass': 'center',
				'sName':"remarks",
			 },
			 {
				"mData": "creator",
				'sClass': 'center',
				'sName':"creator",
				"render": function (data,type,full) {
				    return full.creatorUser.name;
				}
		     },
			 {
			    "mData": "createDate",
			    'sClass': 'center',
			    'sName':"createDate",
			 },
			 {
				"mData": "updateDate",
				'sClass': 'center',
				'sName':"updateDate",
			 }],
			"fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
			 },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	aoData.push(
		    			{ "name": "filter_frontEndMachinecode_LIKE", "value": $('#frontEndMachinecode').val() },//前置机编码
	                    { "name": "filter_frontEndMachineaddress_LIKE", "value": $('#frontEndMachineaddress').val() },    //前置机地址
	                    { "name": "filter_state_EQ", "value": $("#state").val() } //状态
//	                    { "name": "filter_createDate_GEQ", "value": $("#datemin").val() },//创建开始时间
//	                    { "name": "filter_createDate_LEQ", "value": $("#datemin2").val() }//创建结束时间
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
	// 初始化搜索按钮
	$("#search_btn").click(function() {
		frontEndMachineTable.fnFilter();
	});
}


/**
 * 添加前置机
 */
function addFrontEndMachine(){
	popupForHtml('添加前置机',$('#addFrontEndMachine'),'65%','60%');
	$('#addFrontEndMachineForm :input').val('');//清空输入框内容
	var addFrontEndMachineForm = $("#addFrontEndMachineForm");
	if(addFrontEndMachineForm && addFrontEndMachineForm.length>0){
		addFrontEndMachineForm.Validform({
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
				if(data.responseData.status==0){
					layer.alert('添加前置机成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						frontEndMachineTable.fnFilter();
						layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addFrontEndMachineForm :input").val("");
					});    
				}else{
					layer.alert(data.responseData.message);
				}
			}
		});
	}
}

/**
 * 编辑前置机
 */
function updateFrontEndMachine(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个前置机");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个前置机");
		return;
	}
	//获取选中字典
	var frontEndMachine = getCheckedObject(frontEndMachineTable);
	//设置值
	setValueForUpdate(frontEndMachine);
	popupForHtml('编辑前置机',$('#updateFrontEndMachine'),'65%','60%');
	var updateFrontEndMachineForm = $("#updateFrontEndMachineForm");
	if(updateFrontEndMachineForm && updateFrontEndMachineForm.length>0){
		updateFrontEndMachineForm.Validform({
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
				if(data.responseData.status==0){
					layer.alert('修改前置机成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						frontEndMachineTable.fnFilter();
					    layer.closeAll();
					});    
				}else if(data.responseData.status==2){
					layer.alert(data.responseData.message);
				}else{
					layer.alert(data.responseData.message);
				}
			}
		});
	}
}

/**
 * 设置前置机的值
 * @param frontEndMachine
 */
function setValueForUpdate(frontEndMachine){
	$("#updateFrontEndMachine").find("#id").val(frontEndMachine.id);
	$("#updateFrontEndMachine").find("#frontEndMachinecode").val(frontEndMachine.frontEndMachinecode);
	$("#updateFrontEndMachine").find("#frontEndMachineaddress").val(frontEndMachine.frontEndMachineaddress);
	$("#updateFrontEndMachine").find("#remarks").val(frontEndMachine.remarks);
	$("#updateFrontEndMachine").find("#state").val(frontEndMachine.state);
}

/**
 * 启用前置机
 */disableFrontEndMachine
function enableFrontEndMachine(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个前置机");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个前置机");
		return;
	}
	var data = getCheckedObject(frontEndMachineTable);
	if(data && data.state == "1"){
		$.ajax({
	        "type": 'post',
	        "url": contextPath+"/frontEndMachine/enable",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'id': data.id
	        },
	        "success": function(data) {
	        	if(data.responseData.status==0){
					layer.alert('启用前置机成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						frontEndMachineTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	}else if(data && data.state == "0"){
		layer.alert("前置机已启用");
		return;
	}else{
		layer.alert("前置机异常");
		return;
	}
}
 
 /**
  * 禁用前置机
  */
 function disableFrontEndMachine(){
 	var checkedIds = getCheckedIds();
 	if(!checkedIds || checkedIds.length==0){
 		layer.alert("请选择一个前置机");
 		return;
 	}else if(checkedIds.length>1){
 		layer.alert("只能选择一个前置机");
 		return;
 	}
 	var data = getCheckedObject(frontEndMachineTable);
 	if(data && data.state == "0"){
 		$.ajax({
 	        "type": 'post',
 	        "url": contextPath+"/frontEndMachine/disable",
 	        "dataType": "json",
 	        "async" : false,
 	        "data": {
 	        	'id': data.id
 	        },
 	        "success": function(data) {
 	        	if(data.responseData.status==0){
 					layer.alert('禁用前置机成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
 						// 刷新页面
 						frontEndMachineTable.fnFilter();
 					    layer.closeAll();
 					});    
 				}else{
 					layer.alert(data.message);
 				} 
 	        }
 	    });
 	}else if(data && data.state == "1"){
 		layer.alert("前置机已禁用");
 		return;
 	}else{
 		layer.alert("前置机异常");
 		return;
 	}
 }
 
 /**
  * 删除前置机
  */
 function deleteFrontEndMachine(){
	 	var checkedIds = getCheckedIds();
	 	if(!checkedIds || checkedIds.length==0){
	 		layer.alert("请选择一个前置机");
	 		return;
	 	}else if(checkedIds.length>1){
	 		layer.alert("只能选择一个前置机");
	 		return;
	 	}
	 	var data = getCheckedObject(frontEndMachineTable);
	 	layer.confirm("前置机删除不可恢复，是否删除？", function() {
	 		$.ajax({
	 	        "type": 'post',
	 	        "url": contextPath+"/frontEndMachine/del",
	 	        "dataType": "json",
	 	        "async" : false,
	 	        "data": {
	 	        	'id': data.id
	 	        },
	 	        "success": function(data) {
	 	        	if(data.responseData.status==0){
	 					layer.alert('删除前置机成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
	 						// 刷新页面
	 						frontEndMachineTable.fnFilter();
	 					    layer.closeAll();
	 					});    
	 				}else{
	 					layer.alert(data.message);
	 				} 
	 	        }
	 	    });
		});
	 }