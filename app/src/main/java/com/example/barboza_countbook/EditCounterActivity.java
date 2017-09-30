package com.example.barboza_countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * EditCounterActivity
 *
 * The activity for editing an existing counter.
 *
 * Created by sharidanbarboza on 2017-09-25.
 */
public class EditCounterActivity extends AppCompatActivity {

    private Counter counter;
    private EditText editName;
    private EditText editCurrent;
    private EditText editValue;
    private EditText editComment;
    private Context context;

    private CounterController cc;

    /**
     * Called when the edit activity is first created.
     * @param savedInstanceState for passing data between activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        // Get the activity context to access edit activity resources
        context = EditCounterActivity.this;

        // Get the counter controller
        cc = CounterApplication.getCounterController(getApplicationContext());

        // Get the counter to edit
        // Taken from https://stackoverflow.com/questions/34120858/how-do-i-pass-listview-data-to-another-activity
        // 2017-9-27
        Intent intent = getIntent();
        int position = intent.getExtras().getInt("position");
        counter = cc.getCounter(position);

        // Set up edit text views
        editName = (EditText) findViewById(R.id.editName);
        editCurrent = (EditText) findViewById(R.id.editCurrent);
        editValue = (EditText) findViewById(R.id.editValue);
        editComment = (EditText) findViewById(R.id.editComment);
        TextView editDate = (TextView) findViewById(R.id.editDate);

        // Set text for counter's current fields
        editName.setText(counter.getName());
        editCurrent.setText(String.valueOf(counter.getCurrentVal()));
        editValue.setText(String.valueOf(counter.getInitVal()));
        editComment.setText(counter.getComment());

        // Set up display for counter date
        String lastUpdate = context.getString(R.string.last_updated) + " " + counter.getDate();
        editDate.setText(lastUpdate);

        // Set up buttons
        Button editBtn = (Button) findViewById(R.id.editBtn);
        Button cancelBtn = (Button) findViewById(R.id.cancelBtnEdit);

        // Edit counter with the updated fields
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCounter();
            }
        });

        // Cancel editing and go back to main activity
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Edit the counter that was edited after validating all input fields.
     */
    public void editCounter() {
        if (CounterValidator.validate(context, editName, editValue, editCurrent)) {
            String name = editName.getText().toString().trim();
            int current = Integer.parseInt(editCurrent.getText().toString().trim());
            int value = Integer.parseInt(editValue.getText().toString().trim());
            String comment = editComment.getText().toString().trim();

            cc.editCounter(counter, name, current, value, comment);
            String update_str = context.getString(R.string.edit_toast);
            Toast.makeText(context, update_str, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
