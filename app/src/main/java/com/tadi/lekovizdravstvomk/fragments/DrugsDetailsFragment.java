package com.tadi.lekovizdravstvomk.fragments;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.adapter.ViewPagerAdapter;
import com.tadi.lekovizdravstvomk.model.Drug;

import java.util.Calendar;
import java.util.TimeZone;

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
    @BindView(R.id.text_price)
    TextView text_price;

    @BindView(R.id.container)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout sliding_tabs;


    @BindView(R.id.FloatingActionMenu1)
    FloatingActionMenu fab;

    @BindView(R.id.subFloatingMenu1)
    FloatingActionButton menu1;
    @BindView(R.id.subFloatingMenu2)
    FloatingActionButton menu2;
    @BindView(R.id.subFloatingMenu3)
    FloatingActionButton menu3;

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
        initFabActionButtons();
        return view;
    }

    private void populateFields() {
        title.setText(data.getIme());
        text_tile_latinsko.setText(data.getLatinicno_ime());
        text_jacina.setText(data.getJacina());
        text_farmacevskaforma.setText(data.getFarmacevska_forma());
        text_price.setText(data.getMaloprodazna_cena() + " / " + data.getGolemoprodazna_cena());


        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(Details_Tab1.newInstance(data), "Basic");
        adapter.addFragment(Details_Tab2.newInstance(data), "Info");
        adapter.addFragment(Details_Tab2.newInstance(data), "Reviews");
        viewPager.setAdapter(adapter);

        sliding_tabs.setupWithViewPager(viewPager);
    }

    private void initFabActionButtons() {

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity()
                        , " Alarm Icon clicked ", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");

                Calendar cal = Calendar.getInstance();
                long startTime = cal.getTimeInMillis();
                long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                intent.putExtra(CalendarContract.Events.TITLE, data.getIme());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is drug bla bla");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Skopje");
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shareBody = "Here is the drug" + data.getIme() + " who have new price!!(" + data.getMaloprodazna_cena() + ") ";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Update, items");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Add to fav Icon clicked", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
