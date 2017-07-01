package com.as.commontool.base.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by FJQ on 2017/6/30.
 */

public abstract class CommonPageAdapter<T> extends PagerAdapter {
    protected List<T> mData;
    private SparseArray<View> mViews;
    protected Context mContext;

    public CommonPageAdapter(List<T> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mViews = new SparseArray<View>(mData.size());
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = newView(position);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public abstract View newView(int position);
}
