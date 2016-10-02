package com.ht.cn.simplechat.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class SnackBarUtils {
    private static Snackbar snackbar;
    private SnackBarUtils(){

    }
    public static void setMessage(View view ,String message){
        if(snackbar == null){
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        }else{
            snackbar.setText(message);
        }
        snackbar.show();
    }
}
