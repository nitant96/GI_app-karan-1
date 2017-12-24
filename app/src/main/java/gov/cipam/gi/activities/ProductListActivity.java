package gov.cipam.gi.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import gov.cipam.gi.R;
import gov.cipam.gi.database.Database;
import gov.cipam.gi.fragments.ProductListFragment;
import gov.cipam.gi.model.Product;

public class ProductListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String         LOG_TAG ="Refresh" ;
    ProductListFragment                 productListFragment;
    SwipeRefreshLayout                  swipeRefreshLayout;

    Database databaseInstance;
    SQLiteDatabase database;

    public static ArrayList<Product> subGIList=new ArrayList<>();

    public static String type,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpToolbar(this);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        value=intent.getStringExtra("value");

        databaseInstance = new Database(this);
        database = databaseInstance.getReadableDatabase();

        fetchGIFromDB();

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        productListFragment=new ProductListFragment();

        if(savedInstanceState==null){
            fragmentInflate(productListFragment);
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.md_red_500,R.color.md_blue_500,R.color.md_green_500);

    }

    private void fetchGIFromDB() {
        subGIList.clear();
        Cursor cursor;
        String[] s={value};
        if(type.equals("state")){
            cursor=database.query(Database.GI_PRODUCT_TABLE,null,Database.GI_PRODUCT_STATE+"=?",s,null,null,null);
        }
        else{
            cursor=database.query(Database.GI_PRODUCT_TABLE,null,Database.GI_PRODUCT_CATEGORY+"=?",s,null,null,null);
        }

        while (cursor.moveToNext()){
            String name,detail,category,state,dpurl,uid;

            name=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_NAME));
            detail=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_DETAIL));
            category=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_CATEGORY));
            state=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_STATE));
            dpurl=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_DP_URL));
            uid=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_UID));

            Product oneGI=new Product(name,dpurl,detail,category,state,uid);
            subGIList.add(oneGI);
        }

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
