package org.d3ifcool.dailyactivityroutine.Timeline;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.d3ifcool.dailyactivityroutine.Database.ActivityContract;
import org.d3ifcool.dailyactivityroutine.R;

public class ScheduleCursorAdapter extends CursorAdapter {
    public ScheduleCursorAdapter(Context context, Cursor c) {
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_schedule,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.schedule_textView);
        TextView timeTextView = (TextView) view.findViewById(R.id.time_textView);
        TextView labelTextView = (TextView) view.findViewById(R.id.label_textView);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description_textView);

        int nameColumnIndex = cursor.getColumnIndex(ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_NAME);
        int startColumnIndex = cursor.getColumnIndex(ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_START);
        int labelColumnIndex = cursor.getColumnIndex(ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_LABEL);
        int descriptionColumnIndex = cursor.getColumnIndex(ActivityContract.ScheduleEntry.COLUMN_SCHEDULE_DESCRIPTION);

        String scheduleName = cursor.getString(nameColumnIndex);
        String scheduleTime = cursor.getString(startColumnIndex);
        String scheduleLabel = cursor.getString(labelColumnIndex);
        String scheduleDescription = cursor.getString(descriptionColumnIndex);

        nameTextView.setText(scheduleName);
        timeTextView.setText(scheduleTime);
        labelTextView.setText(scheduleLabel);
        descriptionTextView.setText(scheduleDescription);
    }
}
