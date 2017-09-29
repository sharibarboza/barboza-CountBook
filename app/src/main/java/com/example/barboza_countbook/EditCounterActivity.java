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

public class EditCounterActivity extends AppCompatActivity {

    private CounterController cc;
    private EditText editName, editCurrent, editValue, editComment;
    private TextView editDate;
    private String name, comment;
    private int current, value;
    private Intent intent;
    private Counter counter;
    private Button editBtn, cancelBtn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);
        context = getApplicationContext();

        // Get the controller
        cc = CounterApplication.getCounterController(context);
        // Get the counter to edit
        intent = getIntent();
        int position = intent.getExtras().getInt("position");
        counter = cc.getCounter(position);

        // Set up edit text views
        editName = (EditText) findViewById(R.id.editName);
        editCurrent = (EditText) findViewById(R.id.editCurrent);
        editValue = (EditText) findViewById(R.id.editValue);
        editComment = (EditText) findViewById(R.id.editComment);
        editDate = (TextView) findViewById(R.id.editDate);

        // Set text for counter's current fields
        editName.setText(counter.getName());
        editCurrent.setText(String.valueOf(counter.getCurrentVal()));
        editValue.setText(String.valueOf(counter.getInitVal()));
        editComment.setText(counter.getComment());

        // Set up display for counter date
        String lastUpdate = context.getString(R.string.last_updated) + " " + counter.getDate();
        editDate.setText(lastUpdate);

        // Set up buttons
        editBtn = (Button) findViewById(R.id.editBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtnEdit);

        // Edit counter with the updated fields
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCounter();
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

    public void updateCounter() {
        if (CounterUtils.validate(this, editName, editValue, editCurrent)) {
            initialize();
            cc.updateCounters();
            Toast.makeText(context, context.getString(R.string.edit_toast),
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initialize() {
        name = editName.getText().toString().trim();
        current = Integer.parseInt(editCurrent.getText().toString().trim());
        value = Integer.parseInt(editValue.getText().toString().trim());
        comment = editComment.getText().toString().trim();

        if (!name.equals(counter.getName())) {
            counter.setName(name);
        }

        if (current != counter.getCurrentVal()) {
            counter.setCurrentVal(current);
        }

        if (value != counter.getInitVal()) {
            counter.setInitVal(value);
        }

        if (!comment.equals(counter.getComment())) {
            counter.setComment(comment);
        }

        counter.setDate(CounterUtils.initalizeDate(context));
    }
}
