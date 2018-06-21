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
public class Details_Tab2 extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.text_nositel_odobrenie)
    TextView text_nositel_odobrenie;
    @BindView(R.id.text_br_resenie)
    TextView text_br_resenie;
    @BindView(R.id.text_datum_vaznost)
    TextView text_datum_vaznost;
    @BindView(R.id.text_datum_resenie)
    TextView text_datum_resenie;
    public static Drug data;

    public static Details_Tab2 newInstance(Drug data) {
        Details_Tab2 fragment = new Details_Tab2();
        Bundle b = new Bundle();
        Details_Tab2.data = data;

        fragment.setArguments(b);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_tab2, container, false);
        unbinder = ButterKnife.bind(this, view);

        text_nositel_odobrenie.setText(data.getNositel_odobrenie());
        text_br_resenie.setText(data.getBroj_na_resenie());
        text_datum_resenie.setText(data.getDatum_na_resenie());
        text_datum_vaznost.setText(data.getDatum_na_vaznost());


        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
