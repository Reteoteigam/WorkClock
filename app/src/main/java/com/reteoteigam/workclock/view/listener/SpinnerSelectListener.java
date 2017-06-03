package com.reteoteigam.workclock.view.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * Created by Sammy on 02.06.2017.
 */

public class SpinnerSelectListener implements AdapterView.OnItemSelectedListener {

    private TextView textView;

    public SpinnerSelectListener(View view) {
        this.textView = (TextView) view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view instanceof TextView) {
            TextView newText = (TextView) view;
            textView.setText(newText.getText());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
