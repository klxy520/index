/**
 * 
 */
var dictionaryListDataTable;
var dictionaryTree
var setting = {
		view: {
			dblClickExpand: false,
			showLine: false,
			selectedMulti: false
		},
		data: {
			key : {
				checked : "checked",
				children : "subDicitonaryList",
				name : "name",
				title : "description",
				url : "url"
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
	initDictionary();
	initDataTable();
});

function initDictionary(){
	$.ajax({
		"type":"POST",
		"url":contextPath + "/dictionary/topDictionary",
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
	var topDictionary = $("#topDictionary");
	dictionaryTree = $.fn.zTree.init(topDictionary, setting, zNodes);
	dictionaryTree.selectNode(dictionaryTree.getNodes()[0]);
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
    //刷新子字典
    dictionaryListDataTable.fnFilter();
};

function initDataTable(){
	// 初始化dataTable
	var dictionaryListTable=$("#dictionaryList");
	if(dictionaryListTable && dictionaryListTable.length>0){
		dictionaryListDataTable = dictionaryListTable.dataTable({
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
		    "sAjaxSource": contextPath+'/dictionary/list',
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
		        "mData": "dictkey",
		        'sClass': 'center',
		        'sName':"dictkey"
		    },
		    {
		        "mData": "value",
		        'sClass': 'center',
		        'sName':"value"
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
			        if(data=='1'){
			        	return '启用';
			        }else if(data=='0'){
			        	return '禁用';
			        }else{
			        	return '';
			        }
		        }
		    },
		    {
		        "mData": "isdefault",
		        'sClass': 'center',
		        'sName':"isdefault",
		        'mRender':function(data,type,full){ 
				    if(data=='1'){
				        return '是';
				    }else if(data=='0'){
				        return '否';
				    }else{
				        return '';
				    }
			    }
		    },
		    {
		    	"mData": "showindex",
		        'sClass': 'center',
		        'sName':"showindex"
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
	                    { "name": "filter_parentid_EQ", "value": parentId})
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
 * 添加字典
 */
function addDictionary(){
	var selectedNodes = dictionaryTree.getSelectedNodes();
	if(!selectedNodes || selectedNodes.length!=1){
		layer.alert("请选择一个字典节点");
		return;
	}
	var parentDictionary = selectedNodes[0];
	$("#addDictionaryForm :input").val("");
	//获取父类字典
	$("#addDictionary").find("#parentId").val(parentDictionary.id);
	dynamicAddition(parentDictionary);
	popupForHtml(parentDictionary.name+'->添加字典',$('#addDictionary'),'70%','50%');
	var addDictionaryForm = $("#addDictionaryForm");
	if(addDictionaryForm && addDictionaryForm.length>0){
		addDictionaryForm.Validform({
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
					layer.alert('添加字典成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						dictionaryListDataTable.fnFilter();
						// 刷新字典树
						initDictionary();
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addDictionaryForm :input").val("");
					});    
				}else if(data.status==2){
					layer.alert(data.message);
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}

/**
 * 编辑字典
 */
function updateDictionary(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个字典");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个字典");
		return;
	}
	
	//获取选中字典
	var dictionary =getCheckedObject(dictionaryListDataTable);
	//设置值
	setValueForUpdate(dictionary);
	
	popupForHtml('编辑字典',$('#updateDictionary'),'70%','50%');
	var updateDictionaryForm = $("#updateDictionaryForm");
	if(updateDictionaryForm && updateDictionaryForm.length>0){
		updateDictionaryForm.Validform({
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
					layer.alert('修改字典成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						dictionaryListDataTable.fnFilter();
						// 刷新字典树
						initDictionary();
					    layer.closeAll();
					});    
				}else if(data.status==2){
					layer.alert(data.message);
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}

function setValueForUpdate(dictionary){
	$("#updateDictionary").find("#id").val(dictionary.id);
	$("#updateDictionary").find("#parentId").val(dictionary.parentid);
	$("#updateDictionary").find("#name").val(dictionary.name);
	$("#updateDictionary").find("#dictkey").val(dictionary.dictkey);
	$("#updateDictionary").find("#value").val(dictionary.value);
	$("#updateDictionary").find("#description").val(dictionary.description);
	$("#updateDictionary").find("#showindex").val(dictionary.showindex);
}

/**
 * 启用用户
 */
function enableDictionary(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个字典");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个字典");
		return;
	}
	var data = getCheckedObject(dictionaryListDataTable);
	if(data && data.status == "0"){
		$.ajax({
	        "type": 'post',
	        "url": contextPath+"/dictionary/setDictionaryEnable",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'id': data.id
	        },
	        "success": function(data) {
	        	if(data.responseData.status==0){
					layer.alert('启用字典成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						dictionaryListDataTable.fnFilter();
						// 刷新字典树
						initDictionary();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	}else{
		layer.alert("字典已启用");
		return;
	}
}

/**
 * 禁用字典
 */
function disableDictionary(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一个字典");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个字典");
		return;
	}
	var data = getCheckedObject(dictionaryListDataTable);
	if(data && data.status == "1"){
		$.ajax({
	        "type": 'post',
	        "url": contextPath+"/dictionary/disableDictionary",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'id': data.id
	        },
	        "success": function(data) {
	        	if(data.responseData.status==0){
					layer.alert('禁用字典成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						// 刷新页面
						dictionaryListDataTable.fnFilter();
						// 刷新字典树
						initDictionary();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	}else{
		layer.alert("字典已禁用");
		return;
	}
}

/**
 * 动态添加readonly属性
 */
function dynamicAddition(parentDictionary){
	if(parentDictionary.dictkey != null){
		$("#addDictionary").find('input[name=dictkey]').attr("readonly","readonly");
		$("#addDictionary").find("#dictkey").val(parentDictionary.dictkey);
	}else{
		$("#addDictionary").find('input[name=dictkey]').removeAttr("readonly");
	}
}


