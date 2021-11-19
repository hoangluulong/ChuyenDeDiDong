package java.android.quanlybanhang.function;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Model.KhuyenMai.KhuyenMai;
import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.android.quanlybanhang.function.Account.SignInActivity;
import java.android.quanlybanhang.function.Account.ThongTinAccountActivity;
import java.android.quanlybanhang.function.BaoCao.BaoCaoTongQuanActivity;
import java.android.quanlybanhang.function.BepBar.BepActivity;
import java.android.quanlybanhang.function.CuaHangOnline.CuaHangOnlineActivity;
import java.android.quanlybanhang.function.DonHangOnline.DuyetDonHangActivity;
import java.android.quanlybanhang.function.KhachHang.ListKhachHang;
import java.android.quanlybanhang.function.KhachHang.ListNhomKhachHang;
import java.android.quanlybanhang.function.KhuyenMai.ListKhuyenMai;
import java.android.quanlybanhang.function.KhuyenMai.ThemKhuyenMai;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.android.quanlybanhang.function.SanPham.ListProduct;

//import java.android.quanlybanhang.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Database_order database_order;
    FirebaseAuth mFirebaseAuth;
    RelativeLayout ordermenu, baocao, donOnline, bep, online,account;
    private DatabaseReference mDatabase;//khai bao database
    private DatabaseReference mDatabase1;//khai bao database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ordermenu = findViewById(R.id.orderbutton);
        baocao = findViewById(R.id.baocao);
        donOnline = findViewById(R.id.donOnline);
        bep = findViewById(R.id.bep);
        online = findViewById(R.id.online);
        account = findViewById(R.id.account);

        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("gopban");
        mDatabase.child("trangthai").setValue("0");

        ordermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderMenu.class);
                startActivity(intent);
            }
        });

        baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaoCaoTongQuanActivity.class);
                startActivity(intent);
            }
        });

        donOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DuyetDonHangActivity.class);
                startActivity(intent);
            }
        });

        bep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BepActivity.class);
                startActivity(intent);
            }
        });

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CuaHangOnlineActivity.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThongTinAccountActivity.class);
                startActivity(intent);
            }
        });

        // Write a message to the database
        mFirebaseAuth = FirebaseAuth.getInstance();

        /*lay id tung phan*/
        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*lay ra ten cua toolbar*/
//        setSupportActionBar(toolbar);

        /* lay ra action bar*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_homes);

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);

        Toast.makeText(this, thongTinCuaHangSql.IDUser(), Toast.LENGTH_LONG).show();
        NhanVien nhanVien = thongTinCuaHangSql.selectUser();
        Log.d("zz", thongTinCuaHangSql.IDCuaHang());
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "nhấn trở lại 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homes:
                break;
            case R.id.ds_order:
                Intent intent = new Intent(MainActivity.this, ListProduct.class);
                startActivity(intent);
                break;
            case R.id.ds_chebien:
                Intent intent1 = new Intent(MainActivity.this, ListNhanVien.class);
                startActivity(intent1);
                break;
            case R.id.ds_thuchi:
                Intent intent2 = new Intent(MainActivity.this, ListKhuyenMai.class);
                startActivity(intent2);
                break;
            case R.id.quanly:
                Intent intent3 = new Intent(MainActivity.this, ListNhomKhachHang.class);
                startActivity(intent3);
                break;
            case R.id.profile:
                Intent intent4 = new Intent(MainActivity.this, ListKhachHang.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent5);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}