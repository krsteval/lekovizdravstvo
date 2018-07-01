package com.tadi.lekovizdravstvomk.fragments;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.tadi.lekovizdravstvomk.model.MonitoringDatabase;
import com.tadi.lekovizdravstvomk.model.WayOfPublishing;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteDrugsFragment extends BaseFragment {

    private static final String TAG = FavouriteDrugsFragment.class.getSimpleName();

    private FirebaseDatabase mFirebaseInstance;

    private List<WayOfPublishing> wayOfPublishingList;
    private static List<Drug> drugsList;
    private List<Drug> tempDrugList;
    private DrugsAdapter mAdapter;
    public ArrayList<String> itemFilters;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.loader)
    ProgressBar loader;

    @BindView(R.id.list_empty)
    TextView emptyList;

    public FavouriteDrugsFragment() {
        // Required empty public constructor
    }
    @Override
    public void setArguments(Bundle args) {
//        drugsList = args.getParcelable("data_item");
        super.setArguments(args);
    }
    public static FavouriteDrugsFragment newInstance(List<Drug> dataItem) {
        FavouriteDrugsFragment fragment = new FavouriteDrugsFragment();
        Bundle b = new Bundle();
        FavouriteDrugsFragment.drugsList = dataItem;
        b.putParcelableArrayList("data_item", (ArrayList<? extends Parcelable>) dataItem);
        fragment.setArguments(b);
        return fragment;
    }

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_drugs_register, container, false);
        unbinder = ButterKnife.bind(this, view);

        initComponents();
        setDataFromServer();

        handleIntent(getActivity().getIntent());
        return view;
    }

    private void initComponents() {
        itemFilters = new ArrayList<>();
        loader.setVisibility(View.VISIBLE);


    }

    private void setDataFromServer() {

        mAdapter = new DrugsAdapter(drugsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        loader.setVisibility(View.GONE);

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
        if(rezDrugList.size()==0)
            emptyList.setVisibility(View.VISIBLE);
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
