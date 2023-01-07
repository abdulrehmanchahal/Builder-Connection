package com.example.builderconnection;

public class Dataholder {
    String name, pass, phone_no, CNIC_no, pimage;

    public Dataholder(String name, String pass, String phome_no, String CNIC_no, String pimage) {
        this.name = name;
        this.pass = pass;
        this.phone_no = phome_no;
        this.CNIC_no = CNIC_no;
        this.pimage = pimage;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhome_no() {
        return phone_no;
    }

    public void setPhome_no(String phome_no) {
        this.phone_no = phome_no;
    }

    public String getCNIC_no() {
        return CNIC_no;
    }

    public void setCNIC_no(String CNIC_no) {
        this.CNIC_no = CNIC_no;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}