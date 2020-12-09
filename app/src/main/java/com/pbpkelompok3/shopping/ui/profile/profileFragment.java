package com.pbpkelompok3.shopping.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.api.ApiRequestUser;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserResponse;
import com.pbpkelompok3.shopping.model.User;
import com.pbpkelompok3.shopping.ui.LogoutFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profileFragment extends Fragment {

    private TextView tvFirst,tvlast, tvEmail, tvPhoneNumber;
    private MaterialButton btnEdit, btnLogout;
    private User dataUser;
    private SharedPreferences preferences;
    private int idPref;
    private ImageView ivProfile;
    private String CHANNEL_ID = "Channel logout";
    private int IdUser;
    private String sFirst,sLast, sEmail, sNoTelp,sPassword;
    private SwipeRefreshLayout refresh;

    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvFirst = view.findViewById(R.id.profile_first);
        tvlast = view.findViewById(R.id.profile_last);
        tvEmail = view.findViewById(R.id.profile_email);
        tvPhoneNumber = view.findViewById(R.id.profile_phone);
        btnEdit = view.findViewById(R.id.btn_editProfile);
        btnLogout = view.findViewById(R.id.btn_logout);
        refresh = view.findViewById(R.id.refreshProfile);

        //buat dapatin data dari room
        preferences  = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        idPref = preferences.getInt("id", Context.MODE_PRIVATE);

//        refresh.setRefreshing(true);
        getData(idPref);
//        refresh.setRefreshing(false);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(idPref);
                refresh.setRefreshing(false);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment fragmentEditProfile = new EditProfileFragment();
                Bundle profileData = new Bundle();
                profileData.putSerializable("user_profile", dataUser);
                fragmentEditProfile.setArguments(profileData);
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_profile,fragmentEditProfile).addToBackStack(null).commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                LogoutFragment dialog = new LogoutFragment();
                dialog.show(manager,"dialog");
            }
        });
    }

    private void getData(int id){
        ApiRequestUser apiService = Retroserver.getClient().create(ApiRequestUser.class);
        Call<UserResponse> add = apiService.getUserById(id,"data");

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Load profile...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();

                IdUser = response.body().getUsers().get(0).getId();
                sFirst = response.body().getUsers().get(0).getFirst_name();
                sLast = response.body().getUsers().get(0).getLast_name();
                sEmail = response.body().getUsers().get(0).getEmail();
                sPassword = response.body().getUsers().get(0).getPassword();
                sNoTelp = response.body().getUsers().get(0).getNo_telp();

                tvFirst.setText(sFirst);
                tvlast.setText(sLast);
                tvEmail.setText(sEmail);
                tvPhoneNumber.setText(sNoTelp);
                dataUser = new User(sFirst,sLast,sEmail,sPassword,sNoTelp);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}