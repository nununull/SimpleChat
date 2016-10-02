package com.ht.cn.simplechat.viewpager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.adapter.MyExpandableAdapter;
import com.ht.cn.simplechat.base.BasePager;
import com.ht.cn.simplechat.bean.Friend;
import com.ht.cn.simplechat.bean.FriendGroups;
import com.ht.cn.simplechat.utils.GetJsonUtils;
import com.ht.cn.simplechat.utils.GlobalUtils;
import com.ht.cn.simplechat.utils.SharedPreUtils;
import com.ht.cn.simplechat.utils.ToastUtils;

import java.util.ArrayList;

import io.rong.imkit.RongIM;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */

public class FriendshipViewPager extends BasePager {

    public FriendshipViewPager(Context context) {
        super(context);
    }

    private ExpandableListView expandableistView;
    private static String TAG = FriendshipViewPager.class.getSimpleName();
    ArrayList<FriendGroups.DataBean> groupList = new ArrayList<>();

    ArrayList<ArrayList<Friend.DataBean>> friendList = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.pager_friendship,null);
        expandableistView = (ExpandableListView) view.findViewById(R.id.expandableistView);

        return view;
    }

    @Override
    public void initData() {
        getData();
        //回调方法，监听异步请求是否完成
        this.over(new FriendshipViewPager.Over() {
            @Override
            public void thenDo() {//完成异步请求
                MyExpandableAdapter myExpandableAdapter = new MyExpandableAdapter(mContext, groupList, friendList);
                expandableistView.setAdapter(myExpandableAdapter);
                initDataAfter();
            }
        });

    }
    //初始化数据完成后
    private void initDataAfter() {
        expandableistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(mContext,friendList.get(groupPosition).get(childPosition).friendid, null);
                }
                return true;
            }
        });
    }


    int i = 0;

    /**
     * 从服务器获取列表信息及好友列表
     */
    private void getData() {
        final GetJsonUtils getJsonUtils = new GetJsonUtils();
        final String userId = SharedPreUtils.getString(mContext, "userId", null);
        getJsonUtils.execute(GlobalUtils.QUERY_FRIEND_GROUP + "userid=" + userId);
        getJsonUtils.over(new GetJsonUtils.Over() {
            @Override
            public void toDo() {
                String json_groups = getJsonUtils.json;
                if (null == json_groups) {
                    ToastUtils.setMessage(mContext, "网络链接失败!");
                } else {
                    final Gson gson = new Gson();
                    FriendGroups friendGroups = gson.fromJson(json_groups, FriendGroups.class);
                    groupList = friendGroups.data;
                    for (i = 0; i < groupList.size(); i++) {
                        final GetJsonUtils getJsonUtils1 = new GetJsonUtils();
                        String url = GlobalUtils.QUERY_FRIENDS + "userid=" + userId + "&groupid=" + groupList.get(i).groupid;
                        getJsonUtils1.execute(url);
                        getJsonUtils1.over(new GetJsonUtils.Over() {
                            @Override
                            public void toDo() {
                                Friend friend = gson.fromJson(getJsonUtils1.json, Friend.class);
                                if(null != friend.data){
                                    friendList.add(friend.data);
                                }
                                if (i >= groupList.size() - 1) {
                                    if (null != o) {
                                        o.thenDo();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    //该接口用来实现监听从服务器获取数据完成的状态
    interface Over {
        void thenDo();
    }

    FriendshipViewPager.Over o;

    private void over(FriendshipViewPager.Over o) {
        this.o = o;
    }
}
