package com.ht.cn.simplechat.utils;

import android.os.Environment;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class GlobalUtils {
    //注册链接
    public static String REGISTER_URL = "http://www.nununull.esy.es/app/simplechat/register.php";
    //注册成功后将给用户添加默认好友分组
    public static String ADD_GROUPS = "http://www.nununull.esy.es/app/simplechat/addgroups.php?";
    //图片本地缓存地址
    public static String FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SimpleChat";
    //查询用户信息链接
    public static String QUERY_USER_INFO = "http://www.nununull.esy.es/app/simplechat/userinfo.php?userid=";
    //查询好友列表分组链接
    public static String QUERY_FRIEND_GROUP = "http://nununull.esy.es/app/simplechat/getfriendshipinfo.php?type=1&";
    //查询好友列表（不按组划分）
    public static String QUERY_FREINDS_NO_GROUP = "http://nununull.esy.es/app/simplechat/getfriendshipinfo.php?type=2&";
    //查询好友列表（按组划分）
    public static String QUERY_FRIENDS = "http://nununull.esy.es/app/simplechat/getfriendshipinfo.php?type=2&";
    //获取Token链接
    public static String GET_Token = "http://www.nununull.esy.es/app/simplechat/gettoken.php?userid=";
}
