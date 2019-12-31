package com.jxxc.jingxijishi.ui.mywallet;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.bindingaccount.BindingAccountActivity;
import com.jxxc.jingxijishi.ui.withdrawdeposit.WithdrawDepositActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyWalletActivity extends MVPBaseActivity<MyWalletContract.View, MyWalletPresenter> implements MyWalletContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_withdraw_deposit)
    LinearLayout ll_withdraw_deposit;
    @BindView(R.id.ll_binding)
    LinearLayout ll_binding;
    @Override
    protected int layoutId() {
        return R.layout.my_wallet_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("我的钱包");
    }

    @OnClick({R.id.tv_back,R.id.ll_withdraw_deposit,R.id.ll_binding})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_withdraw_deposit://提现
                ZzRouter.gotoActivity(this, WithdrawDepositActivity.class);
                break;
            case R.id.ll_binding://绑定
                ZzRouter.gotoActivity(this, BindingAccountActivity.class);
                break;
            default:
        }
    }
}
