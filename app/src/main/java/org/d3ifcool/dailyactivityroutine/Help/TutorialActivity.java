package org.d3ifcool.dailyactivityroutine.Help;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

public class TutorialActivity extends AppCompatActivity {


    private final String LOG_TAG = TutorialActivity.class.getSimpleName();

    // Titles of the individual pages (displayed in tabs)
    // private final String[] PAGE_TITLES = new String[] {
    //       "Page 1",
    //     "Page 2",
    //   "Page 3"
    //};

    // The fragments that are used as the individual pages
    private final Fragment[] PAGES = new Fragment[] {
            new Page1Fragment(),
            new Page2Fragment(),
            new Page3Fragment(),
            new Page4Fragment(),
            new Page5Fragment()


    };

    // The ViewPager is responsible for sliding pages (fragments) in and out upon user input
    private ViewPager mViewPager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp);
        finish();
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_tutor );
        toolbar.setTitle( "Cara penggunaan" );
        setSupportActionBar( toolbar );
        toolbar.setBackgroundColor( Constant.color );
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayShowHomeEnabled( true );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        // Set the Toolbar as the activity's app bar (instead of the default ActionBar)
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Connect the ViewPager to our custom PagerAdapter. The PagerAdapter supplies the pages
        // (fragments) to the ViewPager, which the ViewPager needs to display.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));

        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to a new page, the corresponding tab is selected)
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //tabLayout.setupWithViewPager(mViewPager);
    }


    /* PagerAdapter for supplying the ViewPager with the pages (fragments) to display. */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        //  @Override
        //public CharSequence getPageTitle(int position) {
        //  return PAGE_TITLES[position];
        //}

    }
}
