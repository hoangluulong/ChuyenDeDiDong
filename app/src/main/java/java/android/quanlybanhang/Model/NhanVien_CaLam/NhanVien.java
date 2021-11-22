package java.android.quanlybanhang.Model.NhanVien_CaLam;

import java.io.Serializable;
import java.util.ArrayList;

public class NhanVien implements Serializable {
    String username;
    String email;
    ArrayList<Boolean> chucVu;
    CaLam caLam;
    String phone;
    String id;
    String avata;
    String nameAvata;


    public NhanVien() {
    }


    public NhanVien(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public NhanVien(String username, ArrayList<Boolean> chucVu, CaLam caLam, String phone) {
        this.username = username;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
    }

    public NhanVien(String username, String email, ArrayList<Boolean> chucVu, CaLam caLam, String phone, String id) {
        this.username = username;
        this.email = email;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
        this.id = id;
    }

    public NhanVien(String username, String email, ArrayList<Boolean> chucVu, CaLam caLam, String phone, String id, String avata) {
        this.username = username;
        this.email = email;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
        this.id = id;
        this.avata = avata;
    }

    public NhanVien(String username, String email, ArrayList<Boolean> chucVu, CaLam caLam, String phone, String id, String avata, String nameAvata) {
        this.username = username;
        this.email = email;
        this.chucVu = chucVu;
        this.caLam = caLam;
        this.phone = phone;
        this.id = id;
        this.avata = avata;
        this.nameAvata = nameAvata;
    }

    public String getNameAvata() {
        return nameAvata;
    }

    public void setNameAvata(String nameAvata) {
        this.nameAvata = nameAvata;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
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
