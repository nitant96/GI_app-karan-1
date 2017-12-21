package gov.cipam.gi.viewholder;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gov.cipam.gi.R;
import gov.cipam.gi.model.Product;

/**
 * Created by karan on 11/22/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private TextView    mTitle,mFiller,mCategory,mState;
    private ImageView   imageView;

    public ProductViewHolder(View itemView) {
        super(itemView);
        mTitle=itemView.findViewById(R.id.card_view_title);
        mFiller=itemView.findViewById(R.id.card_view_filler);
        mCategory=itemView.findViewById(R.id.card_view_category);
        mState=itemView.findViewById(R.id.card_view_state1);
        imageView=itemView.findViewById(R.id.card_view_image);
    }
    public void bindProductDetails(Product product) {
        mTitle.setText(product.getName());
        mFiller.setText(product.getDetail());
        mCategory.setText(product.getCategory());
        mState.setText(product.getState());

        Picasso.with(itemView.getContext())
                .load(product.getDpurl())
                .fit()
                .placeholder(R.drawable.image)
                .into(imageView);
    }

}
