package com.reteoteigam.workclock.logic.utils;

import android.util.Log;

public class Logger {
    private static boolean isUseCompatible;

    public static void init(boolean isOldCompatible) {
        setOldCompatible(isOldCompatible);
    }

    public static void setOldCompatible(boolean isUseCompatible) {
        Logger.isUseCompatible = isUseCompatible;
    }

    private static String getTagName(String tagName) {
        if (isUseCompatible && tagName.length() > 23) {
            return tagName.substring(0, Math.min(23, tagName.length()));
        }
        return tagName;
    }

    private static String getTagName(Class<?> clazz) {
        String simpleClassName = clazz.getSimpleName();
        return getTagName(simpleClassName);
    }

    /**
     * info logger
     *
     * @param clazz   the class to log
     * @param message the message to log
     */
    public static void i(Class<?> clazz, String message) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.INFO)) {
            Log.i(tagName, message);
        }
    }

    /**
     * info logger
     *
     * @param clazz     the class to log
     * @param message   the message to log
     * @param throwable the throwable to log
     */
    public static void i(Class<?> clazz, String message, Throwable throwable) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.INFO)) {
            Log.i(tagName, message, throwable);
        }
    }

    /**
     * error logger
     *
     * @param clazz   the class to log
     * @param message the message to log
     */
    public static void e(Class<?> clazz, String message) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.ERROR)) {
            Log.e(tagName, message);
        }
    }

    /**
     * error logger
     *
     * @param clazz     the class to log
     * @param message   the message to log
     * @param throwable the throwable to log
     */
    public static void e(Class<?> clazz, String message, Throwable throwable) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.ERROR)) {
            Log.e(tagName, message, throwable);
        }
    }

    /**
     * debug logger
     *
     * @param clazz   the class to log
     * @param message the message to log
     */
    public static void d(Class<?> clazz, String message) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.DEBUG)) {
            Log.d(tagName, message);
        }
    }

    /**
     * debug logger
     *
     * @param clazz     the class to log
     * @param message   the message to log
     * @param throwable the throwable to log
     */
    public static void d(Class<?> clazz, String message, Throwable throwable) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.DEBUG)) {
            Log.d(tagName, message, throwable);
        }
    }

    /**
     * verbose logger
     *
     * @param clazz   the class to log
     * @param message the message to log
     */
    public static void v(Class<?> clazz, String message) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.VERBOSE)) {
            Log.v(tagName, message);
        }
    }

    /**
     * verbose logger
     *
     * @param clazz     the class to log
     * @param message   the message to log
     * @param throwable the throwable to log
     */
    public static void v(Class<?> clazz, String message, Throwable throwable) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.VERBOSE)) {
            Log.v(tagName, message, throwable);
        }
    }

    /**
     * warning logger
     *
     * @param clazz   the class to log
     * @param message the message to log
     */
    public static void w(Class<?> clazz, String message) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.WARN)) {
            Log.w(tagName, message);
        }
    }

    /**
     * warning logger
     *
     * @param clazz     the class to log
     * @param message   the message to log
     * @param throwable the throwable to log
     */
    public static void w(Class<?> clazz, String message, Throwable throwable) {
        String tagName = getTagName(clazz);

        if (Log.isLoggable(tagName, Log.WARN)) {
            Log.w(tagName, message, throwable);
        }
    }

    /**
     * wtf logger
     *
     * @param clazz   the class to log
     * @param message the message to log
     */
    public static void wtf(Class<?> clazz, String message) {
        String tagName = getTagName(clazz);

        Log.wtf(tagName, message);
    }

    /**
     * wtf logger
     *
     * @param clazz     the class to log
     * @param message   the message to log
     * @param throwable the throwable to log
     */
    public static void wtf(Class<?> clazz, String message, Throwable throwable) {
        String tagName = getTagName(clazz);

        Log.wtf(tagName, message, throwable);
    }
}


