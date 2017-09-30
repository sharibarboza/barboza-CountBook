package com.example.barboza_countbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * MainActivity
 *
 * The main activity for the application, mainly responsible for displaying
 * the opening screen and showing the list view of counters. Also, handles
 * incrementing, decrementing, and resetting actions for any counter.
 *
 * Created by sharidanbarboza on 2017-09-24.
 */

public class MainActivity extends AppCompatActivity {

    private ListView counterListView;
    private ArrayList<Counter> counterList;
    private CounterController cc;
    private CustomAdapter adapter;
    private Context context;

    /**
     * Called when the main activity is first created.
     * @param savedInstanceState for passing data between activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the activity context
        context = MainActivity.this;

        // Clicking the FAB will take user to the add counter activity
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

        // Get the counter controller
        cc = CounterApplication.getCounterController(getApplicationContext());

        // Load and initialize the counter array data
        cc.configCounters();
        counterList = cc.getCounters().getList();

        // Register context menu for list view
        counterListView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(counterListView);

        // Handle list view click events
        // Taken from https://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
        // 2017-9-28
        counterListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Taken from https://www.youtube.com/watch?v=plnLs6aST1M&t=758s
                // 2017-9-27
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Set the view
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.counter_dialog, null);
                builder.setView(v);

                // Get counter
                final Counter counter = cc.getCounter(position);

                // Set the counter name and comment
                TextView dialogName = (TextView) v.findViewById(R.id.dialogName);
                TextView dialogComment = (TextView) v.findViewById(R.id.dialogComment);
                dialogName.setText(counter.getName());
                dialogComment.setText(counter.getComment());

                // Set the current value
                final TextView dialogValue = (TextView) v.findViewById(R.id.dialogValue);
                dialogValue.setText(String.valueOf(counter.getCurrentVal()));

                // Set up the dialog buttons
                Button upBtn = (Button) v.findViewById(R.id.increment);
                Button downBtn = (Button) v.findViewById(R.id.decrement);
                Button resetBtn = (Button) v.findViewById(R.id.reset);
                Button doneBtn = (Button) v.findViewById(R.id.done);

                // Increment counter value when up button is clicked
                upBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cc.incrementCounter(counter);
                        adapter.notifyDataSetChanged();
                        dialogValue.setText(String.valueOf(counter.getCurrentVal()));
                    }
                });

                // Decrement counter value when down button is clicked.
                downBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cc.decrementCounter(counter)) {
                            adapter.notifyDataSetChanged();
                            dialogValue.setText(String.valueOf(counter.getCurrentVal()));
                        } else {
                            // Notify user that they cannot count below 0
                            String neg_str = context.getString(R.string.negative_value);
                            Toast.makeText(context, neg_str, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Reset counter value when reset button is clicked
                resetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cc.resetCounter(counter);
                        String reset_str = context.getString(R.string.reset_toast);
                        Toast.makeText(context, reset_str, Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        dialogValue.setText(String.valueOf(counter.getCurrentVal()));
                    }
                });

                // Build and display the dialog
                final AlertDialog dialog = builder.create();
                dialog.show();

                // Exit alert dialog
                doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

            }
        });
    }

    /**
     * Called after onCreate or when returning to the main activity.
     * Mainly creates and updates the adapter for displaying the list view.
     */
    @Override
    protected void onStart() {
        super.onStart();
        adapter = new CustomAdapter(this, R.layout.list_item, counterList);
        counterListView.setAdapter(adapter);
        updateTotal();
    }

    /**
     * Creates the context menu for displaying counter options, such as editing or
     * deleting a counter. Opens after user long clicks on a list view item.
     * @param menu the context menu being built
     * @param v the view for which the menu is being built
     * @param menuInfo extra information about the menu item
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // Taken from http://wptrafficanalyzer.in/blog/creating-a-floating-contextual-menu-in-android/
        // 2017-9-25
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.actions , menu);
    }

    /**
     * Called whenever a context menu item is selected and deals with calling
     * the appropriate activity/action for each option.
     * @param item the context menu item that was selected
     * @return false to allow normal context processing to proceed or true when an
     * option in the menu has been selected
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Taken from https://developer.android.com/guide/topics/ui/menus.html#PopupMenu
        // 2017-9-26
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()) {
            case R.id.menu_delete:
                // Delete the counter selected
                cc.deleteCounter(position);
                adapter.notifyDataSetChanged();
                String delete_str = context.getString(R.string.delete_toast);
                Toast.makeText(context, delete_str, Toast.LENGTH_SHORT).show();
                updateTotal();
                return true;
            case R.id.menu_edit:
                // Edit the counter selected
                // Taken from https://stackoverflow.com/questions/34120858/how-do-i-pass-listview-data-to-another-activity
                // 2017-9-27
                setResult(RESULT_OK);
                Intent editCounterIntent = new Intent(context, EditCounterActivity.class);
                editCounterIntent.putExtra("position", position);
                startActivity(editCounterIntent);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Changes the value of total counters text view usually called after adding
     * or deleting a counter.
     */
    public void updateTotal() {
        TextView textView = (TextView) findViewById(R.id.totalCount);
        String totalCountersText = "Total Counters: " + adapter.getCount();
        textView.setText(totalCountersText);
    }
}
