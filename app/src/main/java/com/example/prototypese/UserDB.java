package com.example.prototypese;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDB {
    private DBHelper dbHelper;

    public UserDB(Context ctx) {
        dbHelper = new DBHelper(ctx);
    }

    public void insertUsers(Users users){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_USER_USERNAME, users.username);
        cv.put(DBHelper.FIELD_USER_PASSWORD, users.password);
        cv.put(DBHelper.FIELD_USER_PHONE, users.phone);
        cv.put(DBHelper.FIELD_USER_EMAIL, users.email);
        cv.put(DBHelper.FIELD_USER_BALANCE, users.balance);

        db.insert(DBHelper.TABLE_USERS, null, cv );

        db.close();
    }

    public boolean checkUsers(String username, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "username=? AND password=?";
        String[] selectionargs = {"" + username, "" + password};

        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, selection, selectionargs,null, null ,null);

        if (cursor.moveToFirst()){
            return true;
        }
        else return false;
    }

    public boolean checkUsers2(String username){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "username=?";
        String[] selectionargs = {"" + username};

        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, selection, selectionargs,null, null ,null);

        if (cursor.moveToFirst()){
            return true;
        }
        else return false;
    }

    public Users getUser(String username) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Users users = null;

        String selection = DBHelper.FIELD_USER_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToNext())
        {
            int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_USER_ID));
            String password = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_USER_PASSWORD));
            String phone = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_USER_PHONE));
            String email = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_USER_EMAIL));
            long balance = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_USER_BALANCE));

            users = new Users();
            users.setUserId(userId);
            users.setUsername(username);
            users.setPassword(password);
            users.setPhone(phone);
            users.setEmail(email);
            users.setBalance(balance);
        }

        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();

        return users;
    }

    public Users getUserByID(int user_id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Users users = null;

        String selection = DBHelper.FIELD_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(user_id)};

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToNext())
        {
            String username = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_USER_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_USER_PASSWORD));
            String phone = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_USER_PHONE));
            String email = cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_USER_EMAIL));
            long balance = cursor.getLong(cursor.getColumnIndex(DBHelper.FIELD_USER_BALANCE));

            users = new Users();
            users.setUserId(user_id);
            users.setUsername(username);
            users.setPassword(password);
            users.setPhone(phone);
            users.setEmail(email);
            users.setBalance(balance);
        }

        cursor.close();
        sqLiteDatabase.close();
        dbHelper.close();

        return users;
    }

    public void updateWallet (int user_id, long balance, long amount, int type) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.FIELD_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(user_id)};

        ContentValues cv = new ContentValues();
        if(type == 1) {
            cv.put(DBHelper.FIELD_USER_BALANCE, balance + amount);
        }else if (type == 2) {
            cv.put(DBHelper.FIELD_USER_BALANCE, balance - amount);
        }

        db.update(DBHelper.TABLE_USERS, cv, selection, selectionArgs);
    }

    public void updatePassword (int user_id, String new_pass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.FIELD_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(user_id)};

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_USER_PASSWORD, new_pass);

        db.update(DBHelper.TABLE_USERS, cv, selection, selectionArgs);
    }

}
