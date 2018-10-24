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
	var importBaseResidentTable = $("#importBaseResidentTable");
	if (importBaseResidentTable && importBaseResidentTable.length > 0) {
		importResidentListTable = importBaseResidentTable.dataTable({
			"oLanguage" : { // 语言国际化
				"sUrl" : contextPath + "/js/de_DE.txt"
			},
			"bPaginate" : false,
			"bFilter" : false,
			"bLengthChange" : false,
			'reset' : true,
			"iDisplayLength" : 15,
			"bSort" : false,
			"aaSorting" : [ [ 1, "desc" ] ],
			"aoColumnDefs" : [ {
				"bSortable" : false,
				"aTargets" : [ 0, 3, 4, 5, 6, 7 ]
			} ],
			"aLengthMenu" : [ [ 15, 30, 50, 100 ], [ 15, 30, 50, 100 ] ],// 定义每页显示数据数量
			"bInfo" : true,
			"bWidth" : true,
			"bScrollCollapse" : true,
			"sPaginationType" : "full_numbers",
			"bProcessing" : true,
			"bServerSide" : true,
			"bDestroy" : true,
			"bSortCellsTop" : true,
			"bStateSave" : false,// true状态保持 刷新页面保持页码等数据,false:反之
			"asStripeClasses" : [ 'text-c odd', 'text-c even' ],
			"sAjaxSource" : importResidentPath + '/importDtaResidentDataDto',
			"aoColumns" : [ {
				"mData" : 'realName',
				'sClass' : 'center',
				'sName' : "realName",
				"render" : function(data, type, full) {
					return validateRealName(data);
				}
			}, {
				"mData" : 'healthNumber',
				'sClass' : 'center',
				'sName' : "healthNumber",
				"render" : function(data, type, full) {
					return validateHealthNumber(data);
				}
			}, {
				"mData" : "socialNumber",
				'sClass' : 'center',
				'sName' : "socialNumber",
				"render" : function(data, type, full) {
					return validateSocialNumber(data);
				}
			}, {
				"mData" : "idNumber",
				'sClass' : 'center',
				'sName' : "idNumber",
				"render" : function(data, type, full) {
					return validateIdNumber(data);
				}
			}, {
				"mData" : "periodValidityDate",
				'sClass' : 'center',
				'sName' : "periodValidityDate",
				'mRender' : function(data, type, full) {
					return validatePeriodValidityDate(data);
				}
			}, {
				"mData" : "sex",
				'sClass' : 'center',
				'sName' : "sex",
				'mRender' : function(data, type, full) {
					return validatePeriodSex(data);
				}
			}, {
				"mData" : "age",
				'sClass' : 'center',
				'sName' : "age",
			}, {
				"mData" : "nation",
				'sClass' : 'center',
				'sName' : "nation",
				'mRender' : function(data, type, full) {
					return validateNation(data);
				}

			}, {
				"mData" : "houseAddress",
				'sClass' : 'center',
				'sName' : "houseAddress",

			}, {
				"mData" : "nowAddress",
				'sClass' : 'center',
				'sName' : "nowAddress",
			}, {
				"mData" : "postCode",
				'sClass' : 'center',
				'sName' : "postCode",
				'mRender' : function(data, type, full) {
					return validatePostCode(data);
				}
			}, {
				"mData" : "phone",
				'sClass' : 'center',
				'sName' : "phone",
				'mRender' : function(data, type, full) {
					return validatePhone(data);
				}

			}, {
				"mData" : "wrokUnit",
				'sClass' : 'center',
				'sName' : "wrokUnit"
			}, {
				"mData" : "education",
				'sClass' : 'center',
				'sName' : "education",
				'mRender' : function(data, type, full) {
					return validateEducation(data);
				}
			}, {
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
					return validateDisabilityType(data);
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
 * 验证真实姓名是否合法
 * @param realName
 * @returns {String}
 */
function validateRealName(realName) {
	if (realName == null || realName == "") {
		return color("不能为空")
	}
	if (!/^[\u4e00-\u9fa5a-zA-Z]{2,20}$/.test(realName)) {
		return color(realName)
	}
	return realName;
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
	return healthNumber;
}
/***
 * 验证居民社保卡号是否合法
 * @param realName
 * @returns {String}
 */
function validateSocialNumber(socialNumber) {
	if (socialNumber == null || socialNumber == "") {
		return color("不能为空")
	}
	var length = socialNumber.length
	if (length < 8 || length > 15) {
		return color(socialNumber)
	}
	if (!/^\d+(\.\d+)?$/.test(socialNumber)) {
		return color(socialNumber)
	}
	return socialNumber;
}
/***
 * 验证身份证号是否合法
 * @param realName
 * @returns {String}
 */
function validateIdNumber(idNumber) {
	if (idNumber == null || idNumber == "") {
		return color("不能为空")
	}
	if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idNumber)) {
		return color(idNumber)
	}
	return idNumber;
}
/***
 * 验证证件有效期是否合法
 * @param periodValidityDate
 * @returns {String}
 */
function validatePeriodValidityDate(periodValidityDate) {
	periodValidityDate = periodValidityDate.substring(0, 10);
	if (periodValidityDate == null || periodValidityDate == "") {
		return color("不能为空")
	}
	if (!periodValidityDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/)) {
		return color(periodValidityDate)
	}
	return periodValidityDate;
}
/***
 * 验证性别是否合法
 * @param periodValidityDate
 * @returns {String}
 */
function validatePeriodSex(sex) {
	if (sex == null || sex == "") {
		return color("不能为空")
	}
	if (sex != "男" && sex != "女") {
		return color(sex)
	}
	return sex;
}
/***
 * 验证民族是否合法
 * @param nation
 * @returns {String}
 */
function validateNation(nation) {
	if (nation == null || nation == "") {
		return color("不能为空")
	}
	var length = nation.length
	if (length < 0 || length > 6) {
		return color(nation)
	}
	if (!/^[\u4e00-\u9fa5a-zA-Z]{1,6}$/.test(nation)) {
		return color(nation)
	}
	return nation;
}
/***
 * 验证民族是否合法
 * @param nation
 * @returns {String}
 */
function validatePhone(phone) {
	if (phone == null || phone == "") {
		return color("不能为空")
	}
	var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	var isMob = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
	if (!isMob.test(phone) && !isPhone.test(phone)) {
		return color(phone);
	}
	return phone;
}
/***
 * 验证邮编是否合法
 * @param nation
 * @returns {String}
 */
function validatePostCode(postCode) {
	if (postCode == null || postCode == "") {
		return color("不能为空")
	}
	if (!/^[1-9][0-9]{5}$/.test(postCode)) {
		return color(postCode);
	}
	return postCode;
}
/***
 * 验证学历是否合法
 * @param education
 * @returns {String}
 */
function validateEducation(education) {
	if (education == null || education == "") {
		return color("不能为空")
	}
	if (!/^[\u4e00-\u9fa5a-zA-Z]{2,6}$/.test(education)) {
		return color(education);
	}
	return education;
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
