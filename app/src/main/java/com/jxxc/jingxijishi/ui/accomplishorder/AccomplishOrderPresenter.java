package com.jxxc.jingxijishi.ui.accomplishorder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.entity.backparameter.UpdateInfoEntity;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AccomplishOrderPresenter extends BasePresenterImpl<AccomplishOrderContract.View> implements AccomplishOrderContract.Presenter{
    ISListConfig config;

    @Override
    public void gotoImageSelect(AccomplishOrderActivity malfunctionRepairActivity, int requestCodeChoose) {
        ISNav.getInstance().toListActivity(malfunctionRepairActivity, config, requestCodeChoose);
    }

    /**
     * 查询订单详情
     * @param orderId
     */
    @Override
    public void getOrderDetails(String orderId) {
        OkGo.<HttpResult<AwaitReceiveOrderEntity>>post(Api.GET_ORDER_DETAILS)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult<AwaitReceiveOrderEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<AwaitReceiveOrderEntity>> response) {
                        AwaitReceiveOrderEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getOrderDetailsCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void initImageSelecter() {
        config = new ISListConfig.Builder()
                .multiSelect(true)
                .rememberSelected(true)
                .btnBgColor(Color.TRANSPARENT)
                .btnTextColor(Color.WHITE)
                .statusBarColor(ContextCompat.getColor(mView.getContext().getApplicationContext(), R.color.public_all))
                .backResId(R.mipmap.back)
                .title("图片选择")
                .titleColor(Color.WHITE)
                .titleBgColor(ContextCompat.getColor(mView.getContext().getApplicationContext(),R.color.public_all))
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                .needCamera(true)
                .maxNum(3)
                .build();

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 完成服务
     * @param orderId
     */
    @Override
    public void endService(String orderId,String imgUrl) {
        OkGo.<HttpResult>post(Api.END_SERVICE)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
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
     * 上传文件
     * @param uriList
     */
    @Override
    public void commit(List<String> uriList) {
        final List<File> fileList = new ArrayList<>();
        for (String path:uriList) {
            fileList.add(new File(path));
        }
        StyledDialog.buildLoading("正在上传").setActivity((Activity)mContext).show();
        if (fileList.size() == 1){
            OkGo.<HttpResult<UpdateInfoEntity>>post(Api.UPLOAD)
                    .isMultipart(true)
                    .params("file0",fileList.get(0))
                    //.addFileParams("file",fileList)
                    .execute(new JsonCallback<HttpResult<UpdateInfoEntity>>() {
                        @Override
                        public void onSuccess(Response<HttpResult<UpdateInfoEntity>> response) {
                            UpdateInfoEntity d = response.body().data;
                            if (response.body().code == 0) {
                                mView.commitCallback(d.fileName);
                                toast(mContext,response.body().message);
                            }else{
                                toast(mContext,response.body().message);
                            }
                        }
                    });
        }else if (fileList.size() == 2){
            OkGo.<HttpResult<UpdateInfoEntity>>post(Api.UPLOAD)
                    .isMultipart(true)
                    .params("file0",fileList.get(0))
                    .params("file1",fileList.get(1))
                    //.addFileParams("file",fileList)
                    .execute(new JsonCallback<HttpResult<UpdateInfoEntity>>() {
                        @Override
                        public void onSuccess(Response<HttpResult<UpdateInfoEntity>> response) {
                            UpdateInfoEntity d = response.body().data;
                            if (response.body().code == 0) {
                                mView.commitCallback(d.fileName);
                                toast(mContext,response.body().message);
                            }else{
                                toast(mContext,response.body().message);
                            }
                        }
                    });
        }else if (fileList.size() == 3){
            OkGo.<HttpResult<UpdateInfoEntity>>post(Api.UPLOAD)
                    .isMultipart(true)
                    .params("file0",fileList.get(0))
                    .params("file1",fileList.get(1))
                    .params("file2",fileList.get(2))
                    //.addFileParams("file",fileList)
                    .execute(new JsonCallback<HttpResult<UpdateInfoEntity>>() {
                        @Override
                        public void onSuccess(Response<HttpResult<UpdateInfoEntity>> response) {
                            UpdateInfoEntity d = response.body().data;
                            if (response.body().code == 0) {
                                mView.commitCallback(d.fileName);
                                toast(mContext,response.body().message);
                            }else{
                                toast(mContext,response.body().message);
                            }
                        }
                    });
        }
    }
}
