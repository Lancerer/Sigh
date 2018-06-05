package com.example.lancer.sigh.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.example.lancer.sigh.R;
import com.example.lancer.sigh.fragment.news.CityFragment;
import com.example.lancer.sigh.fragment.news.HappyFragment;
import com.example.lancer.sigh.fragment.news.ITFragment;
import com.example.lancer.sigh.fragment.news.PeFragment;
import com.example.lancer.sigh.fragment.news.SpecialFragment;

import java.util.ArrayList;
import java.util.List;


public class NewFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private android.support.design.widget.TabLayout tabNew;
    private android.support.v4.view.ViewPager vpNew;
    private String[] title = {"娱乐", "体育", "城市", "互联网", "奇闻"};
    private List<Fragment> fragments;
    private android.support.v7.widget.Toolbar toolbarNew;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_new;
    }

    @Override
    public void initView(View view) {
        tabNew = view.findViewById(R.id.tab_new);
        vpNew = view.findViewById(R.id.vp_new);
        toolbarNew = view.findViewById(R.id.toolbar_new);
    }

    @Override
    public void initData() {

        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbarNew, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        for (String i : title) {
            tabNew.addTab(tabNew.newTab().setText(i));
        }
        tabNew.setTabMode(TabLayout.MODE_SCROLLABLE);//可滑动
        toolbarNew.setTitle("新闻");
        initFragment();
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        };
        vpNew.setAdapter(fragmentPagerAdapter);
        tabNew.setupWithViewPager(vpNew);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HappyFragment());
        fragments.add(new PeFragment());
        fragments.add(new CityFragment());
        fragments.add(new ITFragment());
        fragments.add(new SpecialFragment());
    }

    @Override
    public void initListener() {
        tabNew.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vpNew.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
