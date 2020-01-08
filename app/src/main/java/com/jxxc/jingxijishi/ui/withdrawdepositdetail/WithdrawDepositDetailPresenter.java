package com.jxxc.jingxijishi.ui.withdrawdepositdetail;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.WithdrawDepositDetailEntity;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WithdrawDepositDetailPresenter extends BasePresenterImpl<WithdrawDepositDetailContract.View> implements WithdrawDepositDetailContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void drawMoneyRecord(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<WithdrawDepositDetailEntity>>>post(Api.DRAW_MONEY_RECORD)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<WithdrawDepositDetailEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<WithdrawDepositDetailEntity>>> response) {
                        List<WithdrawDepositDetailEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.drawMoneyRecordCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void drawMoneyRecordMore(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<WithdrawDepositDetailEntity>>>post(Api.DRAW_MONEY_RECORD)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<WithdrawDepositDetailEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<WithdrawDepositDetailEntity>>> response) {
                        List<WithdrawDepositDetailEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.drawMoneyRecordMoreCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
