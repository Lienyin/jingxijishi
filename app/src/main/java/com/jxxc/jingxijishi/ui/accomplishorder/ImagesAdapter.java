package com.jxxc.jingxijishi.ui.accomplishorder;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.utils.GlideImgManager;

import java.util.List;

/**
 * @explain 故障报修附件图片适配器
 * @author feisher.qq:458079442
 * @time 2017/11/7 17:09.
 */
public class ImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImagesAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        ImageView view = helper.getView(R.id.iv_item);
        GlideImgManager.loadImageCenterCrop(mContext,item,view);

    }


}