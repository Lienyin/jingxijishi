package com.jxxc.jingxijishi.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.utils.AppUtils;

public class PopSeek extends PopupWindow {
    private View conentView;
    private Context mContext;
    private LinearLayout tv_qrest,tv_xia_xian;

    public PopSeek(final Activity context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.title_seek, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(280);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimationPreview);
        //this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
    }

    private void initView(){
        tv_xia_xian = conentView.findViewById(R.id.tv_xia_xian);
        tv_qrest = conentView.findViewById(R.id.tv_qrest);
        tv_qrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFenxiangClickListener.onFenxiangClick(1);
            }
        });
        tv_xia_xian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFenxiangClickListener.onFenxiangClick(2);
            }
        });
    }
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getLayoutParams().width, 18);
        } else {
            this.dismiss();
        }
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(int type);
    }
}
