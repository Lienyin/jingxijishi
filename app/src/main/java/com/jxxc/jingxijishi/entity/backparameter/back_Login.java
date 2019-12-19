package com.jxxc.jingxijishi.entity.backparameter;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/20.15:05
 * email:458079442@qq.com
 */

public class back_Login implements Serializable{
//{"data":{"expireIn":86400,"id":2,"token":"49338d17a2b28a4497ab5b1ada234f97ac89"},"code":0,"message":null}
    public int id;
    public String token;
    public int expireIn;
    public int loginCode;

    public String photoPath;
    public String fullname;
    public String aliUserId;

}
