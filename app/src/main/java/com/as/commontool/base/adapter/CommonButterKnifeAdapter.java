package com.as.commontool.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by FJQ on 2017/6/30.
 */

public abstract class CommonButterKnifeAdapter<T, E extends BaseButterKnifeHolder> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;

    public CommonButterKnifeAdapter(Context context, List<T> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        E holder = null;
        if (convertView == null) {
            //getView()的抽象方法，返回值为int
            convertView = LayoutInflater.from(mContext).inflate(
                    getView(), null);
            //通过泛型自动转换holder类型，由于butterknife需要在holder里面绑定控件，所以不能写成同一个holder，而需要自动转换。
            holder = getHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (E) convertView.getTag();
        }
        //逻辑处理的抽象方法
        bindEvent(holder,position);
        return convertView;
    }

    //三个抽象方法
    protected abstract E getHolder(View v);

    protected abstract int getView();

    protected abstract void bindEvent(E holder,int position);

}
