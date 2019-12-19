package com.jxxc.jingxijishi.http;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author feisher on 2017/10/27
 *  Email：458079442@qq.com
 *  修改：2018年2月2日16:02:00，去除插件支持，兼容任意改包名
 *
 */
public class ZzRouter {

    public static final int NORMAL = 1;
    public static final int HOST_PLUGIN = 2;
    public static final int PLUGIN1_PLUGIN2 = 3;
    public static final int PLUGIN_HOST = 4;
    public static final String INTENT_DATA = "intent_data";
    @IntDef({NORMAL, HOST_PLUGIN, PLUGIN1_PLUGIN2, PLUGIN_HOST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface JampType {
    }


    public static <T extends AppCompatActivity> boolean gotoActivity(Activity context, Class<T> targetActivity) {
        return gotoActivity(context, targetActivity, NORMAL);
    }

    /**
     *
     *
     * @param context
     * @param targetActivityFullName
     * @param jampType
     * @return
     */
    public static boolean gotoActivity(Activity context, String targetActivityFullName, @JampType int jampType) {
        return gotoActivity(context, targetActivityFullName, jampType, "", true);
    }

    public static <T extends AppCompatActivity, V> boolean gotoActivity(Activity context, Class<T> targetActivity, V intentValue) {

        return gotoActivity(context, targetActivity.getName(), NORMAL, intentValue, true);
    }

    public static <T extends AppCompatActivity, V> boolean gotoActivity(Activity context, String targetActivityFullName, int jampType, V intentValue, boolean isInApp) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        String targetAppId;
        if (isInApp) {
            targetAppId = context.getApplicationInfo().processName;
//            OkLogger.d("context.getApplicationInfo().processName ==== "+targetAppId);
        } else {
            String[] split = targetActivityFullName.split("\\.");
            targetAppId = split[0].concat(".").concat(split[1]).concat(".").concat(split[2]);
        }
        ;
        ComponentName componentName = new ComponentName(targetAppId, targetActivityFullName);
        intent.setComponent(componentName);
        bundle.putString(INTENT_DATA, GsonUtil.toJson(intentValue));
        intent.putExtras(bundle);
        switch (jampType) {
            case NORMAL:
            case PLUGIN1_PLUGIN2:
            case HOST_PLUGIN:
            case PLUGIN_HOST:
                context.startActivity(intent);
                return true;
//            case HOST_PLUGIN:
//                return  RePlugin.startActivity(context,intent);
            default:
                return false;
        }
    }

    public static <T> T getIntentData(Activity context, Class<T> clazz) {
//        String packageName = context.getPackageName();
//        String targetActivityFullName = packageName.concat("."+context.getLocalClassName());
        Intent intent = context.getIntent();
        Bundle extras = intent.getExtras();
        if (null != extras) {
            String string = extras.getString(INTENT_DATA);
            return GsonUtil.fromJson(string, clazz);
        }
        return null;
    }

    public static <T extends AppCompatActivity> boolean gotoActivityForResult(Activity context, Class<T> targetActivity, int requestCode) {
        return gotoActivity(context, targetActivity, NORMAL, requestCode);
    }

    /**
     *
     *
     * @param context
     * @param targetActivityFullName
     * @param jampType
     * @param requestCode
     * @return
     */
    public static boolean gotoActivity(Activity context, String targetActivityFullName, @JampType int jampType, int requestCode) {
        return gotoActivity(context, targetActivityFullName, jampType, "", requestCode, true);
    }

    public static <T extends AppCompatActivity, V> boolean gotoActivity(Activity context, Class<T> targetActivity, V intentValue, int requestCode) {
        return gotoActivity(context, targetActivity.getName(), NORMAL, intentValue, requestCode, true);
    }

    public static <T extends AppCompatActivity, V> boolean gotoActivity(Activity context, String targetActivityFullName, int jampType, V intentValue, int requestCode, boolean isInApp) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        String targetAppId;
        if (isInApp) {
            targetAppId = context.getApplicationInfo().processName;
//            OkLogger.d("context.getApplicationInfo().processName ==== "+targetAppId);
        } else {
            String[] split = targetActivityFullName.split("\\.");
            targetAppId = split[0].concat(".").concat(split[1]).concat(".").concat(split[2]);
        }
        ComponentName componentName = new ComponentName(targetAppId, targetActivityFullName);
        intent.setComponent(componentName);
        bundle.putString(INTENT_DATA, GsonUtil.toJson(intentValue));
        intent.putExtras(bundle);
        switch (jampType) {
            case NORMAL:
            case PLUGIN1_PLUGIN2:
            case PLUGIN_HOST:
                context.startActivityForResult(intent, requestCode);
                return true;
            case HOST_PLUGIN:
//                return  RePlugin.startActivityForResult(context,intent,requestCode);
            default:
                return false;
        }
    }
}
