package com.jxxc.jingxijishi.ui.orderlist;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.OrderListEntity;
import com.jxxc.jingxijishi.utils.AppUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class OrderListAdapter extends BaseQuickAdapter<OrderListEntity, BaseViewHolder> {

    private List<OrderListEntity> list;

    public OrderListAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListEntity> data) {
        super(layoutResId, data);
        this.list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderListEntity item) {
        helper.setText(R.id.tv_dating_order_static, item.orderId);
        helper.setText(R.id.tv_dating_order_car_number, item.carNum);
        helper.setText(R.id.tv_dating_order_address, item.address);
        helper.setText(R.id.tv_dating_order_time, item.appointmentTime);
        helper.setText(R.id.tv_dating_order_memo, item.remark);
        helper.setText(R.id.tv_dating_order_money, "￥"+item.price);

        //订单状态 0待支付 1已支付待接单 2已接单待服务 3服务中 4服务已完成 5取消订单
        if (item.status == 1){
            //不会有状态1
        }else if (item.status == 2){//待服务
            helper.setImageResource(R.id.iv_dating_order_static_icon,R.mipmap.order_dengdai);
            helper.setText(R.id.tv_dating_order_static_memo,"待服务");
            helper.setTextColor(R.id.tv_dating_order_static_memo,mContext.getResources().getColor(R.color.yellow_fuwu));
            helper.setGone(R.id.tv_dating_order_zhuandan,true);
            helper.setGone(R.id.tv_dating_order_start,true);
            helper.setGone(R.id.iv_navigation_icon,true);
            helper.setGone(R.id.tv_dating_order_wancheng,false);
            helper.setGone(R.id.tv_dating_order_count_down,false);
        }else if (item.status == 3){//服务中
            helper.setImageResource(R.id.iv_dating_order_static_icon,R.mipmap.order_jingxingz);
            helper.setText(R.id.tv_dating_order_static_memo,"服务中");
            helper.setTextColor(R.id.tv_dating_order_static_memo,mContext.getResources().getColor(R.color.public_all));
            helper.setVisible(R.id.tv_dating_order_zhuandan,false);
            helper.setGone(R.id.tv_dating_order_start,false);
            helper.setGone(R.id.iv_navigation_icon,false);

            //服务截至时间-当前时间
            int jzTIme =  Integer.parseInt(getTime(item.getCanCompleteTime()));//截至时间
            int dqTime = Integer.parseInt(getTime(getDQTime()));//当前时间
            if (jzTIme-dqTime>0){
                helper.setGone(R.id.tv_dating_order_count_down,true);
                helper.setGone(R.id.tv_dating_order_wancheng,false);
                String str = "";
                int s = jzTIme-dqTime;
                int time = s/3600;
                if (time>=1){
                    int h = s/3600;//小时
                    s = s-h*3600;//剩余秒数
                    int m = s/60;//分钟
                    s = s-m*60;//秒数
                    if (h>=10){
                        helper.setText(R.id.tv_dating_order_count_down,"  "+h+":"+m+":"+s);
                    }else{
                        helper.setText(R.id.tv_dating_order_count_down,"  0"+h+":"+m+":"+s);
                    }
                }else{
                    int m = s/60;//分钟
                    s = s-m*60;//秒数
                    //str = getStrTimeMinute((jzTIme-dqTime)+"");//服务剩余时间
                    if (m>=10){
                        helper.setText(R.id.tv_dating_order_count_down,m+":"+s);
                    }else{
                        helper.setText(R.id.tv_dating_order_count_down,"  0"+m+":"+s);
                    }
                }
            }else{
                helper.setGone(R.id.tv_dating_order_count_down,false);
                helper.setGone(R.id.tv_dating_order_wancheng,true);
            }
        }else if (item.status == 4){//服务已完成
            helper.setImageResource(R.id.iv_dating_order_static_icon,R.mipmap.order_jingxingz);
            helper.setText(R.id.tv_dating_order_static_memo,"已完成");
            helper.setTextColor(R.id.tv_dating_order_static_memo,mContext.getResources().getColor(R.color.public_all));
            helper.setVisible(R.id.tv_dating_order_zhuandan,false);
            helper.setGone(R.id.tv_dating_order_start,false);
            helper.setGone(R.id.iv_navigation_icon,false);
            helper.setVisible(R.id.tv_dating_order_wancheng,false);
            helper.setGone(R.id.tv_dating_order_count_down,false);
        }else{
            helper.setImageResource(R.id.iv_dating_order_static_icon,R.mipmap.icon_user_5_3x);
            helper.setText(R.id.tv_dating_order_static_memo,item.statusName);
            helper.setTextColor(R.id.tv_dating_order_static_memo,mContext.getResources().getColor(R.color.black));
            helper.setVisible(R.id.tv_dating_order_zhuandan,false);
            helper.setGone(R.id.tv_dating_order_start,false);
            helper.setGone(R.id.iv_navigation_icon,false);
            helper.setVisible(R.id.tv_dating_order_wancheng,false);
            helper.setGone(R.id.tv_dating_order_count_down,false);
        }
        helper.setOnClickListener(R.id.tv_dating_order_kehu, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtils.isEmpty(item.phonenumber)){
                    AppUtils.callPhone(mContext,item.phonenumber);
                }else{
                    Toast.makeText(mContext,"暂无联系方式",Toast.LENGTH_LONG).show();
                }
            }
        });
        helper.setOnClickListener(R.id.tv_dating_order_zhuandan, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(item.orderId,1,0,0,"");//转单
            }
        });
        helper.setOnClickListener(R.id.tv_dating_order_start, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(item.orderId,2,0,0,"");//开始服务
            }
        });
        helper.setOnClickListener(R.id.tv_dating_order_wancheng, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(item.orderId,3,0,0,"");//完成服务
            }
        });
        helper.setOnClickListener(R.id.iv_navigation_icon, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick(item.orderId,4,item.lat,item.lng,item.address);//导航
            }
        });
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String orderId, int type,double siteLat,double siteLng,String siteAddress);
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
                        for (OrderListEntity person : list) {
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
