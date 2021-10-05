package java.android.quanlybanhang.ChiTietSanPham;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.android.quanlybanhang.OrderMon.Product;
import java.android.quanlybanhang.R;

public class ChiTietSanPham_Banh extends AppCompatActivity {
    private Toolbar toolbar;//tool bar khai bao id
    Product staticMonOrderModel;
    TextView tensp,giasp,giatongsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham_banh);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
//             viet su kien cho toolbar
        ActionBar actionBar = getSupportActionBar();
//Thiết lập tiêu đề nếu muốn
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //lấy thông tin qua trâng xác nhận order
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        staticMonOrderModel = (Product) bundle.getSerializable("sp");
        String tensps = staticMonOrderModel.getNameProduct();
        Double giasanphams = staticMonOrderModel.getGiaBan();

        tensp = findViewById(R.id.tvtensanpham);
        giasp = findViewById(R.id.tvgiasanpham);
        giatongsp = findViewById(R.id.tvgiatongsanpham);
        tensp.setText(tensps);
        giasp.setText(giasanphams+"$");
        giatongsp.setText(giasanphams+"$");

    }
}