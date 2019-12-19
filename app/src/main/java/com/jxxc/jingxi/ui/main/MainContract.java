package com.jxxc.jingxi.ui.main;

import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
    }

    interface  Presenter extends BasePresenter<View> {
    }
}
