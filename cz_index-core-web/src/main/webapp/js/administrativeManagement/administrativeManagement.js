/**
 * 文 件 名：administrativeManagement.js
 * CopyRight (c) 2016 kentrasoft, Inc. All rights reserved.
 * 创 建 人：wangdeyou
 * 日      期：2017年8月28日
 * 描      述：
 */
var administrativeManagementListDataTable;
$(document).ready(function(){
	initDataTable();
});
/**
 * 
 * 描述：初始化行政机构数据
 * @author wangdeyou 2017年8月28日 下午4:23:14 
 * @version 1.0
 */
function initDataTable(){
	// 初始化dataTable
	var administrativeManagementListTable=$("#administrativeManagementList");
	if(administrativeManagementListTable && administrativeManagementListTable.length>0){
		administrativeManagementListDataTable = administrativeManagementListTable.dataTable({
		    "oLanguage": { //语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0] }],
		    "aLengthMenu": [[15,30,50,100], [15,30,50,100]],//定义每页显示数据数量
		    "bInfo": true,
		    "bWidth": true,
		    "bScrollCollapse": true,
		    "sPaginationType": "full_numbers",
		    "bProcessing": true,
		    "aaSorting" : [ [ 1, "desc" ] ],
		    "bServerSide": true,
		    "bDestroy": true,
		    "bSortCellsTop": true,
		    "bStateSave":true,// 状态保持 刷新页面保持页码等数据
		    "asStripeClasses":['text-c odd','text-c even'],
		    "aoSearchCols":[null, {"sSearch": "name"}],
		    "sAjaxSource": contextPath+'/administrativeManagement/list',
		    "aoColumns": [{
		        "mData": "id",
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){return '<input type="checkbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'}
		    },
		    {
		        "mData": "administrativeName",
		        'sClass': 'center',
		        'sName':"administrativeName"
		    },
		    {
		        "mData": "address",
		        'sClass': 'center',
		        'sName':"address"
		    },
		    {
		        "mData": "phone",
		        'sClass': 'center',
		        'sName':"phone"
		    },
		    {
		        "mData": "zipCode",
		        'sClass': 'center',
		        'sName':"zipCode"
		    },
		    {
		        "mData": "personCharge",
		        'sClass': 'center',
		        'sName':"personCharge"
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
		    }
		    ], 
		    "fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	// 条件查询:
		    	aoData.push(
		    			{ "name": "filter_administrativeName_LIKE", "value": $('#administrativeName').val() },//机构名称
	                    { "name": "filter_address_LIKE", "value": $('#address').val() },    //地址
	                    { "name": "filter_phone_LIKE", "value": $("#phone").val() }, //电话
	                    { "name": "filter_personCharge_LIKE", "value": $("#personCharge").val() }, //负责人
	                    { "name": "filter_zipCode_LIKE", "value": $("#zipCode").val() },//邮编
	                    { "name": "filter_createDate_GEQ", "value": $("#datemin").val() },//创建开始时间
	                    { "name": "filter_createDate_LEQ", "value": $("#datemin2").val() },//创建结束时间
	                    { "name": "filter_isDelete_EQ", "value": "0" }
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
	// 初始化搜索按钮
	$("#search_btn").click(function(){
		administrativeManagementListDataTable.fnFilter();
	});
}



/**
 * 添加行政机构
 */
function addAdministrativeManagement(){
	popupForHtml('添加行政机构',$('#addAdministrativeManagement'),'70%','50%');
	$('#addAdministrativeManagementForm :input').val('');//清空输入框内容
	var addAdministrativeManagementForm = $("#addAdministrativeManagementForm");
	if(addAdministrativeManagementForm && addAdministrativeManagementForm.length>0){
		addAdministrativeManagementForm.Validform({
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
					layer.alert('添加行政机构成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						administrativeManagementListDataTable.fnFilter();
						layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addDictionaryForm :input").val("");
					});    
				}else if(data.responseData.status==1){
					layer.alert(data.responseData.message);
				}else{
					layer.alert(data.responseData.message);
				}
			}
		});
	}
}


/**
 * 编辑行政机构
 */
function updateAdministrativeManagement(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个行政机构");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个行政机构");
		return;
	}
	//获取选中字典
	var administrativeManagement =getCheckedObject(administrativeManagementListDataTable);
	//设置值
	setValueForUpdate(administrativeManagement);
	popupForHtml('编辑行政机构',$('#updateAdministrativeManagement'),'70%','50%');
	var updateAdministrativeManagementForm = $("#updateAdministrativeManagementForm");
	if(updateAdministrativeManagementForm && updateAdministrativeManagementForm.length>0){
		updateAdministrativeManagementForm.Validform({
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
					layer.alert('修改行政机构成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						administrativeManagementListDataTable.fnFilter();
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
 * 
 * 描述：设置行政的值
 * @param administrativeManagement
 * @author wangdeyou 2017年8月30日 上午11:42:51 
 * @version 1.0
 */
function setValueForUpdate(administrativeManagement){
	$("#updateAdministrativeManagement").find("#id").val(administrativeManagement.id);
	$("#updateAdministrativeManagement").find("#administrativeName").val(administrativeManagement.administrativeName);
	$("#updateAdministrativeManagement").find("#address").val(administrativeManagement.address);
	$("#updateAdministrativeManagement").find("#phone").val(administrativeManagement.phone);
	$("#updateAdministrativeManagement").find("#zipCode").val(administrativeManagement.zipCode);
	$("#updateAdministrativeManagement").find("#personCharge").val(administrativeManagement.personCharge);
	$("#updateAdministrativeManagement").find("#remark").val(administrativeManagement.remark);
}

/**
 * 
 * 描述：删除行政机构
 * @author wangdeyou 2017年8月30日 下午1:26:17 
 * @version 1.0
 */
function del(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个行政机构");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个行政机构");
		return;
	}
	var data = getCheckedObject(administrativeManagementListDataTable);
	layer.confirm('确定删除吗？',function(){
		$.ajax({
	        "type": 'post',
	        "url": contextPath+"/administrativeManagement/del",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'id': data.id
	        },
	        "success": function(data) {
	        	if(data.responseData.status==0){
					layer.alert('删除行政机构成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						administrativeManagementListDataTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.responseData.message);
				} 
	        }
	    });
	})
}
/*********************************************************************/
var fieldPath = contextPath + "/roleField";
function setRoleFieldLayer(){
	
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个行政机构");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个行政机构");
		return;
	}
	var checkedRole = getCheckedObject(administrativeManagementListDataTable);

	// 赋值角色from表单
	$("#setRoleField").find("#roleId").val(checkedRole.id);
	$("#setRoleField").find("#roleName").val(checkedRole.administrativeName);
	// 初始化菜单
	initField(checkedRole.id);
	// 获取角色菜单并选中
	//initRoleField(checkedRole.id);
	popupForHtml('信息授权',$('#setRoleField'),'40%','50%');
}
/**
获取该角色可以查看字段信息
 */
function initField(roleId){
	$.ajax({
		"type":"POST",
		"url":fieldPath+"/topField",
		"dataType":"json",
		"async":false,
		"data":{"roleId":roleId},
		"success":function(data){
			if(data.status==0){
        		var roleField = data.data.roleField;//该用户可以查看字段
        		var allFields = data.data.allFields;//所有字段    
        		initRoleField(roleField,allFields);
			}else{
				layer.alert(data.message);
			}   
		}
	});
}
/**查看该角色字段查看权限
 */
function initRoleField(roleField,allFields){
	$("#topField").empty()
	var str="";
	for(var i=0;i<allFields.length;i++){
			str+="<li class='mt-5'><label><input  name='field'  type='checkbox' value="+allFields[i].id+" />"+allFields[i].describe+" </label> </li>"
	}	
	$("#topField").append(str);
	for(var i=0;i<roleField.length;i++){
		$("#topField input[name=field]").each(function(){ //遍历table里的全部checkbox
			if($(this).val()==roleField[i].fieldId) //如果被选中
			$(this).attr("checked",'true') 
		});
	}
}
/**
 *设置该角色字段查看权限
 */
function setRoleField(){
	var roleId = $("#setRoleField").find("#roleId").val();
	
	var s_child="",s_parent="";
	var fieldStr=""
	$("#topField input[name=field]:checked").each(function() {
		fieldStr += $(this).val() + ",";
	});
	$.ajax({
        "type": 'post',
        "url": fieldPath+"/setRoleField",
        "dataType": "json",
        "async" : false,
        "data":{"roleId":roleId,"fieldStr":fieldStr},
        "success": function(data) {
        	if(data.status==0){
        		layer.alert('设置成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
					  // 刷新页面
				    layer.closeAll();
				});   
    		} else{
				layer.alert(data.message);
			}   
		}
    });
}