package java.android.quanlybanhang.function.CuaHangOnline;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Data.ThoiGian;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThoiGianLamViecOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView thoiGianBatDau, thoiGianKetThuc, tamDung, xacNhan;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatabaseReference mFirebaseDatabase;
    private TextView thonbao;

    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private boolean isCheckStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_gian_lam_viec_online);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.giolamviec);

        getDataFirebase();

    }

    private void IDLayout() {
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        thoiGianBatDau = findViewById(R.id.thoiGianBatDau);
        thoiGianKetThuc = findViewById(R.id.thoiGianKetThuc);
        tamDung = findViewById(R.id.tamDung);
        xacNhan = findViewById(R.id.xacNhan);
        thonbao = findViewById(R.id.thonbao);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        thoiGianBatDau.setOnClickListener(this);
        thoiGianKetThuc.setOnClickListener(this);
        xacNhan.setOnClickListener(this);
        tamDung.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thoiGianBatDau:
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, R.style.TimePicker,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                thoiGianBatDau.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog1.show();
                break;
            case R.id.thoiGianKetThuc:
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(this,R.style.TimePicker,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                thoiGianKetThuc.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog2.show();
                break;
            case R.id.tamDung:

                break;
            case R.id.xacNhan:
                saveCheck();
                break;
        }
    }
    
    private void kichHoat() {
        if (isCheckStatus) {
            isCheckStatus = false;
        }else {
            isCheckStatus = true;
        }
        mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thoiGianLamViec/status").setValue(isCheckStatus);
    }

    private void saveCheck() {
        String batDau = thoiGianBatDau.getText().toString();
        String ketThuc = thoiGianKetThuc.getText().toString();

        ThoiGian thoiGian = new ThoiGian(batDau, ketThuc, isCheckStatus);
        mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thoiGianLamViec").setValue(thoiGian);
    }

    private Date dataC(String dateStr){
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        try {
            Date date = formatter.parse(dateStr);
            return date;
        } catch (Exception e) {
            Date date = new Date();
            return date;
        }
    }

    private void getDataFirebase() {
        mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thoiGianLamViec").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ThoiGian thoiGian = snapshot.getValue(ThoiGian.class);
                thoiGianBatDau.setText(thoiGian.getBatDau());
                thoiGianKetThuc.setText(thoiGian.getKetThuc());
                isCheckStatus = thoiGian.isStatus();

                if (isCheckStatus) {
                    tamDung.setText("Tạm dừng");
                    tamDung.setTextColor(getColor(R.color.red));
                    tamDung.setBackgroundResource(R.drawable.bg_xoa_van_chuyen);
                    thonbao.setText("");
                    xacNhan.setEnabled(true);
                }else {
                    tamDung.setText("Kích hoạt");
                    tamDung.setTextColor(getColor(R.color.Python));
                    tamDung.setBackgroundResource(R.drawable.bg_kich_hoat);
                    thonbao.setText("Cửa hàng của bạn đang không hoạt động, kích hoạt để bắt đầu");
                    xacNhan.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cuahang:
                intent = new Intent(this, CuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sanpham:
                intent = new Intent(this, TaoSanPhamOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.quangcao:
                intent = new Intent(this, QuangCaoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.thongtin:
                intent = new Intent(this, ThongTinCuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.giolamviec:
                break;
            case R.id.vanchuyen:
                intent = new Intent(this, CauHinhVanChuyenOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
