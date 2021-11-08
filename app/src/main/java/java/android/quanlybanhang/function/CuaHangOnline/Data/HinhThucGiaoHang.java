package java.android.quanlybanhang.function.CuaHangOnline.Data;

public class HinhThucGiaoHang {
    private boolean khacHangDenLay;
    private boolean quaDoiTac;
    private boolean tuGiao;
    private boolean tienMat;
    private boolean chuyenKhoan;
    private String thongTinChuyenKhoan;
    private String ghiChu;

    public HinhThucGiaoHang() {
    }

    public HinhThucGiaoHang(boolean khacHangDenLay, boolean quaDoiTac, boolean tuGiao, boolean tienMat, boolean chuyenKhoan, String thongTinChuyenKhoan, String ghiChu) {
        this.khacHangDenLay = khacHangDenLay;
        this.quaDoiTac = quaDoiTac;
        this.tuGiao = tuGiao;
        this.tienMat = tienMat;
        this.chuyenKhoan = chuyenKhoan;
        this.thongTinChuyenKhoan = thongTinChuyenKhoan;
        this.ghiChu = ghiChu;
    }

    public boolean isKhacHangDenLay() {
        return khacHangDenLay;
    }

    public void setKhacHangDenLay(boolean khacHangDenLay) {
        this.khacHangDenLay = khacHangDenLay;
    }

    public boolean isQuaDoiTac() {
        return quaDoiTac;
    }

    public void setQuaDoiTac(boolean quaDoiTac) {
        this.quaDoiTac = quaDoiTac;
    }

    public boolean isTuGiao() {
        return tuGiao;
    }

    public void setTuGiao(boolean tuGiao) {
        this.tuGiao = tuGiao;
    }

    public boolean isTienMat() {
        return tienMat;
    }

    public void setTienMat(boolean tienMat) {
        this.tienMat = tienMat;
    }

    public boolean isChuyenKhoan() {
        return chuyenKhoan;
    }

    public void setChuyenKhoan(boolean chuyenKhoan) {
        this.chuyenKhoan = chuyenKhoan;
    }

    public String getThongTinChuyenKhoan() {
        return thongTinChuyenKhoan;
    }

    public void setThongTinChuyenKhoan(String thongTinChuyenKhoan) {
        this.thongTinChuyenKhoan = thongTinChuyenKhoan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
