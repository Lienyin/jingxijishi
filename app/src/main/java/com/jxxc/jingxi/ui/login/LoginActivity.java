package com.jxxc.jingxi.ui.login;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.main.MainActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AppUtils;

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

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_login})
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
            default:
        }
    }

    @Override
    public void loginCallBack() {
        ZzRouter.gotoActivity(this, MainActivity.class);
    }
}
