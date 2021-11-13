package java.android.quanlybanhang.function.KhachHang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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

import java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang.AdapterNhomKhachHang;
import java.android.quanlybanhang.Model.KhachHang.NhomKhachHang;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ListNhomKhachHang extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_NKH = "nhomkhachhang";
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private AdapterNhomKhachHang adapterNhomKhachHang;
    private NhomKhachHang nhomKhachHang;
    private ArrayList<NhomKhachHang> nhomKhachHangs;
    private EditText searchView;
    private ArrayList<NhomKhachHang> listSearch;
    private String key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listnhomkhachhang);
        recyclerView = findViewById(R.id.recyclerViewKhachHang);
        floatingActionButton = findViewById(R.id.themnhomkhachhang);
        searchView = findViewById(R.id.btn_searchnhomKH);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference(STR_CUAHANG).child(STR_NKH);
        DanhSachNhomSanPham();
        ThemNhomKhachHang();
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
            adapterNhomKhachHang = new AdapterNhomKhachHang(ListNhomKhachHang.this,ListNhomKhachHang.this,nhomKhachHangs);
            recyclerView.setAdapter(adapterNhomKhachHang);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNhomKhachHang.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < nhomKhachHangs.size();i++)
        {
            if(nhomKhachHangs.get(i).getTenNhomKh().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(nhomKhachHangs.get(i));
            }
        }
        adapterNhomKhachHang = new AdapterNhomKhachHang(ListNhomKhachHang.this,ListNhomKhachHang.this,listSearch);
        recyclerView.setAdapter(adapterNhomKhachHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNhomKhachHang.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void DanhSachNhomSanPham(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhomKhachHangs = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    nhomKhachHang = snapshot1.getValue(NhomKhachHang.class);
                    nhomKhachHangs.add(nhomKhachHang);
                    adapterNhomKhachHang = new AdapterNhomKhachHang(ListNhomKhachHang.this,ListNhomKhachHang.this,nhomKhachHangs);
                    recyclerView.setAdapter(adapterNhomKhachHang);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNhomKhachHang.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ThemNhomKhachHang(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm nhóm khách hàng mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListNhomKhachHang.this, ThemNhomKhachHang.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
    }

    public void delete(int position){
        new AlertDialog.Builder(ListNhomKhachHang.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mDatabase.child(nhomKhachHangs.get(position).getId()).removeValue();

            }
        }).setNegativeButton("No", null)
                .show();
    }


}
