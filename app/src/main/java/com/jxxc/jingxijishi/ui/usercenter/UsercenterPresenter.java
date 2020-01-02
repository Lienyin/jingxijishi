package com.jxxc.jingxijishi.ui.usercenter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UsercenterPresenter extends BasePresenterImpl<UsercenterContract.View> implements UsercenterContract.Presenter{

    ISListConfig config ;
    /**
     * 获取用户信息
     */
    @Override
    public void getUserInfo() {
        OkGo.<HttpResult<UserInfoEntity>>post(Api.INFO_USER)
                .tag(this)
                .execute(new JsonCallback<HttpResult<UserInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<UserInfoEntity>> response) {
                        HttpResult<UserInfoEntity> body = response.body();
                        if (body.code == 0) {
                            UserInfoEntity userInfo1 = body.data;
                            if (!AppUtils.isEmpty(userInfo1)) {
                                mView.queryUserInfoCallback(userInfo1);
                            }
                        }else{
                            toast(mContext,body.message);
                        }
                    }
                });
    }

    /**
     * 选择图片
     */
    @Override
    public void initImageSelecter() {
        config = new ISListConfig.Builder()
                .multiSelect(false)
                .rememberSelected(true)
                .btnBgColor(Color.TRANSPARENT)
                .btnTextColor(Color.WHITE)
                .statusBarColor(ContextCompat.getColor(mView.getContext().getApplicationContext(), R.color.gray))
                .backResId(R.mipmap.back)
                .title("图片选择")
                .titleColor(Color.WHITE)
                .titleBgColor(ContextCompat.getColor(mView.getContext().getApplicationContext(),R.color.public_all))
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                .needCamera(true)
                .maxNum(1)
                .build();
    }

    @Override
    public void gotoImageSelect(UsercenterActivity activity, int requestCodeChoose) {
        ISNav.getInstance().toListActivity(activity, config, requestCodeChoose);
    }

    /**
     * @param s 头像路径
     */
    @Override
    public void uploadImage(String s) {
        OkGo.<HttpResult>post(Api.UPDATE_INFO)
                .params("avatar",new File(s))
                .isMultipart(true)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (AppUtils.isEmpty(mView)) {
                            return;
                        }
                        if (response.body().code == 0) {
                            mView.uploadImageCallBack();
                            toast(mContext,response.body().message);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }
}
