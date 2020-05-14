package com.jxxc.jingxijishi.ui.photoview;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PhotoViewActivity extends MVPBaseActivity<PhotoViewContract.View, PhotoViewPresenter> implements PhotoViewContract.View {

    public static final String TAG = PhotoViewActivity.class.getSimpleName();
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private List<String> Urls = new ArrayList<>();
    private String dataBeanUrl;
    @Override
    protected int layoutId() {
        return R.layout.photo_view_activity;
    }

    @Override
    public void initData() {

        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);

        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        dataBeanUrl = intent.getStringExtra("dataBeanUrl");
//        HomeQuestionListModel.DataBeanX DataBean = ((HomeQuestionListModel.DataBeanX) intent.getSerializableExtra("questionlistdataBean"));
//        Urls = DataBean.getAttach().getImage().getOri();

        //技师端上传的图片
        if (!AppUtils.isEmpty(dataBeanUrl)){
            String[] split = dataBeanUrl.split(",");
            for (int i=0;i<split.length;i++){
                Urls.add(split[i]);
            }
        }

        adapter = new MyImageAdapter(Urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition+1 + "/" + Urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
            }
        });
    }
}
