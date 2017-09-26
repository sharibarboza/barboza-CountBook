package com.example.barboza_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private int totalCount;
    private ListView counterListView;
    private Counters counters;
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

        cc = CounterApplication.getCounterController(this);
        cc.configCounters();
        counters = cc.getCounters();
        counterList = counters.getList();
        counterListView = (ListView) findViewById(R.id.listView);

        // Register context for list view
        registerForContextMenu(counterListView);

    }

    @Override
    protected void onStart() {
        super.onStart();

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

    /**
     * Taken from http://wptrafficanalyzer.in/blog/creating-a-floating-contextual-menu-in-android/
     * 2017-9-25
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.actions , menu);
    }

}
