package com.example.barboza_countbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView counterListView;
    private Counters counters;
    private ArrayList<Counter> counterList;
    private CounterController cc;
    private CustomAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivity.this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent addCounterIntent = new Intent(context, AddCounterActivity.class);
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

        // Handle list view click events
        // Taken from https://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
        // 2017-9-28
        counterListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Set the view
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.counter_dialog, null);
                builder.setView(v);

                // Get counter
                final Counter counter = cc.getCounter(position);

                // Set the counter name
                TextView dialogName = (TextView) v.findViewById(R.id.dialogName);
                dialogName.setText(counter.getName());

                // Set the current value
                final TextView dialogValue = (TextView) v.findViewById(R.id.dialogValue);
                dialogValue.setText(String.valueOf(counter.getCurrentVal()));

                // Set up the dialog buttons
                Button upBtn = (Button) v.findViewById(R.id.increment);
                Button downBtn = (Button) v.findViewById(R.id.decrement);
                Button resetBtn = (Button) v.findViewById(R.id.reset);

                // Increment counter value when up button is clicked
                upBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cc.incrementCounter(counter);
                        adapter.notifyDataSetChanged();

                        // Update value in dialog
                        dialogValue.setText(String.valueOf(counter.getCurrentVal()));
                    }
                });

                // Decrement counter value when down button is clicked
                downBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cc.decrementCounter(counter);
                        adapter.notifyDataSetChanged();

                        // Update value in dialog
                        dialogValue.setText(String.valueOf(counter.getCurrentVal()));
                    }
                });

                // Reset counter value when reset button is clicked
                resetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cc.resetCounter(counter);
                        Toast.makeText(context, context.getString(R.string.reset_toast),
                                Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();

                        // Update value in dialog
                        dialogValue.setText(String.valueOf(counter.getCurrentVal()));
                    }
                });

                // Build and display the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new CustomAdapter(this, R.layout.list_item, counterList);
        counterListView.setAdapter(adapter);
        updateTotal();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // Taken from http://wptrafficanalyzer.in/blog/creating-a-floating-contextual-menu-in-android/
        // 2017-9-25
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.actions , menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Taken from https://developer.android.com/guide/topics/ui/menus.html#PopupMenu
        // 2017-9-26
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteCounter(position);
                return true;
            case R.id.menu_edit:
                editCounter(position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Changes the value of total counters text view usually called after adding
     * or deleting a counter.
     */
    private void updateTotal() {
        TextView textView = (TextView) findViewById(R.id.totalCount);
        String totalCountersText = "Total Counters: " + adapter.getCount();
        textView.setText(totalCountersText);
    }

    /**
     * Delete a counter
     * @param position the position of the counter in the counter array
     */
    public void deleteCounter(int position) {
        cc.deleteCounter(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, this.getString(R.string.delete_toast), Toast.LENGTH_SHORT).show();
        updateTotal();
    }

    /**
     * Edits a counter
     * @param position the position of the counter in the counter array
     */
    public void editCounter(int position) {
        setResult(RESULT_OK);
        Intent editCounterIntent = new Intent(context, EditCounterActivity.class);
        editCounterIntent.putExtra("position", position);
        startActivity(editCounterIntent);
        adapter.notifyDataSetChanged();
    }

}
