/**
 * 文 件 名：administrativeDivision.js
 * CopyRight (c) 2016 kentrasoft, Inc. All rights reserved.
 * 创 建 人：liujicheng
 * 日      期：2017年8月30日
 * 描      述：
 */

/**
 * 
 */
var administrativeDivisionListDataTable;
var administrativeDivisionTree
var setting = {
		view: {
			dblClickExpand: false,
			showLine: false,
			selectedMulti: false
		},
		data: {
			/*key : {
				checked : "checked",
				children : "subAdministrativeDivisionList",
				name : "name",
				title : "description",
				url : "url"
			},*/
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentid",
				rootPId : null
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
//				console.log(treeId);
//				console.log(treeNode);
			},
			onClick: zTreeOnClick
		}
	};
$(document).ready(function(){
	initAdministrativeDivision();
	initDataTable();
});

function initAdministrativeDivision(){
	$.ajax({
		"type":"POST",
		"url":contextPath + "/administrativeDivision/topAdministrativeDivision",
		"dataType":"json",
		"async":false,
		"data":null,
		"success":function(data){
			if(data.status==0){
        		initTree(data.data);
			}else{
				layer.alert(data.message);
			}   
		}
	});
}

/**
 * 初始化字典树
 * @param zNodes
 */
function initTree(zNodes){
	var topAdministrativeDivision = $("#topAdministrativeDivision");
	administrativeDivisionTree = $.fn.zTree.init(topAdministrativeDivision, setting, zNodes);
	administrativeDivisionTree.selectNode(administrativeDivisionTree.getNodes()[0]);
}

/**
 * 点击节点事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
//    console.log(treeNode.tId + ", " + treeNode.name);
    $("#parentId").val(treeNode.id);
    //刷新子区域机构
    administrativeDivisionListDataTable.fnFilter();
};

function initDataTable(){
	// 初始化dataTable
	var administrativeDivisionListTable=$("#administrativeDivisionList");
	if(administrativeDivisionListTable && administrativeDivisionListTable.length>0){
		administrativeDivisionListDataTable = administrativeDivisionListTable.dataTable({
		    "oLanguage": { //语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,5] }],
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
		    "sAjaxSource": contextPath+'/administrativeDivision/list',
		    "aoColumns": [{
		        "mData": "id",
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){return '<input type="checkbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'}
		    },
		    {
		        "mData": "name",
		        'sClass': 'center',
		        'sName':"name"
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
		    	// 获取父字典id
		    	var parentId = $('#parentId').val();
		    	// 条件查询:
		    	aoData.push(
	                    { "name": "filter_parentid_EQ", "value": parentId},
	                    { "name": "filter_isDelete_EQ", "value": "0" })
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
 * 添加区域机构
 */
function addAdministrativeDivision(){
	var selectedNodes = administrativeDivisionTree.getSelectedNodes();
	if(!selectedNodes || selectedNodes.length!=1){
		layer.alert("请选择一个区域机构节点");
		return;
	}
	var parentAdministrativeDivision = selectedNodes[0];
	$("#addAdministrativeDivisionForm :input").val("");
	//获取父类区域机构
	$("#addAdministrativeDivision").find("#parentId").val(parentAdministrativeDivision.id);
	popupForHtml(parentAdministrativeDivision.name+'->添加区域机构',$('#addAdministrativeDivision'),'70%','50%');
	var addAdministrativeDivisionForm = $("#addAdministrativeDivisionForm");
	if(addAdministrativeDivisionForm && addAdministrativeDivisionForm.length>0){
		addAdministrativeDivisionForm.Validform({
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
					layer.alert('添加区域机构成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						administrativeDivisionListDataTable.fnFilter();
						// 刷新节点树
						initAdministrativeDivision();
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addAdministrativeDivisionForm :input").val("");
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
 * 编辑区域机构
 */
function updateAdministrativeDivision(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个区域机构");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个区域机构");
		return;
	}
	
	//获取选中区域机构
	var administrativeDivision = getCheckedObject(administrativeDivisionListDataTable);
	//设置值
	setValueForUpdate(administrativeDivision);
	
	popupForHtml('编辑区域机构',$('#updateAdministrativeDivision'),'70%','50%');
	var updateAdministrativeDivisionForm = $("#updateAdministrativeDivisionForm");
	if(updateAdministrativeDivisionForm && updateAdministrativeDivisionForm.length>0){
		updateAdministrativeDivisionForm.Validform({
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
					layer.alert('修改区域机构成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						administrativeDivisionListDataTable.fnFilter();
						// 刷新节点树
						initAdministrativeDivision();
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

function setValueForUpdate(administrativeDivision){
	$("#updateAdministrativeDivision").find("#id").val(administrativeDivision.id);
	$("#updateAdministrativeDivision").find("#parentId").val(administrativeDivision.parentid);
	$("#updateAdministrativeDivision").find("#name").val(administrativeDivision.name);
	$("#updateAdministrativeDivision").find("#address").val(administrativeDivision.address);
	$("#updateAdministrativeDivision").find("#phone").val(administrativeDivision.phone);
	$("#updateAdministrativeDivision").find("#zipCode").val(administrativeDivision.zipCode);
	$("#updateAdministrativeDivision").find("#personCharge").val(administrativeDivision.personCharge);
	$("#updateAdministrativeDivision").find("#remark").val(administrativeDivision.remark);
}


function del(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个区域机构");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个区域机构");
		return;
	}
	var data = getCheckedObject(administrativeDivisionListDataTable);
	layer.confirm('确定删除吗？',function(){
		$.ajax({
	        "type": 'post',
	        "url": contextPath+"/administrativeDivision/del",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'id': data.id
	        },
	        "success": function(data) {
	        	if(data.responseData.status==0){
					layer.alert('删除区域机构成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						administrativeDivisionListDataTable.fnFilter();
						// 刷新节点树
						initAdministrativeDivision();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.responseData.message);
				} 
	        }
	    });
	})
}

