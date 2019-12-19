package com.jxxc.jingxi.ui.myorder;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.orderdetails.OrderDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 我的订单
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyOrderActivity extends MVPBaseActivity<MyOrderContract.View, MyOrderPresenter> implements MyOrderContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.lv_my_order)
    ListView lv_my_order;

    private MyOrderAdapter adapter;
    private List<String> list = new ArrayList<>();
    @Override
    protected int layoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initData() {
        tv_title.setText("我的订单");
        list.add("123");
        list.add("123");
        list.add("123");
        adapter = new MyOrderAdapter(this,list);
        lv_my_order.setAdapter(adapter);

        lv_my_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyOrderActivity.this, OrderDetailsActivity.class);
                startActivity(intent);
            }
        });
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
