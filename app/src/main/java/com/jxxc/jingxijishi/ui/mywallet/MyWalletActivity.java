package com.jxxc.jingxijishi.ui.mywallet;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.bindingaccount.BindingAccountActivity;
import com.jxxc.jingxijishi.ui.commissionlist.CommissionListActivity;
import com.jxxc.jingxijishi.ui.orderlist.OrderListActivity;
import com.jxxc.jingxijishi.ui.withdrawdeposit.WithdrawDepositActivity;
import com.jxxc.jingxijishi.ui.withdrawdepositdetail.WithdrawDepositDetailActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

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
    @BindView(R.id.tv_affirm)
    TextView tv_affirm;
    @BindView(R.id.ll_withdraw_deposit)
    LinearLayout ll_withdraw_deposit;
    @BindView(R.id.ll_wei_binding)
    LinearLayout ll_wei_binding;
    @BindView(R.id.ll_comm_details)
    LinearLayout ll_comm_details;
    @BindView(R.id.tv_ke_tixian_money)
    TextView tv_ke_tixian_money;
    @BindView(R.id.tv_today_order)
    TextView tv_today_order;
    @BindView(R.id.tv_tixian_money)
    TextView tv_tixian_money;
    @BindView(R.id.tv_zong_order)
    TextView tv_zong_order;
    @BindView(R.id.tv_zhanghu_number)
    TextView tv_zhanghu_number;
    @BindView(R.id.iv_tixian_zhanghu)
    ImageView iv_tixian_zhanghu;
    @BindView(R.id.ll_today_order)
    LinearLayout ll_today_order;
    private String canWithdrawMoney="";
    @Override
    protected int layoutId() {
        return R.layout.my_wallet_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("我的钱包");
        Drawable img = this.getResources().getDrawable(R.mipmap.tixian_mingxi);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        tv_affirm.setCompoundDrawables(img, null, null, null); //设置左图标
        tv_affirm.setVisibility(View.VISIBLE);
        mPresenter.getUserInfo();
        mPresenter.getAccountInfo();
    }

    @OnClick({R.id.tv_back,R.id.ll_withdraw_deposit,R.id.ll_wei_binding,R.id.ll_comm_details,
    R.id.tv_affirm,R.id.ll_today_order})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_withdraw_deposit://提现
                ZzRouter.gotoActivity(this, WithdrawDepositActivity.class,canWithdrawMoney);
                break;
            case R.id.ll_wei_binding://绑定
                ZzRouter.gotoActivity(this, BindingAccountActivity.class);
                break;
            case R.id.ll_comm_details://账单明细
                ZzRouter.gotoActivity(this, CommissionListActivity.class);
                break;
            case R.id.tv_affirm://提现明细
                ZzRouter.gotoActivity(this, WithdrawDepositDetailActivity.class);
                break;
            case R.id.ll_today_order://提现明细
                ZzRouter.gotoActivity(this, OrderListActivity.class);
                break;
            default:
        }
    }

    //个人信息返回接口
    @Override
    public void getUserInfoCallBack(UserInfoEntity data) {
        canWithdrawMoney = data.canWithdrawMoney;
        tv_ke_tixian_money.setText(data.canWithdrawMoney);
        tv_today_order.setText(data.todayFinishOrder);
        tv_tixian_money.setText(data.todayProjectedIncome);
        tv_zong_order.setText(data.orderNum);
    }

    //查询账户信息
    @Override
    public void getAccountInfoCallBack(AccountInfoEntity data) {
        if ("not_account".equals(data.step)){
            ll_wei_binding.setVisibility(View.VISIBLE);
            ll_withdraw_deposit.setVisibility(View.GONE);
        }else{
            ll_wei_binding.setVisibility(View.GONE);
            ll_withdraw_deposit.setVisibility(View.VISIBLE);
            if (!AppUtils.isEmpty(data.alipayAccount)){
                tv_zhanghu_number.setText("(支付宝)");
                iv_tixian_zhanghu.setImageResource(R.mipmap.ic_alipay);
            }else if (!AppUtils.isEmpty(data.openId)){
                tv_zhanghu_number.setText("(微信)");
                iv_tixian_zhanghu.setImageResource(R.mipmap.ex_share_wp);
            }else{
                iv_tixian_zhanghu.setVisibility(View.GONE);
                tv_zhanghu_number.setText("");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
        mPresenter.getAccountInfo();
    }
}
