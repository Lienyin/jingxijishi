package com.jxxc.jingxijishi.ui.updatepassword;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpdatePasswordActivity extends MVPBaseActivity<UpdatePasswordContract.View, UpdatePasswordPresenter> implements UpdatePasswordContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_original_password)
    EditText et_original_password;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @BindView(R.id.et_affirm_new_password)
    EditText et_affirm_new_password;
    @BindView(R.id.btn_update_password)
    Button btn_update_password;

    @Override
    protected int layoutId() {
        return R.layout.update_password_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("修改密码");
    }

    @OnClick({R.id.tv_back,R.id.btn_update_password})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_update_password://提交
                if (AppUtils.isEmpty(et_original_password.getText().toString().trim())){
                    toast(this,"请输入您的原密码");
                }else if (AppUtils.isEmpty(et_new_password.getText().toString().trim())){
                    toast(this,"请输入您的新密码");
                }else if (AppUtils.isEmpty(et_affirm_new_password.getText().toString().trim())){
                    toast(this,"请确认您的新密码");
                }else if (!et_affirm_new_password.getText().toString().trim().equals(et_new_password.getText().toString().trim())){
                    toast(this,"密码不一致");
                }else{
                    mPresenter.updatePassword(et_original_password.getText().toString().trim(),et_new_password.getText().toString().trim());
                }
                break;
            default:
        }
    }

    /**
     * 修改成功返回数据
     */
    @Override
    public void updatePasswordCallBack() {
        toast(this,"修改成功");
        finish();
    }
}
