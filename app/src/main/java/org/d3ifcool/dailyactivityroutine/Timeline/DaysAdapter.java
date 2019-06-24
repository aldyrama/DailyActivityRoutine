package org.d3ifcool.dailyactivityroutine.Timeline;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.d3ifcool.dailyactivityroutine.R;

public class DaysAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public DaysAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :return new SundayFragment();
            case 1 :return new MondayFragment();
            case 2 :return new TuesdayFragment();
            case 3 :return new WednesdayFragment();
            case 4 :return new ThursdayFragment();
            case 5 :return new FridayFragment();
            default:return new SaturdayFragment();
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :return mContext.getString(R.string.sunday);
            case 1 :return mContext.getString(R.string.monday);
            case 2 :return mContext.getString(R.string.tuesday);
            case 3 :return mContext.getString(R.string.wednesday);
            case 4 :return mContext.getString(R.string.thursday);
            case 5 :return mContext.getString(R.string.friday);
            default:return mContext.getString(R.string.saturday);
        }
    }
}
