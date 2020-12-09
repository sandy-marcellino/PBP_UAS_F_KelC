package com.pbpkelompok3.shopping.UnitTest.Register;

import com.pbpkelompok3.shopping.api.ApiRequestUser;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserResponse;
import com.pbpkelompok3.shopping.database.UserDao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterService {
    public void Register(final RegisterView view, String first, String last, String email, String password, String telp, final RegisterCallBack callback){
        ApiRequestUser apiService = Retroserver.getClient().create(ApiRequestUser.class);
        Call<UserResponse> add = apiService.register(first,last,email,password,telp);

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                callback.onSuccess(true,response.body().getUsers().get(0));
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
              view.showErrorResponse(t.getMessage());
              callback.onError();
            }
        });
    }

    public Boolean getValid(final RegisterView View, String first, String last, String email, String password, String telp){
        final Boolean[] bool = new Boolean[1];
        Register(View,first,last,email,password,telp,new RegisterCallBack(){

            @Override
            public void onSuccess(boolean value, UserDao user) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
