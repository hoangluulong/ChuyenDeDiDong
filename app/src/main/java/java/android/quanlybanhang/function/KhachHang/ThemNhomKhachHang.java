package java.android.quanlybanhang.function.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Model.KhachHang.NhomKhachHang;
import java.android.quanlybanhang.R;

public class ThemNhomKhachHang extends AppCompatActivity {
    private EditText editTenNhom,editMa,editGhiChu;
    private Button btnTao,btnHuy;
    private DatabaseReference mDatabase;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private  String STR_NKH = "nhomkhachhang";
    private NhomKhachHang nhomKhachHang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitynhomkhachhang);
        editTenNhom = findViewById(R.id.edtTenNhomKhachHang);
        editMa = findViewById(R.id.edtMaKhachHang);
        editGhiChu = findViewById(R.id.edtGhiChuNhomKhachHang);
        btnHuy = findViewById(R.id.btnhuyTaoNhomKhachhang);
        btnTao = findViewById(R.id.btnTaoNhomKhachhang);
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_NKH);

        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTenNhom.getText().toString().isEmpty()){
                    editTenNhom.setError("Hãy nhập tên nhóm khách hàng");
                    editTenNhom.requestFocus();
                }
                else if(editMa.getText().toString().isEmpty()){
                    editMa.setError("Hãy nhập mã nhóm khách hàng");
                }
                else {
                    String id = mDatabase.push().getKey();
                    String name = editTenNhom.getText().toString();
                    String ma = editMa.getText().toString();
                    String ghichu = editGhiChu.getText().toString();
                    nhomKhachHang  = new NhomKhachHang(name,ma,ghichu,id);
                    mDatabase.child(id).setValue(nhomKhachHang);
                }
                editTenNhom.setText("");
                editMa.setText("");
                editGhiChu.setText("");
                Intent intent = new Intent();
                intent = new Intent(ThemNhomKhachHang.this, ListNhomKhachHang.class);
                startActivity(intent);
                finish();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(ThemNhomKhachHang.this, ListNhomKhachHang.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
