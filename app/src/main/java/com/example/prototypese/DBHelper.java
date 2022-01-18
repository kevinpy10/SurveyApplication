package com.example.prototypese;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "surveyDB";
    public static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "Users";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_USER_USERNAME = "username";
    public static final String FIELD_USER_PASSWORD = "password";
    public static final String FIELD_USER_PHONE = "phone";
    public static final String FIELD_USER_EMAIL = "email";
    public static final String FIELD_USER_BALANCE = "balance";

    private static final String CREATE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +" (" +
            FIELD_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_USER_USERNAME + " TEXT," +
            FIELD_USER_PASSWORD + " TEXT," +
            FIELD_USER_PHONE + " INTEGER," +
            FIELD_USER_EMAIL + " TEXT," +
            FIELD_USER_BALANCE + " INTEGER)";

    public static final String TABLE_SURVEY = "Survey";
    public static final String FIELD_SURVEY_ID = "survey_id";
    public static final String FIELD_SURVEY_CATEGORY = "survey_category";
    public static final String FIELD_SURVEY_TITLE = "survey_title";
    public static final String FIELD_SURVEY_AUTHOR = "survey_author";
    public static final String FIELD_SURVEY_DESC = "survey_desc";
    public static final String FIELD_SURVEY_CREATED_AT = "created_at";
    public static final String FIELD_SURVEY_REWARD = "survey_reward";
    public static final String FIELD_SURVEY_PARTICIPANT = "survey_participant";
    public static final String FIELD_SURVEY_MAX_PARTICIPANT = "survey_max_participant";
    public static final String FIELD_SURVEY_URL = "survey_url";

    private static final String CREATE_SURVEY = "CREATE TABLE IF NOT EXISTS " + TABLE_SURVEY +" (" +
            FIELD_SURVEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_USER_ID + " INTEGER," +
            FIELD_SURVEY_CATEGORY + " TEXT," +
            FIELD_SURVEY_TITLE + " TEXT," +
            FIELD_SURVEY_AUTHOR + " TEXT," +
            FIELD_SURVEY_DESC + " TEXT," +
            FIELD_SURVEY_CREATED_AT + " TEXT," +
            FIELD_SURVEY_REWARD + " INTEGER," +
            FIELD_SURVEY_PARTICIPANT + " INTEGER," +
            FIELD_SURVEY_MAX_PARTICIPANT + " INTEGER," +
            FIELD_SURVEY_URL + " TEXT," +
            "FOREIGN KEY (" + FIELD_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + FIELD_USER_ID + "))";

    public static final String TABLE_SURVEY_HISTORY = "Survey_History";
    public static final String FIELD_SURVEY_FILLED_AT = "filled_at";
    public static final String FIELD_SURVEY_FILLED_REWARD = "filled_reward";
    public static final String FIELD_SURVEY_HISTORY_TITLE = "history_title";
    public static final String FIELD_SURVEY_HISTORY_CATEGORY = "history_category";

    private static final String CREATE_SURVEY_HISTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_SURVEY_HISTORY +" (" +
            FIELD_USER_ID + " INTEGER REFERENCES " + TABLE_USERS + "(" + FIELD_USER_ID + ")," +
            FIELD_SURVEY_ID + " INTEGER REFERENCES "  + TABLE_SURVEY + "(" + FIELD_SURVEY_ID + ")," +
            FIELD_SURVEY_FILLED_AT + " TEXT," +
            FIELD_SURVEY_FILLED_REWARD + " INTEGER," +
            FIELD_SURVEY_HISTORY_TITLE + " TEXT," +
            FIELD_SURVEY_HISTORY_CATEGORY + " TEXT," +
            "PRIMARY KEY(" + FIELD_USER_ID + ", " +FIELD_SURVEY_ID + "))";


    public DBHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db ) {
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_SURVEY);
        db.execSQL(CREATE_SURVEY_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
