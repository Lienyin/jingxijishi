package com.jxxc.jingxijishi.ui.updatepassword;

import android.content.Context;

import com.jxxc.jingxijishi.Api;
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

public class UpdatePasswordPresenter extends BasePresenterImpl<UpdatePasswordContract.View> implements UpdatePasswordContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        OkGo.<HttpResult>post(Api.UPDATE_PASSWORD)
                .params("oldPassword",oldPassword)
                .params("newPassword",newPassword)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code == 0){
                            mView.updatePasswordCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
