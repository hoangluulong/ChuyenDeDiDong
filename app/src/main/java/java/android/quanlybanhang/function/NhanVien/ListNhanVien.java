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


import java.android.quanlybanhang.HelperClasses.Package_AdapterNhanVien.AdapterNhanVien;
import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ListNhanVien  extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private AdapterNhanVien adapterNhanVien;
    private RecyclerView recyclerView;
    private ArrayList<NhanVien> nhanViens;
    private FloatingActionButton floatingActionButton;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_USER = "user";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listnhanvien);
        recyclerView = findViewById(R.id.recyclerViewNhanVien);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        floatingActionButton = findViewById(R.id.themnhanvien);
        mDatabase = firebaseDatabase.getReference(STR_CUAHANG).child(STR_USER);
        Danhsachnhanvien();
        Taonhanvienmoi();
    }
    // Button thêm nhân viên
    public void Taonhanvienmoi(){
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
    }
    //Hiển thị nhân viên
    public void Danhsachnhanvien(){
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
