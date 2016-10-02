package com.ht.cn.simplechat.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class SharedPreUtils {
    public static final String FILE_NAME = "config";
    public static void setString(Context context,String name,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(name,value);
        editor.commit();
    }
    public static String getString(Context context,String name,String def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(name,def);
    }

    public static void setBoolean(Context context,String name,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putBoolean(name,value);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String name,boolean def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name,def);
    }
}
