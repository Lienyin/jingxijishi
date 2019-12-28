package com.jxxc.jingxijishi.ui.bindingphonenumber;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.main.MainActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingPhoneNumberActivity extends MVPBaseActivity<BindingPhoneNumberContract.View, BindingPhoneNumberPresenter> implements BindingPhoneNumberContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_binding)
    Button btn_binding;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_affirm_password)
    EditText et_affirm_password;
    private String otherAppId;
    private String photoPath;
    private String nickName;

    @Override
    protected int layoutId() {
        return R.layout.binding_phone_number_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("绑定手机号");
        otherAppId = getIntent().getStringExtra("otherAppId");
        photoPath = getIntent().getStringExtra("userHeadImage");
        nickName = getIntent().getStringExtra("fullName");
    }

    @OnClick({R.id.tv_back,R.id.btn_binding})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_binding://绑定
                if (AppUtils.isEmpty(et_account.getText().toString())){
                    toast(this,"请输入您的手机号码");
                }else if (AppUtils.isEmpty(et_password.getText().toString())){
                    toast(this,"请输入您的账户密码");
                }else if (AppUtils.isEmpty(et_affirm_password.getText().toString())){
                    toast(this,"请确认您的账户密码");
                }else if (!et_password.getText().toString().equals(et_affirm_password.getText().toString())){
                    toast(this,"密码不一致");
                }else{
                    StyledDialog.buildLoading("正在绑定").setActivity(this).show();
                    mPresenter.getThirdPartyInfo(et_account.getText().toString().trim(),et_password.getText().toString().trim(),otherAppId);
                }
                break;
            default:
        }
    }

    @Override
    public void gotoUserMain() {
        ZzRouter.gotoActivity(this, MainActivity.class);
        finish();
    }
}
