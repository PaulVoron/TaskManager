package com.gmail.voron.paul.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Новая запись");
        final EditText etName = findViewById(R.id.etName);
        final ImageButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setEnabled(false);
        btnAdd.setClickable(false);
        btnAdd.setAlpha(75);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task(etName.getText().toString(), 0);
                Intent data = new Intent().putExtra(Task.class.getName(), task);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!text.isEmpty()){
                    btnAdd.setEnabled(true);
                    btnAdd.setClickable(true);
                    btnAdd.setAlpha(255);
                } else {
                    btnAdd.setEnabled(false);
                    btnAdd.setClickable(false);
                    btnAdd.setAlpha(75);
                }
            }
        });
    }
}
