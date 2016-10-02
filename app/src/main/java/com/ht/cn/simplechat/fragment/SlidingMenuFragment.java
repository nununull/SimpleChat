package com.ht.cn.simplechat.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.activity.HomepageActivity;
import com.ht.cn.simplechat.activity.LoginActivity;
import com.ht.cn.simplechat.base.BaseFragment;
import com.ht.cn.simplechat.bean.UserInfo;
import com.ht.cn.simplechat.utils.BitmapUtils;
import com.ht.cn.simplechat.utils.GlobalUtils;
import com.ht.cn.simplechat.utils.SharedPreUtils;
import com.ht.cn.simplechat.utils.GsonUtils;
import com.ht.cn.simplechat.views.CircleImage;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class SlidingMenuFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {
    private GsonUtils gsonUtils;
    private Gson gson;
    private CircleImage circleImage;
    private TextView tv_userName;
    private NavigationView navigationView;
    private static final String TAG = SlidingMenuFragment.class.getSimpleName();

    @Override
    public View initView() {
        View view = LayoutInflater.from(activity).inflate(R.layout.fragment_leftsliding, null);
        circleImage = (CircleImage) view.findViewById(R.id.circleImage);
        tv_userName = (TextView) view.findViewById(R.id.tv_userName);
        navigationView = (NavigationView) view.findViewById(R.id.homepage_navigation);
        afterfindViews();
        return view;
    }

    private void afterfindViews() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        gsonUtils = new GsonUtils();
        gsonUtils.execute(GlobalUtils.QUERY_USER_INFO + SharedPreUtils.getString(activity, "userId", ""));
        gsonUtils.afterConnect(new GsonUtils.OverConnect() {
            @Override
            public void thenDo() {
                String result = gsonUtils.result;
                if (null == gson) {
                    gson = new Gson();
                }
                UserInfo userInfo = gson.fromJson(result, UserInfo.class);
                if (null != userInfo) {
                    int code = userInfo.code;
                    if (code == 200) {
                        String userName = userInfo.data.get(0).username;
                        String imgUrl = userInfo.data.get(0).image;
                        tv_userName.setText(userName);
                        if (imgUrl.isEmpty()) {//该用户没有设置自定义头像

                        } else {//该用户使用了自定义头像
                            BitmapUtils.getInstance().displayImageView(circleImage, null, imgUrl);
//                        BitmapUtils.getInstance().DownOver(new BitmapUtils.Over() {
//                            @Override
//                            public void thenDo() {
//                                circleImage.setBitmap(BitmapUtils.getInstance().bitmap);
//                            }
//                        });
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homepage://主界面
                break;
            case R.id.menu_app_about://关于界面
                break;
            case R.id.menu_feedback:
                break;
            case R.id.menu_app_setting://设置界面
                break;
            case R.id.menu_app_logout://退出帐号回到主界面
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                SharedPreUtils.setBoolean(getContext(), "logined", false);//重置自动登录
                SharedPreUtils.setString(getContext(),"token",null);//重置token
                getActivity().finish();
                break;
        }
        return true;
    }
}
