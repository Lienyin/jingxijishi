package com.jxxc.jingxijishi.ui.newmain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.dialog.PopSeek;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.utils.AppUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            holder.tv_dating_order_zhuandan = convertView.findViewById(R.id.tv_dating_order_zhuandan);
            holder.tv_dating_order_start = convertView.findViewById(R.id.tv_dating_order_start);
            holder.tv_dating_order_wancheng = convertView.findViewById(R.id.tv_dating_order_wancheng);
            holder.tv_dating_order_count_down = convertView.findViewById(R.id.tv_dating_order_count_down);
            holder.ll_fuwu = convertView.findViewById(R.id.ll_fuwu);
            holder.ll_dating = convertView.findViewById(R.id.ll_dating);
            holder.iv_dating_order_static_icon = convertView.findViewById(R.id.iv_dating_order_static_icon);
            holder.tv_dating_order_static_memo = convertView.findViewById(R.id.tv_dating_order_static_memo);
            holder.tv_dating_order_kehu = convertView.findViewById(R.id.tv_dating_order_kehu);
            holder.btn_rob_order = convertView.findViewById(R.id.btn_rob_order);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final AwaitReceiveOrderEntity data = list.get(position);
        holder.tv_dating_order_static.setText(data.orderTopic);
        holder.tv_dating_order_car_number.setText(data.carNum);
        holder.tv_dating_order_address.setText(data.address);
        holder.tv_dating_order_time.setText(data.appointmentTime);
        holder.tv_dating_order_memo.setText(!AppUtils.isEmpty(data.remark)?data.remark:"无");
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
            holder.ll_dating.setVisibility(View.VISIBLE);//抢单
            holder.ll_fuwu.setVisibility(View.GONE);//服务
            holder.tv_dating_order_zhuandan.setVisibility(View.INVISIBLE);//转单
            holder.tv_dating_order_start.setVisibility(View.GONE);//开始服务
            holder.tv_dating_order_wancheng.setVisibility(View.GONE);//完成服务
            holder.tv_dating_order_count_down.setVisibility(View.GONE);//倒计时
        }else if (data.status == 2){//待服务
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.order_dengdai);
            holder.tv_dating_order_static_memo.setText("待服务");
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.dai_fuwu));
            holder.ll_dating.setVisibility(View.GONE);
            holder.ll_fuwu.setVisibility(View.VISIBLE);
            if (data.serviceType==0){
                holder.tv_dating_order_zhuandan.setVisibility(View.VISIBLE);
            }else{
                holder.tv_dating_order_zhuandan.setVisibility(View.INVISIBLE);
            }
            holder.tv_dating_order_start.setVisibility(View.VISIBLE);
            holder.tv_dating_order_wancheng.setVisibility(View.GONE);
            holder.tv_dating_order_count_down.setVisibility(View.GONE);
        }else if (data.status == 3){//服务中
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.order_jingxingz);
            holder.tv_dating_order_static_memo.setText("服务中");
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.public_all));
            holder.ll_dating.setVisibility(View.GONE);
            holder.ll_fuwu.setVisibility(View.VISIBLE);
            holder.tv_dating_order_zhuandan.setVisibility(View.INVISIBLE);
            holder.tv_dating_order_start.setVisibility(View.GONE);

            //服务截至时间-当前时间
            int jzTIme =  Integer.parseInt(getTime(data.getCanCompleteTime()));//截至时间
            int dqTime = Integer.parseInt(getTime(getDQTime()));//当前时间
            if (jzTIme-dqTime>0){
                holder.tv_dating_order_count_down.setVisibility(View.VISIBLE);//倒计时
                holder.tv_dating_order_wancheng.setVisibility(View.GONE);//倒计时结束显示完成服务
                String str = "";
                int s = jzTIme-dqTime;
                int time = s/3600;
                if (time>=1){
                    int h = s/3600;//小时
                    s = s-h*3600;//剩余秒数
                    int m = s/60;//分钟
                    s = s-m*60;//秒数
                    if (h>=10){
                        holder.tv_dating_order_count_down.setText("  "+h+":"+m+":"+s);//服务剩余时间
                    }else{
                        holder.tv_dating_order_count_down.setText("  0"+h+":"+m+":"+s);//服务剩余时间
                    }
                }else{
                    int m = s/60;//分钟
                    s = s-m*60;//秒数
                    //str = getStrTimeMinute((jzTIme-dqTime)+"");//服务剩余时间
                    if (m>=10){
                        holder.tv_dating_order_count_down.setText(m+":"+s);
                    }else{
                        holder.tv_dating_order_count_down.setText("  0"+m+":"+s);
                    }
                }
            }else{
                holder.tv_dating_order_count_down.setVisibility(View.GONE);//倒计时
                holder.tv_dating_order_wancheng.setVisibility(View.VISIBLE);//倒计时结束显示完成服务
            }
        }else if (data.status == 4){//服务已完成
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.order_jingxingz);
            holder.tv_dating_order_static_memo.setText("已完成");
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.public_all));
            holder.ll_dating.setVisibility(View.GONE);
            holder.ll_fuwu.setVisibility(View.VISIBLE);
            holder.tv_dating_order_zhuandan.setVisibility(View.INVISIBLE);
            holder.tv_dating_order_start.setVisibility(View.GONE);
            holder.tv_dating_order_wancheng.setVisibility(View.INVISIBLE);
            holder.tv_dating_order_count_down.setVisibility(View.GONE);
        }else{
            holder.iv_dating_order_static_icon.setImageResource(R.mipmap.icon_user_5);
            holder.tv_dating_order_static_memo.setText(data.statusName);
            holder.tv_dating_order_static_memo.setTextColor(context.getResources().getColor(R.color.black));
            holder.ll_dating.setVisibility(View.GONE);
            holder.ll_fuwu.setVisibility(View.GONE);
            holder.tv_dating_order_zhuandan.setVisibility(View.INVISIBLE);
            holder.tv_dating_order_start.setVisibility(View.GONE);
            holder.tv_dating_order_wancheng.setVisibility(View.GONE);
            holder.tv_dating_order_count_down.setVisibility(View.GONE);
        }
        holder.btn_rob_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(1,"",data.orderId);//抢单
            }
        });
        holder.tv_dating_order_kehu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(2,data.phonenumber,data.orderId);//联系客户
            }
        });
        holder.tv_dating_order_zhuandan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(3,"",data.orderId);//转单
            }
        });
        holder.tv_dating_order_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(4,"",data.orderId);//开始服务
            }
        });
        holder.tv_dating_order_wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(5,"",data.orderId);//完成服务
            }
        });
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
        private TextView tv_dating_order_zhuandan;
        private TextView tv_dating_order_start;
        private TextView tv_dating_order_wancheng;
        private TextView tv_dating_order_count_down;
        private TextView tv_dating_order_kehu;
        private LinearLayout ll_fuwu;
        private LinearLayout ll_dating;
        private ImageView iv_dating_order_static_icon;
        private TextView btn_rob_order;
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(int type,String phoneNumber,String orderId);
    }

    // 将字符串转为时间戳
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }

    //获取当前时间
    public String getDQTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    int result = 0;
    private Thread thread;
    public void start() {
        thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        if (list == null || result == list.size()) {
                            break;
                        }
                        sleep(1);
                        for (AwaitReceiveOrderEntity person : list) {
                            if (!"时间到".equals(person.getCanCompleteTime())) {
                                if ("1".equals(person.getCanCompleteTime())) {
                                    person.setCanCompleteTime("时间到");
                                    result++;
                                } else {
                                    person.setCanCompleteTime(person.getCanCompleteTime());
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
