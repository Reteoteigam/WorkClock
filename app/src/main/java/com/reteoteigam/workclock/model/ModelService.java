package com.reteoteigam.workclock.model;

import com.reteoteigam.workclock.logic.utils.FileService;
import com.reteoteigam.workclock.logic.utils.Logger;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sammy on 28.05.2017.
 */


public class ModelService {


    public static final String DELIMITER = ";";
    private static final String FILE_NAME_STORAGE = "storage.txt";
    private static final String FILE_NAME_EXPORT = "storage.csv";
    private static ArrayList<Booking> bookingList = new ArrayList<>();

    public static void loadModel() {
        File resourceFile = new File(FileService.getExternalDir(), FILE_NAME_STORAGE);
        try {
            FileReader fileReader = new FileReader(resourceFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            loadIn(bookingList, bufferedReader);
            bufferedReader.close();

        } catch (IOException e) {
            Logger.i(ModelService.class, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadIn(ArrayList<Booking> bookingList, BufferedReader bufferedReader) throws IOException {

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            Booking booking = new Booking();
            loadIn(booking, line);
            bookingList.add(booking);
        }


    }

    public static void loadIn(Booking booking, String line) throws IOException {

        String[] values = line.split(DELIMITER);
        if (values.length == 3 && NumberUtils.isNumber(values[0])) {

            booking.setTime(Long.valueOf(values[0]));
            booking.setName(String.valueOf(values[1]));
            String multiLineContent = String.valueOf(values[2]).replaceAll("\\\\\\\\r\\\\\\\\n", "\n");
            booking.setContent(multiLineContent);
        } else {
            Logger.i(ModelService.class, "Booking is invalid: " + line);
        }
    }

    public static void saveModel(Boolean readAble) {
        File targetFile;
        if (readAble) {
            targetFile = new File(FileService.getExternalDir(), FILE_NAME_EXPORT);
        } else {
            targetFile = new File(FileService.getExternalDir(), FILE_NAME_STORAGE);
        }
        try {
            FileWriter fileWriter = new FileWriter(targetFile);
            saveIn(bookingList, fileWriter, readAble);
            fileWriter.close();

        } catch (IOException e) {
            Logger.i(ModelService.class, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveIn(ArrayList<Booking> bookingList, FileWriter fileWriter, Boolean readAble) throws IOException {
        for (Booking booking : bookingList) {
            saveIn(booking, fileWriter, readAble);
            fileWriter.write("\r\n");
        }
    }

    public static void saveIn(Booking booking, FileWriter fileWriter, Boolean readAble) throws IOException {
        String time;
        if (readAble) {
            time = formatTimeToHHmm(booking.getTime());
        } else {
            time = String.valueOf(booking.getTime());
        }
        fileWriter.write(time + DELIMITER);
        fileWriter.write(booking.getName() + DELIMITER);
        String nultilineContent = escapeMultineContent(booking.getContent());
        fileWriter.write(nultilineContent + DELIMITER);
    }

    private static String escapeMultineContent(String content) {
        String result = content.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");
        return result;
    }

    public static void add(Booking booking) {
        bookingList.add(booking);
    }

    public static Booking getLastBooking() {
        Booking result = bookingList.get(bookingList.size() - 1);
        return result;
    }

    public static String formatTimeToHHmm(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String result = simpleDateFormat.format(date);
        return result;
    }
}
