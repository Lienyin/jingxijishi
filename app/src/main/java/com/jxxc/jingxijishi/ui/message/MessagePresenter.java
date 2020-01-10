package com.jxxc.jingxijishi.ui.message;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.MessageListEntity;
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

public class MessagePresenter extends BasePresenterImpl<MessageContract.View> implements MessageContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void MessageList(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<MessageListEntity>>>post(Api.MESSAGE_LIST)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<MessageListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<MessageListEntity>>> response) {
                        List<MessageListEntity> d = response.body().data;
                        if(response.body().code==0){
                            mView.MessageListCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void MessageListMore(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<MessageListEntity>>>post(Api.MESSAGE_LIST)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<MessageListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<MessageListEntity>>> response) {
                        List<MessageListEntity> d = response.body().data;
                        if(response.body().code==0){
                            mView.MessageListMoreCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
