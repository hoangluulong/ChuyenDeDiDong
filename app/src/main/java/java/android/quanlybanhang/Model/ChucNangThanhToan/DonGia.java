package java.android.quanlybanhang.Model.ChucNangThanhToan;

import java.io.Serializable;

public class DonGia implements Serializable {
    String TenDonGia;
    Double GiaBan;
    String TenLoaiChung;
    String id;

    public String getTenLoaiChung() {
        return TenLoaiChung;
    }

    public void setTenLoaiChung(String tenLoaiChung) {
        TenLoaiChung = tenLoaiChung;
    }



    Boolean Check = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    Double giachung=222222220.0;
    public Boolean getCheck() {
        return Check;
    }

    public DonGia(String tenDonGia, Double giaBan, String id) {
        TenDonGia = tenDonGia;
        GiaBan = giaBan;
        this.id = id;
    }

    public Double getGiachung() {
        return giachung;
    }

    public void setGiachung(Double giachung) {
        this.giachung = giachung;
    }

    public void setCheck(Boolean check) {
        Check = check;
    }

    public DonGia() {
    }

    public DonGia(String tenDonGia, Double giaBan) {
        TenDonGia = tenDonGia;
        GiaBan = giaBan;
    }

    public String getTenDonGia() {
        return TenDonGia;
    }

    public void setTenDonGia(String tenDonGia) {
        TenDonGia = tenDonGia;
    }

    public Double getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(Double giaBan) {
        GiaBan = giaBan;
    }
}
