package com.example.shipper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fragment.HomeFragment;

public class ChiTietActivity extends AppCompatActivity {
    Button btnnhandon, btnquaylai;
    TextView tvdiemnhan,tvdiemgiao,tvtonggia,tvtenkh,tvsodt,tvghichu,tvthunhap,tvtensp;
    private DonHang donHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        Anhxa();


        btnnhandon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietActivity.this,HoaDonActivity.class);

//                tvdiemnhan.setText(intent.getBundleExtra(HomeFragment.KEY_DIEMNHAN));
                //Bundle bundle = intent.getBundleExtra()
                Bundle bundle = new Bundle();
                bundle.putSerializable(HomeFragment.KEY_DIEMNHAN,donHang);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietActivity.this, Home.class);
                startActivity(intent);
            }
        });
    }



    private void Anhxa(){
        btnnhandon = (Button) findViewById(R.id.btn_nhandon);
        btnquaylai = (Button) findViewById(R.id.btn_quaylai);
        tvdiemnhan = (TextView) findViewById(R.id.diemnhan);
        tvdiemgiao = (TextView) findViewById(R.id.diemgiao);
        tvtonggia = (TextView) findViewById(R.id.tonggia);
        tvtenkh = (TextView) findViewById(R.id.tenKH);
        tvsodt = (TextView) findViewById(R.id.sdt);
        tvghichu = (TextView) findViewById(R.id.ghiChu);
        tvthunhap = (TextView) findViewById(R.id.tv_thunhap);
        tvtensp = (TextView) findViewById(R.id.tenSanPham);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        donHang = (DonHang) bundle.getSerializable(HomeFragment.KEY_DIEMNHAN);
        tvdiemnhan.setText(donHang.getDiemnhan());
        tvdiemgiao.setText(donHang.getDiaChi());
        tvtonggia.setText( donHang.getDonGia()+"");
        tvtenkh.setText( donHang.getTenKhachHang());
        tvsodt.setText(donHang.getSdtkhachhang());
        tvthunhap.setText(donHang.getThunhap()+"");
        tvghichu.setText(donHang.getGhiChu());

    }
}