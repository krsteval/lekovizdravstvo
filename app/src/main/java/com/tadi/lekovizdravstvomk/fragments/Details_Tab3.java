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
public class Details_Tab3 extends Fragment {

    private Unbinder unbinder;

    public static Drug data;

    private String drugId;

    @BindView(R.id.editTextMessage)
    EditText comment;
    @BindView(R.id.btn_addComent)
    Button btnAddReview;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<ReviewForDrugs> reviewForDrugItems;

    public Details_Tab3() {
        // Required empty public constructor
    }

    public static Details_Tab3 newInstance(Drug data) {
        Details_Tab3 fragment = new Details_Tab3();
        Bundle b = new Bundle();
        Details_Tab3.data = data;

        //Get Firebase auth instance
        fragment.setArguments(b);
        return fragment;
    }
    FirebaseDatabase mFirebaseInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_tab3, container, false);
        unbinder = ButterKnife.bind(this, view);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        updateListItems();

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(comment.getText().toString())) {
                    comment.setError(getString(R.string.error_field_required));
                    comment.requestFocus();

                }else{

                    DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("ReviewForDrugs");
                    if (TextUtils.isEmpty(drugId)) {

                        drugId = mFirebaseDatabase.push().getKey();
                    }
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    Date date = new Date();
                    ReviewForDrugs review = new ReviewForDrugs();
                    review.setIdDrug(data.getId());
                    review.setIdUser(Common.getInstance().emailAddressIdentifire);
                    review.setReviewBody(comment.getText().toString());
                    review.setDate(dateFormat.format(date));
                    mFirebaseDatabase.child(drugId).setValue(review);

                    comment.setText("");
                }
            }
        });
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
                        if(item.getIdDrug() == data.getId())
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
