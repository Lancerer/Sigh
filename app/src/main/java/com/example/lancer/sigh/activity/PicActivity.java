package com.example.lancer.sigh.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.lancer.sigh.R;

public class PicActivity extends AppCompatActivity {

    private ImageView iv;
    private WebView wb;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        initView();
        initData();
    }
    public void initData() {

        String url = getIntent().getStringExtra("URL");
        wb.loadUrl(url);
        WebSettings settings = wb.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        wb.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        wb.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbLoading.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.INVISIBLE);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    public void initView() {
        wb = findViewById(R.id.wb);
        pbLoading = findViewById(R.id.pb_loading);
    }

}
