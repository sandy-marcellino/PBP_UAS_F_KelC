package com.pbpkelompok3.shopping.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.PUT;

public class EditProfileFragment extends Fragment{

    private TextInputEditText editEmail, editFirst, editLast, editPhoneNumber;
    private TextInputLayout editPhoneLayout, editFirstLayout,editLastLayout;
    private MaterialButton btnCancel,btnUpdate;
    private Uri selectedImage = null;
    private static final int PERMISSION_CODE = 1000;
    private User user;
    private View view;
    private int id;
    private String email,status,selected;
    private ImageView ivGambar;
    private Bitmap bitmap;

    private SharedPreferences preferences;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile,container,false);
        preferences  = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        id = preferences.getInt("id", Context.MODE_PRIVATE);

        init();
        setAttribut();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAttribut();
    }

    public void init(){
        user = (User) getArguments().getSerializable("user_profile");
        editFirst = view.findViewById(R.id.input_firstEdit);
        editLast = view.findViewById(R.id.input_lastEdit);
        editPhoneNumber = view.findViewById(R.id.input_phoneEdit);
        editEmail = view.findViewById(R.id.input_emailEdit);
        btnCancel = view.findViewById(R.id.btn_cancelEdit);
        btnUpdate = view.findViewById(R.id.btn_updateEdit);
        ivGambar = view.findViewById(R.id.ivGambar);

        editFirst.setText(user.getFirst_name());
        editLast.setText(user.getLast_name());
        editEmail.setText(user.getEmail());
        editPhoneNumber.setText(user.getNo_telp());
    }

    public void setAttribut(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().
//                        replace(R.id.edit_profile_fragment,new profileFragment()).addToBackStack(null).commit();

                loadFragment(new profileFragment());
                closeFragment();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gambar = "";
                if(cekDataValid()){
                    editUser(id, gambar);
                }
            }
        });
    }

    public boolean cekDataValid(){
        boolean validation = true;
        if(editLast.getText().toString().isEmpty()){
            editLast.setError("Last Name is required");
            validation = false;
        }

        if(editFirst.getText().toString().isEmpty()){
            editFirst.setError("First Name is required");
            validation = false;
        }

        int phoneNumber = editPhoneNumber.getText().toString().length();
        if( phoneNumber < 10 || phoneNumber > 13 ){
            editPhoneNumber.setError("Phone Number should be 10-13 characters");
            validation = false;
        }

        return validation;
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.replace(R.id.edit_profile_fragment, fragment)
                .detach(this)
                .attach(this)
                .commit();
    }

    public void closeFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(EditProfileFragment.this).detach(this)
                .attach(this).commit();
    }

    private void editUser(int id, final String gambar){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Update profile user...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(PUT, UserApi.URL_UPDATE + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);



                    loadFragment(new profileFragment());

                    closeFragment();
                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("first_name", editFirst.getText().toString());
                params.put("last_name", editLast.getText().toString());
                params.put("email", editEmail.getText().toString());
                params.put("no_telp", editPhoneNumber.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}