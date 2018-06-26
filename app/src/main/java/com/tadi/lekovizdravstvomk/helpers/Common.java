package com.tadi.lekovizdravstvomk.helpers;

import com.tadi.lekovizdravstvomk.model.Drug;

import java.util.List;

public class Common {
    private static final Common ourInstance = new Common();

    public static Common getInstance() {
        return ourInstance;
    }

    private Common() {
    }

    public String emailAddressIdentifire = "";

    public List<Drug> drugList;

    public boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
    public boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
