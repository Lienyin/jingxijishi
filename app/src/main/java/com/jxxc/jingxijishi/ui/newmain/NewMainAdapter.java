package com.jxxc.jingxijishi.ui.newmain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;

import java.text.DecimalFormat;
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
            holder.tv_dating_order_car_number = convertView.findViewById(R.id.tv_dating_order_car_number);
            holder.ll_fuwu = convertView.findViewById(R.id.ll_fuwu);
            holder.ll_dating = convertView.findViewById(R.id.ll_dating);
            holder.iv_dating_order_static_icon = convertView.findViewById(R.id.iv_dating_order_static_icon);
            holder.tv_dating_order_static_memo = convertView.findViewById(R.id.tv_dating_order_static_memo);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AwaitReceiveOrderEntity data = list.get(position);
        holder.tv_dating_order_static.setText(data.orderTopic);
        holder.tv_dating_order_car_number.setText(data.carNum);
        holder.tv_dating_order_address.setText(data.address);
        holder.tv_dating_order_time.setText(data.appointmentTime);
        holder.tv_dating_order_memo.setText(data.remark);
        holder.tv_dating_order_money.setText("￥"+data.price);
        //订单状态 0待支付 1已支付待接单 2已接单待服务 3服务中 4服务已完成 5取消订单
        if (data.status == 1){
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.order_dingwei);
            //计算距离distance
            String showDistance = "?? m";
            if (data.distance > 1000) {
                showDistance = new DecimalFormat("0.00").format(data.distance / 1000d) + " km";
            } else {
                showDistance = new DecimalFormat("0").format(data.distance) + " m";
            }
            holder.tv_dating_order_static_memo.setText(showDistance);
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.black));
        }else if (data.status == 2){
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.order_dengdai);
            holder.tv_dating_order_static_memo.setText("待服务");
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.dai_fuwu));
        }else if (data.status == 3){
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.order_jingxingz);
            holder.tv_dating_order_static_memo.setText("服务中");
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.public_all));
        }else{
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.icon_user_5_3x);
            holder.tv_dating_order_static_memo.setText(data.statusName);
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.black));
        }
        return convertView;
    }

    class ViewHolder{
        private TextView tv_dating_order_static;
        private TextView tv_dating_order_address;
        private TextView tv_dating_order_time;
        private TextView tv_dating_order_money;
        private TextView tv_dating_order_memo;
        private TextView tv_dating_order_car_number;
        private TextView tv_dating_order_static_memo;
        private LinearLayout ll_fuwu;
        private LinearLayout ll_dating;
        private ImageView iv_dating_order_static_icon;
    }
}
