package com.kang.coronacheck1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "로그";
    public static Context mContext;//새로고침을 위한 추가
    private static Typeface typeface; //글꼴 전역 변경을 위해 추가

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    private Menu4Fragment menu4Fragment = new Menu4Fragment();

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabQrcode;
    Intent intent;

    //플래그 값 초기화
    private int flag = FlagVar.getState();
    SharedPreferences prefs;
    private boolean font;


    private TextView tv_title_2, tv_check_2, tv_safe_2, tv_die_2,tv_title_1, tv_check_1, tv_safe_1,tv_die_1,
            tv_title_3, tv_check_3, tv_safe_3, tv_die_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        //플래그 값 받기
       // FlagVar myflag = (FlagVar) getApplicationContext();
        //폰트변경
       //fontvar = myflag.getFontvar();
        //텍스트 크기
       // flag = myflag.getState();



        Log.d(TAG, "MainActivity - onCreate() called");

        //androidx.preference.PreferenceManager.setDefaultValues(this,R.xml.settings_preference,false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        font =prefs.getBoolean("fontsize",false);
        Log.d(TAG,"font"+font);
        flag= FlagVar.getState();
        //Log.d(TAG, "pref값"+String.valueOf(prefs.getAll()));
        //mContext = this;
        Log.d(TAG, "앞단 flag"+String.valueOf(flag));
        //init();

        TextView mText = (TextView)findViewById(R.id.tv_main_title);

        fabQrcode = (FloatingActionButton)findViewById(R.id.fab);
        fabQrcode.setOnClickListener(this);
        /**
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("first", hiyo.getText().toString());

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
**/
        //임시 세팅 버튼
        Button setting = (Button) findViewById(R.id.btn_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });



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
                        mText.setText("코로나 현황");
                        break;
                    }
                    case R.id.navigation_report: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu2Fragment).commitAllowingStateLoss();
                        mText.setText("코로나 도시별 현황");
                        break;
                    }
                    case R.id.navigation_news: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu3Fragment).commitAllowingStateLoss();
                        mText.setText("코로나 TOP10 뉴스");
                        break;
                    }
                    case R.id.navigation_faq:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame, menu4Fragment).commitAllowingStateLoss();
                        mText.setText("코로나 FAQ");
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void init() {
        tv_title_1 = findViewById(R.id.tv_title_1);
        tv_title_2 = findViewById(R.id.tv_title_2);
        tv_title_3 = findViewById(R.id.tv_title_3);
        tv_check_1 = findViewById(R.id.tv_check_1);
        tv_check_2 = findViewById(R.id.tv_check_2);
        tv_check_3 = findViewById(R.id.tv_check_3);
        tv_safe_1 = findViewById(R.id.tv_safe_1);
        tv_safe_2 = findViewById(R.id.tv_safe_2);
        tv_safe_3 = findViewById(R.id.tv_safe_3);
        tv_die_1 = findViewById(R.id.tv_die_1);
        tv_die_2 = findViewById(R.id.tv_die_2);
        tv_die_3 = findViewById(R.id.tv_die_3);

        if(flag == 1){
            Log.d(TAG,"폰트사이즈 받았습니다.");
            tv_title_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_title_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_title_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

            tv_check_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_check_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_check_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

            tv_safe_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_safe_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_safe_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

            tv_die_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_die_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_die_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        } else if(flag == 2){
            tv_title_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_title_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_title_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

            tv_check_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_check_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_check_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

            tv_safe_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_safe_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_safe_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

            tv_die_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_die_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv_die_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }
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
        Log.d(TAG,prefs.toString());
        if(font == true){
            FlagVar.setState(2);
        }else if(font == false){
            FlagVar.setState(1);
        }

        //????


//        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_frame,menu1Fragment).commit();
//        getSupportFragmentManager().executePendingTransactions();
//        //텍스트 크기 flag
        //flag = FlagVar.getState();
        //Log.d(TAG, "뒷단 flag"+String.valueOf(flag));

        //init();

    }

    //폰트적용
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}