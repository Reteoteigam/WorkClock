package com.reteoteigam.workclock.logic.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Sammy on 28.05.2017.
 */


public class PropertyUtils {


    //private static final String PROPERTIES_FILENAME = Resources.getSystem().getString(R.string.properties_file);
    private static final String PROPERTIES_FILENAME = "knownNames.txt";

    /**
     * @return a properties based on the content of the file which is under the given name
     */
    public static Properties loadProperties() {


        File resultFile = new File(FileService.getExternalDir(), PROPERTIES_FILENAME);

        Properties result = new Properties();
        try {
            if (!resultFile.exists()) {
                resultFile.createNewFile();
                return result;
            }
            FileInputStream fis = new FileInputStream(resultFile);
            InputStream inStream = fis;
            result.load(inStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void storeProperties(Properties knownNames) {
        File targetFile = new File(FileService.getExternalDir(), PROPERTIES_FILENAME);

        try {
            FileOutputStream fos = new FileOutputStream(targetFile);
            OutputStream outStream = fos;
            knownNames.store(outStream, PropertyUtils.class.toString());
        } catch (FileNotFoundException e) {
            Log.w(PropertyUtils.class.toString(), "Error File not found " + targetFile, e);
        } catch (IOException e) {
            // Unable to create file, likely because external storage is
            // not currently mounted.
            Log.w(PropertyUtils.class.toString(), "Error writing " + targetFile, e);
        }

    }


}
