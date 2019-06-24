package org.d3ifcool.dailyactivityroutine.Profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.ProfileEntry;
import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_PROFILE_LOADER = 0;
    private Uri mCurrentProfileUri;

    private EditText mName, mPlace, mDate, mEmail, mPassword;
    private RadioGroup mGenderGroup;
    private RadioButton mGenderButton;
    private Button mSave, mReset;
    private String getTime ;
    private boolean isUpdate;
    public String getName;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mDateFormatter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp);
            finish();
        return super.onOptionsItemSelected( item );
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_all );
        toolbar.setTitle( "Profil" );
        toolbar.setBackgroundColor(Constant.color);
        setSupportActionBar( toolbar );
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );

        }


        Intent intent = getIntent();
        mCurrentProfileUri = intent.getData();

        mName = findViewById(R.id.username_edittext);
        mPlace = findViewById(R.id.place_of_birth_edittext);
        mDate = findViewById(R.id.date_of_birth_edittext);
        mGenderGroup = findViewById(R.id.gender_radiogrup);
        mEmail = findViewById(R.id.email_edittext);
        mPassword = findViewById(R.id.password_edittext);
        mSave = findViewById(R.id.save_btn);

        mDateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        getSupportLoaderManager().initLoader( EXISTING_PROFILE_LOADER, null, this );

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProfile();
                finish();
            }
        });
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

    public void check(View view) {
        int radioId = mGenderGroup.getCheckedRadioButtonId();
        mGenderButton = findViewById( radioId );
        getTime = String.valueOf(mGenderButton.getText().toString());
    }



    private void insertProfile(){
        ContentValues values = new ContentValues();
        values.put(ProfileEntry.COLUMN_PROFILE_NAME,mName.getText().toString().trim());
        values.put(ProfileEntry.COLUMN_PROFILE_PLACE_OF_BIRTH,mPlace.getText().toString().trim());
        values.put(ProfileEntry.COLUMN_PROFILE_DATE_OF_BIRTH,mDate.getText().toString().trim());
        values.put(ProfileEntry.COLUMN_PROFILE_GENDER, mGenderButton.getText().toString().trim());
        values.put(ProfileEntry.COLUMN_PROFILE_EMAIL,mEmail.getText().toString().trim());
        values.put(ProfileEntry.COLUMN_PROFILE_PASSWORD,mPassword.getText().toString().trim());

//        try {
//            if (mCurrentProfileUri == null) {
//                getContentResolver().insert(ProfileEntry.CONTENT_PROFILE_URI, values);
//            } else {
//                getContentResolver().update(mCurrentProfileUri, values, null,null);
//             }
//        } catch (IllegalArgumentException e){
//                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
//        }

        if (!isUpdate) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(ProfileEntry.CONTENT_PROFILE_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "gagal",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "sukses",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.

            int rowsAffected = getContentResolver().update(ProfileEntry.CONTENT_PROFILE_URI,
                    values,
                    ProfileEntry.COLUMN_PROFILE_NAME + "=?",
                    new String[]{getName.toString()});
            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "gagal",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "sukses",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteProfile() {
        if (mCurrentProfileUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentProfileUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDeleteConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hapus data pengguna?");
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteProfile();
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
                ProfileEntry._ID,
                ProfileEntry.COLUMN_PROFILE_NAME,
                ProfileEntry.COLUMN_PROFILE_PLACE_OF_BIRTH,
                ProfileEntry.COLUMN_PROFILE_DATE_OF_BIRTH,
                ProfileEntry.COLUMN_PROFILE_GENDER,
                ProfileEntry.COLUMN_PROFILE_EMAIL,
                ProfileEntry.COLUMN_PROFILE_PASSWORD
        };
        return new CursorLoader(this,
                ProfileEntry.CONTENT_PROFILE_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1){
            isUpdate = false;
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG).show();
            return;
        }
        if (data.moveToFirst()){
            isUpdate = true;

            Toast.makeText(this,"Data Ada",Toast.LENGTH_LONG).show();
            int nameColumnIndex = data.getColumnIndex(ProfileEntry.COLUMN_PROFILE_NAME);
            int placeColumnIndex = data.getColumnIndex(ProfileEntry.COLUMN_PROFILE_PLACE_OF_BIRTH);
            int dateColumnIndex = data.getColumnIndex(ProfileEntry.COLUMN_PROFILE_DATE_OF_BIRTH);
            int genderColumnIndex = data.getColumnIndex(ProfileEntry.COLUMN_PROFILE_GENDER);
            int emailColumnIndex = data.getColumnIndex(ProfileEntry.COLUMN_PROFILE_EMAIL);
            int passwordColumnIndex = data.getColumnIndex(ProfileEntry.COLUMN_PROFILE_PASSWORD);

            String name = data.getString(nameColumnIndex);
            String place = data.getString(placeColumnIndex);
            String date = data.getString(dateColumnIndex);
            String gender = data.getString(genderColumnIndex);
            String email = data.getString(emailColumnIndex);
            String password = data.getString(passwordColumnIndex);

            getName = name;

            mName.setText(name);
            mPlace.setText(place);
            mDate.setText(date);
            switch (gender) {
                case "Laki-laki":
                    mGenderButton = findViewById(R.id.male_radiobutton);
                    mGenderButton.setChecked(true);
                    break;
                case "Perempuan":
                    mGenderButton = findViewById(R.id.female_radiobutton);
                    mGenderButton.setChecked(true);
                    break;
            }
            mEmail.setText(email);
            mPassword.setText(password);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mName.setText("");
        mPlace.setText("");
        mDate.setText("");
        mGenderButton.setText("");
        mEmail.setText("");
        mPassword.setText("");
    }


}
