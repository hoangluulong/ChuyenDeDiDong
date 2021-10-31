package java.android.quanlybanhang.function.BepBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.Common.SupportFragmentDonOnline;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BepBar.Adapter.ChiTietDonHangOnlineAdapter;
import java.android.quanlybanhang.function.BepBar.Data.DonHang;
import java.android.quanlybanhang.function.BepBar.Data.SanPham;
import java.android.quanlybanhang.function.BepBar.Data.SanPhamOder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DangXuLyOnlineActivity extends AppCompatActivity {

    private final String ID_QUAN = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private TextView khuvuc, soluong, tongdon, thoigian;
    private Button hoanthanh;
    private RecyclerView recycleview;
    private ChiTietDonHangOnlineAdapter chiTietDonHangAdapter;
    private String KEY_NGAY, ID_KEY;
    private FormatDouble formatDouble;
    private SupportFragmentDonOnline support = new SupportFragmentDonOnline();

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_xu_ly);
        IDLayout();
        Intent intent = getIntent();
        ID_KEY = intent.getStringExtra("key");
        KEY_NGAY = intent.getStringExtra("key_ngay");

        getFirebase(ID_KEY);
    }

    private void IDLayout() {
        khuvuc = findViewById(R.id.khuvuc);
        soluong = findViewById(R.id.soluong);
        tongdon = findViewById(R.id.tongdon);
        thoigian = findViewById(R.id.thoigian);
        hoanthanh = findViewById(R.id.hoanthanh);
        recycleview = findViewById(R.id.recycleview);
    }

    private void getFirebase(String ID_BAN) {
        Log.d("ZZ", "bb");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonHang donHang = snapshot.getValue(DonHang.class);
                Date date = formatDate(donHang.getTime());
                donHang.setDate(date);

                if (donHang.getTrangthai() == 2) {
                    hoanthanh.setText("Xử lí");
                }else if (donHang.getTrangthai() == 3) {
                    hoanthanh.setText("Hoàn Thành");
                }else if (donHang.getTrangthai() == 4) {
                    hoanthanh.setText("Trả đơn");
                }

                khuvuc.setText("ID " + donHang.getKey());
                double tong = TinhTongTien(donHang.getSanpham());
                ArrayList<SanPham> sanPhams = donHang.getSanpham();
//                soluong.setText(+ "");
                tongdon.setText((tong - donHang.getGiaKhuyenMai())+"");
                thoigian.setText(changeDate(ID_KEY) + "");

                hoanthanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (donHang.getTrangthai() == 2) {
                            mDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY+"/trangthai").setValue(3);
                            hoanthanh.setText("Hoàn Thành");
                            donHang.setTrangthai(3);
                        }else if (donHang.getTrangthai() == 3) {
                            mDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY+"/trangthai").setValue(4);
                            hoanthanh.setText("Trả đơn");
                            donHang.setTrangthai(4);
                            onBackPressed();
                        }else if (donHang.getTrangthai() == 4) {
                            mDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+KEY_NGAY+"/"+ID_KEY+"/trangthai").setValue(5);
                        }
                    }
                });

                recycleview.setLayoutManager(new GridLayoutManager(DangXuLyOnlineActivity.this, 1));
                chiTietDonHangAdapter = new ChiTietDonHangOnlineAdapter(DangXuLyOnlineActivity.this, sanPhams);
                recycleview.setAdapter(chiTietDonHangAdapter);

                chiTietDonHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String changeDate(String date){
        long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if(dates ==0){
            return "";
        }
        Date date1 =new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        return  aaa;
    }

    private int soLuong(SanPhamOder sanPhamOder) {
        int soLuong = 0;
        double tien = 0;
        for (int i = 0; i < sanPhamOder.getSanpham().size(); i++){
            soLuong += sanPhamOder.getSanpham().get(i).getSoluong();
        }

        return soLuong;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Date formatDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.sss yyyy-MM-dd");

        try {
            Date date = simpleDateFormat.parse(strDate);
            return date;
        } catch (Exception e) {
            Date date = new Date();
            return date;
        }
    }
    private Double TinhTongTien(ArrayList<SanPham> sanPhams) {
        double tongGia = 0;
        for (int i = 0; i < sanPhams.size(); i++) {
            tongGia += sanPhams.get(i).getGiaBan();
        }
        return tongGia;
    }
}