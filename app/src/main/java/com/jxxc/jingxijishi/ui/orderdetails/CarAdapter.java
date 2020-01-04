package com.jxxc.jingxijishi.ui.orderdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.utils.GlideImgManager;

import java.util.List;

public class CarAdapter extends BaseAdapter {
    private Context context;
    private List<AwaitReceiveOrderEntity.Products> list;

    public CarAdapter(Context context){
        this.context=context;
    }

    public void setData(List<AwaitReceiveOrderEntity.Products> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.car_adapter,null);
            holder.tv_car_service_icon = convertView.findViewById(R.id.tv_car_service_icon);
            holder.tv_service_name = convertView.findViewById(R.id.tv_service_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AwaitReceiveOrderEntity.Products data = list.get(position);
        holder.tv_service_name.setText(data.productName);
        GlideImgManager.loadCircleDoorImage(context,data.imgUrl,holder.tv_car_service_icon);
        return convertView;
    }

    class ViewHolder{
        private ImageView tv_car_service_icon;
        private TextView tv_service_name;
    }
}
