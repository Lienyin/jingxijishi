package com.jxxc.jingxijishi;

public class Api {
//       public static final String BASEURL = "https://repair-api-sit.zhizukj.com/zhizu/repair";//测试环境
//    public static final String BASEURL = "https://zhizuxia.zhizukj.com/ydb/app/";//生产
   public static final String BASEURL = "http://106.54.252.151:8100/";//

    //1-客户登录
    public static final String LOGIN = BASEURL + "system/technician/login";
    //2-获取短信验证码
    public static final String SMS_CODE = BASEURL + "common/auth/get_code";
    //3-短信验证码登录
    public static final String LOGIN_CODE = BASEURL + "system/technician/login_by_code";
    //4-微信快捷登录
    public static final String LOGIN_WECHAT = BASEURL + "system/technician/login_by_wechat";
    //5-绑定微信
    public static final String AUTH_WECHAT = BASEURL + "system/technician/auth_wechat";
    //6-个人信息
    public static final String INFO_USER = BASEURL + "system/technician/info";
    //7-修改密码
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
    //13-查询app最新版本
    public static final String LATEST_VERSION = BASEURL + "system/upgrade_pack/latest_version";
    //14-我的订单列表
    public static final String MY_ORDER = BASEURL + "biz/carWashOrder/myOrder";
    //15-接单
    public static final String RECEIVE = BASEURL + "biz/carWashOrder/receive";
    //16-开始服务
    public static final String START_SERVICE = BASEURL + "biz/carWashOrder/startService";
    //17-完成服务
    public static final String END_SERVICE = BASEURL + "biz/carWashOrder/endService";
    //18-转单
    public static final String TRANSFER_ORDER = BASEURL + "biz/carWashOrder/transferOrder";
    //19-订单详情
    public static final String GET_ORDER_DETAILS = BASEURL + "biz/carWashOrder/biz/carWashOrder/getOrder";
}
