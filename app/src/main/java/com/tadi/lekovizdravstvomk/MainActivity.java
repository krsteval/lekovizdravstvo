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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tadi.lekovizdravstvomk.activity.LoginActivity;
import com.tadi.lekovizdravstvomk.activity.acount.MyProfile;
import com.tadi.lekovizdravstvomk.adapter.DrugsAdapter;
import com.tadi.lekovizdravstvomk.adapter.DrugsFilterAdapter;
import com.tadi.lekovizdravstvomk.fragments.BaseFragment;
import com.tadi.lekovizdravstvomk.fragments.ContactFragment;
import com.tadi.lekovizdravstvomk.fragments.DrugsDetailsFragment;
import com.tadi.lekovizdravstvomk.fragments.DrugsRegisterFragment;
import com.tadi.lekovizdravstvomk.fragments.MapFragment;
import com.tadi.lekovizdravstvomk.model.Drug;
import com.tadi.lekovizdravstvomk.model.WayOfPublishing;
import com.yalantis.filter.listener.FilterListener;
import com.yalantis.filter.widget.Filter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static boolean doubleBackToExitPressedOnce = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                View header = navigationView.getHeaderView(0);
                TextView textViewUserEmail = (TextView) header.findViewById(R.id.userEmail);
                if(user!= null){
//                    textViewUserName.setText(user.getEmail());
                    textViewUserEmail.setText(user.getEmail());
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


    public void loadFragmentForAction(String action, Object additionalData) {

        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(action);
        if ((currentFragment != null) && currentFragment.isVisible()) {
            return;
        }

        Fragment fragment = null;
        if (action.equals("drug_register")) {
            fab.setVisibility(View.GONE);
            fragment = new DrugsRegisterFragment();
        }
        else if (action.equals("map_locations")) {
            fab.setVisibility(View.GONE);
            fragment = new MapFragment();
        }
        else if (action.equals("contact")) {
            fab.setVisibility(View.GONE);
            fragment = new ContactFragment();
        }
        else if (action.equals("drug_register_details")) {
            fab.setVisibility(View.GONE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
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
            ((DrugsRegisterFragment)fragment).shangeView();
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

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_map) {
            loadFragmentForAction("map_locations", null);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            signOut();
        } else if (id == R.id.nav_manage) {
            loadFragmentForAction("contact", null);
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
}
