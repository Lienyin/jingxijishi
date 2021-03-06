package com.jxxc.jingxijishi.ui.withdrawdeposit;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.AccountInfoEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WithdrawDepositContract {
    interface View extends BaseView {
        void getAccountInfoCallBack(AccountInfoEntity data);
        void drawMoneyApplyCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void getAccountInfo();
        void drawMoneyApply(String money,String remitType);
    }
}
