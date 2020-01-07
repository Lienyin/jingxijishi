package com.jxxc.jingxijishi.ui.orderlist;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.OrderListEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderListContract {
    interface View extends BaseView {
        void myOrderCallBack(List<OrderListEntity> data);
        void myOrderMoreCallBack(List<OrderListEntity> data);

        void startServiceCallBack();
        void transferOrderCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void myOrder(String status,int pageNum,int pageSize);
        void myOrderMore(String status,int pageNum,int pageSize);

        void startService(String orderId);
        void transferOrder(String orderId);
    }
}
