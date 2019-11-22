package com.felipevelasquez.logintest.tools;

public class Validations {
    public boolean isEmail(String email) {
        return email.matches(Constant.REGEX_EMAIL);
    }
}
