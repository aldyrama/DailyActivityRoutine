package org.d3ifcool.dailyactivityroutine.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.ScheduleEntry;
import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.DiaryEntry;
import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.ProfileEntry;


public class ActivityDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "activity.db";
    private static final int DATABASE_VERSION = 1;

    public ActivityDbHelper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_SCHEDULE_TABLE = "CREATE TABLE " + ScheduleEntry.TABLE_NAME + " ("
                + ScheduleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ScheduleEntry.COLUMN_SCHEDULE_NAME + " TEXT NOT NULL, "
                + ScheduleEntry.COLUMN_SCHEDULE_START + " TEXT, "
                + ScheduleEntry.COLUMN_SCHEDULE_FINISH + " TEXT, "
                + ScheduleEntry.COLUMN_SCHEDULE_DAY + " TEXT, "
                + ScheduleEntry.COLUMN_SCHEDULE_LABEL + " TEXT, "
                + ScheduleEntry.COLUMN_SCHEDULE_ALARM + " TEXT, "
                + ScheduleEntry.COLUMN_SCHEDULE_DESCRIPTION + " TEXT);";

        String SQL_CREATE_DIARY_TABLE = "CREATE TABLE " + DiaryEntry.TABLE_NAME + " ("
                + DiaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DiaryEntry.COLUMN_DIARY_DATE + " TEXT, "
                + DiaryEntry.COLUMN_DIARY_TITLE + " TEXT, "
                + DiaryEntry.COLUMN_DIARY_CONTENT + " TEXT NOT NULL);";

        String SQL_CREATE_PROFILE_TABLE = "CREATE TABLE " + ProfileEntry.TABLE_NAME + " ("
                + ProfileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProfileEntry.COLUMN_PROFILE_NAME + " TEXT, "
                + ProfileEntry.COLUMN_PROFILE_PLACE_OF_BIRTH + " TEXT, "
                + ProfileEntry.COLUMN_PROFILE_DATE_OF_BIRTH + " TEXT, "
                + ProfileEntry.COLUMN_PROFILE_GENDER + " INTEGER, "
                + ProfileEntry.COLUMN_PROFILE_EMAIL + " TEXT, "
                + ProfileEntry.COLUMN_PROFILE_PASSWORD + " TEXT);";
        db.execSQL(SQL_CREATE_SCHEDULE_TABLE);

        db.execSQL(SQL_CREATE_DIARY_TABLE);

        db.execSQL(SQL_CREATE_PROFILE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
