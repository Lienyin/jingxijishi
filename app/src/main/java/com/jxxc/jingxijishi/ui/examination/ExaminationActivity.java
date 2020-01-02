package com.jxxc.jingxijishi.ui.examination;

import android.content.Context;
import android.os.Bundle;
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
import com.jxxc.jingxijishi.utils.AnimUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

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
    private List<SartExaminationEntity.Question> list = new ArrayList<>();
    private String examinationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_activity);
        mPager = (ViewPager) findViewById(R.id.mypagers_pager);
        tv_examination_back = (TextView) findViewById(R.id.tv_examination_back);
        tv_topic_number = (TextView) findViewById(R.id.tv_topic_number);
        tv_examination_affirm = (TextView) findViewById(R.id.tv_examination_affirm);
        StyledDialog.buildLoading("加载中").setActivity(this).show();
        startExamination();//获取试题
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
                            list = d.questionList;
                            mAdapter = new MyAdapter(getSupportFragmentManager());
                            mAdapter.setData(ExaminationActivity.this,d.questionList);
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
                            //通过状态 1通过 0未通过
                            if (d.status ==1){
                                Toast.makeText(ExaminationActivity.this, "恭喜您通过", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(ExaminationActivity.this, "您未通过考试", Toast.LENGTH_LONG).show();
                            }
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
        public static String answer="";
        public static List<String> listStr = new ArrayList<>();
        public static List<AnswerEntity> list = new ArrayList<>();
        private static int zongAnswer=0;
        private static Context context;

        static ArrayFragment newInstance(Context mContext,int num,SartExaminationEntity.Question list,int zongNumber) {
            context = mContext;
            ArrayFragment array = new ArrayFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            args.putInt("zNum", zongNumber);
            args.putString("topic",list.topic);
            args.putString("answerA",list.answerA);
            args.putString("answerB",list.answerB);
            args.putString("answerC",list.answerC);
            args.putString("answerD",list.answerD);
            array.setArguments(args);
            return array;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            zNum = getArguments() != null ? getArguments().getInt("zNum") : 0;
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
            } else if (mNum == 1) {
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView) v.findViewById(R.id.textView1)).setText(mNum + "= mNum");
                ((TextView) v.findViewById(R.id.tv_topic)).setText((mNum+1)+"."+topic);
                ((RadioButton) v.findViewById(R.id.tv_topic_a)).setText(answerA);
                ((RadioButton) v.findViewById(R.id.tv_topic_b)).setText(answerB);
                ((RadioButton) v.findViewById(R.id.tv_topic_c)).setText(answerC);
                ((RadioButton) v.findViewById(R.id.tv_topic_d)).setText(answerD);
                ((TextView) v.findViewById(R.id.tv_topic_number)).setText((mNum+1)+"/"+zNum);
            } else if (mNum == 2) {
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView) v.findViewById(R.id.textView1)).setText(mNum + "= mNum");
                ((TextView) v.findViewById(R.id.tv_topic)).setText((mNum+1)+"."+topic);
                ((RadioButton) v.findViewById(R.id.tv_topic_a)).setText(answerA);
                ((RadioButton) v.findViewById(R.id.tv_topic_b)).setText(answerB);
                ((RadioButton) v.findViewById(R.id.tv_topic_c)).setText(answerC);
                ((RadioButton) v.findViewById(R.id.tv_topic_d)).setText(answerD);
                ((TextView) v.findViewById(R.id.tv_topic_number)).setText((mNum+1)+"/"+zNum);
            } else {
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView) v.findViewById(R.id.textView1)).setText(mNum + "= mNum");
                ((TextView) v.findViewById(R.id.tv_topic)).setText((mNum+1)+"."+topic);
                ((RadioButton) v.findViewById(R.id.tv_topic_a)).setText(answerA);
                ((RadioButton) v.findViewById(R.id.tv_topic_b)).setText(answerB);
                ((RadioButton) v.findViewById(R.id.tv_topic_c)).setText(answerC);
                ((RadioButton) v.findViewById(R.id.tv_topic_d)).setText(answerD);
                ((TextView) v.findViewById(R.id.tv_topic_number)).setText((mNum+1)+"/"+zNum);
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

        //组装数据
        public void getAnswer(String str,String answer){
            int n=0;
            AnswerEntity answerEntity = new AnswerEntity();
            for (int i=0;i<listStr.size();i++){
                if (listStr.get(i).toString().equals(str)){
                    n=1;
                }
            }
            if (n==0){
                answerEntity.setAnswer(answer);
                list.add(answerEntity);
                listStr.add(str);
            }else{
                list.remove(answerEntity);
                listStr.remove(str);
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
}
