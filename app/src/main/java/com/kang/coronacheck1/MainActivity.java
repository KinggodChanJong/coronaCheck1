package com.kang.coronacheck1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "로그";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();

    private BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity - onCreate() called");

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫화면에 띄워야 할 것들 지정해주기
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame,menu1Fragment).commitAllowingStateLoss();
        //바텀 네비게이션뷰 안의 아이템들 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu1Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_report: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_news: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }
}