package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SurveyCompletedActivity extends AppCompatActivity {

    int user_id, survey_id, participant;
    long reward;
    TextView tv_reward;
	Button btn_yeay;
    Date date;
	SurveyHistoryDB surveyHistoryDB;
	SurveyHistory surveyHistory;
	SurveyDB surveyDB;
	Survey survey;
	UserDB userDB;
	Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_completed);
        userDB = new UserDB(this);
        surveyDB = new SurveyDB(this);
        surveyHistoryDB = new SurveyHistoryDB(this);

        date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String filled_at = dateFormat.format(date);

        user_id = getIntent().getIntExtra("user_id", 0);
        user = userDB.getUserByID(user_id);
        reward = getIntent().getLongExtra("reward", 0);
        survey_id = getIntent().getIntExtra("survey_id", 0);
        survey = surveyDB.getSurvey(survey_id);
        participant = getIntent().getIntExtra("participant", 0);

        tv_reward = findViewById(R.id.tv_reward);
		btn_yeay = findViewById(R.id.btn_yeay);

		tv_reward.setText("Rp. " + reward);

		surveyHistory = new SurveyHistory(user_id, survey_id, filled_at, reward, survey.getSurvey_title(), survey.getSurvey_category());

		btn_yeay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SurveyHistoryActivity.class);
                surveyHistoryDB.insertSurveyHistory(surveyHistory);
                userDB.updateWallet(user_id, user.getBalance(), reward, 1);
                surveyDB.updateSurvey(survey_id, participant);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

    }
}
