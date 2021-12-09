package java.android.quanlybanhang.admin.cuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.admin.Adapter.CuaHangAdapter;
import java.android.quanlybanhang.admin.SanPhamQuangCaoActivity;
import java.android.quanlybanhang.admin.data.CuaHang;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Product;
import java.util.ArrayList;

public class CuaHangActivity extends AppCompatActivity {
    private RecyclerView recycleview;
    private TextView edt_dang_hoat_dong, edt_bi_khoa;
    private DatabaseReference mDatabase;
    private int loai = 1;
    private ArrayList<CuaHang> cuaHangs = new ArrayList<>();
    private CuaHangAdapter cuaHangAdapter;
    private ProgressBar progressBar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang);
        edt_dang_hoat_dong = findViewById(R.id.edt_dang_hoat_dong);
        edt_bi_khoa = findViewById(R.id.edt_bi_khoa);
        recycleview = findViewById(R.id.recycleview);
        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.title);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        cuaHangAdapter = new CuaHangAdapter(this, cuaHangs, loai);
        recycleview.setAdapter(cuaHangAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CuaHangActivity.this, LinearLayoutManager.VERTICAL, false);
        recycleview.setLayoutManager(linearLayoutManager);
        cuaHangAdapter.notifyDataSetChanged();

        getDataCuaHangDangHoatDong();

        edt_dang_hoat_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 1;
                cuaHangAdapter.setLoai(1);
                edt_dang_hoat_dong.setTextColor(ContextCompat.getColor(CuaHangActivity.this, R.color.xanh));
                edt_bi_khoa.setTextColor(ContextCompat.getColor(CuaHangActivity.this, R.color.periwinkle));
                getDataCuaHangDangHoatDong();
            }
        });

        edt_bi_khoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 2;
                cuaHangAdapter.setLoai(2);
                edt_dang_hoat_dong.setTextColor(ContextCompat.getColor(CuaHangActivity.this, R.color.periwinkle));
                edt_bi_khoa.setTextColor(ContextCompat.getColor(CuaHangActivity.this, R.color.xanh));
                getDataCuaHangKhongHoatDong();
            }
        });
    }

    private void getDataCuaHangDangHoatDong() {
        progressBar.setVisibility(View.VISIBLE);
        title.setText("");
        cuaHangs.clear();
        cuaHangAdapter.notifyDataSetChanged();
        mDatabase.child("CuaHangOder").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cuaHangs.clear();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snap: snapshot.getChildren()) {
                        if (snap.child("khoa").getValue() == null) {
                            String key = snap.getKey();
                            String chuCuaHang = snap.child("ChuCuaHang").getValue().toString();
                            String tenCuaHang = snap.child("ThongTinCuaHang").child("tenCuaHang").getValue().toString();
                            String soDienThoai = snap.child("user").child(chuCuaHang).child("phone").getValue().toString();
                            cuaHangs.add(new CuaHang(tenCuaHang, key, soDienThoai));
                            cuaHangAdapter.notifyDataSetChanged();
                            loai = 1;
                            cuaHangAdapter.setLoai(1);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    if (cuaHangs.size() == 0) {
                        title.setText("Không có dữ liệu!");
                        progressBar.setVisibility(View.GONE);
                    }

                }else {
                    title.setText("Không có dữ liệu!");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataCuaHangKhongHoatDong() {
        progressBar.setVisibility(View.VISIBLE);
        title.setText("");
        cuaHangs.clear();
        cuaHangAdapter.notifyDataSetChanged();
        mDatabase.child("CuaHangOder").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snap: snapshot.getChildren()) {
                        if (snap.child("khoa").getValue() != null) {
                            String key = snap.getKey();
                            String chuCuaHang = snap.child("ChuCuaHang").getValue().toString();
                            String tenCuaHang = snap.child("ThongTinCuaHang").child("tenCuaHang").getValue().toString();
                            String soDienThoai = snap.child("user").child(chuCuaHang).child("phone").getValue().toString();
                            cuaHangs.add(new CuaHang(tenCuaHang, key, soDienThoai));
                            cuaHangAdapter.notifyDataSetChanged();
                            cuaHangAdapter.setLoai(2);
                            loai = 2;
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    if (cuaHangs.size() == 0) {
                        title.setText("Không có dữ liệu!");
                        progressBar.setVisibility(View.GONE);
                    }
                }else {
                    title.setText("Không có dữ liệu!");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}