package com.example.sunrin.test09_preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

/**
 * Created by Sunrin on 2018-04-19.
 */

public class MyPreferenceFragment extends PreferenceFragment {

    SharedPreferences pre;

    ListPreference listPre;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        listPre = (ListPreference) findPreference("network");
        pre = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (!pre.getString("network", "").equals(""))
            listPre.setSummary(pre.getString("network", "LTE"));

        pre.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

    }

    SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("network"))
                        listPre.setSummary(pre.getString("network", "LTE"));
                }
            };

}
