package com.as.commontool.util.view;

import android.content.Context;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 * 
 * @author zhy
 * 
 */
public class DensityManager
{
	private static DensityManager mInstance;
	private Context context;

	private DensityManager(Context context) {
		this.context = context;
	}

	public static DensityManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DensityManager(context.getApplicationContext());
		}
		return mInstance;
	}

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public  int dp2px(float dpVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * sp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public  int sp2px(float spVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public  float px2dp(float pxVal)
	{
		final float scale = getDensity(context);
		return (pxVal / scale);
	}



	/**
	 * px转sp
	 * 
	 * @param fontScale
	 * @param pxVal
	 * @return
	 */
	public  float px2sp(float pxVal)
	{
		return (pxVal / getScaledDensity(context));
	}

	public  float getScaledDensity(Context context)
	{
		return context.getResources().getDisplayMetrics().scaledDensity;
	}

	public  float getDensity(Context context)
	{
		return context.getResources().getDisplayMetrics().density;
	}

}
