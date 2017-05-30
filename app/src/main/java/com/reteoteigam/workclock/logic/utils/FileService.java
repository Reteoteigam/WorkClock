package com.reteoteigam.workclock.logic.utils;

import java.io.File;

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
            Logger.i(FileService.class, "was initialized before");
        }
        Logger.i(FileService.class, "externalDir=" + externalDir);

    }

    public static File getExternalDir() {
        return externalDirectory;
    }
}
