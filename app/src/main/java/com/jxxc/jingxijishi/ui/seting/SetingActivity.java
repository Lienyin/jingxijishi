package com.jxxc.jingxijishi.ui.seting;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.regards.RegardsActivity;
import com.jxxc.jingxijishi.ui.updatepassword.UpdatePasswordActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetingActivity extends MVPBaseActivity<SetingContract.View, SetingPresenter> implements SetingContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_setting_update_password)
    LinearLayout ll_setting_update_password;
    @BindView(R.id.ll_my_guanyu)
    LinearLayout ll_my_guanyu;

    @Override
    protected int layoutId() {
        return R.layout.seting_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("安全设置");
    }

    @OnClick({R.id.tv_back,R.id.ll_setting_update_password,R.id.ll_my_guanyu})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_setting_update_password://修改密码
                ZzRouter.gotoActivity(this, UpdatePasswordActivity.class);
                break;
            case R.id.ll_my_guanyu://关于我们
                ZzRouter.gotoActivity(this, RegardsActivity.class);
                break;
            default:
        }
    }

}
