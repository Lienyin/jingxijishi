package com.jxxc.jingxijishi.ui.usercenter;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.ConfigApplication;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxijishi.entity.requestparameter.ExitLogin;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.updatepassword.UpdatePasswordActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.GlideImgManager;
import com.jxxc.jingxijishi.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * 个人中心--我的
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UsercenterActivity extends MVPBaseActivity<UsercenterContract.View, UsercenterPresenter> implements UsercenterContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_technician_code)
    TextView tv_technician_code;
    @BindView(R.id.tv_user_authentication)
    TextView tv_user_authentication;
    @BindView(R.id.tv_user_info_mobile)
    TextView tv_user_info_mobile;
    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;
    @BindView(R.id.ll_update_password)
    LinearLayout ll_update_password;
    private static final int REQUEST_CODE_CHOOSE = 1100;

    @Override
    protected int layoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initData() {
        tv_title.setText("个人资料");
        mPresenter.initImageSelecter();
    }

    @OnClick({R.id.tv_back,R.id.iv_user_head,R.id.ll_update_password})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.iv_user_head://头像
                mPresenter.gotoImageSelect(this, REQUEST_CODE_CHOOSE);
                break;
            case R.id.ll_update_password://修改密码
                ZzRouter.gotoActivity(this, UpdatePasswordActivity.class);
                break;
            default:
        }
    }

    /**
     * 个人信息返回数据
     * @param userInfo
     */
    @Override
    public void queryUserInfoCallback(UserInfoEntity userInfo) {
        GlideImgManager.loadCircleImage(this, userInfo.avatar, iv_user_head);
        tv_grade.setText(AppUtils.isEmpty(userInfo.grade)?"0级":userInfo.grade);
        tv_technician_code.setText(AppUtils.isEmpty(userInfo.technicianCode)?"暂无":userInfo.technicianCode);
        tv_user_info_mobile.setText(userInfo.phonenumber);
        if (userInfo.isExaminationQualified==1&&userInfo.isOperationQualified==1){
            tv_user_authentication.setText("通过");
        }else{
            tv_user_authentication.setText("未通过");
        }
    }

    /**
     * 修改头像返回数据
     */
    @Override
    public void uploadImageCallBack() {
        mPresenter.getUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            final List<String> pathList = data.getStringArrayListExtra("result");
            if (!AppUtils.isEmpty(pathList)) {
                GlideImgManager.loadRectangleImage(this, pathList.get(0), iv_user_head);
                Luban.with(this)
                        .load(pathList.get(0))
                        .ignoreBy(50)
                        .setTargetDir(ConfigApplication.CACHA_URL)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                //toast(IDCardActivity.this,"正在上传身份证", TastyToast.WARNING);
                                StyledDialog.buildLoading("正在上传头像").setActivity(UsercenterActivity.this).show();
                            }
                            @Override public void onSuccess(File f) {
                                mPresenter.uploadImage(f.getAbsolutePath());
                            }
                            @Override public void onError(Throwable e) {
                                mPresenter.uploadImage(pathList.get(0));
                            }
                        }).launch();    //启动压缩

            }
        }else if (resultCode == 103){
            SPUtils.clear(this);
            EventBus.getDefault().post(new ExitLogin());
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
    }
}
