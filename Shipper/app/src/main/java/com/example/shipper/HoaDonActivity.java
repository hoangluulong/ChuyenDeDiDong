package com.example.shipper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import fragment.HomeFragment;

public class HoaDonActivity extends AppCompatActivity {
    Button btnhuydon, btnhoanthanh;
    Button btnnhandon, btnquaylai;
    TextView tvdiemnhan,tvdiemgiao,tvtonggia,tvtenkh,tvsodt,tvghichu,tvthunhap,tvtensp;
    private DonHang donHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        Anhxa();
        ControlButton();
        ControlButtonHoanThanh();
    }
    private void ControlButton(){
        btnhuydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("Xác nhận hủy đơn");
                builder.setMessage("");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HoaDonActivity.this, Home.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
    private void ControlButtonHoanThanh(){
        btnhoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("Xác nhận hoàn thành");
                builder.setMessage("");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HoaDonActivity.this,Home.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
    private void Anhxa(){
        btnhuydon = (Button) findViewById(R.id.btn_nhandon);
        btnhoanthanh = (Button) findViewById(R.id.btn_quaylai);
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


        btnhoanthanh.setText("Hoàn thành");
        btnhuydon.setText("Hủy đơn");

    }
}
