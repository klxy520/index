var importResidentPath = contextPath + "/residentExtendinfoImport";
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
			"aoColumnDefs": [ { "bSortable": false, "aTargets": [0]}],
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
			"sAjaxSource": importResidentPath+'/list',
			"aoColumns": [{
				"mData" : 'healthNumber',
				'sClass' : 'center',
				'sName' : "healthNumber",
				"render" : function(data, type, full) {
					return validateIdentityNumber(data);
				}
			},{
				"mData" : "isCivilAffairs",
				'sClass' : 'center',
				'sName' : "isCivilAffairs",
				"render" : function(data, type, full) {
					return validateIsCivilAffairs(data);
				}
			}, {
				"mData" : "isDisableFederation",
				'sClass' : 'center',
				'sName' : "isDisableFederation",
				"render" : function(data, type, full) {
					return validateIsDisableFederation(data);
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
 * 验证民政性质是否合法
 */
function validateIsCivilAffairs(isCivilAffairs) {
	if (isCivilAffairs == null || isCivilAffairs == "") {
		return null
	}
	var falg=fn_getDictByKeyValue("isCivilAffairs",isCivilAffairs);
	if (!falg) {
		return color(isCivilAffairs)
	}
	return isCivilAffairs;
}
/***
 * 验证残联性质是否合法
 */
function validateIsDisableFederation(isDisableFederation) {
	if (isDisableFederation == null || isDisableFederation == "") {
		return null
	}
	var falg=fn_getDictByKeyValue("isDisableFederation",isDisableFederation);
	if (!falg) {
		return color(isDisableFederation)
	}
	return isDisableFederation;
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