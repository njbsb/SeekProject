package com.example.seekproject;

public class Appointment {
    private static final Appointment ourInstance = new Appointment();
    private String time, date;
    private boolean done;

    public static Appointment getInstance() {
        return ourInstance;
    }

    private Appointment() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
