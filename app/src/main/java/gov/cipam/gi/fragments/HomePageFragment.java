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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import gov.cipam.gi.R;
import gov.cipam.gi.activities.HomePageActivity;
import gov.cipam.gi.activities.ProductListActivity;

import gov.cipam.gi.adapters.CategoryAdapter;
import gov.cipam.gi.adapters.StatesAdapter;
import gov.cipam.gi.utils.RecyclerViewClickListener;
import gov.cipam.gi.utils.RecyclerViewTouchListener;
import gov.cipam.gi.adapters.ImageViewPageAdapter;
import gov.cipam.gi.model.Categories;
import gov.cipam.gi.model.States;

/**
 * Created by karan on 11/20/2017.
 */

public class HomePageFragment extends Fragment implements RecyclerViewClickListener, CategoryAdapter.setOnCategoryClickListener, StatesAdapter.setOnStateClickedListener {

    RecyclerView rvState,rvCategory;
    ScrollView scrollView;
    AutoScrollViewPager autoScrollViewPager;
    RecyclerView.LayoutManager layoutManager,layoutManager2;

    public static StatesAdapter statesAdapter;
    public static CategoryAdapter categoryAdapter;

    FirebaseAuth mAuth;
    DatabaseReference mDatabaseState,mDatabaseCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseState = FirebaseDatabase.getInstance().getReference("States");
        mDatabaseCategory = FirebaseDatabase.getInstance().getReference("Categories");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        rvState =  view.findViewById(R.id.recycler_states);
        rvCategory =  view.findViewById(R.id.recycler_categories);
        autoScrollViewPager = view.findViewById(R.id.viewpager);
        scrollView=view.findViewById(R.id.scroll_view_home);
        scrollView.setSmoothScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvState.setLayoutManager(layoutManager);
//        rvState.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(),rvState,this));
        rvCategory.setLayoutManager(layoutManager2);
//        rvCategory.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(),rvCategory,this));

        categoryAdapter=new CategoryAdapter(HomePageActivity.mDisplayCategoryList,getContext(),this);
        statesAdapter=new StatesAdapter(this,HomePageActivity.mDisplayStateList,getContext());

        rvState.setAdapter(statesAdapter);
        rvCategory.setAdapter(categoryAdapter);
        setAutoScroll();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setAutoScroll() {
        autoScrollViewPager.setAdapter(new ImageViewPageAdapter(getContext()));
        autoScrollViewPager.getSlideBorderMode();
        autoScrollViewPager.isStopScrollWhenTouch();
        autoScrollViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setScrollDurationFactor(5);
        autoScrollViewPager.setStopScrollWhenTouch(true);
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

    @Override
    public void onCategoryClicked(View view, int position) {
        Intent intent=new Intent(getContext(),ProductListActivity.class);
        intent.putExtra("type","category");
        intent.putExtra("value",HomePageActivity.mDisplayCategoryList.get(position).getName());
        startActivity(intent);
    }

    @Override
    public void onStateClickedListener(View view, int position) {
        Intent intent=new Intent(getContext(),ProductListActivity.class);
        intent.putExtra("type","state");
        intent.putExtra("value",HomePageActivity.mDisplayStateList.get(position).getName());
        startActivity(intent);
    }
}
