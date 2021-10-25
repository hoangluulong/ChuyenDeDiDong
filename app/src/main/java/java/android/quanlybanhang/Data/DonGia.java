package java.android.quanlybanhang.Data;

public class DonGia {
    String id;
    String TenDonGia;
    Double GiaBan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DonGia() {
    }

    public DonGia(String tenDonGia, Double giaBan) {
        TenDonGia = tenDonGia;
        GiaBan = giaBan;
    }

    public DonGia(String id, String tenDonGia, Double giaBan) {
        this.id = id;
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
