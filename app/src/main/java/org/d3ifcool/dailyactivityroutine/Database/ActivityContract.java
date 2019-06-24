package org.d3ifcool.dailyactivityroutine.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ActivityContract {

    private ActivityContract() {}

    public static final String CONTENT_AUTHORITY = "org.d3ifcool.dailyactivityroutinefix";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SCHEDULE_ACTIVITY = "schedule";
    public static final String PATH_DIARY_ACTIVITY = "diary";
    public static final String PATH_PROFILE_ACTIVITY = "profile";

    public static final class ScheduleEntry implements BaseColumns {
        public static final Uri CONTENT_SCHEDULE_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SCHEDULE_ACTIVITY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCHEDULE_ACTIVITY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCHEDULE_ACTIVITY;

        public final static String TABLE_NAME = "schedule" ;
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_SCHEDULE_NAME = "name";
        public final static String COLUMN_SCHEDULE_START = "start";
        public final static String COLUMN_SCHEDULE_FINISH = "finish";
        public final static String COLUMN_SCHEDULE_DAY = "day";
        public final static String COLUMN_SCHEDULE_LABEL = "label";
        public final static String COLUMN_SCHEDULE_ALARM = "alarm";
        public final static String COLUMN_SCHEDULE_DESCRIPTION = "description";
    }

    public static final class DiaryEntry implements BaseColumns {
        public static final Uri CONTENT_DIARY_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DIARY_ACTIVITY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIARY_ACTIVITY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DIARY_ACTIVITY;

        public final static String TABLE_NAME = "diary" ;
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DIARY_DATE = "date";
        public final static String COLUMN_DIARY_TITLE = "title";
        public final static String COLUMN_DIARY_CONTENT = "content";
    }

    public static final class ProfileEntry implements BaseColumns {
        public static final Uri CONTENT_PROFILE_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PROFILE_ACTIVITY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROFILE_ACTIVITY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROFILE_ACTIVITY;

        public final static String TABLE_NAME = "profile" ;
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PROFILE_NAME = "name";
        public final static String COLUMN_PROFILE_PLACE_OF_BIRTH = "place";
        public final static String COLUMN_PROFILE_DATE_OF_BIRTH = "date";
        public final static String COLUMN_PROFILE_GENDER = "gender";
        public final static String COLUMN_PROFILE_EMAIL = "email";
        public final static String COLUMN_PROFILE_PASSWORD = "password";
    }
}
