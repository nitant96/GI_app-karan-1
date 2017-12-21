package gov.cipam.gi.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import gov.cipam.gi.model.Categories;
import gov.cipam.gi.R;

/**
 * Created by Deepak on 11/18/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    private TextView    mName;
    public ImageView    mDp;
    View view;
    private ProgressBar progressBar;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        mName =itemView.findViewById(R.id.card_name_category);
        mDp =itemView.findViewById(R.id.card_dp_category);
        view=itemView.findViewById(R.id.categoryItemView);
        progressBar=itemView.findViewById(R.id.progressBarCategory);
    }
    public void bindCategoryDetails(Categories categories){

        progressBar.setVisibility(View.VISIBLE);

        mName.setText(categories.getName());
        Picasso.with(itemView.getContext())
                .load(categories.getDpurl())
                .into(mDp, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        mDp.setImageResource(R.drawable.image);
                    }
                });
    }
}
