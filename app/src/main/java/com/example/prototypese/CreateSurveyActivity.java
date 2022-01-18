
package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateSurveyActivity extends AppCompatActivity {

    EditText et_survey_title, et_survey_desc, et_survey_reward, et_survey_participant, et_survey_url;
    Button btn_share;
    AutoCompleteTextView actv_category;
    Date date;
    UserDB userDB;
    Users user;
    SurveyDB surveyDB;
    Survey survey;
    String category;
    String[] categories = {"Survey Kesehatan", "Survey Pemasaran", "Survey Pendidikan", "Survey Net Promotor", "Survey Penelitian Pasar", "Survey Kepuasan Pelanggan", "Survey Kepuasan Pegawai", "Survey Perencanaan Acara"};
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        et_survey_title = findViewById(R.id.et_survey_title);
        et_survey_desc = findViewById(R.id.et_survey_desc);
        et_survey_reward = findViewById(R.id.et_survey_reward);
        et_survey_participant = findViewById(R.id.et_survey_participant);
        et_survey_url = findViewById(R.id.et_survey_url);
        btn_share = findViewById(R.id.btn_share);
        actv_category = findViewById(R.id.actv_category);

        surveyDB = new SurveyDB(this);
        date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String created_at = dateFormat.format(date);

        userDB = new UserDB(this);
        int user_id = getIntent().getIntExtra("user_id", 0);
        user = userDB.getUserByID(user_id);
        String author = getIntent().getStringExtra("username");

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, categories);
                actv_category.setAdapter(arrayAdapter);
                actv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        category = adapterView.getItemAtPosition(i).toString();
                    }
                });

                boolean flag = false;

                long reward = 0, reward_per_participant = 0;
                int max_participant = 0;

                String title = et_survey_title.getText().toString();
                String desc = et_survey_desc.getText().toString();
                String url = et_survey_url.getText().toString();

                if (title.isEmpty()) {
                    et_survey_title.setError("Title field must not be empty!");
                    flag = true;
                }

                if (desc.isEmpty()) {
                    et_survey_desc.setError("Description field must not be empty!");
                    flag = true;
                }

                if (et_survey_reward.getText().toString().isEmpty()) {
                    et_survey_reward.setError("Reward field must not be empty!");
                    flag = true;
                }else {
                    reward = Long.parseLong(et_survey_reward.getText().toString());
                }

                if (et_survey_participant.getText().toString().isEmpty()) {
                    et_survey_participant.setError("Participant field must not be empty!");
                    flag = true;
                }else {
                    max_participant = Integer.parseInt(et_survey_participant.getText().toString());
                }

                if (url.isEmpty()) {
                    et_survey_url.setError("Url field must not be empty!");
                    flag = true;
                }

                if (category == null) {
                    Toast.makeText(getApplicationContext(), "You must choose one of the categories!", Toast.LENGTH_SHORT).show();
                    flag = true;
                }

                if (!flag) {
                    reward_per_participant = reward / max_participant;
                    if (reward > user.getBalance()) {
                        Toast.makeText(getApplicationContext(), "Balance insufficient!", Toast.LENGTH_SHORT).show();
                    }else if (reward_per_participant < 10000) {
                        Toast.makeText(getApplicationContext(), "Minimum reward/participant must be Rp. 10.000!", Toast.LENGTH_SHORT).show();
                    }else {
                        survey = new Survey();
                        survey.setUserId(user_id);
                        survey.setSurvey_category(category);
                        survey.setSurvey_title(title);
                        survey.setSurvey_author(author);
                        survey.setSurvey_desc(desc);
                        survey.setCreated_at(created_at);
                        survey.setSurvey_reward(reward);
                        survey.setSurvey_participant(0);
                        survey.setSurvey_max_participant(max_participant);
                        survey.setSurvey_url(url);
                        surveyDB.insertSurvey(survey);
                        userDB.updateWallet(user_id, user.getBalance(), reward, 2);
                        Toast.makeText(getApplicationContext(),"Survey has been shared",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), YourSurveyActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, categories);
        actv_category.setAdapter(arrayAdapter);
        actv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
            }
        });
    }
}