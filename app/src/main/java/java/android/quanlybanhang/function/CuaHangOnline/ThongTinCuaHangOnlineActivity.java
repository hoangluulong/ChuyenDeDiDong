package java.android.quanlybanhang.function.CuaHangOnline;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.android.quanlybanhang.R;

public class ThongTinCuaHangOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout btnDowUpThongtin, thongtinchung, btnDiaChi, diachi, btnHinhAnh;
    private LinearLayout.LayoutParams params1, params2, params3;
    private ImageView thongTinIMG, diaChiIMG;
    private boolean setL1 = false;
    private boolean setL2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_cua_hang_online);
        IDLayout();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void IDLayout() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        thongTinIMG = findViewById(R.id.thongTinIMG);
        btnDiaChi = findViewById(R.id.btnDiaChi);
        diachi = findViewById(R.id.diachi);
        btnDowUpThongtin = findViewById(R.id.btnDowUpThongtin);
        thongtinchung = findViewById(R.id.thongtinchung);
        diaChiIMG = findViewById(R.id.diaChiIMG);
        btnHinhAnh = findViewById(R.id.btnHinhAnh);

        params1 = (LinearLayout.LayoutParams) thongtinchung.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) diachi.getLayoutParams();
//        params3 = (LinearLayout.LayoutParams) thongtinchung.getLayoutParams();

        btnDowUpThongtin.setOnClickListener(this);
        btnDiaChi.setOnClickListener(this);


        playFire();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDowUpThongtin:
                if (setL1) {
                    params1.height = 0;
                    setL1 = false;
                    thongTinIMG.setImageResource(R.drawable.down_24);
                    thongtinchung.setLayoutParams(params1);
                } else {
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL1 = true;
                    thongTinIMG.setImageResource(R.drawable.up_24);
                    thongtinchung.setLayoutParams(params1);
                }
                break;
            case R.id.btnDiaChi:
                if (setL2) {
                    params2.height = 0;
                    setL2 = false;
                    diaChiIMG.setImageResource(R.drawable.down_24);
                    diachi.setLayoutParams(params2);
                } else {
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL2 = true;
                    diaChiIMG.setImageResource(R.drawable.up_24);
                    diachi.setLayoutParams(params2);

                }
                break;
        }
    }

    private void playFire() {
        if (setL1) {
            params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            thongTinIMG.setImageResource(R.drawable.up_24);
            thongtinchung.setLayoutParams(params1);
        } else {
            params1.height = 0;
            thongTinIMG.setImageResource(R.drawable.down_24);
            thongtinchung.setLayoutParams(params1);
        }

        if (setL2) {
            params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            diaChiIMG.setImageResource(R.drawable.up_24);
            diachi.setLayoutParams(params2);
        } else {
            params2.height = 0;
            diaChiIMG.setImageResource(R.drawable.down_24);
            diachi.setLayoutParams(params2);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cuahang:
                Intent intent = new Intent(this, CuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sanpham:
                Toast.makeText(this, "San pham", Toast.LENGTH_LONG).show();
                break;
            case R.id.quangcao:
                Toast.makeText(this, "Quang cáo", Toast.LENGTH_LONG).show();
                break;
            case R.id.thongtin:
                break;
            case R.id.giolamviec:
                Toast.makeText(this, "gio làm việc", Toast.LENGTH_LONG).show();
                break;
            case R.id.vanchuyen:
                Toast.makeText(this, "vanchuye", Toast.LENGTH_LONG).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}