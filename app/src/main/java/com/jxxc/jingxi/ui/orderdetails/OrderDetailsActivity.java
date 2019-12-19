package com.jxxc.jingxi.ui.orderdetails;


import android.view.View;
import android.widget.TextView;

import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 订单详情
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends MVPBaseActivity<OrderDetailsContract.View, OrderDetailsPresenter> implements OrderDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected int layoutId() {
        return R.layout.acivity_order_details;
    }

    @Override
    public void initData() {
        tv_title.setText("订单详情");
    }
    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            default:
        }
    }

}
