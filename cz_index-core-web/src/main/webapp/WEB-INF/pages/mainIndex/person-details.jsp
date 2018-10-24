<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/comm_const_tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="${contextPath}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.form-horizontal .form-label {
    margin-top: 0px;
    cursor: text;
    text-align: right;
    padding-right: 10px;
}
</style>
<title>基本身份信息详情</title>
</head>
<body>
	<div class="pd-20">
		<div class="form form-horizontal" style="border: 1px solid #BEBEBE">
		       <div class="row cl">
                <label class="form-label col-2">主索引ID:</label>
                <div class="formControls col-9">
                   <font>${person.mpiId}</font>
                </div>

            </div>
			 <div class="row cl">
                <label class="form-label col-2">姓名:</label>
                <div class="formControls col-4">
                   <font>${person.personName}</font>
                </div>
                <label class="form-label col-2">性别:</label>
                <div class="formControls col-4">
                 <jc:labelDictionaryByKey_Code key="indexSex" code="${person.sexCode}" id="sexCode"></jc:labelDictionaryByKey_Code>
                </div>
            </div>
            
            <div class="row cl">
                 <label class="form-label col-2">身份证号:</label>
                <div class="formControls col-4">
                   <font>${person.idCard }</font>
                </div>
                <label class="form-label col-2">状态:</label>
                <div class="formControls col-4">
                    <jc:labelDictionaryByKey_Code key="indexIdentityStatus" code="${person.status}" id="status"></jc:labelDictionaryByKey_Code>
                </div>
               
            </div>
            <div class="row cl">
                <label class="form-label col-2">一卡通号:</label>
                <div class="formControls col-4">
                    <font>${person.cardNo}</font>
                </div>
                <label class="form-label col-2">民族:</label>
                <div class="formControls col-4">
                    <jc:labelDictionaryByKey_Code key="nation" code="${person.nationCode}" id="nationCode"></jc:labelDictionaryByKey_Code> 
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">出生日期:</label>
                <div class="formControls col-4">
                     <font><fmt:formatDate value="${person.birthday }" pattern="yyyy-MM-dd"/></font>
                </div>
                 <label class="form-label col-2">国籍:</label>
                <div class="formControls col-4">
                 <jc:labelDictionaryByKey_Code key="indexNationalityCode" code="${person.nationalityCode}" id="nationalityCode"></jc:labelDictionaryByKey_Code>
                 </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">文化程度:</label>
                <div class="formControls col-4">
                     <jc:labelDictionaryByKey_Code key="indexEducationCode" code="${person.educationCode}" id="educationCode"></jc:labelDictionaryByKey_Code>   
                </div>
                 <label class="form-label col-2">血型:</label>
                <div class="formControls col-4">
                     <jc:labelDictionaryByKey_Code key="indexBloodTypeCode" code="${person.bloodTypeCode}" id="bloodTypeCode"></jc:labelDictionaryByKey_Code> 
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">婚姻状况:</label>
                <div class="formControls col-4">
                    <jc:labelDictionaryByKey_Code key="indexMaritalStatus" code="${person.maritalStatusCode}" id="maritalStatusCode"></jc:labelDictionaryByKey_Code>
                </div>
               <label class="form-label col-2">RH血型:</label>
                <div class="formControls col-4">    
                    <jc:labelDictionaryByKey_Code key="indexRhBloodCode" code="${person.rhBloodCode}" id="rhBloodCode"></jc:labelDictionaryByKey_Code>  
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">本人电话:</label>
                <div class="formControls col-4">
                    <font>${person.contactNo }</font>
                </div>
                <label class="form-label col-2">户籍标志:</label>
                <div class="formControls col-4">    
                     <jc:labelDictionaryByKey_Code key="indexRegisteredPermanent" code="${person.registeredPermanent}" id="registeredPermanent"></jc:labelDictionaryByKey_Code>  
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2">保险类别:</label>
                <div class="formControls col-4">
                
                        <jc:labelDictionaryByKey_Code key="indexInsuranceType" code="${person.insuranceType}" id="insuranceType"></jc:labelDictionaryByKey_Code>  
                </div>
                <label class="form-label col-2">医保类别:</label>
                <div class="formControls col-4">    
                       <jc:labelDictionaryByKey_Code key="indexInsuranceCode" code="${person.insuranceCode}" id="insuranceCode"></jc:labelDictionaryByKey_Code>  
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2">工作类别:</label>
                <div class="formControls col-4">
                     <jc:labelDictionaryByKey_Code key="workCode" code="${person.workCode}" id="workCode"></jc:labelDictionaryByKey_Code>  
                </div>
                <label class="form-label col-2">工作单位:</label>
                <div class="formControls col-4">    
                     <font>${person.workPlace }</font> 
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2">开始工作日期:</label>
                <div class="formControls col-4">
                   <font><fmt:formatDate value="${person.startWorkDate }" pattern="yyyy-MM-dd"/></font>
                </div>
                <label class="form-label col-2">死亡标记:</label>
                <div class="formControls col-4">    
                   <jc:labelDictionaryByKey_Code key="indexInDeceasedInd" code="${person.deceasedInd}" id="deceasedInd"></jc:labelDictionaryByKey_Code>  
                </div>
             </div>
            <div class="row cl">
                <label class="form-label col-2">创建人:</label>
                <div class="formControls col-4">
                   <font>${person.creatorUser.name }</font>
                </div>
                <c:choose>
                	<%-- 已死亡 --%>
                	<c:when test="${person.deceasedInd eq 1 }">
                		<label class="form-label col-2">死亡时间:</label>
		                <div class="formControls col-4">
		                    <font><fmt:formatDate value="${person.deceasedTime }" pattern="yyyy-MM-dd"/></font>
		                </div>
                	</c:when>
                	<%-- 未死亡 --%>
                	<c:when test="${person.deceasedInd eq 0 }">
                		<label class="form-label col-2">修改人:</label>
		                <div class="formControls col-4">
		                   <font>${person.updatorUser.name }</font>
		                </div>
                	</c:when>
                </c:choose>
            </div>
            <div class="row cl">
            	<c:choose>
	            	<c:when test="${person.deceasedInd eq 1 }">
		                <label class="form-label col-2">修改人:</label>
		                <div class="formControls col-4">
		                   <font>${person.updatorUser.name }</font>
		                </div>
		                <label class="form-label col-2">创建时间:</label>
		                <div class="formControls col-4">
		                  <font><fmt:formatDate value="${person.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
		                </div>
	                </c:when>
	                <c:when test="${person.deceasedInd eq 0 }">
	                	<label class="form-label col-2">创建时间:</label>
		                <div class="formControls col-4">
		                  <font><fmt:formatDate value="${person.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
		                </div>
		                <label class="form-label col-2">修改时间:</label>
			            <div class="formControls col-4">
			            	<font><fmt:formatDate value="${person.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
			            </div>
	                </c:when>
	           </c:choose>
            </div>
        <c:choose>
	        	<c:when test="${person.deceasedInd eq 1 }">
	        		<div class="row cl">
		            <label class="form-label col-2">修改时间:</label>
		            <div class="formControls col-4">
		            	<font><fmt:formatDate value="${person.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></font>
		            </div>
		             </div>
	            </c:when> 
            </c:choose>   
        <div class="row cl">
        	<label class="form-label col-2">户籍地址:</label>
		    <div class="formControls col-9">
		    	<font>${person.address }</font>
		    </div> 
        </div>
		<div class="form form-horizontal">
				<div class="row cl">
					<div class="col-offset-9">
						<button onClick="layer_close();" class="btn btn-default radius"
							type="button">&nbsp;&nbsp;关闭&nbsp;&nbsp;</button>
					</div>
				</div>
		</div>
	  </div>
	</div>
	<%@ include file="../commons/comm_const_js.jsp"%>
</body>
</html>