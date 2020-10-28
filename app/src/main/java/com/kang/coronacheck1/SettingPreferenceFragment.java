package com.kang.coronacheck1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.util.prefs.Preferences;

import static java.util.ResourceBundle.clearCache;

public class SettingPreferenceFragment extends PreferenceFragmentCompat  {
    private static final String TAG = "로그";
    private static SwitchPreferenceCompat font,popup;
    SharedPreferences prefs;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preference);
        font = (SwitchPreferenceCompat) findPreference("fontsize");

        Log.d(TAG,"체크?" + font);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(prefListener);

        if (font.isChecked() == true) {
            font.setDefaultValue(true);
            font.setSummaryOn("크게");
            FlagVar.setState(2);
            Log.d(TAG,"체크" + font);
        }else if(font.isChecked() == false){
            font.setDefaultValue(false);
            font.setSummaryOff("보통");
            FlagVar.setState(1);
            Log.d(TAG,"논체크" + font);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if(key.equals("email")){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:eeee5656@naver.com"));
            startActivity(intent);
        }else if(key.equals("logout")){
            clearApplicationData(this);
        }else if(key.equals("helppop")){
            Intent intent = new Intent(getContext(),FirstStartActivity.class);
            startActivity(intent);
        }

        return false;
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("fontsize")){
                if (font.isChecked() == true) {
                    font.setDefaultValue(true);
                    font.setSummaryOn("크게");
                    FlagVar.setState(2);
                    Log.d(TAG,"체크" + font);
                    ((MainActivity)(MainActivity.mContext)).onResume();
                }else if(font.isChecked() == false){
                    font.setDefaultValue(false);
                    font.setSummaryOff("보통");
                    FlagVar.setState(1);
                    Log.d(TAG,"논체크" + font);
                    ((MainActivity)(MainActivity.mContext)).onResume();
                }
            }
        }
    };

    private void clearApplicationData(SettingPreferenceFragment settingPreferenceFragment) {
        clearCache();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(QrcodeActivity.mContext);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookies(new ValueCallback() {
                @Override
                public void onReceiveValue(Object value) {
                    Log.d("onReceiveValue", value.toString());
                }
            });
            cookieManager.getInstance().flush();
        }
    }

}