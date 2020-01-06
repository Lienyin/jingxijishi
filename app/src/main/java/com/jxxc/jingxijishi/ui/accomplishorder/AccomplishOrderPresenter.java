package com.jxxc.jingxijishi.ui.accomplishorder;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AccomplishOrderPresenter extends BasePresenterImpl<AccomplishOrderContract.View> implements AccomplishOrderContract.Presenter{
    ISListConfig config;

    @Override
    public void gotoImageSelect(AccomplishOrderActivity malfunctionRepairActivity, int requestCodeChoose) {
        ISNav.getInstance().toListActivity(malfunctionRepairActivity, config, requestCodeChoose);
    }

    @Override
    public void initImageSelecter() {
        config = new ISListConfig.Builder()
                .multiSelect(true)
                .rememberSelected(true)
                .btnBgColor(Color.TRANSPARENT)
                .btnTextColor(Color.WHITE)
                .statusBarColor(ContextCompat.getColor(mView.getContext().getApplicationContext(), R.color.public_all))
                .backResId(R.mipmap.back)
                .title("图片选择")
                .titleColor(Color.WHITE)
                .titleBgColor(ContextCompat.getColor(mView.getContext().getApplicationContext(),R.color.public_all))
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                .needCamera(true)
                .maxNum(3)
                .build();

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }
}
