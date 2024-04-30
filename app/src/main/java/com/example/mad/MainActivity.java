package com.example.mad;// MainActivity.java
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private ListView timeZoneListView;
    private ArrayAdapter<String> adapter;
    private List<String> timeZones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeZoneListView = findViewById(R.id.timeZoneListView);
        timeZones = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeZones);
        timeZoneListView.setAdapter(adapter);

        // Update current time for each time zone every second
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTimeZones();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void updateTimeZones() {
        timeZones.clear();
        // Add India time zone
        TimeZone indiaTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        SimpleDateFormat sdfIndia = new SimpleDateFormat("HH:mm:ss");
        sdfIndia.setTimeZone(indiaTimeZone);
        String indiaCurrentTime = sdfIndia.format(new Date());
        timeZones.add(indiaTimeZone.getDisplayName() + " - " + indiaCurrentTime);

        // Add times for other time zones
        for (String id : TimeZone.getAvailableIDs()) {
            if(!id.equals("Asia/Kolkata")) {
                TimeZone timeZone = TimeZone.getTimeZone(id);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(timeZone);
                String currentTime = sdf.format(new Date());
                timeZones.add(timeZone.getDisplayName() + " - " + currentTime);
            }
        }
        adapter.notifyDataSetChanged();
    }
}