package java.android.quanlybanhang.function.CuaHangOnline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.android.quanlybanhang.R;

public class CuaHangOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private EditText edtThongTinChuyenKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang_online);
        navigationView = findViewById(R.id.nav_view);
        edtThongTinChuyenKhoan = findViewById(R.id.edtThongTinChuyenKhoan);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_homes);


//        edtThongTinChuyenKhoan.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                edtThongTinChuyenKhoan.setBackgroundResource(R.drawable.boder_input2);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                edtThongTinChuyenKhoan.setBackgroundResource(R.drawable.boder_input);
//            }
//        });
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
                Toast.makeText(this, "Thong tin", Toast.LENGTH_LONG).show();
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