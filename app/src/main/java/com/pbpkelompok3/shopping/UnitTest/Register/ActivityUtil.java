package com.pbpkelompok3.shopping.UnitTest.Register;

import android.content.Context;
import android.content.Intent;

import com.pbpkelompok3.shopping.LoginActivity;

public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context){
        this.context = context;
    }

    public void startLoginActivity(){
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
