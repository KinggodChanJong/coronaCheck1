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
import android.text.TextUtils;
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

    private String page = "0";

    public static Context mContext;//새로고침을 위한 추가
    private static Typeface typeface; //글꼴 전역 변경을 위해 추가

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
                        page ="1";
                        break;
                    }
                    case R.id.navigation_city: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu2Fragment).commit();
                        mText.setText("코로나 도시 현황");
                        page ="2";
                        break;
                    }
                    case R.id.navigation_world:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu3Fragment).commit();
                        mText.setText("코로나 세계 현황");
                        page ="3";
                        break;
                    }
                    case R.id.navigation_news: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu4Fragment).commit();
                        mText.setText("코로나 TOP10 뉴스");
                        page ="4";
                        break;
                    }
                }
                return true;
            }
        });
        Intent intent = getIntent();
        String pageOK = intent.getStringExtra("page");
        if(TextUtils.isEmpty(pageOK)){
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu1Fragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
            Log.d("1프레그먼트", "1");
        }else if(pageOK.equals("2")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu2Fragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_city);
            Log.d("2프레그먼트", "2");
        } else if(pageOK.equals("3")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu3Fragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_world);
            Log.d("3프레그먼트", "3");
        }else if(pageOK.equals("4")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu4Fragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_news);
            Log.d("4프레그먼트", "4");
        }
    }

    private void setting() {
        Intent i = new Intent(this, SettingMain.class);
        i.putExtra("page", page);
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

        Log.d(TAG,prefs.toString());
        if(font == true){
            FlagVar.setState(2);
        }else if(font == false){
            FlagVar.setState(1);
        }
    }
}