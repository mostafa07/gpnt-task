package com.example.mostafa.gplanettask;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import com.example.mostafa.gplanettask.data.UserContract.UserEntry;
import com.example.mostafa.gplanettask.data.UserContract.SessionEntry;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private EditText mUserIdInputET;
    private TextView mUserNameTV;
    private TextView mUserIdTV;
    private TextView mPercentageTV;
    private TextView mUserOrderTV;

    private Cursor mCursor;
    private ArrayList<Session> mAllSessions;
    private ArrayList<ArrayList<Session>> mUsersSessionsList;
    private ArrayList<User> mUsersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserIdInputET = findViewById(R.id.user_id_edit_text);
        Button queryButton = findViewById(R.id.query_button);
        mUserNameTV = findViewById(R.id.user_name_tv);
        mUserIdTV = findViewById(R.id.user_id_tv);
        mPercentageTV = findViewById(R.id.percentage_tv);
        mUserOrderTV = findViewById(R.id.user_order_tv);
        TextView usersListTV = findViewById(R.id.users_list_tv);

        //insertDummyData();

        mCursor = getContentResolver().query(SessionEntry.CONTENT_URI,
                null, null, null, SessionEntry.COLUMN_USER_ID);

        extractSessionsData();

        separateUserSessions();

        constructUsersList();
        Collections.sort(mUsersList);

        usersListTV.setText(getString(R.string.users_list_text));
        for (User user : mUsersList) {
            usersListTV.append(user.toString() + "\n");
        }

        mUserNameTV.setText(String.valueOf("User Name: ---"));
        mUserIdTV.setText(String.valueOf("User ID: ---"));
        mPercentageTV.setText(String.valueOf("Reading Percentage: ---"));
        mUserOrderTV.setText(String.valueOf("User Order: ---"));


        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int userIdInput = Integer.parseInt(mUserIdInputET.getText().toString());
                User currentUser = null;
                for (User user : mUsersList) {
                    if (user.getUserId() == userIdInput)
                        currentUser = user;
                }

                if (currentUser == null) {
                    Toast.makeText(MainActivity.this, getString(R.string.user_not_found),
                            Toast.LENGTH_SHORT).show();
                    mUserNameTV.setText(String.valueOf("User Name: NA"));
                    mUserIdTV.setText(String.valueOf("User ID: NA"));
                    mPercentageTV.setText(String.valueOf("Reading Percentage: NA"));
                    mUserOrderTV.setText(String.valueOf("User Order: NA"));
                } else {
                    mUserNameTV.setText(String.valueOf("User Name: " + currentUser.getUserName()));
                    mUserIdTV.setText(String.valueOf("User ID: " + currentUser.getUserId()));
                    mPercentageTV.setText(String.valueOf("Reading Percentage: " + currentUser.getReadingPercentage()));

                    int userOrder = mUsersList.indexOf(currentUser) + 1;
                    mUserOrderTV.setText(String.valueOf("User Order: " + userOrder));
                }
            }
        });

        mCursor.close();
    }


    private void extractSessionsData() {

        mAllSessions = new ArrayList<>();

        int sessionIdColumnIndex = mCursor.getColumnIndex(SessionEntry._ID);
        int fromColumnIndex = mCursor.getColumnIndex(SessionEntry.COLUMN_FROM);
        int toColumnIndex = mCursor.getColumnIndex(SessionEntry.COLUMN_TO);
        int userIdColumnIndex = mCursor.getColumnIndex(SessionEntry.COLUMN_USER_ID);

        while (mCursor.moveToNext()) {

            int sessionId = mCursor.getInt(sessionIdColumnIndex);
            int from = mCursor.getInt(fromColumnIndex);
            int to = mCursor.getInt(toColumnIndex);
            int userId = mCursor.getInt(userIdColumnIndex);

            mAllSessions.add(new Session(sessionId, from, to, userId));
        }
    }

    private void separateUserSessions() {

        mUsersSessionsList = new ArrayList<>();
        mUsersSessionsList.add(new ArrayList<Session>());

        int iter = 0;
        Session previousSession = null;
        Session currentSession = mAllSessions.get(0);
        mUsersSessionsList.get(iter).add(currentSession);

        for (int i = 1; i < mAllSessions.size(); ++i) {

            previousSession = mAllSessions.get(i - 1);
            currentSession = mAllSessions.get(i);

            if (currentSession.getUserId() == previousSession.getUserId()) {
                mUsersSessionsList.get(iter).add(currentSession);
            } else {
                mUsersSessionsList.add(new ArrayList<Session>());
                mUsersSessionsList.get(++iter).add(currentSession);
            }
        }
    }

    private void constructUsersList() {

        mUsersList = new ArrayList<>();

        for (ArrayList<Session> sessionsList : mUsersSessionsList) {

            int currentUserId = sessionsList.get(0).getUserId();

            String selection = UserEntry._ID + " = ?";
            String[] selectionArgs = new String[]{String.valueOf(currentUserId)};

            Cursor cursor = getContentResolver().query(UserEntry.CONTENT_URI,
                    null, selection, selectionArgs, null);

            int userNameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
            cursor.moveToFirst();
            String userName = cursor.getString(userNameColumnIndex);

            mUsersList.add(new User(currentUserId, userName, sessionsList));

            cursor.close();
        }
    }

    private void insertDummyData() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserEntry.COLUMN_USER_NAME, "Jane");
        Uri uri = getContentResolver().insert(UserEntry.CONTENT_URI, contentValues);

        contentValues = new ContentValues();
        contentValues.put(UserEntry.COLUMN_USER_NAME, "Ahmed");
        uri = getContentResolver().insert(UserEntry.CONTENT_URI, contentValues);


        contentValues = new ContentValues();
        contentValues.put(SessionEntry.COLUMN_FROM, 1);
        contentValues.put(SessionEntry.COLUMN_TO, 20);
        contentValues.put(SessionEntry.COLUMN_USER_ID, 1);
        uri = getContentResolver().insert(SessionEntry.CONTENT_URI, contentValues);

        contentValues = new ContentValues();
        contentValues.put(SessionEntry.COLUMN_FROM, 33);
        contentValues.put(SessionEntry.COLUMN_TO, 47);
        contentValues.put(SessionEntry.COLUMN_USER_ID, 2);
        uri = getContentResolver().insert(SessionEntry.CONTENT_URI, contentValues);

        contentValues = new ContentValues();
        contentValues.put(SessionEntry.COLUMN_FROM, 9);
        contentValues.put(SessionEntry.COLUMN_TO, 30);
        contentValues.put(SessionEntry.COLUMN_USER_ID, 1);
        uri = getContentResolver().insert(SessionEntry.CONTENT_URI, contentValues);

        contentValues = new ContentValues();
        contentValues.put(SessionEntry.COLUMN_FROM, 39);
        contentValues.put(SessionEntry.COLUMN_TO, 67);
        contentValues.put(SessionEntry.COLUMN_USER_ID, 2);
        uri = getContentResolver().insert(SessionEntry.CONTENT_URI, contentValues);
    }

}
