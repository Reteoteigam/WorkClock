package com.reteoteigam.workclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.reteoteigam.workclock.model.Booking;
import com.reteoteigam.workclock.model.ModelService;

public class DisplayMessageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        ModelService.saveModel();
        Booking currentBooking = ModelService.getLastBooking();
        TextView textView = (TextView) findViewById(R.id.textView);
        String text = ModelService.getPrintFrom(currentBooking);


        textView.setText(text);


    }


    public void gotoEditor(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }


}
