package java.android.quanlybanhang.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.admin.Adapter.SanPhamQuangCaoAdapter;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Product;
import java.util.ArrayList;

public class SanPhamQuangCaoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<Product> listSPQuangCao = new ArrayList<>();
    private SanPhamQuangCaoAdapter sanPhamQuangCaoAdapter;
    private TextView edt_cho_xac_nhan, edt_dang_hoat_dong;
    private int loai = 1;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_quang_cao);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.sanPhamQuangCao);
        edt_cho_xac_nhan = findViewById(R.id.edt_cho_xac_nhan);
        edt_dang_hoat_dong = findViewById(R.id.edt_dang_hoat_dong);
        title = findViewById(R.id.title);

        sanPhamQuangCaoAdapter = new SanPhamQuangCaoAdapter(listSPQuangCao, loai);
        recyclerView.setAdapter(sanPhamQuangCaoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SanPhamQuangCaoActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        sanPhamQuangCaoAdapter.notifyDataSetChanged();

        getDataXacNhan();

        edt_cho_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 1;
                edt_cho_xac_nhan.setTextColor(ContextCompat.getColor(SanPhamQuangCaoActivity.this, R.color.xanh));
                edt_dang_hoat_dong.setTextColor(ContextCompat.getColor(SanPhamQuangCaoActivity.this, R.color.periwinkle));
                sanPhamQuangCaoAdapter.setLoai(1);
                getDataXacNhan();
            }
        });

        edt_dang_hoat_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 2;
                edt_dang_hoat_dong.setTextColor(ContextCompat.getColor(SanPhamQuangCaoActivity.this, R.color.xanh));
                edt_cho_xac_nhan.setTextColor(ContextCompat.getColor(SanPhamQuangCaoActivity.this, R.color.periwinkle));
                sanPhamQuangCaoAdapter.setLoai(2);
                getDataQuangCao();
            }
        });
    }

    private void getDataXacNhan() {
        mDatabase.child("ChoXacNhan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (loai == 1) {
                    listSPQuangCao.clear();
                    sanPhamQuangCaoAdapter.notifyDataSetChanged();

                    if (snapshot.getValue() != null) {
                        title.setText("");
                        for (DataSnapshot data : snapshot.getChildren()) {
                            listSPQuangCao.add(data.getValue(Product.class));
                        }
                        sanPhamQuangCaoAdapter.notifyDataSetChanged();
                    }
                } else {
                    title.setVisibility(View.VISIBLE);
                    title.setText("Không có dữ liệu!");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getDataQuangCao() {
        mDatabase.child("sanPhamQuangCao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (loai == 2) {
                    listSPQuangCao.clear();
                    sanPhamQuangCaoAdapter.notifyDataSetChanged();

                    if (snapshot.getValue() != null) {
                        title.setText("");
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            for (DataSnapshot data : snap.child("sanpham").getChildren()) {
                                listSPQuangCao.add(data.getValue(Product.class));
                            }
                        }
                        sanPhamQuangCaoAdapter.notifyDataSetChanged();
                    } else {
                        title.setVisibility(View.VISIBLE);
                        title.setText("Không có dữ liệu!");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}