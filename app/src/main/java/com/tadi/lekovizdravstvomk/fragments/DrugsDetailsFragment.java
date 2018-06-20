package com.tadi.lekovizdravstvomk.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.adapter.ViewPagerAdapter;
import com.tadi.lekovizdravstvomk.model.Drug;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugsDetailsFragment extends BaseFragment {

    public static Drug data;
    private Unbinder unbinder;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.text_tile_latinsko)
    TextView text_tile_latinsko;
    @BindView(R.id.text_jacina)
    TextView text_jacina;
    @BindView(R.id.text_farmacevskaforma)
    TextView text_farmacevskaforma;

    @BindView(R.id.container)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout sliding_tabs;

    public DrugsDetailsFragment() {
        // Required empty public constructor
    }

    public static DrugsDetailsFragment newInstance(Drug data) {
        DrugsDetailsFragment fragment = new DrugsDetailsFragment();
        Bundle b = new Bundle();
        DrugsDetailsFragment.data = data;

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
        View view = inflater.inflate(R.layout.fragment_drugs_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        populateFields();
        return view;
    }

    private void populateFields() {
        title.setText(data.getIme());
        text_tile_latinsko.setText(data.getLatinicno_ime());
        text_jacina.setText(data.getJacina());
        text_farmacevskaforma.setText(data.getFarmacevska_forma());


        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(Details_Tab1.newInstance(data), "General");
        adapter.addFragment(new Details_Tab2(), "Optional");
        viewPager.setAdapter(adapter);

        sliding_tabs.setupWithViewPager(viewPager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
