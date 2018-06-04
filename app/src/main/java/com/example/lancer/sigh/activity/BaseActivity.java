package com.example.lancer.sigh.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.lancer.sigh.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Lancer on 2018/6/4.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private long mExitTime;
    public abstract void initView();

    public abstract void initData();

    public abstract int initLayout();

    public abstract void initListener();

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initView();
        initListener();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    public void loadImageView(final ImageView imageView) {
        String path = "http://guolin.tech/api/bing_pic";
        HttpUtil.getRequest(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(BaseActivity.this).load(string).into(imageView);
                    }
                });
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
