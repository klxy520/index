package com.jc.cz_index.core.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jc.cz_index.common.bean.RequestData;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.service.IPlatformService;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Platform;
import com.jc.encrypt.EncryptUtils;
import com.jc.encrypt.RSA;

/**
 * 
 * 描述：平台接口加密、验证工具
 * 
 * @author yangyongchuan 2016年11月8日 上午10:54:21
 * @version 1.0
 */
public class InterfaceVerifyUtils extends EncryptUtils {
    private static Log                   log             = LogFactory.getLog(InterfaceVerifyUtils.class);
    // 平台集合（保存）
    private static Map<String, Platform> platformMap     = new HashMap<String, Platform>();

    private static IPlatformService      platformService = (IPlatformService) SpringConfigTool.getBean("platformService");




    /**
     * 
     * 描述：获取所有平台
     * 
     * @author yangyongchuan 2016年11月14日 下午4:58:53
     * @version 1.0
     */
    @PostConstruct
    public static void loadPaltform() {
        QueryParams params = new QueryParams();
        params.addParameter("status", ContentUtils.DB_FIELD_STATUS_ENABLE);
        List<Platform> platformList = platformService.queryBaseList(params);
        if (CollectionsUtils.isNotNull(platformList)) {
            for (Platform platform : platformList) {
                platformMap.put(platform.getAppid(), platform);
            }
        }
    }



    /**
     * 
     * 描述：根据appid获取平台
     * 
     * @param appid
     * @return
     * @author yangyongchuan 2016年11月14日 下午4:59:44
     * @version 1.0
     */
    public static Platform getPlatform(String appid) {
        if (CollectionsUtils.isNull(platformMap)) {
            loadPaltform();
        }
        return CollectionsUtils.isNull(platformMap) ? null : platformMap.get(appid);
    }



    /**
     * 
     * 描述：加密参数（加签名）
     * 
     * @param requestAppid
     * @param responseAppid
     * @param object
     * @return
     * @author yangyongchuan 2017年9月28日 下午6:04:57
     * @version 1.0
     */
    public Map<String, String> encodeParam(String requestAppid, String responseAppid, Object object) {
        Platform requestPlatform = getPlatform(requestAppid);
        Platform responsePlatform = getPlatform(responseAppid);
        if (null == requestPlatform) {
            log.error("请求平台不存在，appid=" + requestAppid);
            return null;
        }
        if (null == responsePlatform) {
            log.error("响应平台不存在，appid=" + responseAppid);
            return null;
        }
        return encodeParam(object, responsePlatform.getPublicKey(), requestPlatform.getPrivateKey());
    }



    /**
     * 
     * 描述：加密参数（加签名）
     * 
     * @param requestAppid
     * @param responseAppid
     * @param map
     * @return
     * @author yangyongchuan 2017年9月28日 下午6:05:08
     * @version 1.0
     */
    public static Map<String, String> encodeParam(String requestAppid, String responseAppid, TreeMap<String, Object> map) {
        Platform requestPlatform = getPlatform(requestAppid);
        Platform responsePlatform = getPlatform(responseAppid);
        if (null == requestPlatform) {
            log.error("请求平台不存在，appid=" + requestAppid);
            return null;
        }
        if (null == responsePlatform) {
            log.error("响应平台不存在，appid=" + responseAppid);
            return null;
        }
        Map<String, String> responseMap = encodeParam(map, responsePlatform.getPublicKey(), requestPlatform.getPrivateKey());
        if (!CollectionsUtils.isNull(responseMap)) {
            // 加上appid
            responseMap.put("appid", requestAppid);
        }
        return responseMap;
    }

    /**
     * 
     * 描述：加密参数（加签名）返回请求时
     * 
     * @param requestAppid
     * @param responseAppid
     * @param map
     * @return
     * @author yangyongchuan 2017年9月28日 下午6:05:08
     * @version 1.0
     */
    public static Map<String, String> encodeParamToResponse(String requestAppid, String responseAppid, TreeMap<String, Object> map) {
        Platform requestPlatform = getPlatform(requestAppid);
        Platform responsePlatform = getPlatform(responseAppid);
        if (null == requestPlatform) {
            log.error("请求平台不存在，appid=" + requestAppid);
            return null;
        }
        if (null == responsePlatform) {
            log.error("响应平台不存在，appid=" + responseAppid);
            return null;
        }
        Map<String, String> responseMap = encodeParam(map, responsePlatform.getPublicKey(), requestPlatform.getPrivateKey());
        if (!CollectionsUtils.isNull(responseMap)) {
            // 加上appid
            responseMap.put("appid", responseAppid);
        }
        return responseMap;
    }

    /**
     * 
     * 描述：验证加密数据,并转换map
     * 
     * @param data
     * @param encryptkey
     * @return
     * @throws Exception
     * @author yangyongchuan 2016年11月8日 上午11:12:30
     * @version 1.0
     */
    public static Map<String, Object> verify(RequestData requestData, String responseAppid) {
        if (invalidRequestData(requestData)) {
            log.error("接口请求参数不正确或者缺失");
            return null;
        }
        String requestAppid = requestData.getAppid();
        Platform requestPlatform = getPlatform(requestAppid);
        Platform responsePlatform = getPlatform(responseAppid);
        if (null == requestPlatform) {
            log.error("请求平台不存在，appid=" + requestAppid);
            return null;
        }
        if (null == responsePlatform) {
            log.error("响应平台不存在，appid=" + responseAppid);
            return null;
        }

        Map<String, Object> requestDataMap = verify(requestData.getData(), requestData.getEncryptkey(), requestPlatform.getPublicKey(),
                responsePlatform.getPrivateKey());
        if (!CollectionsUtils.isNull(requestDataMap)) {
            requestDataMap.put("appid", requestAppid);
            requestDataMap.put("platformId", requestPlatform.getId());
        }
        return requestDataMap;
    }



    /**
     * 
     * 描述：检查请求参数
     * 
     * @param requestData
     * @return
     * @author yangyongchuan 2016年11月14日 下午5:02:45
     * @version 1.0
     */
    public static boolean invalidRequestData(RequestData requestData) {
        if (null == requestData || StringUtils.isEmpty(requestData.getAppid()) || StringUtils.isEmpty(requestData.getData())
                || StringUtils.isEmpty(requestData.getEncryptkey())) {
            return true;
        }
        return false;
    }



    /**
     * 
     * 描述：获取所有平台信息
     * 
     * @return
     * @author yangyongchuan 2016年11月24日 下午2:53:19
     * @version 1.0
     */
    public static Map<String, Platform> getPlatformMap() {
        return platformMap;
    }



    /**
     * 
     * 描述：生成appid,RSA公钥密钥
     * 
     * @param args
     * @author yangyongchuan 2017年9月20日 下午4:03:41
     * @version 1.0
     */
    public static void main(String[] args) {
        try {
            String platformUUID = UUID.randomUUID().toString();
            Map<String, String> rsaKeyMap = RSA.generateKeyPair();
            String publicKey = rsaKeyMap.get("publicKey");
            String privateKey = rsaKeyMap.get("privateKey");
            System.err.println("------平台UUID，appid=" + platformUUID);
            System.err.println("------RSA秘钥，publicKey=" + publicKey);
            System.err.println("------RSA秘钥，privateKey=" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
