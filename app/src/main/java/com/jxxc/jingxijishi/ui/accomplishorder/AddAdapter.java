package com.jxxc.jingxijishi.ui.accomplishorder;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.R;

import java.util.List;

/**
 * @explain 加号适配器
 * @author feisher.qq:458079442
 * @time 2017/11/7 17:09.
 */
public class AddAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    List<String> data;
    public AddAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        ImageView view = helper.getView(R.id.iv_item);
        view.setBackgroundResource(R.mipmap.ic_camera);
        helper.addOnClickListener(R.id.iv_item);
        if (helper.getAdapterPosition() == data.size()-1) {
            helper.setVisible(R.id.fl_delete, false);
            if (helper.getAdapterPosition() ==4) {
                helper.setVisible(R.id.iv_item, false);
            }else {
                helper.setVisible(R.id.iv_item, true);
            }
        }else {
            helper.setVisible(R.id.iv_item, false);
            helper.setVisible(R.id.fl_delete, true);
        }
        helper.addOnClickListener(R.id.fl_delete);
    }


}