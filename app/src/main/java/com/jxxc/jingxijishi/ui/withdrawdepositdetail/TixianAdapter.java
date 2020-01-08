package com.jxxc.jingxijishi.ui.withdrawdepositdetail;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.CommissionListEntity;
import com.jxxc.jingxijishi.entity.backparameter.WithdrawDepositDetailEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class TixianAdapter extends BaseQuickAdapter<WithdrawDepositDetailEntity, BaseViewHolder> {

    public TixianAdapter(@LayoutRes int layoutResId, @Nullable List<WithdrawDepositDetailEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final WithdrawDepositDetailEntity item) {
        if (item.remitType==1){
            helper.setText(R.id.tv_order_type, "提现到支付宝");
        }else{
            helper.setText(R.id.tv_order_type, "提现到微信");
        }
        helper.setText(R.id.tv_order_id, "￥"+item.orderMoney);
        helper.setText(R.id.tv_order_time, item.applyTime);
        //状态 0未审核 1已审核 -1已驳回
        if (item.status==0){
            helper.setText(R.id.tv_order_money, "等待审核");
        }else if (item.status==1){
            helper.setText(R.id.tv_order_money, "审核通过");
        }else{
            helper.setText(R.id.tv_order_money, "驳回");
        }
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(double lat, double lng, String siteName, int type, int isAction);
    }
}
