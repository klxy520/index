package com.jc.cz_index.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

/**
 * 
 * HttpUtil
 *
 * @author: 杨永川
 * @version: 1.0, 2015年10月28日
 */
public class HttpUtil {
    private static final Logger LOG               = Logger.getLogger(HttpUtil.class);
    public static final Integer MAX_IDLE_TIME_OUT = Integer.valueOf(60000);
    private static final String URL_ENCODE        = "utf-8";
    public static HttpClient    httpClient;

    static {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.closeIdleConnections(MAX_IDLE_TIME_OUT.intValue());
        httpClient = new HttpClient(connectionManager);
    }



    /**
     * 
     * Http请求 请求方式get
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        LOG.info("请求url:" + url);
        GetMethod get = new GetMethod(url);
        try {
            httpClient.executeMethod(get);
            return streamToString(get.getResponseBodyAsStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            get.releaseConnection();
        }
    }



    /**
     * 
     * Http请求 请求方式get
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        StringBuffer urlSB = appendGetParams(url, params);
        return get(urlSB.toString());
    }



    /**
     * 
     * 拼接参数
     *
     * @param url
     * @param params
     * @return
     */
    private static StringBuffer appendGetParams(String url, Map<String, String> params) {
        StringBuffer urlSB = new StringBuffer(url);
        if (params == null || params.isEmpty()) {
            return urlSB;
        }
        if (url.contains("?")) {
            urlSB.append("&");
        } else {
            urlSB.append("?");
        }
        for (Entry<String, String> param : params.entrySet()) {
            urlSB.append(param.getKey());
            urlSB.append("=");
            urlSB.append(param.getValue());
            urlSB.append("&");
        }
        urlSB.deleteCharAt(urlSB.length() - 1);
        return urlSB;
    }



    /**
     * 
     * Http请求 请求方式post
     *
     * @param url
     * @param content
     * @return
     * @throws IOException
     */
    public static String post(String url, String content) {
        return post(url, content, "application/json", "utf-8");
    }



    /**
     * 
     * Http请求 请求方式post
     *
     * @param url
     * @param content
     * @param contentType
     * @param charset
     * @return
     * @throws IOException
     */
    public static String post(String url, String content, String contentType, String charset) {
        PostMethod post = new PostMethod(url);

        try {
            post.setRequestEntity(new StringRequestEntity(content, contentType, charset));
            httpClient.executeMethod(post);
            return streamToString(post.getResponseBodyAsStream());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            post.releaseConnection();
        }
    }



    public static Map<String, String> packParamsFromRequest(HttpServletRequest request, String charSet)
            throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), charSet);
            params.put(name, valueStr);
        }
        return params;
    }



    /**
     * 
     * Http 请求获取html页面源码
     *
     * @param urlStr
     * @return
     */
    public static String getHtmlContent(String urlStr) {
        StringBuffer contentBuffer = new StringBuffer();
        int responseCode = -1;
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            // IE代理进行下载
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            con.setConnectTimeout(60000);
            con.setReadTimeout(60000);
            // 获得网页返回信息码
            responseCode = con.getResponseCode();
            if (responseCode == -1) {
                System.out.println(url.toString() + " : connection is failure...");
                con.disconnect();
                return null;
            }
            // 请求失败
            if (responseCode >= 400) {
                System.out.println("请求失败:get response code: " + responseCode);
                con.disconnect();
                return null;
            }

            InputStream inStr = con.getInputStream();
            InputStreamReader istreamReader = new InputStreamReader(inStr, URL_ENCODE);
            BufferedReader buffStr = new BufferedReader(istreamReader);
            String str = null;
            while ((str = buffStr.readLine()) != null)
                contentBuffer.append(str);
            inStr.close();
        } catch (Exception e) {
            contentBuffer = new StringBuffer();
            contentBuffer.append("请求失败,error:" + e.getMessage());
        } finally {
            con.disconnect();
        }
        return contentBuffer.toString();
    }



    /**
     * 
     * 输入流中获取返回数据
     *
     * @param is
     * @return
     */
    public static String streamToString(InputStream is) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            isr = new InputStreamReader(is, "UTF-8");
            sb = new StringBuffer();
            br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
        }
        return null;
    }

}
