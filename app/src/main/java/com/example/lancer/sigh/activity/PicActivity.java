package com.example.lancer.sigh.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lancer.sigh.R;

public class PicActivity extends BaseActivity {

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_pic;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
    }
}
