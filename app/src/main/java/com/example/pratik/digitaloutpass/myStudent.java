package com.example.pratik.digitaloutpass;

public class myStudent {

    private String name;
    private int enroll;
    private String branch;
    private long phone;
    //private int profPic;

    public myStudent(String name, int enroll, String branch, long phone//, int profPic
                      ) {
        this.name = name;
        this.enroll = enroll;
        this.branch = branch;
        this.phone = phone;
        //this.profPic = profPic;
    }

    public myStudent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnroll() {
        return enroll;
    }

    public void setEnroll(int enroll) {
        this.enroll = enroll;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

//    public int getProfPic() {
//        return profPic;
//    }
//
//    public void setProfPic(int profPic) {
//        this.profPic = profPic;
//    }
}
