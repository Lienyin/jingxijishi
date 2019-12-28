package com.jxxc.jingxijishi.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.jxxc.jingxijishi.http.EventCenter;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                EventBus.getDefault().post(new EventCenter(EventCenter.PAY_SUCESS));
                //微信支付成功回调发广播
                Intent intent = new Intent();
                intent.setAction("wei_xin_pay_sucess");
                sendOrderedBroadcast(intent,null);
                Toast.makeText(this.getApplicationContext(), "微信支付成功!", Toast.LENGTH_SHORT).show();
            } else {
                //微信支付成功回调发广播
                Intent intent = new Intent();
                intent.setAction("wei_xin_pay_defeated");
                sendOrderedBroadcast(intent,null);
                Toast.makeText(this.getApplicationContext(), "微信支付失败!", Toast.LENGTH_SHORT).show();
            }
        }
        this.finish();
    }

    @Override
    protected void onDestroy() {
        if (api!=null){
            api.unregisterApp();
            api.detach();
        }
        super.onDestroy();
    }
}