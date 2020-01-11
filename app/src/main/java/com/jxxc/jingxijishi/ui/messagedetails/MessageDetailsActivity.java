package com.jxxc.jingxijishi.ui.messagedetails;


import android.view.View;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

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
    @BindView(R.id.tv_msg_title)
    TextView tv_msg_title;
    @BindView(R.id.tv_msg_time)
    TextView tv_msg_time;
    @BindView(R.id.tv_msg_content)
    TextView tv_msg_content;
    private MessageListEntity data;
    @Override
    protected int layoutId() {
        return R.layout.message_details_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("消息详情");
        data = ZzRouter.getIntentData(this,MessageListEntity.class);
        tv_msg_title.setText(data.messageTopic);
        tv_msg_time.setText(data.sendTime);
        tv_msg_content.setText(data.content);
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
