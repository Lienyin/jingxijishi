package com.jxxc.jingxijishi.ui.orderlist;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.entity.backparameter.MsgListEntity;
import com.jxxc.jingxijishi.entity.backparameter.OrderListEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderListEntity, BaseViewHolder> {

    public OrderListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderListEntity item) {
        //helper.setText(R.id.tv_order_number, item.id+"");
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String orderId, int type);
    }
}
