package com.jxxc.jingxi.ui.myorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jxxc.jingxi.R;

import java.util.List;

public class MyOrderAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public MyOrderAdapter(Context context,List<String> list){
        this.context = context;
        this.list = list;
        Toast.makeText(context,list.size()+"",Toast.LENGTH_LONG).show();
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
            holder =new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_my_order,null);
            holder.iv_static = convertView.findViewById(R.id.iv_static);
            holder.tv_czr = convertView.findViewById(R.id.tv_czr);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_static;
        TextView tv_czr;
    }
}
