package com.jxxc.jingxi.ui.usercenter;


import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;


/**
 * 个人中心--我的
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UsercenterActivity extends MVPBaseActivity<UsercenterContract.View, UsercenterPresenter> implements UsercenterContract.View {

    @Override
    protected int layoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initData() {

    }
}
