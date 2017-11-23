package gov.cipam.gi.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import gov.cipam.gi.R;
import gov.cipam.gi.activities.ProductListActivity;
import gov.cipam.gi.viewholder.CategoryViewHolder;
import gov.cipam.gi.viewholder.StateViewHolder;
import gov.cipam.gi.adapters.ViewPageAdapter;
import gov.cipam.gi.model.Categories;
import gov.cipam.gi.model.States;
import gov.cipam.gi.utils.ItemClickListener;

/**
 * Created by karan on 11/20/2017.
 */

public class HomePage extends Fragment {

    RecyclerView rvState,rvCategory;
    ScrollView scrollView;
    AutoScrollViewPager autoScrollViewPager;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    FirebaseRecyclerAdapter<States,StateViewHolder> adapter;
    FirebaseRecyclerAdapter<Categories,CategoryViewHolder>adapter2;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabaseState,mDatabaseCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mDatabaseState = FirebaseDatabase.getInstance().getReference("States");
        mDatabaseCategory = FirebaseDatabase.getInstance().getReference("Categories");

        View view= inflater.inflate(R.layout.fragment_homepage, container, false);
        rvState =  view.findViewById(R.id.recycler_states);
        rvCategory =  view.findViewById(R.id.recycler_categories);
        autoScrollViewPager = view.findViewById(R.id.viewpager);
        scrollView=view.findViewById(R.id.scroll_view_home);

        scrollView.setSmoothScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
        layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
        rvState.setLayoutManager(layoutManager);
        rvCategory.setLayoutManager(layoutManager2);

        LoadStates();
        LoadCategories();
        setAutoScroll();
        return view;
    }

    private void LoadStates() {
        adapter = new FirebaseRecyclerAdapter<States, StateViewHolder>(States.class,R.layout.card_view_state_item,StateViewHolder.class,mDatabaseState) {
            @Override
            protected void populateViewHolder(StateViewHolder viewHolder, final States model, int position) {
                viewHolder.mName.setText(model.getName());
                final Uri uri =Uri.parse(model.getDpurl());
                Picasso.with(getContext())
                        .load(uri)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.mDp);
                final States clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getContext(),model.getName(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), ProductListActivity.class));
                    }
                });

            }
        };
        rvState.setAdapter(adapter);

    }

    private void LoadCategories() {
        adapter2 = new FirebaseRecyclerAdapter<Categories,CategoryViewHolder>(Categories.class,R.layout.card_view_category_item,CategoryViewHolder.class,mDatabaseCategory) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Categories model, int position) {
                viewHolder.mName.setText(model.getName());
                final Uri uri =Uri.parse(model.getDpurl());
                Picasso.with(getContext())
                        .load(uri)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.mDp);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getContext(),model.getName(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        rvCategory.setAdapter(adapter2);
    }

    private void setAutoScroll() {
        autoScrollViewPager.setAdapter(new ViewPageAdapter(getContext()));
        autoScrollViewPager.getSlideBorderMode();
        autoScrollViewPager.isStopScrollWhenTouch();
        autoScrollViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setScrollDurationFactor(5);
        autoScrollViewPager.setStopScrollWhenTouch(true);
        autoScrollViewPager.setInterval(3000);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
