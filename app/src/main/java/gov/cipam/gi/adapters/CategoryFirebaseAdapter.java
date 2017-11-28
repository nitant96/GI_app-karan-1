package gov.cipam.gi.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import gov.cipam.gi.model.Categories;
import gov.cipam.gi.viewholder.CategoryViewHolder;

import static gov.cipam.gi.utils.Constants.KEY_PRODUCT_DETAILS;
import static gov.cipam.gi.utils.Constants.POSITION;

/**
 * Created by karan on 11/26/2017.
 */

public class CategoryFirebaseAdapter extends FirebaseRecyclerAdapter<Categories,CategoryViewHolder> {

    private DatabaseReference mRef;
    private Context mContext;

    public CategoryFirebaseAdapter(Context context, Class<Categories> modelClass, int modelLayout,
                                 Class<CategoryViewHolder> viewHolderClass,
                                 Query ref) {

        super(modelClass, modelLayout, viewHolderClass, ref);

        mRef = ref.getRef();
        mContext = context;
    }

    @Override
    protected void populateViewHolder(CategoryViewHolder viewHolder, final Categories model,final int position) {
    viewHolder.bindCategoryDetails(model);

    }
}

