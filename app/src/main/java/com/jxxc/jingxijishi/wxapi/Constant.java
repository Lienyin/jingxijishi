package com.jxxc.jingxijishi.wxapi;

import java.io.Serializable;

/**
 * Created by ds on 2017/12/2.
 */

public class Constant implements Serializable {
    //微信配置
    public static final String APP_ID = "wx5daa1aa8174bd15f";
    public static final String WECHAT_SECRET = "408cb17987a86ff2518d12917f13d9e7";
    //支付宝配置
    /** 支付宝支付业务：入参app_id */
    public static final String ALIPAY_APPID = "2021001103654185";
    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String ALIPAY_PID = "2088731215077504";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String ALIPAY_TARGET_ID = "jingxi";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA_PRIVATE = "";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCeBaAfdQaK/gABgzGH87aJGu70f4t8hUOnwrFj2lUS7vZu3PK5rpMGXD1IEQMLX/bpfoz9YnZPHQPuxfKaSDdAYpSoPjnQ4V+Ye3f96Pw97IQV+mbUOcBAc6foDz2+C2Sh0k/iu3EpUA7401XaDm5Xeb2cPLQ9kYiLrH62hRHMwcRtravkeqb/qHd/cl7H0X2EHxMRxD5c5KRYTzsDGmjcUiupagNRhaXb7hcpF0lhCXAzedcfZmGPAOOb85YfZdqALWEsfGkbajeqMq8uASmh5iisTUGTpv8IRs1nJbZsNm/fNU6TW2K7psg542SezBYcA6jJsE4leSVQEehGNsuZAgMBAAECggEAKeiqB828l343uC74IutC3OfUueldr/+T6tkavLW53FSd7NcSVKZ0/bnW0tcYLJsd290hPU4wvFigo2HCLqK6bIv4iwo9wSUUelc8go2Y4D9q5H1NajmKwE0sGcJ5CH7zCGvDnglCQMenwNwjp7ouD2sNG3OKBc4kHY746CUwjFHmRz3ZhRNNgM66gycPz3D+IGseZLMmSpYH3aqr3R+zSP4pZdBvrRrS+viuk90Uffv7nvoSWbimhvgq6DDsNpOoe3nPOFpgnWOfTxLf8HbcM9COHaKzzskeG9PXNum1vBGcmWFdDqVIY/8ybRZyWQ8P1XDylAyLJZ4iQH1VbSZgEQKBgQD5LF/WYqC69qVKZ+NvBTsN8QWDN7WutlrIbKqebu3bnyFiFG6psL9CO+mqu5kWXwAJGmX21YKgm+rNdj17uANLZMyP7y/1biX3JRSol99xYp8X5O+zrpIbiNl9wDqUvhAp3Kti45NOYOsbqbXZNJJNVlP1wWhrIMZJ0imlDC/M3QKBgQCiWfF9CGpfcqh8la/MAY5MFkj2NFI9YThq0rMJuMB7JFYti9KxDL5vNHGR7wmDVmVYYnIqJDpxfmFd6kTKAdFNrhJvf0oNMqG5mDe97LXK4kEpDIT9xF7E/tEGJQMhPd4IscCSkB/gfY7RoZbRsWdTuvePffJiNphvnATWax7/7QKBgFDWsqkNCnRdvLZtCf5kxgn53f36PvlVtBI+HwSTnJuZ/uyhEF4BN6u3YFHB1yLI42wQom/4SQ7uRrkt2TigNIFrTGqOARMxDyQlZZRk4VsZ0d+gqJu00TYZqeYATYEgQII0U24bwxi0nNI2twam5agtj8O1YbkPwwUEJ/qh/4gJAoGATrMdDgg7UGOLa0FAg7spWJbg7YB1cU2khJv4sDjADbGSmOeTzcGous2IVD60tNb3r72hK+596jBiUJ1UlTwGUwqT4zqoEfe+T/KK6uVJRr0g6x4nhcBgVExV1Q2yE7Yi4MIR4Lo+yZrz1YjoTGQb+4nBoexix9tBT7Eo4yvwTg0CgYEAx7l/bfHQpkr9+0wP3VIRyQPlr25ftUGMccHeiq83fFL+VvXBhMV2oo9kcsFANpVqTgPAwq1LpRium2wnbJT/4AK3N8OP6rlpgvajDSN33iYFA3ARmf5x9qnKL31hW0TldjE8dvr50Mjwe87ncnpk1YP4wptrJ19uy0AU/ZTn+tw=";
        }
