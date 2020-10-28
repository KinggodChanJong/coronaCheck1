package com.kang.coronacheck1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

import com.kang.coronacheck1.Adapter.HelpAdaprter;

import me.relex.circleindicator.CircleIndicator;

public class FirstStartActivity extends AppCompatActivity {

    private int count = 0 ;
    HelpAdaprter adapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        viewPager = (ViewPager) findViewById(R.id.view);
        adapter = new HelpAdaprter(this);
        viewPager.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        Button setting = (Button) findViewById(R.id.btn_help);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
                Log.d("카운트", String.valueOf(count));
                if(count == 1){
                    Intent i = new Intent(getApplication(), MainActivity.class);
                    startActivity(i);
                }else if(count > 1){
                    Intent i = new Intent(getApplication(), SettingMain.class);
                    startActivity(i);
                }
            }
        });
    }
    private void skip() {
     /*   Intent i = new Intent(this, MainActivity.class);
        startActivity(i);*/
        count += 1;

        //환경변수 1로 설정해서 안보이게 해줌
        int infoFirst = 1;
        SharedPreferences a = getSharedPreferences("a",MODE_PRIVATE);
        SharedPreferences.Editor editor = a.edit();
        editor.putInt("First",infoFirst);
        editor.commit();
        Log.d("도움말 페이지" , String.valueOf(editor));
        finish();
    }

}