package com.jxxc.jingxijishi.ui.examinationresult;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.ui.examination.ExaminationActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ExaminationResultActivity extends MVPBaseActivity<ExaminationResultContract.View, ExaminationResultPresenter> implements ExaminationResultContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_score_memo)
    TextView tv_score_memo;
    @BindView(R.id.btn_back_home)
    Button btn_back_home;
    private int status;
    private String score;
    @Override
    protected int layoutId() {
        return R.layout.examination_result_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("技师认证");
        status = getIntent().getIntExtra("status",-1);
        score = getIntent().getStringExtra("score");

        tv_score.setText(score);
        //通过状态 1通过 0未通过
        if (status ==1){
            tv_score_memo.setText("分，符合从业上岗资质。");
        }else{
            tv_score_memo.setText("分，不符合从业上岗资质。");
        }
    }

    @OnClick({R.id.tv_back,R.id.btn_back_home})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_back_home://返回首页
                finish();
                break;
            default:
        }
    }
}
