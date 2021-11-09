package java.android.quanlybanhang.function.KhachHang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang.AdapterKhachHang;
import java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang.AdapterNhomKhachHang;
import java.android.quanlybanhang.Model.KhachHang.KhachHang;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.util.ArrayList;

public class ListKhachHang extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private AdapterKhachHang adapterKhachHang;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_KH = "khachhang";
    private ArrayList<KhachHang> khachHangs;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private FirebaseDatabase firebaseDatabase;
    private EditText searchView;
    private ArrayList<KhachHang> listSearch;
    private String key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listkhachhang);
        recyclerView = findViewById(R.id.recyclerListKhachHang);
        floatingActionButton = findViewById(R.id.themkhachhang);
        searchView = findViewById(R.id.btn_searchKH);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference(STR_CUAHANG).child(STR_KH);
        DanhSachKhachHang();
        ThemKhachHang();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key = searchView.getText().toString();
                getListSearch(key);
            }
        });
    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            adapterKhachHang = new AdapterKhachHang(ListKhachHang.this,ListKhachHang.this,khachHangs);
            recyclerView.setAdapter(adapterKhachHang);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhachHang.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < khachHangs.size();i++)
        {
            if(khachHangs.get(i).getHoTenKhachHang().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(khachHangs.get(i));
            }
        }
        adapterKhachHang = new AdapterKhachHang(ListKhachHang.this,ListKhachHang.this,khachHangs);
        recyclerView.setAdapter(adapterKhachHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhachHang.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void ThemKhachHang(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm khách hàng mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListKhachHang.this, ThemKhachHang.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
    }

    public void DanhSachKhachHang(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khachHangs = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("snap1",snapshot1.getKey()+"");
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                        KhachHang khachHang = snapshot2.getValue(KhachHang.class);
                        khachHangs.add(khachHang);
                        Log.d("khachhang",khachHang.getHoTenKhachHang());
                    }
                    adapterKhachHang = new AdapterKhachHang(ListKhachHang.this,ListKhachHang.this,khachHangs);
                    recyclerView.setAdapter(adapterKhachHang);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhachHang.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void delete(int position){
        new AlertDialog.Builder(ListKhachHang.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            DataSnapshot aaa = snapshot1;
                            Toast.makeText(ListKhachHang.this,khachHangs.get(position).getSoDT()+"",Toast.LENGTH_LONG).show();
                            mDatabase1 = firebaseDatabase.getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khachhang").child(aaa.getKey());
                            mDatabase1.child(khachHangs.get(position).getSoDT()).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }).setNegativeButton("No", null)
                .show();
    }
}
