package com.kang.coronacheck1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class QrcodeActivity extends AppCompatActivity {

    private static final String TAG = "로그";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        Log.d(TAG, "QrcodeActivity - onCreate() called");

    }
}
