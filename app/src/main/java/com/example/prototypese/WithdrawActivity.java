package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WithdrawActivity extends AppCompatActivity {

    TextView tv_user_balance;
    EditText et_amount, et_account_num;
    Button btn_withdraw, btn_cancel;
    UserDB userDB;
    Users user;
    String amount, account_num;
    Long amount_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        tv_user_balance = findViewById(R.id.tv_user_balance);
        et_amount = findViewById(R.id.et_amount);
        et_account_num = findViewById(R.id.et_account_num);
        btn_withdraw = findViewById(R.id.btn_withdraw);
        btn_cancel = findViewById(R.id.btn_cancel);

        int userId = getIntent().getIntExtra("user_id", 0);
        userDB = new UserDB(this);
        user = userDB.getUserByID(userId);

        tv_user_balance.setText("Rp. " + user.getBalance());

        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean flag = false;

                amount = et_amount.getText().toString();
                account_num = et_account_num.getText().toString();

                if(amount.isEmpty()){
                    et_amount.setError("Fill the withdrawal amount!");
                    flag = true;
                }
                if(account_num.isEmpty()) {
                    et_account_num.setError("Fill the account number!");
                    flag = true;
                }

                if(!flag) {

                    amount_num = Long.parseLong(amount);

                    if(amount_num > user.getBalance()) {
                        Toast.makeText(getApplicationContext(), "Your balance is insufficient!", Toast.LENGTH_SHORT).show();
                        et_amount.setError("Balance insufficient!");
                    }else {
                        Toast.makeText(getApplicationContext(), "Withdraw successful!", Toast.LENGTH_SHORT).show();
                        userDB.updateWallet(userId, user.getBalance(), amount_num, 2);
                        finish();
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