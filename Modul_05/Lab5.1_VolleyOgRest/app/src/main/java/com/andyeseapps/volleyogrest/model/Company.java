package com.andyeseapps.volleyogrest.model;

public class Company {
    private String name;
    private String catchPhrase;
    private String bs;

    public Company() {
        this.name = "nullName";
        this.catchPhrase = "nullCatchPhrase";
        this.bs = "nullBs";
    }

    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }



    /* Getters & Setters */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }
    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }
    public void setBs(String bs) {
        this.bs = bs;
    }
}
