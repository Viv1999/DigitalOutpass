package com.example.pratik.digitaloutpass;

import java.util.ArrayList;

public class User {
    public static String STUDENT = "STUDENT";
    public static String WARDEN = "WARDEN";
    public static String CARETAKER = "CARETAKER";

    String id;
    String name;
    String role;
    String email;
    long phoneNo;
    boolean isEmailVerified = false;
    String uri;
    String token;
    ArrayList<String> subscriptions;

    public User(String id, String name, String role, String email, long phoneNo, String uri) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.phoneNo = phoneNo;
        this.uri = uri;
    }

    public User() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public static String getSTUDENT() {
        return STUDENT;
    }

    public static void setSTUDENT(String STUDENT) {
        User.STUDENT = STUDENT;
    }

    public static String getWARDEN() {
        return WARDEN;
    }

    public static void setWARDEN(String WARDEN) {
        User.WARDEN = WARDEN;
    }

    public static String getCARETAKER() {
        return CARETAKER;
    }

    public static void setCARETAKER(String CARETAKER) {
        User.CARETAKER = CARETAKER;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }
}
