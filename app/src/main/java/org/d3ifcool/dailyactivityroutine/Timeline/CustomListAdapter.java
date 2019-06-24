package org.d3ifcool.dailyactivityroutine.Timeline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Schedule> {
    public CustomListAdapter(@NonNull Context context, @NonNull List<Schedule> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            convertView.setBackgroundColor( Constant.color);
        }
        Schedule currentSchedule = getItem(position);
        TextView scheduleName = (TextView) convertView.findViewById( R.id.schedule_textView);
        TextView startTime = (TextView) convertView.findViewById(R.id.time_textView);
        TextView scheduleLabel = (TextView) convertView.findViewById(R.id.label_textView);
        TextView description = (TextView) convertView.findViewById(R.id.description_textView);

        scheduleName.setText(currentSchedule.getScheduleName());
        startTime.setText(currentSchedule.getStartTime());
        scheduleLabel.setText(currentSchedule.getLabel());
        description.setText(currentSchedule.getDescription());
        return convertView;
    }
}