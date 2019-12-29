package com.jxxc.jingxijishi.ui.message;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.entity.backparameter.MsgListEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class MsgAdapter extends BaseQuickAdapter<MsgListEntity, BaseViewHolder> {

    public MsgAdapter(@LayoutRes int layoutResId, @Nullable List<MsgListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MsgListEntity item) {
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
