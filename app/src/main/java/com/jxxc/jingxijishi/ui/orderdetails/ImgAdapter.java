package com.jxxc.jingxijishi.ui.orderdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.utils.GlideImgManager;

import java.util.List;

public class ImgAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
    private List<String> list;

    public ImgAdapter(Context context){
        this.context=context;
    }

    public void setData(List<String> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.img_adapter,null);
            holder.iv_img_url = convertView.findViewById(R.id.iv_img_url);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String data = list.get(position);
        GlideImgManager.loadImage(context, data, holder.iv_img_url);
        return convertView;
    }

    class ViewHolder{
        ImageView iv_img_url;
    }

/**
     * @param position
     * ���ø���״̬��item
     */
    public void setSelectPosition(int position) {
        if (!(position < 0 || position > list.size())) {
            defaultSelection = position;
            notifyDataSetChanged();
        }else{
            defaultSelection = -1;
            notifyDataSetChanged();
        }
    }
}
