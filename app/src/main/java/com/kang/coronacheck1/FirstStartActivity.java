package com.kang.coronacheck1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kang.coronacheck1.Adapter.HelpAdapter;

import me.relex.circleindicator.CircleIndicator;

public class FirstStartActivity extends AppCompatActivity {

    private String count ="";


    HelpAdapter adapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        viewPager = (ViewPager) findViewById(R.id.view);
        adapter = new HelpAdapter(this);
        viewPager.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        Button setting = (Button) findViewById(R.id.btn_help);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
                Log.d("카운트", String.valueOf(count));

            }
        });
    }
    private void skip() {
        Intent intent = getIntent();

        if(TextUtils.isEmpty(intent.getStringExtra("count"))){
            count ="1";
            Log.d("위에 통과함", "1");
        }else{
            count =intent.getStringExtra("count");
            Log.d("아래통과함", "2");
        }
        Log.d("Count 로그", String.valueOf(count));
        //환경변수 1로 설정해서 안보이게 해줌
        int infoFirst = 1;
        SharedPreferences a = getSharedPreferences("a",MODE_PRIVATE);
        SharedPreferences.Editor editor = a.edit();
        editor.putInt("First",infoFirst);
        editor.commit();
        Log.d("도움말 페이지" , String.valueOf(editor));
        if(!count.equals("2")){
            Intent i = new Intent(getApplication(), MainActivity.class);
            startActivity(i);
        }else {
            Intent i = new Intent(getApplication(), SettingMain.class);
            startActivity(i);
        }
        finish();
    }

}