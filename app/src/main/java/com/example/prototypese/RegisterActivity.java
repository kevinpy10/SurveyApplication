package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etPhone, etEmail, etConfirmnPass;
    Button btnRegister;
    UserDB usersDB;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final String USERNAME_PATTERN = "^[a-zA-Z0-9]*$";
//        final Pattern pattern = Pattern.compile(USERNAME_PATTERN);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmnPass = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister);

        usersDB = new UserDB(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = false;

                String username = etUsername.getText().toString();
                if(username.isEmpty()){
                    flag = true;
                    etUsername.setError("Fill username");
                }else {
//                    Matcher matcher = pattern.matcher(username);
                    if (!username.matches(USERNAME_PATTERN)) {
                        flag = true;
                        etUsername.setError("Username must be alphanumeric only, no special characters allowed!");
                    }else if (username.length() < 5 || username.length() > 15) {
                        flag = true;
                        etUsername.setError("Username must be between 5 and 15 characters!");
                    }
                }

                String password = etPassword.getText().toString();
                if(password.isEmpty()){
                    flag = true;
                    etPassword.setError("Fill password");
                }

                String confirmPass = etConfirmnPass.getText().toString();
                if(confirmPass.isEmpty()){
                    flag = true;
                    etConfirmnPass.setError("Fill confirm password");
                }else{
                    if(!confirmPass.equals(password)){
                        flag = true;
                        etConfirmnPass.setError("Password did not match");
                    }
                }

                String phone = etPhone.getText().toString();
                if(phone.isEmpty()){
                    flag = true;
                    etPhone.setError("Fill Phone");
                }else if (phone.length() < 12 || phone.length() > 13) {
                    flag = true;
                    etPhone.setError("Phone number must be between 12 and 13 digits number!");
                }

                String email = etEmail.getText().toString();
                if(email.isEmpty()){
                    flag = true;
                    etEmail.setError("Fill Email");
                }else if (!email.contains("@")) {
                    flag = true;
                    etEmail.setError("Email must contain '@' character!");
                }else if (!email.endsWith(".com")) {
                    flag = true;
                    etEmail.setError("Email must ends with '.com' !");
                }

                if(usersDB.checkUsers2(username)) {
                    flag = true;
                    etUsername.setError("Username already registered!");
                }

                if(!flag){
                    user = new Users();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setPhone(phone);
                    user.setEmail(email);
                    user.setBalance(0);
                    usersDB.insertUsers(user);
                    Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
    }
}