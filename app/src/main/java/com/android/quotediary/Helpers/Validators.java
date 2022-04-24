package com.android.quotediary.Helpers;

import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

public class Validators {

    public static boolean ISEmail(TextInputLayout textInputLayout,String s){
        boolean b = !s.contains("@") | !s.contains(".com");
        Log.i("Nothing_Much",b+""+s);
        textInputLayout.setErrorEnabled(b);
        if(b) textInputLayout.setError("Invalid Email");
        return !b;
    }
    public static boolean IsValidPassword(TextInputLayout textInputLayout,String s){
        if(s.length()<8) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Password Must Me Grater Than 8 Characters");
            return false;
        }
        else if(!s.matches("[A-Z]")) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Password Must Contains At least one Captial Alfabet");
            return false;
        }
        else if(!s.matches("[0-9]]")) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Password Should Contains At least 1 number in 0-9");
            return false;
        }
        else if(!s.matches("[a-z]")){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Password Must Contains At least one smaller Alfabet");
            return false;
        }
        else textInputLayout.setErrorEnabled(true);

        return true;
    }

}
