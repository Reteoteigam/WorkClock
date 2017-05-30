package com.reteoteigam.workclock.logic;


import com.reteoteigam.workclock.MainActivity;
import com.reteoteigam.workclock.logic.utils.PropertyUtils;

import java.util.Properties;

/**
 * Created by Sammy on 26.05.2017.
 */

public class ReactOn {

    private static final String DEFAULT = "No Information found";

    private Properties knownNames = null;

    public ReactOn() {

        knownNames = PropertyUtils.loadProperties();
    }

    public String findInformationFor(String message) {
        String result = null;

        int i = message.lastIndexOf(MainActivity.SPLITTER);
        String key = message.substring(0, i);
        String value = message.substring(i + MainActivity.SPLITTER.length(), message.length());


        if (knownNames.containsKey(key)) {
            result = knownNames.get(key).toString();
        } else {
            result = DEFAULT;
            knownNames.put(key, value);
            PropertyUtils.storeProperties(knownNames);
        }
        result = key + "=" + value;
//TODO change information
        return result;
    }

}

