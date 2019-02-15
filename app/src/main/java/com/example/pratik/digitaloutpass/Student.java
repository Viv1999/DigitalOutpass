package com.example.pratik.digitaloutpass;

public class Student extends User{
    private static int curRegId=0;
    final int regId;
    int enrollNo;
    //String name;
    String batch;
    String branch;
    //String email;
    public Student(){
        this.regId = curRegId++;
    }
    public Student(int enrollNo, String name, String batch, String branch, String email){
        this.enrollNo = enrollNo;
        this.name = name;
        this.batch = batch;
        this.branch = branch;
        this.email = email;
        this.regId = curRegId++;
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
}
