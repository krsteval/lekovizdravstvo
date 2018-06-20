package com.tadi.lekovizdravstvomk.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.model.Drug;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details_Tab1 extends Fragment {


    private Unbinder unbinder;
    @BindView(R.id.test_opis)
    TextView title;
    @BindView(R.id.test_opis1)
    TextView title1;

    public static Drug data;
    public Details_Tab1() {
        // Required empty public constructor
    }

    public static Details_Tab1 newInstance(Drug data) {
        Details_Tab1 fragment = new Details_Tab1();
        Bundle b = new Bundle();
        Details_Tab1.data = data;

        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details__tab1, container, false);
        unbinder = ButterKnife.bind(this, view);

        title.setText(data.getPakuvanje());
        title1.setText(data.getFarmacevska_forma());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
