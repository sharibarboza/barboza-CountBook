package com.example.barboza_countbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private int totalCount;
    private ListView counterListView;
    private ArrayList<Counter> counterList;
    private CounterController cc;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent addCounterIntent = new Intent(MainActivity.this, AddCounterActivity.class);
                startActivity(addCounterIntent);
                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        cc = CounterApplication.getCounterController(this);
        cc.configCounterList();
        counterList = cc.getCounterList();

        counterListView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(this, R.layout.list_item, counterList);
        counterListView.setAdapter(adapter);

        TextView textView = (TextView) findViewById(R.id.totalCount);
        String totalCountersText = "Total Counters: " + counterList.size();
        textView.setText(totalCountersText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
