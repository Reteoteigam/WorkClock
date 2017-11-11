package com.reteoteigam.workclock;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.reteoteigam.workclock.logic.utils.FileService;
import com.reteoteigam.workclock.logic.utils.Logger;
import com.reteoteigam.workclock.model.Booking;
import com.reteoteigam.workclock.model.ModelService;
import com.reteoteigam.workclock.model.ModelValidator;
import com.reteoteigam.workclock.view.listener.SpinnerSelectListener;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int REQUESTCODE_STORAGE_PERMISSION = 0;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int[] EXPECTED_GRANT_RESULTS = new int[]{0, 0};
    public static boolean IS_INITIALIZED_ = false;
    private static boolean storagePermitted = false;

    private static void checkExternalMedia() {
        boolean mExternalStorageAvailable;
        boolean mExternalStorageWriteable;
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
        Logger.i(MainActivity.class, "External Media: readable=" + mExternalStorageAvailable + " writable=" + mExternalStorageWriteable);
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


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Booking[] items = ModelService.getLastEntries(ModelService.getModel(), 3);
        ArrayAdapter<Booking> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectListener(findViewById(R.id.name), findViewById(R.id.description)));

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
        if (!IS_INITIALIZED_) {
            //init FileService
            File externalDir = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            FileService.init(externalDir);
            // init ModelService
            File storage = FileService.createFile(this.getString(R.string.fileName_storage));
            ModelService.init(storage, this.getString(R.string.fileName_uploads));
            IS_INITIALIZED_ = true;
        }
    }


    public void bookInput(View view) {
        Logger.i(MainActivity.class, String.format("Call bookInput from:[%s]", view));
        Booking booking = new Booking();
        booking.setTime(System.currentTimeMillis());
        String name = ((TextView) findViewById(R.id.name)).getText().toString();
        String description = ((TextView) findViewById(R.id.description)).getText().toString();

        boolean isNameValid = ModelValidator.isNotEmpty(name);
        //TODO show the user a valid input
        Logger.i(this.getClass(), String.format("The name is valid:[%s]", isNameValid));
        boolean isDescriptionValid = ModelValidator.isNotEmpty(description);
        //TODO show the user a valid input
        Logger.i(this.getClass(), String.format("The description is valid:[%s]", isDescriptionValid));
        if (isNameValid && isDescriptionValid) {
            booking.setName(name);
            booking.setContent(description);
            ModelService.getModel().add(booking);
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            startActivity(intent);
        }
    }


    public void sendAsMail(View view) {
        ArrayList<Uri> uris = new ArrayList<>(2);

        Uri uriCSV = ModelService.getUploadFrom(this.getString(R.string.fileName_storage));
        uris.add(uriCSV);

        File storage = FileService.createFile(this.getString(R.string.fileName_export));
        ModelService.writeModel(ModelService.getModel(), storage, true);
        Uri uriTXT = ModelService.getUploadFrom(this.getString(R.string.fileName_export));
        uris.add(uriTXT);


        Intent i = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
        i.setType("message/rfc822")
                // .putExtra(Intent.EXTRA_EMAIL, new String[]{"reteoteigam@gmail.com"})
                .putExtra(Intent.EXTRA_SUBJECT, "WorkClock_ExportAsMail")
                .putExtra(Intent.EXTRA_TEXT, "DETAIL MESSAGE")
                //.putExtra(Intent.EXTRA_STREAM, uriTXT);
                .putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Log.i("sendAsMail", ex.toString());
        }

    }

    public void deleteUploadsAndExit(View view) {

        this.getSharedPreferences("WorkClockPrefs", 0).edit().clear().apply();
        this.finish();
    }


}
