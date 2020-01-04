package com.jxxc.jingxijishi.ui.orderlist;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.OrderListEntity;
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

public class OrderListPresenter extends BasePresenterImpl<OrderListContract.View> implements OrderListContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void myOrder(int status,int pageNum,int pageSize) {
        OkGo.<HttpResult<List<OrderListEntity>>>post(Api.MY_ORDER)
                .params("status",status)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<OrderListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<OrderListEntity>>> response) {
                        List<OrderListEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.myOrderCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void myOrderMore(int status,int pageNum,int pageSize) {
        OkGo.<HttpResult<List<OrderListEntity>>>post(Api.MY_ORDER)
                .params("status",status)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<OrderListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<OrderListEntity>>> response) {
                        List<OrderListEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.myOrderMoreCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
