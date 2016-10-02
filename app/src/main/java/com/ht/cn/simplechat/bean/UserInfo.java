package com.ht.cn.simplechat.bean;

import java.util.List;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class UserInfo {

    public int code;
    public String message;
    public List<DataBean> data;

    public static class DataBean {
        public String username;
        public String image;
    }
}
