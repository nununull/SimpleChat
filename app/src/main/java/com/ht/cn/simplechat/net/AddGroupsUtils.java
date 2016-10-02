package com.ht.cn.simplechat.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.ht.cn.simplechat.bean.StatusBean;
import com.ht.cn.simplechat.utils.GlobalUtils;
import com.ht.cn.simplechat.utils.SnackBarUtils;
import com.ht.cn.simplechat.utils.ToastUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.coms
 */
public class AddGroupsUtils {
    private static String TAG = AddGroupsUtils.class.getSimpleName();
    private String url;
    private Context mContext;

    public void addGroups(Context context, String userId, String groupId) {
        url = GlobalUtils.ADD_GROUPS + "userid=" + userId + "&groupsid=" + groupId;
        mContext = context;
        new AddGroups().execute(url);
    }

    //异步请求，添加好友分组
    public class AddGroups extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String result = null;
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {//请求成功
                    Log.i(TAG, "connect success,insert is ok");
                    result = response.body().string();
                } else {//请求失败
                    //请求失败则递归请求，知道创建成功
                    Log.e(TAG, "insert default groupID false,can not connect to the service,retrying...");
                    doInBackground(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String json = s;
            Gson gson = new Gson();
            StatusBean statusBean = gson.fromJson(json, StatusBean.class);
            int code = statusBean.code;
            switch (code) {
                case 200://成功

                    break;
                case 1004://分组已经存在
                    ToastUtils.setMessage(mContext, "该分组已经存在");
                    break;
                case 1001://数据库链接错误
                    Log.e(TAG, "connect to the database error");
                    break;
                case 2000://输入参数错误
                    ToastUtils.setMessage(mContext, "您输入的分组名不合法");
                    break;
            }
        }
    }
}
