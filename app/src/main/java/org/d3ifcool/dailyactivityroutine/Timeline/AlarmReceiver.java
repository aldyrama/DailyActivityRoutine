package org.d3ifcool.dailyactivityroutine.Timeline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import org.d3ifcool.dailyactivityroutine.R;


public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {
        player = MediaPlayer.create(context, R.raw.alarm);
        player.start();
    }
}
