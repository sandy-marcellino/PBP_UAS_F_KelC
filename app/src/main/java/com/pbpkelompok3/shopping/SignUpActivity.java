package com.pbpkelompok3.shopping;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pbpkelompok3.shopping.api.ApiRequestUser;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private String getFirst, getLast, getEmail,getPassword, getPhoneNumber;
    private TextInputEditText firstName,lastName, phoneNumber, email, password;
    private TextInputLayout lastLayout, firstLayout, phoneNumberLayout, emailLayout, passwordLayout;
    private Button btnSignup,btnSignIn;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;
//    private FirebaseAuth auth;
    private String CHANNEL_ID = "Channel Sign Up";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        auth =FirebaseAuth.getInstance();

        firstName = findViewById(R.id.input_firstName);
        lastName = findViewById(R.id.input_lastName);
        phoneNumber = findViewById(R.id.input_phone);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);


        firstLayout = findViewById(R.id.first_name);
        lastLayout = findViewById(R.id.last_name);
        phoneNumberLayout = findViewById(R.id.phoneNumber);
        emailLayout = findViewById(R.id.email);
        passwordLayout = findViewById(R.id.password);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignup = findViewById(R.id.btnsign_up);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cekDataUserSignUp()) {
                    tambahUser(firstName.getText().toString(),lastName.getText().toString(),
                            email.getText().toString(),password.getText().toString(),phoneNumber.getText().toString());
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean cekDataUserSignUp(){
        //Mendapatkan data yang diinputkan User
        getEmail = email.getText().toString();
        getPassword = password.getText().toString();
        getFirst = firstName.getText().toString();
        getLast = lastName.getText().toString();
        getPhoneNumber = phoneNumber.getText().toString();

        boolean validation = true;

        //Mengecek apakah email dan sandi kosong atau tidak
        if(TextUtils.isEmpty(getEmail) || !Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
            email.setError("Email is Invalid");
            validation = false;
        }
        //Mengecek panjang karakter password baru yang akan didaftarkan
        if(getPassword.length() == 0 ){
            password.setError("Password is Required");
            validation = false;
        }
        if(firstName.length()==0){
            firstName.setError("First name is required");
            validation = false;
        }

        if (getPassword.length() < 6){
            password.setError("Password should be 6 characters");
            validation = false;
        }

        if(getPhoneNumber.length()==0){
            phoneNumber.setError("Phone Number is Required");
            validation = false;
        } else if(getPhoneNumber.length()<10 || getPhoneNumber.length()>13){
            phoneNumber.setError("Phone Number should be 10-13 characters");
            validation = false;
        }
        return validation;
    }

    public void tambahUser(final String firstName, final String lastName,  final String email,  final String password, final String noTelp){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Registering Account...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ApiRequestUser apiService = Retroserver.getClient().create(ApiRequestUser.class);
        Call<UserResponse> add = apiService.register(firstName,lastName,email,password,noTelp);

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                createNotificationChannel();
                addNotification();
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        // NotificationChannel diperlukan untuk API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Sign In";
            String description = "This is Channel Sign Up";
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
                .setContentTitle("Congratulation ")
                .setContentText("Register succesfull, verified your account for login !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Membuat intent yang menampilkan notifikasi
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivities(this,0, new Intent[]{notificationIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Menampilkan notifikasi
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    //firebase
        //                    auth.createUserWithEmailAndPassword(getEmail,getPassword)
//                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if(task.isSuccessful()) {
//                                        //send verification link
//                                        FirebaseUser user = auth.getCurrentUser();
//                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Toast.makeText(SignUpActivity.this, "Verification Link has been sent to your email address", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                        FirebaseUser fUser = auth.getCurrentUser();
//
//                                        tambahUser(fullname.getText().toString(),email.getText().toString(),"",
//                                                    phoneNumber.getText().toString(), password.getText().toString());
//                                        createNotificationChannel();
//                                        addNotification();
//                                        Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
//                                        startActivity(i);
//                                    }else {
//                                        Toast.makeText(SignUpActivity.this, "Register Failed :  "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
    
}