package com.andyeseapps.lab42;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Equipment {
    private String type;
    private String manufacturer;
    private String model;
    private Date boughtDate;
    private boolean loaned;
    private String loanedTo;
    private String pictureURL;

    public Equipment(String type, String manufacturer, String model, Date boughtDate, boolean loaned, String loanedTo, String pictureURL) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.boughtDate = boughtDate;
        this.loaned = loaned;
        this.loanedTo = loanedTo;
        this.pictureURL = pictureURL;
    }


    /**
     * Returns object as a String
     * @return object's values as a String
     */
    @NonNull
    @Override
    public String toString() {
        String boughtDate;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            boughtDate = simpleDateFormat.format(null);
        } catch (Exception ex) {
            Log.d("Equipment", "toString() method", ex);
//            Log.d("ParseError at method " + new Object() {}.getClass().getEnclosingMethod().getName() + "()", ex.toString());
            System.out.println(ex);
            boughtDate = "Could not parse date! (See log)";
        }

        String output = String.format("index: %d" +
                "   - type: %s\n" +
                "   - manufacturer: %s\n" +
                "   - model: %s\n" +
                "   - boughtDate: %s\n" +
                "   - loaned: %s\n" +
                "   - loanedTo: %s\n" +
                "   - pictureURL: %s\n", ScreenSlidePageFragment.counter, getType(), getManufacturer(), getModel(), boughtDate, isLoaned() ? "true" : "false", getLoanedTo(), getPictureURL());
        return output;
    }





    /* Getters & Setters */
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public Date getBoughtDate() {
        return boughtDate;
    }
    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }

    public boolean isLoaned() {
        return loaned;
    }
    public void setLoaned(boolean loaned) {
        this.loaned = loaned;
    }

    public String getLoanedTo() {
        return loanedTo;
    }
    public void setLoanedTo(String loanedTo) {
        this.loanedTo = loanedTo;
    }

    public String getPictureURL() {
        return pictureURL;
    }
    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}