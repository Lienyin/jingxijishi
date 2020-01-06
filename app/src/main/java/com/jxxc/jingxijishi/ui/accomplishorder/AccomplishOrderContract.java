package com.jxxc.jingxijishi.ui.accomplishorder;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AccomplishOrderContract {
    interface View extends BaseView {
        void getOrderDetailsCallBack(AwaitReceiveOrderEntity data);
        void endServiceCallBack();
        void commitCallback(String imgUrl);
    }

    interface  Presenter extends BasePresenter<View> {
        void initImageSelecter();
        void gotoImageSelect(AccomplishOrderActivity malfunctionRepairActivity, int requestCodeChoose);

        void getOrderDetails(String orderId);
        void endService(String orderId,String imgUrl);
        void commit(List<String> uriList);
    }
}
