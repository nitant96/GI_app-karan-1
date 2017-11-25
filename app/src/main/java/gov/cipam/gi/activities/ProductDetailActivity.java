package gov.cipam.gi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gov.cipam.gi.R;
import gov.cipam.gi.adapters.SellerFirebaseAdapter;
import gov.cipam.gi.model.Seller;
import gov.cipam.gi.viewholder.SellerViewHolder;

public class ProductDetailActivity extends BaseActivity {

    RecyclerView sellerRecyclerView;
    SellerFirebaseAdapter sellerFirebaseAdapter;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Giproducts").child("agrPalakkadanMattaRice").child("seller");
        sellerRecyclerView= findViewById(R.id.seller_recycler_view);
        sellerFirebaseAdapter= new SellerFirebaseAdapter(this,Seller.class,R.layout.card_view_seller_item, SellerViewHolder.class,mDatabaseReference);
        sellerRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        sellerRecyclerView.setAdapter(sellerFirebaseAdapter);

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
