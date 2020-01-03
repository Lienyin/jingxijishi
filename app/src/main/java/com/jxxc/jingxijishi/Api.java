package com.jxxc.jingxijishi;

public class Api {
//       public static final String BASEURL = "https://repair-api-sit.zhizukj.com/zhizu/repair";//测试环境
//    public static final String BASEURL = "https://zhizuxia.zhizukj.com/ydb/app/";//生产
   public static final String BASEURL = "http://47.100.198.184:9100/";//

    //1-客户登录
    public static final String LOGIN = BASEURL + "system/technician/login";
    //2-获取短信验证码
    public static final String SMS_CODE = BASEURL + "/common/auth/get_code";
    //3-短信验证码登录
    public static final String LOGIN_CODE = BASEURL + "system/technician/login_by_code";
    //4-微信快捷登录
    public static final String LOGIN_WECHAT = BASEURL + "system/technician/login_by_wechat";
    //5-绑定微信
    public static final String AUTH_WECHAT = BASEURL + "system/technician/auth_wechat";
    //6-个人信息
    public static final String INFO_USER = BASEURL + "system/technician/info";
    //7-个人信息
    public static final String UPDATE_PASSWORD = BASEURL + "system/technician/update_password";
    //8-技师开始线上考试
    public static final String START_EXAMINATION = BASEURL + "system/examination/startExamination";
    //9-提交技师试卷
    public static final String END_EXAMINATION = BASEURL + "system/examination/endExamination";
    //10-更改信息(头像、在线状态)
    public static final String UPDATE_INFO = BASEURL + "system/technician/update_info";
    //11-抢单大厅列表
    public static final String AWAIT_RECEIVE_ORDER = BASEURL + "biz/carWashOrder/awaitReceiveOrder";
    //12-待服务列表
    public static final String UNFINISHED_ORDER = BASEURL + "biz/carWashOrder/unfinishedOrder";
}
