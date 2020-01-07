package com.jxxc.jingxijishi.ui.bindingaccount;

import android.content.Context;

import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingAccountContract {
    interface View extends BaseView {
        void bindingAliPayCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void bindingAliPay(String alipayAccount,String alipayName,String code);
    }
}
