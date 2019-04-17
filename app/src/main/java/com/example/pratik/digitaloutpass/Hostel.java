package com.example.pratik.digitaloutpass;


import java.util.ArrayList;

public class Hostel {
    public static final String RAMAN_HOUSE = "Raman House";
    public static final String BHABHA_HOUSE = "Bhabha House";
    public static final String BOSE_HOUSE = "Bose House";
    String name;
    static ArrayList<String> ramanHouseList = new ArrayList<String>();
    static ArrayList<String> bhabhaHouseList = new ArrayList<String>();
    static ArrayList<String> boseHouseList = new ArrayList<String>();
    ArrayList<String> members = new ArrayList<>();
    ArrayList<String> outpasses = new ArrayList<>();
    public Hostel(ArrayList<String> ramanHouseList, ArrayList<String> bhabhaHouseList, ArrayList<String> boseHouseList) {
        this.ramanHouseList = ramanHouseList;
        this.bhabhaHouseList = bhabhaHouseList;
        this.boseHouseList = boseHouseList;
    }

    public Hostel(String name){
        this.name = name;
    }

    public Hostel() {
    }

    public static ArrayList<String> getRamanHouseList() {
        return ramanHouseList;
    }

    public static void setRamanHouseList(ArrayList<String> ramanHouseList) {
        Hostel.ramanHouseList = ramanHouseList;
    }

    public static ArrayList<String> getBhabhaHouseList() {
        return bhabhaHouseList;
    }

    public static void setBhabhaHouseList(ArrayList<String> bhabhaHouseList) {
        Hostel.bhabhaHouseList = bhabhaHouseList;
    }

    public static ArrayList<String> getBoseHouseList() {
        return boseHouseList;
    }

    public static void setBoseHouseList(ArrayList<String> boseHouseList) {
        Hostel.boseHouseList = boseHouseList;
    }

}
