package com.example.wanandroid1.ui.my.setting;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.wanandroid1.R;

/**
 * Created by Golden on 2018/3/6.
 */

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference_fragment);
        Preference settingAutoUpdate = findPreference("settingAutoUpdate");
        Preference checkUpdate = findPreference("checkUpdate");
        Preference about = findPreference("about");
        Preference testPage = findPreference("testPage");

        settingAutoUpdate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });
    }

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }
}
