package com.jxxc.jingxijishi.entity.requestparameter;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/13.16:54
 * email:458079442@qq.com
 */

public class req_Login implements Serializable{
    String mobile;
    String password;

    public req_Login(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }
}
