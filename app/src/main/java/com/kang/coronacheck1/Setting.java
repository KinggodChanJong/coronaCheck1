package com.kang.coronacheck1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.kang.coronacheck1.Adapter.ReportAdapter;

public class Setting extends AppCompatActivity {
    private Button smallbtn, midbtn, bigbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RadioGroup rgFontsize = findViewById(R.id.rg_fontsize);
        RadioGroup rgFontchang = findViewById(R.id.rg_fontchang);
        //FlagVar myApp = (FlagVar) getApplicationContext();
        rgFontsize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_font_small :
                        int myApp =FlagVar.setState(1);
                        //Log.d("로그", String.valueOf(myApp.getState()));
                     //   ((MainActivity)(MainActivity.mContext)).onResume();

                        break;
                    case R.id.btn_font_mid :
                        int myApp1 =FlagVar.setState(2);
                        //Log.d("로그", String.valueOf(myApp.getState()));
                      //  ((MainActivity)(MainActivity.mContext)).onResume();
                        break;
                    case R.id.btn_font_big :
                        int myApp2 = FlagVar.setState(3);
                        //Log.d("로그", String.valueOf(myApp.getState()));
                     //   ((MainActivity)(MainActivity.mContext)).onResume();
                        break;
                }
            }
        });

        rgFontchang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_font_ya :
                        //myApp.setFontvar(1);
                        //Log.d("로그", String.valueOf(myApp.getFontvar()));
                      //  ((MainActivity)(MainActivity.mContext)).onResume();
                        break;
                    case R.id.btn_font_nomal :
                       // myApp.setFontvar(2);
                        //Log.d("로그", String.valueOf(myApp.getFontvar()));
                        //((MainActivity)(MainActivity.mContext)).onResume();
                        break;
                    case R.id.btn_font_kor :
                        //myApp.setFontvar(3);
                        //Log.d("로그", String.valueOf(myApp.getFontvar()));
                        //((MainActivity)(MainActivity.mContext)).onResume();
                        break;
                }
            }
        });
        /*smallbtn = findViewById(R.id.btn_font_small);
        smallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlagVar myApp = (FlagVar) getApplicationContext();
                myApp.setState(1);
                Log.d("로그", String.valueOf(myApp.getState()));
                ((MainActivity)(MainActivity.mContext)).onResume();
            }
        });
        midbtn = findViewById(R.id.btn_font_mid);
        bigbtn = findViewById(R.id.btn_font_big);*/
    }
}