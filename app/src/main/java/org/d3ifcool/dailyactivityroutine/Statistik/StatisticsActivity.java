package org.d3ifcool.dailyactivityroutine.Statistik;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.d3ifcool.dailyactivityroutine.R;
import org.d3ifcool.dailyactivityroutine.Settings.Constant;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {
    private BarChart statisticsChart;

    SeekBar seekBar;
    private int value_1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp);
        finish();
        return super.onOptionsItemSelected( item );
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_statistiks );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar_all );
        toolbar.setTitle( "Statistik" );
        toolbar.setBackgroundColor( Constant.color);
        setSupportActionBar( toolbar );
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }

        final TextView percent = findViewById(R.id.percentation);
        seekBar = findViewById(R.id.statisticsSeekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int a = progress;
                float b = a;
                float c = seekBar.getMax();
                float d = c - progress;

                percent.setText(String.valueOf(progress) + "%");

                statisticsChart = (BarChart) findViewById(R.id.statisticsChart);
                statisticsChart.setDescription("");
                final ArrayList<String> xAxis = new ArrayList<>();
                ArrayList<IBarDataSet> dataSets = null;
                ArrayList<BarEntry>  valueSets = new ArrayList<>();

                xAxis.add("Terlaksana");
                xAxis.add("Tidak Terlaksana");

                valueSets.add(new BarEntry(b,0));
                valueSets.add(new BarEntry(d,1));

                BarDataSet barDataSet = new BarDataSet(valueSets,"");
                barDataSet.setColors(new int[] {Color.GREEN,Color.RED});

                dataSets = new ArrayList<>();
                dataSets.add(barDataSet);

                YAxis yAxisRight = statisticsChart.getAxisRight();
                yAxisRight.setEnabled(false);


                BarData data = new BarData(xAxis,dataSets);
                statisticsChart.setExtraOffsets(0,0,0,20);
                statisticsChart.setData(data);

                statisticsChart.animateXY(2000,2000);
                statisticsChart.invalidate();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





    }

}
