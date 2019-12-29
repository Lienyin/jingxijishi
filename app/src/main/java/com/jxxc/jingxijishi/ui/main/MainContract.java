package com.jxxc.jingxijishi.ui.main;

import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.mvp.BaseView;
import com.jxxc.jingxijishi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void getUserInfoCallBack(UserInfoEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
    }
}
