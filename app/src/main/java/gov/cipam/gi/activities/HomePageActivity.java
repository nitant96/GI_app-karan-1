package gov.cipam.gi.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import gov.cipam.gi.R;
import gov.cipam.gi.common.SharedPref;
import gov.cipam.gi.fragments.HomePageFragment;
import gov.cipam.gi.fragments.MapsFragment;
import gov.cipam.gi.fragments.SocialFeedFragment;
import gov.cipam.gi.model.Users;
import gov.cipam.gi.utils.Constants;
import gov.cipam.gi.utils.NetworkChangeReceiver;
import gov.cipam.gi.utils.NetworkUtil;

public class HomePageActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TabLayout.OnTabSelectedListener{

    private FirebaseAuth mAuth;
    Users user;
    TextView nav_user,nav_email;
    HomePageFragment homePageFragment;
    SocialFeedFragment socialFeedFragment;
    SearchView searchView;
    MapsFragment mapsFragment;
    FrameLayout frameLayout;

    NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setUpToolbar(this);

        showErrorSnackbar();
        homePageFragment =new HomePageFragment();

        if(savedInstanceState==null){
            fragmentInflate(homePageFragment);
        }

        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        nav_user = hView.findViewById(R.id.nav_header_name);
        nav_email=hView.findViewById(R.id.nav_header_email);
        user = SharedPref.getSavedObjectFromPreference(HomePageActivity.this, Constants.KEY_USER_INFO,Constants.KEY_USER_DATA,Users.class);
        setUserName();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home_page, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.isSubmitButtonEnabled();
        searchView.animate();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            case R.id.action_logout:
                logoutAction();
                break;
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentInflate(homePageFragment);
                    return true;
                case R.id.navigation_map:
                    mapsFragment =new MapsFragment();
                    fragmentInflate(mapsFragment);
                    return true;
                case R.id.navigation_social_feed:
                    socialFeedFragment =new SocialFeedFragment();
                    fragmentInflate(socialFeedFragment);
                    return true;
            }
            return false;
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
          startActivity(new Intent(this,AccountInfoActivity.class));
        } else if (id == R.id.nav_sign_up) {

        }  else if (id == R.id.nav_about_us) {
            startActivity(new Intent(this,AboutActivity.class));

        } else if (id == R.id.nav_share){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT,"google.com").setType("text/plain");
            startActivity(Intent.createChooser(intent,"Choose an app"));
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser ==null){
            startActivity(new Intent(this,NewUserActivity.class));
        }
        registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        // register broadcast receiver which listens for change in network state
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregister broadcast receiver
        unregisterReceiver(networkChangeReceiver);
    }

    private void setUserName() {
        if(user!=null) {
            nav_email.setText(user.getEmail());
            nav_user.setText("Hi "+user.getName().substring(0,1).toUpperCase()+user.getName().substring(1));
        }
        else {
            nav_email.setText(getString(R.string.login_request));
            nav_user.setText(getString(R.string.hi));
        }
    }

    public void logoutAction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(R.string.logout);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mAuth.signOut();
                startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).show();
    }
    public void fragmentInflate(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_page_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected int getToolbarID() {
        return R.id.toolbar;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void showErrorSnackbar() {

        networkChangeReceiver = new NetworkChangeReceiver() {

            Snackbar snackbar = null;

            @Override
            protected void dismissSnackbar() {

                if (snackbar != null) {

                    // dismiss snackbar
                    snackbar.dismiss();
                }
            }

            @Override
            protected void setUpLayout() {

                frameLayout = findViewById(R.id.home_page_frame_layout);

                // check if no internet connection
                if (!NetworkUtil.getConnectivityStatus(HomePageActivity.this)) {

                    Log.e("ERRROR", "");

                    snackbar = Snackbar.make(frameLayout, R.string.error_connection, Snackbar.LENGTH_INDEFINITE);
                    snackbar.setActionTextColor(Color.CYAN);
                    snackbar.setAction(R.string.connection_restore, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                        }
                    });

                    final View snackBarView = snackbar.getView();
                    final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView.getLayoutParams();

                    params.setMargins(params.leftMargin,
                            params.topMargin,
                            params.rightMargin,
                            (int) (params.bottomMargin + getResources().getDimension(R.dimen.snackbar_margin)));

                    snackBarView.setLayoutParams(params);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        snackBarView.setElevation(0);
                    }

                    snackbar.show();
                }
            }
        };
    }
}