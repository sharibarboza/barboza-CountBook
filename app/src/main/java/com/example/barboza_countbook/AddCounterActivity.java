package com.example.barboza_countbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCounterActivity extends AppCompatActivity {

    private Counter newCounter;
    private EditText inputName, inputValue, inputComment;
    private String name, comment, date;
    private int value;
    private Button addBtn, cancelBtn;
    private Context context;

    private CounterController cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);
        context = getApplicationContext();

        inputName = (EditText) findViewById(R.id.inputName);
        inputValue = (EditText) findViewById(R.id.inputValue);
        inputComment = (EditText) findViewById(R.id.inputComment);

        addBtn = (Button) findViewById(R.id.addBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        cc = CounterApplication.getCounterController(context);

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
        if (CounterUtils.validate(this, inputName, inputValue, null)) {
            initalize();
            cc.addCounter(newCounter);
            Toast.makeText(context, context.getString(R.string.add_toast),
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initalize() {
        name = inputName.getText().toString().trim();
        value = Integer.parseInt(inputValue.getText().toString().trim());
        comment = inputComment.getText().toString().trim();

        newCounter = new Counter(name, value);
        newCounter.setName(name);
        newCounter.setInitVal(value);
        newCounter.setDate(CounterUtils.initalizeDate(this));
        if (comment.length() > 0) {
            newCounter.setComment(comment);
        }
    }

}
