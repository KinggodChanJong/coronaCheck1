package com.kang.coronacheck1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class NewsWebViewActivity extends AppCompatActivity {

    private static final String TAG = "로그";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_webview);

        Log.d(TAG, "NewsWebViewctivity - onCreate() called");
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView = (WebView) findViewById(R.id.webView_news);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);//웹뷰 실행
        mWebView.setWebChromeClient(new WebChromeClient());//웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
        mWebView.setWebViewClient(new WebViewClientClass());

    }
    private class WebViewClientClass extends WebViewClient {//페이지 이동
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG,"WebViewClientClass - shouldOverriderUrlLoading() - called URL = "+url);
            view.loadUrl(url);
            return true;
        }
    }
}