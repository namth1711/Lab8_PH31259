package com.namth.lab8_ph31259;

import java.io.Serializable;

public class user implements Serializable {
    private String name,pass;

    public user() {
    }

    public user(String name, String pass) {
        this.name = name;
        this.pass = pass;
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
}
