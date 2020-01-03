package com.jxxc.jingxijishi.ui.newmain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;

import java.util.List;

public class NewMainAdapter extends BaseAdapter {
    private Context context;
    private List<AwaitReceiveOrderEntity> list;

    public NewMainAdapter(Context context){
        this.context=context;
    }

    public void setData(List<AwaitReceiveOrderEntity> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.new_main_adapter,null);
            holder.tv_dating_order_static = convertView.findViewById(R.id.tv_dating_order_static);
            holder.tv_dating_order_address = convertView.findViewById(R.id.tv_dating_order_address);
            holder.tv_dating_order_time = convertView.findViewById(R.id.tv_dating_order_time);
            holder.tv_dating_order_money = convertView.findViewById(R.id.tv_dating_order_money);
            holder.tv_dating_order_memo = convertView.findViewById(R.id.tv_dating_order_memo);
            holder.ll_fuwu = convertView.findViewById(R.id.ll_fuwu);
            holder.ll_dating = convertView.findViewById(R.id.ll_dating);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AwaitReceiveOrderEntity data = list.get(position);
        holder.tv_dating_order_static.setText(data.orderTopic);
        holder.tv_dating_order_address.setText(data.address);
        holder.tv_dating_order_time.setText(data.appointmentTime);
        holder.tv_dating_order_memo.setText(data.remark);
        holder.tv_dating_order_money.setText("￥"+data.price);
        return convertView;
    }

    class ViewHolder{
        private TextView tv_dating_order_static;
        private TextView tv_dating_order_address;
        private TextView tv_dating_order_time;
        private TextView tv_dating_order_money;
        private TextView tv_dating_order_memo;
        private LinearLayout ll_fuwu;
        private LinearLayout ll_dating;
    }
}
