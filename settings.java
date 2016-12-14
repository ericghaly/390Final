package com.example.ryan.bananafinder;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * Created by Ryan on 12/13/2016.
 */

public class settings extends PreferenceActivity {

    @Override
    // we create and intialize our listeners here
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        ListPreference colorPref = (ListPreference)findPreference("Colors");
        ListPreference pinPref = (ListPreference)findPreference("pins");

        colorPref.setSummary(colorPref.getEntry());
        pinPref.setSummary(pinPref.getEntry());

        colorPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference prefernce, Object o){
                prefernce.setSummary(o.toString());
                return true;
            }
        });

        pinPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference prefernce, Object o){
                prefernce.setSummary(o.toString());
                return true;
            }
        });

    }
}