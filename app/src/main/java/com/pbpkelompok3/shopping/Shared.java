package com.pbpkelompok3.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Shared {
    // to create sharedpreferences file
    SharedPreferences sharedPreferences;
    // to edit the shared preferences file
    SharedPreferences.Editor editor;
    // context pass the reference to another class
    Context context;
    // mode should be private for sharedpreferences file
    int mode = 0;
    // sharedpreferences file name
    String Filename = "userfile";
    // store the boolean value with respect to key id
    String Data = "b";

    // create constructor to pass memory at runtime to the sharedfile

    public Shared(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Filename, mode);
        editor = sharedPreferences.edit();
    }

    // for second timer user
    public void secondtime() {
        editor.putBoolean(Data, true);
        editor.commit();
    }

    // for first time user
    public void firsttime() {
        if(!this.login()) {
            // if b is false then jump to login activity
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    // to get the default values as false
    private boolean login() {
        return sharedPreferences.getBoolean(Data, false);
    }
}
