package gov.cipam.gi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import gov.cipam.gi.R;
import gov.cipam.gi.adapters.ProductFirebaseAdapter;
import gov.cipam.gi.model.Product;
import gov.cipam.gi.utils.RecyclerViewClickListener;
import gov.cipam.gi.utils.RecyclerViewTouchListener;
import gov.cipam.gi.viewholder.ProductViewHolder;

public class ProductListActivity extends BaseActivity implements RecyclerViewClickListener {
    RecyclerView productListRecycler;
    DatabaseReference mDatabaseProduct;
    ProductFirebaseAdapter productFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpToolbar(this);
        mDatabaseProduct = FirebaseDatabase.getInstance().getReference("Giproducts");
        productListRecycler=findViewById(R.id.product_list_recycler_view);
        productFirebaseAdapter=new ProductFirebaseAdapter(this,Product.class,R.layout.card_view_product_list,ProductViewHolder.class,mDatabaseProduct);

        productListRecycler.setAdapter(productFirebaseAdapter);
        productListRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        productListRecycler.addOnItemTouchListener(new RecyclerViewTouchListener(this,productListRecycler,this));

    }

    @Override
    protected int getToolbarID() {
        return R.id.product_list_toolbar;
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent=new Intent(this,ProductDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
