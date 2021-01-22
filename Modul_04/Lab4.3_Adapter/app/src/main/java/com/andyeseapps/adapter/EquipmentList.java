package com.andyeseapps.adapter;

import java.util.ArrayList;
import java.util.Date;

public class EquipmentList {

    private ArrayList<Equipment> arrayList = new ArrayList<>();

    public EquipmentList() {
        Equipment equipment1 = new Equipment("Tablet", "Samsung", "Galaxy Tab S", new Date(), true, "Anders" ,"https://kark.uit.no/~wfa004/d3330/images/galaxytab.jpg");
        Equipment equipment2 = new Equipment("Tablet", "Google", "Nexus", new Date(), true, "Mats", "https://kark.uit.no/~wfa004/d3330/images/nexustab.jpg");
        Equipment equipment3 = new Equipment("Game", "Bungie" ,"Halo 2", new Date(), true, "Saman", "https://kark.uit.no/~wfa004/d3330/images/spill.jpg");

        arrayList.add(equipment1);
        arrayList.add(equipment2);
        arrayList.add(equipment3);
    }


    /* Getters & Setters */
    public ArrayList<Equipment> getArrayList() {
        return arrayList;
    }
    public void setArrayList(ArrayList<Equipment> arrayList) {
        this.arrayList = arrayList;
    }
}
