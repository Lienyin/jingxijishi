package com.jxxc.jingxijishi.ui.newmain;


import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.examination.ExaminationActivity;
import com.jxxc.jingxijishi.ui.login.LoginActivity;
import com.jxxc.jingxijishi.ui.main.MainActivity;
import com.jxxc.jingxijishi.ui.message.MessageActivity;
import com.jxxc.jingxijishi.ui.mywallet.MyWalletActivity;
import com.jxxc.jingxijishi.ui.orderlist.OrderListActivity;
import com.jxxc.jingxijishi.ui.seting.SetingActivity;
import com.jxxc.jingxijishi.ui.usercenter.UsercenterActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

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
    private DrawerLayout drawerLayout;
    private long exitTime = 0;

    @Override
    protected int layoutId() {
        return R.layout.new_main_activity;
    }

    @Override
    public void initData() {
        //tv_title.setText("");
        drawerLayout =(DrawerLayout)findViewById(R.id.drawerlayout);//抽屉
        mPresenter.getUserInfo();
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
    }

    @OnClick({R.id.iv_user_center,R.id.ll_main_setting,R.id.ll_out_login,
    R.id.iv_user_msg,R.id.ll_order_list,R.id.ll_jishi_renzheng,R.id.ll_my_wallet,R.id.ll_user_info})
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
                drawerLayout.closeDrawer(Gravity.LEFT);//关闭抽屉
                break;
            case R.id.iv_user_msg://系统设置
                //系统信息
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, MessageActivity.class);
                drawerLayout.closeDrawer(Gravity.LEFT);//关闭抽屉
                break;
            case R.id.ll_order_list://订单记录
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, OrderListActivity.class);
                drawerLayout.closeDrawer(Gravity.LEFT);//关闭抽屉
                break;
            case R.id.ll_jishi_renzheng://技师认证
                AnimUtils.clickAnimator(view);
                Intent intent = new Intent(this, ExaminationActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);//关闭抽屉
                break;
            case R.id.ll_my_wallet://我的钱包
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, MyWalletActivity.class);
                drawerLayout.closeDrawer(Gravity.LEFT);//关闭抽屉
                break;
            case R.id.ll_user_info://我的信息
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(this, UsercenterActivity.class);
                drawerLayout.closeDrawer(Gravity.LEFT);//关闭抽屉
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
        swipeLayout.setRefreshing(false);
        tv_user_name.setText(data.realName);
        tv_user_phonenumber.setText(data.phonenumber);
        tv_today_order.setText(data.todayFinishOrder);
        tv_tixian_money.setText(data.canWithdrawMoney);
        tv_today_shouru.setText(data.todayProjectedIncome);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPresenter.getUserInfo();
    }
}
