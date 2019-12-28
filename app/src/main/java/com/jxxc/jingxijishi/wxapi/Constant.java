package com.jxxc.jingxijishi.wxapi;

import java.io.Serializable;

/**
 * Created by ds on 2017/12/2.
 */

public class Constant implements Serializable {
    //微信配置
    public static final String APP_ID = "wxc70188f849c9c3cb";
    public static final String WECHAT_SECRET = "b29ecd4804fe422783a74086b3333218";
    //支付宝配置
    /** 支付宝支付业务：入参app_id */
    public static final String ALIPAY_APPID = "2019032063588814";
    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String ALIPAY_PID = "2088821834281814";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String ALIPAY_TARGET_ID = "zhizukeji";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA_PRIVATE = "";
    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCTRU2UFCbeiEaQU/R4eGq77nT6YIAqR+O4FZtQWlZ1TewFDgpm/DNPARiQsqAfAHaRd/iOjOpfagQu/uce4u1eV9K6yKeMNLs5h1aIDBVXdPlFwJtvSssO24DxQ2gV3IOk33JLyT+p+e/BRQoq6ThjdudJYXxZA1GSaIMYfzWTFPSfKLucxYKxe4G+6qRY/4SyxC4v/sicHC/CtzlmLuf3EjMIhcZ4Zc+B1r+cS8rOeot8i31MYstvo7H4fCJDEASYxHxSFDePoAyfbV7nvDAVnvGkiAtNzGY+ueyTSGUlmlxTPsmo4Bprw5cFDA05p10/SmbnP7ibv2i8xQ6xY/h/AgMBAAECggEAXIvQlJ2VT4KT1V7s4kfJyw+usyixYeCLWcM6NuSOMGknSIXqQ415MMyfg069EL5ZxFCAAhTLs+oLdvz4gJBaWk8WPgqaP6FjhmNmHVh6kNsRSqQfCXEW6d9GyFZzsklA8Pr5+GYfe9F0uR+JpC2rIdY2wgZg/g730+mZeGt4X1R1vZUDXYxX5tvStwa3oauDyUb08SBdrVw2zjuMiRDhuQxtdPQ/HhqDWgCPd+YPDCHw72Ic2yEFxZtLddzkGPy11Y1QTDrvlvnYUwrBL5BLLdRnlgkUyqZKaNUB4j2Vm+1hs+CRVrnaovXaiP5fwpSY/kxSCnwGEt4RfbuuqXpzAQKBgQDdexRC9+atgPhGeD1xA+lZ2aMMt5m4KQwUIxIS3vCypuVFIg2EqlXP/zUmklu4rupBlhkGks3i+LRcz0RuFSPA2oV3rY3hZsF6Msf/hX3t0M6u8CJzSE6AgGKQ2jOYI+zI1oWwv0IT1iYiK8/uTsc6n82yQwT0ONinXFAGaNsQ1QKBgQCqOUv2Pbx8jeD1iiYMSx8Volyd1vHNVKrV8H0AtM9Y21N6Ut3Gw1xfDzzjrYI6Y69WUu9N9w8I2JgaYBDBK+3Dh7mC/fclUJfc77XBroRPAsb6PFlklFdgYNsro//3N12gznY6+w1PAh5d+xgQMSn1jSd4Orf6mMflmtr/TNquAwKBgQCHD5A6imwccCJ27sohCg6aITLiBXBzMmzQNtbFtTg46be0YeC2fx0XWvzxiZzvUZM+7aZTMODmwV1Tn32OV8vWxitP4QE9a+keeKsnsiMqDASN/5gyjux4RlJVHQsLgxTZIKCkOO21pQjaG7eQDnBF2r+YLnBt8eCEprGSRw0YBQKBgQCBK3dkBNb54SO6zzkuDPcqkIkdURUtzbjRHWuN1Kae89e1tBoMkQSQMdzvnCN9MVXE+KwVCowDF3DcWtd77ly/CkgQia04SJ7kjZXrwgUue5oIGzFubE6dmQHXbNx4eS/4LZN0EDu6H45xnG9XIieH6LPxvQUNCVBvAAJ8xpIOhwKBgQCzB9Fxh+TWb/K/f3EqdtqfxNjpXabiwuH8nsfP/CagTygcayhPvZdt7k6HKh79KeBbwxdTAiCkZSD+r1QVMdZMGwsKAZqo1jrCAccXs3fhG4/LbEy3rSbTA2inIGFj2SutfsN51RjrKgbFMi2SDHhs9Q6AF9v/igLAl3edhDYAyQ==";
}
