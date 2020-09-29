package com.kang.coronacheck1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabQrcode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent intent = new Intent(this, QrcodeActivity.class);
                startActivity(intent);
                break;
        }
    }
}