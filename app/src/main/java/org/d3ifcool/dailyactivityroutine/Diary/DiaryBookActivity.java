package org.d3ifcool.dailyactivityroutine.Diary;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.DiaryEntry;
import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

public class DiaryBookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ACTIVITY_LOADER = 0;

    DiaryCursorAdapter mCursorAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp);
        finish();
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diary_book );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_all );
        toolbar.setTitle( "Buku harian" );
        toolbar.setBackgroundColor(Constant.color);
        setSupportActionBar( toolbar );
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }

        ListView diaryListView = (ListView) findViewById(R.id.list_item_diary);
        View emptyView = findViewById(R.id.empty_view);
        diaryListView.setEmptyView(emptyView);

        mCursorAdapter = new DiaryCursorAdapter(this, null);
        diaryListView.setAdapter(mCursorAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab_add_diary );
        fab.setBackgroundTintList( ColorStateList.valueOf( Constant.color ));
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryBookActivity.this,AddDiaryActivity.class);
                startActivity(intent);
            }
        } );

        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DiaryBookActivity.this,AddDiaryActivity.class);
                Uri currentDiaryUri = ContentUris.withAppendedId(DiaryEntry.CONTENT_DIARY_URI,id);
                intent.setData(currentDiaryUri);
                startActivity(intent);
            }
        });


        getSupportLoaderManager().initLoader(ACTIVITY_LOADER, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                DiaryEntry._ID,
                DiaryEntry.COLUMN_DIARY_DATE,
                DiaryEntry.COLUMN_DIARY_TITLE};
        return new CursorLoader(this,
                DiaryEntry.CONTENT_DIARY_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}