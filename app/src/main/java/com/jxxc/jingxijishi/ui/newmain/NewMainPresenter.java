package com.jxxc.jingxijishi.ui.newmain;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
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

public class NewMainPresenter extends BasePresenterImpl<NewMainContract.View> implements NewMainContract.Presenter{

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
     * 抢单大厅列表
     * @param lng
     * @param lat
     */
    @Override
    public void awaitReceiveOrder(double lng, double lat) {
        OkGo.<HttpResult<List<AwaitReceiveOrderEntity>>>post(Api.AWAIT_RECEIVE_ORDER)
                .params("lng",lng)
                .params("lat",lat)
                .execute(new JsonCallback<HttpResult<List<AwaitReceiveOrderEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<AwaitReceiveOrderEntity>>> response) {
                        List<AwaitReceiveOrderEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.awaitReceiveOrderCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 待服务列表
     */
    @Override
    public void unfinishedOrder() {
        OkGo.<HttpResult<List<AwaitReceiveOrderEntity>>>post(Api.UNFINISHED_ORDER)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<AwaitReceiveOrderEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<AwaitReceiveOrderEntity>>> response) {
                        List<AwaitReceiveOrderEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.unfinishedOrderCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 修改服务状态
     * @param type
     */
    @Override
    public void updateServiceStatic(int type) {
        OkGo.<HttpResult>post(Api.UPDATE_INFO)
                .params("isOnline",type)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code == 0){
                            mView.updateServiceStaticCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
