package com.jxxc.jingxijishi.ui.login;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.ui.main.MainActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;

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

    @OnClick({R.id.btn_login,R.id.tv_login_fangshi,R.id.tv_get_code})
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
            default:
        }
    }

    @Override
    public void loginCallBack() {
        ZzRouter.gotoActivity(this, MainActivity.class);
    }
}
