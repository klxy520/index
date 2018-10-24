var rolePath = contextPath + "/role";
var menuPath = contextPath + "/menu";
var menuTree;
var roleListDataTable;
var setting = {
		check: {
			enable: true,      // 是否显示 checkbox / radio
		},
		view: {
			dblClickExpand: false,
			showLine: false,
			selectedMulti: false
		},
		data: {
			key : {
				checked : "checked",
				children : "subMenuList",
				name : "name",
				title : "description",
				url : "url",
				tId:"id"
			}
		},
		callback: {
			beforeClick: function(treeId, treeNode) {
				//console.log(treeId);
				//console.log(treeNode);
			},
			onClick: zTreeOnClick
		}
	};

$(document).ready(function(){
	// 初始化
	init();
});

function init(){
	// 初始化dataTable
	var roleListTable=$("#roleList");
	if(roleListTable && roleListTable.length>0){
		roleListDataTable =roleListTable.dataTable({
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
		    "sAjaxSource": rolePath+'/list',
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
		        "mData": "mark",
		        'sClass': 'center',
		        'sName':"mark"
		    },
		    {
		        "mData": "description",
		        'sClass': 'center',
		        'sName':"description"
		    },
		    {
		        "mData": "status",
		        'sClass': 'center',
		        'sName':"status",
		        'mRender':function(data,type,full){ 
		        	if(data=='0'){
		        		return '启用';
		        	}else{
		        		return '禁用';
		        	}
	        	}
		    },
		    {
		        "mData": "showIndex",
		        'sClass': 'center',
		        'sName':"showIndex"
		    }
		    ],
		    "fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	// 条件查询
		    	aoData.push(
	                    { "name": "filter_name_LIKE", "value": $('#name').val() },//名称
	                    { "name": "filter_mark_LIKE", "value": $('#mark').val() },//标识
	                    { "name": "filter_status_EQ", "value": $('#status').val() })//状态
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
		roleListTable.fnFilter();
	});
}

/**
 * 添加用户
 */
function addRole(){
	//清空添加表单中输入框内容
    $("#addRoleForm :input").val("");
	popupForHtml('添加角色',$('#addRole'),'60%','50%');
	var addRoleForm = $("#addRoleForm");
	if(addRoleForm && addRoleForm.length>0){
		addRoleForm.Validform({
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
					layer.alert('添加角色成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						roleListDataTable.fnFilter();
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addRoleForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}

/**
 * 编辑用户
 */
function updateRole(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择角色");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个角色");
		return;
	}
	
	//获取角色
	var role =getRole(checkedIds[0]);
	//设置值
	setValueForUpdate(role);
	
	popupForHtml('编辑角色',$('#updateRole'),'60%','50%');
	var updateRoleForm = $("#updateRoleForm");
	if(updateRoleForm && updateRoleForm.length>0){
		updateRoleForm.Validform({
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
					layer.alert('修改角色成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						roleListDataTable.fnFilter();
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
 * 获取用户
 * @param id
 * @returns
 */
function getRole(id){
	var role = null;
	$.ajax({
        "type": 'post',
        "url": rolePath+"/get",
        "dataType": "json",
        "async" : false,
        "data": {
            'id': id
        },
        "success": function(data) {
        	if(data.status==0){
        		role = data.data;
			}else{
				layer.alert(data.message);
			}   
        }
    });
	return role;
}
/**
 *编辑页面设置值
 * @param role
 */
function setValueForUpdate(role){
	$("#updateRole").find("#id").val(role.id);
	$("#updateRole").find("#name").val(role.name);
	$("#updateRole").find("#mark").val(role.mark);
	$("#updateRole").find("#description").val(role.description);
	$("#updateRole").find("#showIndex").val(role.showIndex);
	$("#updateRole").find("#status").val(role.status);
	// bug:积分商城-173
	textarealength($("#updateRole").find("#description"),200);
}

/**
 * 点击节点事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    //console.log(treeNode.tId + ", " + treeNode.name);
    // 选中与否
    menuTree.checkNode(treeNode, !(treeNode.checked), true);
};

/**
 * 获取菜单
 */
function initMenu(){
	$.ajax({
		"type":"POST",
		"url":menuPath+"/topMenu",
		"dataType":"json",
		"async":false,
		"data":null,
		"success":function(data){
			if(data.status==0){
        		var topMenu = data.data;
        		initTree(topMenu);
			}else{
				layer.alert(data.message);
			}   
		}
	});
}
/**
 * 初始化菜单树
 * @param zNodes
 */
function initTree(zNodes){
	var topMenu = $("#topMenu");
	menuTree = $.fn.zTree.init(topMenu, setting, zNodes);
	menuTree.expandAll(true);
}
/**
 * 角色授权弹出层
 */
function setRoleMenuLayer(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择角色");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个角色");
		return;
	}
	var checkedRole = getCheckedObject(roleListDataTable);
	// 赋值角色from表单
	$("#setRoleMenu").find("#roleId").val(checkedRole.id);
	$("#setRoleMenu").find("#roleName").val(checkedRole.name);
	
	// 初始化菜单
	initMenu();
	// 获取角色菜单并选中
	initRoleMenu(checkedRole.id);
	popupForHtml('角色授权',$('#setRoleMenu'),'75%','80%');
}

/**
 * 获取角色菜单并选中
 * @param roleId
 */
function initRoleMenu(roleId){
	$.ajax({
		"type":"POST",
		"url":rolePath+"/getRoleMenu",
		"dataType":"json",
		"async":false,
		"data":{"roleId":roleId},
		"success":function(data){
			if(data.status==0){
        		var roleMenuList = data.data;
        		for(var i=0;i<roleMenuList.length;i++){
        			// 选中已赋权限菜单
        			var node = menuTree.getNodesByParam("id", roleMenuList[i].systemMenuId, null);
        			if(node && node[0]){
        				menuTree.checkNode(node[0],true,false);
        			}else{
        				//console.log("没有该菜单id:"+roleMenuList[i].systemMenuId);
        			}
        		}
			}else{
				layer.alert(data.message);
			}   
		}
	});
}

/**
 * 角色授权
 */
function setRoleMenu(){
	var roleId = $("#setRoleMenu").find("#roleId").val();
	// 选中菜单
	var checkedNodes =  menuTree.getCheckedNodes(true);
	var menuIdStr = "";
	if(checkedNodes && checkedNodes.length>0){
		for(var i=0;i<checkedNodes.length;i++){
			var menuId = checkedNodes[i].id;
			menuIdStr=menuIdStr+menuId;
			if(i!=checkedNodes.length-1){
				menuIdStr=menuIdStr+",";
			}
		}
	}
	$.ajax({
        "type": 'post',
        "url": rolePath+"/setRoleMenu",
        "dataType": "json",
        "async" : false,
        "data":{"roleId":roleId,"menuIdStr":menuIdStr},
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