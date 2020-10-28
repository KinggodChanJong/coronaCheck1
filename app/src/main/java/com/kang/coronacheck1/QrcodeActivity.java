package com.kang.coronacheck1;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import java.io.File;

public class QrcodeActivity extends AppCompatActivity {

    private static final String TAG = "로그";

    private static String OAUTH_CLIENT_ID = "dJ5BmNbAdzAmyc8Jczwj";
    private static String OAUTH_CLIENT_SECRET = "iOpZejA0pc";
    private static String OAUTH_CLIENT_NAME = "코로나 체크";


    public static Context mContext;//새로고침을 위한 추가


    private WebView mWebView;
    private WebSettings mWebSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        Log.d(TAG, "QrcodeActivity - onCreate() called");


        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(false);
        /**
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setUserAgentString("app");

         **/

        mWebView.loadUrl("https://nid.naver.com/login/privacyQR/");


        Button btn11 = (Button)findViewById(R.id.btn_logout11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clearApplicationData(this);
                mWebView.clearCache(true);
                Intent i = new Intent(QrcodeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void clearApplicationData(View.OnClickListener onClickListener) {
        mWebView.clearCache(true);
        mWebView.clearHistory();
        // 자동완성은 8.0부터는 내장되어 아래 함수 안먹음
        mWebView.clearFormData();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(this);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookies(new ValueCallback() {
                @Override
                public void onReceiveValue(Object value) {
                    Log.d("onReceiveValue", value.toString());
                }

            });
            cookieManager.getInstance().flush();
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    /**
    private void clearApplicationCache(){
        File dir = getCacheDir();
        if(dir==null) return;

        File[] children = dir.listFiles();
        try {
            // 쿠키 삭제
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();

            for(int i=0;i<children.length;i++) {
                if(children[i].isDirectory()) {
                    clearApplicationCache(children[i]);
                } else {
                    children[i].delete();
                }
            }
        } catch(Exception e){}
    }

    private void clearApplicationCache(java.io.File dir){
        if(dir==null) dir = getCacheDir();
        if(dir==null) return;

        java.io.File[] children = dir.listFiles();
        try{
            for(int i=0;i<children.length;i++)
                if(children[i].isDirectory()) {
                    clearApplicationCache(children[i]);
                } else {
                    children[i].delete();
                }
        }
        catch(Exception e){}
    }



    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
/**
    public boolean deleteDir() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir != null && appDir.isDirectory()) {
            String[] children = appDir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir();
                if (!success) {
                    return false;
                }
            }
        }
        return appDir.delete();
    }
**/

}
