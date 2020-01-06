package com.jxxc.jingxijishi.ui.accomplishorder;

import android.content.Context;

import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AccomplishOrderContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void initImageSelecter();
        void gotoImageSelect(AccomplishOrderActivity malfunctionRepairActivity, int requestCodeChoose);
    }
}
