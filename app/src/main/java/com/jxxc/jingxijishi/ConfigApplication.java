package com.jxxc.jingxijishi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jxxc.jingxijishi.utils.BasicThreadFactory;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.sdsmdg.tastytoast.TastyToast;
import com.wanjian.cockroach.Cockroach;
import com.jxxc.jingxijishi.http.Constants;
import com.jxxc.jingxijishi.http.DensityHelper;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;

import java.io.IOException;
import java.io.Serializable;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class ConfigApplication extends MultiDexApplication implements Serializable {
    // TODO: 2017/11/21 此处需要针对不同宿主进行改写
    public static Stack<Activity> activityStack;
    public static  String LOGIN_PATH="com.jxxc.jingxijishi.ui.login.LoginActivity";
    public static ExecutorService poolExecutor = new ThreadPoolExecutor(3, 5,
            1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
    private HttpsUtils.SSLParams sslParams3;
    public static  long TOKEN_CREATE_TIME= 0;
    public static OkHttpClient okHttpClient;
    public static Toast gToast;
    private static ConfigApplication instans;
    private static Context mContext;
    public static  String CACHA_URL;
    public static ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(3,
            new BasicThreadFactory.Builder().namingPattern("轮询").daemon(true).build());

    @Override
    public void onCreate() {
        super.onCreate();
        instans = this;
        mContext = instans.getApplicationContext();
        MultiDex.install(this);
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        //将可以延时初始化的框架放入服务中
//        MyIntentService.start(this);
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
        try {
            sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("apiclient_cert.p12"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
//        if (BuildConfig.DEBUG) {
//        }
        builder.addInterceptor(loggingInterceptor);
        builder.sslSocketFactory(sslParams3.sSLSocketFactory, sslParams3.trustManager);
//        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

//        HttpHeaders mHeader = new HttpHeaders();
//        mHeader.put("Authorization","Bearer "+ SPUtils.get("token",""));
// 全局请求头刷新有问题。需要token的接口请单独添加
        okHttpClient = builder.build();
        OkGo.getInstance().init(this).setOkHttpClient(okHttpClient);
        initLifecycle();
        new DensityHelper(this, Constants.DESIGN_WIDTH).activate();
//        Stetho.initializeWithDefaults(this);
        SPUtils.init(this);
         CACHA_URL = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : Environment.getDownloadCacheDirectory().getAbsolutePath();
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


    private void initLifecycle() {
        activityStack = new Stack<>();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityStack.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //在这里保存顶层activity的引用(内部以软引用实现)
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityStack.remove(activity);
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }

    private static class SingletonHolder {

        private static final ConfigApplication INSTANCE = instans;
    }

    public static final ConfigApplication getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // 遍历所有Activity并finish
    public static void exit(boolean isRealExit) {
        if (!AppUtils.isEmpty(activityStack)) {
            if (activityStack != null) {
                synchronized (activityStack) {
                    for (Activity act : activityStack) {
                        act.finish();
                    }
                }
            }
            if (isRealExit) {
                //当点击退出登录和token超时不是真的退出
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }

        }
    }
    public static void toast(String message, int type) {
        if (gToast != null) {
            gToast.cancel();
        }
        gToast = TastyToast.makeText(getContext(), message, 0, type);
        gToast.show();
    }

    public static void toast(String message) {
        toast(message, TastyToast.DEFAULT);
    }
}
