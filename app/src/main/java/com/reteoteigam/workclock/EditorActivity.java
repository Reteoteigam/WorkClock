package com.reteoteigam.workclock;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class EditorActivity extends AppCompatActivity {
    EditText inputText;
    TextView response;
    Button saveButton, readButton;
    File myExternalFile;
    String myData = "";
    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Properties properties = savedInstanceState.getParcelable(this.getString(R.string.properties));

        inputText = (EditText) findViewById(R.id.myInputText);
        response = (TextView) findViewById(R.id.response);


        saveButton =
                (Button) findViewById(R.id.saveExternalStorage);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(inputText.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputText.setText("");
                response.setText("SampleFile.txt saved to External Storage...");
            }
        });

        readButton = (Button) findViewById(R.id.getExternalStorage);
        readButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputText.setText(myData);
                response.setText("SampleFile.txt data retrieved from Internal Storage...");
            }
        });

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButton.setEnabled(false);
        } else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }


    }


}
