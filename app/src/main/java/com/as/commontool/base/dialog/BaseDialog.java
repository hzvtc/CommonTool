package com.as.commontool.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by loyal_luo on 2016/9/21.
 */
public abstract class BaseDialog extends Dialog {
    protected Context mContext;
    protected View rootView;

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        rootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        super.setContentView(rootView);
    }

    public abstract int getLayoutId();

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    public void show() {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = 0.4f;
        ((Activity)mContext).getWindow().setAttributes(lp);//改变背景颜色
        super.show();
    }

    @Override
    public void dismiss() {

        super.dismiss();

        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = 1.0f;
        ((Activity)mContext).getWindow().setAttributes(lp);//改变背景颜色


    }

    @Override
    public void hide() {
        super.hide();
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = 1.0f;
        ((Activity)mContext).getWindow().setAttributes(lp);//改变背景颜色
    }
}
