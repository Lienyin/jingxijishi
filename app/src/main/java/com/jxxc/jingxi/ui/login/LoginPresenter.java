package com.jxxc.jingxi.ui.login;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.back_Login;
import com.jxxc.jingxi.entity.requestparameter.req_Login;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.jxxc.jingxi.utils.MD5Utils;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{

    /**
     * 登录接口
     * @param userName
     * @param passWord
     */
    @Override
    public void login(String userName, String passWord) {
        OkGo.<HttpResult<back_Login>>post(Api.LOGIN)
                .params("userName",userName)
                .params("password", MD5Utils.md5(passWord))
                .execute(new JsonCallback<HttpResult<back_Login>>(){
                    @Override
                    public void onSuccess(Response<HttpResult<back_Login>> response) {
                        hideLoading();
                        if (response.body().code == 0){
                            mView.loginCallBack();
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }
}
