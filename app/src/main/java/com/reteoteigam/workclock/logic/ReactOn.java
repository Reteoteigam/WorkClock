package com.reteoteigam.workclock.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Sammy on 26.05.2017.
 */

public class ReactOn {

    private static final String DEFAULT = "No Information found";
    private static final String KNOWN_NAMES_FILE = "knownNames";

    private static Properties knownNames = new Properties();

    public ReactOn() {

        File knownNamesFile = new File(KNOWN_NAMES_FILE);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(knownNamesFile);
            InputStream inStream = fis;
            knownNames.load(inStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String findInformationFor(String message) {
        String result = DEFAULT;

        if (knownNames.containsKey(message)) {
            result = knownNames.get(message).toString();
        }


        return result;

    }
}
