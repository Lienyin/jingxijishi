package com.jxxc.jingxi.mvp;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.ZzRouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V>{
    public V mView;
    protected Context mContext;
    protected Reference<V> viewRef;
    private static  Toast mToast = null;//全局唯一的Toast
    @Override
    public void attachView(V view) {
        viewRef= new WeakReference<V>(view);
        mView = (V) viewRef.get();
        mContext = view.getContext();
        if (useEvenBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventCenter center) {
        if (null != center) {
            if (center.eventCode == EventCenter.TOKEN_INVALID) {
                ConfigApplication.exit(false);
                try{
//                    toast("登录超时，请重新登录",TastyToast.WARNING);
                    ZzRouter.gotoActivity((Activity) mContext,ConfigApplication.LOGIN_PATH, ZzRouter.NORMAL,"",true);
                }catch (Exception e){
                    try {
//                        YsRouter.gotoActivity((Activity) mContext,ConfigApplication.LOGIN_PATH,YsRouter.HOST_PLUGIN,true);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
            onEventComing(center);
        }
    }

    /**
     * EventBus接收消息
     * @param center 获取事件总线信息
     */
    protected abstract void onEventComing(EventCenter center);
    @Override
    public void detachView() {
        if (useEvenBus()) {
            EventBus.getDefault().unregister(this);
        }
        if(viewRef !=null){
            viewRef.clear();
            viewRef=null;
        }
    }
    protected boolean useEvenBus() {
        return true;
    }

    public static void showLoading(String str) {
        StyledDialog.buildLoading(str).show();
    }
    public static void hideLoading() {
        StyledDialog.dismissLoading();
    }

    public static void toast(Context context,String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }
}
