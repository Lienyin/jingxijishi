package com.jxxc.jingxijishi.ui.login;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.ui.main.MainActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_login_fangshi)
    TextView tv_login_fangshi;
    @BindView(R.id.ll_password_view)
    LinearLayout ll_password_view;
    @BindView(R.id.ll_code_view)
    LinearLayout ll_code_view;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_login,R.id.tv_login_fangshi})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.btn_login:
                if (AppUtils.isEmpty(etAccount)){
                    toast(this,"请输入账户");
                }else if ((AppUtils.isEmpty(etPassword))){
                    toast(this,"请输入密码");
                }else{
                    StyledDialog.buildLoading("正在登录").setActivity(this).show();
                    mPresenter.login("13916141340","111111");
                }
                break;
            case R.id.tv_login_fangshi://
                if ("短信验证码登录".equals(tv_login_fangshi.getText().toString())){
                    tv_login_fangshi.setText("账号登录");
                    ll_password_view.setVisibility(View.GONE);
                    ll_code_view.setVisibility(View.VISIBLE);
                }else{
                    tv_login_fangshi.setText("短信验证码登录");
                    ll_password_view.setVisibility(View.VISIBLE);
                    ll_code_view.setVisibility(View.GONE);
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
