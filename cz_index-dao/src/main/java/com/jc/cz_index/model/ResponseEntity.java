package com.jc.cz_index.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.jc.cz_index.common.utils.ContentUtils;

/**
 * @ClassName: ResponseEntity
 * @Description:(出参信息)
 * @author: ZYX
 * @date: 2017年12月27日 下午3:31:05
 * @Copyright: 2017 ... Inc. All rights reserved. Note: 返回code
 *             message字段，returnCount属性以及数据结果
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseEntity implements Serializable {
    private static final long serialVersionUID = 4598718217691269199L;
    // 处理结果状态 200 成功 500 错误
    @XmlElement(name = "code")
    private String            code;
    // 处理结果描述
    @XmlElement(name = "message")
    private String            message;
    @XmlElementWrapper(name = "persons")
    @XmlElement(name = "person")
    private List<Person>      persons;
    @XmlElement(name = "person")
    private Person            person;
    @XmlAttribute(name = "returnCount")
    private Integer           returnCount      = 0;
    @XmlAttribute(name = "sign")
    private String            sign;



    public ResponseEntity() {
    }



    public ResponseEntity(String code, String message, List<Person> persons, Person person, Integer returnCount) {
        super();
        this.code = code;
        this.message = message;
        this.persons = persons;
        this.person = person;
        this.returnCount = returnCount;
    }



    public String getCode() {
        return code;
    }



    public void setCode(String code) {
        this.code = code;
    }



    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }



    public List<Person> getPersons() {
        return persons;
    }



    public void setPersons(List<Person> persons) {
        this.persons = persons;
        this.returnCount += persons == null ? 0 : persons.size();
    }



    public Person getPerson() {
        return person;
    }



    public void setPerson(Person person) {
        this.person = person;
        this.returnCount += 1;
    }



    public Integer getReturnCount() {
        return returnCount;
    }



    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }



    public String getSign() {
        return sign;
    }



    public void setSign(String sign) {
        this.sign = sign;
    }



    /**
     * 
     * 描述：操作成功_返回person
     * 
     * @param person
     * @param returnCount
     * @return
     * @author fengshengliang 2017年12月28日 上午11:56:04
     * @version 1.0
     */
    public static ResponseEntity getSuccessResponse(Person person, Integer returnCount) {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_SUCCESS, "success", null, person, returnCount);
    }



    /**
     * 
     * 描述：操作成功返回-persons
     * 
     * @param person
     * @param returnCount
     * @return
     * @author fengshengliang 2017年12月28日 下午5:38:13
     * @version 1.0
     */
    public static ResponseEntity getSuccessResponsePersons(List<Person> persons, Integer returnCount) {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_SUCCESS, "success", persons, null, returnCount);
    }



    /**
     * 
     * 描述：参数异常
     * 
     * @param person
     * @param returnCount
     * @return
     * @author fengshengliang 2017年12月28日 下午1:14:45
     * @version 1.0
     */
    public static ResponseEntity getParamsErrorResponse(String message) {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_ERROR_PARAMS, message, null, null, 0);
    }



    /**
     * 
     * 描述：访问路径不存在
     * 
     * @param message
     * @return
     * @author fengshengliang 2017年12月28日 下午1:21:03
     * @version 1.0
     */
    public static ResponseEntity getNotFoundErrorResponse() {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_ERROR_NOT_FIND, "访问地址不存在", null, null, 0);
    }



    /**
     * 
     * 描述：操作失败
     * 
     * @return
     * @author fengshengliang 2017年12月28日 下午2:09:41
     * @version 1.0
     */
    public static ResponseEntity getErrorResponse(String message) {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_ERROR, message, null, null, 0);
    }
    
 
    /**
     * 
     * 描述：操作成功
     * @param message
     * @return
     * @author yangjunhui 2018年1月23日 下午5:57:00 
     * @version 1.0
     */
    public static ResponseEntity getSuccessResponse(String message) {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_SUCCESS, message, null, null, 0);
    }

    /**
     * 
     * 描述：验签失败
     * @param message
     * @return
     * @author yangyongchuan 2018年1月12日 下午6:16:44 
     * @version 1.0
     */
    public static ResponseEntity getSignErrorResponse(String message) {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_ERROR_SIGN, message, null, null, 0);
    }

    /**
     * 
     * 描述：批量返回错误信息
     * 
     * @param message
     * @param persons
     * @return
     * @author fengshengliang 2017年12月29日 上午11:36:34
     * @version 1.0
     */
    public static ResponseEntity getErrorResponse(String message, Person person) {
        return new ResponseEntity(ContentUtils.EMPI_STATUS_CODE_ERROR, message, null, person, 0);
    }

}
