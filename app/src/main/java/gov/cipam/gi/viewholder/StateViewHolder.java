package gov.cipam.gi.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gov.cipam.gi.model.States;
import gov.cipam.gi.R;

/**
 * Created by Deepak on 11/18/2017.
 */

public class StateViewHolder extends RecyclerView.ViewHolder{
    private TextView mName;
    private ImageView mDp;



    public StateViewHolder(View itemView) {
        super(itemView);

        mName =itemView.findViewById(R.id.card_name_state);
        mDp =itemView.findViewById(R.id.card_dp_state);
    }

    public void bindStateDetails(States states){
        mName.setText(states.getName());
        Picasso.with(itemView.getContext())
                .load(states.getDpurl())
                .placeholder(R.drawable.image)
                .into(mDp);
    }
}
