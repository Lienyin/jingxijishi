package com.jxxc.jingxijishi.ui.withdrawdeposit;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
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

public class WithdrawDepositPresenter extends BasePresenterImpl<WithdrawDepositContract.View> implements WithdrawDepositContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

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
