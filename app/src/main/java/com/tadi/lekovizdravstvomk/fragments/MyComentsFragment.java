package com.tadi.lekovizdravstvomk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.adapter.CommentAdapter;
import com.tadi.lekovizdravstvomk.helpers.Common;
import com.tadi.lekovizdravstvomk.model.Drug;
import com.tadi.lekovizdravstvomk.model.ReviewForDrugs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyComentsFragment extends BaseFragment {

    private Unbinder unbinder;

    public static Drug data;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<ReviewForDrugs> reviewForDrugItems;

    public MyComentsFragment() {
        // Required empty public constructor
    }

    public static MyComentsFragment newInstance(Drug data) {
        MyComentsFragment fragment = new MyComentsFragment();
        Bundle b = new Bundle();
        MyComentsFragment.data = data;

        //Get Firebase auth instance
        fragment.setArguments(b);
        return fragment;
    }
    FirebaseDatabase mFirebaseInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mycomments, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        updateListItems();

        return view;
    }

    private void updateListItems() {


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewForDrugItems = new ArrayList<>();
        mFirebaseInstance.getReference("ReviewForDrugs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App dataSnapshot updated");

                try {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        ReviewForDrugs item = userSnapshot.getValue(ReviewForDrugs.class);

                        if(item.getIdUser().equals(Common.getInstance().emailAddressIdentifire))
                            reviewForDrugItems.add(item);
                    }
                    recyclerView.setAdapter(new CommentAdapter(reviewForDrugItems));

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

}
