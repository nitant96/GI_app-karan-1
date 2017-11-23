package gov.cipam.gi.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gov.cipam.gi.R;

/**
 * Created by karan on 11/22/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView mTitle,mFiller,mState;
    public ImageView imageView;

    public ProductViewHolder(View itemView) {
        super(itemView);

        mTitle=itemView.findViewById(R.id.card_view_title);
        mFiller=itemView.findViewById(R.id.card_view_filler);
        mState=itemView.findViewById(R.id.card_view_state);
        imageView=itemView.findViewById(R.id.card_view_image);
    }

    public void setTitle(String title){
        mTitle.setText(title);
    }
    public void setFiller(String filler){
        mFiller.setText(filler);
    }
    public void setState(String state){
        mState.setText(state);
    }
}
