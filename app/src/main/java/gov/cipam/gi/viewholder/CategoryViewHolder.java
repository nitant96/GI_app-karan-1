package gov.cipam.gi.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gov.cipam.gi.model.Categories;
import gov.cipam.gi.utils.ItemClickListener;
import gov.cipam.gi.R;

/**
 * Created by Deepak on 11/18/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    public TextView mName;
    public ImageView mDp;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        mName =itemView.findViewById(R.id.card_name_category);
        mDp =itemView.findViewById(R.id.card_dp_category);
    }
    public void bindCategoryDetails(Categories categories){
        mName.setText(categories.getName());
        Picasso.with(itemView.getContext())
                .load(categories.getDpurl())
                .placeholder(R.drawable.place_holder)
                .into(mDp);
    }
}
