package java.android.quanlybanhang.function.CuaHangOnline;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.android.quanlybanhang.Common.DataAddress;
import java.android.quanlybanhang.Model.AddressVN.DiaChi;
import java.android.quanlybanhang.Model.AddressVN.Huyen;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Adapter.DiaChiAdapter;
import java.android.quanlybanhang.function.CuaHangOnline.Adapter.ImageAdapter;
import java.android.quanlybanhang.function.CuaHangOnline.Data.GiaoHang;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Setting;
import java.android.quanlybanhang.function.KhuyenMai.ListKhuyenMai;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CauHinhVanChuyenOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RecyclerView recycleview;
    private TextView btnTaoDonMoi;
    private DiaChiAdapter diaChiAdapter;
    private AutoCompleteTextView thanhphoAuto, quan_huyenAuto, phuongxaAuto;
    private LinearLayout btnTuGiao, btnDoitacvanchuyen, tuGiao, doitac;
    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();
    private LinearLayout.LayoutParams params1, params2;
    private ImageView tugiaoIMG, doitacIMG;

    private String[] tinh;
    private String tenTinh;
    private String tenHuyen;
    private String tenXa;

    private boolean setL1 = false;
    private boolean setL2 = true;

    private ArrayAdapter<String> adapterTinh;
    private ArrayAdapter<String> adapterHuyen;
    private ArrayAdapter<String> adapterXa;
    private int ViTri;

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
        navigationView.setCheckedItem(R.id.vanchuyen);

        DataAddress dataAddress = new DataAddress();
        try {
            listDiaChi = dataAddress.readCompanyJSONFile(this);
            setDataText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params1.height = 0;
        tugiaoIMG.setImageResource(R.drawable.down_24);
        tuGiao.setLayoutParams(params1);
    }

    private void IDLayout() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        thanhphoAuto = findViewById(R.id.thanhpho);
        quan_huyenAuto = findViewById(R.id.quan_huyen);
        phuongxaAuto = findViewById(R.id.phuongxa);
        btnTuGiao = findViewById(R.id.btnTuGiao);
        btnDoitacvanchuyen = findViewById(R.id.btnDoitacvanchuyen);
        tuGiao = findViewById(R.id.tuGiao);
        doitac = findViewById(R.id.doitac);
        doitacIMG = findViewById(R.id.doitacIMG);
        tugiaoIMG = findViewById(R.id.tugiaoIMG);

        params1 = (LinearLayout.LayoutParams) tuGiao.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) doitac.getLayoutParams();

        recycleview = findViewById(R.id.recycleview);

        btnTuGiao.setOnClickListener(this);
        btnDoitacvanchuyen.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.btnTuGiao:
                if (setL1) {
                    params1.height = 0;
                    setL1 = false;
                    tugiaoIMG.setImageResource(R.drawable.down_24);
                    tuGiao.setLayoutParams(params1);
                } else {
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL1 = true;
                    tugiaoIMG.setImageResource(R.drawable.up_24);
                    tuGiao.setLayoutParams(params1);
                }
                break;
            case R.id.btnDoitacvanchuyen:
                if (setL2) {
                    params2.height = 0;
                    setL2 = false;
                    doitacIMG.setImageResource(R.drawable.down_24);
                    doitac.setLayoutParams(params2);
                } else {
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL2 = true;
                    doitacIMG.setImageResource(R.drawable.up_24);
                    doitac.setLayoutParams(params2);
                }
                break;
        }
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
                intent = new Intent(this, ThoiGianLamViecOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.vanchuyen:
                break;
            case R.id.khuyenmai:
                intent = new Intent(this, ListKhuyenMai.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setDataText() {
        tinh = ArrayTinh();
        adapterTinh = new ArrayAdapter<String>(this, R.layout.item_spinner1_setup_store, tinh);
        thanhphoAuto.setAdapter(adapterTinh);
        adapterTinh.notifyDataSetChanged();

        thanhphoAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();
                ViTri = position;
                String[] arrayHuyen = ArrayHuyen(position);
                adapterHuyen = new ArrayAdapter<String>(CauHinhVanChuyenOnlineActivity.this, R.layout.item_spinner1_setup_store, arrayHuyen);
                quan_huyenAuto.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                quan_huyenAuto.setAdapter(adapterHuyen);

                adapterXa = new ArrayAdapter<String>(CauHinhVanChuyenOnlineActivity.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(position).getHuyens().get(0).getXa());
                phuongxaAuto.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
                phuongxaAuto.setAdapter(adapterXa);
            }
        });

        quan_huyenAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();

                adapterXa = new ArrayAdapter<String>(CauHinhVanChuyenOnlineActivity.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                phuongxaAuto.setText(listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0));
                tenXa = listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0);
                phuongxaAuto.setAdapter(adapterXa);
            }
        });

        phuongxaAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenXa = parent.getItemAtPosition(position).toString();
            }
        });
    }

    private String[] ArrayTinh() {

        String[] arr = new String[listDiaChi.size()];

        for (int i = 0; i < listDiaChi.size(); i++){
            arr[i] = listDiaChi.get(i).getTenTinhTP();
        }

        return arr;
    }

    private String[] ArrayHuyen(int pos) {
        String[] arr = new String[listDiaChi.get(pos).getHuyens().size()];

        for (int i = 0; i < listDiaChi.get(pos).getHuyens().size(); i++){
            arr[i] = listDiaChi.get(pos).getHuyens().get(i).getTenHuyen();
        }
        return arr;
    }
}