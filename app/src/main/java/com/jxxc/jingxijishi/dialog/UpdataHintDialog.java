package com.jxxc.jingxijishi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;


/**
 * app更新提示
 */

public class UpdataHintDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private TextView tv_update_context;
    private TextView tv_update_yes;
    private TextView tv_update_no;
    private TextView tv_versions;

    public UpdataHintDialog(Context context){
        this(context,true);
    }

    public UpdataHintDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.layout_updata_hint_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);

        tv_update_context = (TextView) view.findViewById(R.id.tv_update_context);
        tv_update_yes = (TextView) view.findViewById(R.id.tv_update_yes);
        tv_update_no = (TextView) view.findViewById(R.id.tv_update_no);
        tv_versions = (TextView) view.findViewById(R.id.tv_versions);
        tv_update_yes.setOnClickListener(this);
        tv_update_no.setOnClickListener(this);

    }

    public void showShareDialog(boolean outTouchCancel,String hint,String no,String versions) {
        tv_update_context.setText(hint);
        tv_update_no.setText(no);
        tv_versions.setText("V"+versions);
        dialog.setCanceledOnTouchOutside(outTouchCancel);
        dialog.show();
    }

    public void cleanDialog(){
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update_yes://立刻更新
                onFenxiangClickListener.onFenxiangClick(1);
                cleanDialog();
                break;
            case R.id.tv_update_no://暂不更新
                onFenxiangClickListener.onFenxiangClick(0);
                cleanDialog();
                break;
        }
    }
    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(int shareType);
    }
}

