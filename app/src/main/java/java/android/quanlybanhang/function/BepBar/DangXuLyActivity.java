package java.android.quanlybanhang.function.BepBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BepBar.Adapter.ChiTietDonHangAdapter;
import java.android.quanlybanhang.function.BepBar.Data.Mon;
import java.android.quanlybanhang.function.BepBar.Data.SanPham;
import java.android.quanlybanhang.function.BepBar.Data.SanPhamOder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DangXuLyActivity extends AppCompatActivity {

    private final String ID_QUAN = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private TextView khuvuc, soluong, tongdon, thoigian;
    private Button hoanthanh;
    private RecyclerView recycleview;
    private ChiTietDonHangAdapter chiTietDonHangAdapter;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_xu_ly);
        IDLayout();
        Intent intent = getIntent();
        String ID_BAN = intent.getStringExtra("key");

        getFirebase(ID_BAN);
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(ID_QUAN).child("sanphamorder/"+ID_BAN).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                SanPhamOder sanPhamOder = snapshot.getValue(SanPhamOder.class);
                sanPhamOder.setNameTable(key);
                khuvuc.setText("Bàn "+KhuVucBan(sanPhamOder)[0]+" - Khu vực "+ KhuVucBan(sanPhamOder)[1]);
                soluong.setText(soLuong(sanPhamOder)+"");
                tongdon.setText(TongTien(sanPhamOder)+"");
                thoigian.setText(changeDate(sanPhamOder.getDate()+""));

                recycleview.setLayoutManager(new GridLayoutManager(DangXuLyActivity.this, 1));
                chiTietDonHangAdapter = new ChiTietDonHangAdapter(DangXuLyActivity.this, (ArrayList<Mon>) sanPhamOder.getSanpham());
                recycleview.setAdapter(chiTietDonHangAdapter);

                chiTietDonHangAdapter.notifyDataSetChanged();

                if (sanPhamOder.getTrangThai() == 0) {
                    hoanthanh.setText("Xử lý");
                }else if (sanPhamOder.getTrangThai() == 1){
                    hoanthanh.setText("Hoàn thành");
                }else if (sanPhamOder.getTrangThai() == 2){
                    hoanthanh.setText("Trả món");
                }

                hoanthanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sanPhamOder.getTrangThai() == 0) {
                            mDatabase.child(ID_QUAN).child("sanphamorder/"+ID_BAN).child("trangThai").setValue(1);
                            sanPhamOder.setTrangThai(1);
                            hoanthanh.setText("Hoàn thành");
                        }else if (sanPhamOder.getTrangThai() == 1){
                            mDatabase.child(ID_QUAN).child("sanphamorder/"+ID_BAN).child("trangThai").setValue(2);
                            hoanthanh.setText("Xong");
                            onBackPressed();
                        }else if (sanPhamOder.getTrangThai() == 2){
//                            mDatabase.child("trangThai").setValue(3);
                        }
                    }
                });
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

    private double TongTien(SanPhamOder sanPhamOder) {
        double tien = 0;
        for (int i = 0; i < sanPhamOder.getSanpham().size(); i++){
            tien += (sanPhamOder.getSanpham().get(i).getSoluong() * sanPhamOder.getSanpham().get(i).getGiaProudct());
        }

        return tien;
    }

    private String[] KhuVucBan(SanPhamOder sanPhamOder) {
        String[] parts = sanPhamOder.getNameTable().split("_");
        return  parts;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}