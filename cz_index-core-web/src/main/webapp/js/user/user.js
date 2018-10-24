var userPath = contextPath + "/user";

var userListDataTable;
$(document).ready(function(){
	// 初始化
	init();
});

function init(){
	// 初始化dataTable
	var userListTable=$("#userList");
	if(userListTable && userListTable.length>0){
		userListDataTable =userListTable.dataTable({
		    "oLanguage": { //语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,3,7,8,9] }],
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
		    "sAjaxSource": userPath+'/list',
		    "aoColumns": [{
		        "mData": "id",
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){return '<input type="checkbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'}
		    },
		    {
		        "mData": "name",
		        'sClass': 'center',
		        'sName':"name",
		        'mRender':function(data,type,full){return '<a class="particulars"  onClick="userInfo('+full.id+')">'+data+'</a>'}
		    },
		    {
		        "mData": "sn",
		        'sClass': 'center',
		        'sName':"sn"
		    },
		    {
		        "mData": "areaId",
		        'sClass': 'center',
		        'sName':"areaId",
		        'mRender':function(data,type,full){ 
		        	if(data!=null && data!=0){
		        		return full.administrativeDivision.name;
		        	}else{
		        		return '-';
		        	}
	        	}
		    },
		    {
		        "mData": "organizationId",
		        'sClass': 'center',
		        'sName':"organizationId",
		        'mRender':function(data,type,full){ 
		        	if(data != null && data!=0){
		        		return full.administrativeManagement.administrativeName;
		        	}else{
		        		return '-';
		        	}
	        	}
		    },
		    {
		        "mData": "phone",
		        'sClass': 'center',
		        'sName':"phone"
		    },
		    {
		        "mData": "email",
		        'sClass': 'center',
		        'sName':"email"
		    },
		    {
		        "mData": "userType",
		        'sClass': 'center',
		        'sName':"userType",
		        'mRender':function(data,type,full){ 
		        	if(data=='1'){
		        		return '系统管理员';
		        	}else{
		        		return '机构操作员';
		        	}
	        	}
		    },
		    {
		        "mData": "systemUserAuth.loginName",
		        'sClass': 'center',
		        'sName':"systemUserAuth.loginName"
		    },
		    {
		        "mData": "systemUserAuth.loginDate",
		        'sClass': 'center',
		        'sName':"systemUserAuth.loginDate"
		    },
		    {
		        "mData": "systemUserAuth.status",
		        'sClass': 'center',
		        'sName':"systemUserAuth.status",
		        'mRender':function(data,type,full){ 
		        	if(data=='0'){
		        		return '启用';
		        	}else if(data=='1'){
		        		return '禁用';
		        	}else{
		        		return '';
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
	                    { "name": "filter_name_LIKE", "value": $('#name').val() },//用户名
	                    { "name": "filter_sn_LIKE", "value": $('#sn').val() },//员工工号
	                    { "name": "filter_phone_EQ", "value": $('#phone').val() })//电话
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
		userListTable.fnFilter();
	});
}

/**
 * 添加用户
 */
function addUser(){
	popupForHtml('添加用户',$('#addUser'),'60%','55%');
	$("#addUserForm :input").val("");// 清空输入框中的内容
	getOrg('addUser',2,1);//获取二级机构
	checkType($('#addUser').find("#userType").val());
	getDivision(0);
	var addUserForm = $("#addUserForm");
	if(addUserForm && addUserForm.length>0){
		addUserForm.Validform({
			btnSubmit:"#form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:true,
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
					layer.alert('添加用户成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						userListDataTable.fnFilter();
					    layer.closeAll();
					    $("#addUserForm :input").val("");//核心
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
function updateUser(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择用户");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个用户");
		return;
	}
	
	//获取用户
	var user =getUser(checkedIds[0]);
	//设置值
	setValueForUpdate(user);
	
	popupForHtml('编辑用户',$('#updateUser'),'60%','65%');
	var updateUserForm = $("#updateUserForm");
	if(updateUserForm && updateUserForm.length>0){
		updateUserForm.Validform({
			btnSubmit:"#form_submit", 
			btnReset:"",
			tiptype:function(msg,o,cssctl){validformTiptype(msg,o,cssctl)},
			ignoreHidden:true,
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
					layer.alert('修改用户成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						userListDataTable.fnFilter();
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
 * 重置用户密码
 */
function resetPassword(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择用户");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个用户");
		return;
	}
	//获取用户
	var user = getUser(checkedIds[0]);
	//设置值
	setResetPassword(user);
	var resetPasswordForm = $("#resetPasswordForm");
	popupForHtml('重置密码',$('#resetPassword'),'35%','50%');
	$('#resetPasswordForm #password').val('');
	if(resetPasswordForm && resetPasswordForm.length>0){
		resetPasswordForm.Validform({
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
					layer.alert('重置密码成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						userListDataTable.fnFilter();
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
 * 重置密码页面设置值
 */
function setResetPassword(user){
	$("#resetPassword").find("#id").val(user.id);
	$("#resetPassword").find("#systemUserAuthId").val(user.systemUserAuth.id);
	$("#resetPassword").find("#name").text(user.name);
	$("#resetPassword").find("#loginName").text(user.systemUserAuth.loginName);
}
/**
 * 展示用户详情
 * @param id
 */
function userInfo(id){
	//获取用户
	var user =getUser(id);
	//设置值
	setValueForInfo(user);
	
	popupForHtml('用户详情',$('#userInfo'),'65%','70%');
}
/**
 * 启用用户
 */
function setUserEnable(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择用户");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个用户");
		return;
	}
	var data = getCheckedObject(userListDataTable);
	if(data && data.systemUserAuth.status=='1'){
		$.ajax({
	        "type": 'post',
	        "url": userPath+"/setUserEnable",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	        	'systemUserAuthId': data.systemUserAuth.id
	        },
	        "success": function(data) {
	        	if(data.status==0){
					layer.alert('启用用户成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						userListDataTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	}else{
		layer.alert("用户已启用");
		return;
	}
	
}
/**
 * 禁用用户
 */
function setUserDisable(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择用户");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个用户");
		return;
	}
	var data = getCheckedObject(userListDataTable);
	if(data && data.systemUserAuth.status=='0'){
		$.ajax({
	        "type": 'post',
	        "url": userPath+"/setUserDisable",
	        "dataType": "json",
	        "async" : false,
	        "data": {
	            'systemUserAuthId': data.systemUserAuth.id
	        },
	        "success": function(data) {
	        	if(data.status==0){
					layer.alert('禁用用户成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						userListDataTable.fnFilter();
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.message);
				} 
	        }
	    });
	}else{
		layer.alert("用户已禁用");
		return;
	}
	
}
/**
 * 获取用户
 * @param id
 * @returns
 */
function getUser(id){
	var user = null;
	$.ajax({
        "type": 'post',
        "url": userPath+"/get",
        "dataType": "json",
        "async" : false,
        "data": {
            'id': id
        },
        "success": function(data) {
        	if(data.status==0){
        		user = data.data;
			}else{
				layer.alert(data.message);
			}   
        }
    });
	return user;
}
/**
 *编辑页面设置值
 * @param user
 */
function setValueForUpdate(user){
	var areaId = user.areaId;
	var organizationId = user.organizationId;
	$("#updateUser").find("#id").val(user.id);
	$("#updateUser").find("#name").val(user.name);
	$("#updateUser").find("#sn").attr("placeholder",user.sn);
	$("#updateUser").find("#phone").val(user.phone);
	$("#updateUser").find("#email").val(user.email);
	$("#updateUser").find("#systemUserAuthId").val(user.systemUserAuth.id);
	$("#updateUser").find("#loginName").attr("placeholder",user.systemUserAuth.loginName);
	$("#updateUser").find("#userType").empty().append($('<option value="'+user.userType+'">'+(user.userType=="1"?"系统管理员":"机构操作员")+'</option>'));
	$("#updateUser").find("#areaId").val(areaId);
	$("#updateUser").find("#organizationId").val(organizationId);
	if(areaId!=null&&areaId>=1){
		if(user.userType != "1"){// 用户类型是机构操作员,才显示所属商户
			var parentOrgId = user.administrativeDivision.parentid;
			var orgId = user.areaId;
			getAdminDiv(orgId);
			$("#updateUser").find("#div_organizationId").show();
			$("#updateUser").find("#div_organizationType").hide();
		}else{
			$("#updateUser").find("#div_organizationId").hide();
			$("#updateUser").find("#div_organizationType").hide();
		}
	}else if(organizationId!=null&&organizationId>0){
		if(user.userType != "1"){// 用户类型是机构操作员,才显示所属商户
			getDivision(organizationId);
			$("#updateUser").find("#div_organizationType").show();
			$("#updateUser").find("#div_organizationId").hide();
		}else{
			$("#updateUser").find("#div_organizationType").hide();
			$("#updateUser").find("#div_organizationId").hide();
		}
	}else{
		$("#updateUser").find("#div_organizationType").hide();
		$("#updateUser").find("#div_organizationId").hide();
	}
}

/**
 *详情页面设置值
 * @param user
 */
function setValueForInfo(user){
	$("#userInfo").find("#name").text(user.name);
	$("#userInfo").find("#sn").text(user.sn);
	$("#userInfo").find("#phone").text(user.phone);
	$("#userInfo").find("#email").text(user.email);
	$("#userInfo").find("#systemUserAuthId").text(user.systemUserAuth.id);
	$("#userInfo").find("#loginName").text(user.systemUserAuth.loginName);
	$("#userInfo").find("#userType").text(user.userType=="1"?"系统管理员":"机构操作员");
	$("#userInfo").find("#systemUserAuthLoginDate").text(user.systemUserAuth.loginDate!=null?user.systemUserAuth.loginDate:"");
	$("#userInfo").find("#systemUserAuthCreateDate").text(user.systemUserAuth.createDate!=null?user.systemUserAuth.createDate:"");
	$("#userInfo").find("#systemUserAuthStatus").text(user.systemUserAuth.status=="0"?"启用":"禁用");
	$("#userInfo").find("#systemUserAuthUpdatePassDate").text(user.systemUserAuth.updatePassDate!=null?user.systemUserAuth.updatePassDate:"");
	if(user.userType!="1"){  // 用户类型是机构操作员,才显示所属商户
		if(user.organizationId==null||user.organizationId==0){
			$("#userInfo").find("#administrativeManagement").parent().parent().show();
			$("#userInfo").find("#organizationName").parent().parent().hide();
			$("#userInfo").find("#administrativeManagement").text(user.administrativeDivision.name);
		}
		if(user.areaId==null||user.areaId==0){
			$("#userInfo").find("#organizationName").parent().parent().show();
			$("#userInfo").find("#administrativeManagement").parent().parent().hide();
			$("#userInfo").find("#organizationName").text(user.administrativeManagement.administrativeName);
		}
	}else{
		$("#userInfo").find("#organizationName").parent().parent().hide();
		$("#userInfo").find("#administrativeManagement").parent().parent().hide();
	}
}

/**
 * 用户授权
 */
function setUserRoleLayer(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择用户");
		return;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一个用户");
		return;
	}
	var user = getCheckedObject(userListDataTable);
	//获取所有角色并append到页面
	getAllRole();
	// 获取用户角色并选中
	getUsrRole(user.systemUserAuth.id);
	popupForHtml('用户授权',$('#userRole'),'70%','60%');
}

/**
 * 获取所有角色并append到页面
 */
function getAllRole(){
	var checkUser = getAllCheckedObject(userListDataTable)[0];    //获取选中的用户记录
	var userType = checkUser.userType;      //用户类型（1：系统管理员；2：机构操作员）
	var roleTbody = $("#userRole").find("tbody");
	// 清空
	roleTbody.children().remove();
	$.ajax({
        "type": 'post',
        "url": contextPath+"/role/allList",
        "dataType": "json",
        "async" : false,
        "data":null,
        "success": function(data) {
        	if(data.status==0){
        		var roleList = data.data;
        		for(var i=0;i<roleList.length;i++){
        			var role = roleList[i];
        			if (userType == 1) {   		//系统管理员，获取所有的角色
        				roleTbody.append('<tr class="text-c"><td><input id="roleId_'+role.id+'" class="roleId" type="checkbox" value="'+role.id+'"></td><td>'+role.name
        						+'</td><td>'+role.mark+'</td><td>'+role.description+'</td></tr>');
					}else if (userType == 2 && role.mark.indexOf("ROLE_SUP") == -1 && role.mark.indexOf("ROLE_ADMIN") == -1) {   //机构操作员，只获取操作员的角色
						roleTbody.append('<tr class="text-c"><td><input id="roleId_'+role.id+'" class="roleId" type="checkbox" value="'+role.id+'"></td><td>'+role.name
        						+'</td><td>'+role.mark+'</td><td>'+role.description+'</td></tr>');
					}
        		}
			}else{
				layer.alert(data.message);
			}   
        }
    });
}

/**
 * 获取用户角色并选中
 * @param authId
 */
function getUsrRole(authId){
	$("#userRole").find("#authId").val(authId);
	$.ajax({
        "type": 'post',
        "url": userPath+"/getUsrRole",
        "dataType": "json",
        "async" : false,
        "data":{"systemUserAuthId":authId},
        "success": function(data) {
        	if(data.status==0){
        		var userRoleList = data.data;
        		for(var i=0;i<userRoleList.length;i++){
        			var userRole = userRoleList[i];
        			$("#roleId_"+userRole.roleId).prop("checked",true);
        		}
			}else{
				layer.alert(data.message);
			}   
        }
    });
}


/**
 * 获取用户角色并选中
 * @param authId
 */
function setUserRole(){
	var authId = $("#userRole").find("#authId").val();
	var checkedRoleIds = $("#userRole").find(".roleId:checked");
	var roleIdStr = "";
	if(checkedRoleIds && checkedRoleIds.length>0){
		for(var i=0;i<checkedRoleIds.length;i++){
			var roleId = $(checkedRoleIds[i]).val();
			roleIdStr=roleIdStr+roleId;
			if(i!=checkedRoleIds.length-1){
				roleIdStr=roleIdStr+",";
			}
		}
	}
	$.ajax({
        "type": 'post',
        "url": userPath+"/setUsrRole",
        "dataType": "json",
        "async" : false,
        "data":{"systemUserAuthId":authId,"roleIdStr":roleIdStr},
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


/**
 * 根据用户类型,决定所属商户的显示和隐藏
 * @param str
 */
function checkType(str){
	if(str == 1){
		$("#div_organizationId").hide();
		$("#div_organizationType").hide();
		$(".adminType").hide();
		$("#div_organizationId").find("#areaId").val(0);
	}else{
		$("#div_organizationId").show();
		$(".adminType").show();
		$("#div_organizationId").find("#areaId").val(1);
	}
}

/**
 * 
 * 描述：切换行政机构和区域机构   显示与隐藏
 * @param str
 * @author wangdeyou 2017年9月5日 下午2:55:58 
 * @version 1.0
 */
function organizationType(str){
	if(str == 1){
		$("#div_organizationId").show();
		$("#div_organizationType").hide();
		$("#div_organizationId").find("#areaId").val(1);
		$("#div_organizationType").find("#organizationId").val(" ");
	}else{
		$("#div_organizationType").show();
		$("#div_organizationId").hide();
		$("#div_organizationId").find("#areaId").val(0);
	}
}

/**
 * 获取机构并选中
 * @param level
 * @param parentOrgId
 * @param selectedId
 */
function getOrg(tag,level,parentOrgId){
	var orgTag = $('#'+tag).find("#areaId_"+level);
	$.ajax({
        "type": 'post',
        "url": contextPath+"/administrativeDivision/getAdministrativeDivision",
        "dataType": "json",
        "async" : false,
        "data":{"id":parentOrgId},
        "success": function(data) {
        	if(data.status==0){
        		orgTag.empty();
        		var $option =$('<option value="" selected="selected">请选择区域机构</option>');
        		orgTag.append($option);
        		var orgList = data.data;
        		for(var i=0; i<orgList.length; i++){
    				$option = $('<option value="'+orgList[i].id+'">'+orgList[i].name+'</option>');
        			orgTag.append($option);
        		} 
			}else{
				layer.alert(data.message);
			}   
        }
    });
}

/**
 * 
 * 描述：查询行政机构
 * @param id
 * @author wangdeyou 2017年9月6日 下午2:38:51 
 * @version 1.0
 */
function getDivision(id){
	var $option;
	var administrativeDivisionList;
	if(id==0){
		administrativeDivisionList = $("#addUser").find("#organizationId");
	}else{
		administrativeDivisionList = $("#updateUser").find("#organizationId");
	}
	$.ajax({
        "type": 'post',
        "url": contextPath+"/administrativeDivision/getManagement",
        "dataType": "json",
        "async" : false,
        "data":{"id":id},
        "success": function(data) {
        	if(data.responseData.status==0){
        		administrativeDivisionList.empty();
        		if(id==0){
        			$option =$('<option value="" selected="selected">请选择行政机构</option>');
        			administrativeDivisionList.append($option);
        		}
        		var orgList = data.responseData.data;
        		for(var i=0; i<orgList.length; i++){
    				$option = $('<option value="'+orgList[i].id+'">'+orgList[i].administrativeName+'</option>');
    				administrativeDivisionList.append($option);
        		} 
			}else{
				layer.alert(data.message);
			}   
        }
    });
}

/**
 * 切换机构
 * @param level
 * @param orgId
 */
function changeOrg(tag,level,orgId){
	$('#'+tag).find("#areaId").val(orgId);
	// 获取三级银行机构
	if(level==2){
		if(orgId==0){
			// 没有选择任何一个二级机构
			$('#'+tag).find("#areaId").val(1);
			$('#'+tag).find("#areaId_"+3).empty();
		}else{
			getOrg(tag,3,orgId);
		}
	}else if(level==3){
		if(orgId==0){
			// 没有选择任何一个三级机构
			var selectedOrgId= $('#'+tag).find("#areaId_"+2).val();
			$('#'+tag).find("#areaId").val(selectedOrgId);
		}else{
		}
	}
}


/**
 * 
 * 描述：通过ID查询区域机构
 * @param id
 * @author wangdeyou 2017年9月6日 下午4:05:07 
 * @version 1.0
 */
function getAdminDiv(id){
	var $areaId_2;
	var $areaId_3;
	var orgTag2 = $('#updateUser').find("#areaId_2");
	var orgTag3 = $('#updateUser').find("#areaId_3");
	$.ajax({
        "type": 'post',
        "url": contextPath+"/administrativeDivision/getAdminDiv",
        "dataType": "json",
        "async" : false,
        "data":{"id":id},
        "success": function(data) {
        	if(data.responseData.status==0){
        		orgTag2.empty();
        		orgTag3.empty();
        		var orgList = data.responseData.data;
        		if(orgList.length == 1){
        			$areaId_2 = $('<option value="'+orgList[0].id+'">'+orgList[0].name+'</option>');
            		orgTag2.append($areaId_2);
        		}
        		if(orgList.length == 2){
        			$areaId_2 = $('<option value="'+orgList[0].id+'">'+orgList[0].name+'</option>');
        			$areaId_3 = $('<option value="'+orgList[1].id+'">'+orgList[1].name+'</option>');
        			orgTag2.append($areaId_3);
        			orgTag3.append($areaId_2);
        		}
			}else{
				layer.alert(data.responseData.message);
			}   
        }
    });
}
