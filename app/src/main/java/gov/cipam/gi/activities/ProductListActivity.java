package gov.cipam.gi.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import gov.cipam.gi.R;
import gov.cipam.gi.model.States;

import gov.cipam.gi.utils.RecyclerViewClickListener;
import gov.cipam.gi.utils.RecyclerViewTouchListener;
import gov.cipam.gi.viewholder.ProductViewHolder;

public class ProductListActivity extends BaseActivity implements RecyclerViewClickListener {
    RecyclerView productListRecycler;
    DatabaseReference mDatabaseState;
    FirebaseRecyclerAdapter<States,ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpToolbar(this);
        mDatabaseState = FirebaseDatabase.getInstance().getReference("States");
        productListRecycler=findViewById(R.id.product_list_recycler_view);
        productListRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        productListRecycler.addOnItemTouchListener(new RecyclerViewTouchListener(this,productListRecycler,this));
        loadStates();
    }
    private void loadStates() {
        adapter = new FirebaseRecyclerAdapter<States,ProductViewHolder>(States.class,R.layout.card_view_product_list,ProductViewHolder.class,mDatabaseState) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, final States model, int position) {
                viewHolder.mTitle.setText(model.getName());
                viewHolder.mFiller.setText(model.getName());
                final String uri =model.getDpurl();
                Picasso.with(ProductListActivity.this)
                        .load(uri)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.imageView);
                final States clickitem = model;
            }
        };
        productListRecycler.setAdapter(adapter);
    }

    @Override
    protected int getToolbarID() {
        return R.id.product_list_toolbar;
    }

    @Override
    public void onClick(View view, int position) {
        startActivity(new Intent(this,ProductDetailActivity.class));
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
