package gov.cipam.gi.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import gov.cipam.gi.R;
import gov.cipam.gi.utils.Constants;

/**
 * Created by karan on 11/20/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    String theme;
    Toolbar mToolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpTheme();
        //setUpFont();

        super.onCreate(savedInstanceState);

        sharedPreferencesListener();

    }

    @Override
    protected void onStart() {
        super.onStart();

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(prefListener);
    }

    //get toolbar ID
    protected abstract int getToolbarID();

    protected void setUpToolbar(Activity activity) {
        mToolbar = findViewById(getToolbarID());
        setSupportActionBar(mToolbar);
        loadPreferences();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (theme.equals(getString(R.string.theme_light))) {
            mToolbar.setBackgroundColor(ContextCompat.getColor(activity,R.color.colorPrimaryDark));
            mToolbar.setPopupTheme(R.style.AppTheme);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimaryDark));
            }
        else if (theme.equals(getString(R.string.theme_dark))) {
            mToolbar.setBackgroundColor(ContextCompat.getColor(activity,R.color.colorPrimaryDarkThemeDark));
            mToolbar.setPopupTheme(R.style.AppDarkTheme);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimaryDarkThemeDark));
            }
        }
    }

    protected void setUpTheme() {

        loadPreferences();

        if (theme.equals(getString(R.string.theme_light))) {

            setTheme(R.style.AppTheme);
        } else if (theme.equals(getString(R.string.theme_dark))) {

            setTheme(R.style.AppDarkTheme);
        }
    }

    protected void loadPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));

        //downloadImages  = sharedPreferences.getBoolean(Constants.KEY_DOWNLOAD_IMAGES, true);
    }

    protected void sharedPreferencesListener() {

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if (key.equals(Constants.KEY_APP_THEME)) {

                    recreate();
                }
            }
        };
    }
}
