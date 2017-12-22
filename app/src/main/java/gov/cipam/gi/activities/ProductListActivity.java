package gov.cipam.gi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import gov.cipam.gi.R;
import gov.cipam.gi.fragments.ProductListFragment;

public class ProductListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String         LOG_TAG ="Refresh" ;
    ProductListFragment                 productListFragment;
    SwipeRefreshLayout                  swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpToolbar(this);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        productListFragment=new ProductListFragment();

        if(savedInstanceState==null){
            fragmentInflate(productListFragment);
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.md_red_500,R.color.md_blue_500,R.color.md_green_500);

    }

    @Override
    protected int getToolbarID() {
        return R.id.product_list_toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case R.id.action_settings_product_list:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            case R.id.swipeRefreshLayout:
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fragmentInflate(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.product_list_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRefresh() {
        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
        swipeRefreshLayout.setRefreshing(false);
    }
}
