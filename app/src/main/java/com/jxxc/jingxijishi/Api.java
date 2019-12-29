package com.jxxc.jingxijishi;

public class Api {
//       public static final String BASEURL = "https://repair-api-sit.zhizukj.com/zhizu/repair";//测试环境
//    public static final String BASEURL = "https://zhizuxia.zhizukj.com/ydb/app/";//生产
   public static final String BASEURL = "http://47.100.198.184:9100/";//

    //1-客户登录
    public static final String LOGIN = BASEURL + "system/technician/login.json";
    //2-获取短信验证码
    public static final String SMS_CODE = BASEURL + "/common/auth/get_code.json";
    //3-短信验证码登录
    public static final String LOGIN_CODE = BASEURL + "system/technician/login_by_code.json";
    //4-微信快捷登录
    public static final String LOGIN_WECHAT = BASEURL + "system/technician/login_by_wechat.json";
    //5-绑定微信
    public static final String AUTH_WECHAT = BASEURL + "system/technician/auth_wechat.json";
}
