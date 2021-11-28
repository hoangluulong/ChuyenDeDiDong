package java.android.quanlybanhang.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.Account.ChiNhanhActivity;
import java.android.quanlybanhang.function.Account.ThongTinAccountActivity;
import java.android.quanlybanhang.function.KhachHang.ListKhachHang;
import java.android.quanlybanhang.function.NhanVien.ChamCongNhanVienActivity;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.android.quanlybanhang.function.SanPham.ListCategory;
import java.android.quanlybanhang.function.SanPham.ListProduct;


public class ThietLapActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layout_taikhoan, layout_quanlynhanvien, layout_sanpham, layout_danhmuc, layout_nhomkhachang, layout_khuyenmai, layout_kho, layout_chi, layout_chinhanh;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private NhanVien nhanVien;
    private boolean isChu;

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
        layout_kho = findViewById(R.id.layout_kho);
        layout_chi = findViewById(R.id.layout_chi);
        layout_chinhanh = findViewById(R.id.layout_chinhanh);

        layout_taikhoan.setOnClickListener(this);
        layout_quanlynhanvien.setOnClickListener(this);
        layout_sanpham.setOnClickListener(this);
        layout_danhmuc.setOnClickListener(this);
        layout_nhomkhachang.setOnClickListener(this);
        layout_khuyenmai.setOnClickListener(this);
        layout_chinhanh.setOnClickListener(this);
        layout_kho.setOnClickListener(this);
        layout_chi.setOnClickListener(this);

        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        nhanVien = thongTinCuaHangSql.selectUser();
        isChu = thongTinCuaHangSql.isChu();

        if (isChu) {
            layout_chinhanh.setVisibility(View.VISIBLE);
        }else {
            layout_chinhanh.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_taikhoan:
                Intent intent1 = new Intent(ThietLapActivity.this, ThongTinAccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_quanlynhanvien:
                Intent intent2 = new Intent(ThietLapActivity.this, ListNhanVien.class);
                startActivity(intent2);
                break;
            case R.id.layout_sanpham:
                if (isChu) {
                    Intent intent3 = new Intent(ThietLapActivity.this, ListProduct.class);
                    startActivity(intent3);
                } else if (nhanVien.getChucVu().get(1)) {
                    Intent intent3 = new Intent(ThietLapActivity.this, ListProduct.class);
                    startActivity(intent3);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.layout_danhmuc:
                if (isChu) {
                    Intent intent4 = new Intent(ThietLapActivity.this, ListCategory.class);
                    startActivity(intent4);
                } else if (nhanVien.getChucVu().get(1)) {
                    Intent intent3 = new Intent(ThietLapActivity.this, ListProduct.class);
                    startActivity(intent3);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_nhomkhachang:
                if (isChu) {
                    Intent intent5 = new Intent(ThietLapActivity.this, ListKhachHang.class);
                    startActivity(intent5);
                } else if (nhanVien.getChucVu().get(6)) {
                    Intent intent5 = new Intent(ThietLapActivity.this, ListKhachHang.class);
                    startActivity(intent5);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_khuyenmai:
                if (isChu) {
                    Intent intent6 = new Intent(ThietLapActivity.this, ChamCongNhanVienActivity.class);
                    startActivity(intent6);
                } else if (nhanVien.getChucVu().get(7)) {
                    Intent intent6 = new Intent(ThietLapActivity.this, ChamCongNhanVienActivity.class);
                    startActivity(intent6);
                } else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_kho:

                break;
            case R.id.layout_chi:
                Intent intent8 = new Intent(ThietLapActivity.this, ChiTieuActivity.class);
                startActivity(intent8);
                break;
            case R.id.layout_chinhanh:
                Intent intent7 = new Intent(ThietLapActivity.this, ChiNhanhActivity.class);
                startActivity(intent7);
                break;
        }
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