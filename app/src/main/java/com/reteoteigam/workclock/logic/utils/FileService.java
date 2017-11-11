package com.reteoteigam.workclock.logic.utils;

import java.io.File;
import java.io.IOException;

public class FileService {

    private static File externalDirectory;
    private static boolean isInitialized = false;

    public static void init(File externalDir) {
        if (!isInitialized) {
            externalDirectory = externalDir;
            if (!externalDir.isDirectory()) {
                boolean success = externalDir.mkdirs();
                Logger.i(FileService.class, String.format("create externalDir success %s", success));
            }
            isInitialized = true;
        } else {
            Logger.i(FileService.class, "Was initialized before, nothing changed");
        }
        Logger.i(FileService.class, String.format("externalDir:[%s]", externalDirectory));

    }


    public static File createDirectory(String dirName) {
        File result = new File(externalDirectory, dirName);

        if (!result.isDirectory()) {
            boolean success = result.mkdirs();
            Logger.i(FileService.class, String.format("create %s success %s", dirName, success));
        }
        return result;
    }

    public static File createFile(String fileName) {
        File result = new File(externalDirectory, fileName);
        if (!result.exists()) {
            try {
                boolean success = result.createNewFile();
                Logger.i(FileService.class, String.format("create %s exists before:", result));
            } catch (IOException e) {
                Logger.e(FileService.class, String.format("Creating a file:[%s] was not successful", fileName), e);
            }
        }
        return result;
    }


}
