package com.tadi.lekovizdravstvomk.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.tadi.lekovizdravstvomk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class ContactFragment extends BaseFragment {
    private Unbinder unbinder;

    // UI references.
    @BindView(R.id.body)
    EditText body;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.login_progress)
    ProgressBar login_progress;
    @BindView(R.id.send_message)
    Button send_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        unbinder = ButterKnife.bind(this, view);

        send_message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSendMessage();
            }
        });
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }

    private void attemptSendMessage() {

        // Reset errors.
        title.setError(null);
        body.setError(null);

        // Store values at the time of the login attempt.
        String titleItem = title.getText().toString();
        String bodyItem = body.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(bodyItem) && !isPasswordValid(bodyItem)) {
            focusView = body;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(titleItem)) {
            title.setError(getString(R.string.error_field_required));
            focusView = title;
            cancel = true;
        } else if (!isEmailValid(bodyItem)) {
            body.setError(getString(R.string.error_field_required));
            focusView = body;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            login_progress.setVisibility(View.VISIBLE);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}

