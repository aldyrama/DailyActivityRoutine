package org.d3ifcool.dailyactivityroutine.Diary;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d3ifcool.dailyactivityroutine.Database.ActivityContract.DiaryEntry;
import org.d3ifcool.dailyactivityroutine.R;

public class DiaryCursorAdapter extends CursorAdapter {
    public DiaryCursorAdapter(Context context, Cursor c) {
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_diary,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        TextView titleTextView = (TextView) view.findViewById(R.id.title);

        int dateColumnIndex = cursor.getColumnIndex(DiaryEntry.COLUMN_DIARY_DATE);
        int titleColumnIndex = cursor.getColumnIndex(DiaryEntry.COLUMN_DIARY_TITLE);

        String diaryDate = cursor.getString(dateColumnIndex);
        String diaryTitle = cursor.getString(titleColumnIndex);

        dateTextView.setText(diaryDate);
        titleTextView.setText(diaryTitle);
    }
}
