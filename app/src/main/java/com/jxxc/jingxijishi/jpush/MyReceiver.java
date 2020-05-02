package com.jxxc.jingxijishi.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jxxc.jingxijishi.play.AudioPlayer;
import com.jxxc.jingxijishi.ui.message.MessageActivity;
import com.jxxc.jingxijishi.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "TAG";
	private static Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		try {
			Bundle bundle = intent.getExtras();
			Log.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
				//VoiceUtils.with(context).Play("20",true);
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Log.i(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Log.i(TAG, "[MyReceiver] 用户点击打开了通知");
				Intent intent1 = new Intent(context, MessageActivity.class);
				intent1.putExtras(bundle);
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent1);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value1:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value2:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

                    /**
                     * 1.抢单大厅出新单
                     * 2.抢单成功
                     * 3.转单成功
                     * 4.订单取消
                     * 5.订单即将超时
                     * 6.订单已经超时
                     * 7.会员升级
                     */
					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value3: [" +myKey + " - " +json.optString(myKey) + "]");
						if ("sourceType".equals(myKey)&&"1".equals(json.optString(myKey))){
							//抢单大厅出新单
							AudioPlayer.getInstance().startPlay(mContext, "1");
						}else if ("sourceType".equals(myKey)&&"2".equals(json.optString(myKey))){
							//抢单成功
							AudioPlayer.getInstance().startPlay(mContext, "2");
						}else if ("sourceType".equals(myKey)&&"3".equals(json.optString(myKey))){
							//转单成功
							AudioPlayer.getInstance().startPlay(mContext, "3");
						}else if ("sourceType".equals(myKey)&&"4".equals(json.optString(myKey))){
							//订单取消
							AudioPlayer.getInstance().startPlay(mContext, "4");
						}else if ("sourceType".equals(myKey)&&"5".equals(json.optString(myKey))){
                            //订单即将超时
                            AudioPlayer.getInstance().startPlay(mContext, "5");
                        }else if ("sourceType".equals(myKey)&&"6".equals(json.optString(myKey))){
                            //订单已经超时
                            AudioPlayer.getInstance().startPlay(mContext, "6");
                        }else if ("sourceType".equals(myKey)&&"7".equals(json.optString(myKey))){
                            //会员升级
                            AudioPlayer.getInstance().startPlay(mContext, "7");
                        }else if ("sourceType".equals(myKey)&&"8".equals(json.optString(myKey))){
							//分配单
							AudioPlayer.getInstance().startPlay(mContext, "8");
						}else if ("sourceType".equals(myKey)&&"10".equals(json.optString(myKey))){
							//会员升级
							AudioPlayer.getInstance().startPlay(mContext, "10");
						}
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value4:" + bundle.get(key));
				//TTsUtil.init((Activity) context).speak("哈哈哈哈哈"+bundle.get(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
	}
}
