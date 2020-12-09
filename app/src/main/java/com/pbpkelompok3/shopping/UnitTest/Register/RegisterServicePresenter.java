package com.pbpkelompok3.shopping.UnitTest.Register;

import android.util.Patterns;

import com.pbpkelompok3.shopping.database.UserDao;

public class RegisterServicePresenter {
    private RegisterView view;
    private RegisterService service;
    private RegisterCallBack callback;

    public RegisterServicePresenter(RegisterView view,RegisterService service){
        this.view = view;
        this.service = service;
    }

    public void onRegisterClicked(){
        if(view.getFirst().isEmpty()){
            view.showFirstError("First name is required");
            return;
        }else if(view.getLast().isEmpty()){
            view.showLastError("Last name is required");
            return;
        }else if(view.getPass().isEmpty()){
            view.showPassError("Password is required");
            return;
        }else if(view.getPass().length() < 6){
            view.showPassError("Password at least 6 characters");
            return;
        }else if(view.getTelp().isEmpty()){
            view.showTelpError("Phone number is required");
            return;
        }else if(view.getTelp().length() < 10 || view.getTelp().length() > 13){
            view.showTelpError("Phone number should be 10-13 characters");
            return;
        }else if(view.getEm().isEmpty()){
            view.showEmailError("Email is required");
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(view.getEm()).matches()){
            view.showEmailError("Email is invalid");
            return;
        }else{
            service.Register(view, view.getFirst(), view.getLast(), view.getEm(), view.getPass(), view.getTelp(), new RegisterCallBack() {
                @Override
                public void onSuccess(boolean value, UserDao user) {
                    view.startLoginActivity(user);
                }
                @Override
                public void onError() {

                }
            });
            return;
        }
    }
}
