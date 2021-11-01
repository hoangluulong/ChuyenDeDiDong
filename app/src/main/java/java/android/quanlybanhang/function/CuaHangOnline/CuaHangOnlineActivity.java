package java.android.quanlybanhang.function.CuaHangOnline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.android.quanlybanhang.R;

public class CuaHangOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextInputEditText edtThongTinChuyenKhoan, ghiChu;
    private CheckBox checkboxKhachHangDenLay, checkboxDonViVanChuyen, checkboxTuGiao, checkboxChuyenKhoan, checkboxTienMat;
    private CardView cauhinhvanchuyen;
    private LinearLayout layoutThongTinChuyenKhoan;
    private TextView dangKy;
    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang_online);
        IDLayout();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_homes);
        setCheck();
    }

    private void IDLayout () {
        navigationView = findViewById(R.id.nav_view);
        edtThongTinChuyenKhoan = findViewById(R.id.edtThongTinChuyenKhoan);
        ghiChu = findViewById(R.id.ghiChu);
        checkboxKhachHangDenLay = findViewById(R.id.checkboxKhachHangDenLay);
        checkboxDonViVanChuyen = findViewById(R.id.checkboxDonViVanChuyen);
        checkboxTuGiao = findViewById(R.id.checkboxTuGiao);
        checkboxChuyenKhoan = findViewById(R.id.checkboxChuyenKhoan);
        checkboxTienMat = findViewById(R.id.checkboxTienMat);
        cauhinhvanchuyen = findViewById(R.id.cauhinhvanchuyen);
        layoutThongTinChuyenKhoan = findViewById(R.id.layoutThongTinChuyenKhoan);
        dangKy = findViewById(R.id.dangKy);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        params = (LinearLayout.LayoutParams) layoutThongTinChuyenKhoan.getLayoutParams();

        checkboxChuyenKhoan.setOnClickListener(this);
        checkboxTuGiao.setOnClickListener(this);
    }

    private void getData() {

    }

    private void setCheck() {
        if (checkboxChuyenKhoan.isChecked()) {
            layoutThongTinChuyenKhoan.setVisibility(View.VISIBLE);
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutThongTinChuyenKhoan.setLayoutParams(params);
        }else {
            layoutThongTinChuyenKhoan.setVisibility(View.INVISIBLE);
            params.height = 0;
            layoutThongTinChuyenKhoan.setLayoutParams(params);
        }
        if (checkboxTuGiao.isChecked()) {
            cauhinhvanchuyen.setVisibility(View.VISIBLE);
        }else{
            cauhinhvanchuyen.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkboxTuGiao:
                if (checkboxTuGiao.isChecked()) {
                    cauhinhvanchuyen.setVisibility(View.VISIBLE);
                }else{
                    cauhinhvanchuyen.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.cauhinhvanchuyen:
                break;
            case R.id.checkboxChuyenKhoan:
                if (checkboxChuyenKhoan.isChecked()) {
                    layoutThongTinChuyenKhoan.setVisibility(View.VISIBLE);
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutThongTinChuyenKhoan.setLayoutParams(params);
                }else {
                    layoutThongTinChuyenKhoan.setVisibility(View.INVISIBLE);
                    params.height = 0;
                    layoutThongTinChuyenKhoan.setLayoutParams(params);
                }
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cuahang:
                break;
            case R.id.sanpham:
                Toast.makeText(this, "San pham", Toast.LENGTH_LONG).show();
                break;
            case R.id.quangcao:
                Toast.makeText(this, "Quang cáo", Toast.LENGTH_LONG).show();
                break;
            case R.id.thongtin:
                Intent intent = new Intent(this, ThongTinCuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
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