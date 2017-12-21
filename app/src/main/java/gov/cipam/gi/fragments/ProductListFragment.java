package gov.cipam.gi.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import gov.cipam.gi.R;
import gov.cipam.gi.activities.ProductDetailActivity;
import gov.cipam.gi.adapters.ProductFirebaseAdapter;
import gov.cipam.gi.manager.ListRetriever;
import gov.cipam.gi.model.Product;
import gov.cipam.gi.utils.DetailsTransition;
import gov.cipam.gi.utils.ListDataProgressListener;
import gov.cipam.gi.utils.RecyclerViewClickListener;
import gov.cipam.gi.utils.RecyclerViewTouchListener;
import gov.cipam.gi.viewholder.ProductViewHolder;

/**
 * Created by karan on 12/14/2017.
 */

public class ProductListFragment extends Fragment implements RecyclerViewClickListener,ListDataProgressListener {

    RecyclerView                productListRecycler;
    DatabaseReference           mDatabaseProduct;
    ProductFirebaseAdapter      productFirebaseAdapter;
    ProgressBar                 progressBar;
    FirebaseAuth                mAuth;

    public ProductListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        ListRetriever listRetriever = new ListRetriever(this);
        Query query = listRetriever.fetchAllData();
        mDatabaseProduct = FirebaseDatabase.getInstance().getReference("Giproducts");
        this.productFirebaseAdapter = new ProductFirebaseAdapter(getContext(), Product.class, R.layout.card_view_product_list, ProductViewHolder.class, query,listRetriever);
        super.onCreate(savedInstanceState);
        //productListRecycler.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productListRecycler=view.findViewById(R.id.product_list_recycler_view);
        progressBar=view.findViewById(R.id.progressBar);
        productListRecycler.setAdapter(productFirebaseAdapter);
        productListRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        productListRecycler.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(),productListRecycler,this));
    }

    @Override
    public void onClick(View view, int position) {
            ProductDetailFragment productDetailActivity =new ProductDetailFragment();

        Intent intent=new Intent(getContext(),ProductDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onListDataFetchStarted() {
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListDataLoaded() {
        //progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onListDataLoadingCancelled() {
        //progressBar.setVisibility(View.GONE);
    }
}
