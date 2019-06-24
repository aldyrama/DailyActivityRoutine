package org.d3ifcool.dailyactivityroutine.Diary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.DiaryEntry;
import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDiaryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_DIARY_LOADER = 0;

    private Uri mCurrentDiaryUri;

    EditText mDate, mTitle, mContent;
    Button mSave;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mDateFormatter;

    private boolean mDiaryHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mDiaryHasChanged = true;
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp);
        finish();
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_diary );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_all );
        toolbar.setTitle( "Buku harian" );
        toolbar.setBackgroundColor( Constant.color);
        setSupportActionBar( toolbar );
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }

        Intent intent = getIntent();
        mCurrentDiaryUri = intent.getData();

        if (mCurrentDiaryUri == null) {
            setTitle("Tambah Buku Harian");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit Buku Harian");
            getSupportLoaderManager().initLoader(EXISTING_DIARY_LOADER,null,this);
        }

        mDate = findViewById(R.id.date);
        mTitle = findViewById(R.id.title);
        mContent = findViewById(R.id.content);
        mSave = findViewById(R.id.save_btn);

        mDate.setOnTouchListener(mTouchListener);
        mTitle.setOnTouchListener(mTouchListener);
        mContent.setOnTouchListener(mTouchListener);

        mDateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        Button save_btn =(Button) findViewById(R.id.save_btn);



        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDiary();
                    finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_diary,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mCurrentDiaryUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete_diary);
            menuItem.setVisible(false);
        }
        return true;
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                mDate.setText(mDateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mDatePickerDialog.show();
    }

    private boolean insertDiary() {
        ContentValues values = new ContentValues();
        values.put(DiaryEntry.COLUMN_DIARY_DATE,mDate.getText().toString());
        values.put(DiaryEntry.COLUMN_DIARY_TITLE,mTitle.getText().toString());
        values.put(DiaryEntry.COLUMN_DIARY_CONTENT,mContent.getText().toString());


        try {
            if (mCurrentDiaryUri == null) {
                getContentResolver().insert(DiaryEntry.CONTENT_DIARY_URI, values);
            } else {
                getContentResolver().update(mCurrentDiaryUri, values, null,null);
            }
        } catch (IllegalArgumentException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

            return false;
        }
        return true;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
                DiaryEntry._ID,
                DiaryEntry.COLUMN_DIARY_DATE,
                DiaryEntry.COLUMN_DIARY_TITLE,
                DiaryEntry.COLUMN_DIARY_CONTENT};
        return new CursorLoader(this,
                mCurrentDiaryUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1){
            return;
        }

        if (data.moveToFirst()){
            int dateColumnIndex = data.getColumnIndex(DiaryEntry.COLUMN_DIARY_DATE);
            int titleColumnIndex = data.getColumnIndex(DiaryEntry.COLUMN_DIARY_TITLE);
            int contentColumnIndex = data.getColumnIndex(DiaryEntry.COLUMN_DIARY_CONTENT);

            String date = data.getString(dateColumnIndex);
            String title = data.getString(titleColumnIndex);
            String content = data.getString(contentColumnIndex);

            mDate.setText(date);
            mTitle.setText(title);
            mContent.setText(content);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDate.setText("");
        mTitle.setText("");
        mContent.setText("");
    }

    public void delete_diary(MenuItem item) {
        showDeleteConfirmationDialog();
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hapus diary ini?");
        builder.setPositiveButton("hapus", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteSchedule();
            }
        });
        builder.setNegativeButton("kembali", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void deleteSchedule() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentDiaryUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentDiaryUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();

    }
}

