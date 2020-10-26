package com.kang.coronacheck1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

public class NaverLoginActivity extends AppCompatActivity {

    private static final String TAG = "로그";
    // 네이버 로그인
    private static String OAUTH_CLIENT_ID = "dJ5BmNbAdzAmyc8Jczwj";
    private static String OAUTH_CLIENT_SECRET = "iOpZejA0pc";
    private static String OAUTH_CLIENT_NAME = "네이버아이디로 로그인";

    private OAuthLoginButton authLoginButton;
    public static OAuthLogin mOAuthLoginInstance;

    public static Context mContext;//새로고침을 위한 추가


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naverlogin);

        mContext = this;

        initData();
        initView();



        /**
         if (mOAuthLoginInstance.getAccessToken(this) != null) {
         startActivity(new Intent(this,QrcodeActivity.class));
         } else {
         authLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

         //위 코드가 네이버 아이디 로그인을 요청하는 코드입니다.
         }
         **/

    }
    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();

        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        /*
         * 2015년 8월 이전에 등록하고 앱 정보 갱신을 안한 경우 기존에 설정해준 callback intent url 을 넣어줘야 로그인하는데 문제가 안생긴다.
         * 2015년 8월 이후에 등록했거나 그 뒤에 앱 정보 갱신을 하면서 package name 을 넣어준 경우 callback intent url 을 생략해도 된다.
         */
        //mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME, OAUTH_callback_intent_url);
    }
    private void initView() {

        authLoginButton = (OAuthLoginButton) findViewById(R.id.btn_naverlogin);
        authLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

    }
    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();

    }
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                Log.d(TAG, accessToken);
                Log.d(TAG, refreshToken);
                Log.d(TAG, String.valueOf(expiresAt));
                Log.d(TAG, tokenType);
                Log.d(TAG, mOAuthLoginInstance.getState(mContext).toString());
                Intent intent = new Intent(NaverLoginActivity.this, MainActivity.class);
                startActivity(intent);

            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void onButtonClick(View v) throws Throwable {

        switch (v.getId()) {
            case R.id.btn_naverlogin: {
                mOAuthLoginInstance.startOauthLoginActivity(NaverLoginActivity.this, mOAuthLoginHandler);

                break;
            }

            case R.id.btn_guest: {
                mOAuthLoginInstance.logout(mContext);
                break;
            }

            default:
                break;
        }
    }

}
