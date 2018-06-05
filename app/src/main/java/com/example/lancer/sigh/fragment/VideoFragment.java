package com.example.lancer.sigh.fragment;



import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.example.lancer.sigh.R;

public class VideoFragment extends BaseFragment {
    private android.support.v7.widget.Toolbar toolbarVideo;
    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshVideo;
    private android.support.v7.widget.RecyclerView recyVideo;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView(View view) {
        toolbarVideo = view.findViewById(R.id.toolbar_video);
        refreshVideo = view.findViewById(R.id.refresh_video);
        recyVideo = view.findViewById(R.id.recy_video);
    }

    @Override
    public void initData() {
        toolbarVideo.setTitle("视频");
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbarVideo, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void initListener() {
    }
}
