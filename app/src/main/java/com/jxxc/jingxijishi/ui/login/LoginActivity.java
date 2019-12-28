package com.jxxc.jingxijishi.ui.login;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.http.AsyncHttpClient;
import com.baidu.mapapi.http.HttpClient;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.entity.backparameter.ThirdPartyLogin;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.ui.bindingphonenumber.BindingPhoneNumberActivity;
import com.jxxc.jingxijishi.ui.main.MainActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.jxxc.jingxijishi.wxapi.Constant;
import com.jxxc.jingxijishi.wxapi.WeiXin;
import com.sdsmdg.tastytoast.TastyToast;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_code)
    EditText et_password_code;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_login_fangshi)
    TextView tv_login_fangshi;
    @BindView(R.id.ll_password_view)
    LinearLayout ll_password_view;
    @BindView(R.id.ll_code_view)
    LinearLayout ll_code_view;
    @BindView(R.id.tv_get_code)
    TextView tv_get_code;
    @BindView(R.id.iv_open_wx_login)
    ImageView iv_open_wx_login;
    //微信
    public IWXAPI api;
    private String wxOpenid = "";
    private String wxHeadimgurl = "";//头像
    private String fullName = "";//昵称

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        tv_back.setVisibility(View.GONE);
        if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_SESSION_MOBILE,""))){
            etAccount.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE,""));
        }
    }

    @OnClick({R.id.btn_login,R.id.tv_login_fangshi,R.id.tv_get_code,R.id.iv_open_wx_login})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.btn_login:
                if (ll_password_view.getVisibility()==View.VISIBLE){
                    if (AppUtils.isEmpty(etAccount.getText().toString())){
                        toast(this,"请输入您的手机号码");
                    }else if ((AppUtils.isEmpty(etPassword.getText().toString()))){
                        toast(this,"请输入您的账户密码");
                    }else{
                        StyledDialog.buildLoading("正在登录").setActivity(this).show();
                        mPresenter.login(etAccount.getText().toString(),etPassword.getText().toString());
                    }
                }else{
                    if (AppUtils.isEmpty(etAccount.getText().toString())){
                        toast(this,"请输入您的手机号码");
                    }else if ((AppUtils.isEmpty(et_password_code.getText().toString()))){
                        toast(this,"请输入您的验证码");
                    }else{
                        StyledDialog.buildLoading("正在登录").setActivity(this).show();
                        mPresenter.loginCode(etAccount.getText().toString(),et_password_code.getText().toString());
                    }
                }
                break;
            case R.id.tv_login_fangshi:
                if ("短信验证码登录".equals(tv_login_fangshi.getText().toString())){
                    tv_login_fangshi.setText("账号登录");
                    ll_password_view.setVisibility(View.GONE);
                    ll_code_view.setVisibility(View.VISIBLE);
                    etPassword.setText("");
                }else{
                    tv_login_fangshi.setText("短信验证码登录");
                    ll_password_view.setVisibility(View.VISIBLE);
                    ll_code_view.setVisibility(View.GONE);
                    et_password_code.setText("");
                }
                break;
            case R.id.tv_get_code://发送验证码
                if (AppUtils.isEmpty(etAccount.getText().toString())){
                    toast(this,"手机号不能为空");
                }else{
                    StyledDialog.buildLoading("正在发送").setActivity(this).show();
                    mPresenter.getAuthCode(etAccount.getText().toString(),tv_get_code);
                }
                break;
            case R.id.iv_open_wx_login://微信登录
                if (isAvilible(this,"com.tencent.mm")){
                    weiXinLogin();
                }else{
                    toast(this,"目前您安装的微信版本过低或尚未安装");
                }
                break;
            default:
        }
    }

    //登录返回数据
    @Override
    public void loginCallBack() {
        ZzRouter.gotoActivity(this, MainActivity.class);
    }

    //第三方登录返回数据
    @Override
    public void getThirdPartyLogin(ThirdPartyLogin data) {
        if ("ok".equals(data.step)) {
            //第一种状态：授权登录成功
            toast(this,"登录成功");
            ZzRouter.gotoActivity(this, MainActivity.class);
            finish();
        }else if ("not_auth".equals(data.step)){
            //第一次授权登录，跳转到手机获取验证码界面(新用户)
            Intent intent = new Intent(this, BindingPhoneNumberActivity.class);
            intent.putExtra("otherAppId", wxOpenid);
            intent.putExtra("userHeadImage", wxHeadimgurl);
            intent.putExtra("fullName", fullName);
            startActivity(intent);
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    //----------------------微信登录开始（需要重置APP_secret）--------------------------------------
    public void weiXinLogin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());
        api.sendReq(req);
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventMainThread(WeiXin wx) {
        getAccessToken(wx.getCode());
    }
    //获取Token
    public void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + Constant.APP_ID + "&secret=" + Constant.WECHAT_SECRET +
                "&code=" + code + "&grant_type=authorization_code";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new HttpClient.ProtoResultCallback() {
            @Override
            public void onSuccess(String s) {
                if (!s.isEmpty()) {
                    try {
                        JSONObject dataJson = new JSONObject(s);
                        String access_token = dataJson.getString("access_token");
                        String openid = dataJson.getString("openid");
                        getWeiXinUserInfo(access_token, openid);
                    } catch (JSONException e) {
                        System.out.println("Something wrong...");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(HttpClient.HttpStateError httpStateError) {

            }
        });
    }
    //获取用户信息
    public void getWeiXinUserInfo(String access_token, String Openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + Openid;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new HttpClient.ProtoResultCallback() {
            @Override
            public void onSuccess(String s) {
                if (!s.isEmpty()) {
                    try {
                        JSONObject dataJson = new JSONObject(s);
                        wxOpenid = dataJson.getString("openid");
                        wxHeadimgurl = dataJson.getString("headimgurl");
                        fullName = dataJson.getString("nickname");
                        mPresenter.thirdPartyLogin(wxOpenid);
                    } catch (JSONException e) {
                        System.out.println("Something wrong...");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(HttpClient.HttpStateError httpStateError) {

            }
        });
    }
    //---------------------------------微信登录结束----------------------------------------------
}
