package com.jxxc.jingxijishi.ui.login;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.entity.backparameter.back_Login;
import com.jxxc.jingxijishi.entity.requestparameter.req_Login;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.jxxc.jingxijishi.utils.MD5Utils;

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
