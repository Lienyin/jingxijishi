package com.jxxc.jingxijishi.wxpay;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.Serializable;


public class WxPay implements Serializable {
    Context context;
    PayReq req;
    IWXAPI msgApi;
    WXSignBean wxSignBean;

    public WxPay(Context context, WXSignBean wxSignBean) {
        this.context = context;
        this.wxSignBean = wxSignBean;
    }

    public void wxPayMethod() {
        msgApi = WXAPIFactory.createWXAPI(context, wxSignBean.getappId());
        msgApi.registerApp(wxSignBean.getappId());
        if (msgApi.isWXAppInstalled()) {
            req = new PayReq();
            GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
            getPrepayId.execute();
        } else {
            Toast.makeText(context, "您还没安装微信,不能进行微信支付", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * getprepayid
     *
     * @author Administrator
     */
    private class GetPrepayIdTask extends
            AsyncTask<Void, Void, Void> implements Serializable {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Void v) {
            genPayReq();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            this.cancel(true);
        }
    }


    /**
     * 设置参数
     */
    private void genPayReq() {
        req.appId = wxSignBean.getappId();
        req.partnerId = wxSignBean.getpartnerId();
        req.prepayId = wxSignBean.getprepayId();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = wxSignBean.getnonceStr();
        req.timeStamp = wxSignBean.gettimeStamp();
        req.sign = wxSignBean.getSign();
        Log.e("req", "req, appId = " + req.appId+"req, partnerId = " + req.partnerId+"req, prepayId = " + req.prepayId+"req, packageValue = " + req.packageValue+"req, nonceStr = " + req.nonceStr+"req, timeStamp = " + req.timeStamp+"timeStamp, sign = " + req.sign);
        msgApi.sendReq(req);
    }

}
