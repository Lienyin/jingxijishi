package com.jxxc.jingxijishi.ui.main;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.login.LoginActivity;
import com.jxxc.jingxijishi.ui.main.firstfragment.FirstFragment;
import com.jxxc.jingxijishi.ui.main.secondfragment.SecondFragment;
import com.jxxc.jingxijishi.ui.message.MessageActivity;
import com.jxxc.jingxijishi.ui.seting.SetingActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;

import butterknife.BindView;


/**
 * 主界面
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View, View.OnClickListener {

    private TextView topOrder;//上线、下线
    private TextView tabDeal;//首页
    private TextView tabMore;//个人中心
    private FrameLayout ly_content;

    private FirstFragment f1;
    private SecondFragment f2;
    private FragmentManager fragmentManager;
    private long exitTime = 0;
    public static String registrationId;
    private DrawerLayout drawerLayout;
    private ImageView iv_user_center;
    @BindView(R.id.ll_out_login)
    LinearLayout ll_out_login;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_phonenumber)
    TextView tv_user_phonenumber;
    @BindView(R.id.ll_main_setting)
    LinearLayout ll_main_setting;
    @BindView(R.id.iv_user_msg)
    ImageView iv_user_msg;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        bindView();
        mPresenter.getUserInfo();
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        drawerLayout =(DrawerLayout)findViewById(R.id.drawerlayout);//抽屉
        iv_user_center = (ImageView) findViewById(R.id.iv_user_center);
        topOrder = (TextView)this.findViewById(R.id.txt_order);
        tabDeal = (TextView)this.findViewById(R.id.txt_deal);
        tabMore = (TextView)this.findViewById(R.id.txt_more);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);
        tabDeal.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        //默认显示第一个Fragment
        tabDeal.performClick();//自动触发首页按钮
        topOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast(MainActivity.this,"待开发");
            }
        });
        iv_user_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    drawerLayout.openDrawer(Gravity.LEFT);//打开抽屉
                }else{
                    ZzRouter.gotoActivity(MainActivity.this, LoginActivity.class);
                }
            }
        });
        ll_out_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //退出登录
                AnimUtils.clickAnimator(view);
                SPUtils.remove(MainActivity.this,SPUtils.K_TOKEN);
                SPUtils.remove(MainActivity.this,SPUtils.K_SESSION_MOBILE);
                ZzRouter.gotoActivity(MainActivity.this, LoginActivity.class);
            }
        });
        ll_main_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //系统设置
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(MainActivity.this, SetingActivity.class);
            }
        });
        iv_user_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //系统信息
                AnimUtils.clickAnimator(view);
                ZzRouter.gotoActivity(MainActivity.this, MessageActivity.class);
            }
        });
    }

    //重置所有文本的选中状态
    public void selected(){
        tabDeal.setSelected(false);
        tabMore.setSelected(false);
        topOrder.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                if(f1==null){
                    f1 = new FirstFragment(this);
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.txt_more:
                selected();
                tabMore.setSelected(true);
                if(f2==null){
                    f2 = new SecondFragment(this);
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;
        }
        transaction.commit();
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
            toast(MainActivity.this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    //刷新数据
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 个人信息返回数据
     * @param data
     */
    @Override
    public void getUserInfoCallBack(UserInfoEntity data) {
        tv_user_name.setText(data.realName);
        tv_user_phonenumber.setText(data.phonenumber);
    }
}
