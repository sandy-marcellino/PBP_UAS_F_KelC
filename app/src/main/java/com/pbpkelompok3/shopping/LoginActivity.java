package com.pbpkelompok3.shopping;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.pbpkelompok3.shopping.api.ApiRequestUser;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserResponse;
import com.pbpkelompok3.shopping.ui.home.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth auth;
    TextInputEditText email, password;
    MaterialButton login;
    private String getEmail, getPassword;
    MaterialButton btnSignUp;

    private String emailUserLogin;

    private SharedPreferences.Editor editor;
    private SharedPreferences sPreferences;
    public static final String KEY_ID = "id";

    public static final int mode = Activity.MODE_PRIVATE;
    private String CHANNEL_ID = "Channel Sign In";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.inputEmailSignIn);
        password = findViewById(R.id.inputPasswordSignin);
        login = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.btnSignUp);
        sPreferences = getSharedPreferences("userPreferences",Context.MODE_PRIVATE);
//        auth =FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAccount();
            }
        });
    }

    private boolean cekDataUserSignIn(){
        getEmail = email.getText().toString();
        getPassword = password.getText().toString();
        boolean validasi = true;

        //Mengecek apakah email dan sandi kosong atau tidak
        if(TextUtils.isEmpty(getEmail) || !Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
            email.setError("Email is Invalid !");
            validasi=false;
        }

        //Mengecek panjang karakter password baru yang akan didaftarkan
        if(getPassword.length() == 0){
            password.setError("Password is required !");
            validasi = false;
        }else if(getPassword.length() < 6){
            password.setError("Password should be or more than 6 characters !");
            validasi = false;
        }
        return validasi;
    }

    private void signInAccount(){
        if(cekDataUserSignIn()){
            login();
        }
    }

//    public void putPreference(){
//        preferences  = getSharedPreferences("login", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sPreferences.edit();
//        editor.putString("email",email.getText().toString());
//        editor.putInt("isLogin",1);
//        editor.putString("emailLogin",emailUserLogin);
//        editor.commit();
//    }

    private void createNotificationChannel() {
        // NotificationChannel diperlukan untuk API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Sign In";
            String description = "This is Channel Sign In";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification() {
        //konstruktor NotificationCompat.Builder harus diberi CHANNEL_ID untuk API level 26+
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hello")
                .setContentText("Welcome, Please enjoy your stay...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Membuat intent yang menampilkan notifikasi
        Intent notificationIntent = new Intent(this, HomeFragment.class);
        PendingIntent contentIntent = PendingIntent.getActivities(this,0, new Intent[]{notificationIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Menampilkan notifikasi
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    private void login(){

        ApiRequestUser apiService = Retroserver.getClient().create(ApiRequestUser.class);
        Call<UserResponse> login = apiService.login(email.getText().toString(),password.getText().toString());
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Register to system...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        login.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();
                try{
//                    Toast.makeText(LoginActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if(status.equalsIgnoreCase("not-registered")){
                        Toast.makeText(LoginActivity.this, message,Toast.LENGTH_SHORT).show();
                    }
                    if(status.equalsIgnoreCase("fail")){
                        Toast.makeText(LoginActivity.this, message,Toast.LENGTH_SHORT).show();
                    }
                    if(status.equalsIgnoreCase("not-verify")){
                        Toast.makeText(LoginActivity.this, message,Toast.LENGTH_SHORT).show();
                    }
                    if(status.equalsIgnoreCase("success")){
                        Toast.makeText(LoginActivity.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        getDataToPreference(response.body().getUsers().get(0).getId());
                        if(response.body().getUsers().get(0).getEmail().toLowerCase().equalsIgnoreCase("admin@admin.com")){
                            Intent i = new Intent(LoginActivity.this,AdminMainActivity.class);
                            startActivity(i);
                        }else{
                            createNotificationChannel();
                            addNotification();
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDataToPreference(int id){
        sPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sPreferences.edit();
        editor.putInt(KEY_ID,id);
        editor.putInt("isLogin",1);
        editor.commit();
    }

//    auth.signInWithEmailAndPassword(getEmail, getPassword)
//            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//        @Override
//        public void onComplete(@NonNull Task<AuthResult> task) {
//            if (task.isSuccessful()) {
//                //check verified or not
//                FirebaseUser user = auth.getCurrentUser();
//                if(user.isEmailVerified()){
//                    // Sign in success, update UI with the signed-in user's information
//                    Toast.makeText(LoginActivity.this, "Sign in Successfull", Toast.LENGTH_SHORT).show();
//                    login(email.getText().toString());
//                    createNotificationChannel();
//                    addNotification();
//                    String emailAdmin = "ezjarvis001@gmail.com";
//                    if(emailAdmin.equalsIgnoreCase(getEmail)) {
//                        Intent i = new Intent(LoginActivity.this, AdminMainActivity.class);
//                        startActivity(i);
//                        finish();
//                    }else {
//                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//                }else{
//                    Toast.makeText(LoginActivity.this, "Please verified your account first", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                // If sign in fails, display a message to the user.
//                Toast.makeText(LoginActivity.this, "Sign in Unsuccessfull", Toast.LENGTH_SHORT).show();
//            }
//        }
//    });
}