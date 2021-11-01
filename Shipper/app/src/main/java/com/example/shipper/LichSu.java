package com.example.shipper;

public class LichSu {

    private String diemNhan;
    private String diemGiao;
    private String thoiGianNhan;
    private String thoiGianGiao;
    private String trangThai;


    public LichSu(){}

    public LichSu(String diemNhan,String diemGiao, String thoiGianNhan,String thoiGianGiao,String trangThai){
        this.diemGiao=diemGiao;
        this.diemNhan=diemNhan;
        this.thoiGianNhan=thoiGianNhan;
        this.thoiGianGiao=thoiGianGiao;
        this.trangThai=trangThai;
    }

    public String getDiemNhan() {
        return diemNhan;
    }

    public void setDiemNhan(String diemNhan) {
        this.diemNhan = diemNhan;
    }

    public String getDiemGiao() {
        return diemGiao;
    }

    public void setDiemGiao(String diemGiao) {
        this.diemGiao = diemGiao;
    }

    public String getThoiGianNhan() {
        return thoiGianNhan;
    }

    public void setThoiGianNhan(String thoiGianNhan) {
        this.thoiGianNhan = thoiGianNhan;
    }

    public String getThoiGianGiao() {
        return thoiGianGiao;
    }

    public void setThoiGianGiao(String thoiGianGiao) {
        this.thoiGianGiao = thoiGianGiao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }


}
