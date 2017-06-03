package com.reteoteigam.workclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.reteoteigam.workclock.logic.utils.FileService;
import com.reteoteigam.workclock.model.Booking;
import com.reteoteigam.workclock.model.ModelService;

import java.io.File;

public class DisplayMessageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Booking currentBooking = ModelService.getModel().peek();
        TextView textView = (TextView) findViewById(R.id.textView);
        String text = ModelService.formatTimeToHHmm(currentBooking.getTime()) + "\n" + currentBooking.getName() + "\n" + currentBooking.getDescription();

        textView.setText(text);

    }

    @Override
    protected void onDestroy() {
        File storage = FileService.createFile(this.getString(R.string.fileName_storage));
        ModelService.writeModel(ModelService.getModel(), storage, false);
        super.onDestroy();
    }

    public void gotoEditor(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }


}
