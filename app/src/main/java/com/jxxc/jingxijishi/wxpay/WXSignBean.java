package com.jxxc.jingxijishi.wxpay;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dingsheng on 2016/10/17.
 */

public class WXSignBean implements Serializable { /**
 * appId : wxbded21af6cebcf4d
 * nonceStr : EoaXUYahwdVNxPUf
 * package : Sign=WXPay
 * partnerId : 1379860402
 * prepayId : wx2016082416432713d243387e0870258214
 * sign : C4B91F5B8033DB4B7E0D31B9DD84AAF5
 * timeStamp : 1472028208
 */
    private String appId;
    private String nonceStr;
    @SerializedName("package")
    private String packageX;
    private String partnerId;
    private String prepayId;
    private String sign;
    private String timeStamp;
    public String getappId() {
        return appId;
    }

    public void setappId(String appId) {
        this.appId = appId;
    }

    public String getnonceStr() {
        return nonceStr;
    }

    public void setnonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getpartnerId() {
        return partnerId;
    }

    public void setpartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getprepayId() {
        return prepayId;
    }

    public void setprepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String gettimeStamp() {
        return timeStamp;
    }

    public void settimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }





}
