package gov.cipam.gi.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import gov.cipam.gi.R;
import gov.cipam.gi.activities.ProductDetailActivity;
import gov.cipam.gi.adapters.SellerFirebaseAdapter;
import gov.cipam.gi.adapters.SellerListAdapter;
import gov.cipam.gi.database.Database;
import gov.cipam.gi.model.Seller;
import gov.cipam.gi.viewholder.SellerViewHolder;

/**
 * Created by karan on 12/14/2017.
 */

public class ProductDetailFragment extends Fragment implements SellerListAdapter.setOnSellerClickListener {
//    Seller seller;
//    SellerFirebaseAdapter sellerFirebaseAdapter;
//    DatabaseReference mDatabaseReference;

    RecyclerView sellerRecyclerView;
    ArrayList<Seller> sellerList;
    SellerListAdapter sellerListAdapter;
    Database databaseInstance;
    SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sellerList=new ArrayList<>();
        databaseInstance = new Database(getContext());
        database = databaseInstance.getReadableDatabase();
        populateSellerListFrommDB();

        sellerListAdapter=new SellerListAdapter(getContext(),sellerList,this);
//        seller=new Seller();
//        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Giproducts").child("agrPalakkadanMattaRice").child("seller");
//        sellerFirebaseAdapter= new SellerFirebaseAdapter(getContext(),Seller.class,R.layout.card_view_seller_item, SellerViewHolder.class,mDatabaseReference);

        super.onCreate(savedInstanceState);
    }

    private void populateSellerListFrommDB() {

        String[] s={ProductDetailActivity.currentProduct.getUid()};
        Cursor sellerCursor=database.query(Database.GI_SELLER_TABLE,null,Database.GI_SELLER_UID+"=?",s,null,null,null,null);

        while(sellerCursor.moveToNext()){
            String name,address,contact;
            Double lon,lat;

            name=sellerCursor.getString(sellerCursor.getColumnIndex(Database.GI_SELLER_NAME));
            address=sellerCursor.getString(sellerCursor.getColumnIndex(Database.GI_SELLER_ADDRESS));
            contact=sellerCursor.getString(sellerCursor.getColumnIndex(Database.GI_SELLER_CONTACT));
            lat=sellerCursor.getDouble(sellerCursor.getColumnIndex(Database.GI_SELLER_LAT));
            lon=sellerCursor.getDouble(sellerCursor.getColumnIndex(Database.GI_SELLER_LON));

            Seller oneSeller=new Seller(name,address,contact,lon,lat);
            sellerList.add(oneSeller);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sellerRecyclerView= view.findViewById(R.id.seller_recycler_view);
        sellerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        sellerRecyclerView.setAdapter(sellerFirebaseAdapter);
        sellerRecyclerView.setAdapter(sellerListAdapter);
        sellerRecyclerView.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        ExpandableTextView expTv1 = view.findViewById(R.id.expand_text_view);
        expTv1.setText(ProductDetailActivity.currentProduct.getDetail());
    }

    @Override
    public void onSellerClicked(View v, int Position) {

    }
}
