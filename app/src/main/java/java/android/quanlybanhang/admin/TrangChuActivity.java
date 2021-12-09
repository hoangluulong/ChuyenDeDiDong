package java.android.quanlybanhang.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.admin.cuahang.CuaHangActivity;

public class TrangChuActivity extends AppCompatActivity {

    private ConstraintLayout btn_chuyenkhoan, btn_don_hang_quang_cao, cuahang,btn_thong_bao_khoa,btn_logout, btn_doi_pass;
    private DatabaseReference mDatabase;
    private TextView tv_soluong_cho_duyet, tv_tong_so_sp_quang_cao;
    private int tongSoSPChoDuyet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
//        String abc="NamABank: TK 508117563600001 nop 2,000,000VND luc 17:54 12/11/2021. So du 2,610,000VND.ND: -MqbkL7-P9xSMPdHPNBc";
//
//        String [] list=abc.split(" ");
//        Toast.makeText(getApplicationContext(),list.length+"",Toast.LENGTH_SHORT).show();






        btn_chuyenkhoan = findViewById(R.id.btn_chuyenkhoan);
        btn_don_hang_quang_cao = findViewById(R.id.btn_don_hang_quang_cao);
        tv_soluong_cho_duyet = findViewById(R.id.tv_soluong_cho_duyet);
        tv_tong_so_sp_quang_cao = findViewById(R.id.tv_tong_so_sp_quang_cao);
        btn_thong_bao_khoa = findViewById(R.id.btn_thong_bao_khoa);
        cuahang = findViewById(R.id.cuahang);
        btn_doi_pass = findViewById(R.id.btn_doi_pass);
        btn_logout = findViewById(R.id.btn_logout);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_chuyenkhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, ThongTinChuyenKhoanActivity.class);
                startActivity(intent);
            }
        });

        btn_don_hang_quang_cao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, SanPhamQuangCaoActivity.class);
                startActivity(intent);
            }
        });

        cuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, CuaHangActivity.class);
                startActivity(intent);
            }
        });

        btn_thong_bao_khoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, ThongBaoKhoaCuaHangActivity.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_doi_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, DoiPasswordActivity.class);
                startActivity(intent);
            }
        });



        getFirebase();
    }

    private void getFirebase () {
        mDatabase.child("ChoXacNhan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("sssss", snapshot.getChildrenCount() + "");
                tv_soluong_cho_duyet.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("sanPhamQuangCao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tongSoSPChoDuyet = 0;
                for (DataSnapshot data: snapshot.getChildren()) {
                    tongSoSPChoDuyet += data.child("sanpham").getChildrenCount();
                }
                tv_tong_so_sp_quang_cao.setText(tongSoSPChoDuyet+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}