package com.jxxc.jingxijishi.ui.login;

import android.widget.TextView;

import com.jxxc.jingxijishi.mvp.BaseView;
import com.jxxc.jingxijishi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void loginCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void login(String phonenumber,String password);
        void getAuthCode(String phonenumber, TextView tvAuthCode);
    }
}
