package java.android.quanlybanhang.function.KhachHang;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang.AdapterKhachHang;
import java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang.AdapterNhomKhachHang;
import java.android.quanlybanhang.Model.KhachHang.KhachHang;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listnhomkhachhang);
        recyclerView = findViewById(R.id.recyclerViewKhachHang);
        floatingActionButton = findViewById(R.id.themnhomkhachhang);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference(STR_CUAHANG).child(STR_NKH);
    }

    public void DanhSachNhomSanPham(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nhomKhachHangs = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("snap1",snapshot1.getKey()+"");
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                        nhomKhachHang = snapshot2.getValue(NhomKhachHang.class);
                        nhomKhachHangs.add(nhomKhachHang);
                    }
                    adapterNhomKhachHang = new AdapterNhomKhachHang(ListNhomKhachHang.this,nhomKhachHangs);
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


}
