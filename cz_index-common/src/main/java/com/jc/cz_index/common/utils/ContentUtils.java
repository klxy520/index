package com.jc.cz_index.common.utils;

/**
 * 
 * ContentUtil
 *
 * @author: 杨永川
 * @version: 1.0, 2015年10月26日
 */

public class ContentUtils {
    public static final String  VERSION                          = "/v1";
    public static final String  CHARACTER_SET_UTF_8              = "UTF-8";
    public static final String  CONTENT_TYPE                     = "text/xml; charset=utf-8";
    public static final String  TRUE                             = "true";
    public static final String  FALSE                            = "false";
    public static final String  SUCCESS                          = "success";
    public static final String  ERROR                            = "error";
    public static final String  FAILED                           = "failed";
    public static final String  USER                             = "user";
    public static final String  SESSION_USER                     = "session_user";
    public static final String  SESSION_USER_NAME                = "session_user_name";
    public static final String  SESSION_USER_ORG                 = "session_user_org";
    public static final String  SESSION_USER_AREA                = "session_user_area";
    public static final String  SESSION_PLATFORM_INFO            = "platformInfo";
    public static final String  COMMA                            = ",";
    public static final String  TIME_FORMAT1                     = "yyyy-MM-dd HH:mm:ss";
    public static final String  TIME_FORMAT2                     = "yyyy.MM.dd HH:mm";
    public static final String  TIME_FORMAT_SIMPLE               = "yyyy-MM-dd";
    public static final String  TIME_FORMAT_MONTH                = "yyyy-MM";
    public static final String  TIME_FORMAT_YEAR                 = "yyyy";

    public static final String  CONTENTTYPE_JSON                 = "application/json";
    public static final String  HEADER_ADVERTISEMENT             = "headerAdvertisement";
    public static final String  VERTICAL                         = "\\|";
    public static final String  TWO_BLANK                        = "  ";
    public static final String  COLON                            = ":";
    public static final String  PLUS_SIGN                        = "\\+";
    public static final String  POINT_SIGN                       = ".";
    public static final String  YYYY_MM_DD                       = "yyyy-MM-dd";
    public static final String  UNDERLINE                        = "_";

    public static final String  HTTP                             = "http://";
    public static final String  LINE_FEED                        = "\r\n";
    public static final String  PAGEINDEX                        = "pageIndex";
    public static final String  PAGESIZE                         = "pageSize";

    public static final String  APPID                            = "appid";
    public static final String  APPSECRET                        = "appsecret";
    public static final String  CYCLE_ACTIVITY_ID                = "cycleActivityId";
    public static final String  ACTIVITY_NOT_START               = "activity_not_start";
    public static final String  SERVICE_ERROR                    = "service_error";
    public static final String  JOIN_SUCCESS                     = "join_success";
    public static final String  JOIN_FAILED                      = "join_failed";
    public static final String  OPENID                           = "openid";
    public static final String  REMAINING_LOTTERY_NUMBER_IS_NULL = "remaining_lottery_number_is_null";
    public static final String  DOMAIN                           = "domain";
    public static final String  CURRENT_SHARE_URL                = "current_share_url";
    public static final String  SHARE_TITLE                      = "share_title";
    public static final String  SHARE_DESC                       = "share_desc";

    public static final String  SHARE_LINK                       = "share_link";
    public static final String  SHARE_IMGURL                     = "share_imgurl";

    public static final String  MENU_ROOT_ID                     = "menu_root_id";

    public static final String  FILE_UPLOAD_ROOT_PATH            = "file_upload_root_path";

    // 登录失败 security异常
    public static final String  SPRING_SECURITY_LAST_EXCEPTION   = "SPRING_SECURITY_LAST_EXCEPTION";
    // 商品上架状态
    public static final String  GOODS_STATE_ONSHELVES            = "1";
    // 商品下架状态
    public static final String  GOODS_STATE_OFFSHELVES           = "0";

    // 条件查询 字段前缀标记
    public static final String  FILTER_QUERY_FLAG                = "filter_";
    // 数据库字段状态 0 启用
    public static final Integer DB_FIELD_STATUS_ENABLE           = 0;
    // 数据库字段状态 0 禁用
    public static final Integer DB_FIELD_STATUS_DISABLE          = 1;

    // 密码加密前面加特定的字符串
    public static final String  USER_PWD_ENCP_PREFIX             = "OPIJHBNIYTFGAWSDFGYBHUJK";

    // 跑批开关系统参数key
    public static final String  SYS_CONFIG_PAOPI_SWITCH          = "sys_config_paopi_switch";
    // 对比规则开关系统参数key
    public static final String  SYS_CONFIG_COMPARE_RULE          = "sys_config_compare_rule";

    // 居民状态 -- 有效
    public static final String  PERSON_STATUS_ENABLE             = "0";
    // 居民状态 -- 无效
    public static final String  PERSON_STATUS_DISABLE            = "1";
    // 居民死亡标记 -- 未死亡
    public static final Integer PERSON_DECEASED_IND_ENABLE       = 0;
    // 居民死亡标记 -- 已死亡
    public static final Integer PERSON_DECEASED_IND_DISABLE      = 1;
    // 前置机状态 1 禁用
    public static final Integer FRON_END_MACHINE_OFF             = 1;
    // 前置机状态 0 启用
    public static final Integer FRON_END_MACHINE_ON              = 0;

    // 信息同步状态 0 未同步
    public static final String  INFO_SYNC_STATUS_NO              = "0";
    // 信息同步状态 0 已同步
    public static final String  INFO_SYNC_STATUS_YES             = "1";

    // 信息删除状态 -- 未删除
    public static final Integer INFO_NOT_DELETE                  = 0;
    // 信息删除状态 -- 已删除
    public static final Integer INFO_DELETE                      = 1;

    // 管理员ID
    public static final Long    ADMIN_USER_ID                    = 1L;

    // 主索引服务
    // 执行成功
    public static final String  EMPI_STATUS_CODE_SUCCESS         = "200";
    // 参数异常
    public static final String  EMPI_STATUS_CODE_ERROR_PARAMS    = "300";
    // 地址错误
    public static final String  EMPI_STATUS_CODE_ERROR_NOT_FIND  = "400";
    // 执行错误
    public static final String  EMPI_STATUS_CODE_ERROR           = "500";
    // 验签失败
    public static final String  EMPI_STATUS_CODE_ERROR_SIGN      = "999";

    // 卡的状态
    // 卡状态:0=正常
    public static final Integer CARD_STATUS_NORMAL               = 0;
    // 卡状态:1=挂失
    public static final Integer CARD_STATUS_LOSS                 = 1;
    // 卡状态:2=注销
    public static final Integer CARD_STATUS_CANCEL               = 2;
    // 卡状态:3=失效
    public static final Integer CARD_STATUS_INVALID              = 3;
    // 卡激活:4=激活
    public static final Integer CARD_STATUS_ACTIVATION           = 4;
    

}
