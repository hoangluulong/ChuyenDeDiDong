package java.android.quanlybanhang.function;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.android.quanlybanhang.Model.AddressVN.DiaChi;
import java.android.quanlybanhang.Model.AddressVN.Huyen;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.android.quanlybanhang.database.ThongTinCuaHangSql;
import java.android.quanlybanhang.function.Account.SignInActivity;
import java.android.quanlybanhang.function.BaoCao.BaoCaoTongQuanActivity;
import java.android.quanlybanhang.function.BepBar.BepActivity;
import java.android.quanlybanhang.function.CuaHangOnline.CuaHangOnlineActivity;
import java.android.quanlybanhang.function.DonHangOnline.DuyetDonHangActivity;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//import java.android.quanlybanhang.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Database_order database_order;
    FirebaseAuth mFirebaseAuth;
    RelativeLayout ordermenu, baocao, donOnline, bep, online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ordermenu = findViewById(R.id.orderbutton);
        baocao = findViewById(R.id.baocao);
        donOnline = findViewById(R.id.donOnline);
        bep = findViewById(R.id.bep);
        online = findViewById(R.id.online);

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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSon().execute("https://provinces.open-api.vn/api/?depth=3");
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
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
            case R.id.cuahang:
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

    private String IDCuaHang() {
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(MainActivity.this, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTable();
        Cursor cursor = thongTinCuaHangSql.selectThongTin();
        String id = "";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                id = cursor.getString(0);
            }
            Toast.makeText(this, id + "   có", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Không có", Toast.LENGTH_LONG).show();
        }
        return id;
    }

    class docJSon extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<DiaChi> listDiaChi = new ArrayList<>();
            try {

                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject khuVuc = root.getJSONObject(i);
                    String tinhTP = khuVuc.getString("name");
                    JSONArray arrHuyen = khuVuc.getJSONArray("districts");
                    ArrayList<Huyen> huyens = new ArrayList<>();
                    for (int j = 0; j < arrHuyen.length(); j++) {
                        JSONObject khuVucHuyen = arrHuyen.getJSONObject(j);
                        String tenHuyen = khuVucHuyen.getString("name");
                        JSONArray arrXa = khuVucHuyen.getJSONArray("wards");
                        ArrayList<String> xas = new ArrayList<>();
                        for (int k = 0; k < arrXa.length(); k++) {
                            JSONObject khuVucXa = arrXa.getJSONObject(k);
                            String xa = khuVucXa.getString("name");
                            xas.add(xa);
                        }
                        Huyen huyen = new Huyen(tenHuyen, xas);
                        huyens.add(huyen);
                    }

                    DiaChi diaChi = new DiaChi(tinhTP, huyens);
                    listDiaChi.add(diaChi);
                }

                Log.d("qq", listDiaChi.size()+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            //Create a url object
            URL url = new URL(theUrl);

            //create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            //read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

}