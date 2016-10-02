package com.ht.cn.simplechat.utils.bitmapUtils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class BitmapMemoryCacheUtils {

    private Bitmap bitmap;
    private LruCache<String, Bitmap> mMemoryCache;

    public BitmapMemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;//获取最大内存的8分之一
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getRowBytes() * value.getHeight();// 获取图片占用内存大小
                return byteCount;
            }
        };
    }

    /**
     * 往内存中写
     *
     * @param url
     * @return
     */
    public Bitmap getCache(String url) {
        return mMemoryCache.get(url);
    }

    /**
     * 从内存中读
     *
     * @param url
     * @param bitmap
     */
    public void setCache(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
}
