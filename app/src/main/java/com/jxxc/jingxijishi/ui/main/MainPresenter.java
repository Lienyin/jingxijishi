package com.jxxc.jingxijishi.ui.main;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.BuildConfig;
import com.jxxc.jingxijishi.ConfigApplication;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.jxxc.jingxijishi.utils.AppUtils;
import java.io.File;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

}
