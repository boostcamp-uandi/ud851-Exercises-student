package com.example.android.explicitintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by mk on 2017-01-01.
 */

public class ChildActivity extends AppCompatActivity{
    private TextView mDisplayText;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mDisplayText = (TextView) findViewById(R.id.tv_display);
        Intent intent = getIntent();
        String message = intent.getExtras().getString("message");
        mDisplayText.setText(message);


    }
}
