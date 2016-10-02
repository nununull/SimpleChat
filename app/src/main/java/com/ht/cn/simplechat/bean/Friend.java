package com.ht.cn.simplechat.bean;

import java.util.ArrayList;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */
public class Friend {

    public int code;
    public String message;
    public ArrayList<DataBean> data;

    public class DataBean {
        public String friendid;
    }
}
