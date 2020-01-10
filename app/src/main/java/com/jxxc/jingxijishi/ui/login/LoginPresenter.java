package com.jxxc.jingxijishi.ui.login;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.LoginEntity;
import com.jxxc.jingxijishi.entity.backparameter.ThirdPartyLogin;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.jxxc.jingxijishi.Api;
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

    private CountDownTimer timer;
    /**
     * 账户密码登录接口
     * @param phonenumber
     * @param password
     */
    @Override
    public void login(final String phonenumber, String password) {
        OkGo.<HttpResult<LoginEntity>>post(Api.LOGIN)
                .params("phonenumber",phonenumber)
                .params("password", MD5Utils.shaPassword(password).trim().toUpperCase())
                .execute(new JsonCallback<HttpResult<LoginEntity>>(){
                    @Override
                    public void onSuccess(Response<HttpResult<LoginEntity>> response) {
                        hideLoading();
                        LoginEntity d = response.body().data;
                        if (response.body().code == 0){
                            mView.loginCallBack();
                            SPUtils.put(SPUtils.K_SESSION_MOBILE,phonenumber);
                            SPUtils.put(SPUtils.K_TOKEN,d.token);
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 获取短信验证码
     * @param phonenumber
     * @param tvAuthCode
     */
    @Override
    public void getAuthCode(String phonenumber, final TextView tvAuthCode) {
        OkGo.<HttpResult>post(Api.SMS_CODE)
                .params("phonenumber", phonenumber)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        HttpResult data = response.body();
                        if (data.code == 0) {
                            timer = initCountDownTimer(tvAuthCode);
                            timer.start();
                            toast(mContext,"验证码已发送");
                        } else {
                            toast(mContext,data.message);
                        }
                    }
                });
    }

    /**
     * 验证码登录
     * @param phonenumber
     * @param code
     */
    @Override
    public void loginCode(final String phonenumber, String code) {
        OkGo.<HttpResult<LoginEntity>>post(Api.LOGIN_CODE)
                .params("phonenumber",phonenumber)
                .params("code", code)
                .execute(new JsonCallback<HttpResult<LoginEntity>>(){
                    @Override
                    public void onSuccess(Response<HttpResult<LoginEntity>> response) {
                        hideLoading();
                        LoginEntity d = response.body().data;
                        if (response.body().code == 0){
                            mView.loginCallBack();
                            SPUtils.put(SPUtils.K_SESSION_MOBILE,phonenumber);
                            SPUtils.put(SPUtils.K_TOKEN,d.token);
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 第三方登录
     * @param wxOpenId
     */
    @Override
    public void thirdPartyLogin(String wxOpenId) {
        OkGo.<HttpResult<ThirdPartyLogin>>post(Api.LOGIN_WECHAT)
                .params("wxOpenId",wxOpenId)
                .execute(new JsonCallback<HttpResult<ThirdPartyLogin>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<ThirdPartyLogin>> response) {
                        ThirdPartyLogin d = response.body().data;
                        mView.getThirdPartyLogin(d);
                        SPUtils.put(SPUtils.K_TOKEN,d.token);
                    }
                });
    }

    @NonNull
    private CountDownTimer initCountDownTimer(final TextView tvAuthCode) {
        return new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (AppUtils.isEmpty(mView)) {
                    return;
                }
                if (tvAuthCode != null) {
                    tvAuthCode.setEnabled(false);
                    tvAuthCode.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.public_all));
                    tvAuthCode.setText("重新发送(" + (millisUntilFinished / 1000) + ")");
                }
            }

            @Override
            public void onFinish() {
                if (AppUtils.isEmpty(mView)) {
                    return;
                }
                if (tvAuthCode != null) {
                    tvAuthCode.setEnabled(true);
                    tvAuthCode.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.black));
                    tvAuthCode.setText("获取验证码");
                }
            }
        };
    }
}
