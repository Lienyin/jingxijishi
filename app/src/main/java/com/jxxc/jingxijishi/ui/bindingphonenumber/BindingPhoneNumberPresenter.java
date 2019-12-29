package com.jxxc.jingxijishi.ui.bindingphonenumber;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.LoginEntity;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.jxxc.jingxijishi.utils.MD5Utils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingPhoneNumberPresenter extends BasePresenterImpl<BindingPhoneNumberContract.View> implements BindingPhoneNumberContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 绑定微信
     * @param phonenumber
     * @param password
     * @param wxOpenId
     */
    @Override
    public void getThirdPartyInfo(String phonenumber, String password, String wxOpenId) {
        OkGo.<HttpResult<LoginEntity>>post(Api.AUTH_WECHAT)
                .params("phonenumber",phonenumber)
                .params("password", MD5Utils.shaPassword(password).trim().toUpperCase())
                .params("wxOpenId",wxOpenId)
                .execute(new JsonCallback<HttpResult<LoginEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<LoginEntity>> response) {
                        StyledDialog.dismissLoading();
                        LoginEntity d = response.body().data;
                        if (response.body().code == 0) {
                            SPUtils.put(mView.getContext(), SPUtils.K_TOKEN,d.token);
                            toast(mContext,"绑定成功");
                            mView.gotoUserMain();
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
