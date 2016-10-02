package com.ht.cn.simplechat.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.bean.Friend;
import com.ht.cn.simplechat.bean.FriendGroups;

import java.util.ArrayList;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */
public class MyExpandableAdapter implements ExpandableListAdapter {

    private ArrayList<FriendGroups.DataBean> groupList;
    private ArrayList<ArrayList<Friend.DataBean>> friendList;
    private Context mContext;
    private static String TAG = MyExpandableAdapter.class.getSimpleName();

    public MyExpandableAdapter(Context context, ArrayList<FriendGroups.DataBean> groupList, ArrayList<ArrayList<Friend.DataBean>> friendList) {
        this.groupList = new ArrayList<>();
        this.friendList = new ArrayList<>();
        this.groupList = groupList;
        this.friendList = friendList;
        mContext = context;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return friendList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return friendList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        GroupViewHolder groupViewHolder;
        if (null == convertView) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_expandable_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.groupName = (TextView) view.findViewById(R.id.groupName);
            groupViewHolder.groupImage = (ImageView) view.findViewById(R.id.groupImage);
            view.setTag(groupViewHolder);
        } else {
            view = convertView;
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        if(isExpanded){
            groupViewHolder.groupImage.setImageResource(R.drawable.expandable_list_view_down);
        }else{
            groupViewHolder.groupImage.setImageResource(R.drawable.expandable_list_view_right);
        }
        groupViewHolder.groupName.setText(groupList.get(groupPosition).groupid);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        ChildrenViewHolder childrenViewHolder;
        if (null == convertView) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_expandable_friend, parent, false);
            childrenViewHolder = new ChildrenViewHolder();
            childrenViewHolder.friendName = (TextView) view.findViewById(R.id.friendName);
            view.setTag(childrenViewHolder);
        } else {
            view = convertView;
            childrenViewHolder = (ChildrenViewHolder) view.getTag();
        }

        childrenViewHolder.friendName.setText(friendList.get(groupPosition).get(childPosition).friendid);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class GroupViewHolder {
        public TextView groupName;
        public ImageView groupImage;
    }

    class ChildrenViewHolder {
        public TextView friendName;
    }
}
