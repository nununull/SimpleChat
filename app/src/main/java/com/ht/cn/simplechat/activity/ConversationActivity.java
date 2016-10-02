package com.ht.cn.simplechat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.base.BaseActivity;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class ConversationActivity extends BaseActivity{
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initWindow();
        findViews();
        afterFindViews();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
    private void afterFindViews(){
        toolbar.setTitle("聊天");
        //继承的是ActionBarActivity，直接调用 自带的 Actionbar，下面是Actionbar 的配置，如果不用可忽略...
        setSupportActionBar(toolbar);
        // getSupportActionBar().setLogo(R.drawable.de_bar_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.de_actionbar_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
