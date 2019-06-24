package org.d3ifcool.dailyactivityroutine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.d3ifcool.dailyactivityroutine.About.About;
import org.d3ifcool.dailyactivityroutine.Diary.DiaryBookActivity;
import org.d3ifcool.dailyactivityroutine.Help.HelpActivity;
import org.d3ifcool.dailyactivityroutine.News.MenuNews;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;
import org.d3ifcool.dailyactivityroutine.Settings.SettingsActivity;
import org.d3ifcool.dailyactivityroutine.Statistik.StatisticsActivity;
import org.d3ifcool.dailyactivityroutine.Timeline.AddScheduleActivity;
import org.d3ifcool.dailyactivityroutine.Timeline.DaysAdapter;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE = 100;
    final String TAG = this.getClass().getName();

    Constant constant;
    SharedPreferences.Editor editor;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        Constant.color = appColor;

        if (themeColor == 0){
            setTheme(Constant.theme);
        }else if (appTheme == 0){
            setTheme(Constant.theme);
        }else{
           setTheme(appTheme);
       }

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        //toolbar.inflateMenu( R.menu.main_add_label );
        toolbar.setTitle( "Jadwal" );
        toolbar.setBackgroundColor(Constant.color);
        setSupportActionBar( toolbar );

        ViewPager viewPager =(ViewPager) findViewById(R.id.viewpager);
        DaysAdapter adapter = new DaysAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(Constant.color);


        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.floatingActionButton );
        fab.setBackgroundTintList( ColorStateList.valueOf( Constant.color ));
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, AddScheduleActivity.class );
                startActivity( intent );

            }
        } );
//        floatingActionButton  = (FloatingActionButton) findViewById( R.id.floatingActionMenu );
//        Jadwal = (com.github.clans.fab.FloatingActionButton) findViewById( R.id.fab_add_label );
//        Memo = (com.github.clans.fab.FloatingActionButton) findViewById( R.id.floatingActionItem2 );
//        Buku_harian = (com.github.clans.fab.FloatingActionButton) findViewById( R.id.floatingActionItem3 );
//        toolbar.setBackgroundColor(Constant.color);
//        Jadwal.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( MainActivity.this, AddScheduleActivity.class );
//                startActivity( intent );
//                Toast.makeText( MainActivity.this, "Jadwal", Toast.LENGTH_SHORT ).show();
//            }
//        });
//
//        Memo.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                disp();
//                Toast.makeText( MainActivity.this, "Memo", Toast.LENGTH_SHORT ).show();
//            }
//        });
//
//        Buku_harian.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( MainActivity.this, AddDiaryActivity.class );
//                startActivity( intent );
//                Toast.makeText( MainActivity.this, "Buku harian", Toast.LENGTH_SHORT ).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        LayoutInflater l = getLayoutInflater();
        View r = l.inflate( R.layout.nav_header_main, null );

        CircleImageView circleImageView = r.findViewById( R.id.profile_imageView );
//        circleImageView.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                klik_profile();
//
//            }
//        } );
    }
    boolean doubleclick = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {

            Log.d( TAG, "click" );

            if (doubleclick == true){
                Intent intent = new Intent( Intent.ACTION_MAIN );
                intent.addCategory( Intent.CATEGORY_HOME );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                finish();
                System.exit( 0 );
            }
            //super.onBackPressed();
            doubleclick = true;
            Log.d( TAG, "doubleclick" + doubleclick );

            Toast.makeText( MainActivity.this, "Please back again to exit", Toast.LENGTH_SHORT ).show();
            new Handler( ).postDelayed( new Runnable() {
                @Override
                public void run() {
                    doubleclick = false;
                    Log.d( TAG, "doubleclick" + doubleclick );
                }
            }, 1500 );

        }
//        final AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
//        builder.setMessage( "Are you sure want to do this ? " );
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         //Handle action bar item clicks here. The action bar will
         //automatically handle clicks on the Home/Up button, solong
         //as you specify a parent activity in AndroidManifest.xml.
         int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent menu1 = new Intent( MainActivity.this, SettingsActivity.class );
            startActivity( menu1 );
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_jadwal) {
//            Intent menu1 = new Intent( MainActivity.this, MainActivity.class );
//            startActivity( menu1 );
        } else if (id == R.id.nav_memo) {
            disp();
        } else if (id == R.id.nav_buku_harian) {
            Intent intent = new Intent( MainActivity.this, DiaryBookActivity.class );
            startActivity( intent );
        } else if (id == R.id.nav_statistiks) {
            Intent intent = new Intent( MainActivity.this, StatisticsActivity.class );
            startActivity( intent );
        }
        else if (id == R.id.nav_pengaturan) {
            Intent intent = new Intent( MainActivity.this, SettingsActivity.class );
            startActivity( intent );
        } else if (id == R.id.nav_bantuan) {
            Intent intent = new Intent( MainActivity.this, HelpActivity.class );
            startActivity( intent );
        }else if (id == R.id.nav_tentang_kami){
            Intent intent = new Intent( MainActivity.this, About.class );
            startActivity( intent );
        }else if (id== R.id.nav_news){
            Intent intent = new Intent( MainActivity.this, MenuNews.class );
            startActivity( intent );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    public void disp() {
        Calendar startTime = Calendar.getInstance();
        startTime.set(2018, 1, 1, 11, 35);
        Uri uri = Uri.parse("content://com.android.calendar/time/"
                + String.valueOf(startTime.getTimeInMillis()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // Use the Calendar app to view the time.
        startActivity(intent);
    }
//    public void klik_profile() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this );
//        LayoutInflater layoutInflater = this.getLayoutInflater();
//        View dialog = layoutInflater.inflate( R.layout.activity_custom_image_profile, null );
//        alertDialog.setView( dialog );
//        AlertDialog alertDialog1 = alertDialog.create();
//        alertDialog1.show();
//    }
//
//    public void btnprofil(View view) {
//        klik_profile();
//    }

//    public void btncamera(View view) {
//        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
//        startActivityForResult( intent, 0 );
//    }
//    public void btngalery(View view) {
//        Intent galery = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );
//        startActivityForResult( galery, PICK_IMAGE );
//    }
}