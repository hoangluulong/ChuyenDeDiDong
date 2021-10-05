package java.android.quanlybanhang.function.NhanVien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
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

import java.android.quanlybanhang.Adapter.AdapterNhanVien;
import java.android.quanlybanhang.Data.NhanVien;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.SanPham.AddProduct;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.util.ArrayList;

public class ListNhanVien  extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private AdapterNhanVien adapterNhanVien;
    private RecyclerView recyclerView;
    private ArrayList<NhanVien> nhanViens;
    private FloatingActionButton floatingActionButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listnhanvien);
        recyclerView = findViewById(R.id.recyclerViewNhanVien);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        floatingActionButton = findViewById(R.id.themnhanvien);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm sản nhân viên mới ", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListNhanVien.this, AddNhanVien.class);
                                startActivity(intent);
                                finish();

                            }
                        }).show();
            }
        });
        mDatabase = firebaseDatabase.getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("user");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanViens = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    NhanVien nhanVien = snapshot1.getValue(NhanVien.class);
                   nhanViens.add(nhanVien);
                }
                adapterNhanVien = new AdapterNhanVien(ListNhanVien.this,nhanViens);
                recyclerView.setAdapter(adapterNhanVien);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNhanVien.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
