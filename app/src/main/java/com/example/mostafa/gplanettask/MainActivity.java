package com.example.mostafa.gplanettask;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mostafa.gplanettask.data.UserContract.SessionEntry;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userIdInputET = findViewById(R.id.user_id_edit_text);
        Button queryButton = findViewById(R.id.query_button);
        TextView userIdTV = findViewById(R.id.user_id_tv);
        TextView percentageTV = findViewById(R.id.percentage_tv);

        Cursor data = getContentResolver().query(SessionEntry.CONTENT_URI,
                null, null, null, SessionEntry.COLUMN_USER_ID);




        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = Integer.parseInt(userIdInputET.getText().toString());


            }
        });
    }
}
