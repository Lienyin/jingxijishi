package com.jxxc.jingxijishi.ui.login;

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
        void login(String userName,String passWord);
    }
}
