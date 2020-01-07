package com.jxxc.jingxijishi.ui.mywallet;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyWalletContract {
    interface View extends BaseView {
        void getUserInfoCallBack(UserInfoEntity data);
        void getAccountInfoCallBack(AccountInfoEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
        void getAccountInfo();
    }
}
