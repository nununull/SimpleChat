package com.ht.cn.simplechat.utils;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public class GsonUtils extends AsyncTask<String, Void, String> {

    public String result;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OverConnect {
        public void thenDo();
    }

    OverConnect overConnect;

    public void afterConnect(OverConnect o) {
        overConnect = o;
    }

    @Override
    protected void onPostExecute(String s) {
        result = s;
        if (null != overConnect) {
            overConnect.thenDo();
        }
    }
}
