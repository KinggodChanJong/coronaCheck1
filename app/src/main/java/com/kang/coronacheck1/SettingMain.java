package com.kang.coronacheck1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SettingMain extends AppCompatActivity {

    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);

        Intent intent = getIntent();
        page = intent.getStringExtra("page");
        Log.d("로그", page);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("page", page);

        startActivity(intent);
        finish();
    }
}