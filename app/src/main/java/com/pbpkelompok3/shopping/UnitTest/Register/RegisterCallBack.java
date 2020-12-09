package com.pbpkelompok3.shopping.UnitTest.Register;

import com.pbpkelompok3.shopping.database.UserDao;

public interface RegisterCallBack {
    void onSuccess(boolean value, UserDao user);
    void onError();
}


