package com.as.commontool.util.device;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SD卡相关的辅助类
 * 
 * @author zhy
 * 
 */
public class SDCardManager
{
	private static SDCardManager mInstance;
	private Context context;

	private SDCardManager(Context context) {
		this.context = context;
	}

	public static SDCardManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SDCardManager(context.getApplicationContext());
		}
		return mInstance;
	}
	//获取缓存地址
	public File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * 判断SDCard是否可用
	 * 
	 * @return
	 */
	public  boolean isSDCardEnable()
	{
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}

	private String getExtraUsbPath(){
		String[] result = null;
		StorageManager storageManager = (StorageManager)context.getSystemService(Context.STORAGE_SERVICE);
		try {
			Method method = StorageManager.class.getMethod("getVolumePaths");
			method.setAccessible(true);
			try {
				result =(String[])method.invoke(storageManager);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
//            if (result!=null&&result.length>0){
//                File file = new File(BASE_USB_PATH+File.separator+"4K");
//                Log.i(TAG,"U盘的盘符路径"+result[result.length-1]+"文件是否存在:"+file.exists());
//                Toast.makeText(mContext,"U盘的盘符路径"+result[result.length-1]+"文件是否存在:"+file.exists(),Toast.LENGTH_SHORT).show();
//            }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result[result.length-1];
	}

	/**
	 * 获取SD卡路径
	 *
	 * @return
	 */
	public String getSDCardPath() {
		File dir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals("mounted");
		if(sdCardExist) {
			dir = Environment.getExternalStorageDirectory();
		} else {
			dir = Environment.getDataDirectory();
		}

		return dir.toString();
	}

	public  String getDataDirectory(){
		return Environment.getDataDirectory().getAbsolutePath()
				+ File.separator;
	}

	public  long getPhoneDataMemory(){
		StatFs stat = new StatFs(getDataDirectory());
		// 获取空闲的数据块的数量
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		// 获取单个数据块的大小（byte）
		long freeBlocks = stat.getAvailableBlocks();
		return freeBlocks * availableBlocks;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 * 
	 * @return
	 */
	public long getSDCardAllSize()
	{
		if (isSDCardEnable())
		{
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 * 
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public  long getFreeBytes(String filePath)
	{
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath()))
		{
			filePath = getSDCardPath();
		} else
		{// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public  String getRootDirectoryPath()
	{
		return Environment.getRootDirectory().getAbsolutePath();
	}


}
