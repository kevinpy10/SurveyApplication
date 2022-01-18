package com.example.prototypese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.chip.Chip;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class YourSurveyActivity extends AppCompatActivity {

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    RecyclerView rv_your_survey;
    Vector<Survey> yourSurveyVector;
    Vector<String> selectedFilter;
    YourSurveyAdapter yourSurveyAdapter;
    SurveyDB surveyDB;
    Chip c_cat1, c_cat2, c_cat3, c_cat4, c_cat5, c_cat6, c_cat7, c_cat8, c_atoz, c_ztoa, c_ltoh, c_htol;
    Button btn_apply;
    ImageButton ib_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_survey);

        int userId = getIntent().getIntExtra("user_id", 0);
        renderBottomNavbar(userId);
        surveyDB = new SurveyDB(this);
        rv_your_survey = findViewById(R.id.rv_your_survey);
        ib_filter = findViewById(R.id.ib_filter);

        yourSurveyVector = new Vector<>();
        yourSurveyVector = surveyDB.getYourSurvey(userId);

        if (yourSurveyVector.isEmpty()) {
            Toast.makeText(getApplicationContext(), "You haven't create any survey", Toast.LENGTH_SHORT).show();
        }

        Collections.reverse(yourSurveyVector);
        yourSurveyAdapter = new YourSurveyAdapter(this);
        yourSurveyAdapter.setYourSurveyVector(yourSurveyVector);

        rv_your_survey.setAdapter(yourSurveyAdapter);
        rv_your_survey.setLayoutManager(new LinearLayoutManager(this));

        ib_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterPopUp(userId);
            }
        });
    }

    public void filterPopUp(int userId) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View filterPopUpView = getLayoutInflater().inflate(R.layout.filter, null);

        c_cat1 = filterPopUpView.findViewById(R.id.c_cat1);
        c_cat2 = filterPopUpView.findViewById(R.id.c_cat2);
        c_cat3 = filterPopUpView.findViewById(R.id.c_cat3);
        c_cat4 = filterPopUpView.findViewById(R.id.c_cat4);
        c_cat5 = filterPopUpView.findViewById(R.id.c_cat5);
        c_cat6 = filterPopUpView.findViewById(R.id.c_cat6);
        c_cat7 = filterPopUpView.findViewById(R.id.c_cat7);
        c_cat8 = filterPopUpView.findViewById(R.id.c_cat8);
        c_atoz = filterPopUpView.findViewById(R.id.c_atoz);
        c_ztoa = filterPopUpView.findViewById(R.id.c_ztoa);
        c_ltoh = filterPopUpView.findViewById(R.id.c_ltoh);
        c_htol = filterPopUpView.findViewById(R.id.c_htol);
        btn_apply = filterPopUpView.findViewById(R.id.btn_apply);

        selectedFilter = new Vector<>();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    selectedFilter.add(compoundButton.getText().toString());
                }else {
                    selectedFilter.remove(compoundButton.getText().toString());
                }
            }
        };

        c_cat1.setOnCheckedChangeListener(checkedChangeListener);
        c_cat2.setOnCheckedChangeListener(checkedChangeListener);
        c_cat3.setOnCheckedChangeListener(checkedChangeListener);
        c_cat4.setOnCheckedChangeListener(checkedChangeListener);
        c_cat5.setOnCheckedChangeListener(checkedChangeListener);
        c_cat6.setOnCheckedChangeListener(checkedChangeListener);
        c_cat7.setOnCheckedChangeListener(checkedChangeListener);
        c_cat8.setOnCheckedChangeListener(checkedChangeListener);
        c_atoz.setOnCheckedChangeListener(checkedChangeListener);
        c_ztoa.setOnCheckedChangeListener(checkedChangeListener);
        c_ltoh.setOnCheckedChangeListener(checkedChangeListener);
        c_htol.setOnCheckedChangeListener(checkedChangeListener);

        dialogBuilder.setView(filterPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedFilter.isEmpty()) {
                    yourSurveyVector.clear();

                    Vector<Survey> newSurveyVector = new Vector<>();
                    newSurveyVector = surveyDB.getYourSurvey(userId);
                    yourSurveyVector.addAll(newSurveyVector);
                    Collections.reverse(yourSurveyVector);

                    if (yourSurveyVector.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "You haven't create any survey", Toast.LENGTH_SHORT).show();
                    }

                    yourSurveyAdapter.notifyDataSetChanged();
                }else {
                    if (selectedFilter.get(0).equals("Kesehatan") || selectedFilter.get(0).equals("Pemasaran") || selectedFilter.get(0).equals("Pendidikan") ||
                            selectedFilter.get(0).equals("Net Promotor") || selectedFilter.get(0).equals("Penelitian Pasar") || selectedFilter.get(0).equals("Kepuasan Pelanggan")
                            || selectedFilter.get(0).equals("Kepuasan Pegawai") || selectedFilter.get(0).equals("Perencanaan Acara")) {
                        yourSurveyVector.clear();

                        Vector<Survey> filteredSurveyVector = new Vector<>();
                        filteredSurveyVector = surveyDB.getFilteredYourSurvey(selectedFilter.get(0), userId);
                        yourSurveyVector.addAll(filteredSurveyVector);

                        if (yourSurveyVector.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "You haven't create any survey", Toast.LENGTH_SHORT).show();
                        }

                        if (selectedFilter.size() > 1) {
                            if (selectedFilter.get(1).equals("A ~ Z")) {
                                Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        String str1 = survey1.getSurvey_title();
                                        String str2 = survey2.getSurvey_title();
                                        return str1.toLowerCase().compareTo(str2.toLowerCase());
                                    }
                                });
                            } else if (selectedFilter.get(1).equals("Z ~ A")) {
                                Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        String str1 = survey1.getSurvey_title();
                                        String str2 = survey2.getSurvey_title();
                                        return str2.toLowerCase().compareTo(str1.toLowerCase());
                                    }
                                });
                            } else if (selectedFilter.get(1).equals("Low to High")) {
                                Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                        long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                        return (int) (reward_per_participant1 - reward_per_participant2);
                                    }
                                });
                            } else if (selectedFilter.get(1).equals("High to Low")) {
                                Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                        long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                        return (int) (reward_per_participant2 - reward_per_participant1);
                                    }
                                });
                            } else {
                                Collections.reverse(yourSurveyVector);
                            }
                        } else {
                            Collections.reverse(yourSurveyVector);
                        }

                        yourSurveyAdapter.notifyDataSetChanged();
                    } else if (selectedFilter.get(0).equals("A ~ Z") || selectedFilter.get(0).equals("Z ~ A")) {
                        if (selectedFilter.size() > 1) {
                            if (selectedFilter.get(1).equals("Kesehatan") || selectedFilter.get(1).equals("Pemasaran") || selectedFilter.get(1).equals("Pendidikan") ||
                                    selectedFilter.get(1).equals("Net Promotor") || selectedFilter.get(1).equals("Penelitian Pasar") || selectedFilter.get(1).equals("Kepuasan Pelanggan")
                                    || selectedFilter.get(1).equals("Kepuasan Pegawai") || selectedFilter.get(1).equals("Perencanaan Acara")) {
                                yourSurveyVector.clear();

                                Vector<Survey> filteredSurveyVector = new Vector<>();
                                filteredSurveyVector = surveyDB.getFilteredYourSurvey(selectedFilter.get(1), userId);
                                yourSurveyVector.addAll(filteredSurveyVector);

                                if (yourSurveyVector.isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "You haven't create any survey", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            yourSurveyVector.clear();

                            Vector<Survey> newSurveyVector = new Vector<>();
                            newSurveyVector = surveyDB.getYourSurvey(userId);
                            yourSurveyVector.addAll(newSurveyVector);

                            if (yourSurveyVector.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "You haven't create any survey", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedFilter.get(0).equals("A ~ Z")) {
                            Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    String str1 = survey1.getSurvey_title();
                                    String str2 = survey2.getSurvey_title();
                                    return str1.toLowerCase().compareTo(str2.toLowerCase());
                                }
                            });

                        } else {
                            Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    String str1 = survey1.getSurvey_title();
                                    String str2 = survey2.getSurvey_title();
                                    return str2.toLowerCase().compareTo(str1.toLowerCase());
                                }
                            });

                        }
                        yourSurveyAdapter.notifyDataSetChanged();
                    } else if (selectedFilter.get(0).equals("Low to High") || selectedFilter.get(0).equals("High to Low")) {
                        if (selectedFilter.size() > 1) {
                            if (selectedFilter.get(1).equals("Kesehatan") || selectedFilter.get(1).equals("Pemasaran") || selectedFilter.get(1).equals("Pendidikan") ||
                                    selectedFilter.get(1).equals("Net Promotor") || selectedFilter.get(1).equals("Penelitian Pasar") || selectedFilter.get(1).equals("Kepuasan Pelanggan")
                                    || selectedFilter.get(1).equals("Kepuasan Pegawai") || selectedFilter.get(1).equals("Perencanaan Acara")) {
                                yourSurveyVector.clear();

                                Vector<Survey> filteredSurveyVector = new Vector<>();
                                filteredSurveyVector = surveyDB.getFilteredYourSurvey(selectedFilter.get(1), userId);
                                yourSurveyVector.addAll(filteredSurveyVector);

                                if (yourSurveyVector.isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "You haven't create any survey", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            yourSurveyVector.clear();

                            Vector<Survey> newSurveyVector = new Vector<>();
                            newSurveyVector = surveyDB.getYourSurvey(userId);
                            yourSurveyVector.addAll(newSurveyVector);

                            if (yourSurveyVector.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "You haven't create any survey", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedFilter.get(0).equals("Low to High")) {
                            Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                    long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                    return (int) (reward_per_participant1 - reward_per_participant2);
                                }
                            });

                        } else {
                            Collections.sort(yourSurveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                    long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                    return (int) (reward_per_participant2 - reward_per_participant1);
                                }
                            });

                        }
                        yourSurveyAdapter.notifyDataSetChanged();
                    }
                }
                dialog.dismiss();
            }
        });
    }

    private void renderBottomNavbar(int user_id)
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setSelectedItemId(R.id.menuSurvey);
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
                        finish();
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
                        break;
                }
                return false;
            }
        });
    }
}