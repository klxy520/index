var importResidentPath = contextPath + "/importResident";
var importResidentListTable;
var falg = true;
$(document).ready(function() {
	var excelUrl=$("#excelUrl").val(); 
	importResidentList(excelUrl)
});

/**
 * 解析导入数据(判断数据是否合法)列表
 */
function importResidentList(filePath) {
	var importBaseResidentTable = $("#importExtendResidentTable");
	if (importBaseResidentTable && importBaseResidentTable.length > 0) {
		importResidentListTable = importBaseResidentTable.dataTable({
			"oLanguage": { // 语言国际化
		    	"sUrl": contextPath+"/js/de_DE.txt"
		    },
		    "bPaginate" : false,
			"bFilter" : false,
			"bLengthChange" : false,
			'reset' : true,
			"iDisplayLength" : 15,
			"bSort" : false,
		    "aaSorting" : [ [ 1, "desc" ] ],
		    "aoColumnDefs": [ { "bSortable": false, "aTargets": [0,2,3,4,5,6]}],
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
		    "sAjaxSource": importResidentPath+'/importResidentExtendinfo',
		    "aoColumns": [{
				"mData" : 'healthNumber',
				'sClass' : 'center',
				'sName' : "healthNumber",
				"render" : function(data, type, full) {
					return validateHealthNumber(data);
				}
			},{
				"mData" : "insuranceType",
				'sClass' : 'center',
				'sName' : "insuranceType",
				'mRender' : function(data, type, full) {
					return validateInsuranceType(data);
				}
			}, {
				"mData" : "disabilityType",
				'sClass' : 'center',
				'sName' : "disabilityType",
				'mRender' : function(data, type, full) {
					return  validateDisabilityType(data);
				}
			}, {
				"mData" : "unionFeature",
				'sClass' : 'center',
				'sName' : "unionFeature",
				'mRender' : function(data, type, full) {
					return validateUnionFeature(data);
				}
			}, {
				"mData" : "retiredCadres",
				'sClass' : 'center',
				'sName' : "retiredCadres",
				'mRender' : function(data, type, full) {
					return validateRetiredCadres(data);
				}
			}, {
				"mData" : "helpHouse",
				'sClass' : 'center',
				'sName' : "helpHouse",
				'mRender' : function(data, type, full) {
					return validateHelpHouse(data);
				}
			}, {
				"mData" : "lowType",
				'sClass' : 'center',
				'sName' : "lowType",
				'mRender' : function(data, type, full) {
					return validateLowType(data);
				}
			}, {
				"mData" : "illnessType",
				'sClass' : 'center',
				'sName' : "illnessType",
				'mRender' : function(data, type, full) {
					return validateIllnessType(data);
				}
			} ],
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : 'post',
					"url" : sSource,
					"dataType" : "json",
					"data" : {
						"excelUrl" : filePath
					},
					"success" : function(resp) {
						fnCallback(resp);
					}
				});
			}
		});
	}
}

/***
 * 验证居民健康卡银行卡号是否合法
 * @param realName
 * @returns {String}
 */
function validateHealthNumber(healthNumber) {
	if (healthNumber == null || healthNumber == "") {
		return color("不能为空")
	}
	var length = healthNumber.length
	if (length < 15 || length > 30) {
		return color(healthNumber)
	}
	if (!/^\d+(\.\d+)?$/.test(healthNumber)) {
		return color(healthNumber)
	}
	var  healthNumberPage=queyResidentBasByhealthNumberexist(healthNumber);
	return healthNumberPage;
}
/**
 * 通过居民健康卡号查询居民基本信息是否存在
 * @param healthNumber
 */
function queyResidentBasByhealthNumberexist(healthNumber){
	var healthNumberPage=null;
	$.ajax({
		"type" : 'get',
		"url" : importResidentPath + "/queyResidentBasByexist",
		"dataType" : "json",
		"data" : {
			"healthNumber" : healthNumber
		},
		"async" : false,
		"success" : function(data) {
			if (data.status == 0) {
				healthNumberPage=healthNumber
			}else{
				healthNumberPage=color("不存在")
			}
		}
	});
	return healthNumberPage;
}
/***
 * 验证医保类型是否合法
 * @param insuranceType
 * @returns {String}
 */
function validateInsuranceType(insuranceType) {
	if(insuranceType!=null&&insuranceType!=""){
		if (!/^[\u4e00-\u9fa5a-zA-Z]{2,15}$/.test(insuranceType)) {
			return color(insuranceType)
		}
	}
	return insuranceType;
}
/***
 * 验证残疾类型是否合法
 * @param realName
 * @returns {String}
 */
function validateDisabilityType(disabilityType) {
	if(disabilityType!=null&&disabilityType!=""){
		if (!/^[\u4e00-\u9fa5a-zA-Z]{4,15}$/.test(disabilityType)) {
			return color(disabilityType)
		}
	}
	return disabilityType;
}
/***
 * 验证工会类型是否合法
 * @param periodValidityDate
 * @returns {String}
 */
function validateUnionFeature(unionFeature) {
	if(unionFeature!=null&&unionFeature!=""){
		if (!/^[\u4e00-\u9fa5a-zA-Z]{2,10}$/.test(unionFeature)) {
			return color(unionFeature)
		}
	}
	return unionFeature;
}
/***
 * 验证离休干部是否合法
 * @param periodValidityDate
 * @returns {String}
 */
function validateRetiredCadres(retiredCadres) {
	if(retiredCadres!=null&&retiredCadres!=""){
		if (!/^[\u4e00-\u9fa5a-zA-Z]{2,10}$/.test(retiredCadres)) {
			return color(retiredCadres)
		}
	}
	return retiredCadres;
}
/***
 * 验证扶贫户是否合法
 * @param nation
 * @returns {String}
 */
function validateHelpHouse(helpHouse) {
	if(helpHouse!=null&&helpHouse!=""){
		if (!/^[\u4e00-\u9fa5a-zA-Z]{2,10}$/.test(helpHouse)) {
			return color(helpHouse)
		}
	}
	return helpHouse;
}
/***
 * 验证低保类型是否合法
 * @param nation
 * @returns {String}
 */
function validateLowType(lowType) {
	if(lowType!=null&&lowType!=""){
		if (!/^[\u4e00-\u9fa5a-zA-Z]{2,20}$/.test(lowType)) {
			return color(lowType)
		}
	}
	return lowType;
}
/***
 * 验证病种类型是否合法
 * @param nation
 * @returns {String}
 */
function validateIllnessType(illnessType) {
	if(illnessType!=null&&illnessType!=""){
		if (!/^[\u4e00-\u9fa5a-zA-Z]{2,20}$/.test(illnessType)) {
			return color(illnessType)
		}
	}
	return illnessType;
}

/**
 * 为不正确的数据加上颜色
 * @param data
 * @returns {String}
 */
function color(data) {
	falg = false;
	return "<font color='red'>" + data + "</font>";
}
/***
 * 取消导入
 */
function cancelImport() {
	parent.layer_close();
}