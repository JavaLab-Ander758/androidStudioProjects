package com.andyeseapps.volleyogrest.model;

public class Geo {
    private double lat;
    private double lng;

    public Geo() {
        this.lat = -1;
        this.lng = -1;
    }

    public Geo(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }



    /* Getters & Setters */
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
}
