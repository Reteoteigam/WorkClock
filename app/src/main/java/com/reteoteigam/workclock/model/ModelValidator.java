package com.reteoteigam.workclock.model;


/**
 * Created by Sammy on 02.06.2017.
 */

public class ModelValidator {
    public static boolean isNotEmpty(String text) {
        return text != null && text.length() >= 1;
    }
}
