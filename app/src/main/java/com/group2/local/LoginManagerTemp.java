package com.group2.local;

public class LoginManagerTemp {
    static public Boolean isLogin = false;
    static public String token = "";
    static public Boolean isJustFinishRegisterSuccess = false;
    static public Boolean isJustFinishLoginSuccess = false;

    static public void setToken(String token){
        if (token != null && !token.isEmpty()) {
            LoginManagerTemp.token = token;
            LoginManagerTemp.isLogin = true;
        } else {
            LoginManagerTemp.token = "";
            LoginManagerTemp.isLogin = false;
        }
    }
}
