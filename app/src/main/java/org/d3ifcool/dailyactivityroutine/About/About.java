package org.d3ifcool.dailyactivityroutine.About;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.d3ifcool.dailyactivityroutine.Help.Feedback;
import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

public class About extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp);
        finish();
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_all );
        toolbar.setTitle( "Tentang" );
        toolbar.setBackgroundColor( Constant.color);
        setSupportActionBar( toolbar );
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
    }

    public void email_click(View view) {
        Intent intent = new Intent( About.this, Feedback.class );
        startActivity( intent );
    }
}
