package com.as.commontool.base.adapter;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by FJQ on 2017/6/30.
 */

public class BaseButterKnifeHolder {
    public BaseButterKnifeHolder(View view) {
        ButterKnife.inject(this, view);
    }
}
