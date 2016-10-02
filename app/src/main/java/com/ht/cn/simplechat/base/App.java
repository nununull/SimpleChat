package com.ht.cn.simplechat.base;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        RongIM.init(this);
    }
}
