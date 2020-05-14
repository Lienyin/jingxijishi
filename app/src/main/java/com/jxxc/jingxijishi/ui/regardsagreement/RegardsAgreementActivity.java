package com.jxxc.jingxijishi.ui.regardsagreement;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jxxc.jingxijishi.R;
import com.jxxc.jingxijishi.mvp.MVPBaseActivity;
import com.jxxc.jingxijishi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 用户协议
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegardsAgreementActivity extends MVPBaseActivity<RegardsAgreementContract.View, RegardsAgreementPresenter> implements RegardsAgreementContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.wv_agreement)
    WebView wv_agreement;
    @BindView(R.id.progressBar1)
    ProgressBar pg1;
    private String URL = "";
    private String h5Type = "";

    @Override
    protected int layoutId() {
        return R.layout.activity_regards_agreement;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData() {
        URL = getIntent().getStringExtra("URL");
        h5Type = getIntent().getStringExtra("h5Type");
        if ("0".equals(h5Type)) {
            tv_title.setText("隐私政策");
        } else if ("1".equals(h5Type)) {
            tv_title.setText("用户协议");
        } else if ("2".equals(h5Type)) {
            tv_title.setText("江苏汇智拓信息科技有限公司");
        } else {
            tv_title.setText("菁喜科技");
        }

//        wv_agreement.getSettings().setJavaScriptEnabled(true);
//        WebSettings settings = wv_agreement.getSettings();
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);

        WebSettings webSettings = wv_agreement.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        // 设置WebView属性，能够执行Javascript脚本
        // mWebView.getSettings().setJavaScriptEnabled(true);
        //3、 加载需要显示的网页
        ///4、设置响应超链接，在安卓5.0系统，不使用下面语句超链接也是正常的，但在MIUI中安卓4.4.4中需要使用下面这条语句，才能响应超链接
        // mWebView.setWebViewClient(new HelloWebViewClient());

        wv_agreement.loadUrl(URL);

        // 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
        wv_agreement.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                }else{
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });
        wv_agreement.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//ture为在webview中打开
            }
        });
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }
}
