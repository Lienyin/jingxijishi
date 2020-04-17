package com.jxxc.jingxijishi.ui.orderdetails;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.accomplishorder.AccomplishOrderActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.MyGridView;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends MVPBaseActivity<OrderDetailsContract.View, OrderDetailsPresenter> implements OrderDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_dating_order_static)
    TextView tv_dating_order_static;
    @BindView(R.id.tv_dating_order_static_memo)
    TextView tv_dating_order_static_memo;
    @BindView(R.id.iv_dating_order_static_icon)
    ImageView iv_dating_order_static_icon;
    @BindView(R.id.tv_dating_order_car_number)
    TextView tv_dating_order_car_number;
    @BindView(R.id.tv_dating_order_time)
    TextView tv_dating_order_time;
    @BindView(R.id.tv_dating_order_address)
    TextView tv_dating_order_address;
    @BindView(R.id.tv_dating_order_memo)
    TextView tv_dating_order_memo;
    @BindView(R.id.tv_dating_order_money)
    TextView tv_dating_order_money;
    @BindView(R.id.tv_dating_order_car_type)
    TextView tv_dating_order_car_type;
    @BindView(R.id.tv_dating_order_phone_number)
    TextView tv_dating_order_phone_number;
    @BindView(R.id.tv_dating_xia_order_time)
    TextView tv_dating_xia_order_time;
    @BindView(R.id.tv_dating_wan_order_time)
    TextView tv_dating_wan_order_time;
    @BindView(R.id.tv_dating_order_zhuandan)
    TextView tv_dating_order_zhuandan;
    @BindView(R.id.tv_dating_order_start)
    TextView tv_dating_order_start;
    @BindView(R.id.tv_dating_order_count_down)
    TextView tv_dating_order_count_down;
    @BindView(R.id.tv_dating_order_wancheng)
    TextView tv_dating_order_wancheng;
    @BindView(R.id.ll_fuwu)
    LinearLayout ll_fuwu;
    @BindView(R.id.ll_dating)
    LinearLayout ll_dating;
    @BindView(R.id.ll_xiadan_time)
    LinearLayout ll_xiadan_time;
    @BindView(R.id.ll_dating_wan_order_time)
    LinearLayout ll_dating_wan_order_time;
    @BindView(R.id.gv_data)
    MyGridView gv_data;
    @BindView(R.id.tv_dating_order_kehu)
    TextView tv_dating_order_kehu;
    @BindView(R.id.tv_comment_content)
    TextView tv_comment_content;
    @BindView(R.id.tv_customer_comment_time)
    TextView tv_customer_comment_time;
    @BindView(R.id.btn_rob_order)
    TextView btn_rob_order;
    @BindView(R.id.tv_comm_static)
    TextView tv_comm_static;
    @BindView(R.id.rb_pinglun)
    ImageView rb_pinglun;
    @BindView(R.id.ll_comm_view)
    LinearLayout ll_comm_view;
    private String orderId;
    private AwaitReceiveOrderEntity data;
    private boolean isRun = true;
    private CarAdapter carAdapter;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1) {
                daoJishi();
                //每隔1秒更新一次界面，如果只需要精确到秒的倒计时此处改成1000即可
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };

    @Override
    protected int layoutId() {
        return R.layout.order_details_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("订单详情");
        StyledDialog.buildLoading("正在查询").setActivity(this).show();
        orderId = ZzRouter.getIntentData(this,String.class);
        mPresenter.getOrderDetails(orderId);
    }

    //倒计时
    private void daoJishi(){
        //服务截至时间-当前时间
        int jzTIme =  Integer.parseInt(getTime(data.getCanCompleteTime()));//截至时间
        int dqTime = Integer.parseInt(getTime(getDQTime()));//当前时间
        if (jzTIme-dqTime>0){
            tv_dating_order_count_down.setVisibility(View.VISIBLE);//倒计时
            tv_dating_order_wancheng.setVisibility(View.GONE);//倒计时结束显示完成服务
            String str = "";
            int s = jzTIme-dqTime;
            int time = s/3600;
            if (time>=1){
                int h = s/3600;//小时
                s = s-h*3600;//剩余秒数
                int m = s/60;//分钟
                s = s-m*60;//秒数
                if (h>=10){
                    tv_dating_order_count_down.setText("  "+h+":"+m+":"+s);//服务剩余时间
                }else{
                    tv_dating_order_count_down.setText("  0"+h+":"+m+":"+s);//服务剩余时间
                }
            }else{
                int m = s/60;//分钟
                s = s-m*60;//秒数
                //str = getStrTimeMinute((jzTIme-dqTime)+"");//服务剩余时间
                if (m>=10){
                    tv_dating_order_count_down.setText(m+":"+s);
                }else{
                    tv_dating_order_count_down.setText("  0"+m+":"+s);
                }
            }
        }else{
            tv_dating_order_count_down.setVisibility(View.GONE);//倒计时
            tv_dating_order_wancheng.setVisibility(View.VISIBLE);//倒计时结束显示完成服务
        }
    }
    @OnClick({R.id.tv_back,R.id.tv_dating_order_kehu,R.id.tv_dating_order_zhuandan,
            R.id.tv_dating_order_start,R.id.tv_dating_order_wancheng,R.id.btn_rob_order})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.tv_dating_order_kehu://联系客服
                if (!AppUtils.isEmpty(data.phonenumber)){
                    AppUtils.callPhone(this,data.phonenumber);
                }else{
                    toast(this,"暂无联系方式");
                }
                break;
            case R.id.tv_dating_order_zhuandan://转单
                mPresenter.transferOrder(data.orderId);
                break;
            case R.id.tv_dating_order_start://开始服务
                mPresenter.startService(data.orderId);
                break;
            case R.id.tv_dating_order_wancheng://完成服务
                ZzRouter.gotoActivity(this, AccomplishOrderActivity.class,data.orderId);
                break;
            case R.id.btn_rob_order://接单
                mPresenter.receive(data.orderId);
                break;
            default:
        }
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

    private Thread thread;
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 订单详情返回数据
     * @param d
     */
    @Override
    public void getOrderDetailsCallBack(AwaitReceiveOrderEntity d) {
        data = d;
        if (!AppUtils.isEmpty(data.products)){
            carAdapter = new CarAdapter(this);
            carAdapter.setData(data.products);
            gv_data.setAdapter(carAdapter);
        }

        tv_dating_order_static.setText(data.orderTopic);
        tv_dating_order_car_number.setText(data.carNum);
        tv_dating_order_address.setText(data.address);
        tv_dating_order_time.setText(data.appointmentTime);
        tv_dating_order_memo.setText(data.remark);
        tv_dating_order_money.setText("￥"+data.price);
        tv_dating_order_car_type.setText(data.brandType);
        tv_dating_order_phone_number.setText(data.phonenumber);
        tv_dating_xia_order_time.setText(data.createTime);
        //订单状态 0待支付 1已支付待接单 2已接单待服务 3服务中 4服务已完成 5取消订单
        if (data.status == 1){
            //已支付待接单
            iv_dating_order_static_icon.setImageResource(R.mipmap.order_dengdai);
            tv_dating_order_static_memo.setText("待接单");
            tv_dating_order_static_memo.setTextColor(this.getResources().getColor(R.color.dai_fuwu));
            ll_dating.setVisibility(View.VISIBLE);//抢单
            ll_fuwu.setVisibility(View.GONE);//服务
        }else if (data.status == 2){
            //已接单待服务
            iv_dating_order_static_icon.setImageResource(R.mipmap.order_dengdai);
            tv_dating_order_static_memo.setText("待服务");
            tv_dating_order_static_memo.setTextColor(this.getResources().getColor(R.color.dai_fuwu));
            ll_dating.setVisibility(View.GONE);
            ll_fuwu.setVisibility(View.VISIBLE);
            tv_dating_order_zhuandan.setVisibility(View.VISIBLE);//转单
            tv_dating_order_start.setVisibility(View.VISIBLE);
        }else if (data.status == 3){
            //服务中
            iv_dating_order_static_icon.setImageResource(R.mipmap.order_jingxingz);
            tv_dating_order_static_memo.setText("服务中");
            tv_dating_order_static_memo.setTextColor(this.getResources().getColor(R.color.public_all));
            ll_dating.setVisibility(View.GONE);
            ll_fuwu.setVisibility(View.VISIBLE);
            start();
            daoJishi();
        }else if (data.status == 4){
            //服务已完成
            iv_dating_order_static_icon.setImageResource(R.mipmap.order_jingxingz);
            tv_dating_order_static_memo.setText("已完成");
            tv_dating_order_static_memo.setTextColor(this.getResources().getColor(R.color.public_all));
            ll_dating.setVisibility(View.GONE);
            ll_fuwu.setVisibility(View.VISIBLE);
            ll_xiadan_time.setVisibility(View.GONE);
            ll_dating_wan_order_time.setVisibility(View.VISIBLE);
            tv_dating_wan_order_time.setText(data.duration+"分钟");

            //评论内容
            if (!AppUtils.isEmpty(data.commentContent)){
                ll_comm_view.setVisibility(View.VISIBLE);
                tv_comment_content.setText(data.commentContent);
                tv_customer_comment_time.setText(data.customerCommentTime);
                if (data.starLevel==1){
                    rb_pinglun.setBackgroundResource(R.mipmap.score);
                    tv_comm_static.setText("差评");
                }else if (data.starLevel==2){
                    rb_pinglun.setBackgroundResource(R.mipmap.pingfen_tow);
                    tv_comm_static.setText("一般");
                }else if (data.starLevel==3){
                    rb_pinglun.setBackgroundResource(R.mipmap.pingfen_three);
                    tv_comm_static.setText("及格");
                }else if (data.starLevel==4){
                    rb_pinglun.setBackgroundResource(R.mipmap.pingfen_forl);
                    tv_comm_static.setText("好评");
                }else if (data.starLevel==5){
                    rb_pinglun.setBackgroundResource(R.mipmap.pingfen_five);
                    tv_comm_static.setText("优秀");
                }
            }
        }else{
            iv_dating_order_static_icon.setImageResource(R.mipmap.icon_user_5);
            tv_dating_order_static_memo.setText(data.statusName);
            tv_dating_order_static_memo.setTextColor(this.getResources().getColor(R.color.black));
            ll_dating.setVisibility(View.GONE);
            ll_fuwu.setVisibility(View.GONE);
        }
    }

    //接单成功返回数据
    @Override
    public void receiveCallBack() {
        mPresenter.getOrderDetails(data.orderId);
    }

    //开始服务返回数据
    @Override
    public void startServiceCallBack() {
        mPresenter.getOrderDetails(data.orderId);
    }

    //转单返回数据
    @Override
    public void transferOrderCallBack() {
        mPresenter.getOrderDetails(data.orderId);
    }
}
