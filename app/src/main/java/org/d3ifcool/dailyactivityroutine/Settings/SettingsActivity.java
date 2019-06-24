package org.d3ifcool.dailyactivityroutine.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.d3ifcool.dailyactivityroutine.Profile.ProfileActivity;
import org.d3ifcool.dailyactivityroutine.R;

public class SettingsActivity extends AppCompatActivity {
    String item[] = new String[] {"Antar muka", "Edit Profil"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp);
        finish();
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_all );
        toolbar.setTitle( "Pengaturan" );
        toolbar.setBackgroundColor(Constant.color);
        setSupportActionBar( toolbar );
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }

        ListView listView = (ListView) findViewById( R.id.list_setting );
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1,item );
        listView.setAdapter( adapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText( SettingsActivity.this,item[position], Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0 :
                        Intent myinntent = new Intent( view.getContext(),AntarMukaActivity.class );
                        startActivity( myinntent );
                        break;
                    case 1 :
                        myinntent = new Intent( view.getContext(),ProfileActivity.class );
                        startActivity( myinntent );
                        break;

                }
            }
        } );
    }

    public void btnantarmuka(View view) {
        Intent menu1 = new Intent( SettingsActivity.this, AntarMukaActivity.class );
        startActivity( menu1 );
    }

}
