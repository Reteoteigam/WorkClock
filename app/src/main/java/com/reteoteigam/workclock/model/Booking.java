package com.reteoteigam.workclock.model;

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

    public String getDescription() {
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

        String result = String.format("time: %s name: %s content: %n", time, name, content);
        return result;
    }
}
