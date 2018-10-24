package com.jc.cz_index.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * MD5,Base64加密
 *
 * @author: 杨永川
 * @version: 1.0, 2015年11月13日
 */
public class EncodeAndDecodeUtils {
    /**
     * 
     * @Title: base64Encode @Description:base64 加密 @param @param
     *         plainText @param @return @return String @throws
     */
    public static String base64Encode(String str) {
        byte[] b = str.getBytes();
        Base64 base64 = new Base64();
        b = base64.encode(b);
        return new String(b);
    }



    /**
     * 
     * @Title: base64Decode @Description:base64解密 @param @param
     *         str @param @return @return String @throws
     */
    public static String base64Decode(String str) {
        byte[] b = str.getBytes();
        Base64 base64 = new Base64();
        b = base64.decode(b);
        return new String(b);
    }



    /**
     * 
     * @Title: MD5Encode @Description:MD5 加密 @param @param
     *         plainText @param @return @return String @throws
     */
    public static String MD5Encode(String str) {
        return DigestUtils.md5Hex(str);
    }

}
