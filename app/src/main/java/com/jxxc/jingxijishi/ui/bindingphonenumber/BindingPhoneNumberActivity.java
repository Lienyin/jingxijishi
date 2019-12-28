package com.jxxc.jingxijishi.ui.bindingphonenumber;


import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingPhoneNumberActivity extends MVPBaseActivity<BindingPhoneNumberContract.View, BindingPhoneNumberPresenter> implements BindingPhoneNumberContract.View {
    @Override
    protected int layoutId() {
        return R.layout.binding_phone_number_activity;
    }

    @Override
    public void initData() {

    }

}
