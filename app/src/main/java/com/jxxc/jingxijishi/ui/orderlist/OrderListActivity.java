package com.jxxc.jingxijishi.ui.orderlist;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.OrderListEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.updatepassword.UpdatePasswordActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderListActivity extends MVPBaseActivity<OrderListContract.View, OrderListPresenter> implements OrderListContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.rb_work_order_all)
    RadioButton rb_work_order_all;
    @BindView(R.id.rb_work_order_dai_jie)
    RadioButton rb_work_order_dai_jie;
    @BindView(R.id.rb_work_order_jin_xing)
    RadioButton rb_work_order_jin_xing;
    private OrderListAdapter adapter;
    private int offset = 2;
    private int orderType = 0;//状态 不传查默认所有 ( 0, “待支付”),( 1, “已支付待接单”),( 2, “已接单待服务”),( 3, “服务中”),( 4, “服务已完成”),( 5, “取消订单”)

    @Override
    protected int layoutId() {
        return R.layout.order_list_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("我的订单");
        initAdapter();
        onRefresh();
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderListAdapter(R.layout.order_list_adapter, new ArrayList<OrderListEntity>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvList);
        adapter.setEmptyView(R.layout.layout_nothing);

        //隐藏侧滑删除功能(2019/08/05)
        //rvList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        adapter.setOnFenxiangClickListener(new OrderListAdapter.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String orderId, int type) {
                //
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.rb_work_order_all,R.id.rb_work_order_dai_jie,R.id.rb_work_order_jin_xing})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.rb_work_order_all://全部
                orderType = 0;
                mPresenter.myOrder(0,1,10);
                break;
            case R.id.rb_work_order_dai_jie://待服务
                orderType = 2;
                mPresenter.myOrder(2,1,10);
                break;
            case R.id.rb_work_order_jin_xing://已完成
                orderType = 4;
                mPresenter.myOrder(4,1,10);
                break;
            default:
        }
    }

    @Override
    public void onRefresh() {
        offset = 2;
        mPresenter.myOrder(orderType,1, 10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.myOrderMore(orderType,offset, 10);
    }

    @Override
    public void myOrderCallBack(List<OrderListEntity> data) {
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void myOrderMoreCallBack(List<OrderListEntity> data) {
        swipeLayout.setRefreshing(false);
        offset++;
        adapter.addData(data);
        adapter.loadMoreComplete();
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }
    }
}
