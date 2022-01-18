package com.example.prototypese;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Vector;

public class SurveyDB {
    private DBHelper dbHelper;

    public SurveyDB(Context ctx) {
        dbHelper = new DBHelper(ctx);
    }

    public void insertSurvey(Survey survey) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_USER_ID, survey.getUserId());
        cv.put(DBHelper.FIELD_SURVEY_CATEGORY, survey.getSurvey_category());
        cv.put(DBHelper.FIELD_SURVEY_TITLE, survey.getSurvey_title());
        cv.put(DBHelper.FIELD_SURVEY_AUTHOR, survey.getSurvey_author());
        cv.put(DBHelper.FIELD_SURVEY_DESC, survey.getSurvey_desc());
        cv.put(DBHelper.FIELD_SURVEY_CREATED_AT, survey.getCreated_at());
        cv.put(DBHelper.FIELD_SURVEY_REWARD, survey.getSurvey_reward());
        cv.put(DBHelper.FIELD_SURVEY_PARTICIPANT, survey.getSurvey_participant());
        cv.put(DBHelper.FIELD_SURVEY_MAX_PARTICIPANT, survey.getSurvey_max_participant());
        cv.put(DBHelper.FIELD_SURVEY_URL, survey.getSurvey_url());

        db.insert(DBHelper.TABLE_SURVEY, null, cv);

        db.close();
    }

    public Vector<Survey> getYourSurvey(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Vector<Survey> yourSurveyVector = new Vector<>();

        String selection = "userId = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(DBHelper.TABLE_SURVEY, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int survey_id = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_ID));
            String category = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CATEGORY));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_TITLE));
            String author = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_AUTHOR));
            String desc = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_DESC));
            String created_at = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CREATED_AT));
            long reward = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_REWARD));
            int participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_PARTICIPANT));
            int max_participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_MAX_PARTICIPANT));
            String url = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_URL));

            Survey survey = new Survey();
            survey.setSurvey_id(survey_id);
            survey.setUserId(userId);
            survey.setSurvey_category(category);
            survey.setSurvey_title(title);
            survey.setSurvey_author(author);
            survey.setSurvey_desc(desc);
            survey.setCreated_at(created_at);
            survey.setSurvey_reward(reward);
            survey.setSurvey_participant(participant);
            survey.setSurvey_max_participant(max_participant);
            survey.setSurvey_url(url);

            yourSurveyVector.add(survey);
        }

        cursor.close();
        db.close();
        dbHelper.close();
        return yourSurveyVector;
    }

    public Vector<Survey> getAllSurvey() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Vector<Survey> allSurveyVector = new Vector<>();

        Cursor cursor = db.query(DBHelper.TABLE_SURVEY, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int survey_id = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_USER_ID));
            String category = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CATEGORY));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_TITLE));
            String author = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_AUTHOR));
            String desc = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_DESC));
            String created_at = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CREATED_AT));
            long reward = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_REWARD));
            int participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_PARTICIPANT));
            int max_participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_MAX_PARTICIPANT));
            String url = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_URL));

            Survey survey = new Survey();
            survey.setSurvey_id(survey_id);
            survey.setUserId(userId);
            survey.setSurvey_category(category);
            survey.setSurvey_title(title);
            survey.setSurvey_author(author);
            survey.setSurvey_desc(desc);
            survey.setCreated_at(created_at);
            survey.setSurvey_reward(reward);
            survey.setSurvey_participant(participant);
            survey.setSurvey_max_participant(max_participant);
            survey.setSurvey_url(url);

            allSurveyVector.add(survey);
        }

        cursor.close();
        db.close();
        dbHelper.close();
        return allSurveyVector;
    }

    public Vector<Survey> getFilteredSurvey(String category) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Vector<Survey> filteredSurveyVector = new Vector<>();

        String selection = "survey_category = ?";
        String[] selectionArgs = {"Survey " + category};

        Cursor cursor = db.query(DBHelper.TABLE_SURVEY, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int survey_id = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_USER_ID));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_TITLE));
            String author = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_AUTHOR));
            String desc = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_DESC));
            String created_at = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CREATED_AT));
            long reward = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_REWARD));
            int participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_PARTICIPANT));
            int max_participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_MAX_PARTICIPANT));
            String url = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_URL));

            Survey survey = new Survey();
            survey.setSurvey_id(survey_id);
            survey.setUserId(userId);
            survey.setSurvey_category("Survey " + category);
            survey.setSurvey_title(title);
            survey.setSurvey_author(author);
            survey.setSurvey_desc(desc);
            survey.setCreated_at(created_at);
            survey.setSurvey_reward(reward);
            survey.setSurvey_participant(participant);
            survey.setSurvey_max_participant(max_participant);
            survey.setSurvey_url(url);

            filteredSurveyVector.add(survey);
        }

        cursor.close();
        db.close();
        dbHelper.close();
        return filteredSurveyVector;
    }

    public Vector<Survey> getFilteredYourSurvey(String category, int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Vector<Survey> filteredSurveyVector = new Vector<>();

        String selection = "survey_category=? AND userId=?";
        String[] selectionArgs = {"Survey " + category, String.valueOf(userId)};

        Cursor cursor = db.query(DBHelper.TABLE_SURVEY, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int survey_id = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_ID));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_TITLE));
            String author = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_AUTHOR));
            String desc = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_DESC));
            String created_at = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CREATED_AT));
            long reward = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_REWARD));
            int participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_PARTICIPANT));
            int max_participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_MAX_PARTICIPANT));
            String url = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_URL));

            Survey survey = new Survey();
            survey.setSurvey_id(survey_id);
            survey.setUserId(userId);
            survey.setSurvey_category("Survey " + category);
            survey.setSurvey_title(title);
            survey.setSurvey_author(author);
            survey.setSurvey_desc(desc);
            survey.setCreated_at(created_at);
            survey.setSurvey_reward(reward);
            survey.setSurvey_participant(participant);
            survey.setSurvey_max_participant(max_participant);
            survey.setSurvey_url(url);

            filteredSurveyVector.add(survey);
        }

        cursor.close();
        db.close();
        dbHelper.close();
        return filteredSurveyVector;
    }

    public void updateSurvey (int survey_id, int participant) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.FIELD_SURVEY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(survey_id)};

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_SURVEY_PARTICIPANT, participant + 1);

        db.update(DBHelper.TABLE_SURVEY, cv, selection, selectionArgs);
    }

    public Survey getSurvey(int survey_id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Survey survey = null;

        String selection = DBHelper.FIELD_SURVEY_ID + "=?";
        String[] selectionArgs = {String.valueOf(survey_id)};

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_SURVEY, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToNext())
        {
            int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_USER_ID));
            String category = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CATEGORY));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_TITLE));
            String author = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_AUTHOR));
            String desc = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_DESC));
            String created_at = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_CREATED_AT));
            long reward = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_REWARD));
            int participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_PARTICIPANT));
            int max_participant = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_MAX_PARTICIPANT));
            String url = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_URL));

            survey = new Survey();
            survey.setSurvey_id(survey_id);
            survey.setUserId(userId);
            survey.setSurvey_category(category);
            survey.setSurvey_title(title);
            survey.setSurvey_author(author);
            survey.setSurvey_desc(desc);
            survey.setCreated_at(created_at);
            survey.setSurvey_reward(reward);
            survey.setSurvey_participant(participant);
            survey.setSurvey_max_participant(max_participant);
            survey.setSurvey_url(url);
        }

        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();

        return survey;
    }

}
