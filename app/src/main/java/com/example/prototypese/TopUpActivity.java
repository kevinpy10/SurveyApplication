package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TopUpActivity extends AppCompatActivity {

    RadioGroup rg_1;
    RadioButton rb_100, rb_250, rb_500, rb_1000;
    TextView tv_user_balance;
    EditText et_password;
    Button btn_topup, btn_cancel;
    UserDB userDB;
    Users user;
    String password;
    long nominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        rg_1 = findViewById(R.id.rg_1);
        rb_100 = findViewById(R.id.rb_100);
        rb_250 = findViewById(R.id.rb_250);
        rb_500 = findViewById(R.id.rb_500);
        rb_1000 = findViewById(R.id.rb_1000);
        et_password = findViewById(R.id.et_password);
        tv_user_balance = findViewById(R.id.tv_user_balance);
        btn_topup = findViewById(R.id.btn_topup);
        btn_cancel = findViewById(R.id.btn_cancel);

        int userId = getIntent().getIntExtra("user_id", 0);
        userDB = new UserDB(this);
        user = userDB.getUserByID(userId);

        tv_user_balance.setText("Rp. " + user.getBalance());

        btn_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                password = et_password.getText().toString();

                if (!rb_100.isChecked() && !rb_250.isChecked() && !rb_500.isChecked() && !rb_1000.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Top up nominal must be selected!", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()) {
                    et_password.setError("Fill the password!");
                }else if (!userDB.checkUsers(user.getUsername(), password)) {
                    et_password.setError("Password mismatch!");
                }else {

                    if (rb_100.isChecked()) {
                        nominal = 100000;
                    }else if(rb_250.isChecked()) {
                        nominal = 250000;
                    }else if(rb_500.isChecked()) {
                        nominal = 500000;
                    }else {
                        nominal = 1000000;
                    }

                    Toast.makeText(getApplicationContext(), "Top up sucessful!", Toast.LENGTH_SHORT).show();
                    userDB.updateWallet(userId, user.getBalance(), nominal, 1);
                    finish();
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