package com.ht.cn.simplechat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.base.BaseActivity;
import com.ht.cn.simplechat.bean.TokenBean;
import com.ht.cn.simplechat.fragment.SlidingMenuFragment;
import com.ht.cn.simplechat.base.BasePager;
import com.ht.cn.simplechat.utils.GetJsonUtils;
import com.ht.cn.simplechat.utils.GlobalUtils;
import com.ht.cn.simplechat.utils.SharedPreUtils;
import com.ht.cn.simplechat.viewpager.FriendshipViewPager;
import com.ht.cn.simplechat.viewpager.MessageViewPager;
import com.ht.cn.simplechat.viewpager.MomentViewPager;
import com.ht.cn.simplechat.views.NoScrollViewPager;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class HomepageActivity extends BaseActivity implements View.OnClickListener {
    public Toolbar toolbar;
    public TextView toolbarTitle;//toolbar中间的标题
    private RadioGroup radioGroup;
    private NoScrollViewPager viewPager;
    private static final String TAG = HomepageActivity.class.getSimpleName();
    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initWindow();//透明状态栏
        setContentView(R.layout.activity_homepage);
        getToken();//获取token
        findViews();
        initData();
        AfterInitView();
    }

    private void getToken() {
        String token = SharedPreUtils.getString(this,"token",null);
        if(null != token){//如果Token存在，则不从网络上获取
            afterGetToken(token);
        }else{//不存在，从服务器上获取
            final GetJsonUtils jsonUtils = new GetJsonUtils();
            String url = GlobalUtils.GET_Token+ SharedPreUtils.getString(this,"userId",null);
            jsonUtils.execute(url);
            jsonUtils.over(new GetJsonUtils.Over() {
                @Override
                public void toDo() {
                    Gson gson = new Gson();
                    TokenBean tokenBean = gson.fromJson(jsonUtils.json, TokenBean.class);
                    if(null != tokenBean.token){
                        SharedPreUtils.setString(HomepageActivity.this,"token",tokenBean.token);
                        afterGetToken(tokenBean.token);
                    }else{
                        getToken();
                    }
                }
            });
        }

    }

    private void afterGetToken(String token){
        initRongIM(token);//初始化融云连接
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);

        toolbar.setTitle("");
        //toolbar.setNavigationIcon(R.mipmap.simplechat_2);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("主页");
    }

    private void AfterInitView() {

        //对viewGroup进行监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_conversation://会话
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.radio_friendship://联系人
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.radio_moment://动态
                        viewPager.setCurrentItem(2,false);
                        break;
                }
            }
        });


    }

    private void initRongIM(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                getToken();
            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "false" + errorCode.getValue());
            }
        });}

    private void initData() {

        addSlidingMenu();//初始化侧边栏

        ArrayList<BasePager> viewPagers = new ArrayList<BasePager>();
        viewPagers.add(new MessageViewPager(this));
        viewPagers.add(new FriendshipViewPager(this));
        viewPagers.add(new MomentViewPager(this));

        pagerAdapter = new MyPagerAdapter(viewPagers);

        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    private void addSlidingMenu(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.slidingMenu,new SlidingMenuFragment());
        transaction.commit();
    }

    class MyPagerAdapter extends PagerAdapter {

        private ArrayList<BasePager> pagers;

        public MyPagerAdapter(ArrayList<BasePager> pagers) {
            this.pagers = pagers;
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagers.get(position).rootView);
            pagers.get(position).initData();
            return pagers.get(position).rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pagers.get(position).rootView);
        }
    }

}
