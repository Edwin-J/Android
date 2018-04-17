package com.example.mypreference2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Sunrin on 2018-04-17.
 */

public class SettingFragment extends PreferenceFragment {

    SharedPreferences preferences;
    ListPreference soundPref;
    ListPreference keywordPref;
    PreferenceScreen keywordScre;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        soundPref = (ListPreference) findPreference("sound_list");
        keywordPref = (ListPreference) findPreference("keyword_sound_list");
        keywordScre = (PreferenceScreen) findPreference("keyword_screen");

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (!preferences.getString("sound_list", "").equals(""))
            soundPref.setSummary(preferences.getString("sound_list", "까똑"));

        if (!preferences.getString("keyword_sound_list", "").equals(""))
            soundPref.setSummary(preferences.getString("sound_list", "까똑"));

        if (preferences.getBoolean("keyword", false))
            keywordScre.setSummary("Using Now");
        else
            keywordScre.setSummary("Not Using Now");

        preferences.registerOnSharedPreferenceChangeListener(preferencesListener);

    }

    SharedPreferences.OnSharedPreferenceChangeListener preferencesListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals("sound_list"))
                        soundPref.setSummary(preferences.getString("sound_list", "까똑"));

                    if (key.equals("keyword_sound_list"))
                        keywordPref.setSummary(preferences.getString("keyword_sound_list", "까똑"));

                    if (key.equals("keyword"))
                        if (sharedPreferences.getBoolean("keyword", false)) {
                            keywordScre.setSummary("Using Now");
                            getPreferenceScreen().setSummary("1");
                        } else {
                            keywordScre.setSummary("Not Using Now");
                            getPreferenceScreen().setSummary("2");
                        }
                }
            };

}
