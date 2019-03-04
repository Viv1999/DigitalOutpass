package com.example.pratik.digitaloutpass;

import java.util.ArrayList;

public class Student extends User{
    private static int curRegId=0;
    final int regId;
    int enrollNo;
    //String name;
    String batch;
    String branch;
    String hostel;
    //String email;
    ArrayList<String> myOutpasses;
    public Student(){
        this.regId = curRegId++;
    }

    public Student(String id, String name, String role, String email, long phoneNo, int enrollNo, String batch, String branch, String hostel) {
        super(id, name, role, email, phoneNo);
        this.regId = curRegId++;
        this.enrollNo = enrollNo;
        this.batch = batch;
        this.branch = branch;
        this.hostel = hostel;
    }

    public int getEnrollNo() {
        return enrollNo;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setEnrollNo(int enrollNo) {
        this.enrollNo = enrollNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }
}
