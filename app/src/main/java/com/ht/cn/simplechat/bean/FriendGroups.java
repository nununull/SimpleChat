package com.ht.cn.simplechat.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */
public class FriendGroups {

    public int code;
    public String message;
    public ArrayList<DataBean> data;

    public class DataBean {
        public String groupid;
    }
}
