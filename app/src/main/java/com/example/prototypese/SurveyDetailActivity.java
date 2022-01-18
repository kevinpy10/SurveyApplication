package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class SurveyDetailActivity extends AppCompatActivity {

    TextView tv_category_content, tv_title_content, tv_description_content, tv_reward_content, tv_participant_content, tv_author_content;
    String type, category, title, desc, author, url;
    long reward, reward_per_participant;
    int participant, max_participant, user_id, survey_id;
    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);
        type = getIntent().getStringExtra("type");
        user_id = getIntent().getIntExtra("user_id", 0);
        renderBottomNavbar(user_id);
        survey_id = getIntent().getIntExtra("survey_id", 0);
        category = getIntent().getStringExtra("category");
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        author = getIntent().getStringExtra("author");
        reward = getIntent().getLongExtra("reward", 0);
        participant = getIntent().getIntExtra("participant", 0);
        max_participant = getIntent().getIntExtra("max_participant", 0);
        url = getIntent().getStringExtra("url");
        reward_per_participant = reward / max_participant;

        tv_category_content = findViewById(R.id.tv_category_content);
        tv_title_content = findViewById(R.id.tv_title_content);
        tv_description_content = findViewById(R.id.tv_description_content);
        tv_reward_content = findViewById(R.id.tv_reward_content);
        tv_participant_content = findViewById(R.id.tv_participant_content);
        tv_author_content = findViewById(R.id.tv_author_content);
        btn_start = findViewById(R.id.btn_start);

        tv_category_content.setText(category);
        tv_title_content.setText(title);
        tv_description_content.setText(desc);
        tv_reward_content.setText("Rp. " + reward_per_participant + " / Participant");
        tv_participant_content.setText(participant + " / " + max_participant + " Participants");
        tv_author_content.setText(author);

        if (type.equals("your_survey")) {
            btn_start.setText("Okay");
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else {
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("survey_id", survey_id);
                    intent.putExtra("reward", reward_per_participant);
                    intent.putExtra("participant", participant);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
        }

    }

    private void renderBottomNavbar(int user_id)
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                Intent intent;
                switch (item.getItemId())
                {
                    case R.id.menuHome:
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.menuSearch:
                        intent = new Intent(getApplicationContext(), SearchSurveyActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        break;

                    case R.id.menuSurvey:
                        intent = new Intent(getApplicationContext(), YourSurveyActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.menuHistory:
                        intent = new Intent(getApplicationContext(), SurveyHistoryActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.menuProfile:
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