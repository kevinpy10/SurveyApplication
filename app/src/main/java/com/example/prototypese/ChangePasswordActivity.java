package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText et_old_password, et_new_password;
    Button btn_change, btn_cancel;
    UserDB userDB;
    Users user;
    String old_pass, new_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        et_old_password = findViewById(R.id.et_old_password);
        et_new_password = findViewById(R.id.et_new_password);
        btn_change = findViewById(R.id.btn_change);
        btn_cancel = findViewById(R.id.btn_cancel);

        int userId = getIntent().getIntExtra("user_id", 0);
        userDB = new UserDB(this);
        user = userDB.getUserByID(userId);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean flag = false;

                old_pass = et_old_password.getText().toString();
                new_pass = et_new_password.getText().toString();

                if(old_pass.isEmpty()){
                    et_old_password.setError("Fill the old password!");
                    flag = true;
                }
                if(new_pass.isEmpty()) {
                    et_new_password.setError("Fill the new password!");
                    flag = true;
                }

                if(!flag) {

                    if(userDB.checkUsers(user.getUsername(), old_pass)) {

                        if(old_pass.equals(new_pass)) {
                            et_new_password.setError("New password must be different!");
                        }else {
                            userDB.updatePassword(userId, new_pass);
                            Toast.makeText(getApplicationContext(), "Password successfully changed!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }else {
                        et_old_password.setError("Password mismatch!");
                    }

                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}