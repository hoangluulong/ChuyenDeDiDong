package com.example.myapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Table {
    private String  nameTable;
    private List<Mon> danhSachMon;
    private long date;
    private int trangThai;

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public List<Mon> getDanhSachMon() {
        return danhSachMon;
    }

    public void setDanhSachMon(List<Mon> danhSachMon) {
        this.danhSachMon = danhSachMon;
    }



    public Table(  long date) {
        this.date = date;
    }

    public Table() {

    }
    public Table(int trangThai){
        this.trangThai=trangThai;
    }

    public Table(String nameTable, List<Mon> danhSachMon, long date) {
        this.nameTable = nameTable;
        this.danhSachMon = danhSachMon;
        this.date = date;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }



    public void setDate(long date) {
        this.date = date;
    }

    public String getNameTable() {
        return nameTable;
    }


    public long getDate() {
        return date;
    }


}
