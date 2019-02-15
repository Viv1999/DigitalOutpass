package com.example.pratik.digitaloutpass;

public class User {
    public static String STUDENT = "STUDENT";
    public static String WARDEN = "WARDEN";
    public static String CARETAKER = "CARETAKER";

    String id;
    String name;
    String role;
    String email;
    long phoneNo;

    public User(String id, String name, String role, String email, long phoneNo) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public User() {
    }
}
