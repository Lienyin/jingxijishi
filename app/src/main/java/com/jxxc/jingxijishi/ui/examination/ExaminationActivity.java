package com.jxxc.jingxijishi.ui.examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.AnswerEntity;
import com.jxxc.jingxijishi.entity.backparameter.EndExaminationEntity;
import com.jxxc.jingxijishi.entity.backparameter.SartExaminationEntity;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.http.ZzRouter;
import com.jxxc.jingxijishi.ui.examinationresult.ExaminationResultActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.jxxc.jingxijishi.utils.AppUtils;
import com.jxxc.jingxijishi.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ExaminationActivity extends FragmentActivity {
    //这个是有多少个 fragment页面
    private MyAdapter mAdapter;
    private ViewPager mPager;
    private int nowPage;
    private TextView tv_examination_back;
    private TextView tv_topic_number;
    private TextView tv_examination_affirm;
    private TextView tv_time_kaoshi;
    private List<SartExaminationEntity.Question> list = new ArrayList<>();
    private String examinationId;
    private boolean isRun = true;
    private int s = 3599;
    private String PassScore;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_activity);
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        mPager = (ViewPager) findViewById(R.id.mypagers_pager);
        tv_examination_back = (TextView) findViewById(R.id.tv_examination_back);
        tv_topic_number = (TextView) findViewById(R.id.tv_topic_number);
        tv_examination_affirm = (TextView) findViewById(R.id.tv_examination_affirm);
        tv_time_kaoshi = (TextView) findViewById(R.id.tv_time_kaoshi);
        StyledDialog.buildLoading("加载中").setActivity(this).show();
        startExamination();//获取试题
        timer.schedule(task, 1000, 1000);
        //返回
        tv_examination_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.clickAnimator(view);
                finish();
            }
        });
        //提交
        tv_examination_affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtils.clickAnimator(view);
                StyledDialog.buildLoading("正在提交").setActivity(ExaminationActivity.this).show();
                endExamination(examinationId,ArrayFragment.getAnswerData());
            }
        });
    }

    //获取题目
    public void startExamination() {
        OkGo.<HttpResult<SartExaminationEntity>>post(Api.START_EXAMINATION)
                .tag(this)
                .execute(new JsonCallback<HttpResult<SartExaminationEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<SartExaminationEntity>> response) {
                        StyledDialog.dismissLoading();
                        SartExaminationEntity d = response.body().data;
                        if (response.body().code == 0) {
                            examinationId = d.examinationId;
                            PassScore = d.passScore;
                            list = d.questionList;
                            mAdapter = new MyAdapter(getSupportFragmentManager());
                            mAdapter.setData(ExaminationActivity.this,d.questionList,d.passScore);
                            mPager.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(ExaminationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    //提交题目
    public void endExamination(String examinationId,String answerStr) {
        OkGo.<HttpResult<EndExaminationEntity>>post(Api.END_EXAMINATION)
                .params("examinationId",examinationId)
                .params("answerStr",answerStr)
                .execute(new JsonCallback<HttpResult<EndExaminationEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<EndExaminationEntity>> response) {
                        StyledDialog.dismissLoading();
                        EndExaminationEntity d = response.body().data;
                        if (response.body().code == 0) {
                            Intent intent = new Intent(ExaminationActivity.this, ExaminationResultActivity.class);
                            intent.putExtra("status",d.status);
                            intent.putExtra("score",d.score);
                            intent.putExtra("PassScore",PassScore);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ExaminationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * 所有的  每个Fragment
     */
    public static class ArrayFragment extends Fragment {
        private int mNum;
        private int zNum;
        private String topic;
        private String answerA;
        private String answerB;
        private String answerC;
        private String answerD;
        private String PassScore;
        public static String answer="";
        public static List<String> listStr = new ArrayList<>();
        public static List<AnswerEntity> list = new ArrayList<>();
        private static int zongAnswer=0;
        private static Context context;

        static ArrayFragment newInstance(Context mContext,int num,SartExaminationEntity.Question list,int zongNumber,String passScore) {
            context = mContext;

            ArrayFragment array = new ArrayFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            args.putInt("zNum", zongNumber);
            args.putString("passScore", passScore);
            args.putString("topic",AppUtils.isEmpty(list.topic)?"无题":list.topic);
            args.putString("answerA",AppUtils.isEmpty(list.answerA)?"其他":list.answerA);
            args.putString("answerB",AppUtils.isEmpty(list.answerB)?"其他":list.answerB);
            args.putString("answerC",AppUtils.isEmpty(list.answerC)?"其他":list.answerC);
            args.putString("answerD",AppUtils.isEmpty(list.answerD)?"其他":list.answerD);
            array.setArguments(args);
            return array;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            zNum = getArguments() != null ? getArguments().getInt("zNum") : 0;
            PassScore = getArguments() != null ? getArguments().getString("passScore") : "60";
            topic = getArguments() != null ? getArguments().getString("topic") : "";
            answerA = getArguments() != null ? getArguments().getString("answerA") : "";
            answerB = getArguments() != null ? getArguments().getString("answerB") : "";
            answerC = getArguments() != null ? getArguments().getString("answerC") : "";
            answerD = getArguments() != null ? getArguments().getString("answerD") : "";
            zongAnswer = zNum;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //在这里加载每个 fragment的显示的 View
            View v = null;
            if (mNum == 0) {
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView) v.findViewById(R.id.textView1)).setText(mNum + "= mNum");
                ((TextView) v.findViewById(R.id.tv_topic)).setText((mNum+1)+"."+topic);
                ((RadioButton) v.findViewById(R.id.tv_topic_a)).setText(answerA);
                ((RadioButton) v.findViewById(R.id.tv_topic_b)).setText(answerB);
                ((RadioButton) v.findViewById(R.id.tv_topic_c)).setText(answerC);
                ((RadioButton) v.findViewById(R.id.tv_topic_d)).setText(answerD);
                ((TextView) v.findViewById(R.id.tv_topic_number)).setText((mNum+1)+"/"+zNum);
                ((TextView) v.findViewById(R.id.tv_topic_newt_number)).setText((mNum+1) == zNum?"最后一题":"下一题");
                ((TextView) v.findViewById(R.id.tv_kaoshi_tongguo_hint)).setText("说明：从业上岗资格合格成绩≥"+PassScore+"分");
            } else if (mNum == 1) {
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView) v.findViewById(R.id.textView1)).setText(mNum + "= mNum");
                ((TextView) v.findViewById(R.id.tv_topic)).setText((mNum+1)+"."+topic);
                ((RadioButton) v.findViewById(R.id.tv_topic_a)).setText(answerA);
                ((RadioButton) v.findViewById(R.id.tv_topic_b)).setText(answerB);
                ((RadioButton) v.findViewById(R.id.tv_topic_c)).setText(answerC);
                ((RadioButton) v.findViewById(R.id.tv_topic_d)).setText(answerD);
                ((TextView) v.findViewById(R.id.tv_topic_number)).setText((mNum+1)+"/"+zNum);
                ((TextView) v.findViewById(R.id.tv_topic_newt_number)).setText((mNum+1) == zNum?"最后一题":"下一题");
                ((TextView) v.findViewById(R.id.tv_kaoshi_tongguo_hint)).setText("说明：从业上岗资格合格成绩≥"+PassScore+"分");
            } else if (mNum == 2) {
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView) v.findViewById(R.id.textView1)).setText(mNum + "= mNum");
                ((TextView) v.findViewById(R.id.tv_topic)).setText((mNum+1)+"."+topic);
                ((RadioButton) v.findViewById(R.id.tv_topic_a)).setText(answerA);
                ((RadioButton) v.findViewById(R.id.tv_topic_b)).setText(answerB);
                ((RadioButton) v.findViewById(R.id.tv_topic_c)).setText(answerC);
                ((RadioButton) v.findViewById(R.id.tv_topic_d)).setText(answerD);
                ((TextView) v.findViewById(R.id.tv_topic_number)).setText((mNum+1)+"/"+zNum);
                ((TextView) v.findViewById(R.id.tv_topic_newt_number)).setText((mNum+1) == zNum?"最后一题":"下一题");
                ((TextView) v.findViewById(R.id.tv_kaoshi_tongguo_hint)).setText("说明：从业上岗资格合格成绩≥"+PassScore+"分");
            } else {
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView) v.findViewById(R.id.textView1)).setText(mNum + "= mNum");
                ((TextView) v.findViewById(R.id.tv_topic)).setText((mNum+1)+"."+topic);
                ((RadioButton) v.findViewById(R.id.tv_topic_a)).setText(answerA);
                ((RadioButton) v.findViewById(R.id.tv_topic_b)).setText(answerB);
                ((RadioButton) v.findViewById(R.id.tv_topic_c)).setText(answerC);
                ((RadioButton) v.findViewById(R.id.tv_topic_d)).setText(answerD);
                ((TextView) v.findViewById(R.id.tv_topic_number)).setText((mNum+1)+"/"+zNum);
                ((TextView) v.findViewById(R.id.tv_topic_newt_number)).setText((mNum+1) == zNum?"最后一题":"下一题");
                ((TextView) v.findViewById(R.id.tv_kaoshi_tongguo_hint)).setText("说明：从业上岗资格合格成绩≥"+PassScore+"分");
            }
            if (mNum==0){
                ((TextView) v.findViewById(R.id.tv_kaoshi_tongguo_hint)).setVisibility(View.VISIBLE);
            }else{
                ((TextView) v.findViewById(R.id.tv_kaoshi_tongguo_hint)).setVisibility(View.GONE);
            }


            ((RadioButton) v.findViewById(R.id.tv_topic_a)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answer = (mNum+1)+",A";
                    getAnswer(answer,"A");
                }
            });
            ((RadioButton) v.findViewById(R.id.tv_topic_b)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answer = (mNum+1)+",B";
                    getAnswer(answer,"B");
                }
            });
            ((RadioButton) v.findViewById(R.id.tv_topic_c)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answer = (mNum+1)+",C";
                    getAnswer(answer,"C");
                }
            });
            ((RadioButton) v.findViewById(R.id.tv_topic_d)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answer = (mNum+1)+",D";
                    getAnswer(answer,"D");
                }
            });
            return v;
        }

        AnswerEntity answerEntity = new AnswerEntity();
        //组装数据
        public void getAnswer(String str,String answer){
            int n=0;
            for (int i=0;i < listStr.size();i++){
                if (listStr.get(i).substring(0,listStr.get(i).length()-2).equals(str.substring(0,str.length()-2))){
                    listStr.set(i,str);
                    answerEntity.setAnswer(answer);
                    list.set(i,answerEntity);
                    n=1;
                }
            }
            if (n==0){
                answerEntity.setAnswer(answer);
                list.add(answerEntity);
                listStr.add(str);
            }
        }
        //组装数据成功返回去
        public static String getAnswerData(){
//            if (list.size()<zongAnswer){
//                Toast.makeText(context,"请答完试题",Toast.LENGTH_LONG).show();
//                return "";
//            }
            String str = "";
            for (int i=0;i<list.size();i++){
                str += list.get(i).getAnswer()+",";
            }
            Log.i("TAG","str=="+str);
            return str;
        }

        public static void setEmptyList(){
            if (list.size()>0){
                list.clear();
            }
            if (listStr.size()>0){
                listStr.clear();
            }
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {		// UI thread
                @Override
                public void run() {
                    s--;
                    int m = s/60;//分钟
                    int ss = s-m*60;//秒数
                    tv_time_kaoshi.setText(m+":"+ss);
                    if(s < 0){
                        timer.cancel();
                        tv_time_kaoshi.setText("请提交试卷");
                    }
                }
            });
        }
    };

    //倒计时
    private void daoJishi(){
//        int m = s/60;//分钟
//        s = s-m*60;//秒数
//        if (m>=10){
//            tv_time_kaoshi.setText(m+":"+s);
//        }else{
//            tv_time_kaoshi.setText("  0"+m+":"+s);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayFragment.setEmptyList();
    }
}
