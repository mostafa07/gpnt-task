package com.example.mostafa.gplanettask.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mostafa.gplanettask.data.UserContract.UserEntry;
import com.example.mostafa.gplanettask.data.UserContract.SessionEntry;


public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL);";

        String SQL_CREATE_SESSIONS_TABLE = "CREATE TABLE " + SessionEntry.TABLE_NAME + " ("
                + SessionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SessionEntry.COLUMN_FROM + " INTEGER NOT NULL DEFAULT 0, "
                + SessionEntry.COLUMN_TO + " INTEGER NOT NULL, "
                + SessionEntry.COLUMN_USER_ID + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SESSIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
