package com.example.xander.fxconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ConvertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        EditText editText;


            editText = (EditText)findViewById(R.id.editText1);

            // Receiving value into activity using intent.
            String TempHolder = getIntent().getStringExtra("ItemClick");

            // Setting up received value into EditText.
            editText.setText(TempHolder);

    }
}
