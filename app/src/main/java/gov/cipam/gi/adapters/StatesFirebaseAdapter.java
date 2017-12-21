package gov.cipam.gi.adapters;

/**
 * Created by karan on 11/26/2017.
 */

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import gov.cipam.gi.model.States;
import gov.cipam.gi.viewholder.StateViewHolder;

public class StatesFirebaseAdapter extends FirebaseRecyclerAdapter<States, StateViewHolder> {
    private DatabaseReference   mRef;
    private Context             mContext;

    public StatesFirebaseAdapter(Context context, Class<States> modelClass, int modelLayout,
                                 Class<StateViewHolder> viewHolderClass,
                                 Query ref) {

        super(modelClass, modelLayout, viewHolderClass, ref);

        mRef = ref.getRef();
        mContext = context;
    }

    @Override
    protected void populateViewHolder(final StateViewHolder viewHolder, final States model, final int position) {
        viewHolder.bindStateDetails(model);
    }

   /* @Override
    public void cleanup() {
        super.cleanup();
        mRef.removeEventListener(mChildEventListener);
    }*/
    @Override
    public void onCancelled(DatabaseError error) {
        super.onCancelled(error);
    }
}