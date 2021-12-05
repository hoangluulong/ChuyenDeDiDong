package java.android.quanlybanhang.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.HelperClasses.LichSuHoatDongAdapte;
import java.android.quanlybanhang.Model.LichSuHoatDong;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.util.ArrayList;

public class LichSuHoatDongActivity extends AppCompatActivity {

    private LichSuHoatDongAdapte lichSuHoatDongAdapte;
    private ArrayList<LichSuHoatDong> list = new ArrayList<>();
    private ArrayList<LichSuHoatDong> listTheoNgay = new ArrayList<>();
    private RecyclerView recyclerview;
    private String ID_CUAHANG;
    private String ngayChon = "";

    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private boolean all = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_hoat_dong);
        recyclerview = findViewById(R.id.recyclerview);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        firebaseDatabase =  FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("CuaHangOder/"+ID_CUAHANG).child("lichSuHoatDong");

        lichSuHoatDongAdapte = new LichSuHoatDongAdapte(list);
        recyclerview.setAdapter(lichSuHoatDongAdapte);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LichSuHoatDongActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        lichSuHoatDongAdapte.notifyDataSetChanged();
        getData();

    }

    private void getData () {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    String key = snap.getKey();
                    for (DataSnapshot s: snap.getChildren()) {
                        LichSuHoatDong lichSuHoatDong = s.getValue(LichSuHoatDong.class);
                        lichSuHoatDong.setKey(key);
                        if (all) {
                            list.add(lichSuHoatDong);
                            lichSuHoatDongAdapte.setData(list);
                        }else {
                            if (lichSuHoatDong.getKey().equals(ngayChon)) {
                                listTheoNgay.add(lichSuHoatDong);
                                lichSuHoatDongAdapte.setData(listTheoNgay);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}