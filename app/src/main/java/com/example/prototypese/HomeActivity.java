package com.example.prototypese;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class HomeActivity extends AppCompatActivity {

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    RecyclerView rv_survey;
    Vector<Survey> surveyVector;
    Vector<String> selectedFilter;
    SurveyAdapter surveyAdapter;
    FloatingActionButton fab_create;
    SurveyDB surveyDB;
    UserDB userDB;
    Users user;
    TextView tv_welcome, tv_balance;
    Chip c_cat1, c_cat2, c_cat3, c_cat4, c_cat5, c_cat6, c_cat7, c_cat8, c_atoz, c_ztoa, c_ltoh, c_htol;
    Button btn_apply;
    ImageButton ib_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        addDummySurveys();

        userDB = new UserDB(this);
        surveyDB = new SurveyDB(this);
        int user_id = getIntent().getIntExtra("user_id", 0);
        user = userDB.getUserByID(user_id);
        renderBottomNavbar(user_id);
        tv_welcome = findViewById(R.id.tv_welcome);
        tv_balance = findViewById(R.id.tv_balance);
        rv_survey = findViewById(R.id.rv_survey);
        fab_create = findViewById(R.id.fab_create);
        ib_filter = findViewById(R.id.ib_filter);

        tv_welcome.setText("Welcome, " + user.getUsername());
        tv_balance.setText("Your balance: Rp. " + user.getBalance());

        surveyVector = new Vector<>();
        surveyVector = surveyDB.getAllSurvey();

        Collections.reverse(surveyVector);
        surveyAdapter = new SurveyAdapter(this, user_id);
        surveyAdapter.setSurveyVector(surveyVector);

        rv_survey.setAdapter(surveyAdapter);
        rv_survey.setLayoutManager(new LinearLayoutManager(this));

        fab_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Create a Survey", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CreateSurveyActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
            }
        });

        ib_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterPopUp();
            }
        });

    }

    public void filterPopUp() {
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
                    surveyVector.clear();

                    Vector<Survey> newSurveyVector = new Vector<>();
                    newSurveyVector = surveyDB.getAllSurvey();
                    surveyVector.addAll(newSurveyVector);
                    Collections.reverse(surveyVector);
                    
                    surveyAdapter.notifyDataSetChanged();
                }else {
                    if (selectedFilter.get(0).equals("Kesehatan") || selectedFilter.get(0).equals("Pemasaran") || selectedFilter.get(0).equals("Pendidikan") ||
                            selectedFilter.get(0).equals("Net Promotor") || selectedFilter.get(0).equals("Penelitian Pasar") || selectedFilter.get(0).equals("Kepuasan Pelanggan")
                            || selectedFilter.get(0).equals("Kepuasan Pegawai") || selectedFilter.get(0).equals("Perencanaan Acara")) {
                        surveyVector.clear();

                        Vector<Survey> filteredSurveyVector = new Vector<>();
                        filteredSurveyVector = surveyDB.getFilteredSurvey(selectedFilter.get(0));
                        surveyVector.addAll(filteredSurveyVector);

                        if (selectedFilter.size() > 1) {
                            if (selectedFilter.get(1).equals("A ~ Z")) {
                                Collections.sort(surveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        String str1 = survey1.getSurvey_title();
                                        String str2 = survey2.getSurvey_title();
                                        return str1.toLowerCase().compareTo(str2.toLowerCase());
                                    }
                                });
                            }else if (selectedFilter.get(1).equals("Z ~ A")){
                                Collections.sort(surveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        String str1 = survey1.getSurvey_title();
                                        String str2 = survey2.getSurvey_title();
                                        return str2.toLowerCase().compareTo(str1.toLowerCase());
                                    }
                                });
                            }else if (selectedFilter.get(1).equals("Low to High")) {
                                Collections.sort(surveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                        long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                        return (int) (reward_per_participant1 - reward_per_participant2);
                                    }
                                });
                            }else if (selectedFilter.get(1).equals("High to Low")){
                                Collections.sort(surveyVector, new Comparator<Survey>() {
                                    @Override
                                    public int compare(Survey survey1, Survey survey2) {
                                        long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                        long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                        return (int) (reward_per_participant2 - reward_per_participant1);
                                    }
                                });
                            }else {
                                Collections.reverse(surveyVector);
                            }
                        }else {
                            Collections.reverse(surveyVector);
                        }

                        surveyAdapter.notifyDataSetChanged();
                    }else if (selectedFilter.get(0).equals("A ~ Z") || selectedFilter.get(0).equals("Z ~ A")) {
                        if (selectedFilter.size() > 1) {
                            if (selectedFilter.get(1).equals("Kesehatan") || selectedFilter.get(1).equals("Pemasaran") || selectedFilter.get(1).equals("Pendidikan") ||
                                    selectedFilter.get(1).equals("Net Promotor") || selectedFilter.get(1).equals("Penelitian Pasar") || selectedFilter.get(1).equals("Kepuasan Pelanggan")
                                    || selectedFilter.get(1).equals("Kepuasan Pegawai") || selectedFilter.get(1).equals("Perencanaan Acara")) {
                                surveyVector.clear();

                                Vector<Survey> filteredSurveyVector = new Vector<>();
                                filteredSurveyVector = surveyDB.getFilteredSurvey(selectedFilter.get(1));
                                surveyVector.addAll(filteredSurveyVector);
                            }
                        }else {
                            surveyVector.clear();

                            Vector<Survey> newSurveyVector = new Vector<>();
                            newSurveyVector = surveyDB.getAllSurvey();
                            surveyVector.addAll(newSurveyVector);
                        }

                        if (selectedFilter.get(0).equals("A ~ Z")) {
                            Collections.sort(surveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    String str1 = survey1.getSurvey_title();
                                    String str2 = survey2.getSurvey_title();
                                    return str1.toLowerCase().compareTo(str2.toLowerCase());
                                }
                            });

                        }else {
                            Collections.sort(surveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    String str1 = survey1.getSurvey_title();
                                    String str2 = survey2.getSurvey_title();
                                    return str2.toLowerCase().compareTo(str1.toLowerCase());
                                }
                            });

                        }
                        surveyAdapter.notifyDataSetChanged();
                    }else if (selectedFilter.get(0).equals("Low to High") || selectedFilter.get(0).equals("High to Low")) {
                        if (selectedFilter.size() > 1) {
                            if (selectedFilter.get(1).equals("Kesehatan") || selectedFilter.get(1).equals("Pemasaran") || selectedFilter.get(1).equals("Pendidikan") ||
                                    selectedFilter.get(1).equals("Net Promotor") || selectedFilter.get(1).equals("Penelitian Pasar") || selectedFilter.get(1).equals("Kepuasan Pelanggan")
                                    || selectedFilter.get(1).equals("Kepuasan Pegawai") || selectedFilter.get(1).equals("Perencanaan Acara")) {
                                surveyVector.clear();

                                Vector<Survey> filteredSurveyVector = new Vector<>();
                                filteredSurveyVector = surveyDB.getFilteredSurvey(selectedFilter.get(1));
                                surveyVector.addAll(filteredSurveyVector);
                            }
                        }else {
                            surveyVector.clear();

                            Vector<Survey> newSurveyVector = new Vector<>();
                            newSurveyVector = surveyDB.getAllSurvey();
                            surveyVector.addAll(newSurveyVector);
                        }

                        if (selectedFilter.get(0).equals("Low to High")) {
                            Collections.sort(surveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                    long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                    return (int) (reward_per_participant1 - reward_per_participant2);
                                }
                            });

                        }else {
                            Collections.sort(surveyVector, new Comparator<Survey>() {
                                @Override
                                public int compare(Survey survey1, Survey survey2) {
                                    long reward_per_participant1 = survey1.getSurvey_reward() / survey1.getSurvey_max_participant();
                                    long reward_per_participant2 = survey2.getSurvey_reward() / survey2.getSurvey_max_participant();
                                    return (int) (reward_per_participant2 - reward_per_participant1);
                                }
                            });

                        }
                        surveyAdapter.notifyDataSetChanged();
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
        bottomNavigationView.setSelectedItemId(R.id.menuHome);
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
                        finish();
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
                        break;
                }
                return false;
            }
        });
    }

    public void addDummySurveys() {
        surveyDB = new SurveyDB(this);

        Survey survey1 = new Survey(1, 1, "Survey Kesehatan", "Pelaksanaan Manajemen Logistik Alat Kesehatan di Rumah Sakit Umum ABC Tahun 2021", "Author1", "Desc1", "7 Jan 2021", 100000, 0, 10, "Url1");
        surveyDB.insertSurvey(survey1);
        Survey survey2 = new Survey(1, 2, "Survey Net Promotor", "Survey untuk Net Promoter Score Perusahaan ABC", "Author10", "Desc10", "12 Jan 2021", 100000, 0, 10, "Url10");
        surveyDB.insertSurvey(survey2);
        Survey survey3 = new Survey(1, 3, "Survey Net Promotor", "Survey untuk Net Promoter Score Perusahaan XYZ", "Author11", "Desc11", "14 Jan 2021", 200000, 0, 10, "Url11");
        surveyDB.insertSurvey(survey3);
        Survey survey4 = new Survey(1, 4, "Survey Kepuasan Pelanggan", "Survei Kepuasan Pasien Terhadap Pelayanan Inap Puskesmas CDE", "Author16", "Desc16", "20 Jan 2021", 250000, 0, 10, "Url16");
        surveyDB.insertSurvey(survey4);
        Survey survey5 = new Survey(1, 5, "Survey Net Promotor", "Survey untuk Net Promoter Score Perusahaan LMN", "Author12", "Desc12", "21 Jan 2021", 350000, 0, 10, "Url12");
        surveyDB.insertSurvey(survey5);
        Survey survey6 = new Survey(1, 6, "Survey Kepuasan Pegawai", "Pengaruh Kepuasan Kerja Terhadap Kinerja Karyawan PT. IJK", "Author19", "Desc19", "24 Jan 2021", 800000, 0, 10, "Url19");
        surveyDB.insertSurvey(survey6);
        Survey survey7 = new Survey(1, 7, "Survey Penelitian Pasar", "Analisis Brand Telepon Seluler Merk Nokia pada Mahasiswa Universitas DEF", "Author13", "Desc13", "1 Feb 2021", 250000, 0, 10, "Url13");
        surveyDB.insertSurvey(survey7);
        Survey survey8 = new Survey(1, 8, "Survey Kepuasan Pelanggan", "Pengaruh Kualitas Pelayanan Terhadap Kepuasan Pelanggan pada Hotel JKL", "Author17", "Desc17", "6 Feb 2021", 150000, 0, 10, "Url17");
        surveyDB.insertSurvey(survey8);
        Survey survey9 = new Survey(1, 9, "Survey Penelitian Pasar", "Pengaruh Komponen Brand Image Kartu XL Bebas Terhadap Keputusan Pembelian", "Author14", "Desc14", "13 Feb 2021", 500000, 0, 10, "Url14");
        surveyDB.insertSurvey(survey9);
        Survey survey10 = new Survey(1, 10, "Survey Kepuasan Pelanggan", "Analisis Pengaruh Faktor Kualitas Pelayanan Terhadap Kepuasan Pelanggan Minimarket ABC", "Author18", "Desc18", "15 Feb 2021", 250000, 0, 10, "Url18");
        surveyDB.insertSurvey(survey10);
        Survey survey11 = new Survey(1, 11, "Survey Penelitian Pasar", "Analisis Merk yang Dipertimbangkan Konsumen Handphone Android", "Author15", "Desc15", "20 Feb 2021", 400000, 0, 10, "Url15");
        surveyDB.insertSurvey(survey11);
        Survey survey12 = new Survey(1, 12, "Survey Kepuasan Pegawai", "Survei Kepuasan Kerja Karyawan di Bank XYZ", "Author20", "Desc20", "26 Feb 2021", 600000, 0, 10, "Url20");
        surveyDB.insertSurvey(survey12);
        Survey survey13 = new Survey(1, 13, "Survey Pendidikan", "Pelaksanaan Manajemen Kurikulum Pendidikan Inklusif di SDN XX Jawa Timur", "Author7", "Desc7", "2 Apr 2021", 300000, 0, 10, "Url7");
        surveyDB.insertSurvey(survey13);
        Survey survey14 = new Survey(1, 14, "Survey Kesehatan", "Uji Daya Terima dan Nilai Gizi Brownies Singkong", "Author2", "Desc2", "14 Apr 2021", 250000, 0, 10, "Url2");
        surveyDB.insertSurvey(survey14);
        Survey survey15 = new Survey(1, 15, "Survey Kepuasan Pegawai", "Pengaruh Budaya Organisasi Terhadap Kepuasan Kerja Karyawan Bank LMN", "Author21", "Desc21", "17 Apr 2021", 1000000, 0, 10, "Url21");
        surveyDB.insertSurvey(survey15);
        Survey survey16 = new Survey(1, 16, "Survey Kesehatan", "Analisis Kandungan Residu Antibiotik pada Ayam Ras Broiler Serta Penggunaan Antibiotik pada Peternak di Kecamatan BCD Provinsi DEF Tahun 2021", "Author3", "Desc3", "22 Apr 2021", 150000, 0, 10, "Url3");
        surveyDB.insertSurvey(survey16);
        Survey survey17 = new Survey(1, 17, "Survey Pemasaran", "Analisis Pengaruh Diskon, Promosi Dan Kepercayaan Konsumen Terhadap Keputusan Pembelian Pada Pengguna Aplikasi Shopee", "Author4", "Desc4", "3 May 2021", 200000, 0, 10, "Url4");
        surveyDB.insertSurvey(survey17);
        Survey survey18 = new Survey(1, 18, "Survey Pemasaran", "Pengaruh Brand Image Dan Marketing Online Terhadap Keputusan Pembelian", "Author5", "Desc5", "5 May 2021", 100000, 0, 10, "Url5");
        surveyDB.insertSurvey(survey18);
        Survey survey19 = new Survey(1, 19, "Survey Pendidikan", "Meningkatkan Kemampuan Interaksi Sosial terhadap Siswa Kesulitan Belajar Matematika melalui Metode Tutor Sebaya", "Author8", "Desc8", "5 Aug 2021", 200000, 0, 10, "Url8");
        surveyDB.insertSurvey(survey19);
        Survey survey20 = new Survey(1, 20, "Survey Perencanaan Acara", "Analisis Hubungan Cost-Volume-Profit (CVP) untuk Perencanaan Laba Hotel DEF", "Author22", "Desc22", "23 Aug 2021", 300000, 0, 10, "Url22");
        surveyDB.insertSurvey(survey20);
        Survey survey21 = new Survey(1, 21, "Survey Pemasaran", "Daya Tarik Yang Mempengaruhi Kosumen Dalam Melakukan Keputusan Impulse Buying", "Author6", "Desc6", "7 Sep 2021", 150000, 0, 10, "Url6");
        surveyDB.insertSurvey(survey21);
        Survey survey22 = new Survey(1, 22, "Survey Pendidikan", "Rancangan Pembangun Media Pembelajaran Teknologi Layanan Jaringan Berbasis Mobile", "Author9", "Desc9", "15 Oct 2021", 150000, 0, 10, "Url9");
        surveyDB.insertSurvey(survey22);
        Survey survey23 = new Survey(1, 23, "Survey Perencanaan Acara", "Kuesioner untuk Perencanaan Acara Gathering PT. KVN", "Author23", "Desc23", "22 Oct 2021", 200000, 0, 10, "Url23");
        surveyDB.insertSurvey(survey23);
        Survey survey24 = new Survey(1, 24, "Survey Perencanaan Acara", "Survei Rancangan Acara Program 2+2 Universitas QRS", "Author24", "Desc24", "10 Nov 2021", 400000, 0, 10, "Url24");
        surveyDB.insertSurvey(survey24);
    }

}