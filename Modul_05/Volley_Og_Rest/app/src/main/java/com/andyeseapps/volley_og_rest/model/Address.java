package com.andyeseapps.volley_og_rest.model;

public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipCode;
    private Geo geo;

    public Address() {
    }

    public Address(String street, String suite, String city, String zipCode, Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipCode = zipCode;
        this.geo = geo;
    }



    /* Getters & Setters */
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }
    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Geo getGeo() {
        return geo;
    }
    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
