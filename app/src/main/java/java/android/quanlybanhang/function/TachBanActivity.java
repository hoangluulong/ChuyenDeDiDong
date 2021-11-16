package java.android.quanlybanhang.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.android.quanlybanhang.Common.ArrayListTachBan;
import java.android.quanlybanhang.HelperClasses.PackageTachBan.AdapterTachBan;
import java.android.quanlybanhang.HelperClasses.Package_ThanhToanAdapter.ThanhToanAdapter;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.R;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TachBanActivity extends AppCompatActivity implements ArrayListTachBan {
    private ArrayList<ProuductPushFB1> listmon ;
    private Toolbar toolbar;
    private ArrayList<ProductPushFB> ListDate_yc ;
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    String id_ban_thanhtoan;
    String id_khuvuc_thanhtoan;
    ArrayList<ProductPushFB> carsList1;
    ArrayList<ProuductPushFB1> carsList;
    AdapterTachBan adapterTachBan;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tach_ban);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tách bàn");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent1 = getIntent();
        id_ban_thanhtoan = intent1.getStringExtra("id_ban");
        id_khuvuc_thanhtoan = intent1.getStringExtra("id_khuvuc");
        String carListAsString = getIntent().getStringExtra("list_as_string");
        String carListAsString1 = getIntent().getStringExtra("list_as_string1");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProuductPushFB1>>() {
        }.getType();
        Type type1 = new TypeToken<ArrayList<ProductPushFB>>() {
        }.getType();
        carsList1 = gson.fromJson(carListAsString1, type1);
        carsList = gson.fromJson(carListAsString, type);
        Log.d("km",carsList1.size()+"");
        adapterTachBan = new AdapterTachBan(carsList1,this,this);
        recyclerView = findViewById(R.id.rv_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(TachBanActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterTachBan);
        adapterTachBan.notifyDataSetChanged();
    }
    @Override
    public void arrTachBan(ArrayList<ProuductPushFB1> arrayList) {


    }
}