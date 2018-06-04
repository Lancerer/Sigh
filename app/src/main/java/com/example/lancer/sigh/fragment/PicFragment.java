package com.example.lancer.sigh.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lancer.sigh.R;

public class PicFragment extends BaseFragment {
    private SwipeRefreshLayout swipPic;
    private RecyclerView recyPic;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_pic;
    }

    @Override
    public void initView(View view) {
        swipPic = view.findViewById(R.id.swip_pic);
        recyPic = view.findViewById(R.id.recy_pic);
    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
