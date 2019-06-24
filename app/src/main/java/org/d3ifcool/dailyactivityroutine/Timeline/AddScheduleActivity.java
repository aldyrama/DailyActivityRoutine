package org.d3ifcool.dailyactivityroutine.Timeline;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.ScheduleEntry;
import org.d3ifcool.dailyactivityroutine.Database.ActivityDbHelper;
import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.TimePickerDialog.OnTimeSetListener;

public class AddScheduleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ActivityDbHelper myDb;


    private static final int EXISTING_SCHEDULE_LOADER = 0;
    private Uri mCurrentScheduleUri;

    private TextView mStartTime_TextView, mFinishTime_TextView,mDay_Textview, mLabel_TextView;
    private EditText mSchedule_EditText, mDescription_EditText;
    private TimePickerDialog timePickerDialog;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String getTime ;

    //format time
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm" );

    final static int RQS_1 = 1;

    private boolean mScheduleHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mScheduleHasChanged = true;
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
        setContentView( R.layout.activity_add_schedule );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_all );
        toolbar.setTitle( "Tambah jadwal" );
        toolbar.setBackgroundColor( Constant.color);
        setSupportActionBar( toolbar );
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }


        Intent intent = getIntent();
        mCurrentScheduleUri = intent.getData();

        if (mCurrentScheduleUri == null) {
            setTitle( getString( R.string.new_schedule ) );
            invalidateOptionsMenu();
        } else {
            setTitle( getString(R.string.edit_schedule) );
            getSupportLoaderManager().initLoader( EXISTING_SCHEDULE_LOADER, null, this );
        }

        mSchedule_EditText = (EditText) findViewById( R.id.schedule_edittext);
        mStartTime_TextView = (TextView) findViewById( R.id.start_time_textview);
        mFinishTime_TextView = (TextView) findViewById( R.id.finish_time_textview);
        mDay_Textview = findViewById(R.id.day_textview);
        mLabel_TextView = (TextView) findViewById( R.id.label_textView );
        radioGroup = (RadioGroup) findViewById( R.id.radioGroup );
        mDescription_EditText = (EditText) findViewById( R.id.description_textview);

//        mSchedule_EditText.setOnTouchListener(mTouchListener);
//        mStartTime_TextView.setOnTouchListener(mTouchListener);
//        mFinishTime_TextView.setOnTouchListener(mTouchListener);
//        mDay_Textview.setOnTouchListener(mTouchListener);
//        radioGroup.setOnTouchListener(mTouchListener);
//        mDescription_EditText.setOnTouchListener(mTouchListener);

        // Day
        mDay_Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(AddScheduleActivity.this,mDay_Textview);
                popupMenu.getMenuInflater().inflate(R.menu.day, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mDay_Textview.setText(item.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        //Label
        mLabel_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(AddScheduleActivity.this,mLabel_TextView);
                popupMenu.getMenuInflater().inflate(R.menu.label, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mLabel_TextView.setText(item.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        } );

        //Start time
        mStartTime_TextView = (TextView) findViewById( R.id.start_time_textview);
        mStartTime_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialogStart();
            }
        } );

        //Finish time
        mFinishTime_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialogFinish();
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_schedule, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        super.onPrepareOptionsMenu( menu );
        if (mCurrentScheduleUri == null) {
            MenuItem menuItem = menu.findItem( R.id.action_delete );
            menuItem.setVisible( false );
        }
        return true;
    }

    // Start time
    public void showTimeDialogStart() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog( this, new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String start = "";
                try {
                    start = simpleDateFormat.format(simpleDateFormat.parse( hourOfDay + ":" + minute ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mStartTime_TextView.setText(start);
            }
        },
                calendar.get( Calendar.HOUR_OF_DAY ), calendar.get( Calendar.MINUTE ),
                android.text.format.DateFormat.is24HourFormat( this ) );
                timePickerDialog.show();
    }

    // Finsih time
    public void showTimeDialogFinish() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog( this, new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String finish = "";
                try {
                    finish = simpleDateFormat.format( simpleDateFormat.parse( hourOfDay + ":" + minute ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mFinishTime_TextView.setText(finish);
            }
        },
                calendar.get( Calendar.HOUR_OF_DAY ), calendar.get( Calendar.MINUTE ),
                android.text.format.DateFormat.is24HourFormat( this ) );
                timePickerDialog.show();
    }

    public boolean insert(MenuItem item) {
        if (insertSchedule())
            openTimePickerDialog(false);
            finish();
        return true;
    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(AddScheduleActivity.this,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);

        timePickerDialog.show();
    }

    OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set( Calendar.HOUR_OF_DAY, hourOfDay );
            calSet.set( Calendar.MINUTE, minute );
            calSet.set( Calendar.SECOND, 0 );
            calSet.set( Calendar.MILLISECOND, 0 );

            if (calSet.compareTo( calNow ) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add( Calendar.DATE, 1 );
                Log.i( "hasil", " =<0" );
            } else if (calSet.compareTo( calNow ) > 0) {
                Log.i( "hasil", " > 0" );
            } else {
                Log.i( "hasil", " else " );
            }
            setAlarm( calSet );
        }
    };



    private void setAlarm(Calendar targetCal) {
        mStartTime_TextView.setText( targetCal.getTime()
                + "" );
        Intent intent = new Intent( getBaseContext(), AlarmReceiver.class );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0 );
        AlarmManager alarmManager = (AlarmManager) getSystemService( Context.ALARM_SERVICE );
        alarmManager.set( AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent );
    }



    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById( radioId );
        getTime = String.valueOf(radioButton.getText().toString());
    }



    private boolean insertSchedule() {
        ContentValues values = new ContentValues();
        values.put( ScheduleEntry.COLUMN_SCHEDULE_NAME, mSchedule_EditText.getText().toString().trim() );
        values.put( ScheduleEntry.COLUMN_SCHEDULE_START, mStartTime_TextView.getText().toString().trim() );
        values.put( ScheduleEntry.COLUMN_SCHEDULE_FINISH, mFinishTime_TextView.getText().toString().trim() );
        values.put( ScheduleEntry.COLUMN_SCHEDULE_DAY, mDay_Textview.getText().toString().trim());
        values.put( ScheduleEntry.COLUMN_SCHEDULE_LABEL, mLabel_TextView.getText().toString().trim() );
        values.put( ScheduleEntry.COLUMN_SCHEDULE_ALARM, radioButton.getText().toString().trim());
        values.put( ScheduleEntry.COLUMN_SCHEDULE_DESCRIPTION, mDescription_EditText.getText().toString().trim() );

        try {
            if (mCurrentScheduleUri == null) {
                getContentResolver().insert(ScheduleEntry.CONTENT_SCHEDULE_URI, values);
            } else {
                getContentResolver().update(mCurrentScheduleUri, values, null, null );
            }
        }catch (IllegalArgumentException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
            ScheduleEntry._ID,
            ScheduleEntry.COLUMN_SCHEDULE_NAME,
            ScheduleEntry.COLUMN_SCHEDULE_START,
            ScheduleEntry.COLUMN_SCHEDULE_FINISH,
            ScheduleEntry.COLUMN_SCHEDULE_DAY,
            ScheduleEntry.COLUMN_SCHEDULE_LABEL,
            ScheduleEntry.COLUMN_SCHEDULE_ALARM,
            ScheduleEntry.COLUMN_SCHEDULE_DESCRIPTION };
        return new CursorLoader(this,
                mCurrentScheduleUri,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1) {
            return;
        }
        if (data.moveToFirst()) {
            int nameColumnIndex = data.getColumnIndex(ScheduleEntry.COLUMN_SCHEDULE_NAME);
            int startColumnIndex = data.getColumnIndex(ScheduleEntry.COLUMN_SCHEDULE_START);
            int finishColumnIndex = data.getColumnIndex(ScheduleEntry.COLUMN_SCHEDULE_FINISH);
            int dayColumnIndex = data.getColumnIndex(ScheduleEntry.COLUMN_SCHEDULE_DAY);
            int labelColumnIndex = data.getColumnIndex(ScheduleEntry.COLUMN_SCHEDULE_LABEL);
            int alarmColumnIndex = data.getColumnIndex(ScheduleEntry.COLUMN_SCHEDULE_ALARM);
            int descriptionColumnIndex = data.getColumnIndex(ScheduleEntry.COLUMN_SCHEDULE_DESCRIPTION);

            String name = data.getString(nameColumnIndex);
            String start = data.getString(startColumnIndex);
            String finish = data.getString(finishColumnIndex);
            String day = data.getString(dayColumnIndex);
            String label = data.getString(labelColumnIndex);
            String alarm = data.getString(alarmColumnIndex);
            String description = data.getString(descriptionColumnIndex);

            mSchedule_EditText.setText(name);
            mStartTime_TextView.setText(start);
            mFinishTime_TextView.setText(finish);
            mDay_Textview.setText(day);

            switch (alarm) {
                case "15 Menit":
                    radioButton = findViewById(R.id.time_one);
                    radioButton.setChecked(true);
                    break;
                case "30 Menit":
                    radioButton = findViewById(R.id.time_two);
                    radioButton.setChecked(true);
                    break;
                case "1 Jam":
                    radioButton = findViewById(R.id.time_three);
                    radioButton.setChecked(true);
                    break;
                default:
                    radioButton = findViewById(R.id.time_four);
                    radioButton.setChecked(true);
                    break;
            }
            mLabel_TextView.setText(label);
            mDescription_EditText.setText(description);
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSchedule_EditText.setText("");
        mStartTime_TextView.setText("");
        mFinishTime_TextView.setText("");
        mDay_Textview.setText("");
        mLabel_TextView.setText("");
        radioButton.setText("");
        mDescription_EditText.setText("");
    }
    public void delete(MenuItem item) {
        showDeleteConfirmationDialog();
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hapus jadwal ini?");
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
        if (mCurrentScheduleUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentScheduleUri, null, null);
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
