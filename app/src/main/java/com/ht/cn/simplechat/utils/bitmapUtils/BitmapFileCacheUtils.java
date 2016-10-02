package com.ht.cn.simplechat.utils.bitmapUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ht.cn.simplechat.utils.GlobalUtils;
import com.ht.cn.simplechat.utils.MD5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class BitmapFileCacheUtils {

    public Bitmap getCache(String url) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            String fileName = MD5Utils.encode(url);
            File file = new File(GlobalUtils.FILE_DIR, fileName);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public void setCache(Bitmap bitmap, String url) {
        FileOutputStream fos = null;
        try {
            String fileName = MD5Utils.encode(url);
            File file = new File(GlobalUtils.FILE_DIR, fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
