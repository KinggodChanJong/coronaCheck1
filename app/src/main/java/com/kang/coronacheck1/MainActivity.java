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

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabQrcode;
    Intent intent;

    //플래그 값 초기화
    private int flag = 0;
    private int fontvar = 0;

    private TextView tv_title_2, tv_check_2, tv_safe_2, tv_die_2,tv_title_1, tv_check_1, tv_safe_1,tv_die_1,
            tv_title_3, tv_check_3, tv_safe_3, tv_die_3;;

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
        ImageButton web_view = (ImageButton) findViewById(R.id.button);

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
                }
                return true;
            }
        });
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab :
                Log.d(TAG, "MainActivity fab - onClick() called");
                intent = new Intent(this, QrcodeActivity.class);
                startActivity(intent);
                break;
            case R.id.button:
                intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
                break;
        }
    }
    /*
    재시작
    */
    @Override
    protected void onResume() {
        super.onResume();
        //화면전환시 받을 값 지정
        mContext = this;
        //플래그 값 받기
        //FlagVar myflag = (FlagVar) getApplicationContext();
        //폰트변경
        fontvar = FlagVar.getFontvar();
        //텍스트 크기
        flag = FlagVar.getState();

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
            tv_title_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            tv_title_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv_title_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_check_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            tv_check_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv_check_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_safe_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            tv_safe_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv_safe_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_die_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            tv_die_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv_die_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);
        }
        else if(flag == 2){
            tv_title_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_title_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_title_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_check_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_check_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_check_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_safe_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_safe_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_safe_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_die_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_die_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_die_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);
        }else if(flag == 3){
            tv_title_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_title_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_title_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_check_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_check_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_check_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_safe_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_safe_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_safe_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);

            tv_die_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tv_die_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv_die_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);
        }

        ///// 폰트
        else if(fontvar == 1){
            if(typeface == null) {
                typeface = ResourcesCompat.getFont(this, R.font.fonts);
            }
            setGlobalFont(getWindow().getDecorView());

        }




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

    //폰트 전역 변경
    public void setGlobalFont(View view) {
        if(view != null) {
            if(view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view;
                int vgCnt = viewGroup.getChildCount();
                for(int i = 0; i<vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if(v instanceof TextView) {
                        ((TextView) v).setTypeface(typeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }

    //////////////////////////////////////

}