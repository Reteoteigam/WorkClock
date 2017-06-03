package com.reteoteigam.workclock.model;

import com.reteoteigam.workclock.logic.utils.Logger;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Sammy on 28.05.2017.
 */


public class ModelService {

    public static final String DELIMITER = ";";


    private static Stack<Booking> model = new Stack<>();

    public static void init(File storage) {
        boolean correct = ModelService.readValidModel(storage, model);
        Logger.i(ModelService.class, String.format("Initial reading of model from File was correct:[%s]", correct));

    }

    public static boolean readValidModel(File source, Stack<Booking> target) {
        boolean isValid = target != null && source != null && source.exists();
        if (isValid) {
            try {
                FileReader fileReader = new FileReader(source);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                readValidModel(bufferedReader, target);
                bufferedReader.close();

            } catch (IOException e) {
                Logger.i(ModelService.class, e.getMessage());
                e.printStackTrace();
            }
            target.trimToSize();
        }
        return isValid;
    }

    public static boolean readValidModel(BufferedReader source, Stack<Booking> target) throws IOException {

        boolean isValid = false;

        while (source.ready()) {
            String line = source.readLine();

            Booking booking = new Booking();
            boolean wasValid = readValidModel(line, booking);
            if (wasValid) {
                target.add(booking);
            }
            isValid = isValid && wasValid;
        }

        return isValid;
    }

    public static boolean readValidModel(String source, Booking target) {
        boolean isValid = target != null && source != null;
        if (isValid) {
            String[] values = source.split(DELIMITER);
            isValid = isValid && values.length == 3 && NumberUtils.isNumber(values[0]);
            if (isValid) {
                Long time = Long.valueOf(values[0]);
                isValid = time >= 0;
                target.setTime(time);
                String name = String.valueOf(values[1]);
                isValid = isValid && name.length() >= 1;
                target.setName(String.valueOf(values[1]));
                String multiLineContent = String.valueOf(values[2]).replaceAll("\\\\n", "\n");
                isValid = isValid && multiLineContent.length() >= 1;
                target.setContent(multiLineContent);
            }
        }
        return isValid;
    }

    public static void writeModel(Stack<Booking> bookingList, File target, Boolean readAble) {


        try {
            FileWriter fileWriter = new FileWriter(target);
            writeModel(bookingList, fileWriter, readAble);
            fileWriter.close();

        } catch (IOException e) {
            Logger.i(ModelService.class, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void writeModel(Stack<Booking> bookingList, FileWriter target, Boolean readAble) throws IOException {

        for (Booking booking : bookingList) {
            String line = lineFromModel(booking, readAble);
            target.write(line);
        }
    }

    public static String lineFromModel(Booking booking, Boolean readAble) {
        String result;

        String time;
        if (readAble) {
            time = formatTimeToHHmm(booking.getTime());
        } else {
            time = String.valueOf(booking.getTime());
        }
        result = time + DELIMITER;
        result = result + booking.getName() + DELIMITER;
        String multilineContent = escapeNewLines(booking.getDescription());
        result = result + multilineContent;
        result = result + "\n";
        return result;
    }

    private static String escapeNewLines(String content) {
        String result = content.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");
        return result;
    }


    public static String formatTimeToHHmm(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String result = simpleDateFormat.format(date);
        return result;
    }

    public static Booking[] getLastEntries(Stack<Booking> bookings, int count) {
        Booking[] last5;
        if (bookings == null || count <= 1) {
            last5 = new Booking[0];
        } else {
            int indexBookings = bookings.size() - 1;
            int begin = indexBookings - count;
            if (begin < 0) {
                begin = 0;
                last5 = new Booking[indexBookings + 1];
            } else {
                last5 = new Booking[count];
            }
            int indexArray = 0;
            while (indexArray < count && begin <= indexBookings) {
                Booking last = bookings.get(indexBookings);
                last5[indexArray] = last;
                indexBookings--;
                indexArray++;
            }
        }

        return last5;
    }

    public static Stack<Booking> getModel() {
        return model;
    }
}

