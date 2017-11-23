package gov.cipam.gi.activities;

import android.os.Bundle;

import gov.cipam.gi.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setUpToolbar(this);
    }

    @Override
    protected int getToolbarID() {
        return R.id.about_activity_toolbar;
    }
}
