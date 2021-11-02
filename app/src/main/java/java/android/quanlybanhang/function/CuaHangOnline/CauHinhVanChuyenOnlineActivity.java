package java.android.quanlybanhang.function.CuaHangOnline;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Adapter.DiaChiAdapter;
import java.android.quanlybanhang.function.CuaHangOnline.Adapter.ImageAdapter;
import java.android.quanlybanhang.function.CuaHangOnline.Data.GiaoHang;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Setting;
import java.util.ArrayList;

public class CauHinhVanChuyenOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RecyclerView recycleview;
    private TextView btnTaoDonMoi;
    private DiaChiAdapter diaChiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau_hinh_van_chuyen_online);
        IDLayout();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void IDLayout() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);

        recycleview = findViewById(R.id.recycleview);

        displayItem();
    }

    private void displayItem() {
        ArrayList<Setting> list = new ArrayList<>();
        ArrayList<GiaoHang> list1 = new ArrayList<>();
        GiaoHang giaoHang = new GiaoHang("loai 1", 20000, 10000, 30000);

        list1.add(new GiaoHang("loai 1", 20000, 10000, 30000));
        list1.add(new GiaoHang("loai 2", 20000, 10000, 30000));
        list1.add(new GiaoHang("loai 3", 20000, 10000, 30000));

        list.add(new Setting("Dak Nong", list1));
        list.add(new Setting("Dak lak", list1));

        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(this, 1));
        diaChiAdapter = new DiaChiAdapter(this, list);
        recycleview.setAdapter(diaChiAdapter);
        diaChiAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

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
                Toast.makeText(this, "San pham", Toast.LENGTH_LONG).show();
                break;
            case R.id.quangcao:
                Toast.makeText(this, "Quang cáo", Toast.LENGTH_LONG).show();
                break;
            case R.id.thongtin:
                intent = new Intent(this, ThongTinCuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.giolamviec:
                Toast.makeText(this, "gio làm việc", Toast.LENGTH_LONG).show();
                break;
            case R.id.vanchuyen:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}