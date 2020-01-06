package com.jxxc.jingxijishi.ui.orderdetails;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
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

public class OrderDetailsPresenter extends BasePresenterImpl<OrderDetailsContract.View> implements OrderDetailsContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 订单详情
     * @param orderId
     */
    @Override
    public void getOrderDetails(String orderId) {
        OkGo.<HttpResult<AwaitReceiveOrderEntity>>post(Api.GET_ORDER_DETAILS)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult<AwaitReceiveOrderEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<AwaitReceiveOrderEntity>> response) {
                        StyledDialog.dismissLoading();
                        AwaitReceiveOrderEntity d = response.body().data;
                        if (response.body().code == 0){
                            mView.getOrderDetailsCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 接单
     * @param orderId
     */
    @Override
    public void receive(String orderId) {
        OkGo.<HttpResult>post(Api.RECEIVE)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.receiveCallBack();
                            toast(mContext,response.body().message);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 开始服务
     * @param orderId
     */
    @Override
    public void startService(String orderId) {
        OkGo.<HttpResult>post(Api.START_SERVICE)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.startServiceCallBack();
                            toast(mContext,response.body().message);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 完成服务
     * @param orderId
     */
    @Override
    public void endService(String orderId) {
        OkGo.<HttpResult>post(Api.END_SERVICE)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.endServiceCallBack();
                            toast(mContext,response.body().message);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 转单
     * @param orderId
     */
    @Override
    public void transferOrder(String orderId) {
        OkGo.<HttpResult>post(Api.TRANSFER_ORDER)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.transferOrderCallBack();
                            toast(mContext,response.body().message);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
