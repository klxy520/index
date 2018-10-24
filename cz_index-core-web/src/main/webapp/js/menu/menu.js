var menuPath = contextPath + "/menu";
var menuTree;
var menuListDataTable;
var setting = {
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
				url : "url"
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
	initMenu();
	initDataTable();
});


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
	menuTree.selectNode(menuTree.getNodes()[0]);
}
/**
 * 点击节点事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    //console.log(treeNode.tId + ", " + treeNode.name);
    $("#parentId").val(treeNode.id);
    //刷新子菜单
    menuListDataTable.fnFilter();
};

function initDataTable(){
	// 初始化dataTable
	var menuListTable=$("#menuList");
	if(menuListTable && menuListTable.length>0){
		menuListDataTable =menuListTable.dataTable({
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
		    "sAjaxSource": menuPath+'/list',
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
		        "mData": "sn",
		        'sClass': 'center',
		        'sName':"sn"
		    },
		    {
		        "mData": "targetUrl",
		        'sClass': 'center',
		        'sName':"targetUrl"
		    },
		    {
		        "mData": "description",
		        'sClass': 'center',
		        'sName':"description"
		    },
		    {
		        "mData": "iconUrl",
		        'sClass': 'center',
		        'sName':"iconUrl",
		        'mRender':function(data,type,full){
		        	console.log(data);
		        	if(data && data!=null){
		        		return '<i class="Hui-iconfont">'+data+'</i>'
		        	}else{
		        		return "";
		        	}
		        }
		        	
		    },
		    {
		        "mData": "showIndex",
		        'sClass': 'center',
		        'sName':"showIndex"
		    },
		    {
		        "mData": "type",
		        'sClass': 'center',
		        'sName':"type",
		        'mRender':function(data,type,full){ 
		        	if(data=='0'){
		        		return '菜单';
		        	}else if(data=='1'){
		        		return '按钮';
		        	}
	        	}
		    },
		    {
		        "mData": "method",
		        'sClass': 'center',
		        'sName':"method",
		        'mRender':function(data,type,full){ 
		        	if(data=='0'){
		        		return 'GET';
		        	}else if(data=='1'){
		        		return 'POST';
		        	}else if(data=='2'){
		        		return 'PUT';
		        	}else if(data=='3'){
		        		return 'DELETE';
		        	}else{
		        		return '';
		        	}
	        	}
		    },
		    {
		        "mData": "status",
		        'sClass': 'center',
		        'sName':"status",
		        'mRender':function(data,type,full){ 
		        	if(data=='0'){
		        		return '启用';
		        	}else if(data=='1'){
		        		return '禁用';
		        	}else{
		        		return '';
		        	}
	        	}
		    },
		    {
		        "mData": "defaultOpen",
		        'sClass': 'center',
		        'sName':"defaultOpen",
		        'mRender':function(data,type,full){ 
		        	if(data=='0'){
		        		return '否';
		        	}else if(data=='1'){
		        		return '是';
		        	}
	        	}
		    }
		    ], 
		    "fnDrawCallback": function( settings ) {
				checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	// 获取父菜单id
		    	var parentId = $('#parentId').val();
		    	// 条件查询:
		    	aoData.push(
	                    { "name": "filter_parentId_EQ", "value": parentId})
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
 * 添加菜单
 */
function addMenu(){
	var selectedNodes =  menuTree.getSelectedNodes();
	if(!selectedNodes || selectedNodes.length!=1){
		layer.alert("请选择一个菜单节点");
		return;
	}
	var parentMenu = selectedNodes[0];
	//清空添加表单中输入框内容
    $("#addMenuForm :input").val("");
	// 设置父类菜单
	$("#addMenu").find("#parentId").val(parentMenu.id);
	popupForHtml(parentMenu.name+'->添加菜单',$('#addMenu'),'70%','60%');
	var addMenuForm = $("#addMenuForm");
	if(addMenuForm && addMenuForm.length>0){
		addMenuForm.Validform({
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
					layer.alert('添加菜单成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						menuListDataTable.fnFilter();
						// 刷新菜单树
						initMenu();
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addMenuForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}

/**
 * 编辑菜单
 */
function updateMenu(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个菜单");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个菜单");
		return;
	}
	
	//获取选中菜单
	var menu =getCheckedObject(menuListDataTable);
	//设置值
	setValueForUpdate(menu);
	
	popupForHtml('编辑菜单',$('#updateMenu'),'70%','60%');
	var updateMenuForm = $("#updateMenuForm");
	if(updateMenuForm && updateMenuForm.length>0){
		updateMenuForm.Validform({
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
					layer.alert('修改菜单成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						menuListDataTable.fnFilter();
						// 刷新菜单树
						initMenu();
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
 *编辑页面设置值
 * @param menu
 */
function setValueForUpdate(menu){
	$("#updateMenu").find("#id").val(menu.id);
	$("#updateMenu").find("#parentId").val(menu.parentId);
	$("#updateMenu").find("#name").val(menu.name);
	$("#updateMenu").find("#type").val(menu.type);
	$("#updateMenu").find("#status").val(menu.status);
	$("#updateMenu").find("#iconUrl").val(menu.iconUrl);
	$("#updateMenu").find("#showIndex").val(menu.showIndex);
	$("#updateMenu").find("#method").val(menu.method);
	$("#updateMenu").find("#targetUrl").val(menu.targetUrl);
	$("#updateMenu").find("#description").val(menu.description);
	$("#updateMenu").find("#defaultOpen").val(menu.defaultOpen);
	// bug:积分商城-173
	textarealength($("#updateMenu").find("#description"),200);
}