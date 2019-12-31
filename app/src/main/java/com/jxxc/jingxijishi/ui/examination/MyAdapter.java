package com.jxxc.jingxijishi.ui.examination;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.jxxc.jingxijishi.entity.backparameter.SartExaminationEntity;

import java.util.List;

public class MyAdapter extends FragmentStatePagerAdapter {
    public List<SartExaminationEntity.Question> list;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<SartExaminationEntity.Question> list) {
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    //得到每个item
    @Override
    public Fragment getItem(int position) {
        return ExaminationActivity.ArrayFragment.newInstance(position,list.get(position),list.size());
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
