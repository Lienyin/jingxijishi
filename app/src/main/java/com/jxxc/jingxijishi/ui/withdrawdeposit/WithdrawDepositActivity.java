package com.jxxc.jingxijishi.ui.withdrawdeposit;


import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WithdrawDepositActivity extends MVPBaseActivity<WithdrawDepositContract.View, WithdrawDepositPresenter> implements WithdrawDepositContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_ke_tixian_money)
    TextView tv_ke_tixian_money;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_account_icon)
    ImageView tv_account_icon;
    @BindView(R.id.et_money)
    EditText etMoney;
    private String canWithdrawMoney;
    private double money;

    @Override
    protected int layoutId() {
        return R.layout.withdraw_deposit_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("余额提现");
        canWithdrawMoney = ZzRouter.getIntentData(this,String.class);
        tv_ke_tixian_money.setText("可提现金额"+canWithdrawMoney+"元");
        mPresenter.getAccountInfo();
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                if (temp.contains(".")){
                    int posDot = temp.indexOf(".");
                    if (posDot <= 0) return;
                    if (temp.length() - posDot - 1 > 2){
                        s.delete(posDot + 3, posDot + 4);
                    }
                    String number = etMoney.getText().toString();
                    if (!TextUtils.isEmpty(number)) {
                        if (number.startsWith(".")) {
                            etMoney.setText("0.");
                            CharSequence cs = etMoney.getText();
                            if (cs instanceof Spannable) {
                                Selection.setSelection((Spannable) cs, cs.length());
                            }
                        } else {
                            money = Double.parseDouble(number);
                        }
                    }
                }else{
                    money = Double.parseDouble(etMoney.getText().toString());
                }
            }
        });
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }

    //查询账户信息
    @Override
    public void getAccountInfoCallBack(AccountInfoEntity data) {
        if (!AppUtils.isEmpty(data.alipayAccount)){
            tv_account.setText("("+data.alipayAccount+")");
            tv_account_icon.setImageResource(R.mipmap.ic_alipay);
        }else if (!AppUtils.isEmpty(data.openId)){
            tv_account.setText("("+data.openId+")");
            tv_account_icon.setImageResource(R.mipmap.ex_share_wp);
        }else{
            tv_account_icon.setVisibility(View.GONE);
            tv_account.setText("");
        }
    }
}
