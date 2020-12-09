package com.pbpkelompok3.shopping.UnitTest.Register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.database.UserDao;

public class RegisterActvty extends AppCompatActivity implements RegisterView {

    private RegisterServicePresenter presenter;
    private TextInputEditText firstName,lastName, phoneNumber, email, password;
    private Button btnSignup,btnSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName = findViewById(R.id.input_firstName);
        lastName = findViewById(R.id.input_lastName);
        phoneNumber = findViewById(R.id.input_phone);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignup = findViewById(R.id.btnsign_up);

        presenter = new RegisterServicePresenter(this, new RegisterService());

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRegisterClicked();
            }
        });
    }

    @Override
    public String getFirst() {
        return firstName.getText().toString();
    }

    @Override
    public void showFirstError(String message) {
        firstName.setError(message);
    }

    @Override
    public String getLast() {
        return lastName.getText().toString();
    }

    @Override
    public void showLastError(String message) {
        lastName.setError(message);
    }

    @Override
    public String getEm() {
        return email.getText().toString();
    }

    @Override
    public void showEmailError(String message) {
        email.setError(message);
    }

    @Override
    public String getPass() {
        return password.getText().toString();
    }

    @Override
    public void showPassError(String message) {
        password.setError(message);
    }

    @Override
    public String getTelp() {
        return phoneNumber.getText().toString();
    }

    @Override
    public void showTelpError(String message) {
        phoneNumber.setError(message);
    }

    @Override
    public void startLoginActivity(UserDao user) {
        new ActivityUtil(this).startLoginActivity();
    }

    @Override
    public void showRegisterError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
