package com.tadi.lekovizdravstvomk.fragments;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.adapter.DrugsAdapter;
import com.tadi.lekovizdravstvomk.adapter.DrugsFilterAdapter;
import com.tadi.lekovizdravstvomk.model.Drug;
import com.tadi.lekovizdravstvomk.model.WayOfPublishing;
import com.yalantis.filter.listener.FilterListener;
import com.yalantis.filter.widget.Filter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugsRegisterFragment extends BaseFragment {

    private static final String TAG = DrugsRegisterFragment.class.getSimpleName();

    private FirebaseDatabase mFirebaseInstance;

    private List<WayOfPublishing> wayOfPublishingList;
    private List<Drug> drugsList;
    private List<Drug> tempDrugList;
    private DrugsAdapter mAdapter;
    public ArrayList<String> itemFilters;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.loader)
    ProgressBar loader;

    public DrugsRegisterFragment() {
        // Required empty public constructor
    }


    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugs_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        initComponents();
        setDataFromServer();

        handleIntent(getActivity().getIntent());
        return view;
    }

    private void initComponents() {
        itemFilters = new ArrayList<>();
        loader.setVisibility(View.VISIBLE);
        drugsList = new ArrayList<>();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseInstance.getReference("drugsregister").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App dataSnapshot updated");
                drugsList.clear();
                try {

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        drugsList.add(userSnapshot.getValue(Drug.class));
                    }

                } catch (Exception ex){
                    Log.w("Exception", ex.toString());
                }
                tempDrugList = new ArrayList<>();
                tempDrugList.addAll(drugsList);
                loader.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();


                mFirebaseInstance.getReference("nacin_na_izdavanje").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        wayOfPublishingList = new ArrayList<>();
                        try {

                            for (DataSnapshot wayOfPublishingSnapshot : dataSnapshot.getChildren()) {
                                wayOfPublishingList.add(wayOfPublishingSnapshot.getValue(WayOfPublishing.class));
                            }
                            Filter mFilter = (Filter<WayOfPublishing>) getView().findViewById(R.id.filter);
                            mFilter.setAdapter(new DrugsFilterAdapter(wayOfPublishingList, getContext()));
                            mFilter.setListener(mListener);

                            //the text to show when there's no selected items
                            mFilter.setNoSelectedItemText("");
                            mFilter.build();
                        } catch (Exception ex){
                            Log.w("Exception", ex.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.e(TAG, "Failed to read app title value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });



    }

    private void setDataFromServer() {

        mAdapter = new DrugsAdapter(drugsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean skipBack() {
        return true;
    }

    public void clearSearch(){
        drugsList.clear();
        drugsList.addAll(tempDrugList);
        mAdapter.notifyDataSetChanged();
    }

    public void searchItems(String query){
        drugsList.clear();
        drugsList.addAll(tempDrugList);
        ArrayList<Drug> rezDrugList = new ArrayList<>();
        for (Drug item: drugsList) {
            if(item.getLatinicno_ime().toLowerCase().contains(query.toLowerCase()) || item.getFarmacevska_forma().toLowerCase().contains(query.toLowerCase()))
                rezDrugList.add(item);
        }
        drugsList.clear();
        drugsList.addAll(rezDrugList);
        mAdapter.notifyDataSetChanged();
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);


            for (Drug item: drugsList) {
                if(!item.getLatinicno_ime().toLowerCase().contains(query.toLowerCase()) && !item.getFarmacevska_forma().toLowerCase().contains(query.toLowerCase()))
                    drugsList.remove(item);
            }
            mAdapter.notifyDataSetChanged();

        }

    }

    public void shangeView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private final SortedList.Callback<Drug> mCallback = new SortedList.Callback<Drug>() {

        @Override
        public void onInserted(int position, int count) {
            mAdapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            mAdapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public int compare(Drug o1, Drug o2) {
            return 0;
        }

        @Override
        public void onChanged(int position, int count) {
            mAdapter.notifyItemRangeChanged(position, count);
        }


        @Override
        public boolean areContentsTheSame(Drug oldItem, Drug newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Drug item1, Drug item2) {
            return item1.getId() == item2.getId();
        }
    };

    private FilterListener<WayOfPublishing> mListener = new FilterListener<WayOfPublishing>() {

        @Override
        public void onFilterDeselected(WayOfPublishing wayOfPublishing) {
            for (int i = 0; i < itemFilters.size(); i++) {
                if(itemFilters.get(i).equalsIgnoreCase(wayOfPublishing.getOpis())){
                    itemFilters.remove(i);
                    break;
                }
            }
            fiterByCategory();
        }

        @Override
        public void onFilterSelected(WayOfPublishing wayOfPublishing) {
            itemFilters.add(wayOfPublishing.getOpis());
            fiterByCategory();
        }

        @Override
        public void onFiltersSelected(@NotNull ArrayList<WayOfPublishing> filters) {

        }

        @Override
        public void onNothingSelected() {

        }



    };

    private void fiterByCategory() {
        drugsList.clear();
        drugsList.addAll(tempDrugList);
        ArrayList<Drug> rezDrugList = new ArrayList<>();
        if(itemFilters.size()==0){

            rezDrugList.addAll(tempDrugList);
        }
        else {

            for (Drug item: drugsList) {
                for (int i = 0; i < itemFilters.size(); i++) {
                    if(item.getNacin_izdavanje().equalsIgnoreCase(itemFilters.get(i)))
                        rezDrugList.add(item);
                }
            }
        }
        drugsList.clear();
        drugsList.addAll(rezDrugList);
        mAdapter.notifyDataSetChanged();
    }
}
