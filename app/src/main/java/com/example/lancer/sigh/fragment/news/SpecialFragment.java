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
import com.example.lancer.sigh.adapter.CityAdapter;
import com.example.lancer.sigh.adapter.PicAdapter;
import com.example.lancer.sigh.adapter.SpecialAdapter;
import com.example.lancer.sigh.fragment.BaseFragment;
import com.example.lancer.sigh.model.CityBean;
import com.example.lancer.sigh.model.SpecialBean;
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


public class SpecialFragment extends BaseFragment {

    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshCity;
    private android.support.v7.widget.RecyclerView recyCity;
    private List<SpecialBean.NewslistBean> lists;
    private LinearLayoutManager linearLayoutManager;
    private SpecialAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_special;
    }

    @Override
    public void initView(View view) {

        refreshCity = view.findViewById(R.id.refresh_city);
        recyCity = view.findViewById(R.id.recy_city);
    }

    @Override
    public void initData() {
        lists = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        request(false);
    }

    private void request(final boolean flag) {
        HttpUtil.getRequest("http://api.tianapi.com/qiwen/?key=4e8309c39e4ddab74b0b3ac294030bc3&num=10&rand=1", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                final SpecialBean specialBean = gson.fromJson(json, SpecialBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!flag) {
                            lists = specialBean.getNewslist();
                            recyCity.setLayoutManager(linearLayoutManager);
                            adapter = new SpecialAdapter(lists, getContext());
                            recyCity.setAdapter(adapter);
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
                            List<SpecialBean.NewslistBean> newslist = specialBean.getNewslist();
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
        refreshCity.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                request(true);
                refreshlayout.finishLoadmore(1000);
            }
        });
        refreshCity.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request(false);
                refreshlayout.finishRefresh(1000);
            }
        });
    }

}
