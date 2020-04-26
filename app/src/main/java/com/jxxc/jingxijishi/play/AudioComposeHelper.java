package com.jxxc.jingxijishi.play;

import android.content.Context;
import android.media.AudioManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 音频拼接合成
 */
public class AudioComposeHelper {
    private static final int SIZE = 2048;
    private static Map<String, String> mMatchMap = new HashMap<>();
    private Context mContext;
    private InputStream mInputStream;
    private AudioManager audioMa;

    public AudioComposeHelper(Context paramContext, String paramString) {
//        mMatchMap.put("B", "tts_success.mp3");
//        mMatchMap.put("零", "tts_0.mp3");
//        mMatchMap.put("一", "tts_1.mp3");
//        mMatchMap.put("二", "tts_2.mp3");
//        mMatchMap.put("三", "tts_3.mp3");
//        mMatchMap.put("四", "tts_4.mp3");
//        mMatchMap.put("五", "tts_5.mp3");
//        mMatchMap.put("六", "tts_6.mp3");
//        mMatchMap.put("七", "tts_7.mp3");
//        mMatchMap.put("八", "tts_8.mp3");
//        mMatchMap.put("九", "tts_9.mp3");
//        mMatchMap.put("点", "tts_dot.mp3");
//        mMatchMap.put("十", "tts_ten.mp3");
//        mMatchMap.put("百", "tts_hundred.mp3");
//        mMatchMap.put("千", "tts_thousand.mp3");
//        mMatchMap.put("万", "tts_ten_thousand.mp3");
//        mMatchMap.put("亿", "tts_ten_million.mp3");
//        mMatchMap.put("元", "tts_yuan.mp3");
        /**
         * 1.抢单大厅出新单
         * 2.抢单成功
         * 3.转单成功
         * 4.订单取消
         * 5.订单即将超时
         * 6.订单已经超时
         * 7.会员升级
         * 10.客户催单
         */
        mMatchMap.put("一", "qiang_order.mp3");
        mMatchMap.put("二", "qiang_order_chenggong.mp3");
        mMatchMap.put("三", "order_zhuan.mp3");
        mMatchMap.put("四", "order_cancel.mp3");
        mMatchMap.put("五", "order_chaoshi.mp3");
        mMatchMap.put("六", "order_yi_chaoshi.mp3");
        mMatchMap.put("七", "vip_shengji.mp3");
        mMatchMap.put("十", "gui_order.mp3");
        mInputStream = null;
        mContext = paramContext;
        read(paramString);
    }

    private void read(String paramString) {
        //设置音量
//        audioMa = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
//        final int current = audioMa.getStreamVolume(AudioManager.STREAM_MUSIC);//当前音量
//        final int max = audioMa.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//最大音量
//        if (current < max-5){
//            audioMa.setStreamVolume(AudioManager.STREAM_MUSIC,audioMa.getStreamMaxVolume
//                    (AudioManager.STREAM_MUSIC),AudioManager.FLAG_SHOW_UI);
//        }

        for (int i = 0;i < paramString.length() ; i++) {
            try {
                String str = "tts" + File.separator + mMatchMap.get(String.valueOf(paramString.charAt(i)));
                Log.i("tts", "=========" + str);
                InputStream local = mContext.getAssets().openFd(str).createInputStream();
                if(mInputStream == null){
                    mInputStream = local;
                }else{
                    mInputStream = new SequenceInputStream(mInputStream, new BufferedInputStream(local));
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(6500);
//                    if (current < max-5){
//                        audioMa.setStreamVolume(AudioManager.STREAM_MUSIC,current,AudioManager.FLAG_SHOW_UI);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private String getDiskCacheDir() {
        String cachePath;
        boolean existed = (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) && mContext.getExternalCacheDir() != null;
        if (existed) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return cachePath;
    }

    public File getAudioFile() throws IOException {
        if (this.mInputStream == null) {
            return null;
        }
        File localFile = new File(getDiskCacheDir() + "/xxx.mp3");
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
        byte[] arrayOfByte = new byte[SIZE];
        int length = mInputStream.read(arrayOfByte);
        while(length > 0) {
            outputStream.write(arrayOfByte, 0, length);
            length = mInputStream.read(arrayOfByte);
        }
        outputStream.flush();
        mInputStream.close();
        outputStream.close();
        return localFile;
    }
}
