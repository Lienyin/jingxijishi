package com.jxxc.jingxijishi.ui.orderdetails;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsContract {
    interface View extends BaseView {
        void getOrderDetailsCallBack(AwaitReceiveOrderEntity data);
        void receiveCallBack();
        void startServiceCallBack();
        void transferOrderCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void getOrderDetails(String orderId);
        void receive(String orderId);
        void startService(String orderId);
        void transferOrder(String orderId);
    }
}
