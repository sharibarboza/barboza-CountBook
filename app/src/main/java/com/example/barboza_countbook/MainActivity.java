package com.example.barboza_countbook;

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

public class MainActivity extends AppCompatActivity {

    private int totalCount;
    private CounterController counters;
    private ListView counterListView;

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

            }
        });

        counters = new CounterController(MainActivity.this);
        counterListView = (ListView) findViewById(R.id.oldCounterList);

    }

    @Override
    protected void onStart() {
        super.onStart();

        counters.configCounterList();
        ArrayList<Counter> counterList = counters.getCounterList();
        ArrayAdapter<Counter> adapter = new ArrayAdapter<Counter>(this, R.layout.list_item, counterList);
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
