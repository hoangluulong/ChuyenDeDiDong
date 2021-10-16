package java.android.quanlybanhang.OrderMon;

import java.android.quanlybanhang.PushToFire;
import java.util.ArrayList;

public class PushToFire1 {
    public PushToFire1(long date, boolean flag) {
        this.date = date;
        this.flag = flag;
    }

    private long date;
    private boolean flag;
    private ArrayList<PushToFire> sanpham;

    public PushToFire1(long date, boolean flag, ArrayList<PushToFire> sanpham) {
        this.date = date;
        this.flag = flag;
        this.sanpham = sanpham;
    }

    public ArrayList<PushToFire> getSanpham() {
        return sanpham;
    }

    public void setSanpham(ArrayList<PushToFire> sanpham) {
        this.sanpham = sanpham;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
