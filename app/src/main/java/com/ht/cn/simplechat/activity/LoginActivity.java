package com.ht.cn.simplechat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.base.BaseActivity;
import com.ht.cn.simplechat.utils.SharedPreUtils;
import com.ht.cn.simplechat.utils.SnackBarUtils;
import com.ht.cn.simplechat.utils.StatusUtils;
import com.ht.cn.simplechat.utils.ToastUtils;
import com.ht.cn.simplechat.views.FlowButton;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView splash_img;//注册界面背景
    private TextInputEditText tv_userId;//用户ID
    private TextInputEditText tv_password;//用户密码
    private Button btn_login;//登录按钮
    private TextView login_register;//注册
    private TextView login_cant_log;//无法登录
    private StatusUtils statusUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        if(SharedPreUtils.getBoolean(this,"logined",false)){//登录成功过
            Intent intent = new Intent(this,HomepageActivity.class);
            this.startActivity(intent);
            finish();
        }else{
            initWindow();//调用父类方法，使状态栏透明
            findViews();
            afterFindViews();
            getDataFromIntent();
        }
    }

    private void getDataFromIntent() {
        try {
            String userid = getIntent().getStringExtra("userid");
            String password = getIntent().getStringExtra("password");
            tv_userId.setText(userid);
            tv_password.setText(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afterFindViews() {
        btn_login.setOnClickListener(this);
        login_register.setOnClickListener(this);
        login_cant_log.setOnClickListener(this);
    }

    private void findViews() {
        //设置gif背景
        splash_img = (ImageView) findViewById(R.id.splash_img);
        Glide.with(LoginActivity.this).load(R.mipmap.login_bg_1).asGif().into(splash_img);
        //
        tv_userId = (TextInputEditText) findViewById(R.id.login_userID);
        tv_password = (TextInputEditText) findViewById(R.id.login_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        login_register = (TextView) findViewById(R.id.login_register);
        login_cant_log = (TextView) findViewById(R.id.login_cant_log);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录
                String userID = tv_userId.getText().toString();
                String password = tv_password.getText().toString();
                if (userID.isEmpty() || password.isEmpty()) {
                    SnackBarUtils.setMessage(view, "信息填写不完整！");
                } else {
                    login(view, userID, password);
                }
                break;
            case R.id.login_register://注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                this.startActivity(intent);
                break;
            case R.id.login_cant_log://不能登录

                break;
        }
    }

    private void login(final View view, final String userID, final String password) {
        String url = "http://nununull.esy.es/app/simplechat/login.php" + "?userid=" + userID + "&password=" + password;
        statusUtils = new StatusUtils(LoginActivity.this);
        // statusUtils.cancel(true);
        statusUtils.execute(url);
        statusUtils.afterConnect(new StatusUtils.OverConnect() {
            @Override
            public void thenDo() {
                int code = statusUtils.result;

                switch (code) {
                    case 0:
                        SnackBarUtils.setMessage(view, "连接错误，请稍候再试");
                        break;
                    case 200:
                        //SnackBarUtils.setMessage(view, "注册成功");
                        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                        intent.putExtra("userid", userID);
                        intent.putExtra("password", password);
                        SharedPreUtils.setString(LoginActivity.this,"userId",userID+"");
                        SharedPreUtils.setBoolean(LoginActivity.this,"logined",true);
                        startActivity(intent);
                        finish();
                        break;
                    case 1102:
                        SnackBarUtils.setMessage(view, "该用户不存在！");
                        break;
                    case 1103:
                        SnackBarUtils.setMessage(view, "密码错误！");
                        break;
                }
            }
        });
    }
}
