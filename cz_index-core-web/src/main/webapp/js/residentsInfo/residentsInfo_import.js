var importResidentPath = contextPath + "/residentsInfoImport";
var importResidentListTable;
var falg = true;
$(document).ready(function() {
	var excelUrl=$("#excelUrl").val();
	residentsInfoImportList(excelUrl)
});
/**
 * 解析导入数据(判断数据是否合法)列表
 */
function residentsInfoImportList(filePath) {
	var residentsInfoImportTable = $("#residentsInfoImportTable");
	if (residentsInfoImportTable && residentsInfoImportTable.length > 0) {
		importResidentListTable = residentsInfoImportTable.dataTable({
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
			"sAjaxSource" : importResidentPath + '/importDtaresidentsInfo',
			"aoColumns" : [ {
				"mData" : 'bidUtil',
				'sClass' : 'center',
				'sName' : "bidUtil",
				"render" : function(data, type, full) {
					return validatebidUtil(data);
				}
			}, {
				"mData" : 'bankCardNumber',
				'sClass' : 'center',
				'sName' : "bankCardNumber",
				"render" : function(data, type, full) {
					return validateBankCardNumber(data);
				}
			}, {
				"mData" : "cardType",
				'sClass' : 'center',
				'sName' : "cardType",
				"render" : function(data, type, full) {
					return validateCardType(data);
				}
			}, {
				"mData" : "issuersCardName",
				'sClass' : 'center',
				'sName' : "issuersCardName",
				"render" : function(data, type, full) {
					return validateIssuersCardName(data,full.issuersCardCode+"");
				}
			}, {
				"mData" : "issuersCardCode",
				'sClass' : 'center',
				'sName' : "issuersCardCode",
				'mRender' : function(data, type, full) {
					return validateIssuersCardCode(data+"",full.issuersCardName);
				}
			}, {
				"mData" : "issuingSerialNumber",
				'sClass' : 'center',
				'sName' : "issuingSerialNumber",
				'mRender' : function(data, type, full) {
					return validateIssuingSerialNumber(data);
				}
			}, {
				"mData" : "issuersCardCertificate",
				'sClass' : 'center',
				'sName' : "issuersCardCertificate",
				'mRender' : function(data, type, full) {
					return validateIssuersCardCertificate(data);
				}
			}, {
				"mData" : "issuingTime",
				'sClass' : 'center',
				'sName' : "issuingTime",
				'mRender' : function(data, type, full) {
					return validateIssuingTime(data);
				}
			}, {
				"mData" : "name",
				'sClass' : 'center',
				'sName' : "name",
				'mRender' : function(data, type, full) {
					return validateName(data);
				}
			}, {
				"mData" : "sex",
				'sClass' : 'center',
				'sName' : "sex",
				'mRender' : function(data, type, full) {
					return validateSex(data);
				}
			}, {
				"mData" : "nationalCode",
				'sClass' : 'center',
				'sName' : "nationalCode",
				'mRender' : function(data, type, full) {
					return validateNationalCode(data);
				}
			}, {
				"mData" : "birthday",
				'sClass' : 'center',
				'sName' : "birthday",
				'mRender' : function(data, type, full) {
					return validateBirthday(data);
				}

			}, {
				"mData" : "identityNumber",
				'sClass' : 'center',
				'sName' : "identityNumber",
				'mRender' : function(data, type, full) {
						return validateIdentityNumber(data);
				}
			}, {
				"mData" : "cardValidityPeriod",
				'sClass' : 'center',
				'sName' : "cardValidityPeriod",
				'mRender' : function(data, type, full) {
					return validateCardValidityPeriod(data);
				}
			},{
				"mData" : "selfPhone",
				'sClass' : 'center',
				'sName' : "selfPhone",
				'mRender' : function(data, type, full) {
					return validateSelfPhone(data);
				}
			},{
				"mData" : "medicalPayment",
				'sClass' : 'center',
				'sName' : "medicalPayment",
				'mRender' : function(data, type, full) {
					return validatedictExist("medical_payment",data+'');
				}
			},{
				"mData" : "houseAddress",
				'sClass' : 'center',
				'sName' : "houseAddress",
				'mRender' : function(data, type, full) {
					return validateAddress(data);
				}
			},{
				"mData" : "nowAddress",
				'sClass' : 'center',
				'sName' : "nowAddress",
				'mRender' : function(data, type, full) {
					return validateAddress(data);
				}
			},{
				"mData" : "contactName",
				'sClass' : 'center',
				'sName' : "contactName",
				'mRender' : function(data, type, full) {
					return validateContact(data);
				}
			},{
				"mData" : "contactRelation",
				'sClass' : 'center',
				'sName' : "contactRelation",
				'mRender' : function(data, type, full) {
					return validateContact(data);
				}
			},{
				"mData" : "contactPhone",
				'sClass' : 'center',
				'sName' : "contactPhone",
				'mRender' : function(data, type, full) {
					return validateContactPhone(data);
				}
			},{
				"mData" : "educationLevelCode",
				'sClass' : 'center',
				'sName' : "educationLevelCode",
				'mRender' : function(data, type, full) {
					return validatedictExist("education",data+'');
				}
			},{
				"mData" : "maritalStatusCode",
				'sClass' : 'center',
				'sName' : "maritalStatusCode",
				'mRender' : function(data, type, full) {
					return validatedictExist("marital_status",data+'');
				}
			}
			,{
				"mData" : "professionalCode",
				'sClass' : 'center',
				'sName' : "professionalCode",
				'mRender' : function(data, type, full) {
					return validatedictExist("professional",data+'');
				}
			}
			,{
				"mData" : "socialSecurityNum",
				'sClass' : 'center',
				'sName' : "socialSecurityNum",
				'mRender' : function(data, type, full) {
					return validateSocialNumber(data);
				}
			}
			,{
				"mData" : "issuingBank",
				'sClass' : 'center',
				'sName' : "issuingBank",
				'mRender' : function(data, type, full) {
					return validateIssuingBank(data);
				}
			}
			,{
				"mData" : "cardStatus",
				'sClass' : 'center',
				'sName' : "cardStatus",
				'mRender' : function(data, type, full) {
					return validateCardStatus(data);
				}
			}
			,{
				"mData" : "chipNum",
				'sClass' : 'center',
				'sName' : "chipNum",
				'mRender' : function(data, type, full) {
					return validateChipNum(data);
				}
			}
			],
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
 * 验证申办单位是否合法
 */
function validatebidUtil(bidUtil) {
	if (bidUtil == null || bidUtil == "") {
		return color("不能为空")
	}
	return bidUtil;
}
/***
 * 验证银行卡号是否合法
 */
function validateBankCardNumber(bankCardNumber) {
	if (bankCardNumber == null || bankCardNumber == "") {
		return color("不能为空")
	}
     if (!/^([1-9]{1})(\d{14}|\d{18})$/.test(bankCardNumber)) {  
         return color(bankCardNumber);  
     }  
	return bankCardNumber;
}
/***
 * 验证卡的类别是否合法
 */
function validateCardType(cardType) {
	if (cardType == null || cardType == "") {
		return color("不能为空")
	}
	if(!cardType.replace(/[^\d]/g,'')){
		return color(cardType)
	}
	if(validatedict("card_type",cardType+"")){
		return color(cardType);
	}
	return cardType;
}
/***
 * 验证发卡机构名称是否合法
 */

function validateIssuersCardName(issuersCardName,issuersCardCode) {
	if (issuersCardName == null || issuersCardName == "") {
		return color("不能为空");
	}
	if(!(/^[\u4e00-\u9fa5\\]{2,20}$/).test(issuersCardName)){
		return color(issuersCardName);
	}
	if(fn_getDictByKeyCode("issuers_card_name",issuersCardCode) !== issuersCardName){
		return color(issuersCardName);
	}
	return issuersCardName;
}
/***
 * 验证发卡机构代码是否合法
 */
function validateIssuersCardCode(issuersCardCode,issuersCardName) {
	if (issuersCardCode == null || issuersCardCode == "") {
		return color("不能为空");
	}
	if (issuersCardCode.length !=9) {
		return color(issuersCardCode);
	}
	if(!/^\d{9}$/.test(issuersCardCode)){
		return color(issuersCardCode );
	}
	if(fn_getDictByKeyCode("issuers_card_name",issuersCardCode) === "暂无数据"){
		return color(issuersCardCode);
	}
	if(fn_getDictByKeyCode("issuers_card_name",issuersCardCode) !== issuersCardName){
		return color(issuersCardCode);
	}
	return issuersCardCode;
}
/***
 * 验证发卡序列号是否合法
 */
function validateIssuingSerialNumber(issuingSerialNumber) {
	if (issuingSerialNumber == null || issuingSerialNumber == "") {
		return color("不能为空")
	}
	if (issuingSerialNumber.length !=10) {
		return color(issuingSerialNumber)
	}
	if(!/^(0|[1-9][0-9]*)$/.test(issuingSerialNumber)){
		return color(issuingSerialNumber )
	}
	return issuingSerialNumber;
}
/***
 * 验证发卡时间是否合法
 */
function validateIssuingTime(issuingTime) {
	if (issuingTime == null || issuingTime == "") {
		return color("不能为空")
	}
	if (issuingTime.length !=8) {
		return color(issuingTime)
	}
	if(!/^(0|[1-9][0-9]*)$/.test(issuingTime)){
		return color(issuingTime )
	}
	if(issuingTime.length!=8){
		return color(issuingTime )
	}
	return issuingTime;
}
/***
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
/***
 * 验证性别是否合法
 */
function validateSex(sex) {
	if (sex == null || sex == "") {
		return color("不能为空")
	}
	if(!/^(0|[1-9][0-9]*)$/.test(sex)){
		return color(sex )
	}
	if (sex!=1&&sex!=2) {
		return color(sex)
	}
	return sex;
}
/***
 * 验证民族代码是否合法
 */
function validateNationalCode(nationalCode) {
	if (nationalCode == null || nationalCode == "") {
		return color("不能为空")
	}
	if (nationalCode.length!=2) {
		return color(nationalCode)
	}
	if(!nationalCode.replace(/[^1-9]/g,'')){
		return color(nationalCode)
	}
	if(validatedict("nation",nationalCode+"")){
		return color(nationalCode);
	}
	return nationalCode;
}
/***
 * 验证出生日期是否合法
 */
function validateBirthday(birthday) {
	if (birthday == null || birthday == "") {
		return color("不能为空")
	}
	if(!/^(0|[1-9][0-9]*)$/.test(birthday)){
		return color(birthday )
	}
	if(birthday.length!=8){
		return color(birthday )
	}
	return birthday;
}
/***
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
/***
 * 验证卡效期是否合法
 */
function validateCardValidityPeriod(cardValidityPeriod) {
	if (cardValidityPeriod == null || cardValidityPeriod == "") {
		return color("不能为空")
	}
	if(!/^(0|[1-9][0-9]*)$/.test(cardValidityPeriod)){
		return color(cardValidityPeriod )
	}
	if(cardValidityPeriod.length!=8){
		return color(cardValidityPeriod )
	}
	return cardValidityPeriod;
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
 * 验证本人电话是否合法
 */
function validateSelfPhone(selfPhone) {
	if (selfPhone == null || selfPhone == "") {
		return color("不能为空")
	}
	if (!/^0?1[3|4|5|8][0-9]\d{8}$/.test(selfPhone)) {
		return color(selfPhone);
	}
	return selfPhone;
}
/***
 * 验证地址是否合法
 */
function validateAddress(address) {
	if (address == null || address == "") {
		return color("不能为空")
	}
	return address;
}
/***
 * 验证联系人是否合法
 */
function validateContact(contact) {
	if (contact == null || contact == "") {
		return contact;
	}
	if (!/^[\u4e00-\u9fa5\\]{2,20}$/.test(contact)) {
		return color(contact)
	}
	return contact;
}
/***
 * 验证联系人电话是否合法
 */
function validateContactPhone(contactPhone) {
	if (contactPhone == null || contactPhone == "") {
		return contactPhone;
	}
	if (!/^0?1[3|4|5|8][0-9]\d{8}$/.test(contactPhone)) {
		return color(contactPhone)
	}
	return contactPhone;
}
/***
 * 验证居民社保卡号是否合法
 */
function validateSocialNumber(socialNumber) {
	if (socialNumber == null || socialNumber == "") {
		return socialNumber;
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
 * 验证发卡银行是否合法
 */
function validateIssuingBank(issuingBank) {
	if (issuingBank == null || issuingBank == "") {
		return  color(issuingBank);
	}
	if (!/^[\u4e00-\u9fa5a-zA-Z]{2,20}$/.test(issuingBank)) {
		return color(issuingBank)
	}
	return issuingBank;
}
/***
 * 验证卡状态是否合法
 */
function validateCardStatus(cardStatus) {
	if (cardStatus == null || cardStatus == "") {
		return  color(cardStatus);
	}
	if (!/^[\u4e00-\u9fa5a-zA-Z]{2,10}$/.test(cardStatus)) {
		return color(cardStatus)
	}
	return cardStatus;
}
/***
 * 验证芯片号是否合法
 */
function validateChipNum(chipNum) {
	if (chipNum == null || chipNum == "") {
		return  color(chipNum);
	}
	if (!/^[A-Za-z0-9]{15,20}$/.test(chipNum)) {
		return color(chipNum)
	}
	return chipNum;
}
/**
 * 验证数据字典的值是否存在(三次封装)
 * @param dictKey
 * @param code
 * @returns
 */
function validatedictExist(dictKey,code){
	if (code == null ||code=="null"|| code == "") {
		return null;
	}
	var falg=validatedict(dictKey,code);
	if(falg){
		return color(code)
	}
	return code;
}
/***
 * 发卡机构证书
 */
function validateIssuersCardCertificate(cate){
	if (cate == null ||cate=="null"|| cate == "") {
		return null;
	}
	if (!/^[\S+]{0,20}$/.test(cate)) {
		return color(cate)
	}
	return cate;
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
/**
 * [导入基本信息]将基本信息批量插入数据库
 */
function batchAddResidentsinfo() {
	if (falg) {
		$.ajax({
			"type" : 'post',
			"url" : importResidentPath + "/batchAddResidentsinfo",
			"dataType" : "json",
			"async" : false,
			"success" : function(data) {
				if (data.status == 0) {
					layer.alert(data.message, {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						shift : 4
					}, function(index) {
						parent.residentListTable.fnFilter();
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
/***
 * 取消导入
 */
function cancelImport() {
	parent.layer_close();
}
/**
 * 验证数据字典的值是否存在
 */
function validatedict(dictKey,code){
	var falg=false;
	var tag=fn_getDictByKeyCode(dictKey,code);
	if(tag=="暂无数据"){
		 falg=true;
	}
	return falg;
}

