package com.jxxc.jingxijishi.ui.newmain;

import android.content.Context;

import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewMainContract {
    interface View extends BaseView {
        void getUserInfoCallBack(UserInfoEntity data);
        void awaitReceiveOrderCallBack(AwaitReceiveOrderEntity data);
        void unfinishedOrderCallBack(AwaitReceiveOrderEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
        void awaitReceiveOrder(double lng,double lat);
        void unfinishedOrder();
    }
}
