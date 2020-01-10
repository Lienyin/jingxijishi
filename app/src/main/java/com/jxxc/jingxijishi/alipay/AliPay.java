package com.jxxc.jingxijishi.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.jxxc.jingxijishi.http.EventCenter;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ds
 */

public class AliPay implements Serializable {
    Activity mActivity;
    AliPayCallBack aliPayCallBack;
    String signInfo;
    private static final int SDK_PAY_FLAG = 1;

    public AliPay(Activity mActivity, AliPayCallBack aliPayCallBack, String signInfo) {
        this.mActivity = mActivity;
        this.aliPayCallBack = aliPayCallBack;
        this.signInfo = signInfo;
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay() {
        final String payInfo = signInfo;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(payInfo, true);
                Log.e("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        EventBus.getDefault().post(new EventCenter(EventCenter.PAY_SUCESS));
                        aliPayCallBack.paySuccess();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")||TextUtils.equals(resultStatus, "6004")) {
                            aliPayCallBack.payConfirm();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            aliPayCallBack.payFail();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    public interface AliPayCallBack {
        void paySuccess();

        void payFail();

        void payConfirm();
    }


}
