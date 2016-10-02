package com.ht.cn.simplechat.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */
public class GetJsonUtils extends AsyncTask<String, Void, String> {
    public String json;
    private static String TAG = GetJsonUtils.class.getSimpleName();
    @Override
    protected String doInBackground(String... params) {
        String json = null;
        String url = params[0];
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                json = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
//该接口用作回调，监听异步任务的完成
public interface Over {
        void toDo();
    }
    Over o;
    public void over(Over o){
        this.o = o;
    }
    @Override
    protected void onPostExecute(String s) {
        json = s;
        if(null != o){
            o.toDo();
        }
    }
}
