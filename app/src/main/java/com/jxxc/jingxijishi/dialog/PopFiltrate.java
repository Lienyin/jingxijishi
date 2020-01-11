package com.jxxc.jingxijishi.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.jxxc.jingxijishi.R;

public class PopFiltrate extends PopupWindow {
    private View conentView;
    private Context mContext;
    private RadioButton rb_filtrate_juli,rb_filtrate_time,rb_filtrate_money,rb_filtrate_empty;

    public PopFiltrate(final Activity context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_filtrate, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
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
        rb_filtrate_empty = conentView.findViewById(R.id.rb_filtrate_empty);
        rb_filtrate_money = conentView.findViewById(R.id.rb_filtrate_money);
        rb_filtrate_time = conentView.findViewById(R.id.rb_filtrate_time);
        rb_filtrate_juli = conentView.findViewById(R.id.rb_filtrate_juli);
        //排序 0智能排序(不传 默认0)1距离优先2时间有限3金额优先
        rb_filtrate_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFenxiangClickListener.onFenxiangClick(0);
                dismiss();
            }
        });
        rb_filtrate_juli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFenxiangClickListener.onFenxiangClick(1);
                dismiss();
            }
        });
        rb_filtrate_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFenxiangClickListener.onFenxiangClick(2);
                dismiss();
            }
        });
        rb_filtrate_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFenxiangClickListener.onFenxiangClick(3);
                dismiss();
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
