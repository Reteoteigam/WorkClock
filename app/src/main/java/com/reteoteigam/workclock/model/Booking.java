package com.reteoteigam.workclock.model;

import java.text.SimpleDateFormat;

/**
 * Created by Sammy on 30.05.2017.
 */

public class Booking {


    private String name;
    private String content;
    private long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");


        String result = time + ModelService.DELIMITER + name + ModelService.DELIMITER + content.replaceAll("\n", "\\\\\\\\n");
        return result;
    }
}
