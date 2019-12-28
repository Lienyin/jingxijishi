package com.jxxc.jingxijishi.ui.bindingphonenumber;

import android.content.Context;

import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingPhoneNumberContract {
    interface View extends BaseView {
        void gotoUserMain();
    }

    interface  Presenter extends BasePresenter<View> {
        void getThirdPartyInfo(String phonenumber,String password,String wxOpenId);
    }
}
