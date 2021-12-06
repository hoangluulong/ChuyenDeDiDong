package java.android.quanlybanhang.function.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Common.SupportSaveLichSu;
import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Model.KhachHang.NhomKhachHang;
import java.android.quanlybanhang.R;

public class SuaNhomKhachHang  extends AppCompatActivity {
    private EditText editTenNhom,editMa,editGhiChu;
    private Button btnTao,btnHuy;
    private String STR_CUAHANG = "CuaHangOder";
    private  String STR_NKH = "nhomkhachhang";
    private NhomKhachHang nhomKhachHang;
    private DatabaseReference mDatabase;
    private String ID_CUAHANG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitynhomkhachhang);
        editTenNhom = findViewById(R.id.edtTenNhomKhachHang);
        editMa = findViewById(R.id.edtMaKhachHang);
        editGhiChu = findViewById(R.id.edtGhiChuNhomKhachHang);
        btnHuy = findViewById(R.id.btnhuyTaoNhomKhachhang);
        btnTao = findViewById(R.id.btnTaoNhomKhachhang);

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        nhomKhachHang = (NhomKhachHang) getIntent().getSerializableExtra("Key_arrNKH");
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_NKH);

        editTenNhom.setText(nhomKhachHang.getTenNhomKh());
        editMa.setText(nhomKhachHang.getMaKH());
        editGhiChu.setText(nhomKhachHang.getGhichuNhom());
        updateNKH();

    }

    public void updateNKH(){
        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTenNhom.getText().toString().isEmpty()){
                    editTenNhom.setError("Hãy nhập tên nhóm khách hàng");
                    editTenNhom.requestFocus();
                }
                else if(editMa.getText().toString().isEmpty()){
                    editMa.setError("Hãy nhập mã nhóm khách hàng");
                }else {
                    String id = mDatabase.push().getKey();
                    String name = editTenNhom.getText().toString();
                    String ma = editMa.getText().toString();
                    String ghichu = editGhiChu.getText().toString();
                    mDatabase.child(nhomKhachHang.getId()).child("tenNhomKH").setValue(name);
                    mDatabase.child(nhomKhachHang.getId()).child("maKH").setValue(ma);
                    mDatabase.child(nhomKhachHang.getId()).child("ghichuNhom").setValue(ghichu);
                    new SupportSaveLichSu(SuaNhomKhachHang.this, "Đã sửa nhóm khách hàng: "+ nhomKhachHang.getTenNhomKh());
                    finish();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
