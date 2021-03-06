package com.jxxc.jingxijishi.ui.examination;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.jxxc.jingxijishi.entity.backparameter.SartExaminationEntity;

import java.util.List;

public class MyAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private String PassScore;
    public List<SartExaminationEntity.Question> list;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(Context context,List<SartExaminationEntity.Question> list,String PassScore) {
        this.list = list;
        this.context = context;
        this.PassScore = PassScore;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    //得到每个item
    @Override
    public Fragment getItem(int position) {
        return ExaminationActivity.ArrayFragment.newInstance(context,position,list.get(position),list.size(),PassScore);
    }
    // 初始化每个页卡选项
    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        // TODO Auto-generated method stub
        return super.instantiateItem(arg0, arg1);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
