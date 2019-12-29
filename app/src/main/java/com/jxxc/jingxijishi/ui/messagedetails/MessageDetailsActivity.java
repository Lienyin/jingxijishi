package com.jxxc.jingxijishi.ui.messagedetails;


import android.view.View;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageDetailsActivity extends MVPBaseActivity<MessageDetailsContract.View, MessageDetailsPresenter> implements MessageDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected int layoutId() {
        return R.layout.message_details_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("信息详情");
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }
}
