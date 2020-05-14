package com.jxxc.jingxijishi.ui.orderdetails;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.jxxc.jingxijishi.utils.GlideImgManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class LookImgAdapter extends PagerAdapter {

    private Context context;
    private List<String> list;
    public LookImgAdapter(Context context, List<String> list) {
        this.context=context;
        this.list=list;

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

    }

    @Override
    public int getCount() {

        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        //ImageLoader.getInstance().displayImage(list.get(position%list.size()),imageView);
        GlideImgManager.loadRectangleSiteImage(context, list.get(position%list.size()),imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick();
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener {
        void onFenxiangClick();
    }
}
