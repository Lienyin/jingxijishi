package com.jxxc.jingxijishi.ui.message;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.MessageListEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class MsgAdapter extends BaseQuickAdapter<MessageListEntity, BaseViewHolder> {

    public MsgAdapter(@LayoutRes int layoutResId, @Nullable List<MessageListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MessageListEntity item) {
        helper.setText(R.id.tv_order_static_name, item.messageTopic);
        helper.setText(R.id.tv_order_static_time, item.sendTime);
        helper.setText(R.id.tv_order_static_content, item.content);
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String orderId, int type);
    }
}
