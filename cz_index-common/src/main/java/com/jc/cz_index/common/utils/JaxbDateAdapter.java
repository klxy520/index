package com.jc.cz_index.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * 描述：xml Date 与 String 格式化
 * 
 * @author yangyongchuan 2018年1月2日 下午3:44:54
 * @version 1.0
 */
public class JaxbDateAdapter extends XmlAdapter<String, Date> {

    static final String STANDARM_DATE_FORMAT = "yyyy-MM-dd";



    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.parse(v);
    }



    @Override
    public String marshal(Date v) throws Exception {
        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.format(v);
    }

}
