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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.File;

public class QrcodeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "로그";

    public static Context mContext;//새로고침을 위한 추가

    private WebView mWebView;
    private WebSettings mWebSettings;
    private Button btnHome, btnSetting;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        Log.d(TAG, "QrcodeActivity - onCreate() called");

        btnHome = (Button)findViewById(R.id.btn_qr_home);
        btnSetting = (Button)findViewById(R.id.btn_qr_setting);
        btnSetting.setOnClickListener(this);
        btnHome.setOnClickListener(this);
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


        /**
        Button btn11 = (Button)findViewById(R.id.btn_logout11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWebView.clearCache(true);
                mWebView.clearCache(true);
                mWebView.clearHistory();
                // 자동완성은 8.0부터는 내장되어 아래 함수 안먹음
                mWebView.clearFormData();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(QrcodeActivity.this);
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

                        }
                    });
                    cookieManager.getInstance().flush();
                }
                Intent i = new Intent(QrcodeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        **/

    }

    public boolean deleteCache(File dir){
        //File이 Null 인지 Null이 아니라면 폴더인지 체크
        if(dir != null && dir.isDirectory()){
            //Null도 아니고 폴더도 아니라면 폴더안 파일 리스트를 호출
            String[] children = dir.list();
            //파일 리스트를 반복문으로 호출
            for(String child : children){
                //파일 리스트중 폴더가 존재할 수 있기 때문에 재귀호출
                boolean isSuccess = deleteCache(new File(dir, child));
                if(!isSuccess){
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qr_home :
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_qr_setting:
                intent = new Intent(getApplicationContext(), SettingMain.class);
                startActivity(intent);
                break;
        }
    }
}
