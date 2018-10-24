package com.jc.cz_index.common.utils;

import com.jc.encrypt.MD5;

public class EmpiUtils {

    /**
     * 
     * 描述：主索引id
     * @param certificate_no
     * @param certificate_type_code
     * @param personName
     * @return
     * @author fengshengliang 2017年12月26日 下午7:36:37 
     * @version 1.0
     */
    public static String getEmpiId(String certificate_no, String certificate_type_code, String personName){
        return MD5.encode(certificate_no + certificate_type_code + personName);
    }
}
