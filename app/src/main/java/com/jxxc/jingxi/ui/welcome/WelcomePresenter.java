package com.jxxc.jingxi.ui.welcome;

import android.support.v4.view.ViewPager;

import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WelcomePresenter extends BasePresenterImpl<WelcomeContract.View> implements WelcomeContract.Presenter{

    @Override
    public void isShowViewPager(ViewPager viewPager, boolean isfirstlogin) {

    }

    @Override
    public void querySetting() {

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }
}
