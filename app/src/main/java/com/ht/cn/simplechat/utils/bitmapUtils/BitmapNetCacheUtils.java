package com.ht.cn.simplechat.utils.bitmapUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.ht.cn.simplechat.views.CircleImage;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class BitmapNetCacheUtils {
    private ImageView imageView;
    private String url;
    public Bitmap resultBitmap;
    private BitmapFileCacheUtils fileCacheUtils;
    private BitmapMemoryCacheUtils memoryCacheUtils;
    private CircleImage circleImage;

    public BitmapNetCacheUtils(BitmapMemoryCacheUtils memoryCacheUtils, BitmapFileCacheUtils fileCacheUtils) {
        this.fileCacheUtils = fileCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }

    public void getCache(CircleImage circleImage, ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
        this.circleImage = circleImage;
        if (imageView != null) {
            this.imageView.setTag(this.url);
        }
        if(circleImage != null){
            this.circleImage.setTag(this.url);
        }
        new DownImage().execute();
    }

    //异步任务下载图片
    class DownImage extends AsyncTask<Object, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Object... objects) {
            return down();
        }

        private Bitmap down() {
            Bitmap bitmap = null;
            InputStream is = null;
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).get().build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    is = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (null != bitmap) {
                if (imageView != null) {
                    if(url.equals(imageView.getTag().toString())){
                        imageView.setImageBitmap(bitmap);
                    }
                }
                if(circleImage != null){
                    if(url.equals(circleImage.getTag().toString())){
                        circleImage.setBitmap(bitmap);
                    }
                }
                resultBitmap = bitmap;
                fileCacheUtils.setCache(bitmap, url);
                memoryCacheUtils.setCache(url, bitmap);
                if (overDownload != null) {
                    overDownload.thenDo();
                }
            }
        }
    }

    public interface OverDownload {
        void thenDo();
    }

    OverDownload overDownload;

    public void afterDown(OverDownload o) {
        overDownload = o;
    }
}
