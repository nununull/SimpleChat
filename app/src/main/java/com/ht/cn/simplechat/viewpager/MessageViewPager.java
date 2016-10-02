package com.ht.cn.simplechat.viewpager;

import android.content.Context;
import android.view.View;

import com.ht.cn.simplechat.R;
import com.ht.cn.simplechat.base.BasePager;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */

public class MessageViewPager extends BasePager {

    public MessageViewPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.pager_message,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
