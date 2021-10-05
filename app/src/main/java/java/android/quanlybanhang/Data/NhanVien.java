package java.android.quanlybanhang.Data;

import java.util.ArrayList;

public class NhanVien {
    String username;
    String email;
    ArrayList<Boolean> chucVu;
    CaLam caLam;
    String phone;
    String id;


    public NhanVien() {
    }

    public NhanVien(String username, String email, String phone, String id) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.id = id;
    }

    public NhanVien(String username, String email, ArrayList<Boolean> chucVu, CaLam caLam, String phone, String id) {
        this.username = username;
        this.email = email;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
        this.id = id;
    }

    public ArrayList<Boolean> getChucVu() {
        return chucVu;
    }

    public void setChucVu(ArrayList<Boolean> chucVu) {
        this.chucVu = chucVu;
    }

    public CaLam getCaLam() {
        return caLam;
    }

    public void setCaLam(CaLam caLam) {
        this.caLam = caLam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
