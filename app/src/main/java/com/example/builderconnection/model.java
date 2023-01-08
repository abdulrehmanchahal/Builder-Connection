package com.example.builderconnection;

public class model {

    String name, pass, phome_no, pimage;

    public model(){

    }

    public model(String name, String pass, String phome_no, String pimage) {
        this.name = name;
        this.pass = pass;
        this.phome_no = phome_no;
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
        return phome_no;
    }

    public void setPhome_no(String phome_no) {
        this.phome_no = phome_no;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
