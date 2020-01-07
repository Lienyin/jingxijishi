package com.jxxc.jingxijishi.ui.bindingaccount;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingAccountPresenter extends BasePresenterImpl<BindingAccountContract.View> implements BindingAccountContract.Presenter{

    private CountDownTimer timer;

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 绑定账户
     * @param alipayAccount
     * @param alipayName
     * @param code
     */
    @Override
    public void bindingAliPay(String alipayName,String alipayAccount,String openId,String code,String phonenumber) {
        OkGo.<HttpResult>post(Api.BINDING_TIXIAN)
                .params("alipayName",alipayName)
                .params("alipayAccount",alipayAccount)
                .params("openId",openId)
                .params("code",code)
                .params("phonenumber",phonenumber)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (response.body().code==0){
                            mView.bindingAliPayCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 获取验证码
     * @param phonenumber
     */
    @Override
    public void getCode(String phonenumber, final TextView textView) {
        OkGo.<HttpResult>post(Api.SMS_CODE)
                .params("phonenumber",phonenumber)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            timer = initCountDownTimer(textView);
                            timer.start();
                            toast(mContext,"验证码已发送");
                        }else{
                            toast(mContext,response.body().message);
                        }
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
