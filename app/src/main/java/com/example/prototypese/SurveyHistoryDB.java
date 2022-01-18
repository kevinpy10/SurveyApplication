package com.example.prototypese;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class SurveyHistoryDB {

    private DBHelper dbHelper;

    public SurveyHistoryDB(Context ctx) {
        dbHelper = new DBHelper(ctx);
    }

    public void insertSurveyHistory(SurveyHistory survey_history) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_USER_ID, survey_history.getUserId());
        cv.put(DBHelper.FIELD_SURVEY_ID, survey_history.getSurvey_id());
        cv.put(DBHelper.FIELD_SURVEY_FILLED_AT, survey_history.getFilled_at());
        cv.put(DBHelper.FIELD_SURVEY_FILLED_REWARD, survey_history.getSurvey_filled_reward());
        cv.put(DBHelper.FIELD_SURVEY_HISTORY_TITLE, survey_history.getHistory_title());
        cv.put(DBHelper.FIELD_SURVEY_HISTORY_CATEGORY, survey_history.getHistory_category());

        db.insert(DBHelper.TABLE_SURVEY_HISTORY, null, cv);

        db.close();
    }

    public Vector<SurveyHistory> getSurveyHistory(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Vector<SurveyHistory> surveyHistoryVector = new Vector<>();

        String selection = "userId = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(DBHelper.TABLE_SURVEY_HISTORY, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int survey_id = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_ID));
            String filled_at = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_FILLED_AT));
            long survey_filled_reward = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_FILLED_REWARD));
            String history_title = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_HISTORY_TITLE));
            String history_category = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_HISTORY_CATEGORY));

            SurveyHistory surveyHistory = new SurveyHistory();
            surveyHistory.setUserId(userId);
            surveyHistory.setSurvey_id(survey_id);
            surveyHistory.setFilled_at(filled_at);
            surveyHistory.setSurvey_filled_reward(survey_filled_reward);
            surveyHistory.setHistory_title(history_title);
            surveyHistory.setHistory_category(history_category);

            surveyHistoryVector.add(surveyHistory);
        }

        cursor.close();
        db.close();
        dbHelper.close();
        return surveyHistoryVector;
    }

    public Vector<SurveyHistory> getFilteredSurveyHistory(String category, int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Vector<SurveyHistory> filteredSurveyHistoryVector = new Vector<>();

        String selection = "history_category=? AND userId=?";
        String[] selectionArgs = {"Survey " + category, String.valueOf(userId)};

        Cursor cursor = db.query(DBHelper.TABLE_SURVEY_HISTORY, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int survey_id = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_ID));
            String filled_at = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_FILLED_AT));
            long survey_filled_reward = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_FILLED_REWARD));
            String history_title = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_SURVEY_HISTORY_TITLE));

            SurveyHistory surveyHistory = new SurveyHistory();
            surveyHistory.setUserId(userId);
            surveyHistory.setSurvey_id(survey_id);
            surveyHistory.setFilled_at(filled_at);
            surveyHistory.setSurvey_filled_reward(survey_filled_reward);
            surveyHistory.setHistory_title(history_title);
            surveyHistory.setHistory_category("Survey " + category);

            filteredSurveyHistoryVector.add(surveyHistory);
        }

        cursor.close();
        db.close();
        dbHelper.close();
        return filteredSurveyHistoryVector;
    }

    public boolean checkSurvey(int user_id, int survey_id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "userId=? AND survey_id=?";
        String[] selectionargs = {String.valueOf(user_id), String.valueOf(survey_id)};

        Cursor cursor = db.query(DBHelper.TABLE_SURVEY_HISTORY, null, selection, selectionargs,null, null ,null);

        if (cursor.moveToFirst()){
            return true;
        }
        else return false;
    }

}
