package com.example.prototypese;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class ProfileActivity extends AppCompatActivity {

    TextView tv_username, tv_email, tv_user_balance;
    ImageButton arrow1, arrow2, arrow3, arrow4;
    UserDB userDB;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_username = findViewById(R.id.tv_username);
        tv_email = findViewById(R.id.tv_email);
        tv_user_balance = findViewById(R.id.tv_user_balance);
        arrow1 = findViewById(R.id.arrow1);
        arrow2 = findViewById(R.id.arrow2);
        arrow3 = findViewById(R.id.arrow3);
        arrow4 = findViewById(R.id.arrow4);

        int userId = getIntent().getIntExtra("user_id", 0);
        renderBottomNavbar(userId);

        userDB = new UserDB(this);
        user = userDB.getUserByID(userId);

        tv_username.setText(user.getUsername());
        tv_email.setText(user.getEmail());
        tv_user_balance.setText("Rp. " + user.getBalance());

        arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TopUpActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

        arrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

        arrow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

                builder.setMessage("Do you want to log out ?")
                        .setCancelable(false)
                        .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })

                        .setNegativeButton("LOGOUT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    }
                });

                alertDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int userId = getIntent().getIntExtra("user_id", 0);
        user = userDB.getUserByID(userId);
        tv_user_balance.setText("Rp. " + user.getBalance());
    }

    private void renderBottomNavbar(int user_id)
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setSelectedItemId(R.id.menuProfile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                Intent intent;
                switch (item.getItemId())
                {
                    case R.id.menuHome:
//                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        break;

                    case R.id.menuSearch:
//                        Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), SearchSurveyActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        break;

                    case R.id.menuSurvey:
//                        Toast.makeText(getApplicationContext(), "Survey", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), YourSurveyActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        break;

                    case R.id.menuHistory:
//                        Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), SurveyHistoryActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        break;

                    case R.id.menuProfile:
//                        Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

}