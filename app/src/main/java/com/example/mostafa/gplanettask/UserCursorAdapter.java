package com.example.mostafa.gplanettask;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mostafa.gplanettask.data.UserContract.SessionEntry;


public class UserCursorAdapter extends CursorAdapter {

    public UserCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView userNamePlusIdTV = view.findViewById(R.id.user_name_plus_id);
        TextView percentageTV = view.findViewById(R.id.percentage);

        int userIdColumnIndex = cursor.getColumnIndex(SessionEntry._ID);
        
    }
}
