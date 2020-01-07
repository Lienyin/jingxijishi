package com.jxxc.jingxijishi.ui.mywallet;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyWalletPresenter extends BasePresenterImpl<MyWalletContract.View> implements MyWalletContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 个人信息
     */
    @Override
    public void getUserInfo() {
        OkGo.<HttpResult<UserInfoEntity>>post(Api.INFO_USER)
                .tag(this)
                .execute(new JsonCallback<HttpResult<UserInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<UserInfoEntity>> response) {
                        UserInfoEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getUserInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 查询账户信息
     */
    @Override
    public void getAccountInfo() {
        OkGo.<HttpResult<AccountInfoEntity>>post(Api.GET_ACCOUNT_INFO)
                .tag(this)
                .execute(new JsonCallback<HttpResult<AccountInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<AccountInfoEntity>> response) {
                        AccountInfoEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getAccountInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
