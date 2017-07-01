package com.as.commontool.util.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跟App相关的辅助类
 * 
 * @author zhy
 * 
 */
public class AppManager
{
	private static final String TAG="AppManager";
	private static AppManager mInstance;
	private Context context;

	private AppManager(Context context) {
		this.context = context;
	}

	public static AppManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AppManager(context.getApplicationContext());
		}
		return mInstance;
	}
	//获取应用版本号
	public int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}
	/**
	 * 获取正在运行的应用程序
	 *
	 * @param context
	 */
	public  List<Map<String, Object>> getRunningProcess() {
		PackageManager pm = context.getApplicationContext().getPackageManager();
		List<ApplicationInfo> applications = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取正在运行的应用
		List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>(runningAppProcesses.size());
		Map<String, Object> nameIconMap;
		for (ActivityManager.RunningAppProcessInfo ra : runningAppProcesses) {
			String processName = ra.processName;
			for (ApplicationInfo applicationInfo : applications) {
				if (processName.equals(applicationInfo.processName)) {
					String appName = applicationInfo.loadLabel(context.getPackageManager()).toString();
					String packageName = applicationInfo.packageName;
					Drawable appIcon = applicationInfo.loadIcon(context.getPackageManager()).getCurrent();
					nameIconMap = new HashMap<>();
					nameIconMap.put(appName,appIcon);
					listMap.add(nameIconMap);
					Logger.i("appName = " + appName);
					Logger.i("packageName = " + packageName);
				}
			}
		}

		return listMap;
	}



	/**
	 * 获取应用程序名称
	 */
	public  String getAppName()
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public  String getVersionName()
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本号信息]
	 *
	 * @param context
	 * @return 当前应用的版本号
	 */
	public  int getVersionCode()
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionCode;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 获取用户手机上已经安装的非系统自带APP列表
	 */
	public  List<Map<String, Object>> getInstalledApps() {
		List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>(packages.size());
		Map<String, Object> nameIconMap;
		for (int j = 0; j < packages.size(); j++) {

			PackageInfo packageInfo = packages.get(j);
			// 显示非系统软件
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

				String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
				String packageName = packageInfo.packageName;
				Drawable appIcon = packageInfo.applicationInfo.loadIcon(context.getPackageManager()).getCurrent();
				nameIconMap = new HashMap<>();
				nameIconMap.put(appName,appIcon);
				listMap.add(nameIconMap);
				Logger.i("appName = " + appName+"packageName = " + packageName);
			}
		}
		return listMap;
	}


}
