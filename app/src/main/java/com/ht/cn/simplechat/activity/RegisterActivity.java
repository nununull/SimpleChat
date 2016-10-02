package com.ht.cn.simplechat.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.base.BaseActivity;
import com.ht.cn.simplechat.net.AddGroupsUtils;
import com.ht.cn.simplechat.utils.GlobalUtils;
import com.ht.cn.simplechat.utils.SnackBarUtils;
import com.ht.cn.simplechat.utils.StatusUtils;
import com.ht.cn.simplechat.views.FlowButton;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class RegisterActivity extends BaseActivity {
    private Toolbar toolbar;
    private FlowButton btn_register;//注册按钮
    private TextInputEditText register_userId;//用户ID
    private TextInputEditText register_password;//用户密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initWindow();
        findViews();
        afterFindViews();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_register = (FlowButton) findViewById(R.id.btn_register);
        register_userId = (TextInputEditText) findViewById(R.id.register_userid);
        register_password = (TextInputEditText) findViewById(R.id.register_password);
    }

    private void afterFindViews() {
        toolbar.setTitle("注册");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//点击开始注册
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startRegister(view);
            }
        });
    }

    private void startRegister(final View view) {
        btn_register.setBegainFlow();
        final String userid = register_userId.getText().toString();
        final String password = register_password.getText().toString();
        if (userid.isEmpty() || password.isEmpty()) {
            SnackBarUtils.setMessage(view, "信息填写不完整！");
            return;
        }
        String url = GlobalUtils.REGISTER_URL + "?userid=" + userid + "&password=" + password;
        final StatusUtils statusUtils = new StatusUtils(this);
        statusUtils.execute(url);
        statusUtils.afterConnect(new StatusUtils.OverConnect() {
            @Override
            public void thenDo() {
                btn_register.setEndFlow();
                int code = statusUtils.result;
                switch (code) {
                    case 0:
                        SnackBarUtils.setMessage(view, "连接错误，请稍候再试");
                        break;
                    case 200:
                        SnackBarUtils.setMessage(view, "注册成功");
                        //注册成功则向该用户的好友列表添加默认好友列表
                        new AddGroupsUtils().addGroups(RegisterActivity.this, userid, "我的好友");
                        //注册成功跳转到登录界面
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("userid", userid);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        finish();
                        break;
                    case 1003:
                        SnackBarUtils.setMessage(view, "该id已经被注册啦！");
                        break;
                }

            }
        });
    }
}
