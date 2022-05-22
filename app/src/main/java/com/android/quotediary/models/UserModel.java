package com.android.quotediary.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Body;

public class UserModel {

    public static class Login{
        @SerializedName("password")
        private String Password="";
        @Expose
        @SerializedName("email")
        private String Email="";

        public Login(){

        }

        public Login(String password, String email) {
            Password = password;
            Email = email;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }


        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }
    }

    public static class Register{

        @Expose
        @SerializedName("Uname")
        private String Uname="";
        @Expose
        @SerializedName("email")
        private String Email="";
        @Expose
        @SerializedName("password")
        private String Password="";

        public String getUname() {
            return Uname;
        }

        public void setUname(String uname) {
            Uname = uname;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

    }

    @Expose
    @SerializedName("Uname")
    private String Uname="";
    @Expose
    @SerializedName("email")
    private String Email="";
    @Expose
    @SerializedName("password")
    private String Password="";

    @Expose
    @SerializedName("accessToken")
    private String authToken="";
    @Expose
    @SerializedName("refreshToken")
    private String RefreshToken="";


    public UserModel() {
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

//    public String getPassword2() {
//        return Password2;
//    }
//
//    public void setPassword2(String password2) {
//        Password2 = password2;
//    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }


}
