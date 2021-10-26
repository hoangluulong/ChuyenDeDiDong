package java.android.quanlybanhang.function;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.android.quanlybanhang.function.Account.SignInActivity;
import java.android.quanlybanhang.function.BaoCao.BaoCaoTongQuanActivity;
import java.android.quanlybanhang.function.DonHangOnline.DuyetDonHangActivity;
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
    RelativeLayout ordermenu,baocao, donOnline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        odermenu
//        DeleteSp();
        ordermenu = findViewById(R.id.orderbutton);
        baocao = findViewById(R.id.baocao);
        donOnline = findViewById(R.id.donOnline);

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


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("aa");

        myRef.setValue("Hello, World!");
        mFirebaseAuth = FirebaseAuth.getInstance();

       /*lay id tung phan*/
        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*lay ra ten cua toolbar*/
//        setSupportActionBar(toolbar);

        /* lay ra action bar*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_homes);

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
                Intent intent2 = new Intent(MainActivity.this, OrderMenu.class);
                startActivity(intent2);
                break;
            case R.id.quanly:
                Intent intent3 = new Intent(MainActivity.this, OrderMenu.class);
                startActivity(intent3);
                break;
            case R.id.profile:
                Intent intent4 = new Intent(MainActivity.this, OrderMenu.class);
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