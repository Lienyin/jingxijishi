package com.jxxc.jingxijishi;

public class Api {
//       public static final String BASEURL = "https://repair-api-sit.zhizukj.com/zhizu/repair";//测试环境
//    public static final String BASEURL = "https://zhizuxia.zhizukj.com/ydb/app/";//生产
   public static final String BASEURL = "http://192.168.0.46:8100/system";//于立华

    //1-客户登录
    public static final String LOGIN = BASEURL + "/technician/login.json";
    //2-获取短信验证码
    public static final String SMS_CODE = BASEURL + "/common/auth/get_code.json";
}
