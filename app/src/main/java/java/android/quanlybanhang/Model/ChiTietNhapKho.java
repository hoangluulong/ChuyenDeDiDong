package java.android.quanlybanhang.Model;

import java.io.Serializable;

public class ChiTietNhapKho implements Serializable {
    String tenSanPham;
    String soLieuCu;

    public ChiTietNhapKho() {
    }

    public ChiTietNhapKho(String tenSanPham, String soLieuCu, String soLieuMoi) {
        this.tenSanPham = tenSanPham;
        this.soLieuCu = soLieuCu;
        this.soLieuMoi = soLieuMoi;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getSoLieuCu() {
        return soLieuCu;
    }

    public void setSoLieuCu(String soLieuCu) {
        this.soLieuCu = soLieuCu;
    }

    public String getSoLieuMoi() {
        return soLieuMoi;
    }

    public void setSoLieuMoi(String soLieuMoi) {
        this.soLieuMoi = soLieuMoi;
    }

    String soLieuMoi;
}
