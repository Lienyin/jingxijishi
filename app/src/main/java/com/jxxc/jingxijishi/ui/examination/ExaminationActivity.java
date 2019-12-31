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
import android.widget.TextView;
import com.jxxc.jingxijishi.R;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ExaminationActivity extends FragmentActivity {
    //这个是有多少个 fragment页面
    static final int NUM_ITEMS = 5;
    private MyAdapter mAdapter;
    private ViewPager mPager;
    private int  nowPage;
    private TextView tv_examination_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_activity);
        mAdapter = new MyAdapter(getSupportFragmentManager() );
        mPager = (ViewPager)findViewById(R.id.mypagers_pager);
        tv_examination_back = (TextView) findViewById(R.id.tv_examination_back);
        mPager.setAdapter(mAdapter);

        //返回
        tv_examination_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     *  有状态的 ，只会有前3个存在 其他销毁，  前1个， 中间， 下一个
     */
    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        //得到每个item
        @Override
        public Fragment getItem(int position) {
            return ArrayFragment.newInstance(position);
        }
        // 初始化每个页卡选项
        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            // TODO Auto-generated method stub
            return super.instantiateItem(arg0, arg1);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println( "position Destory" + position);
            super.destroyItem(container, position, object);
        }
    }

    /**
     * 所有的  每个Fragment
     */
    public static class ArrayFragment extends Fragment {

        int mNum;
        static ArrayFragment newInstance(int num) {
            ArrayFragment  array= new ArrayFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            array.setArguments(args);
            return array;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            System.out.println("mNum Fragment create ="+ mNum);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            System.out.println("onCreateView = ");
            //在这里加载每个 fragment的显示的 View
            View v = null;
            if(mNum == 0){
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView)v.findViewById(R.id.textView1)).setText(mNum+ "= mNum");
            }else if(mNum == 1){
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView)v.findViewById(R.id.textView1)).setText(mNum+ "= mNum");
            }else  if(mNum == 2){
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView)v.findViewById(R.id.textView1)).setText(mNum+ "= mNum");
            }else{
                v = inflater.inflate(R.layout.pagers_fragment1, container, false);
                ((TextView)v.findViewById(R.id.textView1)).setText(mNum+ "= mNum");
            }
            return v;
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            System.out.println("onActivityCreated = ");
            super.onActivityCreated(savedInstanceState);
        }
        @Override
        public void onDestroyView(){
            System.out.println(mNum + "mNumDestory");
            super.onDestroyView();
        }
        @Override
        public void onDestroy(){
            super.onDestroy();
        }
    }
}
