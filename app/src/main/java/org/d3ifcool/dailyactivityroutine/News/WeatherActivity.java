package org.d3ifcool.dailyactivityroutine.News;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.d3ifcool.dailyactivityroutine.R;

public class WeatherActivity extends AppCompatActivity {


    private EditText locationEditText;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(this)) buildDialog(WeatherActivity.this).show();
        else {
            Toast.makeText(WeatherActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView( R.layout.activity_weather );
            setWidgets();
        }
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
        return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }
    private String getUrlFor(String location) {
        return "http://openweathermap.org/data/2.5/weather?q=" +
                location +
                "&appid=b6907d289e10d714a6e88b30761fae22";
    }


    private void setWidgets() {
        locationEditText = (EditText) findViewById( R.id.location);
        descriptionTextView = (TextView) findViewById(R.id.description);
    }

    public void search(View view) {
        String location = getLocation();
        setDescriptionFor(location);
        turnKeyboardOff();
    }

    private String getLocation() {
        return locationEditText.getText().toString();
    }

    private void setDescriptionFor(String location) {
        String description = getDescriptionFor(location);
        descriptionTextView.setText(description);
    }

    private String getDescriptionFor(String location) {
        String url = getUrlFor(location);
        String data = getDataFrom(url);
        String description = getEditedDescriptionFrom(data);
        return description;
    }

    private String getDataFrom(String url) {
        try {
            HtmlDownloader downloader = new HtmlDownloader();
            return downloader.execute(url).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getEditedDescriptionFrom(String data) {
        return JSONParser.getDataFrom(data);
    }

    private void turnKeyboardOff() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(locationEditText.getWindowToken(), 0);
    }
}