package com.jxxc.jingxijishi.ui.updatepassword;

import android.content.Context;

import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpdatePasswordContract {
    interface View extends BaseView {
        void updatePasswordCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void updatePassword(String oldPassword,String newPassword);
    }
}
