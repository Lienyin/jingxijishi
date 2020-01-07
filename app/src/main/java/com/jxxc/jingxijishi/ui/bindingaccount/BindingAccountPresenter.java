package com.jxxc.jingxijishi.ui.bindingaccount;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingAccountPresenter extends BasePresenterImpl<BindingAccountContract.View> implements BindingAccountContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 绑定支付宝
     * @param alipayAccount
     * @param alipayName
     * @param code
     */
    @Override
    public void bindingAliPay(String alipayAccount, String alipayName, String code) {
//        OkGo.<HttpResult>post(Api.BINDING_TIXIAN)
//                .params("userName",userName)
//                .params("phone",phone)
//                .params("code",code)
//                .execute();
    }
}
