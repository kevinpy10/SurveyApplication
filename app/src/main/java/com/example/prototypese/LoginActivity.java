package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView btnRegister;
    UserDB userDB;
    Users users;
    private String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        userDB = new UserDB(this);
//        users = new Users();
//        adminAccount();
//        userDB.insertUsers(users);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean flag = false;

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if(username.isEmpty()){
                    etUsername.setError("Fill the username!");
                    flag = true;
                }
                if(password.isEmpty()) {
                    etPassword.setError("Fill the password!");
                    flag = true;
                }

                if(!flag){
                    if(userDB.checkUsers(username,password)){
                        Users users = userDB.getUser(username);
                        int user_id = users.getUserId();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }else{
                        etUsername.setError("Wrong Username");
                        etPassword.setError("Wrong Password");
                    }
                }
            }
        });

    }

    public void adminAccount(){
        users.setUsername("admin");
        users.setPassword("admin");
        users.setPhone("082177722226");
        users.setEmail("andre.lay@binus.ac.id");
        users.setBalance(0);
    }
}