var userPath = contextPath +"/user";
var taskItemPath=contextPath +"/notice";
var userListDataTable;


$(function(){
	runajax();
	//setInterval(runajax,60000);
});

function runajax(){  
	$.ajax({
		url:taskItemPath+"/getMyNoticeList",
		data:{},
        type:"post",
        dataType:"json",
        success:function(data){ 
        	var list=data.data;
        	var len=list.length;
        	if(len==0){
        		$("#taskCount").empty();
        		$("#taskItems").empty();
        		$("#taskItems").append('<li>'+'欢迎使用健康崇州-卡管平台'+'</li>');
        	}else{
        		var str=""
        		for(var i=0;i<len;i++){
        			if(list[i].title.length>20)
        				str+="<li onclick='readNotice("+list[i].id+")'>"+list[i].title.substring(0,15)+"...</li>"
        			else
        				str+="<li onclick='readNotice("+list[i].id+")'>"+list[i].title+"</li>"

        		}
        		$("#taskItems").empty();
        		$("#taskItems").append(str);
        		$("#taskCount").text("公告:"+len+"条"); 
        	}     	     	
        },
        error:function(){
        	//layer.alert("服务访问失败")
        }
    });  
} 

$(document).ready(function(){
	$('.list_lh li:even').addClass('lieven');
})
$(function(){
	$(".list_lh").easysroll({
		direction: "top",  //滚动方向 left（向左）right（向右） top(向上) bottom(向下) 默认left
		numberr: "1", //每一次滚动数量 默认是1
		delays:"1500",//完成一次动画所需时间 默认是1000等于1秒
		scrolling: "3000",//每一次动画的时间间隔 默认是1000等于1秒
		fadein:false,  //是否支持淡入或淡出 默认false
		enterStop:true//鼠标移入是否暂停滚动 默认是true
		})
}); 
function readNotice(id){
	$.ajax({
		url:taskItemPath+"/readMyNoticeDetail",
		data:{"nid":id},
        type:"post",
        dataType:"json",
        success:function(data){ 
        	if (data.data==null){
        		layer.alert("该公告已被管理员删除");
        		return		
        	}
        	var notice=data.data;
        	$("#readNotice").find("#title").text(notice.title);
			$("#readNotice").find("#content").text(notice.content);
			$("#readNotice").find("#creatorName").text(notice.creatorUser.name);
			$("#readNotice").find("#createDate").text(notice.createDate.substring(0,10));
			popupForHtml('公告查看', $('#readNotice'), '40%', '55%');       
        },
        error:function(){
        //	layer.alert("服务访问失败")
        }
    });  
}
$(document).ready(function(){
	// 初始化
	$(".menu_dropdown dl dd ul li a").click(function(){
		$(this).parents(".menu_dropdown").find('a').removeClass("checkA");
		$(this).addClass("checkA");
	})
	firstLanding();
});


/**
 *查询用户是否第一次登录或者两个月没有修改密码
 */
function firstLanding(){
	var userId = $("#userId").val();
	//获取用户
	var systemUser = getUser(userId);
	var id = systemUser.systemUserAuth.id;
	$.ajax({
		async : false,  //同步请求，请将此选项设置为false。注意，同步请求将锁住浏览器，用户其他操作必须等待请求完成才可以执行。
		url : contextPath + "/user/firstLanding",
		type : 'post',
		cache : false,
		data : {"id":id},
		dataType : 'JSON',
		success : function(data){
			if(data.status == 1){
				$("#passwordChange").find("#systemUserAuthId").val(id);
				$("#passwordChange").find("#loginName").text(data.data.loginName);
				$("#passwordChangeName").text("第一次登录,请修改密码");
				layer.open({
					type: 1,
					title: '重置密码',
					area: ['35%', '55%'], //定义层的宽高
					maxmin:false,   // 去除右上角放大,缩小按钮
					zIndex:1050,
					closeBtn: false,// 去除关闭按钮
					content:$('#passwordChange')
				});
			}
			if(data.status == 2){
				$("#passwordChange").find("#systemUserAuthId").val(id);
				$("#passwordChange").find("#loginName").text(data.data.loginName);
				$("#passwordChangeName").text(data.message+"天以上未修改密码,请更改密码");
				layer.open({
					type: 1,
					title: '重置密码',
					area: ['35%', '55%'], //定义层的宽高
					maxmin:false,   // 去除右上角放大,缩小按钮
					zIndex:1050,
					closeBtn: false,// 去除关闭按钮
					content:$('#passwordChange')
				});
			}
			passwordChange();
		}
	});
}

function passwordChange(){
	var passwordChangeForm = $("#passwordChangeForm");
	if(passwordChangeForm && passwordChangeForm.length>0){
		passwordChangeForm.Validform({
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
				var newPassword = $("#newPassword").val();
				var confirmPassword = $("#confirmPassword").val();
				if(newPassword != confirmPassword){
					layer.alert("密码不一致请重新输入");
					return false;
				}
			},
			callback:function(data){
				if(data.responseData.status==0){
					layer.alert('修改成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
					    layer.closeAll();
					});    
				}else{
					layer.alert(data.responseData.message);
				}
			}
		});
	}
}

/**
 * 编辑用户
 */
function updateUser(id){
	
	//获取用户
	var user =getUser(id);
	//设置值
	setValueForUpdate(user);
	
	popupForHtml('编辑用户',$('#updateUser'),'60%','35%');
	var updateUserForm = $("#updateUserForm");
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
					layer.alert('修改成功', {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
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
	$("#updateUser").find("#id").val(user.id);
	$("#updateUser").find("#name").val(user.name);
	$("#updateUser").find("#phone").val(user.phone);
	$("#updateUser").find("#email").val(user.email);
	$("#updateUser").find("#systemUserAuthId").val(user.systemUserAuth.id);
	$("#updateUser").find("#loginName").val(user.systemUserAuth.loginName);
}

//ajax方法执行  

