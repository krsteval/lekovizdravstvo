package com.tadi.lekovizdravstvomk;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.tadi.lekovizdravstvomk.activity.LoginActivity;
import com.tadi.lekovizdravstvomk.activity.acount.MyProfile;
import com.tadi.lekovizdravstvomk.fragments.BaseFragment;
import com.tadi.lekovizdravstvomk.fragments.ContactFragment;
import com.tadi.lekovizdravstvomk.fragments.DrugsDetailsFragment;
import com.tadi.lekovizdravstvomk.fragments.DrugsRegisterFragment;
import com.tadi.lekovizdravstvomk.fragments.FavouriteDrugsFragment;
import com.tadi.lekovizdravstvomk.fragments.MapFragment;
import com.tadi.lekovizdravstvomk.fragments.MyComentsFragment;
import com.tadi.lekovizdravstvomk.helpers.Common;
import com.tadi.lekovizdravstvomk.model.Drug;
import com.tadi.lekovizdravstvomk.model.MonitoringDatabase;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener,

        RewardedVideoAdListener,
         AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener
        {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static boolean doubleBackToExitPressedOnce = false;
    private RewardedVideoAd mRewardedVideoAd;
    AdLoader adLoader;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authListener;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    SearchView searchView;

    Menu menu;
    MenuItem itemSearch = null;
    MenuItem itemChangeView = null;

    public boolean isGridFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                Snackbar.make(view, "Contact administrator of app", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                loadFragmentForAction("contact", null);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //ca-app-pub-4089031262831925~3261264146
        MobileAds.initialize(this, "ca-app-pub-5161878407094994/4828770607");

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                View header = navigationView.getHeaderView(0);
                TextView textViewUserEmail = (TextView) header.findViewById(R.id.userEmail);
                if(user!= null){
//                    textViewUserName.setText(user.getEmail());
                    textViewUserEmail.setText(user.getEmail());
                    Common.getInstance().emailAddressIdentifire = user.getEmail();


                    loadRewardedVideoAd();
                }
                //get current user
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }

            }
        };


        loadFragmentForAction("drug_register", null);

    }

    private void loadRewardedVideoAd() {

        mRewardedVideoAd.loadAd("ca-app-pub-5161878407094994/4828770607",
                new AdRequest.Builder().build());
    }

    public void loadFragmentForAction(String action, Object additionalData) {

        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(action);
        if ((currentFragment != null) && currentFragment.isVisible()) {
            return;
        }

        Fragment fragment = null;

        checkActiveFragment();

        if (action.equals("drug_register")) {
            fab.setVisibility(View.GONE);
            fragment = new DrugsRegisterFragment();

        }
        else if (action.equals("map_locations")) {
            fab.setVisibility(View.GONE);
            fragment = new MapFragment();

            itemSearch.setVisible(false);
            itemChangeView.setVisible(false);
        }
        else if (action.equals("my_comments")) {
            fab.setVisibility(View.GONE);
            fragment = new MyComentsFragment();

            itemSearch.setVisible(false);
            itemChangeView.setVisible(false);
        }
        else if (action.equals("favorite")) {
            fab.setVisibility(View.GONE);

            List<Drug> drugsFavList = App.getDatabase().receptionDao().getAllFavoritesDrugs();
            fragment = FavouriteDrugsFragment.newInstance(drugsFavList);

            itemSearch.setVisible(false);
            itemChangeView.setVisible(false);
        }
        else if (action.equals("contact")) {
            fab.setVisibility(View.GONE);
            fragment = new ContactFragment();

            itemSearch.setVisible(false);
            itemChangeView.setVisible(false);
        }
        else if (action.equals("drug_register_details")) {
            fab.setVisibility(View.GONE);

            itemSearch.setVisible(false);
            itemChangeView.setVisible(false);

            Drug drug = null;
            if(additionalData instanceof Drug)
                drug = (Drug)additionalData;
            fragment = DrugsDetailsFragment.newInstance(drug);
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentTransaction transaction = ft.replace(R.id.frame_content, fragment, action);
            //we should not keep loading fragment in back stack
            transaction.addToBackStack(action + (((BaseFragment) fragment).skipBack() ? "_skip_back" : ""));
            ft.commitAllowingStateLoss();
        }

    }


    private void checkActiveFragment() {
        if(fab != null)
            fab.setVisibility(View.VISIBLE);
        if(menu!= null){
            itemSearch = menu.findItem(R.id.action_search);
            itemChangeView = menu.findItem(R.id.action_changeview);
            itemSearch.setVisible(true);
            itemChangeView.setVisible(true);

        }
    }

//    public void setBanner(){
//        adLoader = new AdLoader.Builder(getBaseContext(), "ca-app-pub-3940256099942544/2247696110")
//                .forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
//                    @Override
//                    public void onAppInstallAdLoaded(NativeAppInstallAd appInstallAd) {
//                        // Show the app install ad.
//                    }
//                })
//                .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
//                    @Override
//                    public void onContentAdLoaded(NativeContentAd contentAd) {
//                        // Show the content ad.
//                    }
//                })
//                .withAdListener(new AdListener() {
//                    @Override
//                    public void onAdFailedToLoad(int errorCode) {
//                        // Handle the failure by logging, altering the UI, and so on.
//                    }
//                })
//                .withNativeAdOptions(new NativeAdOptions.Builder()
//                        // Methods in the NativeAdOptions.Builder class can be
//                        // used here to specify individual options settings.
//                        .build())
//                .build();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        this.menu = menu;
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
        setupSearchView();
        return true;
    }
    private void setupSearchView()
    {

        searchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null)
        {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables)
            {
                if (inf.getSuggestAuthority() != null && inf.getSuggestAuthority().startsWith("applications"))
                {
                    info = inf;
                }
            }
            searchView.setSearchableInfo(info);
        }

        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                break;
            case R.id.action_changeview:
                if(isGridFragment)
                    isGridFragment = false;
                else
                    isGridFragment = true;

                changeItemsView();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeItemsView() {

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("drug_register");
        if(fragment != null)
            ((DrugsRegisterFragment)fragment).shangeView(isGridFragment);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, MyProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_favorite) {
            loadFragmentForAction("favorite", null);
        } else if (id == R.id.nav_map) {
            loadFragmentForAction("map_locations", null);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            signOut();
        } else if (id == R.id.my_coments) {
            loadFragmentForAction("my_comments", null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("drug_register");
        if(fragment != null)
            ((DrugsRegisterFragment)fragment).searchItems(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onClose() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("drug_register");
        if(fragment != null)
            ((DrugsRegisterFragment)fragment).clearSearch();
        return false;
    }

    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        //find next fragment in backstack
        FragmentManager.BackStackEntry backEntry = null;
        String tag = "drug_register";
        boolean hasEntryInBackStack = false;
        for (int index = 1; index < getSupportFragmentManager().getBackStackEntryCount(); index++) {
            backEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1 - index);
            tag = backEntry.getName();

            if (tag.equals("drug_register")) {
                checkActiveFragment();
            }
            if (!tag.contains("skip_back")) {

                hasEntryInBackStack = true;
                break;
            }

        }

        if (hasEntryInBackStack) {
            //clear back stack
            getSupportFragmentManager().popBackStack(tag, 0);
        } else {
            // Default action on back pressed
            if (doubleBackToExitPressedOnce) {
                if (backEntry != null) {
                    //clear back stack
                    getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                super.onBackPressed();
                finish();
                return;
            }

            doubleBackToExitPressedOnce = true;

            Toast.makeText(this, getText(R.string.double_press_back_message), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

        @Override
        public void onRewarded(RewardItem reward) {
            Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                    reward.getAmount(), Toast.LENGTH_SHORT).show();
            // Reward the user.
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            Toast.makeText(this, "onRewardedVideoAdLeftApplication",
                    Toast.LENGTH_SHORT).show();
        }

        public ArrayMap<String, List<String>> getApplied_filters() {
            return new ArrayMap<>();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int errorCode) {
            Toast.makeText(this, "No video found", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdLoaded() {
            try {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoStarted() {
            Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoCompleted() {
            Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            // Load the next rewarded video ad.
            loadRewardedVideoAd();
        }

        @Override
        public void onResume() {
            mRewardedVideoAd.resume(this);
            super.onResume();
        }

        @Override
        public void onPause() {
            mRewardedVideoAd.pause(this);
            super.onPause();
        }

        @Override
        public void onDestroy() {
            mRewardedVideoAd.destroy(this);
            super.onDestroy();
        }

            @Override
            public void onOpenAnimationStart() {

            }

            @Override
            public void onOpenAnimationEnd() {

            }

            @Override
            public void onCloseAnimationStart() {

            }

            @Override
            public void onCloseAnimationEnd() {

            }

            @Override
            public void onResult(Object result) {

            }
        }
