package gov.cipam.gi.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import gov.cipam.gi.R;

/**
 * Created by karan on 11/20/2017.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);
    }
}
