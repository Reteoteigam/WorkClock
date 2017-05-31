package com.reteoteigam.workclock;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.reteoteigam.workclock.logic.utils.FileService;
import com.reteoteigam.workclock.logic.utils.Logger;
import com.reteoteigam.workclock.model.Booking;
import com.reteoteigam.workclock.model.ModelService;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private static final int REQUESTCODE_STORAGE_PERMISSION = 0;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int[] EXPECTED_GRANT_RESULTS = new int[]{0, 0};
    private static boolean storagePermitted = false;

    /**
     * Method to check whether external media available and writable. This is adapted from
     * http://developer.android.com/guide/topics/data/data-storage.html#filesExternal
     */
    private static void checkExternalMedia() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        Logger.i(MainActivity.class, "\n\nExternal Media: readable=" + mExternalStorageAvailable + " writable=" + mExternalStorageWriteable);
    }

    private static void storagePermitted(Activity activity) {
        storagePermitted = (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!storagePermitted) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUESTCODE_STORAGE_PERMISSION);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger.i(MainActivity.class, "onCreate");

        setContentView(R.layout.activity_main);
        checkExternalMedia();

        storagePermitted(this);

        if (storagePermitted) {
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        storagePermitted =
                requestCode == REQUESTCODE_STORAGE_PERMISSION
                        && grantResults.length == EXPECTED_GRANT_RESULTS.length
                        && grantResults[0] == EXPECTED_GRANT_RESULTS[0]
                        && grantResults[1] == EXPECTED_GRANT_RESULTS[1];

        Logger.i(MainActivity.class, "User grant permission:" + storagePermitted);

        if (storagePermitted) {
            Logger.i(MainActivity.class, "start");
            init();

        } else {
            Logger.i(MainActivity.class, "end");
            this.finish();
        }


    }

    public void init() {
        File externalDir = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        FileService.init(externalDir);
        ModelService.loadModel();
    }

    public void sendMessage(View View) {


        Logger.i(MainActivity.class, "sendMessage()");
        EditText name = (EditText) findViewById(R.id.name);
        EditText content = (EditText) findViewById(R.id.content);
        Booking booking = new Booking();
        booking.setTime(System.currentTimeMillis());
        booking.setName(name.getText().toString());
        booking.setContent(content.getText().toString());
        ModelService.add(booking);

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);

    }


    public void exit(View view) {

        Log.i(DisplayMessageActivity.class.getSimpleName(), "exit button was tapped");
        this.finish();
    }

}
