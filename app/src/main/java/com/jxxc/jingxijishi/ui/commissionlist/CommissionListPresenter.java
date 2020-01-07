package com.jxxc.jingxijishi.ui.commissionlist;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.CommissionListEntity;
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

public class CommissionListPresenter extends BasePresenterImpl<CommissionListContract.View> implements CommissionListContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void CommissionDetail(String createTime, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<CommissionListEntity>>>post(Api.COMMISSION_DETAIL)
                .params("createTime",createTime)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<CommissionListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<CommissionListEntity>>> response) {
                        List<CommissionListEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.CommissionDetailCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void CommissionDetailMore(String createTime, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<CommissionListEntity>>>post(Api.COMMISSION_DETAIL)
                .params("createTime",createTime)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<CommissionListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<CommissionListEntity>>> response) {
                        List<CommissionListEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.CommissionDetailMoreCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
