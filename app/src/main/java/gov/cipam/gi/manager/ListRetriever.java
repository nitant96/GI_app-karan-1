package gov.cipam.gi.manager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import gov.cipam.gi.model.Product;
import gov.cipam.gi.utils.ListDataProgressListener;

public class ListRetriever implements ChildEventListener, ValueEventListener {

    private ListDataProgressListener listDataProgressListener;

    public ListRetriever(ListDataProgressListener listDataProgressListener) {
        this.listDataProgressListener = listDataProgressListener;
        this.listDataProgressListener.onListDataFetchStarted();
    }

    public Query fetchAllData() {
        listDataProgressListener.onListDataFetchStarted();

        Query query = FirebaseDatabase.getInstance()
                .getReference("Giproducts");

        query.addChildEventListener(this);
        query.addListenerForSingleValueEvent(this);

        return query;
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        //convert dataSnapshot into POJO
        Product product = dataSnapshot.getValue(Product.class);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        this.listDataProgressListener.onListDataLoaded();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        this.listDataProgressListener.onListDataLoadingCancelled();
    }
}
