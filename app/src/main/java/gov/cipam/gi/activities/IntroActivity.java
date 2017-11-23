package gov.cipam.gi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;

import gov.cipam.gi.common.SharedPref;
import gov.cipam.gi.fragments.Onboarding1;
import gov.cipam.gi.fragments.Onboarding2;
import gov.cipam.gi.model.Users;
import gov.cipam.gi.utils.Constants;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(new Onboarding1());
        addSlide(new Onboarding2());
        setSpecs();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        finishOnboarding();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        finishOnboarding();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    private void finishOnboarding() {
        SharedPreferences preferences =
                getSharedPreferences(Constants.MY_PREFERENCES, MODE_PRIVATE);

        preferences.edit()
                .putBoolean(Constants.ONBOARDING_COMPLETE,true).apply();

        Users user = SharedPref.getSavedObjectFromPreference(this,Constants.KEY_USER_INFO,Constants.KEY_USER_DATA,Users.class);
        if(user!=null) {
            startActivity(new Intent(this, HomePageActivity.class));
        }
        else {
            startActivity(new Intent(this, SignInActivity.class));
        }
        finish();
    }

    public void setSpecs(){
        setBarColor(Color.TRANSPARENT);
        setSeparatorColor(Color.TRANSPARENT);
        showStatusBar(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setColorTransitionsEnabled(true);
        // Hide Skip/Done button.
        showSkipButton(true);
        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(40);
        }
    }
