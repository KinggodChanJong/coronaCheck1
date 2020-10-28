package com.kang.coronacheck1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kang.coronacheck1.MenuFragment.Menu1Fragment;
import com.kang.coronacheck1.MenuFragment.Menu3Fragment;
import com.kang.coronacheck1.MenuFragment.Menu2Fragment;
import com.kang.coronacheck1.MenuFragment.Menu4Fragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "로그";


    // public static OAuthLogin mOAuthLoginInstance;
    // 네이버 로그인
    public static Context mContext;//새로고침을 위한 추가
    private static Typeface typeface; //글꼴 전역 변경을 위해 추가
    private int pop;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    private Menu4Fragment menu4Fragment = new Menu4Fragment();

    private BottomNavigationView bottomNavigationView;
    private Button fabQrcode;
    Intent intent;

    //플래그 값 초기화
    private int flag = FlagVar.getState();
    SharedPreferences prefs , helpprefs;
    private boolean font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //플래그 값 받기
       // FlagVar myflag = (FlagVar) getApplicationContext();
        //폰트변경
       //fontvar = myflag.getFontvar();
        //텍스트 크기
       // flag = myflag.getState();

        Log.d(TAG, "MainActivity - onCreate() called");

        //도움말
            helpprefs = getSharedPreferences("a",MODE_PRIVATE);
            int firstviewhow = helpprefs.getInt("First",0);

            if(firstviewhow != 1){
                Intent intent = new Intent(MainActivity.this,FirstStartActivity.class);
                startActivity(intent);
            }
        //
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        font =prefs.getBoolean("fontsize",false);
        boolean flagpopup =prefs.getBoolean("popup",false);

        Log.d(TAG,"font"+font);
        flag= FlagVar.getState();
        Log.d(TAG, "앞단 flag"+String.valueOf(flag));

        TextView mText = (TextView)findViewById(R.id.tv_main_title);
        fabQrcode = (Button)findViewById(R.id.fab);
        fabQrcode.setOnClickListener(this);

        //임시 세팅 버튼
        Button setting = (Button) findViewById(R.id.btn_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu1Fragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu1Fragment).commit();
                        mText.setText("코로나 현황");
                        break;
                    }
                    case R.id.navigation_city: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu2Fragment).commit();
                        mText.setText("코로나 도시 현황");
                        break;
                    }
                    case R.id.navigation_world:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu3Fragment).commit();
                        mText.setText("코로나 세계 현황");
                        break;
                    }
                    case R.id.navigation_news: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu4Fragment).commit();
                        mText.setText("코로나 TOP10 뉴스");
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void setting() {
        Intent i = new Intent(this, SettingMain.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab :
                Log.d(TAG, "MainActivity fab - onClick() called");

                intent = new Intent(this, QrcodeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_setting:
                intent = new Intent(getApplicationContext(), SettingMain.class);
                startActivity(intent);
                break;
        }
    }

    //재시작
    @Override
    protected void onResume() {
        super.onResume();
        //화면전환시 받을 값 지정

        mContext = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean font =prefs.getBoolean("fontsize",false);
        //도움말 스위치
        boolean flagpopup =prefs.getBoolean("popup",false);
        //도움말 팝업
        if(flagpopup == false){

            /*CustomDialog customDialog = new CustomDialog(MainActivity.this);
            customDialog.callFunction();*/
            //팝업창
            /*new AlertDialog.Builder(MainActivity.this) // 현재 Activity의 이름 입력.
                    .setTitle("도움말")
                    .setMessage("가나다라")     // 제목 부분 (직접 작성)
                    .setPositiveButton("다시보지 않기", new DialogInterface.OnClickListener() {      // 버튼1 (직접 작성)
                        public void onClick(DialogInterface dialog, int which) {
                            FlagVar.setPopup(2);
                        }
                    })
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {     // 버튼2 (직접 작성)
                        public void onClick(DialogInterface dialog, int which){
                        }
                    })
                    .show();*/
        }
        Log.d(TAG,prefs.toString());
        if(font == true){
            FlagVar.setState(2);
        }else if(font == false){
            FlagVar.setState(1);
        }
    }


}