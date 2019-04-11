package com.example.pratik.digitaloutpass;

public class Warden extends User {

    private String hostel;


    public Warden(String id, String name, String role, String email, long phoneNo, String hostel, String uri) {
        super(id, name, role, email, phoneNo, uri);
        this.hostel = hostel;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }
   
    public Warden() {
        this.role = WARDEN;
    }
}
