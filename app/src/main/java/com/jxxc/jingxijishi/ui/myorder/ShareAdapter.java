package com.jxxc.jingxijishi.ui.myorder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;

import java.util.List;

/**
 * Created by 31373 on 2018/5/25.
 */

public class ShareAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    public ShareAdapter(Context context, List<String> list) {
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_my_order,null);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_czr.setText("123132123");
        return convertView;
    }

    class ViewHolder{
        TextView tv_czr;
    }
}
