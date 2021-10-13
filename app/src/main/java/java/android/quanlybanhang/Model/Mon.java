package com.example.myapplication;

import android.widget.TextView;

import java.util.ArrayList;

public class Mon {
    String tensanpham;

    public Mon(String tensanpham, int soluong) {
        this.tensanpham = tensanpham;
        this.soluong = soluong;
    }
    public Mon()
    {

    }
    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    int soluong;
}
