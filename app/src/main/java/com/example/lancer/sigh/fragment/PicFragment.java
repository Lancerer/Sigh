package com.example.lancer.sigh.fragment;


import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;


import com.example.lancer.sigh.R;
import com.example.lancer.sigh.activity.PicActivity;
import com.example.lancer.sigh.adapter.PicAdapter;
import com.example.lancer.sigh.model.PicBean;
import com.example.lancer.sigh.util.HttpUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PicFragment extends BaseFragment {

    private RecyclerView recyPic;
    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshPic;
    private List<PicBean.NewslistBean> lists;
    private LinearLayoutManager linearLayoutManager;
    private PicAdapter adapter;
    private android.support.v7.widget.Toolbar toolbarPic;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_pic;
    }

    @Override
    public void initView(View view) {
        recyPic = view.findViewById(R.id.recy_pic);
        refreshPic = view.findViewById(R.id.refresh_pic);
        toolbarPic = view.findViewById(R.id.toolbar_pic);
    }

    @Override
    public void initListener() {
        refreshPic.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request(false);
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(1000);
            }
        });
        refreshPic.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                request(true);
                refreshlayout.finishLoadmore(1000);
            }
        });
    }

    @Override
    public void initData() {
        toolbarPic.setTitle("图片");
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbarPic, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        lists = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        request(false);

    }

    private void request(final boolean flag) {
        HttpUtil.getRequest("http://api.tianapi.com/meinv/?key=4e8309c39e4ddab74b0b3ac294030bc3&num=20&rand=1", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                final PicBean picBean = gson.fromJson(json, PicBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!flag) {
                            lists = picBean.getNewslist();
                            recyPic.setLayoutManager(linearLayoutManager);
                            adapter = new PicAdapter(lists, getContext());
                            recyPic.setAdapter(adapter);
                            adapter.setOnItemClickListener(new PicAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String url = lists.get(position).getPicUrl();
                                    String replace = url.replace("\\", "");
                                    Intent intent = new Intent(getContext(), PicActivity.class);
                                    intent.putExtra("URL", replace);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            List<PicBean.NewslistBean> newlist = picBean.getNewslist();
                            lists.addAll(newlist);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }
}
