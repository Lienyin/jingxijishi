package com.jxxc.jingxijishi.ui.commissionlist;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.CommissionListEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class CommissionAdapter extends BaseQuickAdapter<CommissionListEntity, BaseViewHolder> {

    public CommissionAdapter(@LayoutRes int layoutResId, @Nullable List<CommissionListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CommissionListEntity item) {
        helper.setText(R.id.tv_order_type, "分佣");
        helper.setText(R.id.tv_order_id, item.orderId);
        helper.setText(R.id.tv_order_time, item.createTime);
        helper.setText(R.id.tv_order_money, "￥"+item.technicianMoney);
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(double lat, double lng, String siteName, int type, int isAction);
    }
}
