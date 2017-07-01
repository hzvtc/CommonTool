package com.as.commontool.util.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;


/**
 * 跟网络相关的工具类
 * 
 * @author zhy
 * 
 */
public class NetManager
{
	public static final String TAG="NetUtils";
	private static final int NETWORK_TYPE_UNAVAILABLE = -1;
	// private static final int NETWORK_TYPE_MOBILE = -100;
	private static final int NETWORK_TYPE_WIFI = -101;

	private static final int NETWORK_CLASS_WIFI = -101;
	private static final int NETWORK_CLASS_UNAVAILABLE = -1;
	/** Unknown network class. */
	private static final int NETWORK_CLASS_UNKNOWN = 0;
	/** Class of broadly defined "2G" networks. */
	private static final int NETWORK_CLASS_2_G = 1;
	/** Class of broadly defined "3G" networks. */
	private static final int NETWORK_CLASS_3_G = 2;
	/** Class of broadly defined "4G" networks. */
	private static final int NETWORK_CLASS_4_G = 3;

	private static DecimalFormat df = new DecimalFormat("#.##");

	// 适配低版本手机
	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	public static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B */
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0 */
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A */
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT */
	public static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B */
	public static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;

	public static final int GB = 1024*1024*1024;

	public static final int MB = 1024*1024*1024;

	public static final int KB = 1024;

	public static final int B = 8;
	private static NetManager mInstance;
	private Context context;

	private NetManager(Context context) {
		this.context = context;
	}

	public static NetManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new NetManager(context.getApplicationContext());
		}
		return mInstance;
	}

	/**
	 * 获取网络类型
	 *
	 * @return
	 */
	public  String getCurrentNetworkType() {
		int networkClass = getNetworkClass();
		String type = "未知";
		switch (networkClass) {
			case NETWORK_CLASS_UNAVAILABLE:
				type = "无";
				break;
			case NETWORK_CLASS_WIFI:
				type = "Wi-Fi";
				break;
			case NETWORK_CLASS_2_G:
				type = "2G";
				break;
			case NETWORK_CLASS_3_G:
				type = "3G";
				break;
			case NETWORK_CLASS_4_G:
				type = "4G";
				break;
			case NETWORK_CLASS_UNKNOWN:
				type = "未知";
				break;
		}
		return type;
	}

	/**
	 * 检查sim卡状态
	 *
	 * @param
	 * @return
	 */
	public  boolean checkSimState() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT
				|| tm.getSimState() == TelephonyManager.SIM_STATE_UNKNOWN) {
			return false;
		}

		return true;
	}
	/**
	 * 判断网络是否连接
	 * @return
	 */
	public  boolean isConnected()
	{

		ConnectivityManager connectivity = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity)
		{

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected())
			{
				if (info.getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}

	private  int getNetworkClass() {
		int networkType = NETWORK_TYPE_UNKNOWN;
		try {
			final NetworkInfo network = ((ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE))
					.getActiveNetworkInfo();
			if (network != null && network.isAvailable()
					&& network.isConnected()) {
				int type = network.getType();
				if (type == ConnectivityManager.TYPE_WIFI) {
					networkType = NETWORK_TYPE_WIFI;
				} else if (type == ConnectivityManager.TYPE_MOBILE) {
					TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
									Context.TELEPHONY_SERVICE);
					networkType = telephonyManager.getNetworkType();
				}
			} else {
				networkType = NETWORK_TYPE_UNAVAILABLE;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return getNetworkClassByType(networkType);

	}

	private  int getNetworkClassByType(int networkType) {
		switch (networkType) {
			case NETWORK_TYPE_UNAVAILABLE:
				return NETWORK_CLASS_UNAVAILABLE;
			case NETWORK_TYPE_WIFI:
				return NETWORK_CLASS_WIFI;
			case NETWORK_TYPE_GPRS:
			case NETWORK_TYPE_EDGE:
			case NETWORK_TYPE_CDMA:
			case NETWORK_TYPE_1xRTT:
			case NETWORK_TYPE_IDEN:
				return NETWORK_CLASS_2_G;
			case NETWORK_TYPE_UMTS:
			case NETWORK_TYPE_EVDO_0:
			case NETWORK_TYPE_EVDO_A:
			case NETWORK_TYPE_HSDPA:
			case NETWORK_TYPE_HSUPA:
			case NETWORK_TYPE_HSPA:
			case NETWORK_TYPE_EVDO_B:
			case NETWORK_TYPE_EHRPD:
			case NETWORK_TYPE_HSPAP:
				return NETWORK_CLASS_3_G;
			case NETWORK_TYPE_LTE:
				return NETWORK_CLASS_4_G;
			default:
				return NETWORK_CLASS_UNKNOWN;
		}
	}

	/**
	 * 判断是否是wifi连接
	 */
	public  boolean isWifi()
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (isConnected()){
			return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
		}
		return false;
	}

	/**
	 * 打开网络设置界面
	 */
	public  void openSetting(Activity activity)
	{
		Intent intent;
		if (android.os.Build.VERSION.SDK_INT > 10) {
			intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		} else {
			intent = new Intent();
			ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
			intent.setComponent(component);
			intent.setAction("android.intent.action.VIEW");
		}
		activity.startActivity(intent);
	}

	/**
	 * 格式化大小
	 *
	 * @param size
	 * @return
	 */
	public  String formatSize(long size) {
		String unit = "B";
		float len = size;
		if (len > 900) {
			len /= 1024f;
			unit = "KB";
		}
		if (len > 900) {
			len /= 1024f;
			unit = "MB";
		}
		if (len > 900) {
			len /= 1024f;
			unit = "GB";
		}
		if (len > 900) {
			len /= 1024f;
			unit = "TB";
		}
		return df.format(len) + unit;
	}

	public  String byte2GB(long size, String pattern){
		StringBuffer memoryInfo = new StringBuffer();
		DecimalFormat df = new DecimalFormat(pattern);
		if (size/GB>=1){
			memoryInfo.append(df.format(size/(float)GB)+"GB");
		}
		else if (size/MB>=1){
			memoryInfo.append(df.format(size/(float)MB)+"MB");
		}
		else if (size/KB>=1){
			memoryInfo.append(df.format(size/(float)KB)+"KB");
		}
		else if (size/B>=1){
			memoryInfo.append(df.format(size/(float)B)+"B");
		}
		else {
			memoryInfo.append(size+"byte");
		}
		return memoryInfo.toString();
	}

	public  String formatSizeBySecond(long size) {
		String unit = "B";
		float len = size;
		if (len > 900) {
			len /= 1024f;
			unit = "KB";
		}
		if (len > 900) {
			len /= 1024f;
			unit = "MB";
		}
		if (len > 900) {
			len /= 1024f;
			unit = "GB";
		}
		if (len > 900) {
			len /= 1024f;
			unit = "TB";
		}
		return df.format(len) + unit + "/s";
	}

	/**
	 * 获取运营商
	 *
	 * @return
	 */
	public  String getProvider() {
		String provider = "未知";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String IMSI = telephonyManager.getSubscriberId();
			Logger.i("getProvider.IMSI:" + IMSI);
			if (IMSI == null) {
				if (TelephonyManager.SIM_STATE_READY == telephonyManager
						.getSimState()) {
					String operator = telephonyManager.getSimOperator();
					Logger.i("getProvider.operator:" + operator);
					if (operator != null) {
						if (operator.equals("46000")
								|| operator.equals("46002")
								|| operator.equals("46007")) {
							provider = "中国移动";
						} else if (operator.equals("46001")) {
							provider = "中国联通";
						} else if (operator.equals("46003")) {
							provider = "中国电信";
						}
					}
				}
			} else {
				if (IMSI.startsWith("46000") || IMSI.startsWith("46002")
						|| IMSI.startsWith("46007")) {
					provider = "中国移动";
				} else if (IMSI.startsWith("46001")) {
					provider = "中国联通";
				} else if (IMSI.startsWith("46003")) {
					provider = "中国电信";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provider;
	}



}
