package com.jxxc.jingxijishi.ui.bindingaccount;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.aliapi.AuthResult;
import com.jxxc.jingxijishi.aliapi.OrderInfoUtil2_0;
import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;
import com.jxxc.jingxijishi.wxapi.Constant;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingAccountActivity extends MVPBaseActivity<BindingAccountContract.View, BindingAccountPresenter> implements BindingAccountContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rb_binding_zfb)
    RadioButton rb_binding_zfb;
    @BindView(R.id.rb_binding_wx)
    RadioButton rb_binding_wx;
    @BindView(R.id.ll_date_caiji)
    LinearLayout ll_date_caiji;
    @BindView(R.id.et_phone_number)
    TextView et_phone_number;
    @BindView(R.id.et_msg_code)
    EditText et_msg_code;
    @BindView(R.id.btn_binding)
    Button btn_binding;
    @BindView(R.id.tv_send_msm_code)
    TextView tv_send_msm_code;
    @BindView(R.id.tv_account_type)
    TextView tv_account_type;
    private  int bindingType=0;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String userId="";
    private String openId="";

    @Override
    protected int layoutId() {
        return R.layout.binding_account_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("绑定账户");
        et_phone_number.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE,""));
        mPresenter.getAccountInfo();
    }

    @OnClick({R.id.tv_back,R.id.rb_binding_zfb,R.id.rb_binding_wx,R.id.btn_binding,R.id.tv_send_msm_code})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.rb_binding_zfb://支付宝
                if (!AppUtils.isEmpty(userId)){
                    toast(this,"已绑定支付宝");
                }else{
                    bindingType =1;
                    //支付宝
                    if (!AppUtils.isAvilible(this,"com.eg.android.AlipayGphone")){
                        toast(this,"目前您安装的支付宝版本过低或尚未安装");
                    }else{
                        aliPayAuthorization();
                    }
                }
                break;
            case R.id.rb_binding_wx://微信
                if (!AppUtils.isEmpty(openId)){
                    toast(this,"已绑定微信");
                }else{
                    bindingType = 2;
                }
                break;
            case R.id.btn_binding://绑定账户
                if (bindingType==0){
                    toast(this,"请选择绑定账户类型");
                }else{
                    if (AppUtils.isEmpty(userId)&&AppUtils.isEmpty(openId)){
                        toast(this,"请绑定提现账户");
                    }else if (AppUtils.isEmpty(et_msg_code.getText().toString().trim())){
                        toast(this,"请输入验证码");
                    }else{
                        StyledDialog.buildLoading("正在绑定").setActivity(this).show();
                        mPresenter.bindingAccount(userId,openId,
                                et_msg_code.getText().toString().trim());
                    }
                }
                break;
            case R.id.tv_send_msm_code://获取验证码
                if (AppUtils.isEmpty(et_phone_number.getText().toString().trim())){
                    toast(this,"请输入手机号");
                }else{
                    mPresenter.getCode(et_phone_number.getText().toString().trim(),tv_send_msm_code);
                }
                break;
            default:
        }
    }

    //绑定支付宝返回数据
    @Override
    public void bindingAccountCallBack() {
        toast(this,"绑定成功");
        finish();
    }

    //查询账户信息返回接口
    @Override
    public void getAccountInfoCallBack(AccountInfoEntity data) {
        if (!AppUtils.isEmpty(data.alipayAccount)){//支付宝
            userId = data.alipayAccount;
            rb_binding_zfb.setChecked(true);
            ll_date_caiji.setVisibility(View.VISIBLE);
            tv_account_type.setText("支付宝");
            bindingType =1;
        }else if (!AppUtils.isEmpty(data.openId)){//微信
            rb_binding_wx.setChecked(true);
            tv_account_type.setText("微信");
            bindingType =2;
        }else{
        }
    }

    //---------------------------------支付宝授权开始----------------------------------------------
    public void aliPayAuthorization() {
        if (TextUtils.isEmpty(Constant.ALIPAY_PID) || TextUtils.isEmpty(Constant.ALIPAY_APPID)
                || (TextUtils.isEmpty(Constant.RSA2_PRIVATE) && TextUtils.isEmpty(Constant.RSA_PRIVATE))
                || TextUtils.isEmpty(Constant.ALIPAY_TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (Constant.RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(Constant.ALIPAY_PID, Constant.ALIPAY_APPID, Constant.ALIPAY_TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? Constant.RSA2_PRIVATE : Constant.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(BindingAccountActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            StyledDialog.dismissLoading();
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    toast(BindingAccountActivity.this,"授权异常");
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(BindingAccountActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                        userId = String.format("%s", authResult.getUserId());
                        Log.i("TAG","userId=="+userId);
                        Log.i("TAG","authResult=="+authResult);
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(BindingAccountActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    //---------------------------------支付宝授权结束----------------------------------------------
}
