package com.jxxc.jingxijishi.ui.usercenter;

import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.mvp.BaseView;
import com.jxxc.jingxijishi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UsercenterContract {
    interface View extends BaseView {
        void queryUserInfoCallback(UserInfoEntity userInfo);
        void uploadImageCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
        void initImageSelecter();

        void gotoImageSelect(UsercenterActivity activity, int requestCodeChoose);

        /**
         * 上传头像
         * @param s 头像路径
         */
        void uploadImage(String s);
    }
}
