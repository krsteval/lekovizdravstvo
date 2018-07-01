package com.tadi.lekovizdravstvomk.helpers;

import com.tadi.lekovizdravstvomk.model.Drug;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {
    private static final Common ourInstance = new Common();

    public static Common getInstance() {
        return ourInstance;
    }

    private Common() {
    }

    public Integer numberOfComments = 0;

    public String emailAddressIdentifire = "";

    public List<Drug> drugList;

    public boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
