package com.reteoteigam.workclock.view.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.reteoteigam.workclock.model.Booking;

/**
 * Created by Sammy on 02.06.2017.
 */

public class SpinnerSelectListener implements AdapterView.OnItemSelectedListener {

    private TextView name;
    private TextView description;

    public SpinnerSelectListener(View name, View description) {

        this.name = (TextView) name;
        this.description = (TextView) description;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getAdapter().getItem(position);
        if (item instanceof Booking) {
            Booking booking = (Booking) item;

            name.setText(booking.getName());
            description.setText(booking.getDescription());
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
