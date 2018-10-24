var personListPath = contextPath + "/person";
var personTable;
/**
 * 初始化主索引基本身份信息列表
 */
$(document).ready(function(){
	iniList();
	$("#deceasedInd").change(function(){
		var flag=$(this).val();
		if(flag==1){
			$("#deceasedTimediv").show();
			$("#addPerson").find("#deceasedTime").removeAttr("ignore");
		}else{
			$("#deceasedTimediv").hide();
			$("#addPerson").find("#deceasedTime").val('');
			$("#addPerson").find("#deceasedTime").attr("ignore","ignore");

		}
		
	});
	$("#updatedeceasedInd").change(function(){
		var flag=$(this).val();
		if(flag==1){
			$("#deceasedTimediv1").show();
			$("#updatedeceasedTime").removeAttr("ignore");
		}else{
			$("#deceasedTimediv1").hide();
			$("#updatedeceasedTime").val('');
			$("#updatedeceasedTime").attr("ignore","ignore");
		}
		
	});
});
function iniList(){
	var personList=$("#personList");
	if(personList && personList.length>0){
		personTable = personList.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate": true,
		    "bFilter": false,
		    "bLengthChange": true,
		    "iDisplayLength":15,
		    "bSort": true,
		    "aaSorting" : [[9,"desc"] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,1,2,3,4,5,7]}],
		    "aLengthMenu": [[15,30,50,100], [15,30,50,100]],// 定义每页显示数据数量
		    "bInfo": true,
		    "bWidth": true,
		    "bScrollCollapse": true,
		    "sPaginationType": "full_numbers",
		    "bProcessing": true,
		    "bServerSide": true,
		    "bDestroy": true,
		    "bSortCellsTop": true,
		    "bStateSave":false,// true状态保持 刷新页面保持页码等数据,false:反之
		    "asStripeClasses":['text-c odd','text-c even'],
		    "sAjaxSource": personListPath+'/list',
		    "aoColumns": [
		     {
		        "mData": 'id',
		        'sClass': 'center',
		        'sName':"id",
		        'mRender':function(data,type,full){ 
		        	return '<input type="checkbox" name="goodCheckbox" id="checkbox_id_'+data+'" class="checkbox_id" value="'+data+'">'
		        }
		     },
		     {
			     "mData": 'mpiId',
			     'sClass': 'center',
			     'sName':"mpiId",
			    "render": function (data,type,full) {
					       return '<a class="particulars" onclick="queryPersonDetails('+full.id+')" href="javascript:;">'+data+'</a>';
			    } 
			   },
			   {
				   "mData": 'personName',
				   'sClass': 'center',
				   'sName':"personName"
			   },
			   {
				   "mData": "sexCode",
				   'sClass': 'center',
				   'sName':"sexCode",
				   "render": function (data,type,full) {
					   return fn_getDictByKeyCode("indexSex",data+'') ;
				   } 
			   },
			    {
			    	"mData": "idCard",
			    	'sClass': 'center',
			    	'sName':"idCard"
			    },
			     {
				     "mData": 'cardNo',
				     'sClass': 'center',
				     'sName':"cardNo"
			     },
		    {
		    	"mData": "birthday",
		    	'sClass': 'center',
		    	'sName':"birthday",
		    	"render": function (data,type,full) {
		    		if(data!=null&&data!=""){
		    			return data.substring(0,10) ;
		    		}else{
		    			return null;
		    		}
		    	} 
		    },
		    {
		    	"mData": "status",
		    	'sClass': 'center',
		    	'sName':"status",
		    	 "render": function (data,type,full) {
					   return fn_getDictByKeyCode("indexIdentityStatus",data+'') ;
				   } 
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
		        //绑定 tr  事件 点击checkbox更容易
		    	checkedOfClickTr(".checkbox_id");
		    },
		    "fnServerData": function(sSource, aoData, fnCallback) {
		    	aoData.push(
	                { "name": "filter_personName_EQ", "value": $('#indexName').val() },
	                { "name": "filter_idCard_EQ", "value": $('#indexIdCard').val() },
	                {"name": "filter_cardNo_EQ", "value": $('#indexCardNo').val() },
	                {"name": "filter_sexCode_EQ", "value": $('#indexSex').val() },
	                { "name": "filter_status_EQ", "value": $("#indexIdentityStatus").val() },
		            { "name": "filter_maxUpateDate_LEQ", "value": $('#maxUpateDate').val()+' 23:59:59'},   
		            { "name": "filter_minUpateDate_GEQ", "value": $("#minUpateDate").val()+' 00:00:00'}
            ),	
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
 * 主索引基本身份信息列表条件查询
 */
function search(){
	personTable.fnFilter();
}
/**
 * 查询单条详细信息
 */
function queryPersonDetails(id){
	popup('基本身份信息详情',personListPath+'/queryPersonDetails?id='+id,'880','550');
}
/***
 *获取单个复选框的主索引基本身份信息
 */
function getPerson(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条基本身份信息");
		return false;
	}else if(checkedIds.length>1){
		layer.alert("只能选择一条基本身份信息");
		return false;
	}
	return getCheckedObject(personTable);
}
/**
 * 根据ID删除基本身份信息
 */
function deletePerson(){
	var person=getPerson()
	if(person!=false){
		layer.confirm("确认删除基本身份信息吗?删除之前先删除关联信息.",function(){
			$.ajax({
				"type" : 'post',
				"url" : personListPath + "/deletePersonById",
				"dataType" : "json",
				"data":{"id":person.id,"name":person.personName,"idCard":person.idCard},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							personTable.fnDraw(true); //刷新当前页面
						    layer.closeAll();
						});
					}else{
						layer.alert(data.message)
					}
				}
			});
		});
	}
}
/**
 * 根据ID注销基本身份信息
 */
function cancelPersonById(){
	var person=getPerson()
	if(person!=false){
		var status=person.status
		if(status=="0"){
		layer.confirm("确认注销基本身份信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : personListPath + "/cancelPersonById",
				"dataType" : "json",
				"data":{"id":person.id,"name":person.personName,"idCard":person.idCard},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							personTable.fnDraw(true); //刷新当前页面
							layer.closeAll();
						});
					}else{
						layer.alert(data.message)
					}
				}
			});
		});
		}else{
			layer.alert("该身份信息已为注销状态")
		}
	}
}
/**
 * 根据ID恢复基本身份信息
 */
function recoveryPersonById(){
	var person=getPerson()
	if(person!=false){
		var status=person.status
		if(status=="1"){
		layer.confirm("确认恢复基本身份信息吗?",function(){
			$.ajax({
				"type" : 'post',
				"url" : personListPath + "/recoveryPersonById",
				"dataType" : "json",
				"data":{"id":person.id,"name":person.personName,"idCard":person.idCard},
				"async" : false,
				"success" : function(data) {
					if (data.status== 0) {
						layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
							personTable.fnDraw(true); //刷新当前页面
							layer.closeAll();
						});
					}else{
						layer.alert(data.message)
					}
				}
			});
		});
	}else{
		layer.alert("该身份信息已为正常状态")
	}
  }
}

/**
 * 添加基本身份信息
 */
function addPerson(){	
	$("#addPerson form input").each(function() {
		$(this).val('');
	});  
	var SelectArr = $("select")
	for (var i = 0; i < SelectArr.length; i++) {
		SelectArr[i].options[0].selected = true;
	}
	$("#addPerson").find("#status").val("0");
	$("#deceasedTimediv").hide();
	popupForHtml('添加基本身份信息',$('#addPerson'),'72%','99%');
	var addPersonForm = $("#addPersonForm");
	if(addPersonForm && addPersonForm.length>0){
		addPersonForm.Validform({
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
			datatype:ResidentVailType,
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
					layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						  // 刷新页面
						personTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    //清空添加表单中输入框内容
					    $("#addPersonForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/***
 * 修改居基本信息()
 */

var person
function updateupdatePersonPage(){
	person=getPerson()
	if(person!=false){
		setupdatePersonFormValue(person)//为编辑表单设置值
		popupForHtml('编辑基本身份信息',$('#updatePerson'),'72%','98%');
	}
	updatePerson();
}
/**
 * 编辑基本身份信息为表单设置值
 */
var person=null;
function setupdatePersonFormValue(person){
	person=person;
	$("#updatePerson").find("#updateid").val(person.id);
	$("#updatePerson").find("#updatepersonName").val(person.personName);
	$("#updatePerson").find("#updatesexCode").val(person.sexCode);
	$("#updatePerson").find("#updateidCard").val(person.idCard);
	$("#updatePerson").find("#updatestatus").val(person.status);
	$("#updatePerson").find("#updatecardNo").val(person.cardNo);
	$("#updatePerson").find("#updatenationalityCode").val(person.nationalityCode);
	var birthday=person.birthday
	if(birthday!=null&& birthday!="" ){
		$("#updatePerson").find("#updatebirthday").val(birthday.substring(0,10));
	}
	$("#updatePerson").find("#updatebloodTypeCode").val(person.bloodTypeCode)
	$("#updatePerson").find("#updateeducationCode").val(person.educationCode);
	$("#updatePerson").find("#updaterhBloodCode").val(person.rhBloodCode);
	$("#updatePerson").find("#updatemaritalStatusCode").val(person.maritalStatusCode);
	$("#updatePerson").find("#updatenationCode").val(person.nationCode);
	$("#updatePerson").find("#updatecontactNo").val(person.contactNo);
	$("#updatePerson").find("#updateregisteredPermanent").val(person.registeredPermanent);
	$("#updatePerson").find("#updateinsuranceCode").val(person.insuranceCode);
	$("#updatePerson").find("#updateinsuranceType").val(person.insuranceType);
	$("#updatePerson").find("#updateworkCode").val(person.workCode);
	$("#updatePerson").find("#updateworkCode").val(person.workCode);
	var startWorkDate=person.startWorkDate
	if(startWorkDate!=null&& startWorkDate!="" ){
		$("#updatePerson").find("#updatestartWorkDate").val(startWorkDate.substring(0,10));
	}
	var deceasedTime=person.deceasedTime
	if(deceasedTime!=null&& deceasedTime!="" ){
		$("#updatePerson").find("#updatedeceasedTime").val(deceasedTime.substring(0,10));
	}
	$("#updatePerson").find("#updatedeceasedInd").val(person.deceasedInd);
	if(person.deceasedInd==1){
		$("#updatedeceasedTime").removeAttr("ignore");
		$("#deceasedTimediv1").show();
	}else{
		$("#updatedeceasedTime").attr("ignore","ignore");
		$("#deceasedTimediv1").hide();
	}
	$("#updatePerson").find("#updateaddress").val(person.address);
	$("#updatePerson").find("#updateworkPlace").val(person.workPlace);
}
/**
 * 修改居民采集信息(提交数据库)
 */
function updatePerson(){
	var updatePersonForm = $("#updatePersonForm");
	if(updatePersonForm && updatePersonForm.length>0){
		updatePersonForm.Validform({
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
			datatype:ResidentVailType,
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
					layer.alert(data.message, {skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
						personTable.fnDraw(true); //刷新当前页面
					    layer.closeAll();
					    $("#updatePersonForm :input").val("");
					});    
				}else{
					layer.alert(data.message);
				}
			}
		});
	}
}
/**
 * 为单个身份信息添加地址
 */
function addAddress(){
	var person=getPerson()
	if(person!=false){
	 popup('地址管理',contextPath + '/address/listPage?id=' + person.id,'1050','510');
	}
}
/**
 * 为单个身份信息添加联系人
 */
function addcontact(){
	var person=getPerson()
	if(person!=false){
		popup('联系人管理',contextPath + '/contact/listPage?id=' + person.id,'1050','510');
	}
}

/**
 * 进入证件管理页面
 */
function manageCertificate(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条基本身份信息");
		return false;
	}
	popup('证件管理',contextPath + '/certificate/manageCertificate?id=' +checkedIds,'1000','510');
}
/**
 * 进入联系方式
 */
function manageContactWay(){
	var checkedIds = getCheckedIds();
	if(!checkedIds || checkedIds.length==0){
		layer.alert("请选择一条基本身份信息");
		return false;
	}
	popup('联系方式管理',contextPath + '/contactWay/manageContactWay?id=' + checkedIds,'1000','510');
	
}
var ResidentVailType={
		"s2-10":/^[\S+]{1,20}$/,//2-10个字符
		"zh2-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/,
		"name":/^[\u4e00-\u9fa5\\]{2,20}$/,//输入2-20个中文字符允许小数点
		"zh4-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{4,20}$/,
		"zh2-50":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,50}$/,
		"zhy2-50":/^[\u4e00-\u9fa5a-zA-Z]{2,50}$/,
		"lm8":/^[a-zA-Z0-9_]{8}$/, // 8位字母+数字
		"n0":/^[0-9a-zA-Z]{8,20}$/, // 字母或数字
		"postCode6":/^[\d+]{6}$/,//6位数字
		"tel":/^1[3|5|8|7|9][0-9]\d{8}$/,
		"sfzh": /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,//身份证号(15位,18位都可以)
		"lxdh":/^[\d+]{8}$/,
		"bank":/^[\d+]{8,20}$/ ,//银行卡号  修复bug595:银行卡字段输入9位数字,无法验证通过
		"socialNum":/^[\d+]{9}$/, //社保卡号
		"newRuralNum":/^[\d+]{8,20}$/, //新农合号
		"bidUtil":/^[\S+]{1,20}$/ ,//申办单位
		"issuingBank": /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/ , //发卡银行
		"issuingSerialNumber":/^[\d+]{10}$/ , //发卡序列号
		"issuersCardCertificate":/^[\S+]{0,20}$/ ,//发卡机构证书
		"chipNum":/^[A-Za-z0-9]{15,20}$/, //芯片号
		"address":/^.{1,50}$/,
		"empty": /^\s*$/,
		"exist_ID_datatype":function(gets,obj,curform,regxp){
		 return queryResidentsInfoExistence(gets,obj,curform,regxp);
		} // 推广
};
/***
 * 根据身份证号查询居民信息是是否存在
 */
function queryResidentsInfoExistence(gets,obj,curform,regxp){
	var check=true;
	if(person==null){
		check=queryResidentsInfoExistenceAXAJ(gets);
	}else{
		idCard=person.idCard
		if(idCard!=gets){
			check=queryResidentsInfoExistenceAXAJ(gets);	
		}
	}
	return check;
}
/***
 * 根据身份证号查询居民信息是是否存在
 */
function queryResidentsInfoExistenceAXAJ(gets){
	var check=true;
		$.ajax({
			"type" : 'get',
			"url" : personListPath + "/queryPersonExistence",
			"dataType" : "json",
			"data":{"idCard":gets},
			"async" : false,
			"success" : function(data) {
				if (data.status==1) {
					check="该身份证号已存在";
				}
			}
		});
	return check;
}