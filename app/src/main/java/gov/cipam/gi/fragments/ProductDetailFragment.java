package gov.cipam.gi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.DividerItemDecoration;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import gov.cipam.gi.R;
import gov.cipam.gi.adapters.SellerFirebaseAdapter;
import gov.cipam.gi.model.Seller;
import gov.cipam.gi.viewholder.SellerViewHolder;

/**
 * Created by karan on 12/14/2017.
 */

public class ProductDetailFragment extends Fragment {
    Seller                  seller;
    RecyclerView            sellerRecyclerView;
    SellerFirebaseAdapter   sellerFirebaseAdapter;
    DatabaseReference       mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        seller=new Seller();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Giproducts").child("agrPalakkadanMattaRice").child("seller");
        sellerFirebaseAdapter= new SellerFirebaseAdapter(getContext(),Seller.class,R.layout.card_view_seller_item, SellerViewHolder.class,mDatabaseReference);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sellerRecyclerView= view.findViewById(R.id.seller_recycler_view);
        sellerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        sellerRecyclerView.setAdapter(sellerFirebaseAdapter);
        sellerRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        ExpandableTextView expTv1 = view.findViewById(R.id.expand_text_view);
        expTv1.setText(getString(R.string.long_text));
    }
}
