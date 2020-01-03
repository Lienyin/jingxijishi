package com.jxxc.jingxijishi.ui.orderlist;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.OrderListEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.updatepassword.UpdatePasswordActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderListActivity extends MVPBaseActivity<OrderListContract.View, OrderListPresenter> implements OrderListContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private OrderListAdapter adapter;//order_list_adapter

    @Override
    protected int layoutId() {
        return R.layout.order_list_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("我的订单");
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

    @Override
    public void myOrderCallBack(OrderListEntity data) {

    }

    @Override
    public void myOrderMoreCallBack(OrderListEntity data) {

    }
}
