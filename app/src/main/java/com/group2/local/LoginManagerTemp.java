package com.group2.local;

import android.content.Context;
import android.util.Log;

import com.group2.database_helper.LocationDatabaseHelper;
import com.group2.database_helper.LoginDatabaseHelper;
import com.group2.database_helper.OrderDatabaseHelper;

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

    static public void clearOldCredentialAndData(Context context) {
        LoginManagerTemp.token = "";
        LoginManagerTemp.isLogin = false;
        isJustFinishLoginSuccess = false;
        isJustFinishRegisterSuccess = false;
        try {
            LoginDatabaseHelper loginDatabaseHelper = new LoginDatabaseHelper(context);
            loginDatabaseHelper.clearAllData();
            OrderDatabaseHelper orderDatabaseHelper = new OrderDatabaseHelper(context);
            orderDatabaseHelper.clearAllData();
            LocationDatabaseHelper locationDatabaseHelper = new LocationDatabaseHelper(context);
            locationDatabaseHelper.clearAllData();
        } catch (Exception e) {
            Log.d("LoginManagerTemp", e.getMessage());
        }
    }

    static public Boolean syncToken(Context context) {
        try {
            LoginDatabaseHelper loginDatabaseHelper = new LoginDatabaseHelper(context);
            return loginDatabaseHelper.syncToken();
        } catch (Exception e) {
            Log.d("LoginManagerTemp", e.getMessage());
            return false;
        }
    }
}
