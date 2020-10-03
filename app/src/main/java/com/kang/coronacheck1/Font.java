package com.kang.coronacheck1;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class Font extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"yaregular.ttf"))
                .addBold(Typekit.createFromAsset(this,"yaregular.ttf"));

    }
}
