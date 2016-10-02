package com.ht.cn.simplechat.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ht.cn.simplechat.bean.StatusBean;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class StatusUtils extends AsyncTask<String, Void, String> {

    public int result = 0;
    private Gson gson;
    private Context mContext;

    public StatusUtils(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.setMessage(mContext,"网络不连通，请稍后再试！");
        }
        return null;
    }

    public interface OverConnect {
        void thenDo();
    }

    OverConnect overConnect;

    public void afterConnect(OverConnect o) {
        overConnect = o;
    }

    @Override
    protected void onPostExecute(String s) {
        if(null == s){
            ToastUtils.setMessage(mContext,"网络连接失败，请稍后再试！");
            result = 0;
        }
        if(null != s){
           if(null == gson){
               gson = new Gson();
           }
            StatusBean statusBean = gson.fromJson(s, StatusBean.class);
            result = statusBean.code;
        }
        if (null != overConnect) {
            overConnect.thenDo();
        }
    }
}
