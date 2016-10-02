package com.ht.cn.simplechat.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void noTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    public void initWindow(){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
    }
}
