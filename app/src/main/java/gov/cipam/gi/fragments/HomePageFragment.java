package gov.cipam.gi.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import gov.cipam.gi.R;
import gov.cipam.gi.activities.ProductListActivity;
import gov.cipam.gi.adapters.CategoryFirebaseAdapter;
import gov.cipam.gi.adapters.StatesFirebaseAdapter;
import gov.cipam.gi.utils.RecyclerViewClickListener;
import gov.cipam.gi.utils.RecyclerViewTouchListener;
import gov.cipam.gi.viewholder.CategoryViewHolder;
import gov.cipam.gi.viewholder.StateViewHolder;
import gov.cipam.gi.adapters.ImageViewPageAdapter;
import gov.cipam.gi.model.Categories;
import gov.cipam.gi.model.States;

/**
 * Created by karan on 11/20/2017.
 */

public class HomePageFragment extends Fragment implements RecyclerViewClickListener {

    RecyclerView                rvState,rvCategory;
    ScrollView                  scrollView;
    AutoScrollViewPager         autoScrollViewPager;
    RecyclerView.LayoutManager  layoutManager,layoutManager2;
    StatesFirebaseAdapter       statesFirebaseAdapter;
    CategoryFirebaseAdapter     categoryFirebaseAdapter;
    DatabaseReference           mDatabaseState,mDatabaseCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_homepage, container, false);

        rvState =  view.findViewById(R.id.recycler_states);
        rvCategory =  view.findViewById(R.id.recycler_categories);
        autoScrollViewPager = view.findViewById(R.id.viewpager);
        scrollView=view.findViewById(R.id.scroll_view_home);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabaseState = FirebaseDatabase.getInstance().getReference("States");
        mDatabaseCategory = FirebaseDatabase.getInstance().getReference("Categories");
        statesFirebaseAdapter=new StatesFirebaseAdapter(getContext(),States.class,R.layout.card_view_state_item,StateViewHolder.class,mDatabaseState);
        categoryFirebaseAdapter=new CategoryFirebaseAdapter(getContext(),Categories.class,R.layout.card_view_category_item,CategoryViewHolder.class,mDatabaseCategory);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView.setSmoothScrollingEnabled(true);

        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvState.setLayoutManager(layoutManager);
        rvCategory.setLayoutManager(layoutManager2);
        rvState.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(),rvState,this));
        rvState.setAdapter(statesFirebaseAdapter);
        rvCategory.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(),rvCategory,this));
        rvCategory.setAdapter(categoryFirebaseAdapter);
        setAutoScroll();
    }

    @Override
    public void onStart() {
        super.onStart();



    }

    private void setAutoScroll() {
        autoScrollViewPager.setAdapter(new ImageViewPageAdapter(getContext()));
        autoScrollViewPager.getSlideBorderMode();
        autoScrollViewPager.isStopScrollWhenTouch();
        autoScrollViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setScrollDurationFactor(5);
        autoScrollViewPager.setInterval(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
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


    @Override
    public void onClick(View view, int position) {
        startActivity(new Intent(getContext(), ProductListActivity.class));
    }

    @Override
    public void onLongClick(View view, int position) {

    }

}
