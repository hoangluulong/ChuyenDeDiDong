package java.android.quanlybanhang.OrderMon;

public class DonGia {
    String TenDonGia;
    Double GiaBan;

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
