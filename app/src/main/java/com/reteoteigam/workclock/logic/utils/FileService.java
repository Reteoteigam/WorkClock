package com.reteoteigam.workclock.logic.utils;

import java.io.File;
import java.io.IOException;


/**
 * Created by Sammy on 29.05.2017.
 */

public class FileService {

    private static File externalDirectory;
    private static boolean isInitialized = false;

    public static void init(File externalDir) {
        if (!isInitialized) {
            externalDirectory = externalDir;
            if (!externalDir.isDirectory()) {
                boolean success = externalDir.mkdirs();
                Logger.i(FileService.class, "create externalDir success " + success);
            }
            isInitialized = true;
        } else {
            Logger.i(FileService.class, "Was initialized before, nothing changed");
        }
        Logger.i(FileService.class, String.format("externalDir:[%s]", externalDirectory));

    }

    public static File getExternalDir() {
        return externalDirectory;
    }

    public static File createFile(String fileName) {
        File result = new File(getExternalDir(), fileName);
        if (!result.exists()) {
            try {
                result.createNewFile();
            } catch (IOException e) {
                Logger.e(FileService.class, String.format("Creating a file:[%s] was not successful", fileName), e);
            }
        }
        return result;
    }
}
