package com.ht.cn.simplechat.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */
public class ToastUtils {

    private ToastUtils() {

    }

    private static Toast toast;

    public static void setMessage(Context context, String content) {
        if (null == toast) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
