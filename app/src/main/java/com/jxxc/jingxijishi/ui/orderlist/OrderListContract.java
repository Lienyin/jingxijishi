package com.jxxc.jingxijishi.ui.orderlist;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.OrderListEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderListContract {
    interface View extends BaseView {
        void myOrderCallBack(OrderListEntity data);
        void myOrderMoreCallBack(OrderListEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void myOrder(int type);
        void myOrderMore(int type);
    }
}
