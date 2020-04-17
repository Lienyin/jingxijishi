package com.jxxc.jingxijishi.ui.withdrawdepositdetail;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.CommissionListEntity;
import com.jxxc.jingxijishi.entity.backparameter.WithdrawDepositDetailEntity;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.commissionlist.CommissionAdapter;
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

public class WithdrawDepositDetailActivity extends MVPBaseActivity<WithdrawDepositDetailContract.View, WithdrawDepositDetailPresenter> implements WithdrawDepositDetailContract.View , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private TixianAdapter adapter;
    private int offset = 2;
    @Override
    protected int layoutId() {
        return R.layout.withdraw_deposit_detail_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("提现明细");
        initAdapter();
        onRefresh();
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TixianAdapter(R.layout.tixian_adapter, new ArrayList<WithdrawDepositDetailEntity>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvList);
        adapter.setEmptyView(R.layout.layout_nothing);

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
    public void onRefresh() {
        offset = 2;
        mPresenter.drawMoneyRecord(1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.drawMoneyRecordMore(offset,10);
    }

    @Override
    public void drawMoneyRecordCallBack(List<WithdrawDepositDetailEntity> data) {
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        if (data.size() < 10){
            adapter.loadMoreEnd();
        }else{
            adapter.disableLoadMoreIfNotFullPage();
        }
    }

    @Override
    public void drawMoneyRecordMoreCallBack(List<WithdrawDepositDetailEntity> data) {
        swipeLayout.setRefreshing(false);
        offset++;
        adapter.addData(data);
        adapter.loadMoreComplete();
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }
    }
}
