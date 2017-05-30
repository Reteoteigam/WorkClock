package com.reteoteigam.workclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.reteoteigam.workclock.logic.ReactOn;

public class DisplayMessageActivity extends AppCompatActivity {

    private ReactOn reactOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reactOn = new ReactOn();
        setContentView(R.layout.activity_display_message);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        message = reactOn.findInformationFor(message);
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);


    }


    public void gotoEditor(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }




}
