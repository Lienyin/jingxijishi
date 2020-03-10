package com.jxxc.jingxijishi.ui.newmain;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewMainContract {
    interface View extends BaseView {
        void getUserInfoCallBack(UserInfoEntity data);
        void awaitReceiveOrderCallBack(List<AwaitReceiveOrderEntity> data);
        void unfinishedOrderCallBack(List<AwaitReceiveOrderEntity> data);
        void updateServiceStaticCallBack();
        void latestVersionCallBack();
        void updateCB(boolean must);
        void receiveCallBack();
        void startServiceCallBack();
        void transferOrderCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
        void awaitReceiveOrder(int sort,int queryFlag,double lng,double lat);
        void unfinishedOrder();
        void updateServiceStatic(int type);
        void latestVersion(int type);
        void receive(String orderId);
        void startService(String orderId);
        void transferOrder(String orderId);
        void reportLocation(double lng,double lat);
    }
}
