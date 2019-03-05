package com.example.pratik.digitaloutpass;

public class Warden extends User {
    private String hostel;
    public Warden() {
        this.role = WARDEN;
    }

    public Warden(String id, String name, String role, String email, long phoneNo, String hostel) {
        super(id, name, role, email, phoneNo);
        this.hostel = hostel;
    }

    public String getHostel() {
        return hostel;
    }
}
