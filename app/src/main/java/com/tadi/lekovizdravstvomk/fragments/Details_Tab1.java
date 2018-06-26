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
    @BindView(R.id.text_genericko_ime)
    TextView genericko_ime;
    @BindView(R.id.text_farmacevskaforma)
    TextView farmacevskaforma;
    @BindView(R.id.text_nacin_na_izdavanje)
    TextView text_nacin_na_izdavanje;
    @BindView(R.id.text_pakuvanje)
    TextView text_pakuvanje;
    @BindView(R.id.text_proizvoditel)
    TextView text_proizvoditel;

    public static Drug data;
    public Details_Tab1() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        data = args.getParcelable("data_item");
        super.setArguments(args);
    }
    public static Details_Tab1 newInstance(Drug dataItem) {
        Details_Tab1 fragment = new Details_Tab1();
        Bundle b = new Bundle();
        DrugsDetailsFragment.data = dataItem;
        b.putParcelable("data_item", dataItem);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_tab1, container, false);
        unbinder = ButterKnife.bind(this, view);

        genericko_ime.setText(data.getIme());
        farmacevskaforma.setText(data.getFarmacevska_forma());
        text_pakuvanje.setText(data.getFarmacevska_forma());
        text_nacin_na_izdavanje.setText(data.getNacin_izdavanje());
        text_pakuvanje.setText(data.getFarmacevska_forma());
        text_proizvoditel.setText(data.getProizvoditel());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
