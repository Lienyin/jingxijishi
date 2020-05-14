package com.jxxc.jingxijishi.ui.regards;


import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.BuildConfig;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.regardsagreement.RegardsAgreementActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 关于我们
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegardsActivity extends MVPBaseActivity<RegardsContract.View, RegardsPresenter> implements RegardsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.ll_grade)
    LinearLayout ll_grade;
    @BindView(R.id.ll_privacy)
    LinearLayout ll_privacy;
    @BindView(R.id.ll_renewal)
    LinearLayout ll_renewal;
    @BindView(R.id.ll_official_website)
    LinearLayout ll_official_website;
    @Override
    protected int layoutId() {
        return R.layout.activity_regards;
    }

    @Override
    public void initData() {
        tv_title.setText("关于我们");
        String versionName = BuildConfig.VERSION_NAME;
        tv_version.setText("V "+versionName);
    }

    @OnClick({R.id.tv_back,R.id.ll_grade,R.id.ll_privacy,R.id.ll_renewal,R.id.ll_official_website})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_grade://给我评分
                gradeGo();
                break;
            case R.id.ll_privacy://隐私政策
                String URL = "https://jx.bashideban.com/tool/build/ios_privacy";
                Intent intent = new Intent(this, RegardsAgreementActivity.class);
                intent.putExtra("URL",URL);
                intent.putExtra("h5Type","0");
                startActivity(intent);
                break;
            case R.id.ll_renewal://版本更新
                StyledDialog.buildLoading("加载中").setActivity(this).show();
                mPresenter.queryAppVersion("1");
                break;
            case R.id.ll_official_website://官方网站
                break;
            default:
        }
    }

    @Override
    public void updateCB(boolean must) {

    }

    public void gradeGo(){
        try{
            Uri uri = Uri.parse("market://details?id="+getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(this, "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
