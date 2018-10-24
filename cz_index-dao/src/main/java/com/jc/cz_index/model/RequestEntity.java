package com.jc.cz_index.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.jc.cz_index.model.Person;

/**
 * @ClassName: RequestEntity
 * @Description:TODO(入参解析对象)
 * @author: ZYX
 * @date: 2017年12月27日 下午3:29:32
 * @Copyright: 2017 ... Inc. All rights reserved. Note:解析可以获取 参数中携带的 action
 *             client等属性和基本的参数信息
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestEntity implements Serializable {
    private static final long serialVersionUID = -4326078453468738104L;
    @XmlElementWrapper(name = "persons")
    @XmlElement(name = "person")
    private List<Person>      persons;
    @XmlElement(name = "person")
    private Person            person;
    @XmlAttribute(name = "action")
    private String            action;
    @XmlAttribute(name = "client")
    private String            client;
    @XmlAttribute(name = "create")
    private boolean           create;
    @XmlAttribute(name = "batch")
    private boolean           batch;
    @XmlAttribute(name = "sign")
    private String            sign;



    public boolean isBatch() {
        return batch;
    }



    public void setBatch(boolean batch) {
        this.batch = batch;
    }



    public boolean isCreate() {
        return create;
    }



    public void setCreate(boolean create) {
        this.create = create;
    }



    public List<Person> getPersons() {
        return persons;
    }



    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }



    public Person getPerson() {
        return person;
    }



    public void setPerson(Person person) {
        this.person = person;
    }



    public String getAction() {
        return action;
    }



    public void setAction(String action) {
        this.action = action;
    }



    public String getClient() {
        return client;
    }



    public void setClient(String client) {
        this.client = client;
    }



    public String getSign() {
        return sign;
    }



    public void setSign(String sign) {
        this.sign = sign;
    }

}
