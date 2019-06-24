package org.d3ifcool.dailyactivityroutine.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.DiaryEntry;
import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.ScheduleEntry;
import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.ProfileEntry;

public class ActivityProvider extends ContentProvider {
    private static final int SCHEDULE = 1;
    private static final int SCHEDULE_ID = 2;

    private static final int DIARY = 3;
    private static final int DIARY_ID = 4;

    private static final int PROFILE = 5;
    private static final int PROFILE_ID = 6;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ActivityContract.CONTENT_AUTHORITY, ActivityContract.PATH_SCHEDULE_ACTIVITY, SCHEDULE);
        sUriMatcher.addURI(ActivityContract.CONTENT_AUTHORITY, ActivityContract.PATH_SCHEDULE_ACTIVITY + "/#", SCHEDULE_ID);

        sUriMatcher.addURI(ActivityContract.CONTENT_AUTHORITY,ActivityContract.PATH_DIARY_ACTIVITY,DIARY);
        sUriMatcher.addURI(ActivityContract.CONTENT_AUTHORITY,ActivityContract.PATH_DIARY_ACTIVITY + "/#",DIARY_ID);

        sUriMatcher.addURI(ActivityContract.CONTENT_AUTHORITY,ActivityContract.PATH_PROFILE_ACTIVITY,PROFILE);
        sUriMatcher.addURI(ActivityContract.CONTENT_AUTHORITY,ActivityContract.PATH_PROFILE_ACTIVITY + "/#",PROFILE);

    }

    private ActivityDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ActivityDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match =sUriMatcher.match(uri);

        switch (match) {
            case SCHEDULE:
                cursor = database.query(ScheduleEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case SCHEDULE_ID:
                selection = ScheduleEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(ScheduleEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DIARY:
                cursor = database.query(DiaryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DIARY_ID:
                selection = DiaryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(DiaryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PROFILE:
                cursor = database.query(ProfileEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PROFILE_ID:
                selection = ProfileEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(ProfileEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCHEDULE:
                return ScheduleEntry.CONTENT_LIST_TYPE;
            case SCHEDULE_ID:
                return ScheduleEntry.CONTENT_ITEM_TYPE;
            case DIARY:
                return DiaryEntry.CONTENT_LIST_TYPE;
            case DIARY_ID:
                return DiaryEntry.CONTENT_ITEM_TYPE;
            case PROFILE:
                return ProfileEntry.CONTENT_LIST_TYPE;
            case PROFILE_ID:
                return ProfileEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCHEDULE:
                return insertSchedule(uri, values);
            case DIARY:
                return insertDiary(uri, values);
            case PROFILE:
                return insertProfile(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertSchedule(Uri uri,ContentValues values) {
        String name = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_NAME);
        String start = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_START);
        String finish = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_FINISH);
        String day = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_DAY);
        String label = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_LABEL);
        String alarm = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_ALARM);
        String description = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_DESCRIPTION);


        if (name.equals("")) {
            throw new IllegalArgumentException("Nama kegiatan belum di isi");
        }
        if (start.equals(finish)) {
            throw new IllegalArgumentException("Waktu tidak boleh sama");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(ScheduleEntry.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertDiary(Uri uri,ContentValues values) {
        String date = values.getAsString(DiaryEntry.COLUMN_DIARY_DATE);
        String title = values.getAsString(DiaryEntry.COLUMN_DIARY_DATE);
        String content = values.getAsString(DiaryEntry.COLUMN_DIARY_DATE);



        if (date.equals("")) {
            throw new IllegalArgumentException("Tanggal belum di isi");
        }

        if (title.equals("")) {
            throw new IllegalArgumentException("Judul belum di isi");
        }

        if (content.equals("")) {
            throw new IllegalArgumentException("Cerita belum di isi");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(DiaryEntry.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }
    private Uri insertProfile(Uri uri,ContentValues values) {
        String name = values.getAsString(ProfileEntry.COLUMN_PROFILE_NAME);
        String place = values.getAsString(ProfileEntry.COLUMN_PROFILE_PLACE_OF_BIRTH);
        String date = values.getAsString(ProfileEntry.COLUMN_PROFILE_DATE_OF_BIRTH);
        String gender = values.getAsString(ProfileEntry.COLUMN_PROFILE_GENDER);
        String email = values.getAsString(ProfileEntry.COLUMN_PROFILE_EMAIL);
        String password = values.getAsString(ProfileEntry.COLUMN_PROFILE_PASSWORD);


        if (name.equals("")) {
            throw new IllegalArgumentException("Nama belum di isi");
        }

        if (place.equals("")) {
            throw new IllegalArgumentException("Tempat belum di isi");
        }

        if (date.equals("")) {
            throw new IllegalArgumentException("Tanggal belum di isi");
        }

        if (email.equals("")) {
            throw new IllegalArgumentException("Email belum di isi");
        }

        if (password.equals("")) {
            throw new IllegalArgumentException("Kata sandi belum di isi");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(ProfileEntry.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCHEDULE:
                rowsDeleted = database.delete(ScheduleEntry.TABLE_NAME,selection,selectionArgs);
                // Delete all rows that match the selection and selection args
                break;
            case SCHEDULE_ID:
                // Delete a single row given by the ID in the URI
                selection = ScheduleEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ScheduleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DIARY:
                rowsDeleted = database.delete(DiaryEntry.TABLE_NAME,selection,selectionArgs);
                // Delete all rows that match the selection and selection args
                break;
            case DIARY_ID:
                // Delete a single row given by the ID in the URI
                selection = DiaryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(DiaryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PROFILE:
                rowsDeleted = database.delete(ProfileEntry.TABLE_NAME,selection,selectionArgs);
                // Delete all rows that match the selection and selection args
                break;
            case PROFILE_ID:
                // Delete a single row given by the ID in the URI
                selection = ProfileEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ProfileEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;

    }

    private int updateSchedule (Uri uri, ContentValues values, String selection, String [] selectionArgs){
        if (values.containsKey(ScheduleEntry.COLUMN_SCHEDULE_NAME)){
            String name = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_NAME);
            if (name.equals("") ){
                throw new IllegalArgumentException("Nama kegiatan kosong");
            }
        }

        if (values.containsKey(ScheduleEntry.COLUMN_SCHEDULE_START) == values.containsKey(ScheduleEntry.COLUMN_SCHEDULE_FINISH)){
            String start = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_START);
            String finish = values.getAsString(ScheduleEntry.COLUMN_SCHEDULE_FINISH);
            if (start.equals(finish)) {
                throw new IllegalArgumentException("Waktu tidak boleh sama");
            }
        }

        if (values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(ScheduleEntry.TABLE_NAME,values,selection,selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsUpdated;
    }

    private int updateDiary (Uri uri, ContentValues values, String selection, String [] selectionArgs){
        if (values.containsKey(DiaryEntry.COLUMN_DIARY_DATE)){
            String date = values.getAsString(DiaryEntry.COLUMN_DIARY_DATE);
            if (date.equals("") ){
                throw new IllegalArgumentException("Tanggal belum di isi");
            }
        }

        if (values.containsKey(DiaryEntry.COLUMN_DIARY_TITLE)){
            String title = values.getAsString(DiaryEntry.COLUMN_DIARY_TITLE);
            if (title.equals("")) {
                throw new IllegalArgumentException("Judul belum di isi");
            }
        }
        if (values.containsKey(DiaryEntry.COLUMN_DIARY_CONTENT)){
            String content = values.getAsString(DiaryEntry.COLUMN_DIARY_CONTENT);
            if (content.equals("")){
                throw new IllegalArgumentException("Cerita belum di isi");
            }
        }

        if (values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(DiaryEntry.TABLE_NAME,values,selection,selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsUpdated;
    }

    private int updateProfile (Uri uri, ContentValues values, String selection, String [] selectionArgs){
        if (values.containsKey(ProfileEntry.COLUMN_PROFILE_NAME)){
            String name = values.getAsString(ProfileEntry.COLUMN_PROFILE_NAME);
            if (name.equals("")){
                throw new IllegalArgumentException("Nama belum di isi");
            }
        }

        if (values.containsKey(ProfileEntry.COLUMN_PROFILE_PLACE_OF_BIRTH)){
            String place = values.getAsString(ProfileEntry.COLUMN_PROFILE_PLACE_OF_BIRTH);
            if (place.equals("")) {
                throw new IllegalArgumentException("Tempat lahir belum diisi");
            }
        }
        if (values.containsKey(ProfileEntry.COLUMN_PROFILE_DATE_OF_BIRTH)){
            String date = values.getAsString(ProfileEntry.COLUMN_PROFILE_DATE_OF_BIRTH);
            if (date.equals("")){
                throw new IllegalArgumentException("Tanggal belum di isi");
            }
        }
        if (values.containsKey(ProfileEntry.COLUMN_PROFILE_EMAIL)){
            String email = values.getAsString(ProfileEntry.COLUMN_PROFILE_EMAIL);
            if (email.equals("")){
                throw new IllegalArgumentException("E-mail belum di isi");
            }
        }
        if (values.containsKey(ProfileEntry.COLUMN_PROFILE_PASSWORD)){
            String password = values.getAsString(ProfileEntry.COLUMN_PROFILE_PASSWORD);
            if (password.equals("")){
                throw new IllegalArgumentException("Kata sandi belum di isi");
            }
        }

        if (values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update( ActivityContract.ProfileEntry.TABLE_NAME,values,selection,selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rowsUpdated;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCHEDULE:
                return updateSchedule(uri,values,selection,selectionArgs);
            case SCHEDULE_ID:
                selection = ScheduleEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateSchedule(uri, values,selection,selectionArgs);
            case DIARY:
                return updateDiary(uri,values,selection,selectionArgs);
            case DIARY_ID:
                selection = DiaryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateDiary(uri, values,selection,selectionArgs);
            case PROFILE:
                return updateProfile(uri,values,selection,selectionArgs);
            case PROFILE_ID:
                selection = ActivityContract.ProfileEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateProfile(uri, values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("");
        }
    }
}
