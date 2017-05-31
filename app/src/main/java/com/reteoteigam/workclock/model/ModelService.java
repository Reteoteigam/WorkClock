package com.reteoteigam.workclock.model;

import com.reteoteigam.workclock.logic.utils.FileService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sammy on 28.05.2017.
 */


public class ModelService {


    public static final String DELIMITER = ";";
    //private static final String PROPERTIES_FILENAME = Resources.getSystem().getString(R.string.properties_file);
    private static final String PROPERTIES_FILENAME = "knownNames.txt";
    private static ArrayList<Booking> bookingList = new ArrayList<>();

    /**
     * @return a properties based on the content of the file which is under the given name
     */
    public static void loadModel() {

        bookingList = new ArrayList<>();


        File resultFile = new File(FileService.getExternalDir(), PROPERTIES_FILENAME);
        if (resultFile.exists()) {
            List<String> loadedData = null;
            try {
                loadedData = FileUtils.readLines(resultFile, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }


            for (String line : loadedData) {
                Booking currentBooking = new Booking();


                String name = StringUtils.substringBetween(line, DELIMITER, DELIMITER);
                String content = StringUtils.substringAfterLast(line, DELIMITER);
                long time = Long.valueOf(StringUtils.substringBefore(line, DELIMITER));


                currentBooking.setContent(content);
                currentBooking.setName(name);
                currentBooking.setTime(time);

                bookingList.add(currentBooking);
            }
        }
    }

    public static void saveModel() {
        File targetFile = new File(FileService.getExternalDir(), PROPERTIES_FILENAME);
        List<? extends Booking> bookingCollection = bookingList;
        try {

            FileUtils.writeLines(targetFile, "UTF-8", bookingCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static String getPrintFrom(Booking currentBooking) {
        String result = ModelService.formatTimeToHHmm(currentBooking.getTime()) +
                ModelService.DELIMITER +
                currentBooking.getName() +
                ModelService.DELIMITER +
                "\n" +
                currentBooking.getContent();
        return result;
    }
}
