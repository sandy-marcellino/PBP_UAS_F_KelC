package com.pbpkelompok3.shopping.database;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private static final String PREFERENCE_FIRST_RUN = "PREFERENCE_FIRST_RUN";
    private static final String PREFERENCE_USERNAME_LOGIN = "PREFERENCE_USERNAME_LOGIN";
    private static final String PREFERENCE_NAME = "PREFERENCE_APP";
    private Context context;
    private SharedPreferences pref;

    public AppPreference(Context context) {
        this.context = context;
        this.pref = this.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public Boolean getFirstRun() {
        boolean firstRun = pref.getBoolean(PREFERENCE_FIRST_RUN, true);

        return firstRun;
    }

    public void setFirstRun() {
        pref.edit().putBoolean(PREFERENCE_FIRST_RUN, false).apply();
    }

    public String getLoginEmail() {
        return pref.getString("email", String.valueOf(Context.MODE_PRIVATE));
    }

    public void setLoginUsername(String username) {
        pref.edit().putString(PREFERENCE_USERNAME_LOGIN, username).apply();
    }
}