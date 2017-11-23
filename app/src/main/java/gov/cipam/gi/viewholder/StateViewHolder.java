package gov.cipam.gi.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import gov.cipam.gi.utils.ItemClickListener;
import gov.cipam.gi.R;

/**
 * Created by Deepak on 11/18/2017.
 */

public class StateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mName;
    public ImageView mDp;

    private ItemClickListener itemClickListener;


    public StateViewHolder(View itemView) {
        super(itemView);

        mName =itemView.findViewById(R.id.card_name);
        mDp =itemView.findViewById(R.id.card_dp);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
