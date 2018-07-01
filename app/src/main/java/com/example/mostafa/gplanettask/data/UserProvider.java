package com.example.mostafa.gplanettask.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mostafa.gplanettask.data.UserContract.UserEntry;
import com.example.mostafa.gplanettask.data.UserContract.SessionEntry;


public class UserProvider extends ContentProvider {

    private static final String LOG_TAG = UserProvider.class.getSimpleName();

    private static final int USERS = 100;
    private static final int USER_ID = 101;
    private static final int SESSIONS = 200;
    private static final int SESSION_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private UserDbHelper mDbHelper;

    static {
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS, USERS);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS + "/#", USER_ID);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_READING_SESSIONS, SESSIONS);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_READING_SESSIONS + "/#", SESSION_ID);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new UserDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS: {
                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }
            case USER_ID: {
                selection = UserEntry._ID;
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }
            case SESSIONS: {
                cursor = database.query(SessionEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }
            case SESSION_ID: {
                selection = SessionEntry._ID;
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(SessionEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }
            default:
                throw new IllegalArgumentException("Can't query unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return insertUser(uri, contentValues);
            case SESSIONS:
                return insertSession(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertUser(Uri uri, ContentValues contentValues) {

        String userName = contentValues.getAsString(UserEntry.COLUMN_USER_NAME);
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("User must have a name");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(UserEntry.TABLE_NAME, null, contentValues);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertSession(Uri uri, ContentValues contentValues) {

        Integer from = contentValues.getAsInteger(SessionEntry.COLUMN_FROM);
        if (from < 0 || from > 70) {
            Log.e(LOG_TAG, "Starting page must be within [0:70], not " + from);
            return null;
        }
        Integer to = contentValues.getAsInteger(SessionEntry.COLUMN_TO);
        if (to < 0 || to > 70) {
            Log.e(LOG_TAG, "Ending page must be within [0:70], not " + to);
            return null;
        }
        if (from > to) {
            Log.e(LOG_TAG, "Ending page can not be before starting page");
            return null;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(SessionEntry.TABLE_NAME, null, contentValues);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return UserEntry.CONTENT_LIST_TYPE;
            case USER_ID:
                return UserEntry.CONTENT_ITEM_TYPE;
            case SESSIONS:
                return SessionEntry.CONTENT_LIST_TYPE;
            case SESSION_ID:
                return SessionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown Uri " + uri + " with match " + match);
        }
    }
}
