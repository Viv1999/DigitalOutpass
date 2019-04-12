package com.example.pratik.digitaloutpass;

import com.example.pratik.digitaloutpass.User;

public class Caretaker extends User {
    private String hostel;

    public Caretaker(String id, String name, String role, String email, long phoneNo, String uri, String hostel) {
        super(id, name, role, email, phoneNo, uri);
        this.hostel = hostel;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }
}
