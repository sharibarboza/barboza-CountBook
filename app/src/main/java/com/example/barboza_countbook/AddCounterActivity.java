package com.example.barboza_countbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCounterActivity extends AppCompatActivity {

    private Counter newCounter;
    private EditText inputName, inputValue, inputComment;
    private String name, comment, date;
    private int value;
    Button addBtn, cancelBtn;

    private CounterController cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);
        inputName = (EditText) findViewById(R.id.inputName);
        inputValue = (EditText) findViewById(R.id.inputValue);
        inputComment = (EditText) findViewById(R.id.inputComment);

        addBtn = (Button) findViewById(R.id.addBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        cc = CounterApplication.getCounterController(getApplicationContext());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCounter();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addCounter() {
        if (validate()) {
            initalize();
            cc.addNewCounter(newCounter);
            finish();
        }
    }

    public void initalize() {
        name = inputName.getText().toString().trim();
        value = Integer.parseInt(inputValue.getText().toString().trim());
        comment = inputComment.getText().toString().trim();

        newCounter = new Counter(name, value);
        newCounter.setName(name);
        newCounter.setInitVal(value);
        newCounter.setDate(initalizeDate());
        if (comment.length() > 0) {
            newCounter.setComment(comment);
        }
    }

    public String initalizeDate() {
        Date currentDate = new Date();
        return new SimpleDateFormat(this.getString(R.string.date_format)).format(currentDate);
    }

    public boolean validate() {
        boolean valid;
        String tempName = inputName.getText().toString();
        String tempValue = inputValue.getText().toString().trim();

        if (tempName.isEmpty()) {
            Toast.makeText(this, this.getString(R.string.name_required), Toast.LENGTH_LONG).show();
            valid = false;
        } else if (tempValue.isEmpty()) {
            Toast.makeText(this, this.getString(R.string.value_required), Toast.LENGTH_LONG).show();
            valid = false;
        } else if (!isInteger(tempValue)) {
            Toast.makeText(this, this.getString(R.string.is_num), Toast.LENGTH_LONG).show();
            valid = false;
        } else if (Integer.parseInt(tempValue) < 0) {
            Toast.makeText(this, this.getString(R.string.is_positive), Toast.LENGTH_LONG).show();
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public boolean isInteger(String input) {
        boolean isInteger = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            isInteger = false;
        }
        return isInteger;
    }


}
