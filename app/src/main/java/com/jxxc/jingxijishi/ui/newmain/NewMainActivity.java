package com.jxxc.jingxijishi.ui.newmain;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.dialog.PopSeek;
import com.jxxc.jingxijishi.dialog.ZhuanDanDialog;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.accomplishorder.AccomplishOrderActivity;
import com.jxxc.jingxijishi.ui.commissionlist.CommissionListActivity;
import com.jxxc.jingxijishi.ui.examination.ExaminationActivity;
import com.jxxc.jingxijishi.ui.login.LoginActivity;
import com.jxxc.jingxijishi.ui.main.MainActivity;
import com.jxxc.jingxijishi.ui.message.MessageActivity;
import com.jxxc.jingxijishi.ui.mywallet.MyWalletActivity;
import com.jxxc.jingxijishi.ui.orderdetails.OrderDetailsActivity;
import com.jxxc.jingxijishi.ui.orderlist.OrderListActivity;
import com.jxxc.jingxijishi.ui.seting.SetingActivity;
import com.jxxc.jingxijishi.ui.usercenter.UsercenterActivity;
import com.jxxc.jingxijishi.ui.withdrawdeposit.WithdrawDepositActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.GlideImgManager;
import com.jxxc.jingxijishi.utils.LocationUtils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewMainActivity extends MVPBaseActivity<NewMainContract.View, NewMainPresenter> implements NewMainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ll_out_login)
    LinearLayout ll_out_login;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_phonenumber)
    TextView tv_user_phonenumber;
    @BindView(R.id.ll_main_setting)
    LinearLayout ll_main_setting;
    @BindView(R.id.ll_order_list)
    LinearLayout ll_order_list;
    @BindView(R.id.ll_my_wallet)
    LinearLayout ll_my_wallet;
    @BindView(R.id.iv_user_msg)
    ImageView iv_user_msg;
    @BindView(R.id.iv_user_center)
    ImageView iv_user_center;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.ll_jishi_renzheng)
    LinearLayout ll_jishi_renzheng;
    @BindView(R.id.ll_user_info)
    LinearLayout ll_user_info;
    @BindView(R.id.tv_today_order)
    TextView tv_today_order;
    @BindView(R.id.tv_tixian_money)
    TextView tv_tixian_money;
    @BindView(R.id.tv_today_shouru)
    TextView tv_today_shouru;
    @BindView(R.id.tv_user_dengji)
    ImageView tv_user_dengji;
    @BindView(R.id.tv_service_type)
    TextView tv_service_type;
    @BindView(R.id.lv_data)
    ListView lv_data;
    @BindView(R.id.rb_dating)
    RadioButton rb_dating;
    @BindView(R.id.rb_fuwu)
    RadioButton rb_fuwu;
    @BindView(R.id.iv_user_logo)
    ImageView iv_user_logo;
    @BindView(R.id.ll_today_order)
    LinearLayout ll_today_order;
    @BindView(R.id.ll_money_tixian)
    LinearLayout ll_money_tixian;
    @BindView(R.id.ll_today_shouru)
    LinearLayout ll_today_shouru;
    @BindView(R.id.ll_icon_home_shuaxin)
    LinearLayout ll_icon_home_shuaxin;
    @BindView(R.id.ll_ting_dan)
    LinearLayout ll_ting_dan;
    @BindView(R.id.ll_pai_xu)
    LinearLayout ll_pai_xu;
    private DrawerLayout drawerLayout;
    private long exitTime = 0;
    private NewMainAdapter adapter;
    private List<AwaitReceiveOrderEntity> list = new ArrayList<>();
    private PopSeek popSeek;
    private int isOnline;
    private String oId;//订单id
    private ZhuanDanDialog zhuanDanDialog;
    private int isExaminationQualified=-1;
    private int isOperationQualified=-1;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1) {
                adapter.notifyDataSetChanged();
                //每隔1秒更新一次界面，如果只需要精确到秒的倒计时此处改成1000即可
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };

    @Override
    protected int layoutId() {
        return R.layout.new_main_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.public_all);//状态栏颜色
        drawerLayout =(DrawerLayout)findViewById(R.id.drawerlayout);//抽屉
        mPresenter.getUserInfo();
        mPresenter.latestVersion(1);//暂时关闭
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        StyledDialog.buildLoading("加载中").setActivity(this).show();
        getLatLng();
        zhuanDanDialog = new ZhuanDanDialog(this);
        adapter = new NewMainAdapter(this);
        adapter.setData(list);
        lv_data.setAdapter(adapter);
        adapter.setOnFenxiangClickListener(new NewMainAdapter.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(int type,String phoneNumber,String orderId) {
                oId = orderId;
                switch (type){
                    case 1://抢单
                        //状态 线上考试是否合格 1合格 0不合格
                        if (isExaminationQualified ==0){
                            toast(NewMainActivity.this,"线上考试不合格");
                        }
//                        else if (isOperationQualified==0){
//                            //状态 线下实操是否合格 1合格 0不合格
//                            toast(NewMainActivity.this,"线下实操不合格");
//                        }
                        else{
                            StyledDialog.buildLoading("正在抢单").setActivity(NewMainActivity.this).show();
                            mPresenter.receive(orderId);
                        }
                        break;
                    case 2: //联系客户
                        if (!AppUtils.isEmpty(phoneNumber)){
                            AppUtils.callPhone(NewMainActivity.this,phoneNumber);
                        }else{
                            toast(NewMainActivity.this,"暂无联系方式");
                        }
                        break;
                    case 3://转单
                        zhuanDanDialog.showShareDialog(true);
                        break;
                    case 4://开始服务
                        mPresenter.startService(orderId);
                        break;
                    case 5://完成服务
                        ZzRouter.gotoActivity(NewMainActivity.this, AccomplishOrderActivity.class,orderId);
                        break;
                }
            }
        });
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ZzRouter.gotoActivity(NewMainActivity.this, OrderDetailsActivity.class,list.get(i).orderId);
            }
        });
        popSeek = new PopSeek(this);
        popSeek.setOnFenxiangClickListener(new PopSeek.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(int type) {
                //状态 2:忙碌 1:上线 0:下线
                if (type ==1){
                    //小休
                    mPresenter.updateServiceStatic(2);
                }else if (type == 2){
                    //下线
                    mPresenter.updateServiceStatic(0);
                }else{
                    //上线
                    mPresenter.updateServiceStatic(1);
                }
            }
        });

        //转单提示框
        zhuanDanDialog.setOnFenxiangClickListener(new ZhuanDanDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(int shareType) {
                mPresenter.transferOrder(oId);
            }
        });
    }

    /**
     * 获取当前经纬度
     */
    public void getLatLng() {
        Location location = LocationUtils.getInstance(NewMainActivity.this).showLocation();
        if (location != null) {
            Log.i("TAG","lng=="+location.getLongitude()+"  lat=="+location.getLatitude());
            if (rb_dating.isChecked() == true){
                mPresenter.awaitReceiveOrder(location.getLongitude(),location.getLatitude());
            }else{
                mPresenter.unfinishedOrder();
            }
        } else {
            String lat = SPUtils.get(this, "lat", "120.97111");//默认江苏昆山
            String lng = SPUtils.get(this, "lng", "31.389817");
            if (!AppUtils.isEmpty(lat)) {
                if (rb_dating.isChecked() == true){
                    mPresenter.awaitReceiveOrder(Double.valueOf(lng),Double.valueOf(lat));
                }else{
                    mPresenter.unfinishedOrder();
                }
            }else{
                toast(this,"定位失败");
            }
        }
    }

    @OnClick({R.id.iv_user_center,R.id.ll_main_setting,R.id.ll_out_login,R.id.rb_dating,R.id.rb_fuwu,
    R.id.iv_user_msg,R.id.ll_order_list,R.id.ll_jishi_renzheng,R.id.ll_my_wallet,R.id.ll_user_info,
    R.id.tv_service_type,R.id.ll_today_order,R.id.ll_money_tixian,R.id.ll_today_shouru,R.id.ll_icon_home_shuaxin,
    R.id.ll_ting_dan,R.id.ll_pai_xu})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.iv_user_center://抽屉
                if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    drawerLayout.openDrawer(Gravity.LEFT);//打开抽屉
                }else{
                    ZzRouter.gotoActivity(this, LoginActivity.class);
                }
                break;
            case R.id.ll_out_login://退出登录
                AnimUtils.clickAnimator(view);
                SPUtils.remove(this,SPUtils.K_TOKEN);
                SPUtils.remove(this,SPUtils.K_SESSION_MOBILE);
                ZzRouter.gotoActivity(this, LoginActivity.class);
                break;
            case R.id.ll_main_setting://系统设置
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, SetingActivity.class);
                //drawerLayout.closeDrawer(Gravity.LEFT);//关闭抽屉
                break;
            case R.id.iv_user_msg://系统设置
                //系统信息
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, MessageActivity.class);
                break;
            case R.id.ll_order_list://订单记录
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, OrderListActivity.class);
                break;
            case R.id.ll_jishi_renzheng://技师认证
                AnimUtils.clickAnimator(view);
                if (isExaminationQualified==1){
                    toast(this,"线上考试已合格");
                }else{
                    Intent intent = new Intent(this, ExaminationActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_my_wallet://我的钱包
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, MyWalletActivity.class);
                break;
            case R.id.ll_user_info://我的信息
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, UsercenterActivity.class);
                break;
            case R.id.rb_dating://抢单大厅
                getLatLng();
                break;
            case R.id.rb_fuwu://带服务
                mPresenter.unfinishedOrder();
                adapter.start();
                handler.sendEmptyMessageDelayed(1,1000);
                break;
            case R.id.tv_service_type://服务状态
                popSeek.showPopupWindow(tv_service_type,isOnline);
                break;
            case R.id.ll_today_order://今日完成订单
                ZzRouter.gotoActivity(this,OrderListActivity.class);
                break;
            case R.id.ll_money_tixian://可提现金额
                ZzRouter.gotoActivity(this, WithdrawDepositActivity.class);
                break;
            case R.id.ll_today_shouru://今日预计收入
                ZzRouter.gotoActivity(this, CommissionListActivity.class);
                break;
            case R.id.ll_icon_home_shuaxin://刷新
                getLatLng();
                break;
            case R.id.ll_ting_dan://开启听单
                toast(this,"功能规划中");
                break;
            case R.id.ll_pai_xu://只能排序
                toast(this,"功能规划中");
                break;
            default:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toast(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    @Override
    public void getUserInfoCallBack(UserInfoEntity data) {
        GlideImgManager.loadCircleImage(this, data.avatar, iv_user_logo);
        isOnline = data.isOnline;
        isExaminationQualified =data.isExaminationQualified;
        isOperationQualified =data.isOperationQualified;
        tv_user_name.setText(data.realName);
        tv_user_phonenumber.setText(data.technicianCode);
        tv_today_order.setText(data.todayFinishOrder);
        tv_tixian_money.setText(data.canWithdrawMoney);
        tv_today_shouru.setText(data.todayProjectedIncome);
        if (data.grade>0&&data.grade<=1){
            tv_user_dengji.setImageResource(R.mipmap.score);
        }else if (data.grade>1&&data.grade<=2){
            tv_user_dengji.setImageResource(R.mipmap.pingfen_tow);
        }else if (data.grade>2&&data.grade<=3){
            tv_user_dengji.setImageResource(R.mipmap.pingfen_three);
        }else if (data.grade>3&&data.grade<=4){
            tv_user_dengji.setImageResource(R.mipmap.pingfen_forl);
        }else if (data.grade>4&&data.grade<=5){
            tv_user_dengji.setImageResource(R.mipmap.pingfen_five);
        }else{
            tv_user_dengji.setImageResource(R.mipmap.pingfen_three);
        }
        //状态 2:忙碌 1:上线 0:下线
        if (data.isOnline == 1){
            tv_service_type.setText("接单中");
        }else if (data.isOnline == 2){
            tv_service_type.setText("小休");
        }else{
            tv_service_type.setText("下线");
        }
    }

    /**
     * 抢单大厅反回数据
     * @param data
     */
    @Override
    public void awaitReceiveOrderCallBack(List<AwaitReceiveOrderEntity> data) {
        swipeLayout.setRefreshing(false);
        if (data.size()>0){
            list = data;
            adapter.setData(list);
        }else{
            list.clear();
            toast(this,"暂无订单");
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 服务列表返回数据
     * @param data
     */
    @Override
    public void unfinishedOrderCallBack(List<AwaitReceiveOrderEntity> data) {
        swipeLayout.setRefreshing(false);
        if (data.size()>0){
            list = data;
            adapter.setData(list);
        }else{
            list.clear();
            toast(this,"暂无订单");
        }
        adapter.notifyDataSetChanged();
    }

    //修改服务状态
    @Override
    public void updateServiceStaticCallBack() {
        popSeek.dismiss();
        mPresenter.getUserInfo();
    }

    /**
     * 查询版本返回数据
     */
    @Override
    public void latestVersionCallBack() {

    }

    boolean isMustUpdate;
    @Override
    public void updateCB(boolean must) {
        this.isMustUpdate = must;
    }

    //接单成功返回数据
    @Override
    public void receiveCallBack() {
        onRefresh();
    }

    //开始服务返回数据
    @Override
    public void startServiceCallBack() {
        onRefresh();
    }
    //转单返回数据
    @Override
    public void transferOrderCallBack() {
        onRefresh();
    }

    //下拉刷新
    @Override
    public void onRefresh() {
       getLatLng();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getUserInfo();
        getLatLng();
    }
}
