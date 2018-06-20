package com.tadi.lekovizdravstvomk.helpers;

public class Common {
    private static final Common ourInstance = new Common();

    public static Common getInstance() {
        return ourInstance;
    }

    private Common() {
    }


    public boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
    public boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
