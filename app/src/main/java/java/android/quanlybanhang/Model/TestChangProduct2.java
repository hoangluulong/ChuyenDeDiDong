package com.example.myapplication;

import java.util.List;

public class TestChangProduct2 {

    private boolean flag;
    private List<Mon> sanpham;


    public TestChangProduct2(boolean flag, List<Mon> testSP) {
        this.flag = flag;
        this.sanpham = testSP;
    }

    public TestChangProduct2() {
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Mon> getSanpham() {
        return sanpham;
    }

    public void setSanpham(List<Mon> sanpham) {
        this.sanpham = sanpham;
    }
}
