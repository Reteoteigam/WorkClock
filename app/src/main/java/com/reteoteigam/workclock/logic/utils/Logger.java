package com.reteoteigam.workclock.logic.utils;

import android.util.Log;

/**
 * Created by Sammy on 29.05.2017.
 */

public class Logger {
    public static void i(Class<?> clazz, String message) {
        Log.i(clazz.getSimpleName(), message);
    }

    public static void e(Class<?> clazz, String message, Throwable throwable) {
        Log.e(clazz.getSimpleName(), message, throwable);
    }
}
