package com.example.weatherreport;

public class WeekWeatherPOJO {
    private int id;
    private double tmax;
    private double tmin;
    private long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTmax() {
        return tmax;
    }

    public void setTmax(double tmax) {
        this.tmax = tmax;
    }

    public double getTmin() {
        return tmin;
    }

    public void setTmin(double tmin) {
        this.tmin = tmin;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
