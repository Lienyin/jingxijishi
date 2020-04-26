package com.jxxc.jingxijishi.ui.accomplishorder;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxijishi.ConfigApplication;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AwaitReceiveOrderEntity;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AccomplishOrderActivity extends MVPBaseActivity<AccomplishOrderContract.View, AccomplishOrderPresenter> implements AccomplishOrderContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_order_hour)
    TextView tv_order_hour;
    @BindView(R.id.tv_order_hour_miao)
    TextView tv_order_hour_miao;
    @BindView(R.id.tv_order_minute)
    TextView tv_order_minute;
    @BindView(R.id.tv_order_second)
    TextView tv_order_second;
    @BindView(R.id.rv_list_images)
    RecyclerView rvListImages;
    @BindView(R.id.rv_ic_add)
    RecyclerView rvIcAdd;
    @BindView(R.id.btn_service_wanc)
    Button btn_service_wanc;
    private ImagesAdapter mImagesAdapter;
    private AddAdapter addAdapter;
    private List<String> uriList = new ArrayList<>();
    private ArrayList<String> addList = new ArrayList<>();
    private static final int REQUEST_CODE_CHOOSE = 110;
    private String OrderId="";
    private boolean isRun = true;
    private AwaitReceiveOrderEntity awaitReceiveOrderEntity;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1) {
                getTimeChang();
                //每隔1秒更新一次界面，如果只需要精确到秒的倒计时此处改成1000即可
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };

    @Override
    protected int layoutId() {
        return R.layout.accomplish_order_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("确认完成");
        OrderId = ZzRouter.getIntentData(this,String.class);
        mPresenter.initImageSelecter();
        initAdapter();
        mPresenter.getOrderDetails(OrderId);
    }

    private void initAdapter() {
        //图片适配器
        rvListImages.setLayoutManager(new GridLayoutManager(this, 4));
        mImagesAdapter = new ImagesAdapter(R.layout.item_malfunction_repair_image, uriList);
        rvListImages.setAdapter(mImagesAdapter);
        //加号
        if (mImagesAdapter.getData().size()<=4) {
            addList.clear();
            addList.addAll(mImagesAdapter.getData());
            addList.add("");
        }
        rvIcAdd.setLayoutManager(new GridLayoutManager(this, 4));
        addAdapter = new AddAdapter(R.layout.item_malfunction_repair_add, addList);
        rvIcAdd.setAdapter(addAdapter);
        addAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_item) {
                    mPresenter.gotoImageSelect(AccomplishOrderActivity.this, REQUEST_CODE_CHOOSE);
                }else if (view.getId() == R.id.fl_delete) {
                    mImagesAdapter.remove(position);
                    if (mImagesAdapter.getData().size()<=4) {
                        addList.clear();
                        addList.addAll(mImagesAdapter.getData());
                        addList.add("");
                    }
                    addAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.btn_service_wanc})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_service_wanc://完成服务
                if (mImagesAdapter.getData().size()<=0){
                    toast(this,"请拍摄照片");
                }else{
                    mPresenter.commit(mImagesAdapter.getData());
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            final List<String> pathList = data.getStringArrayListExtra("result");
            final ArrayList<String> path2 = new ArrayList<>();
            Luban.with(this)
                    .load(pathList)
                    .ignoreBy(50)
                    .setTargetDir(ConfigApplication.CACHA_URL)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
//                            StyledDialog.buildLoading("正在压缩图片").show();
                        }

                        @Override
                        public void onSuccess(File file) {
                            String absolutePath = file.getAbsolutePath();
                            path2.add(absolutePath);
                            mImagesAdapter.setNewData(path2);
                            if (mImagesAdapter.getData().size()<=4) {
                                //addList.clear();
                                addList.addAll(mImagesAdapter.getData());
                                addList.add("");
                            }
                            addAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mImagesAdapter.setNewData(pathList);
                            if (mImagesAdapter.getData().size()<=4) {
                                //addList.clear();
                                addList.addAll(mImagesAdapter.getData());
                                addList.add("");
                            }
                            addAdapter.notifyDataSetChanged();
                        }
                    }).launch();    //启动压缩

        }
    }

    //订单详情返回数据
    @Override
    public void getOrderDetailsCallBack(AwaitReceiveOrderEntity data) {
        awaitReceiveOrderEntity = data;
        getTimeChang();
        start();
    }

    //服务时长
    private void getTimeChang(){
        int jzTIme =  Integer.parseInt(getTime(awaitReceiveOrderEntity.startTime));//开始服务时间
        int dqTime = Integer.parseInt(getTime(getDQTime()));//当前时间
        //当前时间-开始时间
        int s = dqTime-jzTIme;
        int time = s/3600;
        if (time>=1){
            int h = s/3600;//小时
            s = s-h*3600;//剩余秒数
            int m = s/60;//分钟
            s = s-m*60;//秒数
            tv_order_hour.setText(h+"");//服务剩余时间
            tv_order_minute.setText(m+"");//服务剩余时间
            tv_order_second.setText(s+"");//服务剩余时间

            tv_order_hour.setVisibility(View.VISIBLE);
            tv_order_hour_miao.setVisibility(View.VISIBLE);
        }else{
            int m = s/60;//分钟
            s = s-m*60;//秒数
            tv_order_minute.setText(m+"");//服务剩余时间
            tv_order_second.setText(s+"");//服务剩余时间
            tv_order_hour.setVisibility(View.GONE);
            tv_order_hour_miao.setVisibility(View.GONE);
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

    //完成服务返回数据
    @Override
    public void endServiceCallBack() {
        finish();
    }

    //上传文件返回数据
    @Override
    public void commitCallback(String url) {
        mPresenter.endService(OrderId,url);
    }
}
