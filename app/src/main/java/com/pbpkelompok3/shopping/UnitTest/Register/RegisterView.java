package com.pbpkelompok3.shopping.UnitTest.Register;

import com.pbpkelompok3.shopping.database.UserDao;

public interface RegisterView {
    String getFirst();
    void showFirstError(String message);

    String getLast();
    void showLastError(String message);

    String getEm();
    void showEmailError(String message);

    String getPass();
    void showPassError(String message);

    String getTelp();
    void showTelpError(String message);

    void startLoginActivity(UserDao user);

    void showRegisterError(String message);

    void showErrorResponse(String message);
}
