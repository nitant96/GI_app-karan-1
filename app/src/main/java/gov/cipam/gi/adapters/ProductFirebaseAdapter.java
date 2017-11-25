package gov.cipam.gi.adapters;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import gov.cipam.gi.model.Product;
import gov.cipam.gi.model.States;
import gov.cipam.gi.viewholder.ProductViewHolder;
import gov.cipam.gi.viewholder.StateViewHolder;

/**
 * Created by karan on 11/26/2017.
 */

public class ProductFirebaseAdapter extends FirebaseRecyclerAdapter<Product,ProductViewHolder> {

    private DatabaseReference mRef;
    private Context mContext;

    public ProductFirebaseAdapter(Context context, Class<Product> modelClass, int modelLayout,
                                 Class<ProductViewHolder> viewHolderClass,
                                 Query ref) {

        super(modelClass, modelLayout, viewHolderClass, ref);

        mRef = ref.getRef();
        mContext = context;
    }

    @Override
    protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
        viewHolder.bindProductDetails(model);
    }
}
