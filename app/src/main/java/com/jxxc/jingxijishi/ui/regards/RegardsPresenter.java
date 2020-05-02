package com.jxxc.jingxijishi.ui.regards;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxijishi.entity.backparameter.LatestVersionEntity;
import com.jxxc.jingxijishi.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sdsmdg.tastytoast.TastyToast;
import com.jxxc.jingxijishi.Api;
import com.jxxc.jingxijishi.BuildConfig;
import com.jxxc.jingxijishi.ConfigApplication;
import com.jxxc.jingxijishi.dialog.UpdataHintDialog;
import com.jxxc.jingxijishi.dialog.UpdateProgressDialog;
import com.jxxc.jingxijishi.http.EventCenter;
import com.jxxc.jingxijishi.http.HttpResult;
import com.jxxc.jingxijishi.http.JsonCallback;
import com.jxxc.jingxijishi.mvp.BasePresenterImpl;
import com.jxxc.jingxijishi.utils.AppUtils;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegardsPresenter extends BasePresenterImpl<RegardsContract.View> implements RegardsContract.Presenter{

    private UpdataHintDialog dialog;
    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 查询app版本
     */
    @Override
    public void queryAppVersion(String type) {
        OkGo.<HttpResult<LatestVersionEntity>>post(Api.LATEST_VERSION)
                .params("type",type)
                .execute(new JsonCallback<HttpResult<LatestVersionEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<LatestVersionEntity>> response) {
                        LatestVersionEntity version = response.body().data;
                        if (response.body().code == 0){
                            SPUtils.put(SPUtils.K_STATIC_URL,version.staticUrl);
                            String url = version.url;
                            String memo = version.memo;
                            String ver = version.version;
                            StyledDialog.dismissLoading();
                            if (!AppUtils.isEmpty(version)) {
                                if (ver.contains(".")) {
                                    String vOnline = ver.replace(".", "").trim();
                                    String versionName = BuildConfig.VERSION_NAME;
                                    String vLoal = versionName.replace(".", "").trim();
                                    if (Integer.parseInt(vOnline) > Integer.parseInt(vLoal)) {
                                        if (version.isForce == 1) {//是否强制更新
                                            updateAPK(url, memo, true,ver);
                                        } else {
                                            updateAPK(url, memo, false,ver);
                                        }
                                    }
                                }
                            }
                            //mView.latestVersionCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 下载apk并安装
     */
    public void updateAPK(final String url, String memo, boolean isMust, String versions) {
        if (!AppUtils.isEmpty(mView)) {
            mView.updateCB(isMust);
        }
        if (isMust) {
            String msg = null;
            if (!AppUtils.isEmpty(memo)) {
                msg = memo+ "\n如不升级将退出应用";
            } else {
                msg = "如不升级将退出应用";
            }
            dialog = new UpdataHintDialog(mContext);
            dialog.showShareDialog(false, msg, "退出应用",versions);
            dialog.setOnFenxiangClickListener(new UpdataHintDialog.OnFenxiangClickListener() {
                @Override
                public void onFenxiangClick(int shareType) {
                    if (shareType == 1) {
                        if (!AppUtils.isEmpty(url)) {  //开启更新
                            startDownloadAPK(url);
                        }
                    }else{
                        ConfigApplication.exit(true);
                    }
                }
            });
        } else {
            String msg = null;
            if (!AppUtils.isEmpty(memo)) {
                msg = memo;
            } else {
                msg = "有更好的版本等着你，快更新吧！";
            }
            dialog = new UpdataHintDialog(mContext);
            dialog.showShareDialog(true, msg, "暂不更新",versions);
            dialog.setOnFenxiangClickListener(new UpdataHintDialog.OnFenxiangClickListener() {
                @Override
                public void onFenxiangClick(int shareType) {
                    if (shareType == 1) {
                        if (!AppUtils.isEmpty(url)) {  //开启更新
                            startDownloadAPK(url);
                        }
                    }else{
                        dialog.cleanDialog();
                    }
                }
            });
        }

    }

    /**
     * 安装apk
     *
     * @param url
     */
    private void startDownloadAPK(String url) {
        UpdateProgressDialog.show(mView.getContext());
        final RxDownload mRxDownload = RxDownload.getInstance(mView.getContext());
        mRxDownload.download(url, "jingxi.apk", ConfigApplication.CACHA_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(DownloadStatus downloadStatus) throws Exception {

                        double v = (double) downloadStatus.getDownloadSize() / downloadStatus.getTotalSize();
                        int progress = (int) (v * 100);
                        UpdateProgressDialog.setProgress(progress,
                                downloadStatus.getFormatDownloadSize() + "/" + downloadStatus.getFormatTotalSize(),
                                downloadStatus.getDownloadSize());
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toast(mContext, "下载失败");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (!AppUtils.isEmpty(mView)) {
                            //下载成功
                            UpdateProgressDialog.dismiss();
                            File file = mRxDownload.getRealFiles("jingxi.apk", ConfigApplication.CACHA_URL)[0];
                            Context context = mView.getContext().getApplicationContext();
                            AppUtils.installApk(context, file, BuildConfig.APPLICATION_ID + ".provider");
                            //install(mContext,file);
                        }

                    }
                });
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public void install(Context context, File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.jxxc.jingxijishi.provider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

}
