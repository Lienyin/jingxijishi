package com.jxxc.jingxijishi.ui.withdrawdepositdetail;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.WithdrawDepositDetailEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WithdrawDepositDetailContract {
    interface View extends BaseView {
        void drawMoneyRecordCallBack(List<WithdrawDepositDetailEntity> data);
        void drawMoneyRecordMoreCallBack(List<WithdrawDepositDetailEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void drawMoneyRecord(int pageNum,int pageSize);
        void drawMoneyRecordMore(int pageNum,int pageSize);
    }
}
