package com.jxxc.jingxijishi.ui.login;

import android.widget.TextView;

import com.jxxc.jingxijishi.entity.backparameter.ThirdPartyLogin;
import com.jxxc.jingxijishi.mvp.BaseView;
import com.jxxc.jingxijishi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void loginCallBack();
        void getThirdPartyLogin(ThirdPartyLogin data);
    }

    interface  Presenter extends BasePresenter<View> {
        void login(String phonenumber,String password);//账户密码登录
        void getAuthCode(String phonenumber, TextView tvAuthCode);
        void loginCode(String phonenumber,String code);//验证码登录
        void thirdPartyLogin(String wxOpenId);//第三方登录
    }
}
