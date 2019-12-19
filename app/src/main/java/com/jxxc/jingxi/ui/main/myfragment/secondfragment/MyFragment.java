package com.jxxc.jingxi.ui.main.myfragment.secondfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseFragment;

@SuppressLint("ValidFragment")
public class MyFragment extends MVPBaseFragment<MyFragmentContract.View, MyFragmentPresenter> implements View.OnClickListener, MyFragmentContract.View {
    private Context context;
    private TextView tv_user_name;

    public MyFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment,container,false);
        //tv_user_name = (TextView)view.findViewById(R.id.tv_user_name);

        //tv_user_name.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.ll_door_photo://门头照
//                ZzRouter.gotoActivity((Activity) context, DoorPhotoActivity.class);
//                break;
        }
    }
}
