package com.as.commontool.util.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by FJQ on 2016/12/16.
 */

public class AnimationManager {
    private static AnimationManager mInstance;
    private Context context;

    private AnimationManager(Context context) {
        this.context = context;
    }

    public static AnimationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AnimationManager(context.getApplicationContext());
        }
        return mInstance;
    }
    /**
     * 启动视图动画 纯粹的动画效果 没有附加的业务需求
     * @param view
     * @param context
     * @param animResId
     */
    public  void startViewAnimation(View view, int animResId){
        Animation animation = AnimationUtils.loadAnimation(context, animResId);
        view.startAnimation(animation);
    }

    /**
     * 启动属性动画 纯粹的动画效果 没有附加的业务需求
     * @param view
     * @param context
     * @param animtorResId
     */
    public  void startPropertyAnimation(View view, int animtorResId){
        // 加载动画
        Animator anim = AnimatorInflater.loadAnimator(context, animtorResId);
        anim.setTarget(view);
        anim.start();
    }
}
