package com.example.lancer.sigh.fragment;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lancer.sigh.R;


public abstract class BaseFragment extends Fragment {
    private Activity mActivity;
    private View view;

    // Fragment被创建
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(setLayoutId(), container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    protected abstract int setLayoutId();

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void initListener();



}
