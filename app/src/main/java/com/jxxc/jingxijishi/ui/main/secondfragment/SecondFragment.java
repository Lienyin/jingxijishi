package com.jxxc.jingxijishi.ui.main.secondfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseFragment;

@SuppressLint("ValidFragment")
public class SecondFragment extends MVPBaseFragment<SecondFramentContract.View, SecondFramentPresenter> implements View.OnClickListener, SecondFramentContract.View {
    private Context context;
    private TextView tv_user_name;

    public SecondFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment,container,false);
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
