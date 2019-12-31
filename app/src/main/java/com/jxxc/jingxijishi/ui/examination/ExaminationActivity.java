package com.jxxc.jingxijishi.ui.examination;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.entity.backparameter.SartExaminationEntity;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
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
    private List<SartExaminationEntity.Question> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_activity);
        mPager = (ViewPager) findViewById(R.id.mypagers_pager);
        tv_examination_back = (TextView) findViewById(R.id.tv_examination_back);
        tv_topic_number = (TextView) findViewById(R.id.tv_topic_number);
        startExamination();//获取试题
        //返回
        tv_examination_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                        SartExaminationEntity d = response.body().data;
                        if (response.body().code == 0) {
                            list = d.questionList;
                            mAdapter = new MyAdapter(getSupportFragmentManager());
                            mAdapter.setData(d.questionList);
                            mPager.setAdapter(mAdapter);
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

        static ArrayFragment newInstance(int num,SartExaminationEntity.Question list,int zongNumber) {
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
            return v;
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
