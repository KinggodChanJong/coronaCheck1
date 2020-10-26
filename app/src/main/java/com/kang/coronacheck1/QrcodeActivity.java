package com.kang.coronacheck1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.File;

public class QrcodeActivity extends AppCompatActivity {

    private static final String TAG = "로그";

    private static String OAUTH_CLIENT_ID = "dJ5BmNbAdzAmyc8Jczwj";
    private static String OAUTH_CLIENT_SECRET = "iOpZejA0pc";
    private static String OAUTH_CLIENT_NAME = "코로나 체크";

    public static OAuthLogin mOAuthLoginInstance;

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

        mOAuthLoginInstance = OAuthLogin.getInstance();


        Button btn11 = (Button)findViewById(R.id.btn_logout11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // deleteDir();
               // clearApplicationCache();
                clearApplicationData(mContext);
                mWebView.clearCache(true);
                Intent i = new Intent(QrcodeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });



    }
    public static void clearApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                //다운로드 파일은 지우지 않도록 설정
                //if(s.equals("lib") || s.equals("files")) continue;
                deleteDir(new File(appDir, s));
                Log.d("test", "File /data/data/"+context.getPackageName()+"/" + s + " DELETED");
            }
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
