package com.jxxc.jingxijishi.ui.regards;


import com.jxxc.jingxijishi.mvp.BasePresenter;
import com.jxxc.jingxijishi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegardsContract {
    interface View extends BaseView {
        void updateCB(boolean must);
    }

    interface  Presenter extends BasePresenter<View> {
        //查询app版本
        void queryAppVersion(String type);
    }
}
