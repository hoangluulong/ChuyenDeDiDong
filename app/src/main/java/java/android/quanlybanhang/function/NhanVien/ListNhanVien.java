package java.android.quanlybanhang.function.NhanVien;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
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

import java.android.quanlybanhang.HelperClasses.Package_AdapterNhanVien.AdapterNhanVien;
import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ListNhanVien  extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private FirebaseDatabase firebaseDatabase;
    private AdapterNhanVien adapterNhanVien;
    private RecyclerView recyclerView;
    private ArrayList<NhanVien> nhanViens;
    private FloatingActionButton floatingActionButton;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_USER = "user";
    private EditText searchView;
    private ArrayList<NhanVien> listSearch;
    private String key;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listnhanvien);
        recyclerView = findViewById(R.id.recyclerViewNhanVien);
        searchView = findViewById(R.id.btn_searchnv);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        floatingActionButton = findViewById(R.id.themnhanvien);
        mDatabase = firebaseDatabase.getReference(STR_CUAHANG).child(STR_USER);
        Danhsachnhanvien();
        Taonhanvienmoi();
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
            adapterNhanVien = new AdapterNhanVien(ListNhanVien.this,ListNhanVien.this,nhanViens);
            recyclerView.setAdapter(adapterNhanVien);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNhanVien.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < nhanViens.size();i++)
        {
            if(nhanViens.get(i).getUsername().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(nhanViens.get(i));
            }
        }
        adapterNhanVien = new AdapterNhanVien(ListNhanVien.this,ListNhanVien.this,listSearch);
        recyclerView.setAdapter(adapterNhanVien);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNhanVien.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
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
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhanViens = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
//                    String name = snapshot1.child("username").getValue().toString();
//                    String phone = snapshot1.child("phone").getValue().toString();
//                    CaLam caLam = snapshot1.child("caLam").getValue(CaLam.class);
//                    ArrayList<Boolean>  chucVu = (ArrayList<Boolean>) snapshot1.child("chucVu").getValue();
                    NhanVien nhanVien = snapshot1.getValue(NhanVien.class);
                    nhanViens.add(nhanVien);
                }
                adapterNhanVien = new AdapterNhanVien(ListNhanVien.this,ListNhanVien.this,nhanViens);
                recyclerView.setAdapter(adapterNhanVien);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNhanVien.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void delete(final int position){
        new AlertDialog.Builder(ListNhanVien.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.child(nhanViens.get(position).getId()).removeValue();
            }
        }).setNegativeButton("No", null)
                .show();
    }
}
