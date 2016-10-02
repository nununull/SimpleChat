package com.ht.cn.simplechat.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @auther:nununull
 * @email:baiseliunian@outlook.com
 */
public abstract class BaseFragment extends Fragment {

    public Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }
    public abstract View initView();
    public void initData(){
    }
}
