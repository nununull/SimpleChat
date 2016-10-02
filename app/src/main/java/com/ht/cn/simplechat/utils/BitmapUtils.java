package com.ht.cn.simplechat.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ht.cn.simplechat.utils.bitmapUtils.BitmapFileCacheUtils;
import com.ht.cn.simplechat.utils.bitmapUtils.BitmapMemoryCacheUtils;
import com.ht.cn.simplechat.utils.bitmapUtils.BitmapNetCacheUtils;
import com.ht.cn.simplechat.views.CircleImage;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class BitmapUtils {
    private BitmapMemoryCacheUtils memoryCacheUtils;
    private BitmapFileCacheUtils fileCacheUtils;
    private BitmapNetCacheUtils netCacheUtils;
    public static BitmapUtils bitmapUtils;
    public Bitmap bitmap;
    private BitmapUtils() {
        memoryCacheUtils = new BitmapMemoryCacheUtils();
        fileCacheUtils = new BitmapFileCacheUtils();
        netCacheUtils = new BitmapNetCacheUtils(memoryCacheUtils,fileCacheUtils);
    }

    public static BitmapUtils getInstance() {
        if (null == bitmapUtils) {
            return bitmapUtils = new BitmapUtils();
        } else {
            return bitmapUtils;
        }
    }
//
//    //获取bitmap对象
//    public Bitmap getBitmap(String url) {
//        //从内存获取图片
//        bitmap = memoryCacheUtils.getCache(url);
//        if(bitmap != null){
//            return bitmap;
//        }
//        //从本地磁盘获取图片
//        bitmap = fileCacheUtils.getCache(url);
//        if(bitmap !=null){
//            return bitmap;
//        }
//        //从网络下载图片
//        netCacheUtils.getCache(null,url);
//        netCacheUtils.afterDown(new BitmapNetCacheUtils.OverDownload() {
//            @Override
//            public void thenDo() {
//                bitmap = netCacheUtils.resultBitmap;
//            }
//        });
//        return bitmap;
//    }

    //传入bitmap对象显示图片
    public void displayImageView(CircleImage circleImage,ImageView imageView, String url) {
        //从内存获取图片
        bitmap = memoryCacheUtils.getCache(url);
        if(bitmap != null){
            if(null != imageView){
                imageView.setImageBitmap(bitmap);
            }
            if(null != circleImage){
                circleImage.setBitmap(bitmap);
            }
            return;
        }
        //从本地磁盘获取图片
        bitmap = fileCacheUtils.getCache(url);
        if(bitmap != null){
            if(null != imageView){
                imageView.setImageBitmap(bitmap);
            }
            if(null != circleImage){
                circleImage.setBitmap(bitmap);
            }
            return;
        }
        //从网络下载图片
        netCacheUtils.getCache(circleImage,imageView, url);
        netCacheUtils.afterDown(new BitmapNetCacheUtils.OverDownload() {
            @Override
            public void thenDo() {
                bitmap = netCacheUtils.resultBitmap;
                if(over != null){
                    over.thenDo();
                }
            }
        });
    }
    public interface Over{
        void thenDo();
    }
    Over over;
    public void DownOver(Over o){
        over = o;
    }
}
