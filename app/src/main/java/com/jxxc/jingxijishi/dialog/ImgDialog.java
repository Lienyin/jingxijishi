package com.jxxc.jingxijishi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.ui.orderdetails.LookImgAdapter;

import java.util.List;


/**
 * 取消订单
 */

public class ImgDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private ViewPager viewPager;
    private LinearLayout ll_img_icon;

    public ImgDialog(Context context){
        this(context,true);
    }

    public ImgDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.img_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);

        ll_img_icon = view.findViewById(R.id.ll_img_icon);
        viewPager = view.findViewById(R.id.viewPager);
        ll_img_icon.setOnClickListener(this);

    }

    public void showShareDialog(boolean outTouchCancel, List<String> url) {
        LookImgAdapter adapter = new LookImgAdapter(context,url);
        viewPager.setAdapter(adapter);
        adapter.setOnFenxiangClickListener(new LookImgAdapter.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick() {
                cleanDialog();
            }
        });
        dialog.setCanceledOnTouchOutside(outTouchCancel);
        dialog.show();
    }

    public void cleanDialog(){
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_img_icon://取消
                cleanDialog();
                break;
        }
    }
}

