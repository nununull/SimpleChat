package com.ht.cn.simplechat.base;

import android.content.Context;
import android.view.View;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */

public abstract class BasePager {
    public Context mContext;
    public View rootView;

    public BasePager(Context context) {
        mContext = context;
        rootView = initView();
    }

    public abstract View initView();

    public abstract void initData();
}
