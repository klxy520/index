var residentacquisitionImportPath = contextPath + "/residentacquisitionImport";
var importresidentacquisitionListTable;
var falg = true;
$(document).ready(function() {
	var excelUrl = $("#excelUrl").val();
	residentacquisitionImportList(excelUrl)
});
/**
 * 解析导入数据(判断数据是否合法)列表
 */
function residentacquisitionImportList(filePath) {
	var importresidentacquisitionTable = $("#importresidentacquisitionTable");
	if (importresidentacquisitionTable
			&& importresidentacquisitionTable.length > 0) {
		importresidentacquisitionListTable = importresidentacquisitionTable
				.dataTable({
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
					"sAjaxSource" : residentacquisitionImportPath
							+ '/importResidentAcquisitionDta',
					"aoColumns" : [ {
						"mData" : 'bidUtil',
						'sClass' : 'center',
						'sName' : "bidUtil",
						"render" : function(data, type, full) {
							return validatebidUtil(data);
						}
					}, {
						"mData" : 'name',
						'sClass' : 'center',
						'sName' : "name",
						"render" : function(data, type, full) {
							return validateName(data);
						}
					}, {
						"mData" : "identityNumber",
						'sClass' : 'center',
						'sName' : "identityNumber",
						"render" : function(data, type, full) {
							return validateIdentityNumber(data);
						}
					}, {
						"mData" : "issuersCertificateOrgan",
						'sClass' : 'center',
						'sName' : "issuersCertificateOrgan",
						"render" : function(data, type, full) {
							return validateIssuersCertificateOrgan(data);
						}
					}, {
						"mData" : "certificateValidityPeriod",
						'sClass' : 'center',
						'sName' : "certificateValidityPeriod",
						'mRender' : function(data, type, full) {
							return validateCertificateValidityPeriod(data);
						}
					}, {
						"mData" : "national",
						'sClass' : 'center',
						'sName' : "national",
						'mRender' : function(data, type, full) {
							return validatedictIsNotNull("nation",data);
						}
					}, {
						"mData" : "educationLevel",
						'sClass' : 'center',
						'sName' : "educationLevel",
						'mRender' : function(data, type, full) {
							return validatedictIsNull("education",data);
						}
					}, {
						"mData" : "houseAddress",
						'sClass' : 'center',
						'sName' : "houseAddress",
						'mRender' : function(data, type, full) {
							return validateAddress(data);
						}
					}, {
						"mData" : "nowAddress",
						'sClass' : 'center',
						'sName' : "nowAddress",
						'mRender' : function(data, type, full) {
							return validateAddress(data);
						}

					}, {
						"mData" : "postCode",
						'sClass' : 'center',
						'sName' : "postCode",
						'mRender' : function(data, type, full) {
							return validatePostCode(data);
						}
					}, {
						"mData" : "contactPhone",
						'sClass' : 'center',
						'sName' : "contactPhone",
						'mRender' : function(data, type, full) {
							return validateSelfPhone(data);
						}
					}, {
						"mData" : "newRuralNumber",
						'sClass' : 'center',
						'sName' : "newRuralNumber",
						'mRender' : function(data, type, full) {
							return validatenewRuralNumber(data);
						}
					}, {
						"mData" : "socialSecurityNum",
						'sClass' : 'center',
						'sName' : "socialSecurityNum",
						'mRender' : function(data, type, full) {
							return validateSocialNumber(data);
						}
					}, {
						"mData" : "salaryCardBank",
						'sClass' : 'center',
						'sName' : "salaryCardBank",
						'mRender' : function(data, type, full) {
							return validatedictIsNotNull("bank_payroll_card",data);
						}
					}, {
						"mData" : "healthCardBank",
						'sClass' : 'center',
						'sName' : "healthCardBank",
						'mRender' : function(data, type, full) {
							return  validatedictIsNotNull("bank_health_card",data);
						}
					}, {
						"mData" : "professional",
						'sClass' : 'center',
						'sName' : "professional",
						'mRender' : function(data, type, full) {
							return validatedictIsNull("professional",data);
						}
					}, {
						"mData" : "industry",
						'sClass' : 'center',
						'sName' : "industry",
						'mRender' : function(data, type, full) {
							return validatedictIsNull("industry",data);
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
/**
 * 验证申办单位是否合法
 */
function validatebidUtil(bidUtil) {
	if (bidUtil == null || bidUtil == "") {
		return color("不能为空")
	}
	return bidUtil;
}

/**
 * 验证发证机构是否合法
 */

function validateIssuersCertificateOrgan(issuersCertificateOrgan) {
	if (issuersCertificateOrgan == null || issuersCertificateOrgan == "") {
		return null;
	}
	if (!(/^[\u4e00-\u9fa5\\]{2,20}$/).test(issuersCertificateOrgan)) {
		return color(issuersCertificateOrgan);
	}
	return issuersCertificateOrgan;
}

/**
 * 验证姓名是否合法
 */
function validateName(name) {
	if (name == null || name == "") {
		return color("不能为空")
	}
	if (!/^[\u4e00-\u9fa5\\]{2,20}$/.test(name)) {
		return color(name)
	}
	return name;
}
/**
 * 验证姓名是否合法
 */
function validateCertificateValidityPeriod(certificateValidityPeriod){
	if (certificateValidityPeriod == null || certificateValidityPeriod == "") {
		return certificateValidityPeriod
	}
	if (!/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/ .test(certificateValidityPeriod)) {
		return color(certificateValidityPeriod)
	}
	return certificateValidityPeriod;
}

/**
 * 验证身份证号是否合法
 */
function validateIdentityNumber(identityNumber) {
	if (identityNumber == null || identityNumber == "") {
		return color("不能为空")
	}
	if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(identityNumber)) {
		return color(identityNumber)
	}
	return identityNumber;
}
/**
 * 验证联系电话是否合法
 * 
 * @param phone
 * @returns {String}
 */
function validateSelfPhone(phone) {
	if (phone == null || phone == "") {
		return color("不能为空")
	}
	var isPhone = /^[\d+]{8}$/;
	var isMob = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
	if (!isMob.test(phone) && !isPhone.test(phone)) {
		return color(phone);
	}
	return phone;
}
/**
 * 验证地址是否合法
 */
function validateAddress(address) {
	if (address == null || address == "") {
		return color("不能为空")
	}
	return address;
}

/**
 * 验证居民社保号是否合法
 */
function validateSocialNumber(socialNumber) {
	if (socialNumber == null || socialNumber == "") {
		return socialNumber;
	}
	var length = socialNumber.length
	if (length != 9) {
		return color(socialNumber)
	}
	if (!/^\d+(\.\d+)?$/.test(socialNumber)) {
		return color(socialNumber)
	}
	return socialNumber;
}
/**
 * 验证居民新农合号是否合法
 */
function validatenewRuralNumber(newRuralNumber) {
	if (newRuralNumber == null || newRuralNumber == "") {
		return newRuralNumber;
	}
	var length = newRuralNumber.length
	if (length < 8 || length > 20) {
		return color(newRuralNumber)
	}
	if (!/^\d+(\.\d+)?$/.test(newRuralNumber)) {
		return color(newRuralNumber)
	}
	return newRuralNumber;
}
/**
 * 验证邮编是否合法
 */
function validatePostCode(postCode) {
	if (postCode == null || postCode == "") {
		return postCode;
	}
	var length = postCode.length
	if (length !=6) {
		return color(postCode)
	}
	if (!/^\d+(\.\d+)?$/.test(postCode)) {
		return color(postCode)
	}
	return postCode;
}
/**
 * 为不正确的数据加上颜色
 * 
 * @param data
 * @returns {String}
 */
function color(data) {
	falg = false;
	return "<font color='red'>" + data + "</font>";
}

/**
 * 取消导入
 */
function cancelImport() {
	parent.layer_close();
}
/**
 * 验证数据字典的值是否存在(可以为空)
 */
function validatedictIsNull(dictKey, code) {
	if(code==null||code==""){
		return null;
	}
	var falg = fn_getDictByKeyCodeAndDescription(dictKey, code);
	if (falg) {
		return color(code);
	}
	return code;
}
/**
 * 验证数据字典的值是否存在(不可以为空)
 */
function validatedictIsNotNull(dictKey, code) {
	if(code==null||code==""){
		return color("不能为空");
	}
	var falg = fn_getDictByKeyCodeAndDescription(dictKey, code);
	if (falg) {
		return color(code);
	}
	return code;
}
/**
 * 将居民采集信息批量插入数据库
 */
function bathAddResidentAcquisitions() {
	if (falg) {
		$.ajax({
			"type" : 'post',
			"url" : residentacquisitionImportPath+ "/bathAddResidentAcquisitions",
			"dataType" : "json",
			"async" : false,
			"success" : function(data) {
				if (data.status == 0) {
					layer.alert(data.message, {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						parent.residentAcquisitionTable.fnFilter();
						parent.layer_close();
					});
				} else {
					layer.alert(data.message)
				}
			}
		});
	} else {
		layer.alert("数据存在错误不能进行导入,请修改数据");
	}
}