package com.example.lancer.sigh.fragment.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lancer.sigh.R;
import com.example.lancer.sigh.activity.PicActivity;
import com.example.lancer.sigh.adapter.HappyAdapter;
import com.example.lancer.sigh.adapter.PicAdapter;
import com.example.lancer.sigh.fragment.BaseFragment;
import com.example.lancer.sigh.model.HappyBean;
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

public class HappyFragment extends BaseFragment {

    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshHappy;
    private android.support.v7.widget.RecyclerView recyHappy;
    private List<HappyBean.NewslistBean> lists;
    private LinearLayoutManager linearLayoutManager;
    private HappyAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_happy;
    }

    @Override
    public void initView(View view) {
        refreshHappy = view.findViewById(R.id.refresh_happy);
        recyHappy = view.findViewById(R.id.recy_happy);
    }

    @Override
    public void initData() {
        lists = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        request(false);
    }

    private void request(final Boolean flag) {
        HttpUtil.getRequest("http://api.tianapi.com/huabian/?key=4e8309c39e4ddab74b0b3ac294030bc3&num=10&rand=1", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                final HappyBean happyBean = gson.fromJson(json, HappyBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!flag) {
                            lists = happyBean.getNewslist();
                            recyHappy.setLayoutManager(linearLayoutManager);
                            adapter = new HappyAdapter(lists, getContext());
                            recyHappy.setAdapter(adapter);
                            adapter.setOnItemClickListener(new PicAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String url = lists.get(position).getUrl();
                                    String replace = url.replace("\\", "");
                                    Intent intent = new Intent(getContext(), PicActivity.class);
                                    intent.putExtra("URL", replace);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            List<HappyBean.NewslistBean> newslist = happyBean.getNewslist();
                            lists.addAll(newslist);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
            }
        });
    }

    @Override
    public void initListener() {
        refreshHappy.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                request(true);
                refreshlayout.finishLoadmore(1000);
            }
        });
        refreshHappy.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request(false);
                refreshlayout.finishRefresh(1000);
            }
        });
    }

}
