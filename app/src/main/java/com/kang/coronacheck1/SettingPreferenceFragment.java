package com.kang.coronacheck1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.util.prefs.Preferences;

public class SettingPreferenceFragment extends PreferenceFragmentCompat  {
    private static final String TAG = "로그";
    private static SwitchPreferenceCompat font;
    SharedPreferences prefs;
  /*  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        PreferenceManager.setDefaultValues(SettingPreferenceFragment.this, R.xml.settings_preference,
                false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }
*/
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

}