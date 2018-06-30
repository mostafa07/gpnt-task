package com.example.mostafa.gplanettask.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class UserContract {

    public static final String CONTENT_AUTHORITY = "com.example.mostafa,gplanettask";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USERS = "users";
    public static final String PATH_READING_SESSIONS = "sessions";


    private UserContract() {
    }

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +CONTENT_AUTHORITY + "/" + PATH_USERS;


        public static final String TABLE_NAME = "users";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER_NAME = "name";

        private UserEntry() {
        }
    }

    public static final class SessionEntry implements BaseColumns {

        private SessionEntry() {
        }

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_READING_SESSIONS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_READING_SESSIONS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_READING_SESSIONS;

        public static final String TABLE_NAME = "sessions";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FROM = "from";
        public static final String COLUMN_TO = "to";
        public static final String COLUMN_USER_ID = "user_id";
    }
}
