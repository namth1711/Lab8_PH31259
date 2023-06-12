package com.namth.lab8_ph31259;

public class School {
    private int hinh;
    private String ten;

    public School(int hinh, String ten) {
        this.hinh = hinh;
        this.ten = ten;
    }

    public School() {
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return ten;
    }
}
