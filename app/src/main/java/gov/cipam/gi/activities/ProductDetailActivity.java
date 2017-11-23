package gov.cipam.gi.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import gov.cipam.gi.R;

public class ProductDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setUpToolbar(this);
    }

    @Override
    protected int getToolbarID() {
        return R.id.detail_activity_toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_details_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id =item.getItemId();

        switch (id){
            case R.id.action_save_for_later:
                Toast.makeText(this,R.string.save_for_later,Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_url:
                Toast.makeText(this,R.string.url,Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
