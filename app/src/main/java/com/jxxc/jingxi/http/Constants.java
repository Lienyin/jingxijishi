package com.jxxc.jingxi.http;

/**
 * @author:feisher on 2017/10/17 9:49
 * Email：458079442@qq.com
 */
public class Constants {

    public static final float DESIGN_WIDTH = 750f; //绘制页面时参照的设计图宽度
    public static final float DESIGN_HIGHT = 1334f; //绘制页面时参照的设计图宽度
    public static final int REFRESH_CODE = 1;
    public static final int LOADMORE_CODE = 2;
    public static final int RESULT_CAPTURE_IMAGE = 100;// 照相的requestCode
    public static final int REQUEST_CODE_TAKE_VIDEO = 101;// 摄像的照相的requestCode
    public static final int RESULT_CAPTURE_RECORDER_SOUND = 102;// 录音的requestCode
    public static final int RESULT_LOCATION_CODE = 103;// 定位的requestCode
    public static final String MOBAPI_APP_KEY = "1a1956ca75731";//天气预报，老黄历
    public static final String ACTION_INIT_APP = "com.yusong.club.service.action.INIT";
    public static final String SALT = "67884E9%^&*67899A26C18DC28"; //标准协议上没有*

    public static final int LOOP_TTS_LIMIT = 1; //轮询播报语音次数


    private static class SingletonHolder {
        private static final Constants INSTANCE = new Constants();
    }

    private Constants() {

    }

    public static final Constants getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //SP 常量
    public static final String USER_NAME = "user_name";
    public static final String PASS_WORD = "pass_word";
    public static final String TOKEN = "token";

    public static final String TEST_TOKEN = "7730cd3eadef2a49a8abbe4a249ffb51904b";

    public static int authStep;
    //用户协议
    public String hotline = "400-636-2775";
    public String registerProtocol;
    public String faq;
    public String aboutUs;
    public  int lowVolume;
    public String chargeStandard;//收费标准

}
