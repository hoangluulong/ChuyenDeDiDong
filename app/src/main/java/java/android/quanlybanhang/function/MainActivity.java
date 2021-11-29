package java.android.quanlybanhang.function;

import android.content.Intent;
import android.database.Cursor;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.android.quanlybanhang.function.Account.SignInActivity;
import java.android.quanlybanhang.function.BaoCao.BaoCaoTongQuanActivity;
import java.android.quanlybanhang.function.BepBar.BepActivity;
import java.android.quanlybanhang.function.CuaHangOnline.CuaHangOnlineActivity;
import java.android.quanlybanhang.function.DonHangOnline.DuyetDonHangActivity;
import java.android.quanlybanhang.function.KhachHang.ListKhachHang;
import java.android.quanlybanhang.function.KhachHang.ListNhomKhachHang;
import java.android.quanlybanhang.function.KhuyenMai.ListKhuyenMai;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.android.quanlybanhang.function.SanPham.ListProduct;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Database_order database_order;
    FirebaseAuth mFirebaseAuth;
    RelativeLayout ordermenu, baocao, donOnline, bep, online, account;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String ID_CUAHANG;
    private String ID_USER;
    private NhanVien nhanVien;
    private ThongTinCuaHangSql thongTinCuaHangSql;

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

        java.android.quanlybanhang.database.ThongTinCuaHangSql thongTinCuaHangSqlDB = new java.android.quanlybanhang.database.ThongTinCuaHangSql(MainActivity.this, "app_database.sqlite", null, 2);
        thongTinCuaHangSqlDB.createTableUser();

        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        nhanVien = thongTinCuaHangSql.selectUser();
        boolean ss = thongTinCuaHangSql.isChu();
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        ID_USER = thongTinCuaHangSql.IDUser();

        Log.d("ID_USER", ID_USER + " - " + ID_CUAHANG);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();


        mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHANG + "/user/" + ID_USER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cursor cursor = thongTinCuaHangSqlDB.selectUser();
                nhanVien = snapshot.getValue(NhanVien.class);
                String quyen = "";
                for (int i = 0; i < nhanVien.getChucVu().size(); i++) {
                    if (nhanVien.getChucVu().get(i)) {
                        quyen = quyen + "1";
                    } else {
                        quyen = quyen + "0";
                    }
                }

                if (cursor.getCount() > 0) {
                    String IdOld = "";
                    while (cursor.moveToNext()) {
                        IdOld = cursor.getString(0);
                    }
                    thongTinCuaHangSqlDB.UpdateUser(nhanVien.getId(), nhanVien.getId(), nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                } else {
                    thongTinCuaHangSqlDB.InsertUser(nhanVien.getId(), nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ordermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien.getChucVu().get(3)) {
                    Intent intent = new Intent(MainActivity.this, OrderMenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien.getChucVu().get(2)) {
                    Intent intent = new Intent(MainActivity.this, BaoCaoTongQuanActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        donOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien.getChucVu().get(5)) {
                    Intent intent = new Intent(MainActivity.this, DuyetDonHangActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien.getChucVu().get(4)) {
                    Intent intent = new Intent(MainActivity.this, BepActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien.getChucVu().get(5)) {
                    Intent intent = new Intent(MainActivity.this, CuaHangOnlineActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThietLapActivity.class);
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
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
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