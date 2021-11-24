package java.android.quanlybanhang.function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.Account.ThongTinAccountActivity;
import java.android.quanlybanhang.function.KhachHang.ListKhachHang;
import java.android.quanlybanhang.function.NhanVien.ChamCongNhanVienActivity;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.android.quanlybanhang.function.SanPham.ListCategory;
import java.android.quanlybanhang.function.SanPham.ListProduct;


public class ThietLapActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout layout_taikhoan, layout_quanlynhanvien,layout_sanpham, layout_danhmuc,layout_nhomkhachang, layout_khuyenmai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap);
        layout_taikhoan = findViewById(R.id.layout_taikhoan);
        layout_quanlynhanvien = findViewById(R.id.layout_quanlynhanvien);
        layout_sanpham = findViewById(R.id.layout_sanpham);
        layout_danhmuc = findViewById(R.id.layout_danhmuc);
        layout_nhomkhachang = findViewById(R.id.layout_nhomkhachang);
        layout_khuyenmai = findViewById(R.id.layout_khuyenmai);

        layout_taikhoan.setOnClickListener(this);
        layout_quanlynhanvien.setOnClickListener(this);
        layout_sanpham.setOnClickListener(this);
        layout_danhmuc.setOnClickListener(this);
        layout_nhomkhachang.setOnClickListener(this);
        layout_khuyenmai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_taikhoan:
                Intent intent1 = new Intent(ThietLapActivity.this, ThongTinAccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_quanlynhanvien:
                Intent intent2 = new Intent(ThietLapActivity.this, ListNhanVien.class);
                startActivity(intent2);
                break;
            case R.id.layout_sanpham:
                Intent intent3 = new Intent(ThietLapActivity.this, ListProduct.class);
                startActivity(intent3);
                break;
            case R.id.layout_danhmuc:
                Intent intent4 = new Intent(ThietLapActivity.this, ListCategory.class);
                startActivity(intent4);
                break;
            case R.id.layout_nhomkhachang:
                Intent intent5 = new Intent(ThietLapActivity.this, ListKhachHang.class);
                startActivity(intent5);
                break;
            case R.id.layout_khuyenmai:
                Intent intent6 = new Intent(ThietLapActivity.this, ChamCongNhanVienActivity.class);
                startActivity(intent6);
                break;
        };
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