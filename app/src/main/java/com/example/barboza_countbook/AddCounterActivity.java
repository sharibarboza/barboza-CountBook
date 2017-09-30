package com.example.barboza_countbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * AddCounterActivity
 *
 * The activity for adding a new counter.
 *
 * Created by sharidanbarboza on 2017-09-24.
 */
public class AddCounterActivity extends AppCompatActivity {

    private Counter newCounter;
    private EditText inputName;
    private EditText inputValue;
    private EditText inputComment;
    private Context context;

    private CounterController cc;

    /**
     * Called when the add activity is first created.
     * @param savedInstanceState for passing data between activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        // Get the activity context to access add activity resources
        context = AddCounterActivity.this;

        // Get the input field views
        inputName = (EditText) findViewById(R.id.inputName);
        inputValue = (EditText) findViewById(R.id.inputValue);
        inputComment = (EditText) findViewById(R.id.inputComment);

        // Set up buttons
        Button addBtn = (Button) findViewById(R.id.addBtn);
        Button cancelBtn = (Button) findViewById(R.id.cancelBtn);

        // Get the counter controller
        cc = CounterApplication.getCounterController(getApplicationContext());

        // Add counter when the add button is clicked
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCounter();
            }
        });

        // Go back to the main activity
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Add a new counter after validating all input fields.
     */
    public void addCounter() {
        if (CounterUtils.validate(context, inputName, inputValue, null)) {
            initialize();
            cc.addCounter(newCounter);
            String add_str = context.getString(R.string.add_toast);
            Toast.makeText(context, add_str, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Get the user's input for the counter fields and create a new
     * instance of a counter object. Then set the counter's values.
     */
    private void initialize() {
        // Get and convert the input values
        String name = inputName.getText().toString().trim();
        int value = Integer.parseInt(inputValue.getText().toString().trim());
        String comment = inputComment.getText().toString().trim();

        // Create new counter object
        newCounter = new Counter(name, value);

        // Set counter values
        newCounter.setName(name);
        newCounter.setInitVal(value);
        cc.changeDate(newCounter);
        if (comment.length() > 0) {
            newCounter.setComment(comment);
        }
    }

}
