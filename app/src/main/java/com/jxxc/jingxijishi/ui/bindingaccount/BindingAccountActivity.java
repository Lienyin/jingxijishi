package com.jxxc.jingxijishi.ui.bindingaccount;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
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
    @Override
    protected int layoutId() {
        return R.layout.binding_account_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("绑定账户");
    }

    @OnClick({R.id.tv_back,R.id.rb_binding_zfb,R.id.rb_binding_wx})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.rb_binding_zfb://支付宝
                ll_date_caiji.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_binding_wx://微信
                ll_date_caiji.setVisibility(View.GONE);
                break;
            default:
        }
    }

    //绑定支付宝返回数据
    @Override
    public void bindingAliPayCallBack() {

    }
}
