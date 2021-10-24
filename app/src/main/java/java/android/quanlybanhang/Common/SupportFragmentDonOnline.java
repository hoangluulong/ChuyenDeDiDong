package java.android.quanlybanhang.Common;

import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.android.quanlybanhang.function.DonHangOnline.data.SanPham;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SupportFragmentDonOnline {
    public SupportFragmentDonOnline() {}

    public String formartDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dt = formatter.format(date);
        return dt;
    }

    public String ngayHientai(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dt = formatter.format(date);
        return dt;
    }

    public Double TinhTongTien(ArrayList<SanPham> sanPhams) {
        double tongGia = 0;
        for (int i = 0; i < sanPhams.size(); i++) {
            tongGia += sanPhams.get(i).getGiaBan();
        }
        return tongGia;
    }

    public Date formatDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.sss yyyy-MM-dd");

        try {
            Date date = simpleDateFormat.parse(strDate);
            return date;
        } catch (Exception e) {
            Date date = new Date();
            return date;
        }
    }

    public void SapXepDate(ArrayList<DonHang> donHangs) {
        Collections.sort(donHangs, new Comparator<DonHang>() {
            public int compare(DonHang o1, DonHang o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }

}
