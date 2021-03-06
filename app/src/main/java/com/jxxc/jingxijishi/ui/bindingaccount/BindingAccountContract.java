package com.jxxc.jingxijishi.ui.bindingaccount;

import android.content.Context;
import android.widget.TextView;

import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingAccountContract {
    interface View extends BaseView {
        void bindingAccountCallBack();
        void getAccountInfoCallBack(AccountInfoEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void bindingAccount(String alipayAccount,String openId,String code);
        void getCode(String phonenumber, TextView textView);
        void getAccountInfo();
    }
}
