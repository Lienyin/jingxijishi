package com.jxxc.jingxijishi.ui.bindingaccount;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

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
    @BindView(R.id.et_alipay_user_name)
    EditText et_alipay_user_name;
    @BindView(R.id.et_alipay_user_account)
    EditText et_alipay_user_account;
    @BindView(R.id.et_phone_number)
    TextView et_phone_number;
    @BindView(R.id.et_msg_code)
    EditText et_msg_code;
    @BindView(R.id.btn_binding)
    Button btn_binding;
    @BindView(R.id.tv_send_msm_code)
    TextView tv_send_msm_code;
    private  int bindingType=0;
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
                ll_date_caiji.setVisibility(View.VISIBLE);
                bindingType =1;
                break;
            case R.id.rb_binding_wx://微信
                ll_date_caiji.setVisibility(View.GONE);
                bindingType = 2;
                break;
            case R.id.btn_binding://绑定账户
                if (bindingType==0){
                    toast(this,"请选择绑定账户类型");
                }else if (bindingType == 1){
                    if (AppUtils.isEmpty(et_alipay_user_name.getText().toString().trim())){
                        toast(this,"请输入姓名");
                    }else if (AppUtils.isEmpty(et_alipay_user_account.getText().toString().trim())){
                        toast(this,"请输入支付宝账户");
                    }else if (AppUtils.isEmpty(et_phone_number.getText().toString().trim())){
                        toast(this,"请输入手机号");
                    }else if (AppUtils.isEmpty(et_msg_code.getText().toString().trim())){
                        toast(this,"请输入验证码");
                    }else{
                        StyledDialog.buildLoading("正在绑定").setActivity(this).show();
                        mPresenter.bindingAliPay(
                                et_alipay_user_name.getText().toString().trim(),
                                et_alipay_user_account.getText().toString().trim(),
                                "",
                                et_msg_code.getText().toString().trim(),
                                et_phone_number.getText().toString().trim());
                    }
                }else{
                    //
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
    public void bindingAliPayCallBack() {
        toast(this,"绑定成功");
        finish();
    }

    //查询账户信息返回接口
    @Override
    public void getAccountInfoCallBack(AccountInfoEntity data) {
        if (!AppUtils.isEmpty(data.alipayAccount)){//支付宝
            rb_binding_zfb.setChecked(true);
            et_alipay_user_name.setText(data.alipayName);
            et_alipay_user_account.setText(data.alipayAccount);
            ll_date_caiji.setVisibility(View.VISIBLE);
            bindingType =1;
        }else if (!AppUtils.isEmpty(data.openId)){//微信
            rb_binding_wx.setChecked(true);
            bindingType =2;
        }else{
        }
    }
}
