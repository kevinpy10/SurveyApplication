package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.Collections;
import java.util.Vector;

public class SearchSurveyActivity extends AppCompatActivity {

    RecyclerView rv_survey;
    Vector<Survey> surveyVector;
    SearchSurveyAdapter searchSurveyAdapter;
    SurveyDB surveyDB;
    UserDB userDB;
    Users user;
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_survey);

        userDB = new UserDB(this);
        surveyDB = new SurveyDB(this);
        int user_id = getIntent().getIntExtra("user_id", 0);
        user = userDB.getUserByID(user_id);

        rv_survey = findViewById(R.id.rv_survey);
        et_search = findViewById(R.id.et_search);

        surveyVector = new Vector<>();
        surveyVector = surveyDB.getAllSurvey();

        Collections.reverse(surveyVector);
        searchSurveyAdapter = new SearchSurveyAdapter(this, user_id);
        searchSurveyAdapter.setSurveyVector(surveyVector);

        rv_survey.setAdapter(searchSurveyAdapter);
        rv_survey.setLayoutManager(new LinearLayoutManager(this));

//        et_search.onCheckIsTextEditor()

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                searchSurveyAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }

}