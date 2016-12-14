package com.example.ryan.bananafinder;

/**
 * Created by Ryan on 12/7/2016.
 */

public class Banana {
    private int id;
    private int amount;
    private double longitude;
    private double latitude;

    public Banana(){

    }

    public Banana(int id,int amount,double longitude, double latitude)
    {
        this.id = id;
        this.amount = amount;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }
    public int getAmount() {
        return amount;
    }
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}