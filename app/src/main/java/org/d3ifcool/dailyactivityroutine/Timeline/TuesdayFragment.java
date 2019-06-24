package org.d3ifcool.dailyactivityroutine.Timeline;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract;
import org.d3ifcool.dailyactivityroutine.R;

public class TuesdayFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ACTIVITY_LOADER = 0;

    ScheduleCursorAdapter mCursorAdapter;

    public TuesdayFragment() {
        // Required empty publ
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView scheduleListView = (ListView) view.findViewById(R.id.list_item_tuesday);

        View emptyView = view.findViewById(R.id.empty_view);
        scheduleListView.setEmptyView(emptyView);

        mCursorAdapter = new ScheduleCursorAdapter(getContext(), null);
        scheduleListView.setAdapter(mCursorAdapter);

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),AddScheduleActivity.class);
                Uri currentActivityUri = ContentUris.withAppendedId(ActivityContract.ScheduleEntry.CONTENT_SCHEDULE_URI, id);
                intent.setData(currentActivityUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(ACTIVITY_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_tuesday, container, false );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ActivityContract.ScheduleEntry._ID,
                ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_NAME,
                ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_START,
                ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_LABEL,
                ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_DESCRIPTION};
        return new CursorLoader(getContext(),
                ActivityContract.ScheduleEntry.CONTENT_SCHEDULE_URI,
                projection,
                ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_DAY + "=?",
                new String[] {String.valueOf("Selasa")},
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
